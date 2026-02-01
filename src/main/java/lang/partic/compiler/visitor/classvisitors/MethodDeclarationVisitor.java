package lang.partic.compiler.visitor.classvisitors;

import lang.partic.compiler.antlr.ParticBaseVisitor;
import lang.partic.compiler.antlr.ParticParser;
import lang.partic.compiler.context.VisitContext;
import lang.partic.compiler.exceptions.CompileError;
import lang.partic.compiler.ir.*;
import lang.partic.compiler.ir.ParticMethodBody.LocalVar;
import lang.partic.compiler.ir.ParticMethodBody.ReturnStatement;
import lang.partic.compiler.ir.ParticMethodBody.TempVar;
import lang.partic.compiler.symbol.ClassSymbol;
import lang.partic.compiler.symbol.MethodSymbol;
import lang.partic.compiler.utils.TypeUtils;
import lang.partic.compiler.visitor.expressionvisitors.ExpressionVisitor;
import lang.partic.compiler.visitor.statementvisitors.StatementVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Pass 2: 方法声明访问器
 * 
 * 处理方法体，生成 IR（此时符号表已由 Pass 1 建立）
 */
public class MethodDeclarationVisitor extends ParticBaseVisitor<ParticMethod> {
    private final VisitContext context;

    public MethodDeclarationVisitor(VisitContext context) {
        this.context = context;
    }

    @Override
    public ParticMethod visitMethodDeclaration(ParticParser.MethodDeclarationContext ctx) {
        String methodName = ctx.IDENTIFIER().getText();
        String resolvedReturnType = context.resolveType(ctx.type().getText());
        
        // 收集参数类型（用于从符号表查找方法）
        List<String> paramTypes = new ArrayList<>();
        if (ctx.parameterList() != null) {
            for (ParticParser.ParameterContext paramCtx : ctx.parameterList().parameter()) {
                paramTypes.add(context.resolveType(paramCtx.type().getText()));
            }
        }
        
        // 从符号表获取 MethodSymbol
        MethodSymbol methodSymbol = null;
        ClassSymbol currentClass = context.getCurrentClass();
        if (currentClass != null) {
            methodSymbol = currentClass.resolveMethod(methodName, paramTypes);
        }
        
        // 设置当前方法
        context.setCurrentMethod(methodSymbol);
        
        // 进入方法体作用域
        if (methodSymbol != null && methodSymbol.getBodyScope() != null) {
            context.getSymbolTable().enterScope(methodSymbol.getBodyScope());
        }
        
        // 创建 IR 方法对象
        ParticMethod method = new ParticMethod(methodName, resolvedReturnType);
        
        // 方法修饰符
        ParticModifiers modifiers = new ParticModifiers();
        if (ctx.modifiers() != null) {
            for (ParticParser.ModifierContext modCtx : ctx.modifiers().modifier()) {
                String mod = modCtx.getText();
                switch (mod) {
                    case "pub", "priv", "prot", "pack" -> modifiers.setAccess(mod);
                    default -> modifiers.addOther(mod);
                }
            }
        }
        method.setModifiers(modifiers);
        
        // 注解
        for (ParticParser.AnnotationContext annCtx : ctx.annotation()) {
            String annotationType = annCtx.qualifiedName().getText();
            String resolvedAnnotation = context.resolveType(annotationType);
            ParticAnnotation annotation = new ParticAnnotation(resolvedAnnotation);
            
            if (annCtx.elementValuePairs() != null) {
                // TODO: 解析注解参数
            }
            
            method.addAnnotation(annotation);
        }
        
        // 参数（IR 中也需要记录）
        if (ctx.parameterList() != null) {
            for (ParticParser.ParameterContext paramCtx : ctx.parameterList().parameter()) {
                String paramType = context.resolveType(paramCtx.type().getText());
                String paramName = paramCtx.IDENTIFIER().getText();
                
                ParticParameter param = new ParticParameter(paramType, paramName);
                
                // 参数注解
                for (ParticParser.AnnotationContext annCtx : paramCtx.annotation()) {
                    String annotationType = annCtx.qualifiedName().getText();
                    String resolvedAnnotation = context.resolveType(annotationType);
                    ParticAnnotation annotation = new ParticAnnotation(resolvedAnnotation);
                    
                    if (annCtx.elementValuePairs() != null) {
                        // TODO: 解析注解参数
                    }
                    
                    param.addAnnotation(annotation);
                }
                
                method.addParameter(param);
            }
        }
        
        // 方法体
        if (ctx.block() != null) {
            // 传统块语法: { ... }
            ParticMethodBody body = new ParticMethodBody();
            
            StatementVisitor stmtVisitor = new StatementVisitor(body, context);
            for (ParticParser.StatementContext stmtCtx : ctx.block().statement()) {
                stmtVisitor.visit(stmtCtx);
            }

            method.setBody(body);
        } else if (ctx.expression() != null) {
            // 表达式体语法: -> expression;
            ParticMethodBody body = new ParticMethodBody();
            
            // 将参数添加到方法体的 locals 中（这样 ExpressionVisitor 才能访问参数）
            if (methodSymbol != null) {
                for (var param : methodSymbol.getParameters()) {
                    body.addLocal(param.getName(), param.getType(), 0);
                }
            }
            
            ExpressionVisitor exprVisitor = new ExpressionVisitor(body, context);
            String tempName = exprVisitor.visit(ctx.expression());
            String exprType = getExpressionType(tempName, body);
            
            // 检查返回类型
            if (methodSymbol != null) {
                String returnType = methodSymbol.getType();
                
                // void 方法不能返回值
                if (returnType != null && returnType.equals("void")) {
                    throw new CompileError("void 方法不能返回值");
                }
                
                // 检查返回类型是否兼容
                if (exprType != null && returnType != null && !TypeUtils.isAssignableFrom(returnType, exprType)) {
                    throw new CompileError("返回类型不匹配: 方法返回类型是 '" + returnType + "'，但返回表达式的类型是 '" + exprType + "'");
                }
            }
            
            // 自动生成 return 语句
            body.addStatement(new ReturnStatement(tempName));
            
            method.setBody(body);
        }
        
        // 退出方法体作用域
        if (methodSymbol != null && methodSymbol.getBodyScope() != null) {
            context.getSymbolTable().exitScope();
        }
        
        // 清除当前方法
        context.setCurrentMethod(null);
        
        return method;
    }

    /**
     * 获取表达式的类型
     */
    private String getExpressionType(String tempName, ParticMethodBody body) {
        if (tempName == null) return null;
        
        // 先查临时变量
        TempVar temp = body.getTemps().get(tempName);
        if (temp != null) {
            return temp.getType();
        }
        
        // 再查局部变量
        LocalVar local = body.getLocals().get(tempName);
        if (local != null) {
            return local.getType();
        }
        
        return null;
    }
}

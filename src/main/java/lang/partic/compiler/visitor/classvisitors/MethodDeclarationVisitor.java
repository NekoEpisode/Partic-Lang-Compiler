package lang.partic.compiler.visitor.classvisitors;

import lang.partic.compiler.antlr.ParticBaseVisitor;
import lang.partic.compiler.antlr.ParticParser;
import lang.partic.compiler.context.VisitContext;
import lang.partic.compiler.exceptions.CompileError;
import lang.partic.compiler.ir.*;
import lang.partic.compiler.ir.ParticMethodBody.ReturnStatement;
import lang.partic.compiler.manager.ImportManager;
import lang.partic.compiler.visitor.expressionvisitors.ExpressionVisitor;
import lang.partic.compiler.visitor.statementvisitors.StatementVisitor;

public class MethodDeclarationVisitor extends ParticBaseVisitor<ParticMethod> {
    private final VisitContext context;

    public MethodDeclarationVisitor(VisitContext context) {
        this.context = context;
    }

    /**
     * 解析类型名称为完整类名
     * @param typeName 类型名称（可能是简单名、别名或完整名）
     * @return 完整类名
     * @throws CompileError 如果无法解析类型
     */
    private String resolveType(String typeName) {
        ImportManager importManager = context.getImportManager();
        String fullName = importManager.findFullName(typeName);
        
        if (fullName == null) {
            throw new CompileError("Cannot resolve type: " + typeName);
        }
        
        return fullName;
    }

    @Override
    public ParticMethod visitMethodDeclaration(ParticParser.MethodDeclarationContext ctx) {
        // 返回类型 - 解析为完整类名
        String returnType = ctx.type().getText();
        String resolvedReturnType = resolveType(returnType);
        
        // 创建方法对象
        String methodName = ctx.IDENTIFIER().getText();
        ParticMethod method = new ParticMethod(methodName, resolvedReturnType);
        
        // 方法修饰符
        ParticModifiers modifiers = new ParticModifiers();
        if (ctx.modifiers() != null) {
            for (ParticParser.ModifierContext modCtx : ctx.modifiers().modifier()) {
                String mod = modCtx.getText();
                // 访问修饰符：pub, priv, prot, pack
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
            String resolvedAnnotation = resolveType(annotationType);
            ParticAnnotation annotation = new ParticAnnotation(resolvedAnnotation);
            
            if (annCtx.elementValuePairs() != null) {
                // TODO: 解析注解参数
            }
            
            method.addAnnotation(annotation);
        }
        
        // 参数
        if (ctx.parameterList() != null) {
            for (ParticParser.ParameterContext paramCtx : ctx.parameterList().parameter()) {
                // 参数类型 - 解析为完整类名
                String paramType = paramCtx.type().getText();
                String resolvedParamType = resolveType(paramType);
                String paramName = paramCtx.IDENTIFIER().getText();
                
                ParticParameter param = new ParticParameter(resolvedParamType, paramName);
                
                // 参数注解
                for (ParticParser.AnnotationContext annCtx : paramCtx.annotation()) {
                    String annotationType = annCtx.qualifiedName().getText();
                    String resolvedAnnotation = resolveType(annotationType);
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
            
            // 使用StatementVisitor处理方法体
            StatementVisitor stmtVisitor = new StatementVisitor(body, context);
            for (ParticParser.StatementContext stmtCtx : ctx.block().statement()) {
                stmtVisitor.visit(stmtCtx);
            }

            method.setBody(body);
        } else if (ctx.expression() != null) {
            // 表达式体语法: -> expression;
            ParticMethodBody body = new ParticMethodBody();
            
            // 创建ExpressionVisitor处理表达式
            ExpressionVisitor exprVisitor = new ExpressionVisitor(body, context);
            String tempName = exprVisitor.visit(ctx.expression());
            
            // 自动生成return语句
            body.addStatement(new ReturnStatement(tempName));
            
            method.setBody(body);
        }
        
        return method;
    }
}

package lang.partic.compiler.visitor.statementvisitors;

import lang.partic.compiler.antlr.ParticBaseVisitor;
import lang.partic.compiler.antlr.ParticParser;
import lang.partic.compiler.context.VisitContext;
import lang.partic.compiler.exceptions.CompileError;
import lang.partic.compiler.ir.ParticMethodBody;
import lang.partic.compiler.ir.ParticMethodBody.*;
import lang.partic.compiler.utils.TypeUtils;
import lang.partic.compiler.visitor.expressionvisitors.ExpressionVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 语句访问器
 * 处理方法体中的所有语句类型
 */
public class StatementVisitor extends ParticBaseVisitor<Void> {
    private static final Logger log = LoggerFactory.getLogger(StatementVisitor.class);
    
    private final ParticMethodBody body;
    private final VisitContext context;
    private final ExpressionVisitor exprVisitor;
    private int scopeLevel; // 当前作用域层级，0表示方法级作用域
    private int scopeIdCounter; // 作用域ID计数器
    private final Map<String, Integer> varScopes; // 变量名 -> 作用域层级

    public StatementVisitor(ParticMethodBody body, VisitContext context) {
        this.body = body;
        this.context = context;
        this.exprVisitor = new ExpressionVisitor(body, context);
        this.scopeLevel = 0;
        this.scopeIdCounter = 0;
        this.varScopes = new HashMap<>();
    }

    /**
     * 解析类型名称为完整类名
     */
    private String resolveType(String typeName) {
        return context.resolveType(typeName);
    }

    /**
     * 获取临时变量或局部变量的类型
     */
    private String getType(String name) {
        if (name == null) return null;
        
        // 先查临时变量
        TempVar temp = body.getTemps().get(name);
        if (temp != null) {
            return temp.getType();
        }
        
        // 再查局部变量
        ParticMethodBody.LocalVar local = body.getLocals().get(name);
        if (local != null) {
            return local.getType();
        }
        
        return null;
    }

    @Override
    public Void visitStatement(ParticParser.StatementContext ctx) {
        // statement会自动分发到具体的语句类型
        return visitChildren(ctx);
    }

    @Override
    public Void visitBlock(ParticParser.BlockContext ctx) {
        // 进入新作用域
        scopeLevel++;
        int currentScopeId = ++scopeIdCounter;
        body.addStatement(new ScopeStatement(currentScopeId, true));
        
        // 处理块内的所有语句
        for (ParticParser.StatementContext stmtCtx : ctx.statement()) {
            visit(stmtCtx);
        }
        
        // 退出作用域
        body.addStatement(new ScopeStatement(currentScopeId, false));
        scopeLevel--;
        
        return null;
    }

    @Override
    public Void visitVariableDeclarationStatement(ParticParser.VariableDeclarationStatementContext ctx) {
        // variableDeclarationStatement: modifiers type variableDeclarator (',' variableDeclarator)* ';'
        
        // 解析声明类型
        String declaredType = ctx.type().getText();
        boolean isTypeInference = declaredType.equals("var") || declaredType.equals("val");
        String resolvedType = isTypeInference ? null : resolveType(declaredType);
        
        // 解析修饰符
        List<String> modifiers = new ArrayList<>();
        if (ctx.modifiers() != null) {
            for (ParticParser.ModifierContext modCtx : ctx.modifiers().modifier()) {
                modifiers.add(modCtx.getText());
            }
        }
        
        // val 自动添加 final 修饰符
        if (declaredType.equals("val") && !modifiers.contains("final")) {
            modifiers.add("final");
        }
        
        // 处理每个变量声明
        for (ParticParser.VariableDeclaratorContext varCtx : ctx.variableDeclarator()) {
            String varName = varCtx.IDENTIFIER().getText();
            
            // 检查是否需要重命名（同名变量在不同作用域）
            if (varScopes.containsKey(varName) && varScopes.get(varName) < scopeLevel) {
                varName = varName + "$" + scopeLevel;
            }
            
            // 记录变量的作用域
            varScopes.put(varName, scopeLevel);
            
            // 处理初始化表达式
            String tempName = null;
            String exprType = null;
            
            if (varCtx.expression() != null) {
                tempName = exprVisitor.visit(varCtx.expression());
                exprType = getType(tempName);
            }
            
            // 确定变量的最终类型
            String finalType;
            if (isTypeInference) {
                // var/val: 类型推断
                if (exprType == null) {
                    throw new CompileError("使用 " + declaredType + " 声明变量时必须提供初始化表达式");
                }
                finalType = exprType;
                log.debug("类型推断: {} {} = ... → 推断类型: {}", declaredType, varName, finalType);
            } else {
                // 显式类型声明
                finalType = resolvedType;
                
                // 类型检查
                if (exprType != null && !TypeUtils.isAssignableFrom(finalType, exprType)) {
                    throw new CompileError("类型不匹配: 不能将 '" + exprType + "' 赋值给 '" + finalType + "'");
                }
                log.debug("类型检查: {} {} = ... → 声明类型: {}, 表达式类型: {}", declaredType, varName, finalType, exprType);
            }
            
            // 添加到 locals
            body.addLocal(varName, finalType, scopeLevel, modifiers);
            
            // 生成赋值语句
            if (tempName != null) {
                body.addStatement(new AssignStatement(varName, tempName));
            }
        }
        
        return null;
    }

    @Override
    public Void visitExpressionStatement(ParticParser.ExpressionStatementContext ctx) {
        // expressionStatement: expression ';'
        // 表达式语句（如方法调用、赋值等）
        String tempName = exprVisitor.visit(ctx.expression());
        
        // 记录需要执行的表达式
        body.addStatement(new ExpressionStatement(tempName));
        return null;
    }

    @Override
    public Void visitReturnStatement(ParticParser.ReturnStatementContext ctx) {
        // returnStatement: 'return' expression? ';'
        if (ctx.expression() != null) {
            String tempName = exprVisitor.visit(ctx.expression());
            String exprType = getType(tempName);
            
            // 检查返回类型
            var currentMethod = context.getCurrentMethod();
            if (currentMethod != null) {
                String returnType = currentMethod.getType();
                
                // void 方法不能返回值
                if (returnType != null && returnType.equals("void")) {
                    throw new CompileError("void 方法不能返回值");
                }
                
                // 检查返回类型是否兼容
                if (exprType != null && returnType != null && !TypeUtils.isAssignableFrom(returnType, exprType)) {
                    throw new CompileError("返回类型不匹配: 方法返回类型是 '" + returnType + "'，但返回表达式的类型是 '" + exprType + "'");
                }
            }
            
            body.addStatement(new ReturnStatement(tempName));
        } else {
            // 无返回值的 return
            var currentMethod = context.getCurrentMethod();
            if (currentMethod != null) {
                String returnType = currentMethod.getType();
                // void 方法可以没有返回值，非 void 方法必须有返回值
                if (returnType != null && !returnType.equals("void")) {
                    throw new CompileError("方法返回类型是 '" + returnType + "'，但 return 语句没有返回值");
                }
            }
            body.addStatement(new ReturnStatement(null));
        }
        return null;
    }

    @Override
    public Void visitIfStatement(ParticParser.IfStatementContext ctx) {
        // TODO: 处理if语句
        // 需要创建IfStatement类
        return null;
    }

    @Override
    public Void visitWhileStatement(ParticParser.WhileStatementContext ctx) {
        // TODO: 处理while语句
        // 需要创建WhileStatement类
        return null;
    }

    @Override
    public Void visitDoWhileStatement(ParticParser.DoWhileStatementContext ctx) {
        // TODO: 处理do-while语句
        return null;
    }

    @Override
    public Void visitForStatement(ParticParser.ForStatementContext ctx) {
        // TODO: 处理for语句
        return null;
    }

    @Override
    public Void visitForEachStatement(ParticParser.ForEachStatementContext ctx) {
        // TODO: 处理for-each语句
        return null;
    }

    @Override
    public Void visitSwitchStatement(ParticParser.SwitchStatementContext ctx) {
        // TODO: 处理switch语句
        return null;
    }

    @Override
    public Void visitBreakStatement(ParticParser.BreakStatementContext ctx) {
        // TODO: 处理break语句
        return null;
    }

    @Override
    public Void visitContinueStatement(ParticParser.ContinueStatementContext ctx) {
        // TODO: 处理continue语句
        return null;
    }

    @Override
    public Void visitThrowStatement(ParticParser.ThrowStatementContext ctx) {
        // TODO: 处理throw语句
        return null;
    }

    @Override
    public Void visitTryStatement(ParticParser.TryStatementContext ctx) {
        // TODO: 处理try-catch-finally语句
        return null;
    }

    @Override
    public Void visitSynchronizedStatement(ParticParser.SynchronizedStatementContext ctx) {
        // TODO: 处理synchronized语句
        return null;
    }

    @Override
    public Void visitAssertStatement(ParticParser.AssertStatementContext ctx) {
        // TODO: 处理assert语句
        return null;
    }
}

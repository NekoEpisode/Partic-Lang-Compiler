package lang.partic.compiler.visitor.statementvisitors;

import lang.partic.compiler.antlr.ParticBaseVisitor;
import lang.partic.compiler.antlr.ParticParser;
import lang.partic.compiler.context.VisitContext;
import lang.partic.compiler.exceptions.CompileError;
import lang.partic.compiler.ir.ParticMethodBody;
import lang.partic.compiler.ir.ParticMethodBody.AssignStatement;
import lang.partic.compiler.ir.ParticMethodBody.ReturnStatement;
import lang.partic.compiler.ir.ParticMethodBody.ScopeStatement;
import lang.partic.compiler.manager.ImportManager;
import lang.partic.compiler.visitor.expressionvisitors.ExpressionVisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 语句访问器
 * 处理方法体中的所有语句类型
 */
public class StatementVisitor extends ParticBaseVisitor<Void> {
    private final ParticMethodBody body;
    private final VisitContext context;
    private final ExpressionVisitor exprVisitor;
    private int scopeLevel; // 当前作用域层级，0表示方法级作用域
    private int scopeIdCounter; // 作用域ID计数器
    private Map<String, Integer> varScopes; // 变量名 -> 作用域层级

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
        ImportManager importManager = context.getImportManager();
        String fullName = importManager.findFullName(typeName);
        
        if (fullName == null) {
            throw new CompileError("Cannot resolve type: " + typeName);
        }
        
        return fullName;
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
        
        // 解析类型
        String type = ctx.type().getText();
        String resolvedType = resolveType(type);
        
        // 解析修饰符
        List<String> modifiers = new ArrayList<>();
        if (ctx.modifiers() != null) {
            for (ParticParser.ModifierContext modCtx : ctx.modifiers().modifier()) {
                modifiers.add(modCtx.getText());
            }
        }
        
        // 处理每个变量声明
        for (ParticParser.VariableDeclaratorContext varCtx : ctx.variableDeclarator()) {
            String varName = varCtx.IDENTIFIER().getText();
            
            // 检查是否需要重命名（同名变量在不同作用域）
            if (varScopes.containsKey(varName) && varScopes.get(varName) < scopeLevel) {
                // 需要重命名
                varName = varName + "$" + scopeLevel;
            }
            
            // 记录变量的作用域
            varScopes.put(varName, scopeLevel);
            
            // 添加到locals
            body.addLocal(varName, resolvedType, scopeLevel, modifiers);
            
            // 如果有初始化表达式，生成赋值语句
            if (varCtx.expression() != null) {
                String tempName = exprVisitor.visit(varCtx.expression());
                body.addStatement(new AssignStatement(varName, tempName));
            }
        }
        
        return null;
    }

    @Override
    public Void visitExpressionStatement(ParticParser.ExpressionStatementContext ctx) {
        // expressionStatement: expression ';'
        // 表达式语句（如方法调用、赋值等）
        exprVisitor.visit(ctx.expression());
        return null;
    }

    @Override
    public Void visitReturnStatement(ParticParser.ReturnStatementContext ctx) {
        // returnStatement: 'return' expression? ';'
        if (ctx.expression() != null) {
            String tempName = exprVisitor.visit(ctx.expression());
            body.addStatement(new ReturnStatement(tempName));
        } else {
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

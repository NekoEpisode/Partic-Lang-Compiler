package io.github.nekosora.visitor;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.nekosora.antlr.ParticBaseVisitor;
import io.github.nekosora.antlr.ParticParser;
import io.github.nekosora.context.CompileContext;
import io.github.nekosora.context.ImportManager;
import io.github.nekosora.context.function.ParticFunction;
import io.github.nekosora.exception.CompileError;
import io.github.nekosora.logger.ParticLogger;
import io.github.nekosora.utils.JsonUtils;
import io.github.nekosora.utils.TypeUtils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ParticCompilerVisitor extends ParticBaseVisitor<JsonObject> {
    private final CompileContext context;
    private final Path saveFilePath;

    public ParticCompilerVisitor(Path saveFilePath) {
        this.context = new CompileContext();
        this.saveFilePath = saveFilePath;
    }

    @Override
    public JsonObject visitProgram(ParticParser.ProgramContext ctx) {
        context.setStartTime(System.currentTimeMillis());
        ParticLogger.mainInfo("开始编译流程，保存class到 \"" + saveFilePath.toString() + "\" ...");
        ParticLogger.semanticInfo("开始语义分析收集信息...");

        ctx.importStatement().forEach(this::visit);

        ParticLogger.semanticInfo("第一遍扫描：收集函数签名...");
        for (ParticParser.FunctionDeclarationContext funcCtx : ctx.functionDeclaration()) {
            collectFunctionSignature(funcCtx);
        }

        ParticLogger.semanticInfo("第二遍扫描：处理函数体...");
        for (ParticParser.FunctionDeclarationContext funcCtx : ctx.functionDeclaration()) {
            processFunctionBody(funcCtx);
        }

        ParticLogger.semanticInfo("语义信息收集完成");
        new ParticCompiler(context, saveFilePath).compile();
        return null;
    }

    private void collectFunctionSignature(ParticParser.FunctionDeclarationContext ctx) {
        List<ParticParser.ParameterContext> parameterContexts =
                ctx.parameterList() == null ? new ArrayList<>() : ctx.parameterList().parameter();

        StringBuilder descriptor = new StringBuilder("(");

        for (ParticParser.ParameterContext parameterContext : parameterContexts) {
            String fullParamTypeName = resolveFullClassName(parameterContext.type());
            String paramDescriptor = TypeUtils.toDescriptor(fullParamTypeName);
            descriptor.append(paramDescriptor);
        }

        descriptor.append(")");
        String fullReturnTypeName = resolveFullClassName(ctx.type());
        descriptor.append(TypeUtils.toDescriptor(fullReturnTypeName));

        ParticFunction function = new ParticFunction(
                ctx.IDENTIFIER().getText(),
                descriptor.toString()
        );
        context.getFunctionManager().addFunction(function);
    }

    private void processFunctionBody(ParticParser.FunctionDeclarationContext ctx) {
        context.resetLocalVariables();

        ParticFunction function = context.getFunctionManager().getFunction(ctx.IDENTIFIER().getText());
        context.setCurrentFunction(function);

        List<ParticParser.ParameterContext> parameterContexts =
                ctx.parameterList() == null ? new ArrayList<>() : ctx.parameterList().parameter();

        for (ParticParser.ParameterContext parameterContext : parameterContexts) {
            String fullParamTypeName = resolveFullClassName(parameterContext.type());
            String paramName = parameterContext.IDENTIFIER().getText();
            String paramDescriptor = TypeUtils.toDescriptor(fullParamTypeName);
            context.allocateLocalVariable(paramName, paramDescriptor);
        }

        for (ParticParser.StatementContext stmt : ctx.block().statement()) {
            function.getActions().add(visit(stmt));
        }

        function.setLocalVariables(context.getLocalVariablesMap());
    }

    @Override
    public JsonObject visitImportStatement(ParticParser.ImportStatementContext ctx) {
        ImportManager importManager = context.getImportManager();
        String qualifiedName = ctx.qualifiedName().getText();
        String simpleName = qualifiedName.substring(qualifiedName.lastIndexOf('.') + 1);
        if (ctx.IDENTIFIER() != null) {
            String alias = ctx.IDENTIFIER().getText();
            importManager.addImport(qualifiedName, simpleName, alias);
        } else {
            importManager.addImport(qualifiedName, simpleName);
        }
        return null;
    }

    @Override
    public JsonObject visitLocalVariableDeclaration(ParticParser.LocalVariableDeclarationContext ctx) {
        JsonObject action = new JsonObject();
        action.addProperty("type", "VARIABLE_DECLARATION");

        String fullTypeName = resolveFullClassName(ctx.type());
        String varName = ctx.IDENTIFIER().getText();

        action.addProperty("varType", fullTypeName);
        action.addProperty("varName", varName);

        context.allocateLocalVariable(varName, TypeUtils.toDescriptor(fullTypeName));

        if (ctx.expression() != null) {
            action.add("initialValue", visit(ctx.expression()));
        }
        return action;
    }

    @Override
    public JsonObject visitExpressionStatement(ParticParser.ExpressionStatementContext ctx) {
        JsonObject action = new JsonObject();
        action.addProperty("type", "EXPRESSION_STATEMENT");
        action.add("expression", visit(ctx.expression()));
        return action;
    }

    @Override
    public JsonObject visitPrimaryExpression(ParticParser.PrimaryExpressionContext ctx) {
        return visit(ctx.primary());
    }

    @Override
    public JsonObject visitPrimary(ParticParser.PrimaryContext ctx) {
        if (ctx.literal() != null) {
            return JsonUtils.parseLiteral(ctx.literal());
        }

        if (ctx.getText().startsWith("new")) {
            String className = ctx.classType().getText();
            JsonArray args = new JsonArray();
            if (ctx.argumentList() != null) {
                for (ParticParser.ExpressionContext expr : ctx.argumentList().expression()) {
                    args.add(visit(expr));
                }
            }
            return JsonUtils.generateObjectCreation(className, args);
        }

        if (ctx.IDENTIFIER() != null) {
            String name = ctx.IDENTIFIER().getText();
            if(context.getLocalVariable(name) != null) {
                return JsonUtils.generateVariableLoad(name);
            } else if (context.getFunctionManager().getFunction(name) != null) {
                return JsonUtils.generateFunctionReference(name);
            } else {
                return JsonUtils.generateStaticAccess(name);
            }
        }

        if (ctx.qualifiedName() != null) {
            return JsonUtils.generateStaticAccess(ctx.qualifiedName().getText());
        }

        if (ctx.expression() != null) {
            return visit(ctx.expression());
        }

        throw new CompileError("Unsupported primary expression: " + ctx.getText());
    }

    @Override
    public JsonObject visitFunctionCallExpression(ParticParser.FunctionCallExpressionContext ctx) {
        JsonObject methodCall = new JsonObject();
        methodCall.addProperty("type", "METHOD_CALL");
        JsonObject target = visit(ctx.postfixExpression());
        methodCall.add("target", target);

        if (!target.has("type") || !target.get("type").getAsString().equals("FUNCTION_REF")) {
            throw new CompileError("Expression '" + ctx.postfixExpression().getText() + "' is not a function and cannot be called.");
        }

        methodCall.addProperty("methodName", target.get("name").getAsString());

        JsonArray args = new JsonArray();
        if (ctx.argumentList() != null) {
            for (ParticParser.ExpressionContext expr : ctx.argumentList().expression()) {
                args.add(visit(expr));
            }
        }
        methodCall.add("args", args);
        return methodCall;
    }

    @Override
    public JsonObject visitMethodCallExpression(ParticParser.MethodCallExpressionContext ctx) {
        JsonObject target = visit(ctx.postfixExpression());
        String methodName = ctx.IDENTIFIER().getText();
        JsonArray args = new JsonArray();
        if (ctx.argumentList() != null) {
            for (ParticParser.ExpressionContext expr : ctx.argumentList().expression()) {
                args.add(visit(expr));
            }
        }

        return JsonUtils.generateMethodCall(target, methodName, args);
    }

    @Override
    public JsonObject visitFieldAccessExpression(ParticParser.FieldAccessExpressionContext ctx) {
        JsonObject target = visit(ctx.postfixExpression());
        String fieldName = ctx.IDENTIFIER().getText();
        return JsonUtils.generateFieldAccess(target, fieldName);
    }

    @Override
    public JsonObject visitReturnStatement(ParticParser.ReturnStatementContext ctx) {
        JsonObject action = new JsonObject();
        action.addProperty("type", "RETURN_STATEMENT");
        if (ctx.expression() != null) {
            action.add("expression", visit(ctx.expression()));
        }
        return action;
    }

    @Override
    public JsonObject visitAdditiveExpression(ParticParser.AdditiveExpressionContext ctx) {
        JsonObject result = visit(ctx.multiplicativeExpression(0));

        for (int i = 1; i < ctx.multiplicativeExpression().size(); i++) {
            String operator = ctx.getChild(i * 2 - 1).getText(); // 获取 + 或 -
            JsonObject right = visit(ctx.multiplicativeExpression(i));

            JsonObject binaryOp = new JsonObject();
            binaryOp.addProperty("type", "BINARY_OPERATION");
            binaryOp.addProperty("operator", operator);
            binaryOp.add("left", result);
            binaryOp.add("right", right);
            result = binaryOp;
        }

        return result;
    }

    @Override
    public JsonObject visitMultiplicativeExpression(ParticParser.MultiplicativeExpressionContext ctx) {
        JsonObject result = visit(ctx.unaryExpression(0));

        for (int i = 1; i < ctx.unaryExpression().size(); i++) {
            String operator = ctx.getChild(i * 2 - 1).getText();
            JsonObject right = visit(ctx.unaryExpression(i));

            JsonObject binaryOp = new JsonObject();
            binaryOp.addProperty("type", "BINARY_OPERATION");
            binaryOp.addProperty("operator", operator);
            binaryOp.add("left", result);
            binaryOp.add("right", right);
            result = binaryOp;
        }

        return result;
    }

    @Override
    public JsonObject visitIfStatement(ParticParser.IfStatementContext ctx) {
        JsonObject action = new JsonObject();
        action.addProperty("type", "IF_STATEMENT");

        // 处理条件表达式
        action.add("condition", visit(ctx.expression()));

        // 处理then分支
        action.add("thenBranch", visit(ctx.statement(0)));

        // 处理else分支（如果存在）
        if (ctx.statement().size() > 1) {
            action.add("elseBranch", visit(ctx.statement(1)));
        }

        return action;
    }

    @Override
    public JsonObject visitRelationalExpression(ParticParser.RelationalExpressionContext ctx) {
        JsonObject result = visit(ctx.shiftExpression(0));

        for (int i = 1; i < ctx.shiftExpression().size(); i++) {
            String operator = ctx.getChild(i * 2 - 1).getText(); // 获取 <, >, <=, >=
            JsonObject right = visit(ctx.shiftExpression(i));

            JsonObject comparison = new JsonObject();
            comparison.addProperty("type", "COMPARISON");
            comparison.addProperty("operator", operator);
            comparison.add("left", result);
            comparison.add("right", right);
            result = comparison;
        }

        return result;
    }

    @Override
    public JsonObject visitEqualityExpression(ParticParser.EqualityExpressionContext ctx) {
        JsonObject result = visit(ctx.relationalExpression(0));

        for (int i = 1; i < ctx.relationalExpression().size(); i++) {
            String operator = ctx.getChild(i * 2 - 1).getText(); // 获取 ==, !=
            JsonObject right = visit(ctx.relationalExpression(i));

            JsonObject comparison = new JsonObject();
            comparison.addProperty("type", "COMPARISON");
            comparison.addProperty("operator", operator);
            comparison.add("left", result);
            comparison.add("right", right);
            result = comparison;
        }

        return result;
    }

    @Override
    public JsonObject visitConditionalAndExpression(ParticParser.ConditionalAndExpressionContext ctx) {
        JsonObject result = visit(ctx.inclusiveOrExpression(0));

        for (int i = 1; i < ctx.inclusiveOrExpression().size(); i++) {
            JsonObject right = visit(ctx.inclusiveOrExpression(i));

            JsonObject logicalOp = new JsonObject();
            logicalOp.addProperty("type", "LOGICAL_OPERATION");
            logicalOp.addProperty("operator", "&&");
            logicalOp.add("left", result);
            logicalOp.add("right", right);
            result = logicalOp;
        }

        return result;
    }

    @Override
    public JsonObject visitConditionalOrExpression(ParticParser.ConditionalOrExpressionContext ctx) {
        JsonObject result = visit(ctx.conditionalAndExpression(0));

        for (int i = 1; i < ctx.conditionalAndExpression().size(); i++) {
            JsonObject right = visit(ctx.conditionalAndExpression(i));

            JsonObject logicalOp = new JsonObject();
            logicalOp.addProperty("type", "LOGICAL_OPERATION");
            logicalOp.addProperty("operator", "||");
            logicalOp.add("left", result);
            logicalOp.add("right", right);
            result = logicalOp;
        }

        return result;
    }

    @Override
    public JsonObject visitUnaryExpression(ParticParser.UnaryExpressionContext ctx) {
        if (ctx.postfixExpression() != null) {
            return visit(ctx.postfixExpression());
        }

        // 处理一元运算符
        String operator = ctx.getChild(0).getText();
        JsonObject operand = visit(ctx.unaryExpression());

        JsonObject unaryOp = new JsonObject();
        unaryOp.addProperty("type", "UNARY_OPERATION");
        unaryOp.addProperty("operator", operator);
        unaryOp.add("operand", operand);

        return unaryOp;
    }

    @Override
    public JsonObject visitWhileStatement(ParticParser.WhileStatementContext ctx) {
        JsonObject action = new JsonObject();
        action.addProperty("type", "WHILE_STATEMENT");

        // 处理条件表达式
        action.add("condition", visit(ctx.expression()));

        // 处理循环体
        action.add("body", visit(ctx.statement()));

        return action;
    }

    @Override
    public JsonObject visitPostIncrementExpression(ParticParser.PostIncrementExpressionContext ctx) {
        JsonObject action = new JsonObject();
        action.addProperty("type", "POST_INCREMENT");
        action.add("operand", visit(ctx.postfixExpression()));
        return action;
    }

    @Override
    public JsonObject visitPostDecrementExpression(ParticParser.PostDecrementExpressionContext ctx) {
        JsonObject action = new JsonObject();
        action.addProperty("type", "POST_DECREMENT");
        action.add("operand", visit(ctx.postfixExpression()));
        return action;
    }

    @Override
    protected JsonObject defaultResult() { return new JsonObject(); }

    @Override
    public JsonObject visitBlock(ParticParser.BlockContext ctx) {
        JsonObject blockAction = new JsonObject();
        blockAction.addProperty("type", "BLOCK_STATEMENT");
        JsonArray actions = new JsonArray();
        for (ParticParser.StatementContext stmtCtx : ctx.statement()) {
            JsonObject visitedAction = visit(stmtCtx);

            if (visitedAction == null || !visitedAction.has("type")) {
                // 详细信息
                throw new CompileError(
                        "Internal Visitor Error: A statement inside a block returned an invalid/empty JSON object.\n" +
                                "Problematic Statement Text: \"" + stmtCtx.getText() + "\"\n" +
                                "Location: line " + stmtCtx.getStart().getLine()
                );
            }
            actions.add(visitedAction);
        }
        blockAction.add("actions", actions);
        return blockAction;
    }

    @Override
    public JsonObject visitPassThroughAssignment(ParticParser.PassThroughAssignmentContext ctx) {
        return visit(ctx.conditionalExpression());
    }

    @Override
    public JsonObject visitAssignmentAction(ParticParser.AssignmentActionContext ctx) {
        JsonObject assignment = new JsonObject();
        assignment.addProperty("type", "ASSIGNMENT");

        // 取赋值目标（等号左边）
        JsonObject target = visit(ctx.postfixExpression());

        // 做一个简单的检查，确保我们是给一个变量赋值
        if (!"VARIABLE_LOAD".equals(target.get("type").getAsString())) {
            throw new CompileError("Assignment target must be a variable. Complex assignments (e.g., to fields) are not yet supported.");
        }
        assignment.add("target", target);

        // 取赋值操作符，例如 "=" 或 "+="
        assignment.addProperty("operator", ctx.assignmentOperator().getText());

        // 取等号右边的值
        assignment.add("value", visit(ctx.expression()));

        return assignment;
    }

    private String resolveFullClassName(ParticParser.TypeContext typeCtx) {
        String typeName = typeCtx.getText();
        if (typeCtx.primitiveType() == null) {
            String simpleName = typeCtx.classType().qualifiedName().getText();
            return context.getImportManager().foundFullName(simpleName) + (typeName.endsWith("[]") ? "[]" : "");
        }
        return typeName;
    }
}
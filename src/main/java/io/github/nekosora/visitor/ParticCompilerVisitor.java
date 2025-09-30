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
    protected JsonObject defaultResult() { return new JsonObject(); }

    @Override
    protected JsonObject aggregateResult(JsonObject aggregate, JsonObject nextResult) {
        return nextResult == null || nextResult.isEmpty() ? aggregate : nextResult;
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
package io.github.nekosora.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.nekosora.antlr.ParticParser;

/**
 * 一个纯粹的辅助类，用于创建代表不同语法结构（如字面量、方法调用）的 JsonObject。
 * 它不应该包含复杂的解析逻辑，解析工作应该由 Visitor 类完成。
 */
public class JsonUtils {
    public static final Gson GSON = new Gson();

    /**
     * 生成一个方法调用的 JsonObject。
     * @param target 调用目标 (例如，一个变量或另一个表达式的结果)
     * @param methodName 方法名
     * @param args 参数列表
     * @return 代表方法调用的 JsonObject
     */
    public static JsonObject generateMethodCall(JsonObject target, String methodName, JsonArray args) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "METHOD_CALL");
        jsonObject.add("target", target);
        jsonObject.addProperty("methodName", methodName);
        jsonObject.add("args", args);
        return jsonObject;
    }

    /**
     * 生成一个函数引用的 JsonObject。
     * @param functionName 函数名
     * @return 代表函数引用的 JsonObject
     */
    public static JsonObject generateFunctionReference(String functionName) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "FUNCTION_REF");
        jsonObject.addProperty("name", functionName);
        return jsonObject;
    }

    /**
     * 生成一个变量加载的 JsonObject。
     * @param varName 变量名
     * @return 代表变量加载的 JsonObject
     */
    public static JsonObject generateVariableLoad(String varName) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "VARIABLE_LOAD");
        jsonObject.addProperty("varName", varName);
        return jsonObject;
    }

    /**
     * 生成一个静态类访问的 JsonObject。
     * @param className 类名 (可以是簡稱，後續由 ImportManager 解析)
     * @return 代表静态类访问的 JsonObject
     */
    public static JsonObject generateStaticAccess(String className) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "STATIC_ACCESS");
        jsonObject.addProperty("className", className);
        return jsonObject;
    }

    /**
     * 生成一个字段访问的 JsonObject。
     * @param target 访问目标
     * @param fieldName 字段名
     * @return 代表字段访问的 JsonObject
     */
    public static JsonObject generateFieldAccess(JsonObject target, String fieldName) {
        JsonObject fieldAccess = new JsonObject();
        fieldAccess.addProperty("type", "FIELD_ACCESS");
        fieldAccess.add("target", target);
        fieldAccess.addProperty("fieldName", fieldName);
        return fieldAccess;
    }

    public static JsonObject generateStringLiteral(String value) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "stringLiteral");
        jsonObject.addProperty("value", value);
        return jsonObject;
    }

    public static JsonObject generateIntLiteral(int value) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "intLiteral");
        jsonObject.addProperty("value", value);
        return jsonObject;
    }

    public static JsonObject generateFloatLiteral(float value) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "floatLiteral");
        jsonObject.addProperty("value", value);
        return jsonObject;
    }

    public static JsonObject generateDoubleLiteral(double value) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "doubleLiteral");
        jsonObject.addProperty("value", value);
        return jsonObject;
    }

    public static JsonObject generateBooleanLiteral(boolean value) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "booleanLiteral");
        jsonObject.addProperty("value", value);
        return jsonObject;
    }

    public static JsonObject generateCharLiteral(char value) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "charLiteral");
        jsonObject.addProperty("value", value);
        return jsonObject;
    }

    public static JsonObject generateNullLiteral() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "nullLiteral");
        return jsonObject;
    }

    /**
     * 将 ANTLR 的 LiteralContext 解析为对应的 JsonObject。
     * 这个方法是合理的，因为它处理的是最底层的、确定的节点类型。
     * @param ctx ANTLR 的字面量上下文
     * @return 代表字面量的 JsonObject
     */
    public static JsonObject parseLiteral(ParticParser.LiteralContext ctx) {
        if (ctx.StringLiteral() != null) {
            String text = ctx.getText();
            // 移除前后的引号
            return generateStringLiteral(text.substring(1, text.length() - 1));
        } else if (ctx.IntegerLiteral() != null) {
            // 这里可以添加对 long, short 等类型的支持
            return generateIntLiteral(Integer.parseInt(ctx.getText()));
        } else if (ctx.BooleanLiteral() != null) {
            return generateBooleanLiteral(Boolean.parseBoolean(ctx.getText()));
        } else if (ctx.NullLiteral() != null) {
            return generateNullLiteral();
        } else if (ctx.FloatingPointLiteral() != null) {
            // 这里可以添加对 double 类型的支持
            return generateFloatLiteral(Float.parseFloat(ctx.getText()));
        } else if (ctx.CharacterLiteral() != null) {
            String text = ctx.getText();
            // 移除前后的单引号
            return generateCharLiteral(text.charAt(1));
        } else {
            // 应该不存在这种情况，除非语法规则改变
            throw new IllegalArgumentException("无法解析的字面量: " + ctx.getText());
        }
    }

    public static JsonObject generateObjectCreation(String className, JsonArray args) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "OBJECT_CREATION");
        jsonObject.addProperty("className", className);
        jsonObject.add("args", args);
        return jsonObject;
    }
}
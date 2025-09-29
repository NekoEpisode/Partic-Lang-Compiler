package io.github.nekosora.utils;

import io.github.nekosora.context.ImportManager;
import io.github.nekosora.exception.CompileError;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TypeUtils {
    private static final Map<String, String> PRIMITIVE_DESCRIPTORS = new HashMap<>();

    static {
        PRIMITIVE_DESCRIPTORS.put("int", "I");
        PRIMITIVE_DESCRIPTORS.put("long", "J");
        PRIMITIVE_DESCRIPTORS.put("double", "D");
        PRIMITIVE_DESCRIPTORS.put("float", "F");
        PRIMITIVE_DESCRIPTORS.put("bool", "Z");
        PRIMITIVE_DESCRIPTORS.put("char", "C");
        PRIMITIVE_DESCRIPTORS.put("byte", "B");
        PRIMITIVE_DESCRIPTORS.put("short", "S");
        PRIMITIVE_DESCRIPTORS.put("void", "V");
        PRIMITIVE_DESCRIPTORS.put("str", "Ljava/lang/String;");
    }

    /**
     * 将 Partic 类型转换为 JVM 描述符。
     *
     * @param particType Partic 类型
     * @return JVM 描述符
     */
    public static String toDescriptor(String particType) {
        if (particType.endsWith("[]")) {
            return "[" + toDescriptor(particType.substring(0, particType.length() - 2));
        }
        if (PRIMITIVE_DESCRIPTORS.containsKey(particType)) {
            return PRIMITIVE_DESCRIPTORS.get(particType);
        }
        return "L" + particType.replace('.', '/') + ";";
    }

    /**
     * 检查是否为基本类型。
     */
    public static boolean isPrimitive(String type) {
        return PRIMITIVE_DESCRIPTORS.containsKey(getBaseType(type));
    }

    private static String getBaseType(String t) {
        return isArray(t) ? t.substring(0, t.length() - 2) : t;
    }

    /**
     * 检查是否为数组。
     */
    public static boolean isArray(String type) {
        return type.endsWith("[]");
    }

    /**
     * 检查类型是否有效。
     *
     * @param typeName      类型名称（可能是别名、简单名、数组）
     * @param importManager 导入管理器
     * @return 如果类型有效则返回 true
     */
    public static boolean isValidType(String typeName, ImportManager importManager) {
        if (isPrimitive(typeName)) return true;
        if (typeName.endsWith("[]")) {
            return isValidType(typeName.substring(0, typeName.length() - 2), importManager);
        }
        try {
            importManager.foundFullName(typeName);
            return true;
        } catch (CompileError e) {
            return isJavaLangClass(typeName);
        }
    }

    private static boolean isJavaLangClass(String name) {
        return Set.of("String", "Object", "Integer", "Double", "Boolean",
                "Float", "Long", "Short", "Byte", "Character", "System", "Math").contains(name);
    }
}

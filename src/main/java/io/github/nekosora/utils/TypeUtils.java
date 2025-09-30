package io.github.nekosora.utils;

import io.github.nekosora.context.ImportManager;
import io.github.nekosora.exception.CompileError;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TypeUtils {
    private static final Map<String, String> PRIMITIVE_DESCRIPTORS = new HashMap<>();
    private static final Map<String, String> REVERSE_PRIMITIVE_DESCRIPTORS = new HashMap<>();

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

        for (Map.Entry<String, String> entry : PRIMITIVE_DESCRIPTORS.entrySet()) {
            REVERSE_PRIMITIVE_DESCRIPTORS.put(entry.getValue(), entry.getKey());
        }
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

    public static String getReturnType(String descriptor) {
        if (descriptor == null || !descriptor.startsWith("(")) {
            return "void";
        }

        int paramEnd = descriptor.indexOf(')');
        if (paramEnd == -1) {
            return "void";
        }

        String returnTypeDescriptor = descriptor.substring(paramEnd + 1);

        return descriptorToTypeName(returnTypeDescriptor);
    }

    private static String descriptorToTypeName(String descriptor) {
        if (descriptor == null || descriptor.isEmpty()) {
            return "void";
        }

        if (REVERSE_PRIMITIVE_DESCRIPTORS.containsKey(descriptor)) {
            return REVERSE_PRIMITIVE_DESCRIPTORS.get(descriptor);
        }

        if (descriptor.startsWith("[")) {
            String componentType = descriptorToTypeName(descriptor.substring(1));
            return componentType + "[]";
        }

        if (descriptor.startsWith("L") && descriptor.endsWith(";")) {
            String internalName = descriptor.substring(1, descriptor.length() - 1);
            return internalName.replace('/', '.');
        }

        return descriptor;
    }
}

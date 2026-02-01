package lang.partic.compiler.utils;

import lang.partic.compiler.ir.ParticMethod;
import lang.partic.compiler.ir.ParticParameter;

import java.util.List;

public class MethodDescriptorUtils {

    /**
     * 将类型转换为JVM描述符表示
     */
    public static String toDescriptorType(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Type cannot be null or empty");
        }

        type = type.trim();

        // 处理数组类型
        if (type.contains("[")) {
            return convertArrayType(type);
        }

        // 标准化类型（将 str 转换为 java.lang.String 等）
        type = TypeUtils.normalize(type);

        // 处理基本类型
        return switch (type) {
            case "int" -> "I";
            case "float" -> "F";
            case "double" -> "D";
            case "char" -> "C";
            case "byte" -> "B";
            case "short" -> "S";
            case "long" -> "J";
            case "bool" -> "Z";
            case "void" -> "V";
            default ->
                // 对象类型 - 假设已经是完整类名
                    "L" + type.replace('.', '/') + ";";
        };
    }

    /**
     * 转换数组类型
     */
    private static String convertArrayType(String type) {
        // 计算数组维度并提取基础类型
        int dimension = 0;
        String baseType = type;

        while (baseType.endsWith("[]")) {
            dimension++;
            baseType = baseType.substring(0, baseType.length() - 2);
        }

        // 或者处理 [ 和 ] 格式
        while (baseType.startsWith("[") || baseType.endsWith("]")) {
            if (baseType.startsWith("[")) {
                dimension++;
                baseType = baseType.substring(1);
            } else if (baseType.endsWith("]")) {
                baseType = baseType.substring(0, baseType.length() - 1);
            }
        }

        // 标准化基础类型（将 str 转换为 java.lang.String 等）
        baseType = TypeUtils.normalize(baseType);

        // 构建描述符
        StringBuilder descriptor = new StringBuilder();
        descriptor.append("[".repeat(Math.max(0, dimension)));

        // 添加基础类型的描述符
        String baseDescriptor = switch (baseType) {
            case "int" -> "I";
            case "float" -> "F";
            case "double" -> "D";
            case "char" -> "C";
            case "byte" -> "B";
            case "short" -> "S";
            case "long" -> "J";
            case "bool" -> "Z";
            case "void" -> "V";
            default ->
                // 对象类型数组
                    "L" + baseType.replace('.', '/') + ";";
        };

        descriptor.append(baseDescriptor);
        return descriptor.toString();
    }

    /**
     * 根据ParticMethod生成JVM方法描述符
     */
    public static String getMethodDescriptor(ParticMethod method) {
        if (method == null) {
            throw new IllegalArgumentException("Method cannot be null");
        }

        StringBuilder descriptor = new StringBuilder();

        // 添加参数描述符
        descriptor.append("(");

        List<ParticParameter> parameters = method.getParameters();
        if (parameters != null) {
            for (ParticParameter param : parameters) {
                String paramType = param.getType();
                descriptor.append(toDescriptorType(paramType));
            }
        }

        descriptor.append(")");

        // 添加返回值描述符
        String returnType = method.getReturnType();
        if (returnType == null) {
            throw new IllegalStateException("Method return type cannot be null");
        }

        descriptor.append(toDescriptorType(returnType));

        return descriptor.toString();
    }

    /**
     * 生成完整的方法签名（包含方法名和描述符）
     */
    public static String getMethodSignature(ParticMethod method) {
        return method.getName() + getMethodDescriptor(method);
    }
}
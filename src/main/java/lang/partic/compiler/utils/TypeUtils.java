package lang.partic.compiler.utils;

import java.util.Set;

/**
 * 类型工具类 - 提供类型检查、推断、转换等功能
 */
public class TypeUtils {
    
    // 数值类型（按提升顺序）
    private static final String[] NUMERIC_TYPES = {"byte", "short", "int", "long", "float", "double"};
    
    // Partic 基元类型
    private static final Set<String> PRIMITIVES = Set.of(
        "byte", "short", "int", "long", "float", "double", 
        "char", "bool", "void",  // bool 是 Partic 原生布尔类型
        // Partic 别名
        "str"
    );

    /**
     * 检查是否为基元类型
     */
    public static boolean isPrimitive(String type) {
        if (type == null) return false;
        // 去除数组标记
        type = type.replace("[", "").replace("]", "");
        return PRIMITIVES.contains(type);
    }

    /**
     * 检查是否为数值类型
     */
    public static boolean isNumeric(String type) {
        if (type == null) return false;
        return switch (type) {
            case "byte", "short", "int", "long", "float", "double" -> true;
            default -> false;
        };
    }

    /**
     * 检查是否为整数类型
     */
    public static boolean isIntegral(String type) {
        if (type == null) return false;
        return switch (type) {
            case "byte", "short", "int", "long", "char" -> true;
            default -> false;
        };
    }

    /**
     * 检查是否为浮点类型
     */
    public static boolean isFloatingPoint(String type) {
        return "float".equals(type) || "double".equals(type);
    }

    /**
     * 检查是否为布尔类型
     */
    public static boolean isBoolean(String type) {
        return "bool".equals(type);
    }

    /**
     * 标准化类型名（将 Partic 别名转为对应的完整类型）
     * 注意：bool 是 Partic 原生类型，不做转换
     */
    public static String normalize(String type) {
        if (type == null) return null;
        return switch (type) {
            case "str" -> "java.lang.String";
            default -> type;
        };
    }

    // ============ 类型推断 ============

    /**
     * 推断二元算术运算的结果类型（+, -, *, /, %）
     */
    public static String inferArithmeticType(String left, String right) {
        left = normalize(left);
        right = normalize(right);

        // 字符串拼接
        if ("java.lang.String".equals(left) || "java.lang.String".equals(right)) {
            return "java.lang.String";
        }

        // 数值类型提升
        if (isNumeric(left) && isNumeric(right)) {
            return promoteNumeric(left, right);
        }

        // 无法推断
        return null;
    }

    /**
     * 推断比较运算的结果类型（<, >, <=, >=, ==, !=）
     */
    public static String inferComparisonType(String left, String right) {
        // 比较运算结果总是 bool
        return "bool";
    }

    /**
     * 推断逻辑运算的结果类型（&&, ||, !）
     */
    public static String inferLogicalType(String left, String right) {
        return "bool";
    }

    /**
     * 推断位运算的结果类型（&, |, ^, <<, >>, >>>）
     */
    public static String inferBitwiseType(String left, String right) {
        left = normalize(left);
        right = normalize(right);

        // 位运算只对整数类型有效
        if (isIntegral(left) && isIntegral(right)) {
            // 如果任一操作数是 long，结果是 long；否则是 int
            if ("long".equals(left) || "long".equals(right)) {
                return "long";
            }
            return "int";
        }
        return null;
    }

    /**
     * 推断一元运算的结果类型
     */
    public static String inferUnaryType(String operandType, String operator) {
        operandType = normalize(operandType);

        return switch (operator) {
            case "+", "-" -> {
                // 一元正负号：数值类型保持不变（但至少提升到 int）
                if (isNumeric(operandType)) {
                    yield promoteToInt(operandType);
                }
                yield null;
            }
            case "++", "--" -> operandType;  // 自增自减保持原类型
            case "!" -> "bool";
            case "~" -> {
                // 按位取反：整数类型
                if (isIntegral(operandType)) {
                    yield promoteToInt(operandType);
                }
                yield null;
            }
            default -> null;
        };
    }

    /**
     * 数值类型提升（二元运算）
     */
    private static String promoteNumeric(String left, String right) {
        // 如果任一是 double，结果是 double
        if ("double".equals(left) || "double".equals(right)) return "double";
        // 如果任一是 float，结果是 float
        if ("float".equals(left) || "float".equals(right)) return "float";
        // 如果任一是 long，结果是 long
        if ("long".equals(left) || "long".equals(right)) return "long";
        // 其他情况结果是 int（byte, short, char 都提升到 int）
        return "int";
    }

    /**
     * 提升到至少 int（一元运算）
     */
    private static String promoteToInt(String type) {
        return switch (type) {
            case "byte", "short", "char" -> "int";
            default -> type;
        };
    }

    // ============ 类型兼容性 ============

    /**
     * 检查赋值兼容性：from 类型的值能否赋给 to 类型的变量
     */
    public static boolean isAssignableFrom(String to, String from) {
        if (to == null || from == null) return false;
        to = normalize(to);
        from = normalize(from);

        // 完全相同
        if (to.equals(from)) return true;

        // null 可以赋给任何引用类型
        if ("null".equals(from) && !isPrimitive(to)) return true;

        // 数值类型的隐式转换（窄化需要显式转换，这里只允许宽化）
        if (isNumeric(to) && isNumeric(from)) {
            return getNumericRank(to) >= getNumericRank(from);
        }

        // TODO: 支持子类型检查（需要类继承信息）
        return false;
    }

    /**
     * 获取数值类型的等级（用于比较宽化/窄化）
     */
    private static int getNumericRank(String type) {
        return switch (type) {
            case "byte" -> 1;
            case "short" -> 2;
            case "char" -> 2;
            case "int" -> 3;
            case "long" -> 4;
            case "float" -> 5;
            case "double" -> 6;
            default -> 0;
        };
    }

    // ============ 描述符生成 ============

    /**
     * 将类型转换为 JVM 描述符
     */
    public static String toDescriptor(String type) {
        if (type == null) return "V";
        type = normalize(type);

        // 处理数组
        if (type.endsWith("[]")) {
            return "[" + toDescriptor(type.substring(0, type.length() - 2));
        }

        return switch (type) {
            case "void" -> "V";
            case "bool" -> "Z";   // Partic bool -> JVM boolean
            case "byte" -> "B";
            case "char" -> "C";
            case "short" -> "S";
            case "int" -> "I";
            case "long" -> "J";
            case "float" -> "F";
            case "double" -> "D";
            default -> "L" + type.replace('.', '/') + ";";
        };
    }

    /**
     * 将类型转换为内部名（用于 ASM）
     */
    public static String toInternalName(String type) {
        if (type == null) return null;
        return normalize(type).replace('.', '/');
    }
}

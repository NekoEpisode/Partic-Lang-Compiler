package lang.partic.compiler.utils;

public class TypeUtils {
    private static final String[] primitives = {"int", "float", "double", "char", "bool", "str", "void",
    "byte", "long", "short", "true", "false"};

    /**
     * 检查传入的类型是否为基元类型
     * <p>自动去除"["和"]"来检查</p>
     * @param type 类型
     * @return 是否为基元类型
     */
    public static boolean isPrimitive(String type) {
        type = type.replace("[", "").replace("]", "");
        boolean isPrimitive = false;
        for (String primitive : primitives) {
            if (primitive.equals(type)) {
                isPrimitive = true;
                break;
            }
        }
        return isPrimitive;
    }
}

package lang.partic.compiler.utils;

public class ClassNameUtils {
    public static boolean isClassFullName(String name) {
        return name.contains(".");
    }
    public static String replaceAllDotToSlash(String name) {
        return name.replace(".", "/");
    }
}

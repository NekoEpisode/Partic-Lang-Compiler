package io.github.nekosora.utils;

import io.github.nekosora.logger.ParticLogger;

import java.io.File;
import java.nio.file.Path;
import java.util.Set;

public class ClassNameUtils {
    /**
     * 从文件路径提取并合法化类名
     */
    public static String getClassNameFromFilePath(String filePath) {
        File file = new File(filePath);
        String fileName = file.getName();

        // 移除 .class 扩展名
        if (fileName.endsWith(".class")) {
            fileName = fileName.substring(0, fileName.length() - 6);
        }

        return sanitizeClassName(fileName);
    }

    public static String getClassNameFromFilePath(Path filePath) {
        return getClassNameFromFilePath(filePath.toString());
    }

    private static String sanitizeClassName(String rawName) {
        if (rawName == null || rawName.isEmpty()) {
            return "GeneratedClass"; // 提供默认类名
        }

        // 如果已经是合法的类名，直接返回
        if (isValidClassName(rawName)) {
            return rawName;
        }

        StringBuilder legalName = new StringBuilder();
        boolean firstChar = true;

        for (char c : rawName.toCharArray()) {
            if (firstChar) {
                if (Character.isJavaIdentifierStart(c)) {
                    legalName.append(c);
                    firstChar = false;
                } else {
                    // 首字符不合法，用下划线代替
                    legalName.append('_');
                    firstChar = false;
                }
            } else {
                if (Character.isJavaIdentifierPart(c)) {
                    legalName.append(c);
                } else {
                    legalName.append('_');
                }
            }
        }

        String result = legalName.toString();

        // 确保不以数字开头
        if (!result.isEmpty() && Character.isDigit(result.charAt(0))) {
            result = "_" + result;
        }

        // 检查是否是Java关键字
        if (isJavaKeyword(result)) {
            result = result + "_";
        }

        return result.isEmpty() ? "GeneratedClass" : result;
    }

    private static boolean isValidClassName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }

        // 检查首字符
        if (!Character.isJavaIdentifierStart(name.charAt(0))) {
            return false;
        }

        // 检查剩余字符
        for (int i = 1; i < name.length(); i++) {
            if (!Character.isJavaIdentifierPart(name.charAt(i))) {
                return false;
            }
        }

        // 检查是否是关键字
        return !isJavaKeyword(name);
    }

    private static boolean isJavaKeyword(String name) {
        final Set<String> keywords = Set.of(
                "abstract", "assert", "boolean", "break", "byte", "case", "catch",
                "char", "class", "const", "continue", "default", "do", "double",
                "else", "enum", "extends", "final", "finally", "float", "for",
                "goto", "if", "implements", "import", "instanceof", "int", "interface",
                "long", "native", "new", "package", "private", "protected", "public",
                "return", "short", "static", "strictfp", "super", "switch",
                "synchronized", "this", "throw", "throws", "transient", "try",
                "void", "volatile", "while"
        );
        return keywords.contains(name);
    }
}

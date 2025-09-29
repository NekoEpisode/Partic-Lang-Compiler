package io.github.nekosora.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParticLogger {
    private static final Logger logger = LoggerFactory.getLogger(ParticLogger.class);

    public static void mainInfo(String message) {
        logger.info(message);
    }

    public static void lexerDebug(String message) {
        logger.debug("[LEXER] {}", message);
    }

    public static void parserDebug(String message) {
        logger.debug("[PARSER] {}", message);
    }

    public static void semanticInfo(String message) {
        logger.info("[SEMANTIC] {}", message);
    }

    public static void semanticWarning(String message) {
        logger.warn("[SEMANTIC] {}", message);
    }

    public static void semanticError(String message) {
        logger.error("[SEMANTIC] {}", message);
    }

    public static void semanticDebug(String message) {
        logger.debug("[SEMANTIC] {}", message);
    }

    public static void codeGenInfo(String message) {
        logger.info("[CODEGEN] {}", message);
    }

    public static void codeGenWarning(String message) {
        logger.warn("[CODEGEN] {}", message);
    }

    public static void codeGenDebug(String message) {
        logger.debug("[CODEGEN] {}", message);
    }

    public static void codeGenError(String message) {
        logger.error("[CODEGEN] {}", message);
    }

    public static void visitorDebug(String message) {
        logger.debug("[VISITOR] {}", message);
    }

    // 符号表相关
    public static void symbolTableDebug(String message) {
        logger.debug("[SYMBOL] {}", message);
    }

    // 类型检查相关
    public static void typeCheckDebug(String message) {
        logger.debug("[TYPECHECK] {}", message);
    }

    public static void typeCheckError(String message) {
        logger.error("[TYPECHECK] {}", message);
    }
}

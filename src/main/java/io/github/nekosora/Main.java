package io.github.nekosora;

import io.github.nekosora.antlr.ParticLexer;
import io.github.nekosora.antlr.ParticParser;
import io.github.nekosora.utils.ClassNameUtils;
import io.github.nekosora.visitor.ParticCompilerVisitor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));

        if (args.length == 0) {
            System.out.println("用法: java -jar partic-compiler.jar <source-file.partic> [output-dir]");
            System.out.println("示例: java -jar partic-compiler.jar hello.partic ./out");
            System.out.println();
            System.out.println("Usage: java -jar partic-compiler.jar <source-file.partic> [output-dir]");
            System.out.println("Example: java -jar partic-compiler.jar hello.partic ./out");
            return;
        }

        // 解析参数
        String sourceFile = args[0];
        String outputDir = args.length > 1 ? args[1] : "./generated";
        String className = ClassNameUtils.getClassNameFromFilePath(sourceFile);

        try {
            // 1. 读取源文件
            String sourceCode = readFile(sourceFile);

            // 2. 编译
            ParticLexer lexer = new ParticLexer(CharStreams.fromString(sourceCode));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ParticParser parser = new ParticParser(tokens);

            ParticParser.ProgramContext tree = parser.program();
            Path path = Path.of(outputDir, className + ".class");
            ParticCompilerVisitor compiler = new ParticCompilerVisitor(path);
            compiler.visitProgram(tree);

            System.out.println("编译成功！输出: " + path);
            System.out.println("Compile success! Output: " + path);

            // 3. 可选：立即执行编译结果
            if (shouldExecute(args)) {
                executeCompiledClass(outputDir, className);
            }

        } catch (Exception e) {
            System.err.println("编译失败 | Compile Failed: " + e.getMessage());
            log.error("编译失败", e);
        }
    }

    private static String readFile(String filePath) throws IOException {
        return Files.readString(Paths.get(filePath));
    }

    private static boolean shouldExecute(String[] args) {
        // 如果有 --run 参数则执行
        for (String arg : args) {
            if ("--run".equals(arg)) {
                return true;
            }
        }
        return false;
    }

    private static void executeCompiledClass(String classDir, String className) {
        try (URLClassLoader classLoader = new URLClassLoader(
                new URL[]{new URI("file:///" + Paths.get(classDir).toAbsolutePath()).toURL()},
                Main.class.getClassLoader()
        )) {
            Class<?> compiledClass = classLoader.loadClass(className);
            java.lang.reflect.Method mainMethod = compiledClass.getMethod("main", String[].class);
            System.out.println("\n执行输出 | Run output:");
            mainMethod.invoke(null, (Object) new String[]{});
        } catch (Exception e) {
            System.err.println("执行失败 | Run code failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
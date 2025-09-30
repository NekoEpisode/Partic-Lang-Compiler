package io.github.nekosora;

import io.github.nekosora.antlr.ParticLexer;
import io.github.nekosora.antlr.ParticParser;
import io.github.nekosora.visitor.ParticCompilerVisitor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

public class DebugMain {
    public static void main(String[] args) {
        String sourceCode =
                """
                """;

        ParticLexer lexer = new ParticLexer(CharStreams.fromString(sourceCode));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        ParticParser parser = new ParticParser(tokens);
        ParticParser.ProgramContext tree = parser.program();

        ParticCompilerVisitor compiler = new ParticCompilerVisitor(Path.of("D:/IntelliJ IDEA/ParticCompilerI/GeneratedCode/Test.class"));
        compiler.visitProgram(tree);

        System.out.println("\n----- 开始运行代码 -----");
        String classDir = "D:/IntelliJ%20IDEA/ParticCompilerI/GeneratedCode/";
        try (URLClassLoader classLoader = new URLClassLoader(
                new URL[]{new URI("file:///" + classDir).toURL()},
                Main.class.getClassLoader()
        )) {
            Class<?> testClass = classLoader.loadClass("Test");
            java.lang.reflect.Method mainMethod = testClass.getMethod("main", String[].class);
            mainMethod.invoke(null, (Object) new String[]{}); // 调用main方法
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

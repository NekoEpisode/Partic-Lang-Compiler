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
        String sourceCode = """
                import java.lang.StringBuilder as aB;
                import java.lang.System;
                
                def double buildMessage() {
                    System.out.println("awa");
                    int i = 32;
                    return 2 + 1.5f + i * 5 + 2 / 5 + (5 - 3 * 3); // ??
                }
                
                def void main(str[] args) {
                    int i = 10;
                    System.out.println(buildMessage());
                    System.out.println(i);
                }
                """;

        ParticLexer lexer = new ParticLexer(CharStreams.fromString(sourceCode));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ParticParser parser = new ParticParser(tokens);

        ParticParser.ProgramContext tree = parser.program();
        ParticCompilerVisitor compiler = new ParticCompilerVisitor(Path.of("D:/IntelliJ IDEA/ParticCompilerI/GeneratedCode/Test.class"));
        compiler.visitProgram(tree);

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

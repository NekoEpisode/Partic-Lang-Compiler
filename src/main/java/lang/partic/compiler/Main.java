package lang.partic.compiler;

import lang.partic.compiler.antlr.ParticLexer;
import lang.partic.compiler.antlr.ParticParser;
import lang.partic.compiler.compile.ParticCompiler;
import lang.partic.compiler.ir.ParticProgram;
import lang.partic.compiler.visitor.FrontendVisitor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    void main() {
        String source =
                """
                import java.lang.System;
                import java.nio.file.Path;
                
                class Main {
                    static void main() {
                        System.out.println(test(3));
                    }
                    
                    priv double test(int x) -> x * 5;
                }
                """;

        log.debug("源码: {}", source);

        CharStream charStream = CharStreams.fromString(source);
        ParticLexer lexer = new ParticLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ParticParser parser = new ParticParser(tokens);

        ParticParser.ProgramContext tree = parser.program();

        FrontendVisitor visitor = new FrontendVisitor(Path.of("./debug"));
        ParticProgram program = visitor.visitProgram(tree);

        ParticCompiler compiler = new ParticCompiler(program);
        List<byte[]> bytecodes = compiler.compile();

        File dir = new File("./debug");
        if (!dir.exists()) {
            dir.mkdir();
        } else {
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                file.delete();
            }
        }

        for (int i = 0; i < bytecodes.size(); i++) {
            byte[] bytecode = bytecodes.get(i);
            System.out.println("得到的class字节码: " + Arrays.toString(bytecode));
            File file = new File("./debug/bytecode_" + i + ".class");
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }

                Files.write(file.toPath(), bytecode);
            } catch (IOException e) {
                log.error("保存文件时出现错误", e);
            }
        }
    }
}
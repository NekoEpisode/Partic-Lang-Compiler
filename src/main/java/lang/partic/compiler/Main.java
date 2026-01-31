package lang.partic.compiler;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lang.partic.compiler.antlr.ParticLexer;
import lang.partic.compiler.antlr.ParticParser;
import lang.partic.compiler.ir.ParticIRGenerator;
import lang.partic.compiler.ir.ParticProgram;
import lang.partic.compiler.visitor.FrontendVisitor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    void main() {
        String source =
                """
                class Main {
                    int test(int x) -> x + 5;
                }
                
                priv final class Test {
                    int test(int y) -> y - 5;
                }
                """;

        CharStream charStream = CharStreams.fromString(source);
        ParticLexer lexer = new ParticLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ParticParser parser = new ParticParser(tokens);

        ParticParser.ProgramContext tree = parser.program();

        // Visitor构建中间表示
        FrontendVisitor visitor = new FrontendVisitor(Path.of("./debug"));
        ParticProgram program = visitor.visitProgram(tree);

        // IRGenerator生成JSON
        ParticIRGenerator irGenerator = new ParticIRGenerator();
        JsonObject ir = irGenerator.generate(program);

        String json = new GsonBuilder().setPrettyPrinting().create().toJson(ir);
        log.debug("得到的IR: {}", json);
        log.debug("not pretty: {}", ir);
    }
}
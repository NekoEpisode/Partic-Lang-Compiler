package lang.partic.compiler;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lang.partic.compiler.antlr.ParticLexer;
import lang.partic.compiler.antlr.ParticParser;
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
                import java.lang.Override as Ov;
                import lang.partic.NotNull;
                
                @lang.partic.Test
                class Main extends lang.partic.Test implements lang.partic.I1, lang.partic.I2 {
                    @Ov
                    static void main(@NotNull str[] args, int x) {}
                    Main() {}
                }
                """;

        CharStream charStream = CharStreams.fromString(source);
        ParticLexer lexer = new ParticLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ParticParser parser = new ParticParser(tokens);

        ParticParser.ProgramContext tree = parser.program();

        FrontendVisitor visitor = new FrontendVisitor(Path.of("./debug"));
        JsonObject ir = visitor.visitProgram(tree);

        String json = new GsonBuilder().setPrettyPrinting().create().toJson(ir);
        log.debug("得到的IR: {}", json);
        log.debug("not pretty: {}", ir);
    }
}
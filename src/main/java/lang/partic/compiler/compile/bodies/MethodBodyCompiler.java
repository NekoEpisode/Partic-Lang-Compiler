package lang.partic.compiler.compile.bodies;

import lang.partic.compiler.compile.statements.StatementCompiler;
import lang.partic.compiler.context.CompileContext;
import lang.partic.compiler.ir.ParticMethodBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MethodBodyCompiler {
    private static final Logger log = LoggerFactory.getLogger(MethodBodyCompiler.class);
    private final ParticMethodBody methodBody;
    private final CompileContext context;

    public MethodBodyCompiler(ParticMethodBody methodBody, CompileContext context) {
        this.methodBody = methodBody;
        this.context = context;
    }

    public void compile() {
        log.debug("编译 {} 的方法 {} 的方法体", context.getCurrentClass().getName(), context.getCurrentMethod().getName());

        for (ParticMethodBody.Statement statement : methodBody.getStatements()) {
            new StatementCompiler(statement, methodBody, context).compile();
        }
    }
}

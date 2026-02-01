package lang.partic.compiler.compile;

import lang.partic.compiler.compile.classes.ClassCompiler;
import lang.partic.compiler.ir.ParticClass;
import lang.partic.compiler.ir.ParticProgram;

import java.util.HashMap;
import java.util.Map;

public class ParticCompiler {
    private final ParticProgram program;

    public ParticCompiler(ParticProgram program) {
        this.program = program;
    }

    public CompileResult compile() {
        Map<String, byte[]> bytecodes = new HashMap<>();
        for (ParticClass particClass : program.getClasses()) {
            bytecodes.put(particClass.getName(), new ClassCompiler(particClass).compile());
        }
        return new CompileResult(bytecodes);
    }
}

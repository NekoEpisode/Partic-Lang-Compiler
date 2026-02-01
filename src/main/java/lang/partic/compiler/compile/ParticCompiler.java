package lang.partic.compiler.compile;

import lang.partic.compiler.compile.classes.ClassCompiler;
import lang.partic.compiler.ir.ParticClass;
import lang.partic.compiler.ir.ParticProgram;

import java.util.ArrayList;
import java.util.List;

public class ParticCompiler {
    private final ParticProgram program;

    public ParticCompiler(ParticProgram program) {
        this.program = program;
    }

    public List<byte[]> compile() {
        List<byte[]> bytecodes = new ArrayList<>();
        for (ParticClass particClass : program.getClasses()) {
            bytecodes.add(new ClassCompiler(particClass).compile());
        }
        return bytecodes;
    }
}

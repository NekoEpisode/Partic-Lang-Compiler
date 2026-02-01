package lang.partic.compiler.compile.classes;

import lang.partic.compiler.compile.methods.MethodCompiler;
import lang.partic.compiler.context.CompileContext;
import lang.partic.compiler.ir.ParticClass;
import lang.partic.compiler.ir.ParticMethod;
import lang.partic.compiler.utils.ClassNameUtils;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassCompiler {
    private static final Logger log = LoggerFactory.getLogger(ClassCompiler.class);
    private final ParticClass particClass;

    private final ClassWriter classWriter;

    public ClassCompiler(ParticClass particClass) {
        this.particClass = particClass;
        this.classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
    }

    public byte[] compile() {
        CompileContext context = new CompileContext();
        context.setCw(classWriter);
        context.setCurrentClass(particClass);

        log.debug("开始编译类: {}", particClass.getName());
        int access = Opcodes.ACC_PUBLIC;
        switch (particClass.getModifiers().getAccess()) {
            case "priv" -> access = Opcodes.ACC_PRIVATE;
            case "prot" -> access = Opcodes.ACC_PROTECTED;
            case "pack" -> access = 0;
        }

        for (String modifier : particClass.getModifiers().getOthers()) {
            switch (modifier) {
                case "static" -> access |= Opcodes.ACC_STATIC;
                case "final" -> access |= Opcodes.ACC_FINAL;
            }
        }
        String[] impls = new String[particClass.getImplementsList().size()];
        for (int i = 0; i < particClass.getImplementsList().size(); i++) {
            impls[i] = ClassNameUtils.replaceAllDotToSlash(particClass.getImplementsList().get(i));
        }
        this.classWriter.visit(
                Opcodes.V25,
                access,
                particClass.getName(),
                null,
                ClassNameUtils.replaceAllDotToSlash(particClass.getExtendsClass()),
                impls
        );

        for (ParticMethod method : particClass.getMethods().values()) {
            new MethodCompiler(method, context).compile();
        }

        this.classWriter.visitEnd();
        return this.classWriter.toByteArray();
    }
}

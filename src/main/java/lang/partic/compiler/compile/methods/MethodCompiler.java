package lang.partic.compiler.compile.methods;

import lang.partic.compiler.compile.bodies.MethodBodyCompiler;
import lang.partic.compiler.compile.utils.LocalVarIndexManager;
import lang.partic.compiler.context.CompileContext;
import lang.partic.compiler.ir.ParticMethod;
import lang.partic.compiler.utils.MethodDescriptorUtils;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MethodCompiler {
    private static final Logger log = LoggerFactory.getLogger(MethodCompiler.class);
    private final ParticMethod particMethod;
    private final CompileContext context;

    public MethodCompiler(ParticMethod particMethod, CompileContext context) {
        this.particMethod = particMethod;
        this.context = context;
    }

    public void compile() {
        log.debug("编译 {} 的方法: {}", context.getCurrentClass().getName(), particMethod.getName());
        context.setCurrentMethod(particMethod);
        
        // 初始化局部变量索引管理器
        LocalVarIndexManager indexManager = new LocalVarIndexManager(particMethod);
        context.setIndexManager(indexManager);
        
        // 为方法体中的局部变量分配索引
        particMethod.getBody().getLocals().forEach((name, local) -> indexManager.allocateIndex(name, local.getType()));

        int access = Opcodes.ACC_PUBLIC;
        switch (particMethod.getModifiers().getAccess()) {
            case "priv" -> access = Opcodes.ACC_PRIVATE;
            case "prot" -> access = Opcodes.ACC_PROTECTED;
            case "pack" -> access = 0;
        }
        for (String other : particMethod.getModifiers().getOthers()) {
            switch (other) {
                case "final" -> access |= Opcodes.ACC_FINAL;
                case "static" -> access |= Opcodes.ACC_STATIC;
            }
        }
        MethodVisitor mv = context.getCw().visitMethod(access, particMethod.getName(), MethodDescriptorUtils.getMethodDescriptor(particMethod), null, null);
        context.setMv(mv);
        mv.visitCode();

        new MethodBodyCompiler(particMethod.getBody(), context).compile();

        mv.visitMaxs(-1, -1);
        mv.visitEnd();
    }
}

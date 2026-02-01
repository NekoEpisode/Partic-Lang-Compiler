package lang.partic.compiler.context;

import lang.partic.compiler.compile.utils.LocalVarIndexManager;
import lang.partic.compiler.ir.ParticClass;
import lang.partic.compiler.ir.ParticMethod;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class CompileContext {
    private ParticClass currentClass;
    private ParticMethod currentMethod;
    private ClassWriter cw;
    private MethodVisitor mv;
    private LocalVarIndexManager indexManager;

    public CompileContext() {}

    public void setCw(ClassWriter cw) {
        this.cw = cw;
    }

    public ClassWriter getCw() {
        return cw;
    }

    public void setCurrentClass(ParticClass currentClass) {
        this.currentClass = currentClass;
    }

    public ParticClass getCurrentClass() {
        return currentClass;
    }

    public void setMv(MethodVisitor mv) {
        this.mv = mv;
    }

    public MethodVisitor getMv() {
        return mv;
    }

    public void setCurrentMethod(ParticMethod currentMethod) {
        this.currentMethod = currentMethod;
    }

    public ParticMethod getCurrentMethod() {
        return currentMethod;
    }

    public void setIndexManager(LocalVarIndexManager indexManager) {
        this.indexManager = indexManager;
    }

    public LocalVarIndexManager getIndexManager() {
        return indexManager;
    }
}

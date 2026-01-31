package lang.partic.compiler.ir;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示整个程序（编译单元）
 */
public class ParticProgram {
    private String sourceFile;
    private List<ParticClass> classes;

    public ParticProgram(String sourceFile) {
        this.sourceFile = sourceFile;
        this.classes = new ArrayList<>();
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public List<ParticClass> getClasses() {
        return classes;
    }

    public void addClass(ParticClass particClass) {
        this.classes.add(particClass);
    }
}

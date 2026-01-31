package lang.partic.compiler.ir;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示方法参数
 */
public class ParticParameter {
    private String type; // 完整类型名
    private String name;
    private List<ParticAnnotation> annotations;

    public ParticParameter(String type, String name) {
        this.type = type;
        this.name = name;
        this.annotations = new ArrayList<>();
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public List<ParticAnnotation> getAnnotations() {
        return annotations;
    }

    public void addAnnotation(ParticAnnotation annotation) {
        this.annotations.add(annotation);
    }
}

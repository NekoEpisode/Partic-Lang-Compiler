package lang.partic.compiler.ir;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示字段声明
 */
public class ParticField {
    private ParticModifiers modifiers;
    private String type; // 完整类型名
    private String name;
    private Object value; // 初始值（可选）
    private List<ParticAnnotation> annotations; // 注解列表

    public ParticField(String type, String name) {
        this.modifiers = new ParticModifiers();
        this.type = type;
        this.name = name;
        this.value = null;
        this.annotations = new ArrayList<>();
    }

    public ParticModifiers getModifiers() {
        return modifiers;
    }

    public void setModifiers(ParticModifiers modifiers) {
        this.modifiers = modifiers;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    
    public List<ParticAnnotation> getAnnotations() {
        return annotations;
    }
    
    public void addAnnotation(ParticAnnotation annotation) {
        this.annotations.add(annotation);
    }
}

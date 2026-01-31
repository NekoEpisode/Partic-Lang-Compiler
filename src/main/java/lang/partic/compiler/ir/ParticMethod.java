package lang.partic.compiler.ir;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示方法声明
 */
public class ParticMethod {
    private String name;
    private ParticModifiers modifiers;
    private List<ParticAnnotation> annotations;
    private String returnType; // 完整类型名
    private List<ParticParameter> parameters;
    private ParticMethodBody body;

    public ParticMethod(String name, String returnType) {
        this.name = name;
        this.returnType = returnType;
        this.modifiers = new ParticModifiers();
        this.annotations = new ArrayList<>();
        this.parameters = new ArrayList<>();
        this.body = null;
    }

    public String getName() {
        return name;
    }

    public ParticModifiers getModifiers() {
        return modifiers;
    }

    public void setModifiers(ParticModifiers modifiers) {
        this.modifiers = modifiers;
    }

    public List<ParticAnnotation> getAnnotations() {
        return annotations;
    }

    public void addAnnotation(ParticAnnotation annotation) {
        this.annotations.add(annotation);
    }

    public String getReturnType() {
        return returnType;
    }

    public List<ParticParameter> getParameters() {
        return parameters;
    }

    public void addParameter(ParticParameter parameter) {
        this.parameters.add(parameter);
    }

    public ParticMethodBody getBody() {
        return body;
    }

    public void setBody(ParticMethodBody body) {
        this.body = body;
    }
}

package lang.partic.compiler.ir;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示构造函数声明
 */
public class ParticConstructor {
    private ParticModifiers modifiers;
    private List<ParticAnnotation> annotations;
    private List<ParticParameter> parameters;
    private ParticMethodBody body;

    public ParticConstructor() {
        this.modifiers = new ParticModifiers();
        this.annotations = new ArrayList<>();
        this.parameters = new ArrayList<>();
        this.body = null;
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

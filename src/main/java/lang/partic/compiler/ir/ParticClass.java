package lang.partic.compiler.ir;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表示类声明
 */
public class ParticClass {
    private String name;
    private ParticModifiers modifiers;
    private List<String> implementsList; // 实现的接口（完整类名）
    private List<ParticAnnotation> annotations;
    private List<String> typeParameters; // 泛型参数（目前为空）
    private String extendsClass; // 父类（完整类名）
    private List<ParticField> fields;
    private Map<String, ParticMethod> methods; // key是方法名
    private List<ParticConstructor> constructors;

    public ParticClass(String name) {
        this.name = name;
        this.modifiers = new ParticModifiers();
        this.implementsList = new ArrayList<>();
        this.annotations = new ArrayList<>();
        this.typeParameters = new ArrayList<>();
        this.extendsClass = "java.lang.Object"; // 默认继承Object
        this.fields = new ArrayList<>();
        this.methods = new HashMap<>();
        this.constructors = new ArrayList<>();
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

    public List<String> getImplementsList() {
        return implementsList;
    }

    public void addImplements(String interfaceName) {
        this.implementsList.add(interfaceName);
    }

    public List<ParticAnnotation> getAnnotations() {
        return annotations;
    }

    public void addAnnotation(ParticAnnotation annotation) {
        this.annotations.add(annotation);
    }

    public List<String> getTypeParameters() {
        return typeParameters;
    }

    public String getExtendsClass() {
        return extendsClass;
    }

    public void setExtendsClass(String extendsClass) {
        this.extendsClass = extendsClass;
    }

    public List<ParticField> getFields() {
        return fields;
    }

    public void addField(ParticField field) {
        this.fields.add(field);
    }

    public Map<String, ParticMethod> getMethods() {
        return methods;
    }

    public void addMethod(ParticMethod method) {
        this.methods.put(method.getName(), method);
    }

    public List<ParticConstructor> getConstructors() {
        return constructors;
    }

    public void addConstructor(ParticConstructor constructor) {
        this.constructors.add(constructor);
    }
}

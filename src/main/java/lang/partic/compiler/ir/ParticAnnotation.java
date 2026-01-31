package lang.partic.compiler.ir;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示注解
 */
public class ParticAnnotation {
    private String annotation; // 完整类名
    private List<AnnotationArg> args;

    public ParticAnnotation(String annotation) {
        this.annotation = annotation;
        this.args = new ArrayList<>();
    }

    public String getAnnotation() {
        return annotation;
    }

    public List<AnnotationArg> getArgs() {
        return args;
    }

    public void addArg(AnnotationArg arg) {
        this.args.add(arg);
    }

    /**
     * 注解参数
     */
    public static class AnnotationArg {
        private String name;
        private Object value;
        private String type;
        private List<ParticAnnotation> annotations;

        public AnnotationArg(String name, Object value, String type) {
            this.name = name;
            this.value = value;
            this.type = type;
            this.annotations = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public Object getValue() {
            return value;
        }

        public String getType() {
            return type;
        }

        public List<ParticAnnotation> getAnnotations() {
            return annotations;
        }

        public void addAnnotation(ParticAnnotation annotation) {
            this.annotations.add(annotation);
        }
    }
}

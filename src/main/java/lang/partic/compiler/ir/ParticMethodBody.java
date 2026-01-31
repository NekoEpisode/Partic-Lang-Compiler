package lang.partic.compiler.ir;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表示方法体（包含locals, temps, statements）
 */
public class ParticMethodBody {
    private Map<String, LocalVar> locals;
    private Map<String, TempVar> temps;
    private List<Statement> statements;
    private int tempCounter; // 用于生成临时变量名

    public ParticMethodBody() {
        this.locals = new HashMap<>();
        this.temps = new HashMap<>();
        this.statements = new ArrayList<>();
        this.tempCounter = 0;
    }

    /**
     * 生成新的临时变量名
     */
    public String generateTempName() {
        return "t" + (tempCounter++);
    }

    /**
     * 添加临时变量
     */
    public String addTemp(TempVar temp) {
        String name = generateTempName();
        temps.put(name, temp);
        return name;
    }

    public void addLocal(String name, String type) {
        locals.put(name, new LocalVar(type, 0));
    }

    public void addLocal(String name, String type, int scopeLevel) {
        locals.put(name, new LocalVar(type, scopeLevel));
    }

    public void addLocal(String name, String type, int scopeLevel, List<String> modifiers) {
        locals.put(name, new LocalVar(type, scopeLevel, modifiers));
    }

    public void addStatement(Statement statement) {
        statements.add(statement);
    }

    public Map<String, LocalVar> getLocals() {
        return locals;
    }

    public Map<String, TempVar> getTemps() {
        return temps;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    /**
     * 局部变量
     */
    public static class LocalVar {
        private String type;
        private int scopeLevel; // 作用域层级，0表示方法级作用域
        private List<String> modifiers; // 修饰符列表（如final）

        public LocalVar(String type, int scopeLevel) {
            this(type, scopeLevel, new ArrayList<>());
        }

        public LocalVar(String type, int scopeLevel, List<String> modifiers) {
            this.type = type;
            this.scopeLevel = scopeLevel;
            this.modifiers = modifiers;
        }

        public String getType() {
            return type;
        }

        public int getScopeLevel() {
            return scopeLevel;
        }

        public List<String> getModifiers() {
            return modifiers;
        }
    }

    /**
     * 临时变量（表示中间计算结果）
     */
    public static class TempVar {
        private String op;
        private Object value; // 用于常量
        private List<String> operands; // 操作数（可以是其他temp或local的名字）
        private Map<String, Object> metadata; // 额外信息（如object, field, method等）

        public TempVar(String op) {
            this.op = op;
            this.operands = new ArrayList<>();
            this.metadata = new HashMap<>();
        }

        public String getOp() {
            return op;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public List<String> getOperands() {
            return operands;
        }

        public void addOperand(String operand) {
            this.operands.add(operand);
        }

        public Map<String, Object> getMetadata() {
            return metadata;
        }

        public void putMetadata(String key, Object value) {
            this.metadata.put(key, value);
        }
    }

    /**
     * 语句基类
     */
    public static abstract class Statement {
        private String op;

        public Statement(String op) {
            this.op = op;
        }

        public String getOp() {
            return op;
        }
    }

    /**
     * 赋值语句
     */
    public static class AssignStatement extends Statement {
        private String target;
        private String value; // temp或local的名字

        public AssignStatement(String target, String value) {
            super("assign");
            this.target = target;
            this.value = value;
        }

        public String getTarget() {
            return target;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 返回语句
     */
    public static class ReturnStatement extends Statement {
        private String value; // temp或local的名字

        public ReturnStatement(String value) {
            super("return");
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 方法调用语句
     */
    public static class CallStatement extends Statement {
        private String object; // temp或local的名字
        private String method;
        private List<String> signature;
        private List<String> args;

        public CallStatement(String op, String object, String method) {
            super(op); // call_virtual, call_static, etc.
            this.object = object;
            this.method = method;
            this.signature = new ArrayList<>();
            this.args = new ArrayList<>();
        }

        public String getObject() {
            return object;
        }

        public String getMethod() {
            return method;
        }

        public List<String> getSignature() {
            return signature;
        }

        public List<String> getArgs() {
            return args;
        }

        public void addArg(String arg) {
            this.args.add(arg);
        }

        public void addSignature(String type) {
            this.signature.add(type);
        }
    }

    /**
     * 作用域语句（进入/退出作用域）
     */
    public static class ScopeStatement extends Statement {
        private int scopeId;
        private boolean isEnter; // true=enter, false=exit

        public ScopeStatement(int scopeId, boolean isEnter) {
            super(isEnter ? "scope_enter" : "scope_exit");
            this.scopeId = scopeId;
            this.isEnter = isEnter;
        }

        public int getScopeId() {
            return scopeId;
        }

        public boolean isEnter() {
            return isEnter;
        }
    }

    // TODO: 添加更多语句类型（IfStatement, WhileStatement等）
}

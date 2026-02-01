package lang.partic.compiler.symbol;

/**
 * 符号基类 - 所有可命名实体的抽象
 * 包括：类、方法、字段、变量、参数
 */
public abstract class Symbol {
    protected final String name;
    protected String type;           // 类型（完整类名，如 java.lang.String）
    protected Scope scope;           // 所属作用域
    protected int modifiers;         // 修饰符（使用 java.lang.reflect.Modifier）

    public Symbol(String name, String type) {
        this.name = name;
        this.type = type;
        this.modifiers = 0;
    }

    public Symbol(String name, String type, int modifiers) {
        this.name = name;
        this.type = type;
        this.modifiers = modifiers;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public int getModifiers() {
        return modifiers;
    }

    public void setModifiers(int modifiers) {
        this.modifiers = modifiers;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{name='" + name + "', type='" + type + "'}";
    }
}

package lang.partic.compiler.symbol;

import java.lang.reflect.Modifier;

/**
 * 字段符号 - 表示类的字段
 */
public class FieldSymbol extends Symbol {
    private ClassSymbol owner;  // 所属的类

    public FieldSymbol(String name, String type) {
        super(name, type);
    }

    public FieldSymbol(String name, String type, int modifiers) {
        super(name, type, modifiers);
    }

    public boolean isStatic() {
        return Modifier.isStatic(modifiers);
    }

    public boolean isFinal() {
        return Modifier.isFinal(modifiers);
    }

    public ClassSymbol getOwner() {
        return owner;
    }

    public void setOwner(ClassSymbol owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "FieldSymbol{name='" + name + "', type='" + type + "', static=" + isStatic() + "}";
    }
}

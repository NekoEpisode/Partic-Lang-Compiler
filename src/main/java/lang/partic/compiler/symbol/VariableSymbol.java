package lang.partic.compiler.symbol;

/**
 * 变量符号 - 表示局部变量或方法参数
 */
public class VariableSymbol extends Symbol {
    private final int index;        // 局部变量表索引（用于字节码生成）
    private final boolean isParameter;  // 是否为参数

    public VariableSymbol(String name, String type, int index) {
        this(name, type, index, false);
    }

    public VariableSymbol(String name, String type, int index, boolean isParameter) {
        super(name, type);
        this.index = index;
        this.isParameter = isParameter;
    }

    public int getIndex() {
        return index;
    }

    public boolean isParameter() {
        return isParameter;
    }

    @Override
    public String toString() {
        return "VariableSymbol{name='" + name + "', type='" + type + "', index=" + index + 
               ", isParameter=" + isParameter + "}";
    }
}

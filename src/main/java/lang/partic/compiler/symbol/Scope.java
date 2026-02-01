package lang.partic.compiler.symbol;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 作用域 - 符号的容器，支持嵌套查找
 */
public class Scope {
    
    public enum ScopeType {
        GLOBAL,     // 全局作用域（存放所有类）
        CLASS,      // 类作用域（存放字段、方法）
        METHOD,     // 方法作用域（存放参数、局部变量）
        BLOCK       // 块作用域（if/for/while 内部的局部变量）
    }

    private final ScopeType type;
    private final Scope parent;
    private final Map<String, Symbol> symbols = new LinkedHashMap<>();  // 保持插入顺序
    private final String name;  // 作用域名称（用于调试）

    public Scope(ScopeType type, Scope parent) {
        this(type, parent, null);
    }

    public Scope(ScopeType type, Scope parent, String name) {
        this.type = type;
        this.parent = parent;
        this.name = name;
    }

    /**
     * 在当前作用域定义符号
     * @return 如果同名符号已存在，返回 false
     */
    public boolean define(Symbol symbol) {
        if (symbols.containsKey(symbol.getName())) {
            return false;  // 重复定义
        }
        symbols.put(symbol.getName(), symbol);
        symbol.setScope(this);
        return true;
    }

    /**
     * 解析符号 - 从当前作用域向上查找
     */
    public Symbol resolve(String name) {
        Symbol s = symbols.get(name);
        if (s != null) {
            return s;
        }
        // 向父作用域查找
        if (parent != null) {
            return parent.resolve(name);
        }
        return null;
    }

    /**
     * 仅在当前作用域查找（不向上查找）
     */
    public Symbol resolveLocal(String name) {
        return symbols.get(name);
    }

    /**
     * 检查当前作用域是否包含指定名称的符号
     */
    public boolean contains(String name) {
        return symbols.containsKey(name);
    }

    public ScopeType getType() {
        return type;
    }

    public Scope getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public Collection<Symbol> getSymbols() {
        return symbols.values();
    }

    public int getSymbolCount() {
        return symbols.size();
    }

    /**
     * 获取指定类型的所有符号
     */
    @SuppressWarnings("unchecked")
    public <T extends Symbol> Collection<T> getSymbolsOfType(Class<T> clazz) {
        return symbols.values().stream()
                .filter(clazz::isInstance)
                .map(s -> (T) s)
                .toList();
    }

    @Override
    public String toString() {
        return "Scope{type=" + type + ", name='" + name + "', symbols=" + symbols.size() + "}";
    }
}

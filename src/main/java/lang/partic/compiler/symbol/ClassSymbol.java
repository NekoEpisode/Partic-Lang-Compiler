package lang.partic.compiler.symbol;

import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 类符号 - 表示类或接口
 * 同时也是一个作用域（包含字段和方法）
 */
public class ClassSymbol extends Symbol {
    private final Scope memberScope;                            // 类成员作用域
    private ClassSymbol superClass;                             // 父类
    private final List<ClassSymbol> interfaces;                 // 实现的接口
    private final Map<String, List<MethodSymbol>> methodMap;    // 方法映射（支持重载）
    private final List<MethodSymbol> constructors;              // 构造函数列表
    private String fullName;                                    // 完整类名（含包名）

    public ClassSymbol(String name, Scope enclosingScope) {
        super(name, name);  // 类的类型就是它自己
        this.memberScope = new Scope(Scope.ScopeType.CLASS, enclosingScope, name);
        this.interfaces = new ArrayList<>();
        this.methodMap = new HashMap<>();
        this.constructors = new ArrayList<>();
        this.fullName = name;  // 默认为简单名，后续可以设置完整名
    }

    // ============ 字段相关 ============

    /**
     * 添加字段
     */
    public void addField(FieldSymbol field) {
        field.setOwner(this);
        memberScope.define(field);
    }

    /**
     * 查找字段（包括继承的字段）
     */
    public FieldSymbol resolveField(String name) {
        Symbol s = memberScope.resolveLocal(name);
        if (s instanceof FieldSymbol) {
            return (FieldSymbol) s;
        }
        // 从父类查找
        if (superClass != null) {
            return superClass.resolveField(name);
        }
        return null;
    }

    /**
     * 获取所有字段（不包括继承的）
     */
    public Collection<FieldSymbol> getFields() {
        return memberScope.getSymbolsOfType(FieldSymbol.class);
    }

    // ============ 方法相关 ============

    /**
     * 添加方法
     */
    public void addMethod(MethodSymbol method) {
        method.setOwner(this);
        methodMap.computeIfAbsent(method.getName(), _ -> new ArrayList<>()).add(method);
        memberScope.define(method);  // 注意：重载方法会覆盖，但 methodMap 会保留所有
    }

    /**
     * 添加构造函数
     */
    public void addConstructor(MethodSymbol constructor) {
        constructor.setOwner(this);
        constructors.add(constructor);
    }

    /**
     * 根据参数类型解析方法（处理重载）
     */
    public MethodSymbol resolveMethod(String name, List<String> argTypes) {
        List<MethodSymbol> overloads = methodMap.get(name);
        if (overloads != null) {
            for (MethodSymbol m : overloads) {
                if (m.matchesParameters(argTypes)) {
                    return m;
                }
            }
        }
        // 从父类查找
        if (superClass != null) {
            return superClass.resolveMethod(name, argTypes);
        }
        return null;
    }

    /**
     * 获取指定名称的所有重载方法
     */
    public List<MethodSymbol> getMethods(String name) {
        return methodMap.getOrDefault(name, Collections.emptyList());
    }

    /**
     * 获取所有方法（不包括继承的）
     */
    public Collection<MethodSymbol> getAllMethods() {
        List<MethodSymbol> all = new ArrayList<>();
        for (List<MethodSymbol> overloads : methodMap.values()) {
            all.addAll(overloads);
        }
        return all;
    }

    /**
     * 根据参数类型解析构造函数
     */
    public MethodSymbol resolveConstructor(List<String> argTypes) {
        for (MethodSymbol c : constructors) {
            if (c.matchesParameters(argTypes)) {
                return c;
            }
        }
        return null;
    }

    public List<MethodSymbol> getConstructors() {
        return constructors;
    }

    // ============ 继承相关 ============

    public void setSuperClass(ClassSymbol superClass) {
        this.superClass = superClass;
    }

    public ClassSymbol getSuperClass() {
        return superClass;
    }

    public void addInterface(ClassSymbol iface) {
        interfaces.add(iface);
    }

    public List<ClassSymbol> getInterfaces() {
        return interfaces;
    }

    /**
     * 检查是否是指定类型的子类型
     */
    public boolean isSubtypeOf(ClassSymbol other) {
        if (this == other) return true;
        if (superClass != null && superClass.isSubtypeOf(other)) return true;
        for (ClassSymbol iface : interfaces) {
            if (iface.isSubtypeOf(other)) return true;
        }
        return false;
    }

    // ============ 其他 ============

    public Scope getMemberScope() {
        return memberScope;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 获取内部名（用于字节码）
     * 例如: java.lang.String -> java/lang/String
     */
    public String getInternalName() {
        return fullName.replace('.', '/');
    }

    public boolean isInterface() {
        return Modifier.isInterface(modifiers);
    }

    public boolean isAbstract() {
        return Modifier.isAbstract(modifiers);
    }

    @Override
    public String toString() {
        return "ClassSymbol{name='" + fullName + "', fields=" + getFields().size() + 
               ", methods=" + methodMap.size() + "}";
    }
}

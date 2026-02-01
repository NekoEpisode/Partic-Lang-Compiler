package lang.partic.compiler.symbol;

import java.util.*;

/**
 * 符号表 - 管理单个源文件的所有符号和作用域
 * 
 * 使用流程：
 * 1. Pass 1（声明收集）：收集所有类、字段、方法的声明
 * 2. Pass 2（语义分析）：处理方法体，进行类型检查
 */
public class SymbolTable {
    private final Scope globalScope;                        // 全局作用域（存放类）
    private Scope currentScope;                             // 当前作用域
    private final Map<String, ClassSymbol> classes;         // 类名 -> 类符号
    private final Deque<Scope> scopeStack;                  // 作用域栈（用于调试）

    public SymbolTable() {
        this.globalScope = new Scope(Scope.ScopeType.GLOBAL, null, "global");
        this.currentScope = globalScope;
        this.classes = new LinkedHashMap<>();  // 保持定义顺序
        this.scopeStack = new ArrayDeque<>();
        this.scopeStack.push(globalScope);
    }

    // ============ 作用域管理 ============

    /**
     * 进入新作用域
     */
    public Scope enterScope(Scope.ScopeType type, String name) {
        Scope newScope = new Scope(type, currentScope, name);
        currentScope = newScope;
        scopeStack.push(newScope);
        return newScope;
    }

    /**
     * 进入已存在的作用域（例如进入类的成员作用域）
     */
    public void enterScope(Scope scope) {
        currentScope = scope;
        scopeStack.push(scope);
    }

    /**
     * 退出当前作用域
     */
    public Scope exitScope() {
        Scope exited = scopeStack.pop();
        currentScope = scopeStack.peek();
        return exited;
    }

    /**
     * 获取当前作用域
     */
    public Scope getCurrentScope() {
        return currentScope;
    }

    /**
     * 获取全局作用域
     */
    public Scope getGlobalScope() {
        return globalScope;
    }

    // ============ 符号定义 ============

    /**
     * 定义类
     */
    public boolean defineClass(ClassSymbol classSymbol) {
        if (classes.containsKey(classSymbol.getName())) {
            return false;  // 类名重复
        }
        classes.put(classSymbol.getName(), classSymbol);
        globalScope.define(classSymbol);
        return true;
    }

    /**
     * 在当前作用域定义符号
     */
    public boolean define(Symbol symbol) {
        return currentScope.define(symbol);
    }

    // ============ 符号解析 ============

    /**
     * 解析类（按名称）
     */
    public ClassSymbol resolveClass(String name) {
        return classes.get(name);
    }

    /**
     * 从当前作用域解析符号
     */
    public Symbol resolve(String name) {
        return currentScope.resolve(name);
    }

    /**
     * 检查当前作用域是否已定义指定名称
     */
    public boolean isDefined(String name) {
        return currentScope.resolveLocal(name) != null;
    }

    /**
     * 获取所有已定义的类
     */
    public Collection<ClassSymbol> getAllClasses() {
        return classes.values();
    }

    // ============ 辅助方法 ============

    /**
     * 查找变量（局部变量、参数、字段）
     * 从当前作用域向上查找
     */
    public Symbol resolveVariable(String name) {
        // 首先在当前作用域链中查找局部变量/参数
        Symbol s = currentScope.resolve(name);
        if (s instanceof VariableSymbol || s instanceof FieldSymbol) {
            return s;
        }
        return null;
    }

    /**
     * 查找方法（在指定类中）
     */
    public MethodSymbol resolveMethod(ClassSymbol clazz, String methodName, List<String> argTypes) {
        if (clazz == null) return null;
        return clazz.resolveMethod(methodName, argTypes);
    }

    /**
     * 查找字段（在指定类中）
     */
    public FieldSymbol resolveField(ClassSymbol clazz, String fieldName) {
        if (clazz == null) return null;
        return clazz.resolveField(fieldName);
    }

    // ============ 调试 ============

    /**
     * 打印符号表内容（用于调试）
     */
    public void dump() {
        System.out.println("=== Symbol Table ===");
        for (ClassSymbol cls : classes.values()) {
            System.out.println("Class: " + cls.getFullName());
            
            System.out.println("  Fields:");
            for (FieldSymbol field : cls.getFields()) {
                System.out.println("    " + field);
            }
            
            System.out.println("  Methods:");
            for (MethodSymbol method : cls.getAllMethods()) {
                System.out.println("    " + method);
            }
            
            System.out.println("  Constructors:");
            for (MethodSymbol ctor : cls.getConstructors()) {
                System.out.println("    " + ctor);
            }
        }
        System.out.println("====================");
    }

    @Override
    public String toString() {
        return "SymbolTable{classes=" + classes.keySet() + ", currentScope=" + currentScope + "}";
    }
}

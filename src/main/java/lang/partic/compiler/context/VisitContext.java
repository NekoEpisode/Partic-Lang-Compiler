package lang.partic.compiler.context;

import lang.partic.compiler.manager.ImportManager;
import lang.partic.compiler.symbol.*;
import lang.partic.compiler.utils.TypeUtils;

import java.util.List;

/**
 * 访问上下文 - 在 AST 遍历过程中共享的状态
 */
public class VisitContext {
    private final ImportManager importManager;
    private final SymbolTable symbolTable;
    private final ExternalClassLoader externalClassLoader;
    
    // 当前上下文
    private ClassSymbol currentClass;
    private MethodSymbol currentMethod;

    public VisitContext() {
        this.importManager = new ImportManager();
        this.symbolTable = new SymbolTable();
        this.externalClassLoader = new ExternalClassLoader(symbolTable);
    }

    public VisitContext(ImportManager importManager, SymbolTable symbolTable) {
        this.importManager = importManager;
        this.symbolTable = symbolTable;
        this.externalClassLoader = new ExternalClassLoader(symbolTable);
    }

    // ============ Import 管理 ============

    public ImportManager getImportManager() {
        return importManager;
    }

    // ============ 符号表 ============

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    // ============ 外部类加载 ============

    public ExternalClassLoader getExternalClassLoader() {
        return externalClassLoader;
    }

    // ============ 当前上下文 ============

    public ClassSymbol getCurrentClass() {
        return currentClass;
    }

    public void setCurrentClass(ClassSymbol currentClass) {
        this.currentClass = currentClass;
    }

    public MethodSymbol getCurrentMethod() {
        return currentMethod;
    }

    public void setCurrentMethod(MethodSymbol currentMethod) {
        this.currentMethod = currentMethod;
    }

    // ============ 类型解析 ============

    /**
     * 解析类型名为完整类名
     */
    public String resolveType(String typeName) {
        // 处理数组类型
        String arrayDim = "";
        String baseType = typeName;
        while (baseType.endsWith("[]")) {
            arrayDim += "[]";
            baseType = baseType.substring(0, baseType.length() - 2);
        }

        // 先标准化基础类型（将 str 转换为 java.lang.String 等）
        baseType = TypeUtils.normalize(baseType);

        // 尝试从 ImportManager 解析
        String resolved = importManager.findFullName(baseType);
        if (resolved != null) {
            return resolved + arrayDim;
        }

        // 尝试从当前文件的类中查找
        ClassSymbol localClass = symbolTable.resolveClass(baseType);
        if (localClass != null) {
            return localClass.getFullName() + arrayDim;
        }

        // 如果 normalize 后仍然是基元类型，直接返回
        if (TypeUtils.isPrimitive(baseType)) {
            return baseType + arrayDim;
        }

        // 返回标准化后的类型名（可能是完整类名）
        return baseType + arrayDim;
    }

    /**
     * 解析类（本地 + 外部）
     * @param className 类名（简单名或完整名）
     * @return ClassSymbol，找不到返回 null
     */
    public ClassSymbol resolveClass(String className) {
        // 1. 先尝试解析完整类名
        String fullName = resolveType(className);
        
        // 2. 查找本地类
        ClassSymbol local = symbolTable.resolveClass(className);
        if (local != null) {
            return local;
        }
        
        // 3. 加载外部类
        return externalClassLoader.loadClass(fullName);
    }

    // ============ 符号解析 ============

    /**
     * 解析变量（局部变量、参数、字段）
     */
    public Symbol resolveVariable(String name) {
        // 首先从符号表当前作用域查找
        Symbol s = symbolTable.resolve(name);
        if (s != null) {
            return s;
        }

        // 如果在方法中，尝试查找当前类的字段
        if (currentClass != null) {
            FieldSymbol field = currentClass.resolveField(name);
            if (field != null) {
                return field;
            }
        }

        return null;
    }

    /**
     * 解析方法调用（支持外部类）
     */
    public MethodSymbol resolveMethod(String targetClassName, String methodName, List<String> argTypes) {
        ClassSymbol targetClass = null;
        
        if (targetClassName != null) {
            targetClass = resolveClass(targetClassName);
        } else {
            targetClass = currentClass;
        }
        
        if (targetClass == null) {
            return null;
        }
        
        return targetClass.resolveMethod(methodName, argTypes);
    }

    /**
     * 解析字段访问（支持外部类）
     */
    public FieldSymbol resolveField(String targetClassName, String fieldName) {
        ClassSymbol targetClass = null;
        
        if (targetClassName != null) {
            targetClass = resolveClass(targetClassName);
        } else {
            targetClass = currentClass;
        }
        
        if (targetClass == null) {
            return null;
        }
        
        return targetClass.resolveField(fieldName);
    }
}

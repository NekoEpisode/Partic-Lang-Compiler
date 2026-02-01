package lang.partic.compiler.symbol;

import lang.partic.compiler.utils.TypeUtils;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 方法符号 - 表示类的方法或构造函数
 * 同时也是一个作用域（包含参数和局部变量）
 */
public class MethodSymbol extends Symbol {
    private ClassSymbol owner;                          // 所属的类
    private final List<VariableSymbol> parameters;      // 参数列表
    private final List<String> parameterTypes;          // 参数类型列表（用于快速匹配）
    private Scope bodyScope;                            // 方法体作用域
    private final boolean isConstructor;                // 是否为构造函数
    private int nextLocalIndex;                         // 下一个局部变量索引
    private boolean isVarArgs;                           // 是否为可变参数方法

    public MethodSymbol(String name, String returnType, boolean isConstructor) {
        super(name, returnType);
        this.parameters = new ArrayList<>();
        this.parameterTypes = new ArrayList<>();
        this.isConstructor = isConstructor;
        this.isVarArgs = false;
        // 非静态方法的 index 0 是 this
        this.nextLocalIndex = 1;
    }

    /**
     * 创建方法体作用域
     */
    public void createBodyScope(Scope enclosingScope) {
        this.bodyScope = new Scope(Scope.ScopeType.METHOD, enclosingScope, name);
        // 将参数加入方法体作用域
        for (VariableSymbol param : parameters) {
            bodyScope.define(param);
        }
    }

    /**
     * 添加参数
     */
    public void addParameter(String name, String type) {
        int index = isStatic() ? parameters.size() : parameters.size() + 1;  // 非静态方法 index 0 是 this
        VariableSymbol param = new VariableSymbol(name, type, index, true);
        parameters.add(param);
        parameterTypes.add(type);
        nextLocalIndex = index + 1;
    }

    /**
     * 分配下一个局部变量索引
     */
    public int allocateLocalIndex() {
        return nextLocalIndex++;
    }

    /**
     * 检查参数是否匹配（用于重载解析）
     * 支持可变参数方法
     */
    public boolean matchesParameters(List<String> argTypes) {
        int fixedParamCount = isVarArgs ? parameterTypes.size() - 1 : parameterTypes.size();
        
        // 参数数量必须至少等于固定参数数量
        if (argTypes.size() < fixedParamCount) {
            return false;
        }
        
        // 如果不是可变参数方法，参数数量必须完全匹配
        if (!isVarArgs && argTypes.size() != parameterTypes.size()) {
            return false;
        }
        
        // 检查固定参数
        for (int i = 0; i < fixedParamCount; i++) {
            if (!isTypeCompatible(argTypes.get(i), parameterTypes.get(i))) {
                return false;
            }
        }
        
        // 如果是可变参数方法，检查可变参数部分
        if (isVarArgs) {
            String varArgElementType = getVarArgElementType();
            // 检查所有剩余参数是否与可变参数元素类型兼容
            for (int i = fixedParamCount; i < argTypes.size(); i++) {
                if (!isTypeCompatible(argTypes.get(i), varArgElementType)) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * 获取可变参数的元素类型
     * 例如：String[] -> String
     */
    private String getVarArgElementType() {
        if (!isVarArgs || parameterTypes.isEmpty()) {
            return null;
        }
        String lastParamType = parameterTypes.get(parameterTypes.size() - 1);
        if (lastParamType.endsWith("[]")) {
            return lastParamType.substring(0, lastParamType.length() - 2);
        }
        return lastParamType;
    }

    /**
     * 类型兼容性检查
     * 检查实际参数类型（from）是否可以赋值给方法参数类型（to）
     * 支持子类型转换、自动装箱等
     */
    private boolean isTypeCompatible(String from, String to) {
        if (from == null || to == null) return false;
        if (from.equals(to)) return true;
        
        // 首先使用 TypeUtils.isAssignableFrom 检查（支持数值类型提升、自动装箱等）
        boolean assignable = TypeUtils.isAssignableFrom(to, from);
        if (assignable) {
            return true;
        }
        
        // 如果 TypeUtils 检查失败，尝试子类型检查
        // 通过 owner 的 resolveClassByName 方法解析类
        if (owner != null) {
            ClassSymbol fromClass = owner.resolveClassByName(from);
            ClassSymbol toClass = owner.resolveClassByName(to);
            
            if (fromClass != null && toClass != null) {
                // 检查 fromClass 是否是 toClass 的子类型
                return fromClass.isSubtypeOf(toClass);
            }
        }
        
        return false;
    }

    /**
     * 生成方法描述符（用于字节码）
     * 例如: (II)V, (Ljava/lang/String;)I
     */
    public String getDescriptor() {
        StringBuilder sb = new StringBuilder("(");
        for (String paramType : parameterTypes) {
            sb.append(toDescriptor(paramType));
        }
        sb.append(")");
        sb.append(toDescriptor(type));  // 返回类型
        return sb.toString();
    }

    /**
     * 将类型转换为描述符格式
     */
    private String toDescriptor(String typeName) {
        return switch (typeName) {
            case "void" -> "V";
            case "bool" -> "Z";  // Partic bool -> JVM boolean
            case "byte" -> "B";
            case "char" -> "C";
            case "short" -> "S";
            case "int" -> "I";
            case "long" -> "J";
            case "float" -> "F";
            case "double" -> "D";
            default -> {
                // 数组类型
                if (typeName.endsWith("[]")) {
                    yield "[" + toDescriptor(typeName.substring(0, typeName.length() - 2));
                }
                // 对象类型: java.lang.String -> Ljava/lang/String;
                yield "L" + typeName.replace('.', '/') + ";";
            }
        };
    }

    // Getters
    public ClassSymbol getOwner() { return owner; }
    public void setOwner(ClassSymbol owner) { this.owner = owner; }
    public List<VariableSymbol> getParameters() { return parameters; }
    public List<String> getParameterTypes() { return parameterTypes; }
    public Scope getBodyScope() { return bodyScope; }
    public boolean isConstructor() { return isConstructor; }
    public boolean isStatic() { return Modifier.isStatic(modifiers); }
    public boolean isVarArgs() { return isVarArgs; }
    public void setVarArgs(boolean isVarArgs) { this.isVarArgs = isVarArgs; }
    public int getNextLocalIndex() { return nextLocalIndex; }

    @Override
    public String toString() {
        return "MethodSymbol{name='" + name + "', returnType='" + type + 
               "', params=" + parameterTypes + ", isConstructor=" + isConstructor + "}";
    }
}

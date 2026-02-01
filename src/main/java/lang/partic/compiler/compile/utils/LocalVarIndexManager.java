package lang.partic.compiler.compile.utils;

import lang.partic.compiler.ir.ParticMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * 局部变量索引管理器
 * 负责为局部变量分配 JVM 局部变量表索引
 */
public class LocalVarIndexManager {
    private final Map<String, Integer> varIndexMap = new HashMap<>();
    private int nextIndex;

    public LocalVarIndexManager(ParticMethod method) {
        // 计算起始索引
        nextIndex = 0;
        
        // 非静态方法：this 占用索引 0
        boolean isStatic = method.getModifiers().getOthers().contains("static");
        if (!isStatic) {
            varIndexMap.put("this", nextIndex++);
        }
        
        // 方法参数占用后续索引
        for (var param : method.getParameters()) {
            String paramName = param.getName();
            String paramType = param.getType();
            varIndexMap.put(paramName, nextIndex);
            nextIndex += getTypeSlots(paramType);
        }
    }

    /**
     * 获取变量的索引
     */
    public int getIndex(String varName) {
        Integer index = varIndexMap.get(varName);
        if (index != null) {
            return index;
        }
        
        // 如果变量不存在，分配新索引（这种情况不应该发生）
        int newIndex = nextIndex;
        varIndexMap.put(varName, newIndex);
        nextIndex += 1; // 默认假设是引用类型（1个slot）
        return newIndex;
    }

    /**
     * 为局部变量分配索引
     */
    public int allocateIndex(String varName, String type) {
        if (varIndexMap.containsKey(varName)) {
            return varIndexMap.get(varName);
        }
        
        int index = nextIndex;
        varIndexMap.put(varName, index);
        nextIndex += getTypeSlots(type);
        return index;
    }

    /**
     * 获取类型占用的 slot 数
     * long 和 double 占用 2 个 slot，其他占用 1 个
     */
    private int getTypeSlots(String type) {
        if (type == null) return 1;
        return (type.equals("long") || type.equals("double")) ? 2 : 1;
    }
}

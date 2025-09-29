package io.github.nekosora.context;

import io.github.nekosora.exception.CompileError;

import java.util.HashMap;
import java.util.Map;

public class ImportManager {
    private final Map<String, String> simpleNameToFullName = new HashMap<>();
    private final Map<String, String> aliasToSimpleName = new HashMap<>();

    public void addImport(String fullClassName, String simpleName) {
        if (aliasToSimpleName.containsKey(simpleName)) {
            throw new IllegalArgumentException("simpleName与已有alia冲突");
        }
        if (simpleNameToFullName.containsKey(simpleName)) {
            throw new IllegalArgumentException("import重复");
        }
        simpleNameToFullName.put(simpleName, fullClassName);
    }

    public void addImport(String fullClassName, String simpleName, String alia) {
        if (simpleNameToFullName.containsKey(alia)) {
            throw new IllegalArgumentException("alia与已有simpleName冲突");
        }
        if (aliasToSimpleName.containsKey(alia)) {
            throw new IllegalArgumentException("别名重复");
        }
        aliasToSimpleName.put(alia, simpleName);
        addImport(fullClassName, simpleName);
    }

    public boolean isKnownClass(String name) {
        return simpleNameToFullName.containsKey(name) || aliasToSimpleName.containsKey(name);
    }

    public String foundFullName(String name) {
        if (aliasToSimpleName.containsKey(name)) {
            String alia = aliasToSimpleName.get(name);
            if (simpleNameToFullName.containsKey(alia)) {
                return simpleNameToFullName.get(alia);
            } else {
                throw new RuntimeException("已找到别名 " + alia + " 对应值，但使用值去查询FullName时发现不存在！");
            }
        }

        if (simpleNameToFullName.containsKey(name)) {
            return simpleNameToFullName.get(name);
        }
        throw new CompileError("未定义的名称: " + name);
    }
}

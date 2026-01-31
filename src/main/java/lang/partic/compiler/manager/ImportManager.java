package lang.partic.compiler.manager;

import lang.partic.compiler.utils.ClassNameUtils;
import lang.partic.compiler.utils.TypeUtils;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ImportManager {
    private final Map<String, String> simpleNameToFullName;
    private final Map<String, String> aliaToSimpleName;
    private final Map<String, String> originalSimpleNameToFullName;

    public ImportManager() {
        this.simpleNameToFullName = new HashMap<>();
        this.aliaToSimpleName = new HashMap<>();
        this.originalSimpleNameToFullName = new HashMap<>();
    }

    public void addImport(String simpleName, String fullName) {
        this.simpleNameToFullName.put(simpleName, fullName);
    }

    public void addImport(String simpleName, String alia, String fullName) {
        this.simpleNameToFullName.put(simpleName, fullName);
        this.aliaToSimpleName.put(alia, simpleName);
    }

    public void addAlia(String alia, String simpleName) {
        if (!simpleNameToFullName.containsKey(simpleName))
            throw new IllegalArgumentException("Simple name not exists!");

        // 保存原始 simpleName 的映射，然后从 simpleNameToFullName 中移除
        this.originalSimpleNameToFullName.put(simpleName, simpleNameToFullName.get(simpleName));
        this.simpleNameToFullName.remove(simpleName);

        this.aliaToSimpleName.put(alia, simpleName);
    }

    /**
     * 移除一个import并删除对应的alia
     * <p>请注意，此操作会遍历一次aliaToSimpleName Map</p>
     * @param simpleName 要删除import的simpleName
     */
    public void removeImport(String simpleName) {
        if (!simpleNameToFullName.containsKey(simpleName) &&
                !originalSimpleNameToFullName.containsKey(simpleName)) {
            return;
        }

        // 移除所有指向该 simpleName 的别名
        aliaToSimpleName.entrySet().removeIf(entry -> entry.getValue().equals(simpleName));

        // 同时移除 simpleName 在两个映射中的记录
        simpleNameToFullName.remove(simpleName);
        originalSimpleNameToFullName.remove(simpleName);
    }

    public void removeAlia(String alia) {
        if (!this.aliaToSimpleName.containsKey(alia)) {
            return;
        }

        String originalSimpleName = this.aliaToSimpleName.get(alia);

        // 如果该别名有对应的原始 simpleName，则恢复其映射
        if (this.originalSimpleNameToFullName.containsKey(originalSimpleName)) {
            String fullName = this.originalSimpleNameToFullName.get(originalSimpleName);
            this.simpleNameToFullName.put(originalSimpleName, fullName);
            this.originalSimpleNameToFullName.remove(originalSimpleName);
        }

        this.aliaToSimpleName.remove(alia);
    }

    /**
     * 通过一个Alia或SimpleName获得FullName
     * <p>优先查找Alia</p>
     * @return 找到的FullName，未找到返回null，如果传入的内容已经是FullName则直接返回，如传入的是基元类型则直接返回
     */
    public @Nullable String findFullName(String aliaOrSimpleName) {
        if (ClassNameUtils.isClassFullName(aliaOrSimpleName) || TypeUtils.isPrimitive(aliaOrSimpleName)) {
            return aliaOrSimpleName;
        }

        if (this.aliaToSimpleName.containsKey(aliaOrSimpleName)) {
            String simpleName = this.aliaToSimpleName.get(aliaOrSimpleName);
            return this.originalSimpleNameToFullName.getOrDefault(simpleName,
                    this.simpleNameToFullName.get(simpleName));
        }

        if (this.simpleNameToFullName.containsKey(aliaOrSimpleName)) {
            return this.simpleNameToFullName.get(aliaOrSimpleName);
        }

        return null;
    }
}
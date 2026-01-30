package lang.partic.compiler.manager;

import lang.partic.compiler.utils.ClassNameUtils;
import lang.partic.compiler.utils.TypeUtils;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ImportManager {
    private final Map<String, String> simpleNameToFullName;
    private final Map<String, String> aliaToSimpleName;

    public ImportManager() {
        this.simpleNameToFullName = new HashMap<>();
        this.aliaToSimpleName = new HashMap<>();
    }

    public void addImport(String simpleName, String fullName) {
        this.simpleNameToFullName.put(simpleName, fullName);
    }

    public void addImport(String simpleName, String alia, String fullName) {
        this.simpleNameToFullName.put(simpleName, fullName);
        this.aliaToSimpleName.put(alia, simpleName);
    }

    public void addAlia(String alia, String simpleName) {
        if (!simpleNameToFullName.containsKey(simpleName)) throw new IllegalArgumentException("Simple name not exists!");
        this.aliaToSimpleName.put(alia, simpleName);
    }

    /**
     * 移除一个import并删除对应的alia
     * <p>请注意，此操作会遍历一次aliaToSimpleName Map</p>
     * @param simpleName 要删除import的simpleName
     */
    public void removeImport(String simpleName) {
        if (!simpleNameToFullName.containsKey(simpleName)) {
            return;
        }

        aliaToSimpleName.entrySet().removeIf(stringStringEntry -> stringStringEntry.getValue().equals(simpleName));
        simpleNameToFullName.remove(simpleName);
    }

    public void removeAlia(String alia) {
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
            return this.simpleNameToFullName.get(
                    this.aliaToSimpleName.get(aliaOrSimpleName)
            );
        }
        return this.simpleNameToFullName.get(aliaOrSimpleName);
    }
}

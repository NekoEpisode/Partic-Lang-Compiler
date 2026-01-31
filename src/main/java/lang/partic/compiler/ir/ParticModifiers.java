package lang.partic.compiler.ir;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示修饰符（访问修饰符 + 其他修饰符）
 */
public class ParticModifiers {
    private String access; // pub, priv, prot, pack
    private List<String> others; // static, final, abstract, etc.

    public ParticModifiers() {
        this.access = "pub"; // 默认public
        this.others = new ArrayList<>();
    }

    public ParticModifiers(String access, List<String> others) {
        this.access = access;
        this.others = others;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public List<String> getOthers() {
        return others;
    }

    public void addOther(String modifier) {
        this.others.add(modifier);
    }
}

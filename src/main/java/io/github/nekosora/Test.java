package io.github.nekosora;

import java.util.ArrayList;
import java.util.List;

/**
 * For showcase only.
 */
public class Test {
    private final List<String> list = new ArrayList<>();

    public void add(String string) {
        list.add(string);
    }

    public void add(int i) {
        list.add(String.valueOf(i));
    }

    public void add(boolean b) {
        list.add(String.valueOf(b));
    }

    public void remove(int index) {
        list.remove(index);
    }

    public String get(int index) {
        return list.get(index);
    }
}

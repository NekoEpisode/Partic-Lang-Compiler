package io.github.nekosora;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public String toString() {
        return "Test{" +
                "list=" + list +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Test test = (Test) o;
        return Objects.equals(list, test.list);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(list);
    }
}

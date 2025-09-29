package io.github.nekosora.context.function;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FunctionManager {
    private final Map<String, ParticFunction> functionMap;

    public FunctionManager() {
        this.functionMap = new HashMap<>();
    }

    public void addFunction(ParticFunction function) {
        functionMap.put(function.getName(), function);
    }

    public void removeFunction(String name) {
        functionMap.remove(name);
    }

    public ParticFunction getFunction(String name) {
        return functionMap.get(name);
    }

    public boolean hasFunction(String name) {
        return functionMap.containsKey(name);
    }

    public Collection<ParticFunction> getFunctions() {
        return functionMap.values();
    }
}

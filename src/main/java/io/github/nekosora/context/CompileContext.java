package io.github.nekosora.context;

import io.github.nekosora.context.function.FunctionManager;
import io.github.nekosora.context.function.ParticFunction;
import io.github.nekosora.context.var.VariableInfo;

import java.util.HashMap;
import java.util.Map;

public class CompileContext {
    private final ImportManager importManager;
    private final FunctionManager functionManager;
    private ParticFunction currentFunction = null;
    private long startTime;
    private final Map<String, VariableInfo> localVariables = new HashMap<>();
    private int nextLocalIndex = 0;

    public CompileContext() {
        this.startTime = 0;
        this.importManager = new ImportManager();
        this.functionManager = new FunctionManager();
    }

    public ImportManager getImportManager() {
        return importManager;
    }

    public FunctionManager getFunctionManager() {
        return functionManager;
    }

    public ParticFunction getCurrentFunction() {
        return currentFunction;
    }

    public void setCurrentFunction(ParticFunction currentFunction) {
        this.currentFunction = currentFunction;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public int allocateLocalVariable(String varName, String descriptor) {
        int index = nextLocalIndex;
        nextLocalIndex += getTypeSlots(descriptor);

        localVariables.put(varName, new VariableInfo(descriptor, index, varName));
        return index;
    }

    public VariableInfo getLocalVariable(String varName) {
        return localVariables.get(varName);
    }

    private int getTypeSlots(String descriptor) {
        return switch (descriptor) {
            case "J", "D" -> 2;
            default -> 1;
        };
    }

    public void resetLocalVariables() {
        localVariables.clear();
        nextLocalIndex = 0;
    }

    public Map<String, VariableInfo> getLocalVariablesMap() {
        return this.localVariables;
    }

    public void setLocalVariables(Map<String, VariableInfo> localVariables) {
        this.localVariables.clear();
        this.localVariables.putAll(localVariables);

        int maxIndex = -1;
        int maxSlots = 1;
        for (VariableInfo var : localVariables.values()) {
            if (var.index() > maxIndex) {
                maxIndex = var.index();
                maxSlots = getTypeSlots(var.descriptor());
            }
        }
        this.nextLocalIndex = maxIndex == -1 ? 0 : maxIndex + maxSlots;
    }
}
package io.github.nekosora.context.function;

import com.google.gson.JsonObject;
import io.github.nekosora.antlr.ParticParser;
import io.github.nekosora.context.var.VariableInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParticFunction {
    private final String name;
    private final String descriptor;
    private final List<ParticParser.ParameterContext> parameterContexts;
    private final List<JsonObject> actions;
    private Map<String, VariableInfo> localVariables;

    public ParticFunction(String name, String descriptor, List<ParticParser.ParameterContext> parameterContexts) {
        this.name = name;
        this.descriptor = descriptor;
        this.parameterContexts = parameterContexts;
        this.actions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public List<JsonObject> getActions() {
        return actions;
    }

    public List<ParticParser.ParameterContext> getParameterContexts() {
        return parameterContexts;
    }

    public void setLocalVariables(Map<String, VariableInfo> localVariables) {
        this.localVariables = new HashMap<>(localVariables);
    }

    public Map<String, VariableInfo> getLocalVariables() {
        return this.localVariables;
    }
}

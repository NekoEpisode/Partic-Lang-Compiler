package io.github.nekosora.visitor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.nekosora.context.CompileContext;
import io.github.nekosora.context.function.ParticFunction;
import io.github.nekosora.context.var.VariableInfo;
import io.github.nekosora.exception.CompileError;
import io.github.nekosora.logger.ParticLogger;
import io.github.nekosora.utils.ClassNameUtils;
import io.github.nekosora.utils.TypeUtils;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.objectweb.asm.Opcodes.*;

public class ParticCompiler {
    private final CompileContext context;
    private final Path outputPath;
    private final ClassWriter classWriter;
    private MethodVisitor methodVisitor;
    private final String className;

    public ParticCompiler(CompileContext context, Path outputPath) {
        this.context = context;
        this.outputPath = outputPath;
        this.className = ClassNameUtils.getClassNameFromFilePath(outputPath);
        this.classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
    }

    public void compile() {
        ParticLogger.codeGenInfo("正在编译字节码，输出路径为 " + outputPath);
        generateClassHeader();
        generateFunctions();
        generateClassFooter();
        writeClassFile();
        ParticLogger.codeGenInfo("编译完成！class 文件已保存到: " + outputPath);
    }

    private void writeClassFile() {
        try {
            byte[] bytecode = classWriter.toByteArray();
            if (outputPath.getParent() != null) {
                java.nio.file.Files.createDirectories(outputPath.getParent());
            }
            java.nio.file.Files.write(outputPath, bytecode);
            ParticLogger.codeGenDebug("字节码写入成功，共 " + bytecode.length + " 字节");
            ParticLogger.codeGenInfo("编译成功！用时: " + (System.currentTimeMillis() - context.getStartTime()) + "ms");
        } catch (java.io.IOException e) {
            ParticLogger.codeGenError("写入 class 文件失败: " + e.getMessage());
            throw new RuntimeException("Failed to write class file", e);
        }
    }

    private void generateClassHeader() {
        classWriter.visit(V21, ACC_PUBLIC, className, null, "java/lang/Object", null);
    }

    private void generateFunctions() {
        for (ParticFunction function : context.getFunctionManager().getFunctions()) {
            context.setLocalVariables(function.getLocalVariables());

            methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, function.getName(), function.getDescriptor(), null, null);
            methodVisitor.visitCode();
            for (JsonObject action : function.getActions()) {
                generateAction(action);
            }
            methodVisitor.visitInsn(RETURN);
            methodVisitor.visitMaxs(0, 0);
            methodVisitor.visitEnd();
        }
    }

    private void generateAction(JsonObject action) {
        String type = action.get("type").getAsString();
        switch (type) {
            case "VARIABLE_DECLARATION" -> generateVariableDeclaration(action);
            case "EXPRESSION_STATEMENT" -> generateExpression(action.get("expression").getAsJsonObject());
        }
    }

    private void generateVariableDeclaration(JsonObject action) {
        String varName = action.get("varName").getAsString();
        String particType = action.get("varType").getAsString();
        String descriptor = TypeUtils.toDescriptor(particType);

        if (action.has("initialValue")) {
            JsonObject initialValue = action.get("initialValue").getAsJsonObject();
            generateExpression(initialValue);
        } else {
            generateDefaultValue(descriptor);
        }

        VariableInfo varInfo = context.getLocalVariable(varName);
        if (varInfo == null) {
            throw new CompileError("Internal Compiler Error: variable '" + varName + "' was not found in the context during bytecode generation.");
        }
        int varIndex = varInfo.index();
        int storeOpcode = getStoreOpcode(descriptor);
        methodVisitor.visitVarInsn(storeOpcode, varIndex);
    }

    private void generateDefaultValue(String descriptor) {
        switch (descriptor) {
            case "J" -> methodVisitor.visitLdcInsn(0L);
            case "D" -> methodVisitor.visitLdcInsn(0.0);
            case "F" -> methodVisitor.visitLdcInsn(0.0f);
            case "I", "Z", "B", "C", "S" -> methodVisitor.visitInsn(ICONST_0);
            default -> methodVisitor.visitInsn(ACONST_NULL);
        }
    }

    private Type generateExpression(JsonObject expr) {
        String type = expr.get("type").getAsString();
        switch (type) {
            case "OBJECT_CREATION": return generateObjectCreation(expr);
            case "METHOD_CALL": return generateMethodCall(expr);
            case "FIELD_ACCESS": return generateFieldAccess(expr);
            case "VARIABLE_LOAD": return generateVariableLoad(expr);
            case "STATIC_ACCESS":
                String fqcn = context.getImportManager().foundFullName(expr.get("className").getAsString());
                try {
                    return Type.getType(Class.forName(fqcn));
                } catch (ClassNotFoundException e) {
                    throw new CompileError("Class not found: " + fqcn);
                }
            case "FUNCTION_REF":
                ParticFunction particFunction = context.getFunctionManager().getFunction(expr.get("name").getAsString());
                if (particFunction == null) throw new CompileError("Function not found: " + expr.get("name").getAsString());
                return Type.getMethodType(particFunction.getDescriptor());
            case "intLiteral":
                methodVisitor.visitLdcInsn(expr.get("value").getAsInt());
                return Type.INT_TYPE;
            case "longLiteral":
                methodVisitor.visitLdcInsn(expr.get("value").getAsLong());
                return Type.LONG_TYPE;
            case "floatLiteral":
                methodVisitor.visitLdcInsn(expr.get("value").getAsFloat());
                return Type.FLOAT_TYPE;
            case "doubleLiteral":
                methodVisitor.visitLdcInsn(expr.get("value").getAsDouble());
                return Type.DOUBLE_TYPE;
            case "booleanLiteral":
                methodVisitor.visitInsn(expr.get("value").getAsBoolean() ? ICONST_1 : ICONST_0);
                return Type.BOOLEAN_TYPE;
            case "stringLiteral":
                methodVisitor.visitLdcInsn(expr.get("value").getAsString());
                return Type.getType(String.class);
            case "charLiteral":
                methodVisitor.visitLdcInsn((int) expr.get("value").getAsString().charAt(0));
                return Type.CHAR_TYPE;
            case "nullLiteral":
                methodVisitor.visitInsn(ACONST_NULL);
                return Type.getType(Object.class);
        }
        throw new CompileError("Unknown expression type: " + type);
    }

    private java.lang.reflect.Constructor<?> findConstructor(Class<?> clazz, List<Type> argTypes) throws NoSuchMethodException {
        for (java.lang.reflect.Constructor<?> constructor : clazz.getConstructors()) {
            Class<?>[] paramTypes = constructor.getParameterTypes();
            if (paramTypes.length != argTypes.size()) {
                continue;
            }
            boolean match = true;
            for (int i = 0; i < paramTypes.length; i++) {
                if (!Type.getType(paramTypes[i]).equals(argTypes.get(i))) {
                    match = false;
                    break;
                }
            }
            if (match) {
                return constructor;
            }
        }
        throw new NoSuchMethodException("No matching constructor found for class " + clazz.getName() + " with args " + argTypes);
    }

    private Type generateObjectCreation(JsonObject expr) {
        String className = expr.get("className").getAsString();
        String fqcn = context.getImportManager().foundFullName(className);
        String internalName = fqcn.replace('.', '/');
        JsonArray args = expr.getAsJsonArray("args");
        List<Type> argTypes = new ArrayList<>();

        methodVisitor.visitTypeInsn(NEW, internalName);
        methodVisitor.visitInsn(DUP);

        for (JsonElement arg : args) {
            argTypes.add(generateExpression(arg.getAsJsonObject()));
        }

        try {
            Class<?> targetClass = Class.forName(fqcn);
            java.lang.reflect.Constructor<?> constructorToCall = findConstructor(targetClass, argTypes);
            String constructorDescriptor = Type.getConstructorDescriptor(constructorToCall);

            methodVisitor.visitMethodInsn(INVOKESPECIAL, internalName, "<init>", constructorDescriptor, false);
            return Type.getType(targetClass);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new CompileError("Could not create object of class " + fqcn + " with args " + argTypes);
        }
    }

    private Type generateMethodCall(JsonObject expr) {
        JsonObject targetExpr = expr.getAsJsonObject("target");
        String methodName = expr.get("methodName").getAsString();
        JsonArray args = expr.getAsJsonArray("args");
        List<Type> argTypes = new ArrayList<>();

        if (targetExpr.get("type").getAsString().equals("FUNCTION_REF")) {
            for (JsonElement arg : args) {
                argTypes.add(generateExpression(arg.getAsJsonObject()));
            }
            ParticFunction function = context.getFunctionManager().getFunction(targetExpr.get("name").getAsString());
            methodVisitor.visitMethodInsn(INVOKESTATIC, this.className, function.getName(), function.getDescriptor(), false);
            return Type.getReturnType(function.getDescriptor());
        }

        Type targetType = generateExpression(targetExpr);
        String targetTypeName = targetType.getClassName();

        for (JsonElement arg : args) {
            argTypes.add(generateExpression(arg.getAsJsonObject()));
        }

        try {
            Class<?> targetClass = Class.forName(targetTypeName);
            Method methodToCall = findMethod(targetClass, methodName, argTypes);
            String methodDescriptor = Type.getMethodDescriptor(methodToCall);
            String owner = Type.getInternalName(methodToCall.getDeclaringClass());

            boolean isStatic = java.lang.reflect.Modifier.isStatic(methodToCall.getModifiers());
            int opcode;
            if (targetClass.isInterface()) {
                opcode = INVOKEINTERFACE;
            } else if (isStatic) {
                opcode = INVOKESTATIC;
            } else {
                opcode = INVOKEVIRTUAL;
            }

            methodVisitor.visitMethodInsn(opcode, owner, methodName, methodDescriptor, targetClass.isInterface());
            return Type.getType(methodToCall.getReturnType());
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new CompileError("Could not find method \"" + methodName + "\" on class " + targetTypeName + " with args " + argTypes);
        }
    }

    private Type generateFieldAccess(JsonObject expr) {
        JsonObject targetExpr = expr.getAsJsonObject("target");
        String fieldName = expr.get("fieldName").getAsString();

        Type targetType = generateExpression(targetExpr);
        try {
            Class<?> targetClass = Class.forName(targetType.getClassName());
            Field field = targetClass.getField(fieldName);
            String owner = Type.getInternalName(field.getDeclaringClass());
            String descriptor = Type.getDescriptor(field.getType());
            methodVisitor.visitFieldInsn(GETSTATIC, owner, fieldName, descriptor);
            return Type.getType(field.getType());
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            throw new CompileError("Could not find field " + fieldName + " on class " + targetType.getClassName());
        }
    }

    private Type generateVariableLoad(JsonObject expr) {
        String varName = expr.get("varName").getAsString();
        VariableInfo varInfo = context.getLocalVariable(varName);
        if (varInfo == null) {
            throw new CompileError("Undefined variable: " + varName);
        }
        int loadOpcode = getLoadOpcode(varInfo.descriptor());
        methodVisitor.visitVarInsn(loadOpcode, varInfo.index());
        return Type.getType(varInfo.descriptor());
    }

    private int getStoreOpcode(String descriptor) {
        return Type.getType(descriptor).getOpcode(ISTORE);
    }

    private int getLoadOpcode(String descriptor) {
        return Type.getType(descriptor).getOpcode(ILOAD);
    }

    private Method findMethod(Class<?> clazz, String name, List<Type> argTypes) throws NoSuchMethodException {
        for (Method method : clazz.getMethods()) {
            if (!method.getName().equals(name)) {
                continue;
            }
            Class<?>[] paramTypes = method.getParameterTypes();
            if (paramTypes.length != argTypes.size()) {
                continue;
            }
            boolean match = true;
            for (int i = 0; i < paramTypes.length; i++) {
                if (!Type.getType(paramTypes[i]).equals(argTypes.get(i))) {
                    match = false;
                    break;
                }
            }
            if (match) {
                return method;
            }
        }
        throw new NoSuchMethodException();
    }

    private void generateClassFooter() {
        classWriter.visitEnd();
    }
}
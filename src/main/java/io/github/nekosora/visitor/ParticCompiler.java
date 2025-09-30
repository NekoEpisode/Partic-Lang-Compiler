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
import java.util.Map;

import static org.objectweb.asm.Opcodes.*;

public class ParticCompiler {
    private final CompileContext context;
    private final Path outputPath;
    private final ClassWriter classWriter;
    private MethodVisitor methodVisitor;
    private final String className;

    private static final Map<Type, Integer> TYPE_RANK = Map.of(
            Type.INT_TYPE, 1,
            Type.LONG_TYPE, 2,
            Type.FLOAT_TYPE, 3,
            Type.DOUBLE_TYPE, 4
    );

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
            context.setCurrentFunction(function);
            context.setLocalVariables(function.getLocalVariables());

            methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, function.getName(), function.getDescriptor(), null, null);
            methodVisitor.visitCode();

            for (JsonObject action : function.getActions()) {
                generateAction(action);
            }

            // 大部分return类型已经被函数本身处理过，这里我们只允许隐式void return
            Type returnType = Type.getReturnType(function.getDescriptor());
            if (returnType.equals(Type.VOID_TYPE)) {
                boolean hasReturn = function.getActions().stream()
                        .anyMatch(action -> "RETURN_STATEMENT".equals(action.get("type").getAsString()));
                if (!hasReturn) {
                    methodVisitor.visitInsn(RETURN);
                }
            }

            methodVisitor.visitMaxs(0, 0);
            methodVisitor.visitEnd();
        }
    }

    private void generateAction(JsonObject action) {
        System.out.println(action);
        String type = action.get("type").getAsString();
        switch (type) {
            case "VARIABLE_DECLARATION" -> generateVariableDeclaration(action);
            case "EXPRESSION_STATEMENT" -> generateExpression(action.get("expression").getAsJsonObject());
            case "RETURN_STATEMENT" -> generateReturnStatement(action);
        }
    }

    private void generateReturnStatement(JsonObject action) {
        if (action.has("expression")) {
            Type expressionType = generateExpression(action.get("expression").getAsJsonObject());

            Type methodReturnType = Type.getReturnType(context.getCurrentFunction().getDescriptor());

            if (methodReturnType.equals(Type.VOID_TYPE)) {
                throw new CompileError("Cannot return a value from a void method: " + context.getCurrentFunction().getName());
            }

            if (!expressionType.equals(methodReturnType)) {
                convertToReturnType(expressionType, methodReturnType);
            }

            int returnOpcode = methodReturnType.getOpcode(IRETURN);
            methodVisitor.visitInsn(returnOpcode);
        } else {
            Type methodReturnType = Type.getReturnType(context.getCurrentFunction().getDescriptor());

            if (!methodReturnType.equals(Type.VOID_TYPE)) {
                throw new CompileError("Missing return value in method: " + context.getCurrentFunction().getName());
            }

            methodVisitor.visitInsn(RETURN);
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
            case "FIELD_ACCESS": {
                JsonObject targetExpr = expr.getAsJsonObject("target");
                String fieldName = expr.get("fieldName").getAsString();

                Type targetType = generateExpression(targetExpr);

                if (targetType.getSort() == Type.ARRAY && fieldName.equals("length")) {
                    methodVisitor.visitInsn(ARRAYLENGTH);
                    return Type.INT_TYPE;
                }

                try {
                    Class<?> targetClass = Class.forName(targetType.getClassName());
                    Field field = targetClass.getField(fieldName);
                    String owner = Type.getInternalName(field.getDeclaringClass());
                    String descriptor = Type.getDescriptor(field.getType());

                    if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                        methodVisitor.visitFieldInsn(GETSTATIC, owner, fieldName, descriptor);
                    } else {
                        methodVisitor.visitFieldInsn(GETFIELD, owner, fieldName, descriptor);
                    }

                    return Type.getType(field.getType());
                } catch (ClassNotFoundException | NoSuchFieldException e) {
                    throw new CompileError("Could not find field " + fieldName + " on class " + targetType.getClassName());
                }
            }
            case "VARIABLE_LOAD": return generateVariableLoad(expr);
            case "BINARY_OPERATION":
                return generateBinaryOperation(expr);
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

    private Type analyzeExpressionType(JsonObject expr) {
        String type = expr.get("type").getAsString();
        switch (type) {
            case "intLiteral":
                return Type.INT_TYPE;
            case "longLiteral":
                return Type.LONG_TYPE;
            case "floatLiteral":
                return Type.FLOAT_TYPE;
            case "doubleLiteral":
                return Type.DOUBLE_TYPE;
            case "VARIABLE_LOAD": {
                String varName = expr.get("varName").getAsString();
                VariableInfo varInfo = context.getLocalVariable(varName);
                if (varInfo == null) {
                    throw new CompileError("Undefined variable: " + varName);
                }
                return Type.getType(varInfo.descriptor());
            }
            case "BINARY_OPERATION": {
                JsonObject left = expr.getAsJsonObject("left");
                JsonObject right = expr.getAsJsonObject("right");
                Type leftType = analyzeExpressionType(left);
                Type rightType = analyzeExpressionType(right);
                return getTargetType(leftType, rightType);
            }
            case "STATIC_ACCESS": {
                String fqcn = context.getImportManager().foundFullName(expr.get("className").getAsString());
                try {
                    return Type.getType(Class.forName(fqcn));
                } catch (ClassNotFoundException e) {
                    throw new CompileError("Class not found: " + fqcn);
                }
            }
            case "FIELD_ACCESS": {
                JsonObject targetExpr = expr.getAsJsonObject("target");
                String fieldName = expr.get("fieldName").getAsString();
                Type targetType = analyzeExpressionType(targetExpr);
                try {
                    Class<?> targetClass = Class.forName(targetType.getClassName());
                    Field field = targetClass.getField(fieldName);
                    return Type.getType(field.getType());
                } catch (ClassNotFoundException | NoSuchFieldException e) {
                    throw new CompileError("Cannot analyze field type: " + fieldName);
                }
            }
            case "METHOD_CALL": {
                JsonObject targetExpr = expr.getAsJsonObject("target");
                String methodName = expr.get("methodName").getAsString();
                JsonArray args = expr.getAsJsonArray("args");

                if (targetExpr.get("type").getAsString().equals("FUNCTION_REF")) {
                    ParticFunction function = context.getFunctionManager()
                            .getFunction(targetExpr.get("name").getAsString());
                    return Type.getReturnType(function.getDescriptor());
                }

                try {
                    Type targetType = analyzeExpressionType(targetExpr);
                    Class<?> targetClass = Class.forName(targetType.getClassName());

                    List<Type> argTypes = new ArrayList<>();
                    for (JsonElement arg : args) {
                        argTypes.add(analyzeExpressionType(arg.getAsJsonObject()));
                    }

                    Method method = findMethod(targetClass, methodName, argTypes);
                    return Type.getType(method.getReturnType());
                } catch (ClassNotFoundException e) {
                    throw new CompileError("Cannot analyze return type of method: " + methodName + " - " + e.getMessage());
                } catch (NoSuchMethodException e) {
                    throw new CompileError("Cannot find method: " + methodName + " - " + e.getMessage());
                }
            }
            default:
                throw new CompileError("Unsupported type analysis for: " + type);
        }
    }

    private Type generateBinaryOperation(JsonObject expr) {
        String operator = expr.get("operator").getAsString();
        JsonObject leftExpr = expr.getAsJsonObject("left");
        JsonObject rightExpr = expr.getAsJsonObject("right");

        // 先分析目标类型
        Type targetType = analyzeExpressionType(expr);

        // 生成左边并提升到目标类型
        Type leftType = generateExpression(leftExpr);
        if (!leftType.equals(targetType)) {
            promote(leftType, targetType);
        }

        // 生成右边并提升到目标类型
        Type rightType = generateExpression(rightExpr);
        if (!rightType.equals(targetType)) {
            promote(rightType, targetType);
        }

        // 发射字节码
        switch (operator) {
            case "+":
                methodVisitor.visitInsn(targetType.getOpcode(IADD));
                break;
            case "-":
                methodVisitor.visitInsn(targetType.getOpcode(ISUB));
                break;
            case "*":
                methodVisitor.visitInsn(targetType.getOpcode(IMUL));
                break;
            case "/":
                methodVisitor.visitInsn(targetType.getOpcode(IDIV));
                break;
            case "%":
                methodVisitor.visitInsn(targetType.getOpcode(IREM));
                break;
            default:
                throw new CompileError("Unsupported operator: " + operator);
        }

        return targetType;
    }

    private java.lang.reflect.Constructor<?> findConstructor(Class<?> clazz, List<Type> argTypes) throws NoSuchMethodException {
        Class<?>[] paramClasses = new Class<?>[argTypes.size()];
        for (int i = 0; i < argTypes.size(); i++) {
            paramClasses[i] = getClassFromType(argTypes.get(i));
        }

        // 首先尝试精确匹配
        for (java.lang.reflect.Constructor<?> constructor : clazz.getConstructors()) {
            Class<?>[] constructorParams = constructor.getParameterTypes();
            if (constructorParams.length != paramClasses.length) {
                continue;
            }
            boolean exactMatch = true;
            for (int i = 0; i < constructorParams.length; i++) {
                if (!constructorParams[i].equals(paramClasses[i])) {
                    exactMatch = false;
                    break;
                }
            }
            if (exactMatch) {
                return constructor;
            }
        }

        // 精确匹配失败，查找兼容的构造函数
        java.lang.reflect.Constructor<?> bestMatch = null;
        int bestMatchScore = Integer.MAX_VALUE;

        for (java.lang.reflect.Constructor<?> constructor : clazz.getConstructors()) {
            Class<?>[] constructorParams = constructor.getParameterTypes();
            if (constructorParams.length != paramClasses.length) {
                continue;
            }

            boolean compatible = true;
            int matchScore = 0;

            for (int i = 0; i < constructorParams.length; i++) {
                if (!isAssignable(paramClasses[i], constructorParams[i])) {
                    compatible = false;
                    break;
                }
                matchScore += getAssignmentDistance(paramClasses[i], constructorParams[i]);
            }

            if (compatible && matchScore < bestMatchScore) {
                bestMatch = constructor;
                bestMatchScore = matchScore;
            }
        }

        if (bestMatch != null) {
            return bestMatch;
        }

        throw new NoSuchMethodException("No compatible constructor found for class " + clazz.getName() +
                " with args " + java.util.Arrays.toString(paramClasses));
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
            String owner = targetType.getInternalName();

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

        // 特殊处理数组的 length 字段
        if (targetType.getSort() == Type.ARRAY && fieldName.equals("length")) {
            methodVisitor.visitInsn(ARRAYLENGTH);
            return Type.INT_TYPE;
        }

        try {
            Class<?> targetClass = Class.forName(targetType.getClassName());
            Field field = targetClass.getField(fieldName);
            String owner = Type.getInternalName(field.getDeclaringClass());
            String descriptor = Type.getDescriptor(field.getType());

            // 区分静态字段和实例字段
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                // 静态字段：需要先弹出栈上的类引用（因为 STATIC_ACCESS 生成的）
                methodVisitor.visitInsn(POP);
                methodVisitor.visitFieldInsn(GETSTATIC, owner, fieldName, descriptor);
            } else {
                // 实例字段：对象引用已经在栈上
                methodVisitor.visitFieldInsn(GETFIELD, owner, fieldName, descriptor);
            }

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
        Class<?>[] paramClasses = new Class<?>[argTypes.size()];
        for (int i = 0; i < argTypes.size(); i++) {
            paramClasses[i] = getClassFromType(argTypes.get(i));
        }

        // 首先尝试精确匹配
        try {
            return clazz.getMethod(name, paramClasses);
        } catch (NoSuchMethodException e) {
            // 精确匹配失败，尝试查找兼容的方法
        }

        // 查找所有公共方法，包括继承的方法
        Method bestMatch = null;
        int bestMatchScore = Integer.MAX_VALUE;

        for (Method method : clazz.getMethods()) {
            if (!method.getName().equals(name)) {
                continue;
            }

            Class<?>[] methodParams = method.getParameterTypes();
            if (methodParams.length != paramClasses.length) {
                continue;
            }

            // 检查参数是否兼容
            boolean compatible = true;
            int matchScore = 0;

            for (int i = 0; i < methodParams.length; i++) {
                if (!isAssignable(paramClasses[i], methodParams[i])) {
                    compatible = false;
                    break;
                }
                // 计算匹配分数：精确匹配得0分，需要转换的得更高分
                matchScore += getAssignmentDistance(paramClasses[i], methodParams[i]);
            }

            if (compatible && matchScore < bestMatchScore) {
                bestMatch = method;
                bestMatchScore = matchScore;
            }
        }

        if (bestMatch != null) {
            return bestMatch;
        }

        throw new NoSuchMethodException("No compatible method found for " + clazz.getName() + "." + name +
                " with args " + java.util.Arrays.toString(paramClasses));
    }

    /**
     * 检查 from 类型是否可以赋值给 to 类型
     */
    private boolean isAssignable(Class<?> from, Class<?> to) {
        // 完全相同
        if (from.equals(to)) {
            return true;
        }

        // 处理基本类型的自动装箱/拆箱
        if (from.isPrimitive() || to.isPrimitive()) {
            return isPrimitiveAssignable(from, to);
        }

        // 处理继承关系
        return to.isAssignableFrom(from);
    }

    /**
     * 处理基本类型之间的兼容性
     */
    private boolean isPrimitiveAssignable(Class<?> from, Class<?> to) {
        // 自动装箱/拆箱
        Class<?> fromWrapper = getWrapperClass(from);
        Class<?> toWrapper = getWrapperClass(to);

        if (fromWrapper.equals(toWrapper)) {
            return true;
        }

        // 基本类型的自动提升（如 int -> long）
        if (from.isPrimitive() && to.isPrimitive()) {
            if (from == byte.class) return to == short.class || to == int.class || to == long.class || to == float.class || to == double.class;
            if (from == short.class) return to == int.class || to == long.class || to == float.class || to == double.class;
            if (from == char.class) return to == int.class || to == long.class || to == float.class || to == double.class;
            if (from == int.class) return to == long.class || to == float.class || to == double.class;
            if (from == long.class) return to == float.class || to == double.class;
            if (from == float.class) return to == double.class;
        }

        return false;
    }

    /**
     * 获取基本类型对应的包装类
     */
    private Class<?> getWrapperClass(Class<?> primitiveClass) {
        if (!primitiveClass.isPrimitive()) {
            return primitiveClass;
        }
        if (primitiveClass == int.class) return Integer.class;
        if (primitiveClass == long.class) return Long.class;
        if (primitiveClass == double.class) return Double.class;
        if (primitiveClass == float.class) return Float.class;
        if (primitiveClass == boolean.class) return Boolean.class;
        if (primitiveClass == char.class) return Character.class;
        if (primitiveClass == byte.class) return Byte.class;
        if (primitiveClass == short.class) return Short.class;
        if (primitiveClass == void.class) return Void.class;
        return primitiveClass;
    }

    /**
     * 计算赋值距离，用于选择最佳匹配
     * 返回值越小表示匹配度越高
     */
    private int getAssignmentDistance(Class<?> from, Class<?> to) {
        if (from.equals(to)) {
            return 0; // 精确匹配
        }

        // 装箱/拆箱
        if (getWrapperClass(from).equals(getWrapperClass(to))) {
            return 1;
        }

        // 基本类型提升
        if (from.isPrimitive() && to.isPrimitive()) {
            return 2;
        }

        // 继承关系：计算继承层级
        if (to.isAssignableFrom(from)) {
            int distance = 3;
            Class<?> current = from;
            while (current != null && !current.equals(to)) {
                distance++;
                current = current.getSuperclass();
                if (current == null) {
                    // 检查接口
                    for (Class<?> iface : from.getInterfaces()) {
                        if (to.isAssignableFrom(iface)) {
                            return distance;
                        }
                    }
                }
            }
            return distance;
        }

        return Integer.MAX_VALUE; // 不兼容
    }

    private void generateClassFooter() {
        classWriter.visitEnd();
    }

    private Class<?> getClassFromType(Type type) {
        try {
            String className = type.getClassName();

            switch (className) {
                case "int": return int.class;
                case "long": return long.class;
                case "double": return double.class;
                case "float": return float.class;
                case "boolean": return boolean.class;
                case "char": return char.class;
                case "byte": return byte.class;
                case "short": return short.class;
                case "void": return void.class;
            }

            if (className.endsWith("[]")) {
                return Class.forName(type.getDescriptor().replace('/', '.'));
            }

            return Class.forName(className);

        } catch (ClassNotFoundException e) {
            throw new CompileError("Internal error during reflection: " + e.getMessage() + " (type: " + type + ")");
        }
    }

    private Type promote(Type from, Type to) {
        if (from.equals(to)) return from;

        if (from.equals(Type.INT_TYPE)) {
            if (to.equals(Type.LONG_TYPE)) {
                methodVisitor.visitInsn(I2L);
                return Type.LONG_TYPE;
            } else if (to.equals(Type.FLOAT_TYPE)) {
                methodVisitor.visitInsn(I2F);
                return Type.FLOAT_TYPE;
            } else if (to.equals(Type.DOUBLE_TYPE)) {
                methodVisitor.visitInsn(I2D);
                return Type.DOUBLE_TYPE;
            }
        } else if (from.equals(Type.LONG_TYPE)) {
            if (to.equals(Type.FLOAT_TYPE)) {
                methodVisitor.visitInsn(L2F);
                return Type.FLOAT_TYPE;
            } else if (to.equals(Type.DOUBLE_TYPE)) {
                methodVisitor.visitInsn(L2D);
                return Type.DOUBLE_TYPE;
            }
        } else if (from.equals(Type.FLOAT_TYPE) && to.equals(Type.DOUBLE_TYPE)) {
            methodVisitor.visitInsn(F2D);
            return Type.DOUBLE_TYPE;
        }

        throw new CompileError("Cannot promote " + from + " to " + to);
    }

    private Type getTargetType(Type left, Type right) {
        int leftRank = TYPE_RANK.getOrDefault(left, 0);
        int rightRank = TYPE_RANK.getOrDefault(right, 0);
        if (leftRank == 0 || rightRank == 0) {
            throw new CompileError("Unsupported type for arithmetic: " + left + ", " + right);
        }
        return leftRank >= rightRank ? left : right;
    }

    private void convertToReturnType(Type fromType, Type toType) {
        if (fromType.equals(toType)) {
            return;
        }

        // 基本类型转换
        if (fromType.equals(Type.INT_TYPE)) {
            if (toType.equals(Type.DOUBLE_TYPE)) {
                methodVisitor.visitInsn(I2D);
            } else if (toType.equals(Type.FLOAT_TYPE)) {
                methodVisitor.visitInsn(I2F);
            } else if (toType.equals(Type.LONG_TYPE)) {
                methodVisitor.visitInsn(I2L);
            }
        } else if (fromType.equals(Type.FLOAT_TYPE)) {
            if (toType.equals(Type.DOUBLE_TYPE)) {
                methodVisitor.visitInsn(F2D);
            } else {
                throw new CompileError("Cannot convert from " + fromType + " to " + toType + " in return statement");
            }
        } else if (fromType.equals(Type.LONG_TYPE)) {
            if (toType.equals(Type.DOUBLE_TYPE)) {
                methodVisitor.visitInsn(L2D);
            } else if (toType.equals(Type.FLOAT_TYPE)) {
                methodVisitor.visitInsn(L2F);
            } else {
                throw new CompileError("Cannot convert from " + fromType + " to " + toType + " in return statement");
            }
        } else {
            throw new CompileError("Cannot convert from " + fromType + " to " + toType + " in return statement");
        }
    }
}
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

    private static final boolean SHOW_JSON_IR = true;

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
        if (SHOW_JSON_IR) {
            ParticLogger.codeGenDebug("JSON-IR: " + action);
        }
        String type = action.get("type").getAsString();
        switch (type) {
            case "VARIABLE_DECLARATION" -> generateVariableDeclaration(action);
            case "EXPRESSION_STATEMENT" -> {
                Type expressionType = generateExpression(action.get("expression").getAsJsonObject());
                // 表达式语句执行完后，其返回值（如果有的话）必须被丢弃
                if (!expressionType.equals(Type.VOID_TYPE)) {
                    // long 和 double 占用两个槽 (POP2)
                    if (expressionType.getSize() == 2) {
                        methodVisitor.visitInsn(POP2);
                    } else { // 其他类型占用一个槽 (POP)
                        methodVisitor.visitInsn(POP);
                    }
                }
            }
            case "RETURN_STATEMENT" -> generateReturnStatement(action);
            case "IF_STATEMENT" -> generateIfStatement(action);
            case "WHILE_STATEMENT" -> generateWhileStatement(action);
            case "BLOCK_STATEMENT" -> {
                JsonArray actions = action.getAsJsonArray("actions");
                for (JsonElement element : actions) {
                    generateAction(element.getAsJsonObject());
                }
            }
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
            case "COMPARISON": return generateComparison(expr);
            case "LOGICAL_OPERATION": return generateLogicalOperation(expr);
            case "UNARY_OPERATION": return generateUnaryOperation(expr);
            case "POST_INCREMENT": return generatePostIncrement(expr);
            case "POST_DECREMENT": return generatePostDecrement(expr);
            case "ASSIGNMENT": return generateAssignment(expr);
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

    private Type generateAssignment(JsonObject expr) {
        JsonObject target = expr.getAsJsonObject("target");
        String operator = expr.get("operator").getAsString();
        JsonObject valueExpr = expr.getAsJsonObject("value");

        // 目前我们只支持对简单变量的赋值
        if (!"VARIABLE_LOAD".equals(target.get("type").getAsString())) {
            throw new CompileError("Assignment target must be a simple variable.");
        }

        String varName = target.get("varName").getAsString();
        VariableInfo varInfo = context.getLocalVariable(varName);
        if (varInfo == null) {
            throw new CompileError("Cannot assign to an undeclared variable: " + varName);
        }

        Type varType = Type.getType(varInfo.descriptor());
        int varIndex = varInfo.index();

        // 暂时只处理最简单的 "=" 赋值
        // TODO: 实现更多赋值
        if (!operator.equals("=")) {
            throw new CompileError("Compound assignment operators like '" + operator + "' are not yet supported.");
        }

        Type valueType = generateExpression(valueExpr);

        if (!valueType.equals(varType)) {
            // TODO: 实现数字提升
            throw new CompileError("Type mismatch: cannot assign " + valueType.getClassName() + " to " + varType.getClassName());
        }

        if (varType.getSize() == 2) { // long or double
            methodVisitor.visitInsn(DUP2);
        } else { // int, float, object refs, etc.
            methodVisitor.visitInsn(DUP);
        }

        int storeOpcode = varType.getOpcode(ISTORE); // ISTORE, LSTORE, FSTORE, DSTORE, ASTORE
        methodVisitor.visitVarInsn(storeOpcode, varIndex);

        return varType;
    }

    private Type generatePostIncrement(JsonObject expr) {
        JsonObject operand = expr.getAsJsonObject("operand");

        if (operand.get("type").getAsString().equals("VARIABLE_LOAD")) {
            String varName = operand.get("varName").getAsString();
            VariableInfo varInfo = context.getLocalVariable(varName);
            if (varInfo == null) {
                throw new CompileError("Undefined variable: " + varName);
            }

            Type varType = Type.getType(varInfo.descriptor());
            int varIndex = varInfo.index();

            // 加载变量值（用于返回）
            methodVisitor.visitVarInsn(varType.getOpcode(ILOAD), varIndex);

            // 加载变量值（用于计算）
            methodVisitor.visitVarInsn(varType.getOpcode(ILOAD), varIndex);

            // 加1
            if (varType.equals(Type.INT_TYPE)) {
                methodVisitor.visitInsn(ICONST_1);
                methodVisitor.visitInsn(IADD);
            } else if (varType.equals(Type.LONG_TYPE)) {
                methodVisitor.visitInsn(LCONST_1);
                methodVisitor.visitInsn(LADD);
            } else if (varType.equals(Type.FLOAT_TYPE)) {
                methodVisitor.visitInsn(FCONST_1);
                methodVisitor.visitInsn(FADD);
            } else if (varType.equals(Type.DOUBLE_TYPE)) {
                methodVisitor.visitInsn(DCONST_1);
                methodVisitor.visitInsn(DADD);
            } else {
                throw new CompileError("Cannot increment non-numeric type: " + varType);
            }

            // 存储新值
            methodVisitor.visitVarInsn(varType.getOpcode(ISTORE), varIndex);

            // 栈顶是旧值（后置递增返回旧值）
            return varType;
        }

        throw new CompileError("POST_INCREMENT only supports variables");
    }

    private Type generatePostDecrement(JsonObject expr) {
        JsonObject operand = expr.getAsJsonObject("operand");

        if (operand.get("type").getAsString().equals("VARIABLE_LOAD")) {
            String varName = operand.get("varName").getAsString();
            VariableInfo varInfo = context.getLocalVariable(varName);
            if (varInfo == null) {
                throw new CompileError("Undefined variable: " + varName);
            }

            Type varType = Type.getType(varInfo.descriptor());
            int varIndex = varInfo.index();

            // 加载变量值（用于返回）
            methodVisitor.visitVarInsn(varType.getOpcode(ILOAD), varIndex);

            // 加载变量值（用于计算）
            methodVisitor.visitVarInsn(varType.getOpcode(ILOAD), varIndex);

            // 减1
            if (varType.equals(Type.INT_TYPE)) {
                methodVisitor.visitInsn(ICONST_1);
                methodVisitor.visitInsn(ISUB);
            } else if (varType.equals(Type.LONG_TYPE)) {
                methodVisitor.visitInsn(LCONST_1);
                methodVisitor.visitInsn(LSUB);
            } else if (varType.equals(Type.FLOAT_TYPE)) {
                methodVisitor.visitInsn(FCONST_1);
                methodVisitor.visitInsn(FSUB);
            } else if (varType.equals(Type.DOUBLE_TYPE)) {
                methodVisitor.visitInsn(DCONST_1);
                methodVisitor.visitInsn(DSUB);
            } else {
                throw new CompileError("Cannot decrement non-numeric type: " + varType);
            }

            // 存储新值
            methodVisitor.visitVarInsn(varType.getOpcode(ISTORE), varIndex);

            // 栈顶是旧值（后置递减返回旧值）
            return varType;
        }

        throw new CompileError("POST_DECREMENT only supports variables");
    }

    private Type generateComparison(JsonObject expr) {
        String operator = expr.get("operator").getAsString();
        JsonObject leftExpr = expr.getAsJsonObject("left");
        JsonObject rightExpr = expr.getAsJsonObject("right");

        Type leftType = generateExpression(leftExpr);
        Type rightType = generateExpression(rightExpr);

        // 创建标签
        org.objectweb.asm.Label trueLabel = new org.objectweb.asm.Label();
        org.objectweb.asm.Label endLabel = new org.objectweb.asm.Label();

        // 根据类型选择比较指令
        if (leftType.equals(Type.INT_TYPE) && rightType.equals(Type.INT_TYPE)) {
            int opcode = switch (operator) {
                case "==" -> IF_ICMPEQ;
                case "!=" -> IF_ICMPNE;
                case "<" -> IF_ICMPLT;
                case ">" -> IF_ICMPGT;
                case "<=" -> IF_ICMPLE;
                case ">=" -> IF_ICMPGE;
                default -> throw new CompileError("Unknown comparison operator: " + operator);
            };

            methodVisitor.visitJumpInsn(opcode, trueLabel);
            methodVisitor.visitInsn(ICONST_0);  // false
            methodVisitor.visitJumpInsn(GOTO, endLabel);
            methodVisitor.visitLabel(trueLabel);
            methodVisitor.visitInsn(ICONST_1);  // true
            methodVisitor.visitLabel(endLabel);
        } else if (leftType.equals(Type.LONG_TYPE) || rightType.equals(Type.LONG_TYPE)) {
            // long类型比较
            methodVisitor.visitInsn(LCMP);
            int opcode = switch (operator) {
                case "==" -> IFEQ;
                case "!=" -> IFNE;
                case "<" -> IFLT;
                case ">" -> IFGT;
                case "<=" -> IFLE;
                case ">=" -> IFGE;
                default -> throw new CompileError("Unknown comparison operator: " + operator);
            };

            methodVisitor.visitJumpInsn(opcode, trueLabel);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitJumpInsn(GOTO, endLabel);
            methodVisitor.visitLabel(trueLabel);
            methodVisitor.visitInsn(ICONST_1);
            methodVisitor.visitLabel(endLabel);
        } else if (leftType.equals(Type.FLOAT_TYPE) || rightType.equals(Type.FLOAT_TYPE)) {
            // float类型比较
            methodVisitor.visitInsn(FCMPG);
            int opcode = switch (operator) {
                case "==" -> IFEQ;
                case "!=" -> IFNE;
                case "<" -> IFLT;
                case ">" -> IFGT;
                case "<=" -> IFLE;
                case ">=" -> IFGE;
                default -> throw new CompileError("Unknown comparison operator: " + operator);
            };

            methodVisitor.visitJumpInsn(opcode, trueLabel);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitJumpInsn(GOTO, endLabel);
            methodVisitor.visitLabel(trueLabel);
            methodVisitor.visitInsn(ICONST_1);
            methodVisitor.visitLabel(endLabel);
        } else if (leftType.equals(Type.DOUBLE_TYPE) || rightType.equals(Type.DOUBLE_TYPE)) {
            // double类型比较
            methodVisitor.visitInsn(DCMPG);
            int opcode = switch (operator) {
                case "==" -> IFEQ;
                case "!=" -> IFNE;
                case "<" -> IFLT;
                case ">" -> IFGT;
                case "<=" -> IFLE;
                case ">=" -> IFGE;
                default -> throw new CompileError("Unknown comparison operator: " + operator);
            };

            methodVisitor.visitJumpInsn(opcode, trueLabel);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitJumpInsn(GOTO, endLabel);
            methodVisitor.visitLabel(trueLabel);
            methodVisitor.visitInsn(ICONST_1);
            methodVisitor.visitLabel(endLabel);
        } else {
            // 引用类型比较
            if (operator.equals("==")) {
                methodVisitor.visitJumpInsn(IF_ACMPEQ, trueLabel);
            } else if (operator.equals("!=")) {
                methodVisitor.visitJumpInsn(IF_ACMPNE, trueLabel);
            } else {
                throw new CompileError("Operator " + operator + " not supported for reference types");
            }
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitJumpInsn(GOTO, endLabel);
            methodVisitor.visitLabel(trueLabel);
            methodVisitor.visitInsn(ICONST_1);
            methodVisitor.visitLabel(endLabel);
        }

        return Type.BOOLEAN_TYPE;
    }

    private Type generateLogicalOperation(JsonObject expr) {
        String operator = expr.get("operator").getAsString();
        JsonObject leftExpr = expr.getAsJsonObject("left");
        JsonObject rightExpr = expr.getAsJsonObject("right");

        if (operator.equals("&&")) {
            // 短路AND：如果left为false，直接返回false
            org.objectweb.asm.Label falseLabel = new org.objectweb.asm.Label();
            org.objectweb.asm.Label endLabel = new org.objectweb.asm.Label();

            generateExpression(leftExpr);
            methodVisitor.visitJumpInsn(IFEQ, falseLabel);  // left == false

            generateExpression(rightExpr);
            methodVisitor.visitJumpInsn(GOTO, endLabel);

            methodVisitor.visitLabel(falseLabel);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitLabel(endLabel);

        } else if (operator.equals("||")) {
            // 短路OR：如果left为true，直接返回true
            org.objectweb.asm.Label trueLabel = new org.objectweb.asm.Label();
            org.objectweb.asm.Label endLabel = new org.objectweb.asm.Label();

            generateExpression(leftExpr);
            methodVisitor.visitJumpInsn(IFNE, trueLabel);  // left == true

            generateExpression(rightExpr);
            methodVisitor.visitJumpInsn(GOTO, endLabel);

            methodVisitor.visitLabel(trueLabel);
            methodVisitor.visitInsn(ICONST_1);
            methodVisitor.visitLabel(endLabel);
        } else {
            throw new CompileError("Unknown logical operator: " + operator);
        }

        return Type.BOOLEAN_TYPE;
    }

    private Type generateUnaryOperation(JsonObject expr) {
        String operator = expr.get("operator").getAsString();
        JsonObject operand = expr.getAsJsonObject("operand");

        switch (operator) {
            case "!" -> {
                // 逻辑非
                generateExpression(operand);

                org.objectweb.asm.Label trueLabel = new org.objectweb.asm.Label();
                org.objectweb.asm.Label endLabel = new org.objectweb.asm.Label();

                methodVisitor.visitJumpInsn(IFEQ, trueLabel);  // 如果为0(false)，跳到true

                methodVisitor.visitInsn(ICONST_0);  // 否则返回false

                methodVisitor.visitJumpInsn(GOTO, endLabel);
                methodVisitor.visitLabel(trueLabel);
                methodVisitor.visitInsn(ICONST_1);  // 返回true

                methodVisitor.visitLabel(endLabel);

                return Type.BOOLEAN_TYPE;
            }
            case "-" -> {
                // 数值取负
                Type operandType = generateExpression(operand);
                int opcode = operandType.getOpcode(INEG);
                methodVisitor.visitInsn(opcode);
                return operandType;
            }
            case "+" -> {
                // 数值取正（什么都不做）
                return generateExpression(operand);
                // 数值取正（什么都不做）
            }
            default -> throw new CompileError("Unsupported unary operator: " + operator);
        }
    }

    private void generateWhileStatement(JsonObject action) {
        JsonObject condition = action.getAsJsonObject("condition");
        JsonObject body = action.getAsJsonObject("body");

        // 创建标签
        org.objectweb.asm.Label startLabel = new org.objectweb.asm.Label();
        org.objectweb.asm.Label endLabel = new org.objectweb.asm.Label();

        // 开始循环：startLabel
        methodVisitor.visitLabel(startLabel);

        // 添加Frame信息（告诉ASM这里的栈帧状态）
        // F_SAME 表示栈帧与前一个相同
        methodVisitor.visitFrame(org.objectweb.asm.Opcodes.F_SAME, 0, null, 0, null);

        // 生成条件表达式
        generateExpression(condition);

        // 如果条件为假（0），跳出循环
        methodVisitor.visitJumpInsn(IFEQ, endLabel);

        // 生成循环体
        generateAction(body);

        // 跳回到循环开始
        methodVisitor.visitJumpInsn(GOTO, startLabel);

        // 循环结束标签
        methodVisitor.visitLabel(endLabel);

        // 添加Frame信息
        methodVisitor.visitFrame(org.objectweb.asm.Opcodes.F_SAME, 0, null, 0, null);
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

    private void generateIfStatement(JsonObject action) {
        JsonObject condition = action.getAsJsonObject("condition");
        JsonObject thenBranch = action.getAsJsonObject("thenBranch");
        JsonObject elseBranch = action.has("elseBranch") ? action.getAsJsonObject("elseBranch") : null;

        // 生成条件表达式的字节码
        generateExpression(condition);

        // 创建标签
        org.objectweb.asm.Label elseLabel = new org.objectweb.asm.Label();
        org.objectweb.asm.Label endLabel = new org.objectweb.asm.Label();

        // 如果条件为假，跳转到else标签
        methodVisitor.visitJumpInsn(IFEQ, elseBranch != null ? elseLabel : endLabel);

        // 生成then分支
        generateAction(thenBranch);

        // 如果有else分支，跳过它
        if (elseBranch != null) {
            methodVisitor.visitJumpInsn(GOTO, endLabel);

            // else分支
            methodVisitor.visitLabel(elseLabel);
            methodVisitor.visitFrame(org.objectweb.asm.Opcodes.F_SAME, 0, null, 0, null);
            generateAction(elseBranch);
        }

        // 结束标签
        methodVisitor.visitLabel(endLabel);
        methodVisitor.visitFrame(org.objectweb.asm.Opcodes.F_SAME, 0, null, 0, null);
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
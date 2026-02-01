package lang.partic.compiler.compile.expressions;

import lang.partic.compiler.context.CompileContext;
import lang.partic.compiler.exceptions.CompileError;
import lang.partic.compiler.ir.ParticMethodBody;
import lang.partic.compiler.ir.ParticMethodBody.LocalVar;
import lang.partic.compiler.ir.ParticMethodBody.TempVar;
import lang.partic.compiler.utils.TypeUtils;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 表达式编译器
 * 将 TempVar 编译为字节码
 */
public class ExpressionCompiler {
    private static final Logger log = LoggerFactory.getLogger(ExpressionCompiler.class);
    
    private final TempVar temp;
    private final ParticMethodBody methodBody;
    private final CompileContext context;
    private final MethodVisitor mv;

    public ExpressionCompiler(TempVar temp, ParticMethodBody methodBody, CompileContext context) {
        this.temp = temp;
        this.methodBody = methodBody;
        this.context = context;
        this.mv = context.getMv();
    }

    /**
     * 编译表达式，生成字节码
     */
    public void compile() {
        String op = temp.getOp();
        log.debug("编译表达式: op={}, type={}", op, temp.getType());

        switch (op) {
            case "const" -> compileConst();
            case "load", "load_local" -> compileLoad(); // load_local 和 load 使用相同的编译逻辑
            case "load_class" -> compileLoadClass(); // 类名引用，不生成字节码
            case "add" -> compileBinaryArithmetic(Opcodes.IADD, Opcodes.LADD, Opcodes.FADD, Opcodes.DADD);
            case "sub" -> compileBinaryArithmetic(Opcodes.ISUB, Opcodes.LSUB, Opcodes.FSUB, Opcodes.DSUB);
            case "mul" -> compileBinaryArithmetic(Opcodes.IMUL, Opcodes.LMUL, Opcodes.FMUL, Opcodes.DMUL);
            case "div" -> compileBinaryArithmetic(Opcodes.IDIV, Opcodes.LDIV, Opcodes.FDIV, Opcodes.DDIV);
            case "mod" -> compileBinaryArithmetic(Opcodes.IREM, Opcodes.LREM, Opcodes.FREM, Opcodes.DREM);
            case "eq" -> compileComparison(Opcodes.IF_ICMPEQ, Opcodes.IF_ACMPEQ);
            case "ne" -> compileComparison(Opcodes.IF_ICMPNE, Opcodes.IF_ACMPNE);
            case "lt" -> compileComparison(Opcodes.IF_ICMPLT, null);
            case "le" -> compileComparison(Opcodes.IF_ICMPLE, null);
            case "gt" -> compileComparison(Opcodes.IF_ICMPGT, null);
            case "ge" -> compileComparison(Opcodes.IF_ICMPGE, null);
            case "and" -> compileLogical(Opcodes.IAND);
            case "or" -> compileLogical(Opcodes.IOR);
            case "xor" -> compileLogical(Opcodes.IXOR);
            case "shl" -> compileShift(Opcodes.ISHL, Opcodes.LSHL);
            case "shr" -> compileShift(Opcodes.ISHR, Opcodes.LSHR);
            case "ushr" -> compileShift(Opcodes.IUSHR, Opcodes.LUSHR);
            case "pos" -> compileUnaryPos();
            case "neg" -> compileUnaryNeg();
            case "not" -> compileUnaryNot();
            case "bitnot" -> compileUnaryBitNot();
            case "preinc", "predec" -> compilePreIncDec();
            case "postinc", "postdec" -> compilePostIncDec();
            case "cast" -> compileCast();
            case "field_access" -> compileFieldAccess();
            case "call_method" -> compileMethodCall();
            case "array_access" -> compileArrayAccess();
            case "ternary" -> compileTernary();
            default -> {
                log.warn("未实现的表达式操作: {}", op);
            }
        }
    }

    /**
     * 编译常量
     */
    private void compileConst() {
        Object value = temp.getValue();
        log.debug("编译常量: value={}, type={}", value, value != null ? value.getClass() : "null");

        switch (value) {
            case null -> {
                // null 字面量
                mv.visitInsn(Opcodes.ACONST_NULL);
                return;
            }
            case Boolean b -> {
                // bool 类型
                mv.visitInsn(b ? Opcodes.ICONST_1 : Opcodes.ICONST_0);
                return;
            }
            case Integer i -> {
                // int 类型
                if (i >= -1 && i <= 5) {
                    // ICONST_M1 (3), ICONST_0 (3), ICONST_1 (4), ICONST_2 (5), ICONST_3 (6), ICONST_4 (7), ICONST_5 (8)
                    int opcode = switch (i) {
                        case -1 -> Opcodes.ICONST_M1;
                        case 0 -> Opcodes.ICONST_0;
                        case 1 -> Opcodes.ICONST_1;
                        case 2 -> Opcodes.ICONST_2;
                        case 3 -> Opcodes.ICONST_3;
                        case 4 -> Opcodes.ICONST_4;
                        case 5 -> Opcodes.ICONST_5;
                        default -> -1; // 不应该到达这里
                    };
                    mv.visitInsn(opcode);
                } else if (i >= Byte.MIN_VALUE && i <= Byte.MAX_VALUE) {
                    mv.visitIntInsn(Opcodes.BIPUSH, i);
                } else if (i >= Short.MIN_VALUE && i <= Short.MAX_VALUE) {
                    mv.visitIntInsn(Opcodes.SIPUSH, i);
                } else {
                    mv.visitLdcInsn(i);
                }
                return;
            }
            case Long l -> {
                if (l == 0L || l == 1L) {
                    mv.visitInsn(l == 0L ? Opcodes.LCONST_0 : Opcodes.LCONST_1);
                } else {
                    mv.visitLdcInsn(l);
                }
                return;
            }
            case Float f -> {
                if (f == 0.0f || f == 1.0f || f == 2.0f) {
                    mv.visitInsn(f == 0.0f ? Opcodes.FCONST_0 : (f == 1.0f ? Opcodes.FCONST_1 : Opcodes.FCONST_2));
                } else {
                    mv.visitLdcInsn(f);
                }
                return;
            }
            case Double d -> {
                if (d == 0.0 || d == 1.0) {
                    mv.visitInsn(d == 0.0 ? Opcodes.DCONST_0 : Opcodes.DCONST_1);
                } else {
                    mv.visitLdcInsn(d);
                }
                return;
            }
            case Character c -> {
                int code = c;
                if (code <= 5) {
                    mv.visitInsn(Opcodes.ICONST_0 + code + 1);
                } else {
                    mv.visitIntInsn(Opcodes.BIPUSH, code);
                }
                return;
            }
            case String s -> {
                mv.visitLdcInsn(value);
                return;
            }
            default -> {
            }
        }

        log.warn("未知的常量类型: {}", value.getClass());
    }

    /**
     * 编译变量加载
     */
    private void compileLoad() {
        List<String> operands = temp.getOperands();
        if (operands.isEmpty()) {
            log.warn("load 操作没有操作数");
            return;
        }

        String varName = operands.getFirst();
        LocalVar local = methodBody.getLocals().get(varName);
        
        if (local == null) {
            log.warn("找不到局部变量: {}", varName);
            return;
        }

        int index = context.getIndexManager().getIndex(varName);
        String type = local.getType();
        
        // 根据类型选择加载指令
        int opcode = getLoadOpcode(type);
        mv.visitVarInsn(opcode, index);
    }

    /**
     * 编译二元算术运算
     */
    private void compileBinaryArithmetic(int iOp, int lOp, int fOp, int dOp) {
        List<String> operands = temp.getOperands();
        if (operands.size() < 2) {
            log.warn("二元运算操作数不足");
            return;
        }

        String leftType = getOperandType(operands.getFirst());
        String rightType = getOperandType(operands.get(1));
        String type = temp.getType();
        log.debug("二元算术运算: leftType={}, rightType={}, resultType={}", leftType, rightType, type);

        // 先编译左操作数
        compileOperand(operands.getFirst());
        // 如果左操作数类型与结果类型不同，需要类型转换
        if (leftType != null && !leftType.equals(type)) {
            int castOp = getCastOpcode(leftType, type);
            if (castOp != 0) {
                mv.visitInsn(castOp);
            } else {
                throw new CompileError(
                    "无法将类型 '" + leftType + "' 转换为 '" + type + "'");
            }
        }

        // 再编译右操作数
        compileOperand(operands.get(1));
        // 如果右操作数类型与结果类型不同，需要类型转换
        if (rightType != null && !rightType.equals(type)) {
            int castOp = getCastOpcode(rightType, type);
            if (castOp != 0) {
                mv.visitInsn(castOp);
            } else {
                throw new CompileError(
                    "无法将类型 '" + rightType + "' 转换为 '" + type + "'");
            }
        }

        // 根据类型选择指令
        int opcode = switch (type) {
            case "int" -> iOp;
            case "long" -> lOp;
            case "float" -> fOp;
            case "double" -> dOp;
            default -> {
                log.warn("不支持的算术运算类型: {}", type);
                yield iOp;
            }
        };
        mv.visitInsn(opcode);
    }

    /**
     * 编译比较运算（生成 bool 结果）
     */
    private void compileComparison(int intOp, Integer refOp) {
        List<String> operands = temp.getOperands();
        if (operands.size() < 2) {
            log.warn("比较运算操作数不足");
            return;
        }

        compileOperand(operands.getFirst());
        compileOperand(operands.get(1));

        String leftType = getOperandType(operands.getFirst());
        String rightType = getOperandType(operands.get(1));

        // 处理浮点数比较
        if (TypeUtils.isFloatingPoint(leftType) || TypeUtils.isFloatingPoint(rightType)) {
            compileFloatingComparison();
            return;
        }

        // 处理引用类型比较
        if (!TypeUtils.isPrimitive(leftType) && !TypeUtils.isPrimitive(rightType)) {
            if (refOp != null) {
                // 使用引用比较指令
                mv.visitJumpInsn(refOp, null); // TODO: 需要标签
                mv.visitInsn(Opcodes.ICONST_1);
                // TODO: 跳转到结束
                mv.visitInsn(Opcodes.ICONST_0);
            } else {
                log.warn("不支持引用类型的比较: {}", temp.getOp());
            }
            return;
        }

        // 整数比较：使用 IF_ICMP* 指令
        // 但我们需要生成 bool 值，所以需要更复杂的逻辑
        // 简化版：使用 IF_ICMPEQ/IF_ICMPNE + ICONST
        // TODO: 完整实现需要标签
        log.debug("比较运算需要标签支持，暂时跳过");
    }

    /**
     * 编译浮点数比较
     */
    private void compileFloatingComparison() {
        String type = getOperandType(temp.getOperands().getFirst());
        int cmpOp = "float".equals(type) ? Opcodes.FCMPL : Opcodes.DCMPL;
        mv.visitInsn(cmpOp);
        
        // FCMPL/DCMPL 将结果压栈：-1, 0, 1
        // 需要根据比较操作转换为 bool
        // TODO: 需要标签支持
        log.debug("浮点数比较需要标签支持，暂时跳过");
    }

    /**
     * 编译逻辑运算
     */
    private void compileLogical(int opcode) {
        List<String> operands = temp.getOperands();
        if (operands.size() < 2) {
            log.warn("逻辑运算操作数不足");
            return;
        }

        compileOperand(operands.getFirst());
        compileOperand(operands.get(1));
        mv.visitInsn(opcode);
    }

    /**
     * 编译位移运算
     */
    private void compileShift(int intOp, int longOp) {
        List<String> operands = temp.getOperands();
        if (operands.size() < 2) {
            log.warn("位移运算操作数不足");
            return;
        }

        compileOperand(operands.getFirst());
        compileOperand(operands.get(1));

        String type = temp.getType();
        int opcode = type.equals("long") ? longOp : intOp;
        mv.visitInsn(opcode);
    }

    /**
     * 编译一元正号（通常不需要操作）
     */
    private void compileUnaryPos() {
        compileOperand(temp.getOperands().getFirst());
        // 正号通常不需要操作，但可能需要类型提升
    }

    /**
     * 编译一元负号
     */
    private void compileUnaryNeg() {
        compileOperand(temp.getOperands().getFirst());
        String type = temp.getType();
        int opcode = switch (type) {
            case "int" -> Opcodes.INEG;
            case "long" -> Opcodes.LNEG;
            case "float" -> Opcodes.FNEG;
            case "double" -> Opcodes.DNEG;
            default -> Opcodes.INEG;
        };
        mv.visitInsn(opcode);
    }

    /**
     * 编译逻辑非
     */
    private void compileUnaryNot() {
        compileOperand(temp.getOperands().getFirst());
        // 逻辑非：0 -> 1, 非0 -> 0
        // 使用 IFEQ + ICONST
        // TODO: 需要标签支持
        log.debug("逻辑非需要标签支持，暂时跳过");
    }

    /**
     * 编译按位取反
     */
    private void compileUnaryBitNot() {
        compileOperand(temp.getOperands().getFirst());
        String type = temp.getType();
        int opcode = type.equals("long") ? Opcodes.LXOR : Opcodes.IXOR;
        // 按位取反 = XOR -1
        if (type.equals("long")) {
            mv.visitLdcInsn(-1L);
        } else {
            mv.visitInsn(Opcodes.ICONST_M1);
        }
        mv.visitInsn(opcode);
    }

    /**
     * 编译前自增/自减
     */
    private void compilePreIncDec() {
        // TODO: 需要实现
        log.debug("前自增/自减暂未实现");
    }

    /**
     * 编译后自增/自减
     */
    private void compilePostIncDec() {
        // TODO: 需要实现
        log.debug("后自增/自减暂未实现");
    }

    /**
     * 编译类型转换
     */
    private void compileCast() {
        compileOperand(temp.getOperands().getFirst());
        String fromType = getOperandType(temp.getOperands().getFirst());
        String toType = (String) temp.getMetadata().get("target_type");
        
        if (toType == null) {
            toType = temp.getType();
        }

        int castOp = getCastOpcode(fromType, toType);
        if (castOp != 0) {
            mv.visitInsn(castOp);
        } else if (fromType != null && toType != null && !fromType.equals(toType)) {
            // 类型不同但无法转换，抛出错误
            throw new CompileError(
                "无法将类型 '" + fromType + "' 转换为 '" + toType + "'");
        }
        // 如果类型相同，不需要转换
    }

    /**
     * 编译类名引用（不生成字节码，仅用于类型信息）
     */
    private void compileLoadClass() {
        // 类名引用不生成字节码，因为类名本身不是值
        // 它只用于类型信息，在字段访问或方法调用时使用
        log.debug("类名引用，不生成字节码");
    }

    /**
     * 编译字段访问
     */
    private void compileFieldAccess() {
        Map<String, Object> metadata = temp.getMetadata();
        String object = (String) metadata.get("object");
        String objectType = (String) metadata.get("object_type");
        String field = (String) metadata.get("field");
        Boolean isStatic = (Boolean) metadata.get("is_static");
        
        if (isStatic == null) isStatic = false;

        // 如果 objectType 为 null，尝试从 object 操作数获取类型
        if (objectType == null && object != null) {
            objectType = getOperandType(object);
        }

        if (objectType == null) {
            log.error("字段访问无法确定对象类型: field={}, object={}", field, object);
            return;
        }

        String fieldType = temp.getType();
        if (fieldType == null) {
            log.error("字段访问无法确定字段类型: field={}", field);
            return;
        }

        String descriptor = TypeUtils.toDescriptor(fieldType);
        String owner = TypeUtils.toInternalName(objectType);

        if (isStatic) {
            mv.visitFieldInsn(Opcodes.GETSTATIC, owner, field, descriptor);
        } else {
            // 先加载对象
            if (object != null) {
                compileOperand(object);
            }
            mv.visitFieldInsn(Opcodes.GETFIELD, owner, field, descriptor);
        }
    }

    /**
     * 编译方法调用
     */
    private void compileMethodCall() {
        Map<String, Object> metadata = temp.getMetadata();
        String objectType = (String) metadata.get("object_type");
        String method = (String) metadata.get("method");
        Boolean isStatic = (Boolean) metadata.get("is_static");
        @SuppressWarnings("unchecked")
        List<String> argTypes = (List<String>) metadata.get("arg_types");
        
        if (isStatic == null) isStatic = false;

        // 如果 objectType 为 null，尝试从 object 操作数获取类型
        if (objectType == null) {
            String object = (String) metadata.get("object");
            if (object != null) {
                objectType = getOperandType(object);
                log.debug("从 object 操作数获取类型: object={}, type={}", object, objectType);
            }
        }

        // 如果 objectType 仍为 null，可能是局部方法调用（当前类的方法）
        if (objectType == null && method != null && !method.isEmpty()) {
            // 从当前类获取类名
            if (context.getCurrentClass() != null) {
                objectType = context.getCurrentClass().getName();
                log.debug("局部方法调用: method={}, class={}", method, objectType);
            }
        }

        if (objectType == null) {
            log.error("方法调用无法确定对象类型: method={}, metadata={}", method, metadata);
            return;
        }
        
        // 判断是否为局部方法调用（objectType 从当前类获取，且原始 metadata 中 object_type 为 null）
        boolean isLocalMethodCall = metadata.get("object_type") == null && context.getCurrentClass() != null && objectType.equals(context.getCurrentClass().getName());
        
        // 对于非静态方法，需要先加载对象
        if (!isStatic) {
            if (isLocalMethodCall) {
                // 局部方法调用：非静态方法需要 this
                // 检查当前方法是否为静态方法
                if (context.getCurrentMethod() != null) {
                    boolean currentMethodIsStatic = context.getCurrentMethod().getModifiers().getOthers().contains("static");
                    if (!currentMethodIsStatic) {
                        // 加载 this
                        mv.visitVarInsn(Opcodes.ALOAD, 0);
                    }
                }
            } else {
                // 实例方法调用：加载对象
                String object = (String) metadata.get("object");
                if (object != null) {
                    compileOperand(object);
                }
            }
        }

        // 编译参数（从左到右）
        List<String> operands = temp.getOperands();
        for (String arg : operands) {
            compileOperand(arg);
        }

        // 构建方法描述符
        StringBuilder desc = new StringBuilder("(");
        if (argTypes != null) {
            for (String argType : argTypes) {
                desc.append(TypeUtils.toDescriptor(argType));
            }
        }
        desc.append(")");
        desc.append(TypeUtils.toDescriptor(temp.getType()));

        String owner = TypeUtils.toInternalName(objectType);
        int opcode = isStatic ? Opcodes.INVOKESTATIC : Opcodes.INVOKEVIRTUAL;

        mv.visitMethodInsn(opcode, owner, method, desc.toString(), false);
    }

    /**
     * 编译数组访问
     */
    private void compileArrayAccess() {
        List<String> operands = temp.getOperands();
        if (operands.size() < 2) {
            log.warn("数组访问操作数不足");
            return;
        }

        compileOperand(operands.getFirst()); // 数组
        compileOperand(operands.get(1)); // 索引

        String elementType = temp.getType();
        int opcode = getArrayLoadOpcode(elementType);
        mv.visitInsn(opcode);
    }

    /**
     * 编译三元运算符
     */
    private void compileTernary() {
        // TODO: 需要标签支持
        log.debug("三元运算符需要标签支持，暂时跳过");
    }

    // ============ 辅助方法 ============

    /**
     * 编译操作数（递归编译依赖的 TempVar）
     */
    private void compileOperand(String operandName) {
        TempVar operand = methodBody.getTemps().get(operandName);
        if (operand != null) {
            // 递归编译
            new ExpressionCompiler(operand, methodBody, context).compile();
        } else {
            // 可能是局部变量，直接加载
            LocalVar local = methodBody.getLocals().get(operandName);
            if (local != null) {
                int opcode = getLoadOpcode(local.getType());
                int index = context.getIndexManager().getIndex(operandName);
                mv.visitVarInsn(opcode, index);
            } else {
                log.warn("找不到操作数: {}", operandName);
            }
        }
    }

    /**
     * 获取操作数的类型
     */
    private String getOperandType(String operandName) {
        TempVar temp = methodBody.getTemps().get(operandName);
        if (temp != null) {
            return temp.getType();
        }
        LocalVar local = methodBody.getLocals().get(operandName);
        if (local != null) {
            return local.getType();
        }
        return null;
    }

    /**
     * 获取加载指令的操作码
     */
    private int getLoadOpcode(String type) {
        return switch (type) {
            case "bool", "byte", "char", "short", "int" -> Opcodes.ILOAD;
            case "long" -> Opcodes.LLOAD;
            case "float" -> Opcodes.FLOAD;
            case "double" -> Opcodes.DLOAD;
            default -> Opcodes.ALOAD;
        };
    }

    /**
     * 获取存储指令的操作码
     */
    private int getStoreOpcode(String type) {
        return switch (type) {
            case "bool", "byte", "char", "short", "int" -> Opcodes.ISTORE;
            case "long" -> Opcodes.LSTORE;
            case "float" -> Opcodes.FSTORE;
            case "double" -> Opcodes.DSTORE;
            default -> Opcodes.ASTORE;
        };
    }

    /**
     * 获取数组加载指令的操作码
     */
    private int getArrayLoadOpcode(String elementType) {
        return switch (elementType) {
            case "bool", "byte" -> Opcodes.BALOAD;
            case "char" -> Opcodes.CALOAD;
            case "short" -> Opcodes.SALOAD;
            case "int" -> Opcodes.IALOAD;
            case "long" -> Opcodes.LALOAD;
            case "float" -> Opcodes.FALOAD;
            case "double" -> Opcodes.DALOAD;
            default -> Opcodes.AALOAD;
        };
    }

    /**
     * 获取类型转换指令的操作码
     */
    private int getCastOpcode(String fromType, String toType) {
        if (fromType == null || toType == null) return 0;
        if (fromType.equals(toType)) return 0;

        // 数值类型转换
        return switch (fromType + "->" + toType) {
            case "int->long" -> Opcodes.I2L;
            case "int->float" -> Opcodes.I2F;
            case "int->double" -> Opcodes.I2D;
            case "long->int" -> Opcodes.L2I;
            case "long->float" -> Opcodes.L2F;
            case "long->double" -> Opcodes.L2D;
            case "float->int" -> Opcodes.F2I;
            case "float->long" -> Opcodes.F2L;
            case "float->double" -> Opcodes.F2D;
            case "double->int" -> Opcodes.D2I;
            case "double->long" -> Opcodes.D2L;
            case "double->float" -> Opcodes.D2F;
            case "int->byte" -> Opcodes.I2B;
            case "int->char" -> Opcodes.I2C;
            case "int->short" -> Opcodes.I2S;
            default -> 0; // 不需要转换或引用类型转换
        };
    }
}

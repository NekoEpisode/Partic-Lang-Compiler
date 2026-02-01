package lang.partic.compiler.compile.statements;

import lang.partic.compiler.compile.expressions.ExpressionCompiler;
import lang.partic.compiler.context.CompileContext;
import lang.partic.compiler.ir.ParticMethodBody;
import lang.partic.compiler.ir.ParticMethodBody.*;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatementCompiler {
    private static final Logger log = LoggerFactory.getLogger(StatementCompiler.class);
    private final Statement statement;
    private final ParticMethodBody body;
    private final CompileContext context;

    public StatementCompiler(Statement statement, ParticMethodBody body, CompileContext context) {
        this.statement = statement;
        this.body = body;
        this.context = context;
    }

    public void compile() {
        log.debug("编译 {} 的方法 {} 的方法体的语句: {}", 
            context.getCurrentClass().getName(), 
            context.getCurrentMethod().getName(), 
            statement.getOp());
            
        switch (statement.getOp()) {
            case "assign" -> compileAssign((AssignStatement) statement);
            case "return" -> compileReturn((ReturnStatement) statement);
            case "expression" -> compileExpression((ExpressionStatement) statement);
            case "scope_enter" -> compileScopeEnter((ScopeStatement) statement);
            case "scope_exit" -> compileScopeExit((ScopeStatement) statement);
            default -> log.warn("未知的语句类型: {}", statement.getOp());
        }
    }

    private void compileAssign(AssignStatement stmt) {
        String target = stmt.getTarget();
        String value = stmt.getValue();
        
        log.debug("  赋值: {} = {}", target, value);
        
        // 编译右侧表达式
        TempVar valueTemp = body.getTemps().get(value);
        if (valueTemp != null) {
            new ExpressionCompiler(valueTemp, body, context).compile();
        } else {
            // 可能是局部变量，直接加载
            LocalVar local = body.getLocals().get(value);
            if (local != null) {
                int loadOp = getLoadOpcode(local.getType());
                int index = context.getIndexManager().getIndex(value);
                context.getMv().visitVarInsn(loadOp, index);
            } else {
                log.warn("  找不到赋值右侧的值: {}", value);
                return;
            }
        }
        
        // 存储到目标变量
        LocalVar targetLocal = body.getLocals().get(target);
        if (targetLocal != null) {
            String targetType = targetLocal.getType();
            int storeOp = getStoreOpcode(targetType);
            int index = context.getIndexManager().getIndex(target);
            context.getMv().visitVarInsn(storeOp, index);
        } else {
            log.warn("  找不到赋值目标变量: {}", target);
        }
    }

    private void compileReturn(ReturnStatement stmt) {
        MethodVisitor mv = context.getMv();
        String returnType = context.getCurrentMethod().getReturnType();
        
        if (stmt.getValue() != null) {
            // 有返回值的 return
            String value = stmt.getValue();
            String valueType = null;
            
            // 编译返回值表达式
            TempVar valueTemp = body.getTemps().get(value);
            if (valueTemp != null) {
                valueType = valueTemp.getType();
                new ExpressionCompiler(valueTemp, body, context).compile();
            } else {
                // 可能是局部变量
                LocalVar local = body.getLocals().get(value);
                if (local != null) {
                    valueType = local.getType();
                    int loadOp = getLoadOpcode(local.getType());
                    int index = context.getIndexManager().getIndex(value);
                    mv.visitVarInsn(loadOp, index);
                } else {
                    log.warn("  找不到返回值: {}", value);
                    return;
                }
            }
            
            // 如果返回值类型与方法返回类型不同，需要类型转换
            if (valueType != null && returnType != null && !valueType.equals(returnType)) {
                int castOp = getCastOpcode(valueType, returnType);
                if (castOp != 0) {
                    mv.visitInsn(castOp);
                } else {
                    log.warn("  无法将返回值类型 '{}' 转换为方法返回类型 '{}'", valueType, returnType);
                }
            }
            
            // 生成返回指令
            int returnOp = getReturnOpcode(returnType);
            mv.visitInsn(returnOp);
        } else {
            // void return
            mv.visitInsn(Opcodes.RETURN);
        }
    }

    private void compileExpression(ExpressionStatement stmt) {
        String tempName = stmt.getTempName();
        TempVar temp = body.getTemps().get(tempName);
        
        if (temp == null) {
            log.warn("  表达式语句引用了不存在的临时变量: {}", tempName);
            return;
        }
        
        log.debug("  表达式: {} (op={}, type={})", tempName, temp.getOp(), temp.getType());
        
        // 使用 ExpressionCompiler 编译表达式
        new ExpressionCompiler(temp, body, context).compile();
        
        // 如果表达式有返回值但不需要使用，需要弹出栈顶值
        // 但方法调用等 void 方法不需要弹出
        String type = temp.getType();
        if (type != null && !type.equals("void")) {
            // 弹出栈顶值（根据类型选择 POP 或 POP2）
            if (type.equals("long") || type.equals("double")) {
                context.getMv().visitInsn(Opcodes.POP2);
            } else {
                context.getMv().visitInsn(Opcodes.POP);
            }
        }
    }

    private void compileScopeEnter(ScopeStatement stmt) {
        // 作用域进入通常不需要特殊处理（局部变量索引已分配）
        log.debug("  进入作用域: {}", stmt.getScopeId());
    }

    private void compileScopeExit(ScopeStatement stmt) {
        // 作用域退出通常不需要特殊处理
        log.debug("  退出作用域: {}", stmt.getScopeId());
    }

    // ============ 辅助方法 ============

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
     * 获取返回指令的操作码
     */
    private int getReturnOpcode(String returnType) {
        if (returnType == null || returnType.equals("void")) {
            return Opcodes.RETURN;
        }
        return switch (returnType) {
            case "bool", "byte", "char", "short", "int" -> Opcodes.IRETURN;
            case "long" -> Opcodes.LRETURN;
            case "float" -> Opcodes.FRETURN;
            case "double" -> Opcodes.DRETURN;
            default -> Opcodes.ARETURN;
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

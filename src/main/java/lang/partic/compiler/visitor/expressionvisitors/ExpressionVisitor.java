package lang.partic.compiler.visitor.expressionvisitors;

import lang.partic.compiler.antlr.ParticBaseVisitor;
import lang.partic.compiler.antlr.ParticParser;
import lang.partic.compiler.context.VisitContext;
import lang.partic.compiler.exceptions.CompileError;
import lang.partic.compiler.ir.ParticMethodBody;
import lang.partic.compiler.ir.ParticMethodBody.TempVar;
import lang.partic.compiler.symbol.*;
import lang.partic.compiler.utils.TypeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 表达式访问器（带类型推断）
 * 
 * 返回临时变量名（String），表示表达式的计算结果
 * 每个 TempVar 都会记录其结果类型
 */
public class ExpressionVisitor extends ParticBaseVisitor<String> {
    private final ParticMethodBody body;
    private final VisitContext context;

    public ExpressionVisitor(ParticMethodBody body, VisitContext context) {
        this.body = body;
        this.context = context;
    }

    /**
     * 获取临时变量或局部变量的类型
     */
    private String getType(String name) {
        if (name == null) return null;
        
        // 先查临时变量
        TempVar temp = body.getTemps().get(name);
        if (temp != null) {
            return temp.getType();
        }
        
        // 再查局部变量
        ParticMethodBody.LocalVar local = body.getLocals().get(name);
        if (local != null) {
            return local.getType();
        }
        
        return null;
    }

    @Override
    public String visitExpression(ParticParser.ExpressionContext ctx) {
        return visit(ctx.assignmentExpression());
    }

    @Override
    public String visitAssignmentExpression(ParticParser.AssignmentExpressionContext ctx) {
        if (ctx.assignmentOperator() != null) {
            // 赋值表达式: a = b, a += b, etc.
            String target = visit(ctx.postfixExpression());
            String value = visit(ctx.expression());
            
            TempVar temp = new TempVar("assign", getType(target));
            temp.addOperand(target);
            temp.addOperand(value);
            return body.addTemp(temp);
        }
        return visit(ctx.conditionalExpression());
    }

    @Override
    public String visitConditionalExpression(ParticParser.ConditionalExpressionContext ctx) {
        if (ctx.getChildCount() > 1) {
            // 三元运算符: cond ? trueExpr : falseExpr
            String cond = visit(ctx.conditionalOrExpression());
            String trueExpr = visit(ctx.expression());
            String falseExpr = visit(ctx.conditionalExpression());
            
            // 结果类型取两个分支的公共类型
            String trueType = getType(trueExpr);
            String falseType = getType(falseExpr);
            String resultType = inferCommonType(trueType, falseType);
            
            TempVar temp = new TempVar("ternary", resultType);
            temp.addOperand(cond);
            temp.addOperand(trueExpr);
            temp.addOperand(falseExpr);
            return body.addTemp(temp);
        }
        return visit(ctx.conditionalOrExpression());
    }

    @Override
    public String visitConditionalOrExpression(ParticParser.ConditionalOrExpressionContext ctx) {
        String result = visit(ctx.conditionalAndExpression(0));
        for (int i = 1; i < ctx.conditionalAndExpression().size(); i++) {
            String right = visit(ctx.conditionalAndExpression(i));
            TempVar temp = new TempVar("or", "bool");  // 逻辑或结果是 boolean
            temp.addOperand(result);
            temp.addOperand(right);
            result = body.addTemp(temp);
        }
        return result;
    }

    @Override
    public String visitConditionalAndExpression(ParticParser.ConditionalAndExpressionContext ctx) {
        String result = visit(ctx.inclusiveOrExpression(0));
        for (int i = 1; i < ctx.inclusiveOrExpression().size(); i++) {
            String right = visit(ctx.inclusiveOrExpression(i));
            TempVar temp = new TempVar("and", "bool");  // 逻辑与结果是 boolean
            temp.addOperand(result);
            temp.addOperand(right);
            result = body.addTemp(temp);
        }
        return result;
    }

    @Override
    public String visitInclusiveOrExpression(ParticParser.InclusiveOrExpressionContext ctx) {
        String result = visit(ctx.exclusiveOrExpression(0));
        for (int i = 1; i < ctx.exclusiveOrExpression().size(); i++) {
            String right = visit(ctx.exclusiveOrExpression(i));
            String leftType = getType(result);
            String rightType = getType(right);
            String resultType = TypeUtils.inferBitwiseType(leftType, rightType);
            
            TempVar temp = new TempVar("bitor", resultType);
            temp.addOperand(result);
            temp.addOperand(right);
            result = body.addTemp(temp);
        }
        return result;
    }

    @Override
    public String visitExclusiveOrExpression(ParticParser.ExclusiveOrExpressionContext ctx) {
        String result = visit(ctx.andExpression(0));
        for (int i = 1; i < ctx.andExpression().size(); i++) {
            String right = visit(ctx.andExpression(i));
            String leftType = getType(result);
            String rightType = getType(right);
            String resultType = TypeUtils.inferBitwiseType(leftType, rightType);
            
            TempVar temp = new TempVar("bitxor", resultType);
            temp.addOperand(result);
            temp.addOperand(right);
            result = body.addTemp(temp);
        }
        return result;
    }

    @Override
    public String visitAndExpression(ParticParser.AndExpressionContext ctx) {
        String result = visit(ctx.equalityExpression(0));
        for (int i = 1; i < ctx.equalityExpression().size(); i++) {
            String right = visit(ctx.equalityExpression(i));
            String leftType = getType(result);
            String rightType = getType(right);
            String resultType = TypeUtils.inferBitwiseType(leftType, rightType);
            
            TempVar temp = new TempVar("bitand", resultType);
            temp.addOperand(result);
            temp.addOperand(right);
            result = body.addTemp(temp);
        }
        return result;
    }

    @Override
    public String visitEqualityExpression(ParticParser.EqualityExpressionContext ctx) {
        String result = visit(ctx.relationalExpression(0));
        for (int i = 1; i < ctx.relationalExpression().size(); i++) {
            String right = visit(ctx.relationalExpression(i));
            String op = ctx.getChild(2 * i - 1).getText();
            String opName = switch (op) {
                case "==" -> "cmp_eq";
                case "!=" -> "cmp_ne";
                case "===" -> "cmp_eq_strict";
                case "!==" -> "cmp_ne_strict";
                default -> "cmp_eq";
            };
            TempVar temp = new TempVar(opName, "bool");  // 比较结果是 boolean
            temp.addOperand(result);
            temp.addOperand(right);
            result = body.addTemp(temp);
        }
        return result;
    }

    @Override
    public String visitRelationalExpression(ParticParser.RelationalExpressionContext ctx) {
        String result = visit(ctx.shiftExpression(0));
        for (int i = 1; i < ctx.shiftExpression().size(); i++) {
            String right = visit(ctx.shiftExpression(i));
            String op = ctx.getChild(2 * i - 1).getText();
            String opName = switch (op) {
                case "<" -> "cmp_lt";
                case ">" -> "cmp_gt";
                case "<=" -> "cmp_le";
                case ">=" -> "cmp_ge";
                case "instanceof" -> "instanceof";
                case "in" -> "in";
                default -> "cmp_lt";
            };
            TempVar temp = new TempVar(opName, "bool");  // 比较结果是 boolean
            temp.addOperand(result);
            temp.addOperand(right);
            result = body.addTemp(temp);
        }
        return result;
    }

    @Override
    public String visitShiftExpression(ParticParser.ShiftExpressionContext ctx) {
        String result = visit(ctx.additiveExpression(0));
        for (int i = 1; i < ctx.additiveExpression().size(); i++) {
            String right = visit(ctx.additiveExpression(i));
            String op = ctx.getChild(2 * i - 1).getText();
            String opName = switch (op) {
                case "<<" -> "shl";
                case ">>" -> "shr";
                case ">>>" -> "ushr";
                default -> "shl";
            };
            
            String leftType = getType(result);
            String rightType = getType(right);
            String resultType = TypeUtils.inferBitwiseType(leftType, rightType);
            
            TempVar temp = new TempVar(opName, resultType);
            temp.addOperand(result);
            temp.addOperand(right);
            result = body.addTemp(temp);
        }
        return result;
    }

    @Override
    public String visitAdditiveExpression(ParticParser.AdditiveExpressionContext ctx) {
        String result = visit(ctx.multiplicativeExpression(0));
        for (int i = 1; i < ctx.multiplicativeExpression().size(); i++) {
            String right = visit(ctx.multiplicativeExpression(i));
            String op = ctx.getChild(2 * i - 1).getText();
            
            String leftType = getType(result);
            String rightType = getType(right);
            String resultType = TypeUtils.inferArithmeticType(leftType, rightType);
            
            TempVar temp = new TempVar(op.equals("+") ? "add" : "sub", resultType);
            temp.addOperand(result);
            temp.addOperand(right);
            result = body.addTemp(temp);
        }
        return result;
    }

    @Override
    public String visitMultiplicativeExpression(ParticParser.MultiplicativeExpressionContext ctx) {
        String result = visit(ctx.unaryExpression(0));
        for (int i = 1; i < ctx.unaryExpression().size(); i++) {
            String right = visit(ctx.unaryExpression(i));
            String op = ctx.getChild(2 * i - 1).getText();
            String opName = switch (op) {
                case "*" -> "mul";
                case "/" -> "div";
                case "%" -> "mod";
                default -> "mul";
            };
            
            String leftType = getType(result);
            String rightType = getType(right);
            String resultType = TypeUtils.inferArithmeticType(leftType, rightType);
            
            TempVar temp = new TempVar(opName, resultType);
            temp.addOperand(result);
            temp.addOperand(right);
            result = body.addTemp(temp);
        }
        return result;
    }

    @Override
    public String visitUnaryExpression(ParticParser.UnaryExpressionContext ctx) {
        if (ctx.unaryExpression() != null) {
            String operand = visit(ctx.unaryExpression());
            String op = ctx.getChild(0).getText();
            String opName = switch (op) {
                case "+" -> "pos";
                case "-" -> "neg";
                case "++" -> "preinc";
                case "--" -> "predec";
                case "!" -> "not";
                case "~" -> "bitnot";
                default -> "pos";
            };
            
            String operandType = getType(operand);
            String resultType = TypeUtils.inferUnaryType(operandType, op);
            
            TempVar temp = new TempVar(opName, resultType);
            temp.addOperand(operand);
            return body.addTemp(temp);
        }
        if (ctx.castExpression() != null) {
            return visit(ctx.castExpression());
        }
        return visit(ctx.postfixExpression());
    }

    @Override
    public String visitCastExpression(ParticParser.CastExpressionContext ctx) {
        String operand = visit(ctx.unaryExpression());
        String targetType = context.resolveType(ctx.type().getText());
        
        TempVar temp = new TempVar("cast", targetType);  // 强制转换的结果类型就是目标类型
        temp.putMetadata("target_type", targetType);
        temp.addOperand(operand);
        return body.addTemp(temp);
    }

    @Override
    public String visitPostfixExpression(ParticParser.PostfixExpressionContext ctx) {
        if (ctx.primary() != null) {
            return visit(ctx.primary());
        }
        
        // 递归处理左边的表达式
        String object = visit(ctx.postfixExpression());
        String objectType = getType(object);
        
        // 判断是哪种后缀操作
        if (ctx.IDENTIFIER() != null) {
            String member = ctx.IDENTIFIER().getText();
            
            if (ctx.argumentList() != null || ctx.getChild(ctx.getChildCount() - 1).getText().equals(")")) {
                // 方法调用: obj.method(args)
                return handleMethodCall(object, objectType, member, ctx.argumentList());
            } else {
                // 字段访问: obj.field
                return handleFieldAccess(object, objectType, member);
            }
        }
        
        if (ctx.expression() != null && ctx.getChild(1).getText().equals("[")) {
            // 数组访问: obj[index]
            String index = visit(ctx.expression());
            
            // 数组元素类型：去掉一个 []
            String elementType = null;
            if (objectType != null && objectType.endsWith("[]")) {
                elementType = objectType.substring(0, objectType.length() - 2);
            }
            
            TempVar temp = new TempVar("array_access", elementType);
            temp.addOperand(object);
            temp.addOperand(index);
            return body.addTemp(temp);
        }
        
        if (ctx.argumentList() != null || ctx.getChild(1).getText().equals("(")) {
            // 函数调用: func(args)
            // 如果 objectType 为 null，说明 func 不是类名，可能是方法名
            // 尝试从 object 获取方法名（如果 object 是标识符的 temp）
            String methodName = "";
            if (objectType == null && object != null) {
                // object 可能是标识符的 temp，尝试从 metadata 或 operands 获取原始名称
                TempVar objectTemp = body.getTemps().get(object);
                if (objectTemp != null && objectTemp.getOperands().size() > 0) {
                    methodName = objectTemp.getOperands().get(0); // 获取原始标识符名
                } else {
                    // 如果 object 本身就是标识符名（不是 temp），直接使用
                    methodName = object;
                }
            }
            // 对于 func(args) 形式的调用，objectType 为 null，methodName 为方法名
            // handleMethodCall 会尝试从当前类解析
            return handleMethodCall(null, null, methodName, ctx.argumentList());
        }
        
        // 后缀++/--/!
        String op = ctx.getChild(ctx.getChildCount() - 1).getText();
        if (op.equals("++")) {
            TempVar temp = new TempVar("postinc", objectType);
            temp.addOperand(object);
            return body.addTemp(temp);
        } else if (op.equals("--")) {
            TempVar temp = new TempVar("postdec", objectType);
            temp.addOperand(object);
            return body.addTemp(temp);
        } else if (op.equals("!")) {
            // 后缀 ! 是非空断言，类型不变但去掉可空标记
            TempVar temp = new TempVar("notnull", objectType);
            temp.addOperand(object);
            return body.addTemp(temp);
        }
        
        return visitChildren(ctx);
    }

    /**
     * 处理方法调用（支持外部类）
     */
    private String handleMethodCall(String object, String objectType, String methodName, 
                                     ParticParser.ArgumentListContext argList) {
        // 如果 objectType 为 null，尝试从 object 获取类型
        if (objectType == null && object != null) {
            objectType = getType(object);
        }
        
        // 收集参数并获取类型
        List<String> args = new ArrayList<>();
        List<String> argTypes = new ArrayList<>();
        
        if (argList != null) {
            for (ParticParser.ExpressionContext argCtx : argList.expression()) {
                String arg = visit(argCtx);
                args.add(arg);
                argTypes.add(getType(arg));
            }
        }
        
        // 解析方法（包括外部类和当前类的方法）
        String returnType = null;
        boolean isStatic = false;
        
        // 如果 objectType 为 null 且 methodName 不为空，尝试从当前类解析（局部方法调用）
        if (objectType == null && !methodName.isEmpty()) {
            MethodSymbol method = context.resolveMethod(null, methodName, argTypes);
            if (method != null) {
                returnType = method.getType();
                isStatic = method.isStatic();
                
                // 在静态方法中不能调用实例方法
                MethodSymbol currentMethod = context.getCurrentMethod();
                if (currentMethod != null && currentMethod.isStatic() && !isStatic) {
                    throw new CompileError("静态方法 '" + currentMethod.getName() + "' 中不能调用实例方法 '" + methodName + "'");
                }
                
                // 如果返回类型是 void，对于 var 推断来说是有问题的
                if (returnType != null && returnType.equals("void")) {
                    returnType = null; // void 不能用于类型推断
                }
            }
        } else if (objectType != null && !methodName.isEmpty()) {
            // 实例方法或静态方法调用
            MethodSymbol method = context.resolveMethod(objectType, methodName, argTypes);
            if (method != null) {
                returnType = method.getType();
                isStatic = method.isStatic();
                // 如果返回类型是 void，对于 var 推断来说是有问题的
                if (returnType != null && returnType.equals("void")) {
                    returnType = null; // void 不能用于类型推断
                }
            }
        } else if (objectType != null && methodName.isEmpty()) {
            // 函数调用（如 lambda 表达式调用）
            // TODO: 处理函数调用
        }
        
        TempVar temp = new TempVar("call_method", returnType);
        temp.putMetadata("object", object);
        temp.putMetadata("object_type", objectType);
        temp.putMetadata("method", methodName);
        temp.putMetadata("arg_types", argTypes);
        temp.putMetadata("is_static", isStatic);
        
        for (String arg : args) {
            temp.addOperand(arg);
        }
        
        return body.addTemp(temp);
    }

    /**
     * 处理字段访问（支持外部类）
     */
    private String handleFieldAccess(String object, String objectType, String fieldName) {
        // 如果 objectType 为 null，尝试从 object 获取类型
        if (objectType == null && object != null) {
            objectType = getType(object);
        }
        
        // 解析字段（包括外部类）
        String fieldType = null;
        boolean isStatic = false;
        if (objectType != null) {
            FieldSymbol field = context.resolveField(objectType, fieldName);
            if (field != null) {
                fieldType = field.getType();
                isStatic = field.isStatic();
            }
        }
        
        TempVar temp = new TempVar("field_access", fieldType);
        temp.putMetadata("object", object);
        temp.putMetadata("object_type", objectType);
        temp.putMetadata("field", fieldName);
        temp.putMetadata("is_static", isStatic);
        return body.addTemp(temp);
    }

    @Override
    public String visitPrimary(ParticParser.PrimaryContext ctx) {
        if (ctx.literal() != null) {
            return visit(ctx.literal());
        }
        if (ctx.IDENTIFIER() != null) {
            String varName = ctx.IDENTIFIER().getText();
            return handleVariableLoad(varName);
        }
        if (ctx.expression() != null) {
            return visit(ctx.expression());
        }
        if (ctx.getText().equals("this")) {
            // this 的类型是当前类
            String thisType = null;
            ClassSymbol currentClass = context.getCurrentClass();
            if (currentClass != null) {
                thisType = currentClass.getFullName();
            }
            TempVar temp = new TempVar("load_this", thisType);
            return body.addTemp(temp);
        }
        if (ctx.getText().equals("super")) {
            // super 的类型是父类
            String superType = null;
            ClassSymbol currentClass = context.getCurrentClass();
            if (currentClass != null && currentClass.getSuperClass() != null) {
                superType = currentClass.getSuperClass().getFullName();
            }
            TempVar temp = new TempVar("load_super", superType);
            return body.addTemp(temp);
        }
        // TODO: 处理 new, lambda 等
        return visitChildren(ctx);
    }

    /**
     * 处理变量加载
     */
    private String handleVariableLoad(String varName) {
        String varType = null;
        String loadOp = "load";
        
        // 从符号表查找变量
        Symbol symbol = context.resolveVariable(varName);
        
        if (symbol instanceof VariableSymbol vs) {
            varType = vs.getType();
            loadOp = "load_local";
        } else if (symbol instanceof FieldSymbol fs) {
            varType = fs.getType();
            loadOp = fs.isStatic() ? "load_static_field" : "load_field";
        } else {
            // 可能是未解析的外部类或静态成员
            // 尝试从类名解析（使用 context.resolveClass 会通过 ImportManager 解析）
            ClassSymbol cls = context.resolveClass(varName);
            if (cls != null) {
                varType = cls.getFullName();
                loadOp = "load_class";
            }
        }
        
        TempVar temp = new TempVar(loadOp, varType);
        temp.addOperand(varName);
        if (symbol != null) {
            temp.putMetadata("symbol", symbol);
        }
        return body.addTemp(temp);
    }

    @Override
    public String visitLiteral(ParticParser.LiteralContext ctx) {
        TempVar temp = new TempVar("const");
        
        if (ctx.IntegerLiteral() != null) {
            String text = ctx.IntegerLiteral().getText();
            if (text.endsWith("L") || text.endsWith("l")) {
                temp.setValueWithType(Long.parseLong(text.substring(0, text.length() - 1)));
            } else {
                temp.setValueWithType(Integer.parseInt(text));
            }
        } else if (ctx.FloatingPointLiteral() != null) {
            String text = ctx.FloatingPointLiteral().getText();
            if (text.endsWith("F") || text.endsWith("f")) {
                temp.setValueWithType(Float.parseFloat(text));
            } else {
                temp.setValueWithType(Double.parseDouble(text));
            }
        } else if (ctx.BooleanLiteral() != null) {
            temp.setValueWithType(ctx.BooleanLiteral().getText().equals("true"));
        } else if (ctx.StringLiteral() != null) {
            String value = ctx.StringLiteral().getText();
            value = value.substring(1, value.length() - 1);  // 去掉引号
            temp.setValueWithType(value);
        } else if (ctx.CharacterLiteral() != null) {
            String value = ctx.CharacterLiteral().getText();
            value = value.substring(1, value.length() - 1);  // 去掉单引号
            temp.setValueWithType(value.charAt(0));
        } else if (ctx.NullLiteral() != null) {
            temp.setValueWithType(null);
        } else if (ctx.TextBlockLiteral() != null) {
            String value = ctx.TextBlockLiteral().getText();
            // 去掉 """ 
            value = value.substring(3, value.length() - 3).stripIndent();
            temp.setValueWithType(value);
        }
        
        return body.addTemp(temp);
    }

    /**
     * 推断两个类型的公共类型（用于三元运算符）
     */
    private String inferCommonType(String type1, String type2) {
        if (type1 == null) return type2;
        if (type2 == null) return type1;
        if (type1.equals(type2)) return type1;
        
        // 数值类型取更宽的
        if (TypeUtils.isNumeric(type1) && TypeUtils.isNumeric(type2)) {
            return TypeUtils.inferArithmeticType(type1, type2);
        }
        
        // TODO: 对于引用类型，应该找最近公共祖先
        return type1;
    }
}

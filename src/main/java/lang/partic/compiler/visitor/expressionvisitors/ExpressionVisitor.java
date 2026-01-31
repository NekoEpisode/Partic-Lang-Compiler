package lang.partic.compiler.visitor.expressionvisitors;

import lang.partic.compiler.antlr.ParticBaseVisitor;
import lang.partic.compiler.antlr.ParticParser;
import lang.partic.compiler.context.VisitContext;
import lang.partic.compiler.ir.ParticMethodBody;
import lang.partic.compiler.ir.ParticMethodBody.TempVar;

/**
 * 表达式访问器
 * 返回临时变量名（String），表示表达式的计算结果
 */
public class ExpressionVisitor extends ParticBaseVisitor<String> {
    private final ParticMethodBody body;
    private final VisitContext context;

    public ExpressionVisitor(ParticMethodBody body, VisitContext context) {
        this.body = body;
        this.context = context;
    }

    @Override
    public String visitExpression(ParticParser.ExpressionContext ctx) {
        return visit(ctx.assignmentExpression());
    }

    @Override
    public String visitAssignmentExpression(ParticParser.AssignmentExpressionContext ctx) {
        if (ctx.assignmentOperator() != null) {
            // TODO: 处理赋值表达式
            return visitChildren(ctx);
        }
        return visit(ctx.conditionalExpression());
    }

    @Override
    public String visitConditionalExpression(ParticParser.ConditionalExpressionContext ctx) {
        if (ctx.getChildCount() > 1) {
            // TODO: 处理三元运算符
            return visitChildren(ctx);
        }
        return visit(ctx.conditionalOrExpression());
    }

    @Override
    public String visitConditionalOrExpression(ParticParser.ConditionalOrExpressionContext ctx) {
        String result = visit(ctx.conditionalAndExpression(0));
        for (int i = 1; i < ctx.conditionalAndExpression().size(); i++) {
            String right = visit(ctx.conditionalAndExpression(i));
            TempVar temp = new TempVar("or");
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
            TempVar temp = new TempVar("and");
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
            TempVar temp = new TempVar("bitor");
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
            TempVar temp = new TempVar("bitxor");
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
            TempVar temp = new TempVar("bitand");
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
            TempVar temp = new TempVar(opName);
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
            TempVar temp = new TempVar(opName);
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
            TempVar temp = new TempVar(opName);
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
            TempVar temp = new TempVar(op.equals("+") ? "add" : "sub");
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
            TempVar temp = new TempVar(opName);
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
            TempVar temp = new TempVar(opName);
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
        String targetType = ctx.type().getText();
        TempVar temp = new TempVar("cast");
        temp.putMetadata("target_type", targetType);
        temp.addOperand(operand);
        return body.addTemp(temp);
    }

    @Override
    public String visitPostfixExpression(ParticParser.PostfixExpressionContext ctx) {
        // TODO: 处理方法调用、数组访问、字段访问、后缀++/--
        if (ctx.primary() != null) {
            return visit(ctx.primary());
        }
        return visitChildren(ctx);
    }

    @Override
    public String visitPrimary(ParticParser.PrimaryContext ctx) {
        if (ctx.literal() != null) {
            return visit(ctx.literal());
        }
        if (ctx.IDENTIFIER() != null) {
            String varName = ctx.IDENTIFIER().getText();
            TempVar temp = new TempVar("load");
            temp.addOperand(varName);
            return body.addTemp(temp);
        }
        if (ctx.expression() != null) {
            return visit(ctx.expression());
        }
        // TODO: 处理 this, super, new, lambda等
        return visitChildren(ctx);
    }

    @Override
    public String visitLiteral(ParticParser.LiteralContext ctx) {
        if (ctx.IntegerLiteral() != null) {
            int value = Integer.parseInt(ctx.IntegerLiteral().getText());
            TempVar temp = new TempVar("const");
            temp.setValue(value);
            return body.addTemp(temp);
        }
        if (ctx.FloatingPointLiteral() != null) {
            double value = Double.parseDouble(ctx.FloatingPointLiteral().getText());
            TempVar temp = new TempVar("const");
            temp.setValue(value);
            return body.addTemp(temp);
        }
        if (ctx.BooleanLiteral() != null) {
            boolean value = ctx.BooleanLiteral().getText().equals("true");
            TempVar temp = new TempVar("const");
            temp.setValue(value);
            return body.addTemp(temp);
        }
        if (ctx.StringLiteral() != null) {
            String value = ctx.StringLiteral().getText();
            value = value.substring(1, value.length() - 1);
            TempVar temp = new TempVar("const");
            temp.setValue(value);
            return body.addTemp(temp);
        }
        if (ctx.NullLiteral() != null) {
            TempVar temp = new TempVar("const");
            temp.setValue(null);
            return body.addTemp(temp);
        }
        // TODO: 处理 CharacterLiteral, TextBlockLiteral
        return visitChildren(ctx);
    }
}

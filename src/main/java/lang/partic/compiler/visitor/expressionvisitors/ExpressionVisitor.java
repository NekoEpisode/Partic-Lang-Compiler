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
        // expression: assignmentExpression
        return visit(ctx.assignmentExpression());
    }

    @Override
    public String visitAssignmentExpression(ParticParser.AssignmentExpressionContext ctx) {
        // assignmentExpression: conditionalExpression | postfixExpression assignmentOperator expression
        if (ctx.assignmentOperator() != null) {
            // TODO: 处理赋值表达式
            return visitChildren(ctx);
        }
        return visit(ctx.conditionalExpression());
    }

    @Override
    public String visitConditionalExpression(ParticParser.ConditionalExpressionContext ctx) {
        // conditionalExpression: conditionalOrExpression ('?' expression ':' conditionalExpression)?
        if (ctx.getChildCount() > 1) {
            // TODO: 处理三元运算符
            return visitChildren(ctx);
        }
        return visit(ctx.conditionalOrExpression());
    }

    @Override
    public String visitConditionalOrExpression(ParticParser.ConditionalOrExpressionContext ctx) {
        // conditionalOrExpression: conditionalAndExpression ('||' conditionalAndExpression)*
        String result = visit(ctx.conditionalAndExpression(0));
        
        for (int i = 1; i < ctx.conditionalAndExpression().size(); i++) {
            String right = visit(ctx.conditionalAndExpression(i));
            
            TempVar temp = new TempVar("or", "boolean");
            temp.addOperand(result);
            temp.addOperand(right);
            
            result = body.addTemp(temp);
        }
        
        return result;
    }

    @Override
    public String visitConditionalAndExpression(ParticParser.ConditionalAndExpressionContext ctx) {
        // conditionalAndExpression: inclusiveOrExpression ('&&' inclusiveOrExpression)*
        String result = visit(ctx.inclusiveOrExpression(0));
        
        for (int i = 1; i < ctx.inclusiveOrExpression().size(); i++) {
            String right = visit(ctx.inclusiveOrExpression(i));
            
            TempVar temp = new TempVar("and", "boolean");
            temp.addOperand(result);
            temp.addOperand(right);
            
            result = body.addTemp(temp);
        }
        
        return result;
    }

    @Override
    public String visitInclusiveOrExpression(ParticParser.InclusiveOrExpressionContext ctx) {
        // inclusiveOrExpression: exclusiveOrExpression ('|' exclusiveOrExpression)*
        String result = visit(ctx.exclusiveOrExpression(0));
        
        for (int i = 1; i < ctx.exclusiveOrExpression().size(); i++) {
            String right = visit(ctx.exclusiveOrExpression(i));
            
            TempVar temp = new TempVar("bitor", "int");
            temp.addOperand(result);
            temp.addOperand(right);
            
            result = body.addTemp(temp);
        }
        
        return result;
    }

    @Override
    public String visitExclusiveOrExpression(ParticParser.ExclusiveOrExpressionContext ctx) {
        // exclusiveOrExpression: andExpression ('^' andExpression)*
        String result = visit(ctx.andExpression(0));
        
        for (int i = 1; i < ctx.andExpression().size(); i++) {
            String right = visit(ctx.andExpression(i));
            
            TempVar temp = new TempVar("bitxor", "int");
            temp.addOperand(result);
            temp.addOperand(right);
            
            result = body.addTemp(temp);
        }
        
        return result;
    }

    @Override
    public String visitAndExpression(ParticParser.AndExpressionContext ctx) {
        // andExpression: equalityExpression ('&' equalityExpression)*
        String result = visit(ctx.equalityExpression(0));
        
        for (int i = 1; i < ctx.equalityExpression().size(); i++) {
            String right = visit(ctx.equalityExpression(i));
            
            TempVar temp = new TempVar("bitand", "int");
            temp.addOperand(result);
            temp.addOperand(right);
            
            result = body.addTemp(temp);
        }
        
        return result;
    }

    @Override
    public String visitEqualityExpression(ParticParser.EqualityExpressionContext ctx) {
        // equalityExpression: relationalExpression (('==' | '!=' | '===' | '!==') relationalExpression)*
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
            
            TempVar temp = new TempVar(opName, "boolean");
            temp.addOperand(result);
            temp.addOperand(right);
            
            result = body.addTemp(temp);
        }
        
        return result;
    }

    @Override
    public String visitRelationalExpression(ParticParser.RelationalExpressionContext ctx) {
        // relationalExpression: shiftExpression (('<' | '>' | '<=' | '>=' | 'instanceof' | 'in') shiftExpression)*
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
            
            TempVar temp = new TempVar(opName, "boolean");
            temp.addOperand(result);
            temp.addOperand(right);
            
            result = body.addTemp(temp);
        }
        
        return result;
    }

    @Override
    public String visitShiftExpression(ParticParser.ShiftExpressionContext ctx) {
        // shiftExpression: additiveExpression (('<<' | '>>' | '>>>') additiveExpression)*
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
            
            TempVar temp = new TempVar(opName, "int");
            temp.addOperand(result);
            temp.addOperand(right);
            
            result = body.addTemp(temp);
        }
        
        return result;
    }

    @Override
    public String visitAdditiveExpression(ParticParser.AdditiveExpressionContext ctx) {
        // additiveExpression: multiplicativeExpression (('+' | '-') multiplicativeExpression)*
        String result = visit(ctx.multiplicativeExpression(0));
        
        for (int i = 1; i < ctx.multiplicativeExpression().size(); i++) {
            String right = visit(ctx.multiplicativeExpression(i));
            String op = ctx.getChild(2 * i - 1).getText();
            
            TempVar temp = new TempVar(op.equals("+") ? "add" : "sub", "int");
            temp.addOperand(result);
            temp.addOperand(right);
            
            result = body.addTemp(temp);
        }
        
        return result;
    }

    @Override
    public String visitMultiplicativeExpression(ParticParser.MultiplicativeExpressionContext ctx) {
        // multiplicativeExpression: unaryExpression (('*' | '/' | '%') unaryExpression)*
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
            
            TempVar temp = new TempVar(opName, "int");
            temp.addOperand(result);
            temp.addOperand(right);
            
            result = body.addTemp(temp);
        }
        
        return result;
    }

    @Override
    public String visitUnaryExpression(ParticParser.UnaryExpressionContext ctx) {
        // unaryExpression: ('+' | '-' | '++' | '--' | '!' | '~') unaryExpression | castExpression | postfixExpression
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
            
            TempVar temp = new TempVar(opName, "int");
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
        // castExpression: '(' type ')' unaryExpression
        String operand = visit(ctx.unaryExpression());
        String targetType = ctx.type().getText();
        
        TempVar temp = new TempVar("cast", targetType);
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
            // 变量引用
            String varName = ctx.IDENTIFIER().getText();
            TempVar temp = new TempVar("load", "int"); // TODO: 获取实际类型
            temp.addOperand(varName);
            return body.addTemp(temp);
        }
        
        if (ctx.expression() != null) {
            // 括号表达式
            return visit(ctx.expression());
        }
        
        // TODO: 处理 this, super, new, lambda等
        return visitChildren(ctx);
    }

    @Override
    public String visitLiteral(ParticParser.LiteralContext ctx) {
        if (ctx.IntegerLiteral() != null) {
            int value = Integer.parseInt(ctx.IntegerLiteral().getText());
            TempVar temp = new TempVar("const", "int");
            temp.setValue(value);
            return body.addTemp(temp);
        }
        
        if (ctx.FloatingPointLiteral() != null) {
            double value = Double.parseDouble(ctx.FloatingPointLiteral().getText());
            TempVar temp = new TempVar("const", "double");
            temp.setValue(value);
            return body.addTemp(temp);
        }
        
        if (ctx.BooleanLiteral() != null) {
            boolean value = ctx.BooleanLiteral().getText().equals("true");
            TempVar temp = new TempVar("const", "boolean");
            temp.setValue(value);
            return body.addTemp(temp);
        }
        
        if (ctx.StringLiteral() != null) {
            String value = ctx.StringLiteral().getText();
            // 去掉引号
            value = value.substring(1, value.length() - 1);
            TempVar temp = new TempVar("const", "java.lang.String");
            temp.setValue(value);
            return body.addTemp(temp);
        }
        
        if (ctx.NullLiteral() != null) {
            TempVar temp = new TempVar("const", "null");
            temp.setValue(null);
            return body.addTemp(temp);
        }
        
        // TODO: 处理 CharacterLiteral, TextBlockLiteral
        return visitChildren(ctx);
    }
}

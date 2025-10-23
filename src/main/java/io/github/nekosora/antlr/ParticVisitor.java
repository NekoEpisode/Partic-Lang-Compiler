package io.github.nekosora.antlr;// Generated from Partic.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ParticParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ParticVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ParticParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(ParticParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#importStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportStatement(ParticParser.ImportStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(ParticParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#parameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterList(ParticParser.ParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(ParticParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(ParticParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#primitiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveType(ParticParser.PrimitiveTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#classType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassType(ParticParser.ClassTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#qualifiedName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiedName(ParticParser.QualifiedNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(ParticParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(ParticParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#localVariableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalVariableDeclaration(ParticParser.LocalVariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#expressionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionStatement(ParticParser.ExpressionStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(ParticParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(ParticParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(ParticParser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#forInit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInit(ParticParser.ForInitContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#forUpdate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForUpdate(ParticParser.ForUpdateContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(ParticParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(ParticParser.ExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(ParticParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code passThroughAssignment}
	 * labeled alternative in {@link ParticParser#assignmentExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPassThroughAssignment(ParticParser.PassThroughAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignmentAction}
	 * labeled alternative in {@link ParticParser#assignmentExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentAction(ParticParser.AssignmentActionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#assignmentOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentOperator(ParticParser.AssignmentOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#conditionalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalExpression(ParticParser.ConditionalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#conditionalOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalOrExpression(ParticParser.ConditionalOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#conditionalAndExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalAndExpression(ParticParser.ConditionalAndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#inclusiveOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInclusiveOrExpression(ParticParser.InclusiveOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#exclusiveOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExclusiveOrExpression(ParticParser.ExclusiveOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#andExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpression(ParticParser.AndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#equalityExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpression(ParticParser.EqualityExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#relationalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalExpression(ParticParser.RelationalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#shiftExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShiftExpression(ParticParser.ShiftExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#additiveExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpression(ParticParser.AdditiveExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpression(ParticParser.MultiplicativeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#unaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpression(ParticParser.UnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code primaryExpression}
	 * labeled alternative in {@link ParticParser#postfixExpression()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryExpression(ParticParser.PrimaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayAccessExpression}
	 * labeled alternative in {@link ParticParser#postfixExpression()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAccessExpression(ParticParser.ArrayAccessExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code fieldAccessExpression}
	 * labeled alternative in {@link ParticParser#postfixExpression()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldAccessExpression(ParticParser.FieldAccessExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code methodCallExpression}
	 * labeled alternative in {@link ParticParser#postfixExpression()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodCallExpression(ParticParser.MethodCallExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code functionCallExpression}
	 * labeled alternative in {@link ParticParser#postfixExpression()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCallExpression(ParticParser.FunctionCallExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code postIncrementExpression}
	 * labeled alternative in {@link ParticParser#postfixExpression()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostIncrementExpression(ParticParser.PostIncrementExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code postDecrementExpression}
	 * labeled alternative in {@link ParticParser#postfixExpression()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostDecrementExpression(ParticParser.PostDecrementExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#argumentList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentList(ParticParser.ArgumentListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(ParticParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(ParticParser.LiteralContext ctx);
}
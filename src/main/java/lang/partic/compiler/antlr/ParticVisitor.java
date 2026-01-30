package lang.partic.compiler.antlr;// Generated from Partic.g4 by ANTLR 4.13.2
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
	 * Visit a parse tree produced by {@link ParticParser#importDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportDeclaration(ParticParser.ImportDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#typeDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeDeclaration(ParticParser.TypeDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#classDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDeclaration(ParticParser.ClassDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#interfaceDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceDeclaration(ParticParser.InterfaceDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#enumDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumDeclaration(ParticParser.EnumDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#enumBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumBody(ParticParser.EnumBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#enumConstant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumConstant(ParticParser.EnumConstantContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#enumBodyDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumBodyDeclaration(ParticParser.EnumBodyDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(ParticParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#modifiers}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModifiers(ParticParser.ModifiersContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#modifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModifier(ParticParser.ModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#typeParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeParameters(ParticParser.TypeParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#typeParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeParameter(ParticParser.TypeParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#typeBound}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeBound(ParticParser.TypeBoundContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#classBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBody(ParticParser.ClassBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#classBodyDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBodyDeclaration(ParticParser.ClassBodyDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#interfaceBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceBody(ParticParser.InterfaceBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#interfaceMemberDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceMemberDeclaration(ParticParser.InterfaceMemberDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldDeclaration(ParticParser.FieldDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#variableDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclarator(ParticParser.VariableDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#constantDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstantDeclaration(ParticParser.ConstantDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#methodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDeclaration(ParticParser.MethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#abstractMethodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAbstractMethodDeclaration(ParticParser.AbstractMethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#defaultMethodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefaultMethodDeclaration(ParticParser.DefaultMethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#staticMethodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStaticMethodDeclaration(ParticParser.StaticMethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorDeclaration(ParticParser.ConstructorDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#constructorBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorBody(ParticParser.ConstructorBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#explicitConstructorInvocation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExplicitConstructorInvocation(ParticParser.ExplicitConstructorInvocationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#staticBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStaticBlock(ParticParser.StaticBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#annotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotation(ParticParser.AnnotationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#elementValuePairs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValuePairs(ParticParser.ElementValuePairsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#elementValuePair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValuePair(ParticParser.ElementValuePairContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#elementValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValue(ParticParser.ElementValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#elementValueArrayInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValueArrayInitializer(ParticParser.ElementValueArrayInitializerContext ctx);
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
	 * Visit a parse tree produced by {@link ParticParser#typeList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeList(ParticParser.TypeListContext ctx);
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
	 * Visit a parse tree produced by {@link ParticParser#typeArguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeArguments(ParticParser.TypeArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#typeArgument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeArgument(ParticParser.TypeArgumentContext ctx);
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
	 * Visit a parse tree produced by {@link ParticParser#variableDeclarationStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclarationStatement(ParticParser.VariableDeclarationStatementContext ctx);
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
	 * Visit a parse tree produced by {@link ParticParser#switchStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchStatement(ParticParser.SwitchStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#switchCase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchCase(ParticParser.SwitchCaseContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#defaultCase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefaultCase(ParticParser.DefaultCaseContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(ParticParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#doWhileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoWhileStatement(ParticParser.DoWhileStatementContext ctx);
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
	 * Visit a parse tree produced by {@link ParticParser#forEachStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForEachStatement(ParticParser.ForEachStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(ParticParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#breakStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStatement(ParticParser.BreakStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#continueStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinueStatement(ParticParser.ContinueStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#throwStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThrowStatement(ParticParser.ThrowStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#tryStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTryStatement(ParticParser.TryStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#catchClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCatchClause(ParticParser.CatchClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#finallyBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFinallyBlock(ParticParser.FinallyBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#synchronizedStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSynchronizedStatement(ParticParser.SynchronizedStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#assertStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssertStatement(ParticParser.AssertStatementContext ctx);
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
	 * Visit a parse tree produced by {@link ParticParser#assignmentExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentExpression(ParticParser.AssignmentExpressionContext ctx);
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
	 * Visit a parse tree produced by {@link ParticParser#castExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCastExpression(ParticParser.CastExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#postfixExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixExpression(ParticParser.PostfixExpressionContext ctx);
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
	 * Visit a parse tree produced by {@link ParticParser#creator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreator(ParticParser.CreatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#lambdaExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaExpression(ParticParser.LambdaExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#lambdaParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaParameters(ParticParser.LambdaParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#lambdaBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaBody(ParticParser.LambdaBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#methodReference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodReference(ParticParser.MethodReferenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#arrayInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayInitializer(ParticParser.ArrayInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link ParticParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(ParticParser.LiteralContext ctx);
}
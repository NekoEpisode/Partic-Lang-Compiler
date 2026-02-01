package lang.partic.compiler.antlr;// Generated from Partic.g4 by ANTLR 4.13.2

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class ParticParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		T__45=46, T__46=47, T__47=48, T__48=49, T__49=50, T__50=51, T__51=52, 
		T__52=53, T__53=54, T__54=55, T__55=56, T__56=57, T__57=58, T__58=59, 
		T__59=60, T__60=61, T__61=62, T__62=63, T__63=64, T__64=65, T__65=66, 
		T__66=67, T__67=68, T__68=69, T__69=70, T__70=71, T__71=72, T__72=73, 
		T__73=74, T__74=75, T__75=76, T__76=77, T__77=78, T__78=79, T__79=80, 
		T__80=81, T__81=82, T__82=83, T__83=84, T__84=85, T__85=86, T__86=87, 
		T__87=88, T__88=89, T__89=90, T__90=91, T__91=92, T__92=93, T__93=94, 
		T__94=95, T__95=96, T__96=97, T__97=98, T__98=99, T__99=100, T__100=101, 
		T__101=102, T__102=103, T__103=104, T__104=105, T__105=106, T__106=107, 
		IntegerLiteral=108, FloatingPointLiteral=109, BooleanLiteral=110, CharacterLiteral=111, 
		StringLiteral=112, TextBlockLiteral=113, NullLiteral=114, IDENTIFIER=115, 
		WS=116, COMMENT=117, LINE_COMMENT=118;
	public static final int
		RULE_program = 0, RULE_importDeclaration = 1, RULE_typeDeclaration = 2, 
		RULE_classDeclaration = 3, RULE_interfaceDeclaration = 4, RULE_enumDeclaration = 5, 
		RULE_enumBody = 6, RULE_enumConstant = 7, RULE_enumBodyDeclaration = 8, 
		RULE_functionDeclaration = 9, RULE_modifiers = 10, RULE_modifier = 11, 
		RULE_typeParameters = 12, RULE_typeParameter = 13, RULE_typeBound = 14, 
		RULE_classBody = 15, RULE_classBodyDeclaration = 16, RULE_interfaceBody = 17, 
		RULE_interfaceMemberDeclaration = 18, RULE_fieldDeclaration = 19, RULE_variableDeclarator = 20, 
		RULE_constantDeclaration = 21, RULE_methodDeclaration = 22, RULE_abstractMethodDeclaration = 23, 
		RULE_defaultMethodDeclaration = 24, RULE_staticMethodDeclaration = 25, 
		RULE_constructorDeclaration = 26, RULE_constructorBody = 27, RULE_explicitConstructorInvocation = 28, 
		RULE_staticBlock = 29, RULE_annotation = 30, RULE_elementValuePairs = 31, 
		RULE_elementValuePair = 32, RULE_elementValue = 33, RULE_elementValueArrayInitializer = 34, 
		RULE_parameterList = 35, RULE_parameter = 36, RULE_type = 37, RULE_typeList = 38, 
		RULE_primitiveType = 39, RULE_classType = 40, RULE_typeArguments = 41, 
		RULE_typeArgument = 42, RULE_qualifiedName = 43, RULE_block = 44, RULE_statement = 45, 
		RULE_variableDeclarationStatement = 46, RULE_expressionStatement = 47, 
		RULE_ifStatement = 48, RULE_switchStatement = 49, RULE_switchCase = 50, 
		RULE_defaultCase = 51, RULE_whileStatement = 52, RULE_doWhileStatement = 53, 
		RULE_forStatement = 54, RULE_forInit = 55, RULE_forUpdate = 56, RULE_forEachStatement = 57, 
		RULE_returnStatement = 58, RULE_breakStatement = 59, RULE_continueStatement = 60, 
		RULE_throwStatement = 61, RULE_tryStatement = 62, RULE_catchClause = 63, 
		RULE_finallyBlock = 64, RULE_synchronizedStatement = 65, RULE_assertStatement = 66, 
		RULE_expressionList = 67, RULE_expression = 68, RULE_assignmentExpression = 69, 
		RULE_assignmentOperator = 70, RULE_conditionalExpression = 71, RULE_conditionalOrExpression = 72, 
		RULE_conditionalAndExpression = 73, RULE_inclusiveOrExpression = 74, RULE_exclusiveOrExpression = 75, 
		RULE_andExpression = 76, RULE_equalityExpression = 77, RULE_relationalExpression = 78, 
		RULE_shiftExpression = 79, RULE_additiveExpression = 80, RULE_multiplicativeExpression = 81, 
		RULE_unaryExpression = 82, RULE_castExpression = 83, RULE_postfixExpression = 84, 
		RULE_argumentList = 85, RULE_primary = 86, RULE_creator = 87, RULE_lambdaExpression = 88, 
		RULE_lambdaParameters = 89, RULE_lambdaBody = 90, RULE_methodReference = 91, 
		RULE_arrayInitializer = 92, RULE_literal = 93;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "importDeclaration", "typeDeclaration", "classDeclaration", 
			"interfaceDeclaration", "enumDeclaration", "enumBody", "enumConstant", 
			"enumBodyDeclaration", "functionDeclaration", "modifiers", "modifier", 
			"typeParameters", "typeParameter", "typeBound", "classBody", "classBodyDeclaration", 
			"interfaceBody", "interfaceMemberDeclaration", "fieldDeclaration", "variableDeclarator", 
			"constantDeclaration", "methodDeclaration", "abstractMethodDeclaration", 
			"defaultMethodDeclaration", "staticMethodDeclaration", "constructorDeclaration", 
			"constructorBody", "explicitConstructorInvocation", "staticBlock", "annotation", 
			"elementValuePairs", "elementValuePair", "elementValue", "elementValueArrayInitializer", 
			"parameterList", "parameter", "type", "typeList", "primitiveType", "classType", 
			"typeArguments", "typeArgument", "qualifiedName", "block", "statement", 
			"variableDeclarationStatement", "expressionStatement", "ifStatement", 
			"switchStatement", "switchCase", "defaultCase", "whileStatement", "doWhileStatement", 
			"forStatement", "forInit", "forUpdate", "forEachStatement", "returnStatement", 
			"breakStatement", "continueStatement", "throwStatement", "tryStatement", 
			"catchClause", "finallyBlock", "synchronizedStatement", "assertStatement", 
			"expressionList", "expression", "assignmentExpression", "assignmentOperator", 
			"conditionalExpression", "conditionalOrExpression", "conditionalAndExpression", 
			"inclusiveOrExpression", "exclusiveOrExpression", "andExpression", "equalityExpression", 
			"relationalExpression", "shiftExpression", "additiveExpression", "multiplicativeExpression", 
			"unaryExpression", "castExpression", "postfixExpression", "argumentList", 
			"primary", "creator", "lambdaExpression", "lambdaParameters", "lambdaBody", 
			"methodReference", "arrayInitializer", "literal"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'import'", "'as'", "';'", "'class'", "'extends'", "'implements'", 
			"'interface'", "'enum'", "'{'", "','", "'}'", "'('", "')'", "'->'", "'pub'", 
			"'priv'", "'prot'", "'pack'", "'static'", "'final'", "'abstract'", "'sealed'", 
			"'open'", "'override'", "'native'", "'synchronized'", "'transient'", 
			"'volatile'", "'<'", "'>'", "'&'", "'='", "'default'", "'this'", "'super'", 
			"'@'", "'['", "']'", "'int'", "'long'", "'short'", "'byte'", "'float'", 
			"'double'", "'char'", "'bool'", "'str'", "'void'", "'var'", "'val'", 
			"'?'", "'.'", "'if'", "'else'", "'switch'", "'case'", "':'", "'while'", 
			"'do'", "'for'", "'return'", "'break'", "'continue'", "'throw'", "'try'", 
			"'catch'", "'finally'", "'assert'", "'+='", "'-='", "'*='", "'/='", "'%='", 
			"'&='", "'|='", "'^='", "'<<='", "'>>='", "'>>>='", "'??='", "'??'", 
			"'||'", "'&&'", "'|'", "'^'", "'=='", "'!='", "'==='", "'!=='", "'<='", 
			"'>='", "'instanceof'", "'in'", "'<<'", "'>>'", "'>>>'", "'+'", "'-'", 
			"'*'", "'/'", "'%'", "'++'", "'--'", "'!'", "'~'", "'new'", "'::'", null, 
			null, null, null, null, null, "'null'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			"IntegerLiteral", "FloatingPointLiteral", "BooleanLiteral", "CharacterLiteral", 
			"StringLiteral", "TextBlockLiteral", "NullLiteral", "IDENTIFIER", "WS", 
			"COMMENT", "LINE_COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Partic.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ParticParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public List<ImportDeclarationContext> importDeclaration() {
			return getRuleContexts(ImportDeclarationContext.class);
		}
		public ImportDeclarationContext importDeclaration(int i) {
			return getRuleContext(ImportDeclarationContext.class,i);
		}
		public List<TypeDeclarationContext> typeDeclaration() {
			return getRuleContexts(TypeDeclarationContext.class);
		}
		public TypeDeclarationContext typeDeclaration(int i) {
			return getRuleContext(TypeDeclarationContext.class,i);
		}
		public List<FunctionDeclarationContext> functionDeclaration() {
			return getRuleContexts(FunctionDeclarationContext.class);
		}
		public FunctionDeclarationContext functionDeclaration(int i) {
			return getRuleContext(FunctionDeclarationContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(193);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2251319314186642L) != 0) || _la==IDENTIFIER) {
				{
				setState(191);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(188);
					importDeclaration();
					}
					break;
				case 2:
					{
					setState(189);
					typeDeclaration();
					}
					break;
				case 3:
					{
					setState(190);
					functionDeclaration();
					}
					break;
				}
				}
				setState(195);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ImportDeclarationContext extends ParserRuleContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(ParticParser.IDENTIFIER, 0); }
		public ImportDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitImportDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportDeclarationContext importDeclaration() throws RecognitionException {
		ImportDeclarationContext _localctx = new ImportDeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_importDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(196);
			match(T__0);
			setState(197);
			qualifiedName();
			setState(200);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(198);
				match(T__1);
				setState(199);
				match(IDENTIFIER);
				}
			}

			setState(202);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeDeclarationContext extends ParserRuleContext {
		public ClassDeclarationContext classDeclaration() {
			return getRuleContext(ClassDeclarationContext.class,0);
		}
		public InterfaceDeclarationContext interfaceDeclaration() {
			return getRuleContext(InterfaceDeclarationContext.class,0);
		}
		public EnumDeclarationContext enumDeclaration() {
			return getRuleContext(EnumDeclarationContext.class,0);
		}
		public TypeDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitTypeDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeDeclarationContext typeDeclaration() throws RecognitionException {
		TypeDeclarationContext _localctx = new TypeDeclarationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_typeDeclaration);
		try {
			setState(207);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(204);
				classDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(205);
				interfaceDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(206);
				enumDeclaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClassDeclarationContext extends ParserRuleContext {
		public ModifiersContext modifiers() {
			return getRuleContext(ModifiersContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(ParticParser.IDENTIFIER, 0); }
		public ClassBodyContext classBody() {
			return getRuleContext(ClassBodyContext.class,0);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public TypeParametersContext typeParameters() {
			return getRuleContext(TypeParametersContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TypeListContext typeList() {
			return getRuleContext(TypeListContext.class,0);
		}
		public ClassDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitClassDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassDeclarationContext classDeclaration() throws RecognitionException {
		ClassDeclarationContext _localctx = new ClassDeclarationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_classDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(212);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__35) {
				{
				{
				setState(209);
				annotation();
				}
				}
				setState(214);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(215);
			modifiers();
			setState(216);
			match(T__3);
			setState(217);
			match(IDENTIFIER);
			setState(219);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__28) {
				{
				setState(218);
				typeParameters();
				}
			}

			setState(223);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(221);
				match(T__4);
				setState(222);
				type();
				}
			}

			setState(227);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(225);
				match(T__5);
				setState(226);
				typeList();
				}
			}

			setState(229);
			classBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InterfaceDeclarationContext extends ParserRuleContext {
		public ModifiersContext modifiers() {
			return getRuleContext(ModifiersContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(ParticParser.IDENTIFIER, 0); }
		public InterfaceBodyContext interfaceBody() {
			return getRuleContext(InterfaceBodyContext.class,0);
		}
		public TypeParametersContext typeParameters() {
			return getRuleContext(TypeParametersContext.class,0);
		}
		public TypeListContext typeList() {
			return getRuleContext(TypeListContext.class,0);
		}
		public InterfaceDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interfaceDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitInterfaceDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InterfaceDeclarationContext interfaceDeclaration() throws RecognitionException {
		InterfaceDeclarationContext _localctx = new InterfaceDeclarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_interfaceDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(231);
			modifiers();
			setState(232);
			match(T__6);
			setState(233);
			match(IDENTIFIER);
			setState(235);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__28) {
				{
				setState(234);
				typeParameters();
				}
			}

			setState(239);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(237);
				match(T__4);
				setState(238);
				typeList();
				}
			}

			setState(241);
			interfaceBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EnumDeclarationContext extends ParserRuleContext {
		public ModifiersContext modifiers() {
			return getRuleContext(ModifiersContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(ParticParser.IDENTIFIER, 0); }
		public EnumBodyContext enumBody() {
			return getRuleContext(EnumBodyContext.class,0);
		}
		public TypeListContext typeList() {
			return getRuleContext(TypeListContext.class,0);
		}
		public EnumDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitEnumDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumDeclarationContext enumDeclaration() throws RecognitionException {
		EnumDeclarationContext _localctx = new EnumDeclarationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_enumDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(243);
			modifiers();
			setState(244);
			match(T__7);
			setState(245);
			match(IDENTIFIER);
			setState(248);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(246);
				match(T__5);
				setState(247);
				typeList();
				}
			}

			setState(250);
			enumBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EnumBodyContext extends ParserRuleContext {
		public List<EnumConstantContext> enumConstant() {
			return getRuleContexts(EnumConstantContext.class);
		}
		public EnumConstantContext enumConstant(int i) {
			return getRuleContext(EnumConstantContext.class,i);
		}
		public List<EnumBodyDeclarationContext> enumBodyDeclaration() {
			return getRuleContexts(EnumBodyDeclarationContext.class);
		}
		public EnumBodyDeclarationContext enumBodyDeclaration(int i) {
			return getRuleContext(EnumBodyDeclarationContext.class,i);
		}
		public EnumBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumBody; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitEnumBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumBodyContext enumBody() throws RecognitionException {
		EnumBodyContext _localctx = new EnumBodyContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_enumBody);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(252);
			match(T__8);
			setState(253);
			enumConstant();
			setState(258);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(254);
					match(T__9);
					setState(255);
					enumConstant();
					}
					} 
				}
				setState(260);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			}
			setState(268);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__9) {
				{
				setState(261);
				match(T__9);
				setState(265);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(262);
					enumBodyDeclaration();
					}
					}
					setState(267);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(270);
			match(T__10);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EnumConstantContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(ParticParser.IDENTIFIER, 0); }
		public ClassBodyContext classBody() {
			return getRuleContext(ClassBodyContext.class,0);
		}
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public EnumConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumConstant; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitEnumConstant(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumConstantContext enumConstant() throws RecognitionException {
		EnumConstantContext _localctx = new EnumConstantContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_enumConstant);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(272);
			match(IDENTIFIER);
			setState(278);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__11) {
				{
				setState(273);
				match(T__11);
				setState(275);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2251301597483008L) != 0) || ((((_la - 97)) & ~0x3f) == 0 && ((1L << (_la - 97)) & 523235L) != 0)) {
					{
					setState(274);
					argumentList();
					}
				}

				setState(277);
				match(T__12);
				}
			}

			setState(281);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__8) {
				{
				setState(280);
				classBody();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EnumBodyDeclarationContext extends ParserRuleContext {
		public List<ClassBodyDeclarationContext> classBodyDeclaration() {
			return getRuleContexts(ClassBodyDeclarationContext.class);
		}
		public ClassBodyDeclarationContext classBodyDeclaration(int i) {
			return getRuleContext(ClassBodyDeclarationContext.class,i);
		}
		public EnumBodyDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumBodyDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitEnumBodyDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumBodyDeclarationContext enumBodyDeclaration() throws RecognitionException {
		EnumBodyDeclarationContext _localctx = new EnumBodyDeclarationContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_enumBodyDeclaration);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(283);
			match(T__2);
			setState(287);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(284);
					classBodyDeclaration();
					}
					} 
				}
				setState(289);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FunctionDeclarationContext extends ParserRuleContext {
		public ModifiersContext modifiers() {
			return getRuleContext(ModifiersContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(ParticParser.IDENTIFIER, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public FunctionDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitFunctionDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDeclarationContext functionDeclaration() throws RecognitionException {
		FunctionDeclarationContext _localctx = new FunctionDeclarationContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_functionDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(290);
			modifiers();
			setState(291);
			type();
			setState(292);
			match(IDENTIFIER);
			setState(293);
			match(T__11);
			setState(295);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2251319314186240L) != 0) || _la==IDENTIFIER) {
				{
				setState(294);
				parameterList();
				}
			}

			setState(297);
			match(T__12);
			setState(303);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__8:
				{
				setState(298);
				block();
				}
				break;
			case T__13:
				{
				setState(299);
				match(T__13);
				setState(300);
				expression();
				setState(301);
				match(T__2);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ModifiersContext extends ParserRuleContext {
		public List<ModifierContext> modifier() {
			return getRuleContexts(ModifierContext.class);
		}
		public ModifierContext modifier(int i) {
			return getRuleContext(ModifierContext.class,i);
		}
		public ModifiersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modifiers; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitModifiers(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModifiersContext modifiers() throws RecognitionException {
		ModifiersContext _localctx = new ModifiersContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_modifiers);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(308);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(305);
					modifier();
					}
					} 
				}
				setState(310);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ModifierContext extends ParserRuleContext {
		public ModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modifier; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitModifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModifierContext modifier() throws RecognitionException {
		ModifierContext _localctx = new ModifierContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_modifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(311);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 536838144L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeParametersContext extends ParserRuleContext {
		public List<TypeParameterContext> typeParameter() {
			return getRuleContexts(TypeParameterContext.class);
		}
		public TypeParameterContext typeParameter(int i) {
			return getRuleContext(TypeParameterContext.class,i);
		}
		public TypeParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeParameters; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitTypeParameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeParametersContext typeParameters() throws RecognitionException {
		TypeParametersContext _localctx = new TypeParametersContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_typeParameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(313);
			match(T__28);
			setState(314);
			typeParameter();
			setState(319);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__9) {
				{
				{
				setState(315);
				match(T__9);
				setState(316);
				typeParameter();
				}
				}
				setState(321);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(322);
			match(T__29);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeParameterContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(ParticParser.IDENTIFIER, 0); }
		public TypeBoundContext typeBound() {
			return getRuleContext(TypeBoundContext.class,0);
		}
		public TypeParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeParameter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitTypeParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeParameterContext typeParameter() throws RecognitionException {
		TypeParameterContext _localctx = new TypeParameterContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_typeParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(324);
			match(IDENTIFIER);
			setState(327);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(325);
				match(T__4);
				setState(326);
				typeBound();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeBoundContext extends ParserRuleContext {
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public TypeBoundContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeBound; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitTypeBound(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeBoundContext typeBound() throws RecognitionException {
		TypeBoundContext _localctx = new TypeBoundContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_typeBound);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(329);
			type();
			setState(334);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__30) {
				{
				{
				setState(330);
				match(T__30);
				setState(331);
				type();
				}
				}
				setState(336);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClassBodyContext extends ParserRuleContext {
		public List<ClassBodyDeclarationContext> classBodyDeclaration() {
			return getRuleContexts(ClassBodyDeclarationContext.class);
		}
		public ClassBodyDeclarationContext classBodyDeclaration(int i) {
			return getRuleContext(ClassBodyDeclarationContext.class,i);
		}
		public ClassBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classBody; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitClassBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassBodyContext classBody() throws RecognitionException {
		ClassBodyContext _localctx = new ClassBodyContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_classBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(337);
			match(T__8);
			setState(341);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2251319314186248L) != 0) || _la==IDENTIFIER) {
				{
				{
				setState(338);
				classBodyDeclaration();
				}
				}
				setState(343);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(344);
			match(T__10);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClassBodyDeclarationContext extends ParserRuleContext {
		public FieldDeclarationContext fieldDeclaration() {
			return getRuleContext(FieldDeclarationContext.class,0);
		}
		public MethodDeclarationContext methodDeclaration() {
			return getRuleContext(MethodDeclarationContext.class,0);
		}
		public ConstructorDeclarationContext constructorDeclaration() {
			return getRuleContext(ConstructorDeclarationContext.class,0);
		}
		public StaticBlockContext staticBlock() {
			return getRuleContext(StaticBlockContext.class,0);
		}
		public ClassBodyDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classBodyDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitClassBodyDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassBodyDeclarationContext classBodyDeclaration() throws RecognitionException {
		ClassBodyDeclarationContext _localctx = new ClassBodyDeclarationContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_classBodyDeclaration);
		try {
			setState(351);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(346);
				fieldDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(347);
				methodDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(348);
				constructorDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(349);
				staticBlock();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(350);
				match(T__2);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InterfaceBodyContext extends ParserRuleContext {
		public List<InterfaceMemberDeclarationContext> interfaceMemberDeclaration() {
			return getRuleContexts(InterfaceMemberDeclarationContext.class);
		}
		public InterfaceMemberDeclarationContext interfaceMemberDeclaration(int i) {
			return getRuleContext(InterfaceMemberDeclarationContext.class,i);
		}
		public InterfaceBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interfaceBody; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitInterfaceBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InterfaceBodyContext interfaceBody() throws RecognitionException {
		InterfaceBodyContext _localctx = new InterfaceBodyContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_interfaceBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(353);
			match(T__8);
			setState(357);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2251259184644104L) != 0) || _la==IDENTIFIER) {
				{
				{
				setState(354);
				interfaceMemberDeclaration();
				}
				}
				setState(359);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(360);
			match(T__10);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InterfaceMemberDeclarationContext extends ParserRuleContext {
		public ConstantDeclarationContext constantDeclaration() {
			return getRuleContext(ConstantDeclarationContext.class,0);
		}
		public AbstractMethodDeclarationContext abstractMethodDeclaration() {
			return getRuleContext(AbstractMethodDeclarationContext.class,0);
		}
		public DefaultMethodDeclarationContext defaultMethodDeclaration() {
			return getRuleContext(DefaultMethodDeclarationContext.class,0);
		}
		public StaticMethodDeclarationContext staticMethodDeclaration() {
			return getRuleContext(StaticMethodDeclarationContext.class,0);
		}
		public InterfaceMemberDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interfaceMemberDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitInterfaceMemberDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InterfaceMemberDeclarationContext interfaceMemberDeclaration() throws RecognitionException {
		InterfaceMemberDeclarationContext _localctx = new InterfaceMemberDeclarationContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_interfaceMemberDeclaration);
		try {
			setState(367);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(362);
				constantDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(363);
				abstractMethodDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(364);
				defaultMethodDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(365);
				staticMethodDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(366);
				match(T__2);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FieldDeclarationContext extends ParserRuleContext {
		public ModifiersContext modifiers() {
			return getRuleContext(ModifiersContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<VariableDeclaratorContext> variableDeclarator() {
			return getRuleContexts(VariableDeclaratorContext.class);
		}
		public VariableDeclaratorContext variableDeclarator(int i) {
			return getRuleContext(VariableDeclaratorContext.class,i);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public FieldDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitFieldDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldDeclarationContext fieldDeclaration() throws RecognitionException {
		FieldDeclarationContext _localctx = new FieldDeclarationContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_fieldDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(372);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__35) {
				{
				{
				setState(369);
				annotation();
				}
				}
				setState(374);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(375);
			modifiers();
			setState(376);
			type();
			setState(377);
			variableDeclarator();
			setState(382);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__9) {
				{
				{
				setState(378);
				match(T__9);
				setState(379);
				variableDeclarator();
				}
				}
				setState(384);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(385);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VariableDeclaratorContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(ParticParser.IDENTIFIER, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VariableDeclaratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclarator; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitVariableDeclarator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclaratorContext variableDeclarator() throws RecognitionException {
		VariableDeclaratorContext _localctx = new VariableDeclaratorContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_variableDeclarator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(387);
			match(IDENTIFIER);
			setState(390);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__31) {
				{
				setState(388);
				match(T__31);
				setState(389);
				expression();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConstantDeclarationContext extends ParserRuleContext {
		public ModifiersContext modifiers() {
			return getRuleContext(ModifiersContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(ParticParser.IDENTIFIER, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ConstantDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constantDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitConstantDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstantDeclarationContext constantDeclaration() throws RecognitionException {
		ConstantDeclarationContext _localctx = new ConstantDeclarationContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_constantDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(392);
			modifiers();
			setState(393);
			type();
			setState(394);
			match(IDENTIFIER);
			setState(395);
			match(T__31);
			setState(396);
			expression();
			setState(397);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MethodDeclarationContext extends ParserRuleContext {
		public ModifiersContext modifiers() {
			return getRuleContext(ModifiersContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(ParticParser.IDENTIFIER, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public MethodDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitMethodDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodDeclarationContext methodDeclaration() throws RecognitionException {
		MethodDeclarationContext _localctx = new MethodDeclarationContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_methodDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(402);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__35) {
				{
				{
				setState(399);
				annotation();
				}
				}
				setState(404);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(405);
			modifiers();
			setState(406);
			type();
			setState(407);
			match(IDENTIFIER);
			setState(408);
			match(T__11);
			setState(410);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2251319314186240L) != 0) || _la==IDENTIFIER) {
				{
				setState(409);
				parameterList();
				}
			}

			setState(412);
			match(T__12);
			setState(419);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__8:
				{
				setState(413);
				block();
				}
				break;
			case T__13:
				{
				setState(414);
				match(T__13);
				setState(415);
				expression();
				setState(416);
				match(T__2);
				}
				break;
			case T__2:
				{
				setState(418);
				match(T__2);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AbstractMethodDeclarationContext extends ParserRuleContext {
		public ModifiersContext modifiers() {
			return getRuleContext(ModifiersContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(ParticParser.IDENTIFIER, 0); }
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public AbstractMethodDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_abstractMethodDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitAbstractMethodDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AbstractMethodDeclarationContext abstractMethodDeclaration() throws RecognitionException {
		AbstractMethodDeclarationContext _localctx = new AbstractMethodDeclarationContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_abstractMethodDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(421);
			modifiers();
			setState(422);
			type();
			setState(423);
			match(IDENTIFIER);
			setState(424);
			match(T__11);
			setState(426);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2251319314186240L) != 0) || _la==IDENTIFIER) {
				{
				setState(425);
				parameterList();
				}
			}

			setState(428);
			match(T__12);
			setState(429);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DefaultMethodDeclarationContext extends ParserRuleContext {
		public ModifiersContext modifiers() {
			return getRuleContext(ModifiersContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(ParticParser.IDENTIFIER, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public DefaultMethodDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_defaultMethodDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitDefaultMethodDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefaultMethodDeclarationContext defaultMethodDeclaration() throws RecognitionException {
		DefaultMethodDeclarationContext _localctx = new DefaultMethodDeclarationContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_defaultMethodDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(431);
			modifiers();
			setState(432);
			match(T__32);
			setState(433);
			type();
			setState(434);
			match(IDENTIFIER);
			setState(435);
			match(T__11);
			setState(437);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2251319314186240L) != 0) || _la==IDENTIFIER) {
				{
				setState(436);
				parameterList();
				}
			}

			setState(439);
			match(T__12);
			setState(445);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__8:
				{
				setState(440);
				block();
				}
				break;
			case T__13:
				{
				setState(441);
				match(T__13);
				setState(442);
				expression();
				setState(443);
				match(T__2);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StaticMethodDeclarationContext extends ParserRuleContext {
		public ModifiersContext modifiers() {
			return getRuleContext(ModifiersContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(ParticParser.IDENTIFIER, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public StaticMethodDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_staticMethodDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitStaticMethodDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StaticMethodDeclarationContext staticMethodDeclaration() throws RecognitionException {
		StaticMethodDeclarationContext _localctx = new StaticMethodDeclarationContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_staticMethodDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(447);
			modifiers();
			setState(448);
			match(T__18);
			setState(449);
			type();
			setState(450);
			match(IDENTIFIER);
			setState(451);
			match(T__11);
			setState(453);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2251319314186240L) != 0) || _la==IDENTIFIER) {
				{
				setState(452);
				parameterList();
				}
			}

			setState(455);
			match(T__12);
			setState(461);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__8:
				{
				setState(456);
				block();
				}
				break;
			case T__13:
				{
				setState(457);
				match(T__13);
				setState(458);
				expression();
				setState(459);
				match(T__2);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConstructorDeclarationContext extends ParserRuleContext {
		public ModifiersContext modifiers() {
			return getRuleContext(ModifiersContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(ParticParser.IDENTIFIER, 0); }
		public ConstructorBodyContext constructorBody() {
			return getRuleContext(ConstructorBodyContext.class,0);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public ConstructorDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructorDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitConstructorDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstructorDeclarationContext constructorDeclaration() throws RecognitionException {
		ConstructorDeclarationContext _localctx = new ConstructorDeclarationContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_constructorDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(466);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__35) {
				{
				{
				setState(463);
				annotation();
				}
				}
				setState(468);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(469);
			modifiers();
			setState(470);
			match(IDENTIFIER);
			setState(471);
			match(T__11);
			setState(473);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2251319314186240L) != 0) || _la==IDENTIFIER) {
				{
				setState(472);
				parameterList();
				}
			}

			setState(475);
			match(T__12);
			setState(476);
			constructorBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConstructorBodyContext extends ParserRuleContext {
		public ExplicitConstructorInvocationContext explicitConstructorInvocation() {
			return getRuleContext(ExplicitConstructorInvocationContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ConstructorBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructorBody; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitConstructorBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstructorBodyContext constructorBody() throws RecognitionException {
		ConstructorBodyContext _localctx = new ConstructorBodyContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_constructorBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(478);
			match(T__8);
			setState(480);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,41,_ctx) ) {
			case 1:
				{
				setState(479);
				explicitConstructorInvocation();
				}
				break;
			}
			setState(485);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & -240943077743685112L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 4494554426245139L) != 0)) {
				{
				{
				setState(482);
				statement();
				}
				}
				setState(487);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(488);
			match(T__10);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExplicitConstructorInvocationContext extends ParserRuleContext {
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public ExplicitConstructorInvocationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_explicitConstructorInvocation; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitExplicitConstructorInvocation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExplicitConstructorInvocationContext explicitConstructorInvocation() throws RecognitionException {
		ExplicitConstructorInvocationContext _localctx = new ExplicitConstructorInvocationContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_explicitConstructorInvocation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(495);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__28:
			case T__33:
				{
				setState(491);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__28) {
					{
					setState(490);
					typeArguments();
					}
				}

				setState(493);
				match(T__33);
				}
				break;
			case T__34:
				{
				setState(494);
				match(T__34);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(497);
			match(T__11);
			setState(499);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2251301597483008L) != 0) || ((((_la - 97)) & ~0x3f) == 0 && ((1L << (_la - 97)) & 523235L) != 0)) {
				{
				setState(498);
				argumentList();
				}
			}

			setState(501);
			match(T__12);
			setState(502);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StaticBlockContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public StaticBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_staticBlock; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitStaticBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StaticBlockContext staticBlock() throws RecognitionException {
		StaticBlockContext _localctx = new StaticBlockContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_staticBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(504);
			match(T__18);
			setState(505);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AnnotationContext extends ParserRuleContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public ElementValuePairsContext elementValuePairs() {
			return getRuleContext(ElementValuePairsContext.class,0);
		}
		public AnnotationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_annotation; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitAnnotation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnnotationContext annotation() throws RecognitionException {
		AnnotationContext _localctx = new AnnotationContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_annotation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(507);
			match(T__35);
			setState(508);
			qualifiedName();
			setState(514);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__11) {
				{
				setState(509);
				match(T__11);
				setState(511);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2251370316960256L) != 0) || ((((_la - 97)) & ~0x3f) == 0 && ((1L << (_la - 97)) & 523235L) != 0)) {
					{
					setState(510);
					elementValuePairs();
					}
				}

				setState(513);
				match(T__12);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ElementValuePairsContext extends ParserRuleContext {
		public List<ElementValuePairContext> elementValuePair() {
			return getRuleContexts(ElementValuePairContext.class);
		}
		public ElementValuePairContext elementValuePair(int i) {
			return getRuleContext(ElementValuePairContext.class,i);
		}
		public ElementValueContext elementValue() {
			return getRuleContext(ElementValueContext.class,0);
		}
		public ElementValuePairsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementValuePairs; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitElementValuePairs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementValuePairsContext elementValuePairs() throws RecognitionException {
		ElementValuePairsContext _localctx = new ElementValuePairsContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_elementValuePairs);
		int _la;
		try {
			setState(525);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,49,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(516);
				elementValuePair();
				setState(521);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__9) {
					{
					{
					setState(517);
					match(T__9);
					setState(518);
					elementValuePair();
					}
					}
					setState(523);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(524);
				elementValue();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ElementValuePairContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(ParticParser.IDENTIFIER, 0); }
		public ElementValueContext elementValue() {
			return getRuleContext(ElementValueContext.class,0);
		}
		public ElementValuePairContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementValuePair; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitElementValuePair(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementValuePairContext elementValuePair() throws RecognitionException {
		ElementValuePairContext _localctx = new ElementValuePairContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_elementValuePair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(527);
			match(IDENTIFIER);
			setState(528);
			match(T__31);
			setState(529);
			elementValue();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ElementValueContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AnnotationContext annotation() {
			return getRuleContext(AnnotationContext.class,0);
		}
		public ElementValueArrayInitializerContext elementValueArrayInitializer() {
			return getRuleContext(ElementValueArrayInitializerContext.class,0);
		}
		public ElementValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementValue; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitElementValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementValueContext elementValue() throws RecognitionException {
		ElementValueContext _localctx = new ElementValueContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_elementValue);
		try {
			setState(534);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__11:
			case T__33:
			case T__34:
			case T__38:
			case T__39:
			case T__40:
			case T__41:
			case T__42:
			case T__43:
			case T__44:
			case T__45:
			case T__46:
			case T__47:
			case T__48:
			case T__49:
			case T__96:
			case T__97:
			case T__101:
			case T__102:
			case T__103:
			case T__104:
			case T__105:
			case IntegerLiteral:
			case FloatingPointLiteral:
			case BooleanLiteral:
			case CharacterLiteral:
			case StringLiteral:
			case TextBlockLiteral:
			case NullLiteral:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(531);
				expression();
				}
				break;
			case T__35:
				enterOuterAlt(_localctx, 2);
				{
				setState(532);
				annotation();
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 3);
				{
				setState(533);
				elementValueArrayInitializer();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ElementValueArrayInitializerContext extends ParserRuleContext {
		public List<ElementValueContext> elementValue() {
			return getRuleContexts(ElementValueContext.class);
		}
		public ElementValueContext elementValue(int i) {
			return getRuleContext(ElementValueContext.class,i);
		}
		public ElementValueArrayInitializerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementValueArrayInitializer; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitElementValueArrayInitializer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementValueArrayInitializerContext elementValueArrayInitializer() throws RecognitionException {
		ElementValueArrayInitializerContext _localctx = new ElementValueArrayInitializerContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_elementValueArrayInitializer);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(536);
			match(T__8);
			setState(545);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2251370316960256L) != 0) || ((((_la - 97)) & ~0x3f) == 0 && ((1L << (_la - 97)) & 523235L) != 0)) {
				{
				setState(537);
				elementValue();
				setState(542);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__9) {
					{
					{
					setState(538);
					match(T__9);
					setState(539);
					elementValue();
					}
					}
					setState(544);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(547);
			match(T__10);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParameterListContext extends ParserRuleContext {
		public List<ParameterContext> parameter() {
			return getRuleContexts(ParameterContext.class);
		}
		public ParameterContext parameter(int i) {
			return getRuleContext(ParameterContext.class,i);
		}
		public ParameterListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitParameterList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterListContext parameterList() throws RecognitionException {
		ParameterListContext _localctx = new ParameterListContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_parameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(549);
			parameter();
			setState(554);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__9) {
				{
				{
				setState(550);
				match(T__9);
				setState(551);
				parameter();
				}
				}
				setState(556);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParameterContext extends ParserRuleContext {
		public ModifiersContext modifiers() {
			return getRuleContext(ModifiersContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(ParticParser.IDENTIFIER, 0); }
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterContext parameter() throws RecognitionException {
		ParameterContext _localctx = new ParameterContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_parameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(560);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__35) {
				{
				{
				setState(557);
				annotation();
				}
				}
				setState(562);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(563);
			modifiers();
			setState(564);
			type();
			setState(565);
			match(IDENTIFIER);
			setState(568);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__31) {
				{
				setState(566);
				match(T__31);
				setState(567);
				expression();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeContext extends ParserRuleContext {
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public ClassTypeContext classType() {
			return getRuleContext(ClassTypeContext.class,0);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_type);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(572);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__38:
			case T__39:
			case T__40:
			case T__41:
			case T__42:
			case T__43:
			case T__44:
			case T__45:
			case T__46:
			case T__47:
			case T__48:
			case T__49:
				{
				setState(570);
				primitiveType();
				}
				break;
			case IDENTIFIER:
				{
				setState(571);
				classType();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(578);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,57,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(574);
					match(T__36);
					setState(575);
					match(T__37);
					}
					} 
				}
				setState(580);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,57,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeListContext extends ParserRuleContext {
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public TypeListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitTypeList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeListContext typeList() throws RecognitionException {
		TypeListContext _localctx = new TypeListContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_typeList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(581);
			type();
			setState(586);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__9) {
				{
				{
				setState(582);
				match(T__9);
				setState(583);
				type();
				}
				}
				setState(588);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrimitiveTypeContext extends ParserRuleContext {
		public PrimitiveTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primitiveType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitPrimitiveType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimitiveTypeContext primitiveType() throws RecognitionException {
		PrimitiveTypeContext _localctx = new PrimitiveTypeContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_primitiveType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(589);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 2251250057871360L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClassTypeContext extends ParserRuleContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public ClassTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitClassType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassTypeContext classType() throws RecognitionException {
		ClassTypeContext _localctx = new ClassTypeContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_classType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(591);
			qualifiedName();
			setState(593);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__28) {
				{
				setState(592);
				typeArguments();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeArgumentsContext extends ParserRuleContext {
		public List<TypeArgumentContext> typeArgument() {
			return getRuleContexts(TypeArgumentContext.class);
		}
		public TypeArgumentContext typeArgument(int i) {
			return getRuleContext(TypeArgumentContext.class,i);
		}
		public TypeArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeArguments; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitTypeArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeArgumentsContext typeArguments() throws RecognitionException {
		TypeArgumentsContext _localctx = new TypeArgumentsContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_typeArguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(595);
			match(T__28);
			setState(596);
			typeArgument();
			setState(601);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__9) {
				{
				{
				setState(597);
				match(T__9);
				setState(598);
				typeArgument();
				}
				}
				setState(603);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(604);
			match(T__29);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeArgumentContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TypeArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeArgument; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitTypeArgument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeArgumentContext typeArgument() throws RecognitionException {
		TypeArgumentContext _localctx = new TypeArgumentContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_typeArgument);
		int _la;
		try {
			setState(612);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__38:
			case T__39:
			case T__40:
			case T__41:
			case T__42:
			case T__43:
			case T__44:
			case T__45:
			case T__46:
			case T__47:
			case T__48:
			case T__49:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(606);
				type();
				}
				break;
			case T__50:
				enterOuterAlt(_localctx, 2);
				{
				setState(607);
				match(T__50);
				setState(610);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__4 || _la==T__34) {
					{
					setState(608);
					_la = _input.LA(1);
					if ( !(_la==T__4 || _la==T__34) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(609);
					type();
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QualifiedNameContext extends ParserRuleContext {
		public List<TerminalNode> IDENTIFIER() { return getTokens(ParticParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(ParticParser.IDENTIFIER, i);
		}
		public QualifiedNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qualifiedName; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitQualifiedName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QualifiedNameContext qualifiedName() throws RecognitionException {
		QualifiedNameContext _localctx = new QualifiedNameContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_qualifiedName);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(614);
			match(IDENTIFIER);
			setState(619);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,63,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(615);
					match(T__51);
					setState(616);
					match(IDENTIFIER);
					}
					} 
				}
				setState(621);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,63,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BlockContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(622);
			match(T__8);
			setState(626);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & -240943077743685112L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 4494554426245139L) != 0)) {
				{
				{
				setState(623);
				statement();
				}
				}
				setState(628);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(629);
			match(T__10);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public VariableDeclarationStatementContext variableDeclarationStatement() {
			return getRuleContext(VariableDeclarationStatementContext.class,0);
		}
		public ExpressionStatementContext expressionStatement() {
			return getRuleContext(ExpressionStatementContext.class,0);
		}
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public SwitchStatementContext switchStatement() {
			return getRuleContext(SwitchStatementContext.class,0);
		}
		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}
		public DoWhileStatementContext doWhileStatement() {
			return getRuleContext(DoWhileStatementContext.class,0);
		}
		public ForStatementContext forStatement() {
			return getRuleContext(ForStatementContext.class,0);
		}
		public ForEachStatementContext forEachStatement() {
			return getRuleContext(ForEachStatementContext.class,0);
		}
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public BreakStatementContext breakStatement() {
			return getRuleContext(BreakStatementContext.class,0);
		}
		public ContinueStatementContext continueStatement() {
			return getRuleContext(ContinueStatementContext.class,0);
		}
		public ThrowStatementContext throwStatement() {
			return getRuleContext(ThrowStatementContext.class,0);
		}
		public TryStatementContext tryStatement() {
			return getRuleContext(TryStatementContext.class,0);
		}
		public SynchronizedStatementContext synchronizedStatement() {
			return getRuleContext(SynchronizedStatementContext.class,0);
		}
		public AssertStatementContext assertStatement() {
			return getRuleContext(AssertStatementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_statement);
		try {
			setState(648);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,65,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(631);
				block();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(632);
				variableDeclarationStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(633);
				expressionStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(634);
				ifStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(635);
				switchStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(636);
				whileStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(637);
				doWhileStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(638);
				forStatement();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(639);
				forEachStatement();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(640);
				returnStatement();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(641);
				breakStatement();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(642);
				continueStatement();
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(643);
				throwStatement();
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(644);
				tryStatement();
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(645);
				synchronizedStatement();
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(646);
				assertStatement();
				}
				break;
			case 17:
				enterOuterAlt(_localctx, 17);
				{
				setState(647);
				match(T__2);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VariableDeclarationStatementContext extends ParserRuleContext {
		public ModifiersContext modifiers() {
			return getRuleContext(ModifiersContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<VariableDeclaratorContext> variableDeclarator() {
			return getRuleContexts(VariableDeclaratorContext.class);
		}
		public VariableDeclaratorContext variableDeclarator(int i) {
			return getRuleContext(VariableDeclaratorContext.class,i);
		}
		public VariableDeclarationStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclarationStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitVariableDeclarationStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclarationStatementContext variableDeclarationStatement() throws RecognitionException {
		VariableDeclarationStatementContext _localctx = new VariableDeclarationStatementContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_variableDeclarationStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(650);
			modifiers();
			setState(651);
			type();
			setState(652);
			variableDeclarator();
			setState(657);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__9) {
				{
				{
				setState(653);
				match(T__9);
				setState(654);
				variableDeclarator();
				}
				}
				setState(659);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(660);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExpressionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitExpressionStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionStatementContext expressionStatement() throws RecognitionException {
		ExpressionStatementContext _localctx = new ExpressionStatementContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_expressionStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(662);
			expression();
			setState(663);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IfStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_ifStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(665);
			match(T__52);
			setState(666);
			match(T__11);
			setState(667);
			expression();
			setState(668);
			match(T__12);
			setState(669);
			statement();
			setState(672);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,67,_ctx) ) {
			case 1:
				{
				setState(670);
				match(T__53);
				setState(671);
				statement();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SwitchStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<SwitchCaseContext> switchCase() {
			return getRuleContexts(SwitchCaseContext.class);
		}
		public SwitchCaseContext switchCase(int i) {
			return getRuleContext(SwitchCaseContext.class,i);
		}
		public DefaultCaseContext defaultCase() {
			return getRuleContext(DefaultCaseContext.class,0);
		}
		public SwitchStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switchStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitSwitchStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SwitchStatementContext switchStatement() throws RecognitionException {
		SwitchStatementContext _localctx = new SwitchStatementContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_switchStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(674);
			match(T__54);
			setState(675);
			match(T__11);
			setState(676);
			expression();
			setState(677);
			match(T__12);
			setState(678);
			match(T__8);
			setState(682);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__55) {
				{
				{
				setState(679);
				switchCase();
				}
				}
				setState(684);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(686);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__32) {
				{
				setState(685);
				defaultCase();
				}
			}

			setState(688);
			match(T__10);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SwitchCaseContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public SwitchCaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switchCase; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitSwitchCase(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SwitchCaseContext switchCase() throws RecognitionException {
		SwitchCaseContext _localctx = new SwitchCaseContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_switchCase);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(690);
			match(T__55);
			setState(691);
			expression();
			setState(692);
			match(T__56);
			setState(696);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & -240943077743685112L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 4494554426245139L) != 0)) {
				{
				{
				setState(693);
				statement();
				}
				}
				setState(698);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DefaultCaseContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public DefaultCaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_defaultCase; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitDefaultCase(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefaultCaseContext defaultCase() throws RecognitionException {
		DefaultCaseContext _localctx = new DefaultCaseContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_defaultCase);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(699);
			match(T__32);
			setState(700);
			match(T__56);
			setState(704);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & -240943077743685112L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 4494554426245139L) != 0)) {
				{
				{
				setState(701);
				statement();
				}
				}
				setState(706);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WhileStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public WhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStatementContext whileStatement() throws RecognitionException {
		WhileStatementContext _localctx = new WhileStatementContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(707);
			match(T__57);
			setState(708);
			match(T__11);
			setState(709);
			expression();
			setState(710);
			match(T__12);
			setState(711);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DoWhileStatementContext extends ParserRuleContext {
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public DoWhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_doWhileStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitDoWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DoWhileStatementContext doWhileStatement() throws RecognitionException {
		DoWhileStatementContext _localctx = new DoWhileStatementContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_doWhileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(713);
			match(T__58);
			setState(714);
			statement();
			setState(715);
			match(T__57);
			setState(716);
			match(T__11);
			setState(717);
			expression();
			setState(718);
			match(T__12);
			setState(719);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ForStatementContext extends ParserRuleContext {
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ForInitContext forInit() {
			return getRuleContext(ForInitContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ForUpdateContext forUpdate() {
			return getRuleContext(ForUpdateContext.class,0);
		}
		public ForStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitForStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForStatementContext forStatement() throws RecognitionException {
		ForStatementContext _localctx = new ForStatementContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_forStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(721);
			match(T__59);
			setState(722);
			match(T__11);
			setState(724);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2251302134321152L) != 0) || ((((_la - 97)) & ~0x3f) == 0 && ((1L << (_la - 97)) & 523235L) != 0)) {
				{
				setState(723);
				forInit();
				}
			}

			setState(726);
			match(T__2);
			setState(728);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2251301597483008L) != 0) || ((((_la - 97)) & ~0x3f) == 0 && ((1L << (_la - 97)) & 523235L) != 0)) {
				{
				setState(727);
				expression();
				}
			}

			setState(730);
			match(T__2);
			setState(732);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2251301597483008L) != 0) || ((((_la - 97)) & ~0x3f) == 0 && ((1L << (_la - 97)) & 523235L) != 0)) {
				{
				setState(731);
				forUpdate();
				}
			}

			setState(734);
			match(T__12);
			setState(735);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ForInitContext extends ParserRuleContext {
		public VariableDeclarationStatementContext variableDeclarationStatement() {
			return getRuleContext(VariableDeclarationStatementContext.class,0);
		}
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public ForInitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forInit; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitForInit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForInitContext forInit() throws RecognitionException {
		ForInitContext _localctx = new ForInitContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_forInit);
		try {
			setState(739);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,75,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(737);
				variableDeclarationStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(738);
				expressionList();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ForUpdateContext extends ParserRuleContext {
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public ForUpdateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forUpdate; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitForUpdate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForUpdateContext forUpdate() throws RecognitionException {
		ForUpdateContext _localctx = new ForUpdateContext(_ctx, getState());
		enterRule(_localctx, 112, RULE_forUpdate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(741);
			expressionList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ForEachStatementContext extends ParserRuleContext {
		public ModifiersContext modifiers() {
			return getRuleContext(ModifiersContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(ParticParser.IDENTIFIER, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ForEachStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forEachStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitForEachStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForEachStatementContext forEachStatement() throws RecognitionException {
		ForEachStatementContext _localctx = new ForEachStatementContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_forEachStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(743);
			match(T__59);
			setState(744);
			match(T__11);
			setState(745);
			modifiers();
			setState(746);
			type();
			setState(747);
			match(IDENTIFIER);
			setState(748);
			match(T__56);
			setState(749);
			expression();
			setState(750);
			match(T__12);
			setState(751);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ReturnStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitReturnStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_returnStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(753);
			match(T__60);
			setState(755);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2251301597483008L) != 0) || ((((_la - 97)) & ~0x3f) == 0 && ((1L << (_la - 97)) & 523235L) != 0)) {
				{
				setState(754);
				expression();
				}
			}

			setState(757);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BreakStatementContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(ParticParser.IDENTIFIER, 0); }
		public BreakStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_breakStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitBreakStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BreakStatementContext breakStatement() throws RecognitionException {
		BreakStatementContext _localctx = new BreakStatementContext(_ctx, getState());
		enterRule(_localctx, 118, RULE_breakStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(759);
			match(T__61);
			setState(761);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IDENTIFIER) {
				{
				setState(760);
				match(IDENTIFIER);
				}
			}

			setState(763);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ContinueStatementContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(ParticParser.IDENTIFIER, 0); }
		public ContinueStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_continueStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitContinueStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ContinueStatementContext continueStatement() throws RecognitionException {
		ContinueStatementContext _localctx = new ContinueStatementContext(_ctx, getState());
		enterRule(_localctx, 120, RULE_continueStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(765);
			match(T__62);
			setState(767);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IDENTIFIER) {
				{
				setState(766);
				match(IDENTIFIER);
				}
			}

			setState(769);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ThrowStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ThrowStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_throwStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitThrowStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ThrowStatementContext throwStatement() throws RecognitionException {
		ThrowStatementContext _localctx = new ThrowStatementContext(_ctx, getState());
		enterRule(_localctx, 122, RULE_throwStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(771);
			match(T__63);
			setState(772);
			expression();
			setState(773);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TryStatementContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public FinallyBlockContext finallyBlock() {
			return getRuleContext(FinallyBlockContext.class,0);
		}
		public List<CatchClauseContext> catchClause() {
			return getRuleContexts(CatchClauseContext.class);
		}
		public CatchClauseContext catchClause(int i) {
			return getRuleContext(CatchClauseContext.class,i);
		}
		public TryStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tryStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitTryStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TryStatementContext tryStatement() throws RecognitionException {
		TryStatementContext _localctx = new TryStatementContext(_ctx, getState());
		enterRule(_localctx, 124, RULE_tryStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(775);
			match(T__64);
			setState(776);
			block();
			setState(786);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__65:
				{
				setState(778); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(777);
					catchClause();
					}
					}
					setState(780); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__65 );
				setState(783);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__66) {
					{
					setState(782);
					finallyBlock();
					}
				}

				}
				break;
			case T__66:
				{
				setState(785);
				finallyBlock();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CatchClauseContext extends ParserRuleContext {
		public ModifiersContext modifiers() {
			return getRuleContext(ModifiersContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(ParticParser.IDENTIFIER, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public CatchClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_catchClause; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitCatchClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CatchClauseContext catchClause() throws RecognitionException {
		CatchClauseContext _localctx = new CatchClauseContext(_ctx, getState());
		enterRule(_localctx, 126, RULE_catchClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(788);
			match(T__65);
			setState(789);
			match(T__11);
			setState(790);
			modifiers();
			setState(791);
			type();
			setState(792);
			match(IDENTIFIER);
			setState(793);
			match(T__12);
			setState(794);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FinallyBlockContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public FinallyBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_finallyBlock; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitFinallyBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FinallyBlockContext finallyBlock() throws RecognitionException {
		FinallyBlockContext _localctx = new FinallyBlockContext(_ctx, getState());
		enterRule(_localctx, 128, RULE_finallyBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(796);
			match(T__66);
			setState(797);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SynchronizedStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public SynchronizedStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_synchronizedStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitSynchronizedStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SynchronizedStatementContext synchronizedStatement() throws RecognitionException {
		SynchronizedStatementContext _localctx = new SynchronizedStatementContext(_ctx, getState());
		enterRule(_localctx, 130, RULE_synchronizedStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(799);
			match(T__25);
			setState(800);
			match(T__11);
			setState(801);
			expression();
			setState(802);
			match(T__12);
			setState(803);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AssertStatementContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public AssertStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assertStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitAssertStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssertStatementContext assertStatement() throws RecognitionException {
		AssertStatementContext _localctx = new AssertStatementContext(_ctx, getState());
		enterRule(_localctx, 132, RULE_assertStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(805);
			match(T__67);
			setState(806);
			expression();
			setState(809);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__56) {
				{
				setState(807);
				match(T__56);
				setState(808);
				expression();
				}
			}

			setState(811);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionListContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ExpressionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitExpressionList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionListContext expressionList() throws RecognitionException {
		ExpressionListContext _localctx = new ExpressionListContext(_ctx, getState());
		enterRule(_localctx, 134, RULE_expressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(813);
			expression();
			setState(818);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__9) {
				{
				{
				setState(814);
				match(T__9);
				setState(815);
				expression();
				}
				}
				setState(820);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public AssignmentExpressionContext assignmentExpression() {
			return getRuleContext(AssignmentExpressionContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 136, RULE_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(821);
			assignmentExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AssignmentExpressionContext extends ParserRuleContext {
		public ConditionalExpressionContext conditionalExpression() {
			return getRuleContext(ConditionalExpressionContext.class,0);
		}
		public PostfixExpressionContext postfixExpression() {
			return getRuleContext(PostfixExpressionContext.class,0);
		}
		public AssignmentOperatorContext assignmentOperator() {
			return getRuleContext(AssignmentOperatorContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AssignmentExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignmentExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitAssignmentExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentExpressionContext assignmentExpression() throws RecognitionException {
		AssignmentExpressionContext _localctx = new AssignmentExpressionContext(_ctx, getState());
		enterRule(_localctx, 138, RULE_assignmentExpression);
		try {
			setState(828);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,84,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(823);
				conditionalExpression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(824);
				postfixExpression(0);
				setState(825);
				assignmentOperator();
				setState(826);
				expression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AssignmentOperatorContext extends ParserRuleContext {
		public AssignmentOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignmentOperator; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitAssignmentOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentOperatorContext assignmentOperator() throws RecognitionException {
		AssignmentOperatorContext _localctx = new AssignmentOperatorContext(_ctx, getState());
		enterRule(_localctx, 140, RULE_assignmentOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(830);
			_la = _input.LA(1);
			if ( !(((((_la - 32)) & ~0x3f) == 0 && ((1L << (_la - 32)) & 562812514467841L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConditionalExpressionContext extends ParserRuleContext {
		public ConditionalOrExpressionContext conditionalOrExpression() {
			return getRuleContext(ConditionalOrExpressionContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ConditionalExpressionContext conditionalExpression() {
			return getRuleContext(ConditionalExpressionContext.class,0);
		}
		public ConditionalExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditionalExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitConditionalExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionalExpressionContext conditionalExpression() throws RecognitionException {
		ConditionalExpressionContext _localctx = new ConditionalExpressionContext(_ctx, getState());
		enterRule(_localctx, 142, RULE_conditionalExpression);
		try {
			setState(844);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,86,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(832);
				conditionalOrExpression();
				setState(838);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,85,_ctx) ) {
				case 1:
					{
					setState(833);
					match(T__50);
					setState(834);
					expression();
					setState(835);
					match(T__56);
					setState(836);
					conditionalExpression();
					}
					break;
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(840);
				conditionalOrExpression();
				setState(841);
				match(T__80);
				setState(842);
				conditionalExpression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConditionalOrExpressionContext extends ParserRuleContext {
		public List<ConditionalAndExpressionContext> conditionalAndExpression() {
			return getRuleContexts(ConditionalAndExpressionContext.class);
		}
		public ConditionalAndExpressionContext conditionalAndExpression(int i) {
			return getRuleContext(ConditionalAndExpressionContext.class,i);
		}
		public ConditionalOrExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditionalOrExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitConditionalOrExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionalOrExpressionContext conditionalOrExpression() throws RecognitionException {
		ConditionalOrExpressionContext _localctx = new ConditionalOrExpressionContext(_ctx, getState());
		enterRule(_localctx, 144, RULE_conditionalOrExpression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(846);
			conditionalAndExpression();
			setState(851);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,87,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(847);
					match(T__81);
					setState(848);
					conditionalAndExpression();
					}
					} 
				}
				setState(853);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,87,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConditionalAndExpressionContext extends ParserRuleContext {
		public List<InclusiveOrExpressionContext> inclusiveOrExpression() {
			return getRuleContexts(InclusiveOrExpressionContext.class);
		}
		public InclusiveOrExpressionContext inclusiveOrExpression(int i) {
			return getRuleContext(InclusiveOrExpressionContext.class,i);
		}
		public ConditionalAndExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditionalAndExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitConditionalAndExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionalAndExpressionContext conditionalAndExpression() throws RecognitionException {
		ConditionalAndExpressionContext _localctx = new ConditionalAndExpressionContext(_ctx, getState());
		enterRule(_localctx, 146, RULE_conditionalAndExpression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(854);
			inclusiveOrExpression();
			setState(859);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,88,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(855);
					match(T__82);
					setState(856);
					inclusiveOrExpression();
					}
					} 
				}
				setState(861);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,88,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InclusiveOrExpressionContext extends ParserRuleContext {
		public List<ExclusiveOrExpressionContext> exclusiveOrExpression() {
			return getRuleContexts(ExclusiveOrExpressionContext.class);
		}
		public ExclusiveOrExpressionContext exclusiveOrExpression(int i) {
			return getRuleContext(ExclusiveOrExpressionContext.class,i);
		}
		public InclusiveOrExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inclusiveOrExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitInclusiveOrExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InclusiveOrExpressionContext inclusiveOrExpression() throws RecognitionException {
		InclusiveOrExpressionContext _localctx = new InclusiveOrExpressionContext(_ctx, getState());
		enterRule(_localctx, 148, RULE_inclusiveOrExpression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(862);
			exclusiveOrExpression();
			setState(867);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,89,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(863);
					match(T__83);
					setState(864);
					exclusiveOrExpression();
					}
					} 
				}
				setState(869);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,89,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExclusiveOrExpressionContext extends ParserRuleContext {
		public List<AndExpressionContext> andExpression() {
			return getRuleContexts(AndExpressionContext.class);
		}
		public AndExpressionContext andExpression(int i) {
			return getRuleContext(AndExpressionContext.class,i);
		}
		public ExclusiveOrExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exclusiveOrExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitExclusiveOrExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExclusiveOrExpressionContext exclusiveOrExpression() throws RecognitionException {
		ExclusiveOrExpressionContext _localctx = new ExclusiveOrExpressionContext(_ctx, getState());
		enterRule(_localctx, 150, RULE_exclusiveOrExpression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(870);
			andExpression();
			setState(875);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,90,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(871);
					match(T__84);
					setState(872);
					andExpression();
					}
					} 
				}
				setState(877);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,90,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AndExpressionContext extends ParserRuleContext {
		public List<EqualityExpressionContext> equalityExpression() {
			return getRuleContexts(EqualityExpressionContext.class);
		}
		public EqualityExpressionContext equalityExpression(int i) {
			return getRuleContext(EqualityExpressionContext.class,i);
		}
		public AndExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_andExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitAndExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AndExpressionContext andExpression() throws RecognitionException {
		AndExpressionContext _localctx = new AndExpressionContext(_ctx, getState());
		enterRule(_localctx, 152, RULE_andExpression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(878);
			equalityExpression();
			setState(883);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,91,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(879);
					match(T__30);
					setState(880);
					equalityExpression();
					}
					} 
				}
				setState(885);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,91,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EqualityExpressionContext extends ParserRuleContext {
		public List<RelationalExpressionContext> relationalExpression() {
			return getRuleContexts(RelationalExpressionContext.class);
		}
		public RelationalExpressionContext relationalExpression(int i) {
			return getRuleContext(RelationalExpressionContext.class,i);
		}
		public EqualityExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equalityExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitEqualityExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EqualityExpressionContext equalityExpression() throws RecognitionException {
		EqualityExpressionContext _localctx = new EqualityExpressionContext(_ctx, getState());
		enterRule(_localctx, 154, RULE_equalityExpression);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(886);
			relationalExpression();
			setState(891);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,92,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(887);
					_la = _input.LA(1);
					if ( !(((((_la - 86)) & ~0x3f) == 0 && ((1L << (_la - 86)) & 15L) != 0)) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(888);
					relationalExpression();
					}
					} 
				}
				setState(893);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,92,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RelationalExpressionContext extends ParserRuleContext {
		public List<ShiftExpressionContext> shiftExpression() {
			return getRuleContexts(ShiftExpressionContext.class);
		}
		public ShiftExpressionContext shiftExpression(int i) {
			return getRuleContext(ShiftExpressionContext.class,i);
		}
		public RelationalExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relationalExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitRelationalExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelationalExpressionContext relationalExpression() throws RecognitionException {
		RelationalExpressionContext _localctx = new RelationalExpressionContext(_ctx, getState());
		enterRule(_localctx, 156, RULE_relationalExpression);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(894);
			shiftExpression();
			setState(899);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,93,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(895);
					_la = _input.LA(1);
					if ( !(_la==T__28 || _la==T__29 || ((((_la - 90)) & ~0x3f) == 0 && ((1L << (_la - 90)) & 15L) != 0)) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(896);
					shiftExpression();
					}
					} 
				}
				setState(901);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,93,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ShiftExpressionContext extends ParserRuleContext {
		public List<AdditiveExpressionContext> additiveExpression() {
			return getRuleContexts(AdditiveExpressionContext.class);
		}
		public AdditiveExpressionContext additiveExpression(int i) {
			return getRuleContext(AdditiveExpressionContext.class,i);
		}
		public ShiftExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shiftExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitShiftExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShiftExpressionContext shiftExpression() throws RecognitionException {
		ShiftExpressionContext _localctx = new ShiftExpressionContext(_ctx, getState());
		enterRule(_localctx, 158, RULE_shiftExpression);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(902);
			additiveExpression();
			setState(907);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,94,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(903);
					_la = _input.LA(1);
					if ( !(((((_la - 94)) & ~0x3f) == 0 && ((1L << (_la - 94)) & 7L) != 0)) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(904);
					additiveExpression();
					}
					} 
				}
				setState(909);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,94,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AdditiveExpressionContext extends ParserRuleContext {
		public List<MultiplicativeExpressionContext> multiplicativeExpression() {
			return getRuleContexts(MultiplicativeExpressionContext.class);
		}
		public MultiplicativeExpressionContext multiplicativeExpression(int i) {
			return getRuleContext(MultiplicativeExpressionContext.class,i);
		}
		public AdditiveExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additiveExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitAdditiveExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AdditiveExpressionContext additiveExpression() throws RecognitionException {
		AdditiveExpressionContext _localctx = new AdditiveExpressionContext(_ctx, getState());
		enterRule(_localctx, 160, RULE_additiveExpression);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(910);
			multiplicativeExpression();
			setState(915);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,95,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(911);
					_la = _input.LA(1);
					if ( !(_la==T__96 || _la==T__97) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(912);
					multiplicativeExpression();
					}
					} 
				}
				setState(917);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,95,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MultiplicativeExpressionContext extends ParserRuleContext {
		public List<UnaryExpressionContext> unaryExpression() {
			return getRuleContexts(UnaryExpressionContext.class);
		}
		public UnaryExpressionContext unaryExpression(int i) {
			return getRuleContext(UnaryExpressionContext.class,i);
		}
		public MultiplicativeExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplicativeExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitMultiplicativeExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiplicativeExpressionContext multiplicativeExpression() throws RecognitionException {
		MultiplicativeExpressionContext _localctx = new MultiplicativeExpressionContext(_ctx, getState());
		enterRule(_localctx, 162, RULE_multiplicativeExpression);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(918);
			unaryExpression();
			setState(923);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,96,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(919);
					_la = _input.LA(1);
					if ( !(((((_la - 99)) & ~0x3f) == 0 && ((1L << (_la - 99)) & 7L) != 0)) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(920);
					unaryExpression();
					}
					} 
				}
				setState(925);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,96,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class UnaryExpressionContext extends ParserRuleContext {
		public UnaryExpressionContext unaryExpression() {
			return getRuleContext(UnaryExpressionContext.class,0);
		}
		public CastExpressionContext castExpression() {
			return getRuleContext(CastExpressionContext.class,0);
		}
		public PostfixExpressionContext postfixExpression() {
			return getRuleContext(PostfixExpressionContext.class,0);
		}
		public UnaryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitUnaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryExpressionContext unaryExpression() throws RecognitionException {
		UnaryExpressionContext _localctx = new UnaryExpressionContext(_ctx, getState());
		enterRule(_localctx, 164, RULE_unaryExpression);
		int _la;
		try {
			setState(930);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,97,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(926);
				_la = _input.LA(1);
				if ( !(((((_la - 97)) & ~0x3f) == 0 && ((1L << (_la - 97)) & 483L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(927);
				unaryExpression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(928);
				castExpression();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(929);
				postfixExpression(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CastExpressionContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public UnaryExpressionContext unaryExpression() {
			return getRuleContext(UnaryExpressionContext.class,0);
		}
		public CastExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_castExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitCastExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CastExpressionContext castExpression() throws RecognitionException {
		CastExpressionContext _localctx = new CastExpressionContext(_ctx, getState());
		enterRule(_localctx, 166, RULE_castExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(932);
			match(T__11);
			setState(933);
			type();
			setState(934);
			match(T__12);
			setState(935);
			unaryExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PostfixExpressionContext extends ParserRuleContext {
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public PostfixExpressionContext postfixExpression() {
			return getRuleContext(PostfixExpressionContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(ParticParser.IDENTIFIER, 0); }
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public PostfixExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_postfixExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitPostfixExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PostfixExpressionContext postfixExpression() throws RecognitionException {
		return postfixExpression(0);
	}

	private PostfixExpressionContext postfixExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		PostfixExpressionContext _localctx = new PostfixExpressionContext(_ctx, _parentState);
		PostfixExpressionContext _prevctx = _localctx;
		int _startState = 168;
		enterRecursionRule(_localctx, 168, RULE_postfixExpression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(938);
			primary();
			}
			_ctx.stop = _input.LT(-1);
			setState(970);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,101,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(968);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,100,_ctx) ) {
					case 1:
						{
						_localctx = new PostfixExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_postfixExpression);
						setState(940);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(941);
						match(T__51);
						setState(942);
						match(IDENTIFIER);
						setState(943);
						match(T__11);
						setState(945);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2251301597483008L) != 0) || ((((_la - 97)) & ~0x3f) == 0 && ((1L << (_la - 97)) & 523235L) != 0)) {
							{
							setState(944);
							argumentList();
							}
						}

						setState(947);
						match(T__12);
						}
						break;
					case 2:
						{
						_localctx = new PostfixExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_postfixExpression);
						setState(948);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(949);
						match(T__36);
						setState(950);
						expression();
						setState(951);
						match(T__37);
						}
						break;
					case 3:
						{
						_localctx = new PostfixExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_postfixExpression);
						setState(953);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(954);
						match(T__11);
						setState(956);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2251301597483008L) != 0) || ((((_la - 97)) & ~0x3f) == 0 && ((1L << (_la - 97)) & 523235L) != 0)) {
							{
							setState(955);
							argumentList();
							}
						}

						setState(958);
						match(T__12);
						}
						break;
					case 4:
						{
						_localctx = new PostfixExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_postfixExpression);
						setState(959);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(960);
						match(T__51);
						setState(961);
						match(IDENTIFIER);
						}
						break;
					case 5:
						{
						_localctx = new PostfixExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_postfixExpression);
						setState(962);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(963);
						match(T__101);
						}
						break;
					case 6:
						{
						_localctx = new PostfixExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_postfixExpression);
						setState(964);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(965);
						match(T__102);
						}
						break;
					case 7:
						{
						_localctx = new PostfixExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_postfixExpression);
						setState(966);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(967);
						match(T__103);
						}
						break;
					}
					} 
				}
				setState(972);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,101,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArgumentListContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ArgumentListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argumentList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitArgumentList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentListContext argumentList() throws RecognitionException {
		ArgumentListContext _localctx = new ArgumentListContext(_ctx, getState());
		enterRule(_localctx, 170, RULE_argumentList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(973);
			expression();
			setState(978);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__9) {
				{
				{
				setState(974);
				match(T__9);
				setState(975);
				expression();
				}
				}
				setState(980);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryContext extends ParserRuleContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(ParticParser.IDENTIFIER, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public CreatorContext creator() {
			return getRuleContext(CreatorContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public LambdaExpressionContext lambdaExpression() {
			return getRuleContext(LambdaExpressionContext.class,0);
		}
		public MethodReferenceContext methodReference() {
			return getRuleContext(MethodReferenceContext.class,0);
		}
		public PrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitPrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryContext primary() throws RecognitionException {
		PrimaryContext _localctx = new PrimaryContext(_ctx, getState());
		enterRule(_localctx, 172, RULE_primary);
		try {
			setState(1007);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,103,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(981);
				literal();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(982);
				match(IDENTIFIER);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(983);
				match(T__33);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(984);
				match(T__34);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(985);
				match(T__11);
				setState(986);
				expression();
				setState(987);
				match(T__12);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(989);
				match(T__105);
				setState(990);
				creator();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(991);
				type();
				setState(992);
				match(T__51);
				setState(993);
				match(T__3);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(995);
				type();
				setState(996);
				match(T__51);
				setState(997);
				match(T__34);
				setState(998);
				match(T__51);
				setState(999);
				match(IDENTIFIER);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(1001);
				match(T__34);
				setState(1002);
				match(T__51);
				setState(1003);
				match(IDENTIFIER);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(1004);
				qualifiedName();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(1005);
				lambdaExpression();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(1006);
				methodReference();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CreatorContext extends ParserRuleContext {
		public ClassTypeContext classType() {
			return getRuleContext(ClassTypeContext.class,0);
		}
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public ClassBodyContext classBody() {
			return getRuleContext(ClassBodyContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ArrayInitializerContext arrayInitializer() {
			return getRuleContext(ArrayInitializerContext.class,0);
		}
		public CreatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_creator; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitCreator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreatorContext creator() throws RecognitionException {
		CreatorContext _localctx = new CreatorContext(_ctx, getState());
		enterRule(_localctx, 174, RULE_creator);
		int _la;
		try {
			int _alt;
			setState(1043);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,109,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1009);
				classType();
				setState(1010);
				match(T__11);
				setState(1012);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2251301597483008L) != 0) || ((((_la - 97)) & ~0x3f) == 0 && ((1L << (_la - 97)) & 523235L) != 0)) {
					{
					setState(1011);
					argumentList();
					}
				}

				setState(1014);
				match(T__12);
				setState(1016);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,105,_ctx) ) {
				case 1:
					{
					setState(1015);
					classBody();
					}
					break;
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1018);
				type();
				setState(1023); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(1019);
						match(T__36);
						setState(1020);
						expression();
						setState(1021);
						match(T__37);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(1025); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,106,_ctx);
				} while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER );
				setState(1031);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,107,_ctx);
				while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1027);
						match(T__36);
						setState(1028);
						match(T__37);
						}
						} 
					}
					setState(1033);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,107,_ctx);
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1034);
				type();
				setState(1037); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(1035);
					match(T__36);
					setState(1036);
					match(T__37);
					}
					}
					setState(1039); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__36 );
				setState(1041);
				arrayInitializer();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LambdaExpressionContext extends ParserRuleContext {
		public LambdaParametersContext lambdaParameters() {
			return getRuleContext(LambdaParametersContext.class,0);
		}
		public LambdaBodyContext lambdaBody() {
			return getRuleContext(LambdaBodyContext.class,0);
		}
		public LambdaExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lambdaExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitLambdaExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LambdaExpressionContext lambdaExpression() throws RecognitionException {
		LambdaExpressionContext _localctx = new LambdaExpressionContext(_ctx, getState());
		enterRule(_localctx, 176, RULE_lambdaExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1045);
			lambdaParameters();
			setState(1046);
			match(T__13);
			setState(1047);
			lambdaBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LambdaParametersContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(ParticParser.IDENTIFIER, 0); }
		public List<ParameterContext> parameter() {
			return getRuleContexts(ParameterContext.class);
		}
		public ParameterContext parameter(int i) {
			return getRuleContext(ParameterContext.class,i);
		}
		public LambdaParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lambdaParameters; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitLambdaParameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LambdaParametersContext lambdaParameters() throws RecognitionException {
		LambdaParametersContext _localctx = new LambdaParametersContext(_ctx, getState());
		enterRule(_localctx, 178, RULE_lambdaParameters);
		int _la;
		try {
			setState(1062);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(1049);
				match(IDENTIFIER);
				}
				break;
			case T__11:
				enterOuterAlt(_localctx, 2);
				{
				setState(1050);
				match(T__11);
				setState(1059);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2251319314186240L) != 0) || _la==IDENTIFIER) {
					{
					setState(1051);
					parameter();
					setState(1056);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__9) {
						{
						{
						setState(1052);
						match(T__9);
						setState(1053);
						parameter();
						}
						}
						setState(1058);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(1061);
				match(T__12);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LambdaBodyContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public LambdaBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lambdaBody; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitLambdaBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LambdaBodyContext lambdaBody() throws RecognitionException {
		LambdaBodyContext _localctx = new LambdaBodyContext(_ctx, getState());
		enterRule(_localctx, 180, RULE_lambdaBody);
		try {
			setState(1066);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__11:
			case T__33:
			case T__34:
			case T__38:
			case T__39:
			case T__40:
			case T__41:
			case T__42:
			case T__43:
			case T__44:
			case T__45:
			case T__46:
			case T__47:
			case T__48:
			case T__49:
			case T__96:
			case T__97:
			case T__101:
			case T__102:
			case T__103:
			case T__104:
			case T__105:
			case IntegerLiteral:
			case FloatingPointLiteral:
			case BooleanLiteral:
			case CharacterLiteral:
			case StringLiteral:
			case TextBlockLiteral:
			case NullLiteral:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(1064);
				expression();
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 2);
				{
				setState(1065);
				block();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MethodReferenceContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(ParticParser.IDENTIFIER, 0); }
		public MethodReferenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodReference; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitMethodReference(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodReferenceContext methodReference() throws RecognitionException {
		MethodReferenceContext _localctx = new MethodReferenceContext(_ctx, getState());
		enterRule(_localctx, 182, RULE_methodReference);
		try {
			setState(1079);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,114,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1068);
				type();
				setState(1069);
				match(T__106);
				setState(1070);
				match(IDENTIFIER);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1072);
				match(T__34);
				setState(1073);
				match(T__106);
				setState(1074);
				match(IDENTIFIER);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1075);
				type();
				setState(1076);
				match(T__106);
				setState(1077);
				match(T__105);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArrayInitializerContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ArrayInitializerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayInitializer; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitArrayInitializer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayInitializerContext arrayInitializer() throws RecognitionException {
		ArrayInitializerContext _localctx = new ArrayInitializerContext(_ctx, getState());
		enterRule(_localctx, 184, RULE_arrayInitializer);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1081);
			match(T__8);
			setState(1093);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2251301597483008L) != 0) || ((((_la - 97)) & ~0x3f) == 0 && ((1L << (_la - 97)) & 523235L) != 0)) {
				{
				setState(1082);
				expression();
				setState(1087);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,115,_ctx);
				while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1083);
						match(T__9);
						setState(1084);
						expression();
						}
						} 
					}
					setState(1089);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,115,_ctx);
				}
				setState(1091);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__9) {
					{
					setState(1090);
					match(T__9);
					}
				}

				}
			}

			setState(1095);
			match(T__10);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LiteralContext extends ParserRuleContext {
		public TerminalNode IntegerLiteral() { return getToken(ParticParser.IntegerLiteral, 0); }
		public TerminalNode FloatingPointLiteral() { return getToken(ParticParser.FloatingPointLiteral, 0); }
		public TerminalNode BooleanLiteral() { return getToken(ParticParser.BooleanLiteral, 0); }
		public TerminalNode CharacterLiteral() { return getToken(ParticParser.CharacterLiteral, 0); }
		public TerminalNode StringLiteral() { return getToken(ParticParser.StringLiteral, 0); }
		public TerminalNode NullLiteral() { return getToken(ParticParser.NullLiteral, 0); }
		public TerminalNode TextBlockLiteral() { return getToken(ParticParser.TextBlockLiteral, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ParticVisitor) return ((ParticVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 186, RULE_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1097);
			_la = _input.LA(1);
			if ( !(((((_la - 108)) & ~0x3f) == 0 && ((1L << (_la - 108)) & 127L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 84:
			return postfixExpression_sempred((PostfixExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean postfixExpression_sempred(PostfixExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 7);
		case 1:
			return precpred(_ctx, 6);
		case 2:
			return precpred(_ctx, 5);
		case 3:
			return precpred(_ctx, 4);
		case 4:
			return precpred(_ctx, 3);
		case 5:
			return precpred(_ctx, 2);
		case 6:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001v\u044c\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e"+
		"\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007!\u0002\"\u0007\"\u0002"+
		"#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007&\u0002\'\u0007\'\u0002"+
		"(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007+\u0002,\u0007,\u0002"+
		"-\u0007-\u0002.\u0007.\u0002/\u0007/\u00020\u00070\u00021\u00071\u0002"+
		"2\u00072\u00023\u00073\u00024\u00074\u00025\u00075\u00026\u00076\u0002"+
		"7\u00077\u00028\u00078\u00029\u00079\u0002:\u0007:\u0002;\u0007;\u0002"+
		"<\u0007<\u0002=\u0007=\u0002>\u0007>\u0002?\u0007?\u0002@\u0007@\u0002"+
		"A\u0007A\u0002B\u0007B\u0002C\u0007C\u0002D\u0007D\u0002E\u0007E\u0002"+
		"F\u0007F\u0002G\u0007G\u0002H\u0007H\u0002I\u0007I\u0002J\u0007J\u0002"+
		"K\u0007K\u0002L\u0007L\u0002M\u0007M\u0002N\u0007N\u0002O\u0007O\u0002"+
		"P\u0007P\u0002Q\u0007Q\u0002R\u0007R\u0002S\u0007S\u0002T\u0007T\u0002"+
		"U\u0007U\u0002V\u0007V\u0002W\u0007W\u0002X\u0007X\u0002Y\u0007Y\u0002"+
		"Z\u0007Z\u0002[\u0007[\u0002\\\u0007\\\u0002]\u0007]\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0005\u0000\u00c0\b\u0000\n\u0000\f\u0000\u00c3\t\u0000"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001\u00c9\b\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002"+
		"\u00d0\b\u0002\u0001\u0003\u0005\u0003\u00d3\b\u0003\n\u0003\f\u0003\u00d6"+
		"\t\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003\u00dc"+
		"\b\u0003\u0001\u0003\u0001\u0003\u0003\u0003\u00e0\b\u0003\u0001\u0003"+
		"\u0001\u0003\u0003\u0003\u00e4\b\u0003\u0001\u0003\u0001\u0003\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004\u00ec\b\u0004\u0001\u0004"+
		"\u0001\u0004\u0003\u0004\u00f0\b\u0004\u0001\u0004\u0001\u0004\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005\u00f9\b\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0005\u0006\u0101\b\u0006\n\u0006\f\u0006\u0104\t\u0006\u0001\u0006\u0001"+
		"\u0006\u0005\u0006\u0108\b\u0006\n\u0006\f\u0006\u010b\t\u0006\u0003\u0006"+
		"\u010d\b\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0003\u0007\u0114\b\u0007\u0001\u0007\u0003\u0007\u0117\b\u0007\u0001"+
		"\u0007\u0003\u0007\u011a\b\u0007\u0001\b\u0001\b\u0005\b\u011e\b\b\n\b"+
		"\f\b\u0121\t\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0003\t\u0128\b"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0003\t\u0130\b\t\u0001"+
		"\n\u0005\n\u0133\b\n\n\n\f\n\u0136\t\n\u0001\u000b\u0001\u000b\u0001\f"+
		"\u0001\f\u0001\f\u0001\f\u0005\f\u013e\b\f\n\f\f\f\u0141\t\f\u0001\f\u0001"+
		"\f\u0001\r\u0001\r\u0001\r\u0003\r\u0148\b\r\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0005\u000e\u014d\b\u000e\n\u000e\f\u000e\u0150\t\u000e\u0001\u000f"+
		"\u0001\u000f\u0005\u000f\u0154\b\u000f\n\u000f\f\u000f\u0157\t\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0003\u0010\u0160\b\u0010\u0001\u0011\u0001\u0011\u0005\u0011\u0164"+
		"\b\u0011\n\u0011\f\u0011\u0167\t\u0011\u0001\u0011\u0001\u0011\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0003\u0012\u0170\b\u0012"+
		"\u0001\u0013\u0005\u0013\u0173\b\u0013\n\u0013\f\u0013\u0176\t\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0005\u0013\u017d"+
		"\b\u0013\n\u0013\f\u0013\u0180\t\u0013\u0001\u0013\u0001\u0013\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0003\u0014\u0187\b\u0014\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0016"+
		"\u0005\u0016\u0191\b\u0016\n\u0016\f\u0016\u0194\t\u0016\u0001\u0016\u0001"+
		"\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0003\u0016\u019b\b\u0016\u0001"+
		"\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001"+
		"\u0016\u0003\u0016\u01a4\b\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0001"+
		"\u0017\u0001\u0017\u0003\u0017\u01ab\b\u0017\u0001\u0017\u0001\u0017\u0001"+
		"\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001"+
		"\u0018\u0003\u0018\u01b6\b\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001"+
		"\u0018\u0001\u0018\u0001\u0018\u0003\u0018\u01be\b\u0018\u0001\u0019\u0001"+
		"\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0003\u0019\u01c6"+
		"\b\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001"+
		"\u0019\u0003\u0019\u01ce\b\u0019\u0001\u001a\u0005\u001a\u01d1\b\u001a"+
		"\n\u001a\f\u001a\u01d4\t\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001"+
		"\u001a\u0003\u001a\u01da\b\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001"+
		"\u001b\u0001\u001b\u0003\u001b\u01e1\b\u001b\u0001\u001b\u0005\u001b\u01e4"+
		"\b\u001b\n\u001b\f\u001b\u01e7\t\u001b\u0001\u001b\u0001\u001b\u0001\u001c"+
		"\u0003\u001c\u01ec\b\u001c\u0001\u001c\u0001\u001c\u0003\u001c\u01f0\b"+
		"\u001c\u0001\u001c\u0001\u001c\u0003\u001c\u01f4\b\u001c\u0001\u001c\u0001"+
		"\u001c\u0001\u001c\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001e\u0001"+
		"\u001e\u0001\u001e\u0001\u001e\u0003\u001e\u0200\b\u001e\u0001\u001e\u0003"+
		"\u001e\u0203\b\u001e\u0001\u001f\u0001\u001f\u0001\u001f\u0005\u001f\u0208"+
		"\b\u001f\n\u001f\f\u001f\u020b\t\u001f\u0001\u001f\u0003\u001f\u020e\b"+
		"\u001f\u0001 \u0001 \u0001 \u0001 \u0001!\u0001!\u0001!\u0003!\u0217\b"+
		"!\u0001\"\u0001\"\u0001\"\u0001\"\u0005\"\u021d\b\"\n\"\f\"\u0220\t\""+
		"\u0003\"\u0222\b\"\u0001\"\u0001\"\u0001#\u0001#\u0001#\u0005#\u0229\b"+
		"#\n#\f#\u022c\t#\u0001$\u0005$\u022f\b$\n$\f$\u0232\t$\u0001$\u0001$\u0001"+
		"$\u0001$\u0001$\u0003$\u0239\b$\u0001%\u0001%\u0003%\u023d\b%\u0001%\u0001"+
		"%\u0005%\u0241\b%\n%\f%\u0244\t%\u0001&\u0001&\u0001&\u0005&\u0249\b&"+
		"\n&\f&\u024c\t&\u0001\'\u0001\'\u0001(\u0001(\u0003(\u0252\b(\u0001)\u0001"+
		")\u0001)\u0001)\u0005)\u0258\b)\n)\f)\u025b\t)\u0001)\u0001)\u0001*\u0001"+
		"*\u0001*\u0001*\u0003*\u0263\b*\u0003*\u0265\b*\u0001+\u0001+\u0001+\u0005"+
		"+\u026a\b+\n+\f+\u026d\t+\u0001,\u0001,\u0005,\u0271\b,\n,\f,\u0274\t"+
		",\u0001,\u0001,\u0001-\u0001-\u0001-\u0001-\u0001-\u0001-\u0001-\u0001"+
		"-\u0001-\u0001-\u0001-\u0001-\u0001-\u0001-\u0001-\u0001-\u0001-\u0003"+
		"-\u0289\b-\u0001.\u0001.\u0001.\u0001.\u0001.\u0005.\u0290\b.\n.\f.\u0293"+
		"\t.\u0001.\u0001.\u0001/\u0001/\u0001/\u00010\u00010\u00010\u00010\u0001"+
		"0\u00010\u00010\u00030\u02a1\b0\u00011\u00011\u00011\u00011\u00011\u0001"+
		"1\u00051\u02a9\b1\n1\f1\u02ac\t1\u00011\u00031\u02af\b1\u00011\u00011"+
		"\u00012\u00012\u00012\u00012\u00052\u02b7\b2\n2\f2\u02ba\t2\u00013\u0001"+
		"3\u00013\u00053\u02bf\b3\n3\f3\u02c2\t3\u00014\u00014\u00014\u00014\u0001"+
		"4\u00014\u00015\u00015\u00015\u00015\u00015\u00015\u00015\u00015\u0001"+
		"6\u00016\u00016\u00036\u02d5\b6\u00016\u00016\u00036\u02d9\b6\u00016\u0001"+
		"6\u00036\u02dd\b6\u00016\u00016\u00016\u00017\u00017\u00037\u02e4\b7\u0001"+
		"8\u00018\u00019\u00019\u00019\u00019\u00019\u00019\u00019\u00019\u0001"+
		"9\u00019\u0001:\u0001:\u0003:\u02f4\b:\u0001:\u0001:\u0001;\u0001;\u0003"+
		";\u02fa\b;\u0001;\u0001;\u0001<\u0001<\u0003<\u0300\b<\u0001<\u0001<\u0001"+
		"=\u0001=\u0001=\u0001=\u0001>\u0001>\u0001>\u0004>\u030b\b>\u000b>\f>"+
		"\u030c\u0001>\u0003>\u0310\b>\u0001>\u0003>\u0313\b>\u0001?\u0001?\u0001"+
		"?\u0001?\u0001?\u0001?\u0001?\u0001?\u0001@\u0001@\u0001@\u0001A\u0001"+
		"A\u0001A\u0001A\u0001A\u0001A\u0001B\u0001B\u0001B\u0001B\u0003B\u032a"+
		"\bB\u0001B\u0001B\u0001C\u0001C\u0001C\u0005C\u0331\bC\nC\fC\u0334\tC"+
		"\u0001D\u0001D\u0001E\u0001E\u0001E\u0001E\u0001E\u0003E\u033d\bE\u0001"+
		"F\u0001F\u0001G\u0001G\u0001G\u0001G\u0001G\u0001G\u0003G\u0347\bG\u0001"+
		"G\u0001G\u0001G\u0001G\u0003G\u034d\bG\u0001H\u0001H\u0001H\u0005H\u0352"+
		"\bH\nH\fH\u0355\tH\u0001I\u0001I\u0001I\u0005I\u035a\bI\nI\fI\u035d\t"+
		"I\u0001J\u0001J\u0001J\u0005J\u0362\bJ\nJ\fJ\u0365\tJ\u0001K\u0001K\u0001"+
		"K\u0005K\u036a\bK\nK\fK\u036d\tK\u0001L\u0001L\u0001L\u0005L\u0372\bL"+
		"\nL\fL\u0375\tL\u0001M\u0001M\u0001M\u0005M\u037a\bM\nM\fM\u037d\tM\u0001"+
		"N\u0001N\u0001N\u0005N\u0382\bN\nN\fN\u0385\tN\u0001O\u0001O\u0001O\u0005"+
		"O\u038a\bO\nO\fO\u038d\tO\u0001P\u0001P\u0001P\u0005P\u0392\bP\nP\fP\u0395"+
		"\tP\u0001Q\u0001Q\u0001Q\u0005Q\u039a\bQ\nQ\fQ\u039d\tQ\u0001R\u0001R"+
		"\u0001R\u0001R\u0003R\u03a3\bR\u0001S\u0001S\u0001S\u0001S\u0001S\u0001"+
		"T\u0001T\u0001T\u0001T\u0001T\u0001T\u0001T\u0001T\u0003T\u03b2\bT\u0001"+
		"T\u0001T\u0001T\u0001T\u0001T\u0001T\u0001T\u0001T\u0001T\u0003T\u03bd"+
		"\bT\u0001T\u0001T\u0001T\u0001T\u0001T\u0001T\u0001T\u0001T\u0001T\u0001"+
		"T\u0005T\u03c9\bT\nT\fT\u03cc\tT\u0001U\u0001U\u0001U\u0005U\u03d1\bU"+
		"\nU\fU\u03d4\tU\u0001V\u0001V\u0001V\u0001V\u0001V\u0001V\u0001V\u0001"+
		"V\u0001V\u0001V\u0001V\u0001V\u0001V\u0001V\u0001V\u0001V\u0001V\u0001"+
		"V\u0001V\u0001V\u0001V\u0001V\u0001V\u0001V\u0001V\u0001V\u0003V\u03f0"+
		"\bV\u0001W\u0001W\u0001W\u0003W\u03f5\bW\u0001W\u0001W\u0003W\u03f9\b"+
		"W\u0001W\u0001W\u0001W\u0001W\u0001W\u0004W\u0400\bW\u000bW\fW\u0401\u0001"+
		"W\u0001W\u0005W\u0406\bW\nW\fW\u0409\tW\u0001W\u0001W\u0001W\u0004W\u040e"+
		"\bW\u000bW\fW\u040f\u0001W\u0001W\u0003W\u0414\bW\u0001X\u0001X\u0001"+
		"X\u0001X\u0001Y\u0001Y\u0001Y\u0001Y\u0001Y\u0005Y\u041f\bY\nY\fY\u0422"+
		"\tY\u0003Y\u0424\bY\u0001Y\u0003Y\u0427\bY\u0001Z\u0001Z\u0003Z\u042b"+
		"\bZ\u0001[\u0001[\u0001[\u0001[\u0001[\u0001[\u0001[\u0001[\u0001[\u0001"+
		"[\u0001[\u0003[\u0438\b[\u0001\\\u0001\\\u0001\\\u0001\\\u0005\\\u043e"+
		"\b\\\n\\\f\\\u0441\t\\\u0001\\\u0003\\\u0444\b\\\u0003\\\u0446\b\\\u0001"+
		"\\\u0001\\\u0001]\u0001]\u0001]\u0000\u0001\u00a8^\u0000\u0002\u0004\u0006"+
		"\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,."+
		"02468:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082\u0084\u0086\u0088"+
		"\u008a\u008c\u008e\u0090\u0092\u0094\u0096\u0098\u009a\u009c\u009e\u00a0"+
		"\u00a2\u00a4\u00a6\u00a8\u00aa\u00ac\u00ae\u00b0\u00b2\u00b4\u00b6\u00b8"+
		"\u00ba\u0000\u000b\u0001\u0000\u000f\u001c\u0001\u0000\'2\u0002\u0000"+
		"\u0005\u0005##\u0002\u0000  EP\u0001\u0000VY\u0002\u0000\u001d\u001eZ"+
		"]\u0001\u0000^`\u0001\u0000ab\u0001\u0000ce\u0002\u0000abfi\u0001\u0000"+
		"lr\u048e\u0000\u00c1\u0001\u0000\u0000\u0000\u0002\u00c4\u0001\u0000\u0000"+
		"\u0000\u0004\u00cf\u0001\u0000\u0000\u0000\u0006\u00d4\u0001\u0000\u0000"+
		"\u0000\b\u00e7\u0001\u0000\u0000\u0000\n\u00f3\u0001\u0000\u0000\u0000"+
		"\f\u00fc\u0001\u0000\u0000\u0000\u000e\u0110\u0001\u0000\u0000\u0000\u0010"+
		"\u011b\u0001\u0000\u0000\u0000\u0012\u0122\u0001\u0000\u0000\u0000\u0014"+
		"\u0134\u0001\u0000\u0000\u0000\u0016\u0137\u0001\u0000\u0000\u0000\u0018"+
		"\u0139\u0001\u0000\u0000\u0000\u001a\u0144\u0001\u0000\u0000\u0000\u001c"+
		"\u0149\u0001\u0000\u0000\u0000\u001e\u0151\u0001\u0000\u0000\u0000 \u015f"+
		"\u0001\u0000\u0000\u0000\"\u0161\u0001\u0000\u0000\u0000$\u016f\u0001"+
		"\u0000\u0000\u0000&\u0174\u0001\u0000\u0000\u0000(\u0183\u0001\u0000\u0000"+
		"\u0000*\u0188\u0001\u0000\u0000\u0000,\u0192\u0001\u0000\u0000\u0000."+
		"\u01a5\u0001\u0000\u0000\u00000\u01af\u0001\u0000\u0000\u00002\u01bf\u0001"+
		"\u0000\u0000\u00004\u01d2\u0001\u0000\u0000\u00006\u01de\u0001\u0000\u0000"+
		"\u00008\u01ef\u0001\u0000\u0000\u0000:\u01f8\u0001\u0000\u0000\u0000<"+
		"\u01fb\u0001\u0000\u0000\u0000>\u020d\u0001\u0000\u0000\u0000@\u020f\u0001"+
		"\u0000\u0000\u0000B\u0216\u0001\u0000\u0000\u0000D\u0218\u0001\u0000\u0000"+
		"\u0000F\u0225\u0001\u0000\u0000\u0000H\u0230\u0001\u0000\u0000\u0000J"+
		"\u023c\u0001\u0000\u0000\u0000L\u0245\u0001\u0000\u0000\u0000N\u024d\u0001"+
		"\u0000\u0000\u0000P\u024f\u0001\u0000\u0000\u0000R\u0253\u0001\u0000\u0000"+
		"\u0000T\u0264\u0001\u0000\u0000\u0000V\u0266\u0001\u0000\u0000\u0000X"+
		"\u026e\u0001\u0000\u0000\u0000Z\u0288\u0001\u0000\u0000\u0000\\\u028a"+
		"\u0001\u0000\u0000\u0000^\u0296\u0001\u0000\u0000\u0000`\u0299\u0001\u0000"+
		"\u0000\u0000b\u02a2\u0001\u0000\u0000\u0000d\u02b2\u0001\u0000\u0000\u0000"+
		"f\u02bb\u0001\u0000\u0000\u0000h\u02c3\u0001\u0000\u0000\u0000j\u02c9"+
		"\u0001\u0000\u0000\u0000l\u02d1\u0001\u0000\u0000\u0000n\u02e3\u0001\u0000"+
		"\u0000\u0000p\u02e5\u0001\u0000\u0000\u0000r\u02e7\u0001\u0000\u0000\u0000"+
		"t\u02f1\u0001\u0000\u0000\u0000v\u02f7\u0001\u0000\u0000\u0000x\u02fd"+
		"\u0001\u0000\u0000\u0000z\u0303\u0001\u0000\u0000\u0000|\u0307\u0001\u0000"+
		"\u0000\u0000~\u0314\u0001\u0000\u0000\u0000\u0080\u031c\u0001\u0000\u0000"+
		"\u0000\u0082\u031f\u0001\u0000\u0000\u0000\u0084\u0325\u0001\u0000\u0000"+
		"\u0000\u0086\u032d\u0001\u0000\u0000\u0000\u0088\u0335\u0001\u0000\u0000"+
		"\u0000\u008a\u033c\u0001\u0000\u0000\u0000\u008c\u033e\u0001\u0000\u0000"+
		"\u0000\u008e\u034c\u0001\u0000\u0000\u0000\u0090\u034e\u0001\u0000\u0000"+
		"\u0000\u0092\u0356\u0001\u0000\u0000\u0000\u0094\u035e\u0001\u0000\u0000"+
		"\u0000\u0096\u0366\u0001\u0000\u0000\u0000\u0098\u036e\u0001\u0000\u0000"+
		"\u0000\u009a\u0376\u0001\u0000\u0000\u0000\u009c\u037e\u0001\u0000\u0000"+
		"\u0000\u009e\u0386\u0001\u0000\u0000\u0000\u00a0\u038e\u0001\u0000\u0000"+
		"\u0000\u00a2\u0396\u0001\u0000\u0000\u0000\u00a4\u03a2\u0001\u0000\u0000"+
		"\u0000\u00a6\u03a4\u0001\u0000\u0000\u0000\u00a8\u03a9\u0001\u0000\u0000"+
		"\u0000\u00aa\u03cd\u0001\u0000\u0000\u0000\u00ac\u03ef\u0001\u0000\u0000"+
		"\u0000\u00ae\u0413\u0001\u0000\u0000\u0000\u00b0\u0415\u0001\u0000\u0000"+
		"\u0000\u00b2\u0426\u0001\u0000\u0000\u0000\u00b4\u042a\u0001\u0000\u0000"+
		"\u0000\u00b6\u0437\u0001\u0000\u0000\u0000\u00b8\u0439\u0001\u0000\u0000"+
		"\u0000\u00ba\u0449\u0001\u0000\u0000\u0000\u00bc\u00c0\u0003\u0002\u0001"+
		"\u0000\u00bd\u00c0\u0003\u0004\u0002\u0000\u00be\u00c0\u0003\u0012\t\u0000"+
		"\u00bf\u00bc\u0001\u0000\u0000\u0000\u00bf\u00bd\u0001\u0000\u0000\u0000"+
		"\u00bf\u00be\u0001\u0000\u0000\u0000\u00c0\u00c3\u0001\u0000\u0000\u0000"+
		"\u00c1\u00bf\u0001\u0000\u0000\u0000\u00c1\u00c2\u0001\u0000\u0000\u0000"+
		"\u00c2\u0001\u0001\u0000\u0000\u0000\u00c3\u00c1\u0001\u0000\u0000\u0000"+
		"\u00c4\u00c5\u0005\u0001\u0000\u0000\u00c5\u00c8\u0003V+\u0000\u00c6\u00c7"+
		"\u0005\u0002\u0000\u0000\u00c7\u00c9\u0005s\u0000\u0000\u00c8\u00c6\u0001"+
		"\u0000\u0000\u0000\u00c8\u00c9\u0001\u0000\u0000\u0000\u00c9\u00ca\u0001"+
		"\u0000\u0000\u0000\u00ca\u00cb\u0005\u0003\u0000\u0000\u00cb\u0003\u0001"+
		"\u0000\u0000\u0000\u00cc\u00d0\u0003\u0006\u0003\u0000\u00cd\u00d0\u0003"+
		"\b\u0004\u0000\u00ce\u00d0\u0003\n\u0005\u0000\u00cf\u00cc\u0001\u0000"+
		"\u0000\u0000\u00cf\u00cd\u0001\u0000\u0000\u0000\u00cf\u00ce\u0001\u0000"+
		"\u0000\u0000\u00d0\u0005\u0001\u0000\u0000\u0000\u00d1\u00d3\u0003<\u001e"+
		"\u0000\u00d2\u00d1\u0001\u0000\u0000\u0000\u00d3\u00d6\u0001\u0000\u0000"+
		"\u0000\u00d4\u00d2\u0001\u0000\u0000\u0000\u00d4\u00d5\u0001\u0000\u0000"+
		"\u0000\u00d5\u00d7\u0001\u0000\u0000\u0000\u00d6\u00d4\u0001\u0000\u0000"+
		"\u0000\u00d7\u00d8\u0003\u0014\n\u0000\u00d8\u00d9\u0005\u0004\u0000\u0000"+
		"\u00d9\u00db\u0005s\u0000\u0000\u00da\u00dc\u0003\u0018\f\u0000\u00db"+
		"\u00da\u0001\u0000\u0000\u0000\u00db\u00dc\u0001\u0000\u0000\u0000\u00dc"+
		"\u00df\u0001\u0000\u0000\u0000\u00dd\u00de\u0005\u0005\u0000\u0000\u00de"+
		"\u00e0\u0003J%\u0000\u00df\u00dd\u0001\u0000\u0000\u0000\u00df\u00e0\u0001"+
		"\u0000\u0000\u0000\u00e0\u00e3\u0001\u0000\u0000\u0000\u00e1\u00e2\u0005"+
		"\u0006\u0000\u0000\u00e2\u00e4\u0003L&\u0000\u00e3\u00e1\u0001\u0000\u0000"+
		"\u0000\u00e3\u00e4\u0001\u0000\u0000\u0000\u00e4\u00e5\u0001\u0000\u0000"+
		"\u0000\u00e5\u00e6\u0003\u001e\u000f\u0000\u00e6\u0007\u0001\u0000\u0000"+
		"\u0000\u00e7\u00e8\u0003\u0014\n\u0000\u00e8\u00e9\u0005\u0007\u0000\u0000"+
		"\u00e9\u00eb\u0005s\u0000\u0000\u00ea\u00ec\u0003\u0018\f\u0000\u00eb"+
		"\u00ea\u0001\u0000\u0000\u0000\u00eb\u00ec\u0001\u0000\u0000\u0000\u00ec"+
		"\u00ef\u0001\u0000\u0000\u0000\u00ed\u00ee\u0005\u0005\u0000\u0000\u00ee"+
		"\u00f0\u0003L&\u0000\u00ef\u00ed\u0001\u0000\u0000\u0000\u00ef\u00f0\u0001"+
		"\u0000\u0000\u0000\u00f0\u00f1\u0001\u0000\u0000\u0000\u00f1\u00f2\u0003"+
		"\"\u0011\u0000\u00f2\t\u0001\u0000\u0000\u0000\u00f3\u00f4\u0003\u0014"+
		"\n\u0000\u00f4\u00f5\u0005\b\u0000\u0000\u00f5\u00f8\u0005s\u0000\u0000"+
		"\u00f6\u00f7\u0005\u0006\u0000\u0000\u00f7\u00f9\u0003L&\u0000\u00f8\u00f6"+
		"\u0001\u0000\u0000\u0000\u00f8\u00f9\u0001\u0000\u0000\u0000\u00f9\u00fa"+
		"\u0001\u0000\u0000\u0000\u00fa\u00fb\u0003\f\u0006\u0000\u00fb\u000b\u0001"+
		"\u0000\u0000\u0000\u00fc\u00fd\u0005\t\u0000\u0000\u00fd\u0102\u0003\u000e"+
		"\u0007\u0000\u00fe\u00ff\u0005\n\u0000\u0000\u00ff\u0101\u0003\u000e\u0007"+
		"\u0000\u0100\u00fe\u0001\u0000\u0000\u0000\u0101\u0104\u0001\u0000\u0000"+
		"\u0000\u0102\u0100\u0001\u0000\u0000\u0000\u0102\u0103\u0001\u0000\u0000"+
		"\u0000\u0103\u010c\u0001\u0000\u0000\u0000\u0104\u0102\u0001\u0000\u0000"+
		"\u0000\u0105\u0109\u0005\n\u0000\u0000\u0106\u0108\u0003\u0010\b\u0000"+
		"\u0107\u0106\u0001\u0000\u0000\u0000\u0108\u010b\u0001\u0000\u0000\u0000"+
		"\u0109\u0107\u0001\u0000\u0000\u0000\u0109\u010a\u0001\u0000\u0000\u0000"+
		"\u010a\u010d\u0001\u0000\u0000\u0000\u010b\u0109\u0001\u0000\u0000\u0000"+
		"\u010c\u0105\u0001\u0000\u0000\u0000\u010c\u010d\u0001\u0000\u0000\u0000"+
		"\u010d\u010e\u0001\u0000\u0000\u0000\u010e\u010f\u0005\u000b\u0000\u0000"+
		"\u010f\r\u0001\u0000\u0000\u0000\u0110\u0116\u0005s\u0000\u0000\u0111"+
		"\u0113\u0005\f\u0000\u0000\u0112\u0114\u0003\u00aaU\u0000\u0113\u0112"+
		"\u0001\u0000\u0000\u0000\u0113\u0114\u0001\u0000\u0000\u0000\u0114\u0115"+
		"\u0001\u0000\u0000\u0000\u0115\u0117\u0005\r\u0000\u0000\u0116\u0111\u0001"+
		"\u0000\u0000\u0000\u0116\u0117\u0001\u0000\u0000\u0000\u0117\u0119\u0001"+
		"\u0000\u0000\u0000\u0118\u011a\u0003\u001e\u000f\u0000\u0119\u0118\u0001"+
		"\u0000\u0000\u0000\u0119\u011a\u0001\u0000\u0000\u0000\u011a\u000f\u0001"+
		"\u0000\u0000\u0000\u011b\u011f\u0005\u0003\u0000\u0000\u011c\u011e\u0003"+
		" \u0010\u0000\u011d\u011c\u0001\u0000\u0000\u0000\u011e\u0121\u0001\u0000"+
		"\u0000\u0000\u011f\u011d\u0001\u0000\u0000\u0000\u011f\u0120\u0001\u0000"+
		"\u0000\u0000\u0120\u0011\u0001\u0000\u0000\u0000\u0121\u011f\u0001\u0000"+
		"\u0000\u0000\u0122\u0123\u0003\u0014\n\u0000\u0123\u0124\u0003J%\u0000"+
		"\u0124\u0125\u0005s\u0000\u0000\u0125\u0127\u0005\f\u0000\u0000\u0126"+
		"\u0128\u0003F#\u0000\u0127\u0126\u0001\u0000\u0000\u0000\u0127\u0128\u0001"+
		"\u0000\u0000\u0000\u0128\u0129\u0001\u0000\u0000\u0000\u0129\u012f\u0005"+
		"\r\u0000\u0000\u012a\u0130\u0003X,\u0000\u012b\u012c\u0005\u000e\u0000"+
		"\u0000\u012c\u012d\u0003\u0088D\u0000\u012d\u012e\u0005\u0003\u0000\u0000"+
		"\u012e\u0130\u0001\u0000\u0000\u0000\u012f\u012a\u0001\u0000\u0000\u0000"+
		"\u012f\u012b\u0001\u0000\u0000\u0000\u0130\u0013\u0001\u0000\u0000\u0000"+
		"\u0131\u0133\u0003\u0016\u000b\u0000\u0132\u0131\u0001\u0000\u0000\u0000"+
		"\u0133\u0136\u0001\u0000\u0000\u0000\u0134\u0132\u0001\u0000\u0000\u0000"+
		"\u0134\u0135\u0001\u0000\u0000\u0000\u0135\u0015\u0001\u0000\u0000\u0000"+
		"\u0136\u0134\u0001\u0000\u0000\u0000\u0137\u0138\u0007\u0000\u0000\u0000"+
		"\u0138\u0017\u0001\u0000\u0000\u0000\u0139\u013a\u0005\u001d\u0000\u0000"+
		"\u013a\u013f\u0003\u001a\r\u0000\u013b\u013c\u0005\n\u0000\u0000\u013c"+
		"\u013e\u0003\u001a\r\u0000\u013d\u013b\u0001\u0000\u0000\u0000\u013e\u0141"+
		"\u0001\u0000\u0000\u0000\u013f\u013d\u0001\u0000\u0000\u0000\u013f\u0140"+
		"\u0001\u0000\u0000\u0000\u0140\u0142\u0001\u0000\u0000\u0000\u0141\u013f"+
		"\u0001\u0000\u0000\u0000\u0142\u0143\u0005\u001e\u0000\u0000\u0143\u0019"+
		"\u0001\u0000\u0000\u0000\u0144\u0147\u0005s\u0000\u0000\u0145\u0146\u0005"+
		"\u0005\u0000\u0000\u0146\u0148\u0003\u001c\u000e\u0000\u0147\u0145\u0001"+
		"\u0000\u0000\u0000\u0147\u0148\u0001\u0000\u0000\u0000\u0148\u001b\u0001"+
		"\u0000\u0000\u0000\u0149\u014e\u0003J%\u0000\u014a\u014b\u0005\u001f\u0000"+
		"\u0000\u014b\u014d\u0003J%\u0000\u014c\u014a\u0001\u0000\u0000\u0000\u014d"+
		"\u0150\u0001\u0000\u0000\u0000\u014e\u014c\u0001\u0000\u0000\u0000\u014e"+
		"\u014f\u0001\u0000\u0000\u0000\u014f\u001d\u0001\u0000\u0000\u0000\u0150"+
		"\u014e\u0001\u0000\u0000\u0000\u0151\u0155\u0005\t\u0000\u0000\u0152\u0154"+
		"\u0003 \u0010\u0000\u0153\u0152\u0001\u0000\u0000\u0000\u0154\u0157\u0001"+
		"\u0000\u0000\u0000\u0155\u0153\u0001\u0000\u0000\u0000\u0155\u0156\u0001"+
		"\u0000\u0000\u0000\u0156\u0158\u0001\u0000\u0000\u0000\u0157\u0155\u0001"+
		"\u0000\u0000\u0000\u0158\u0159\u0005\u000b\u0000\u0000\u0159\u001f\u0001"+
		"\u0000\u0000\u0000\u015a\u0160\u0003&\u0013\u0000\u015b\u0160\u0003,\u0016"+
		"\u0000\u015c\u0160\u00034\u001a\u0000\u015d\u0160\u0003:\u001d\u0000\u015e"+
		"\u0160\u0005\u0003\u0000\u0000\u015f\u015a\u0001\u0000\u0000\u0000\u015f"+
		"\u015b\u0001\u0000\u0000\u0000\u015f\u015c\u0001\u0000\u0000\u0000\u015f"+
		"\u015d\u0001\u0000\u0000\u0000\u015f\u015e\u0001\u0000\u0000\u0000\u0160"+
		"!\u0001\u0000\u0000\u0000\u0161\u0165\u0005\t\u0000\u0000\u0162\u0164"+
		"\u0003$\u0012\u0000\u0163\u0162\u0001\u0000\u0000\u0000\u0164\u0167\u0001"+
		"\u0000\u0000\u0000\u0165\u0163\u0001\u0000\u0000\u0000\u0165\u0166\u0001"+
		"\u0000\u0000\u0000\u0166\u0168\u0001\u0000\u0000\u0000\u0167\u0165\u0001"+
		"\u0000\u0000\u0000\u0168\u0169\u0005\u000b\u0000\u0000\u0169#\u0001\u0000"+
		"\u0000\u0000\u016a\u0170\u0003*\u0015\u0000\u016b\u0170\u0003.\u0017\u0000"+
		"\u016c\u0170\u00030\u0018\u0000\u016d\u0170\u00032\u0019\u0000\u016e\u0170"+
		"\u0005\u0003\u0000\u0000\u016f\u016a\u0001\u0000\u0000\u0000\u016f\u016b"+
		"\u0001\u0000\u0000\u0000\u016f\u016c\u0001\u0000\u0000\u0000\u016f\u016d"+
		"\u0001\u0000\u0000\u0000\u016f\u016e\u0001\u0000\u0000\u0000\u0170%\u0001"+
		"\u0000\u0000\u0000\u0171\u0173\u0003<\u001e\u0000\u0172\u0171\u0001\u0000"+
		"\u0000\u0000\u0173\u0176\u0001\u0000\u0000\u0000\u0174\u0172\u0001\u0000"+
		"\u0000\u0000\u0174\u0175\u0001\u0000\u0000\u0000\u0175\u0177\u0001\u0000"+
		"\u0000\u0000\u0176\u0174\u0001\u0000\u0000\u0000\u0177\u0178\u0003\u0014"+
		"\n\u0000\u0178\u0179\u0003J%\u0000\u0179\u017e\u0003(\u0014\u0000\u017a"+
		"\u017b\u0005\n\u0000\u0000\u017b\u017d\u0003(\u0014\u0000\u017c\u017a"+
		"\u0001\u0000\u0000\u0000\u017d\u0180\u0001\u0000\u0000\u0000\u017e\u017c"+
		"\u0001\u0000\u0000\u0000\u017e\u017f\u0001\u0000\u0000\u0000\u017f\u0181"+
		"\u0001\u0000\u0000\u0000\u0180\u017e\u0001\u0000\u0000\u0000\u0181\u0182"+
		"\u0005\u0003\u0000\u0000\u0182\'\u0001\u0000\u0000\u0000\u0183\u0186\u0005"+
		"s\u0000\u0000\u0184\u0185\u0005 \u0000\u0000\u0185\u0187\u0003\u0088D"+
		"\u0000\u0186\u0184\u0001\u0000\u0000\u0000\u0186\u0187\u0001\u0000\u0000"+
		"\u0000\u0187)\u0001\u0000\u0000\u0000\u0188\u0189\u0003\u0014\n\u0000"+
		"\u0189\u018a\u0003J%\u0000\u018a\u018b\u0005s\u0000\u0000\u018b\u018c"+
		"\u0005 \u0000\u0000\u018c\u018d\u0003\u0088D\u0000\u018d\u018e\u0005\u0003"+
		"\u0000\u0000\u018e+\u0001\u0000\u0000\u0000\u018f\u0191\u0003<\u001e\u0000"+
		"\u0190\u018f\u0001\u0000\u0000\u0000\u0191\u0194\u0001\u0000\u0000\u0000"+
		"\u0192\u0190\u0001\u0000\u0000\u0000\u0192\u0193\u0001\u0000\u0000\u0000"+
		"\u0193\u0195\u0001\u0000\u0000\u0000\u0194\u0192\u0001\u0000\u0000\u0000"+
		"\u0195\u0196\u0003\u0014\n\u0000\u0196\u0197\u0003J%\u0000\u0197\u0198"+
		"\u0005s\u0000\u0000\u0198\u019a\u0005\f\u0000\u0000\u0199\u019b\u0003"+
		"F#\u0000\u019a\u0199\u0001\u0000\u0000\u0000\u019a\u019b\u0001\u0000\u0000"+
		"\u0000\u019b\u019c\u0001\u0000\u0000\u0000\u019c\u01a3\u0005\r\u0000\u0000"+
		"\u019d\u01a4\u0003X,\u0000\u019e\u019f\u0005\u000e\u0000\u0000\u019f\u01a0"+
		"\u0003\u0088D\u0000\u01a0\u01a1\u0005\u0003\u0000\u0000\u01a1\u01a4\u0001"+
		"\u0000\u0000\u0000\u01a2\u01a4\u0005\u0003\u0000\u0000\u01a3\u019d\u0001"+
		"\u0000\u0000\u0000\u01a3\u019e\u0001\u0000\u0000\u0000\u01a3\u01a2\u0001"+
		"\u0000\u0000\u0000\u01a4-\u0001\u0000\u0000\u0000\u01a5\u01a6\u0003\u0014"+
		"\n\u0000\u01a6\u01a7\u0003J%\u0000\u01a7\u01a8\u0005s\u0000\u0000\u01a8"+
		"\u01aa\u0005\f\u0000\u0000\u01a9\u01ab\u0003F#\u0000\u01aa\u01a9\u0001"+
		"\u0000\u0000\u0000\u01aa\u01ab\u0001\u0000\u0000\u0000\u01ab\u01ac\u0001"+
		"\u0000\u0000\u0000\u01ac\u01ad\u0005\r\u0000\u0000\u01ad\u01ae\u0005\u0003"+
		"\u0000\u0000\u01ae/\u0001\u0000\u0000\u0000\u01af\u01b0\u0003\u0014\n"+
		"\u0000\u01b0\u01b1\u0005!\u0000\u0000\u01b1\u01b2\u0003J%\u0000\u01b2"+
		"\u01b3\u0005s\u0000\u0000\u01b3\u01b5\u0005\f\u0000\u0000\u01b4\u01b6"+
		"\u0003F#\u0000\u01b5\u01b4\u0001\u0000\u0000\u0000\u01b5\u01b6\u0001\u0000"+
		"\u0000\u0000\u01b6\u01b7\u0001\u0000\u0000\u0000\u01b7\u01bd\u0005\r\u0000"+
		"\u0000\u01b8\u01be\u0003X,\u0000\u01b9\u01ba\u0005\u000e\u0000\u0000\u01ba"+
		"\u01bb\u0003\u0088D\u0000\u01bb\u01bc\u0005\u0003\u0000\u0000\u01bc\u01be"+
		"\u0001\u0000\u0000\u0000\u01bd\u01b8\u0001\u0000\u0000\u0000\u01bd\u01b9"+
		"\u0001\u0000\u0000\u0000\u01be1\u0001\u0000\u0000\u0000\u01bf\u01c0\u0003"+
		"\u0014\n\u0000\u01c0\u01c1\u0005\u0013\u0000\u0000\u01c1\u01c2\u0003J"+
		"%\u0000\u01c2\u01c3\u0005s\u0000\u0000\u01c3\u01c5\u0005\f\u0000\u0000"+
		"\u01c4\u01c6\u0003F#\u0000\u01c5\u01c4\u0001\u0000\u0000\u0000\u01c5\u01c6"+
		"\u0001\u0000\u0000\u0000\u01c6\u01c7\u0001\u0000\u0000\u0000\u01c7\u01cd"+
		"\u0005\r\u0000\u0000\u01c8\u01ce\u0003X,\u0000\u01c9\u01ca\u0005\u000e"+
		"\u0000\u0000\u01ca\u01cb\u0003\u0088D\u0000\u01cb\u01cc\u0005\u0003\u0000"+
		"\u0000\u01cc\u01ce\u0001\u0000\u0000\u0000\u01cd\u01c8\u0001\u0000\u0000"+
		"\u0000\u01cd\u01c9\u0001\u0000\u0000\u0000\u01ce3\u0001\u0000\u0000\u0000"+
		"\u01cf\u01d1\u0003<\u001e\u0000\u01d0\u01cf\u0001\u0000\u0000\u0000\u01d1"+
		"\u01d4\u0001\u0000\u0000\u0000\u01d2\u01d0\u0001\u0000\u0000\u0000\u01d2"+
		"\u01d3\u0001\u0000\u0000\u0000\u01d3\u01d5\u0001\u0000\u0000\u0000\u01d4"+
		"\u01d2\u0001\u0000\u0000\u0000\u01d5\u01d6\u0003\u0014\n\u0000\u01d6\u01d7"+
		"\u0005s\u0000\u0000\u01d7\u01d9\u0005\f\u0000\u0000\u01d8\u01da\u0003"+
		"F#\u0000\u01d9\u01d8\u0001\u0000\u0000\u0000\u01d9\u01da\u0001\u0000\u0000"+
		"\u0000\u01da\u01db\u0001\u0000\u0000\u0000\u01db\u01dc\u0005\r\u0000\u0000"+
		"\u01dc\u01dd\u00036\u001b\u0000\u01dd5\u0001\u0000\u0000\u0000\u01de\u01e0"+
		"\u0005\t\u0000\u0000\u01df\u01e1\u00038\u001c\u0000\u01e0\u01df\u0001"+
		"\u0000\u0000\u0000\u01e0\u01e1\u0001\u0000\u0000\u0000\u01e1\u01e5\u0001"+
		"\u0000\u0000\u0000\u01e2\u01e4\u0003Z-\u0000\u01e3\u01e2\u0001\u0000\u0000"+
		"\u0000\u01e4\u01e7\u0001\u0000\u0000\u0000\u01e5\u01e3\u0001\u0000\u0000"+
		"\u0000\u01e5\u01e6\u0001\u0000\u0000\u0000\u01e6\u01e8\u0001\u0000\u0000"+
		"\u0000\u01e7\u01e5\u0001\u0000\u0000\u0000\u01e8\u01e9\u0005\u000b\u0000"+
		"\u0000\u01e97\u0001\u0000\u0000\u0000\u01ea\u01ec\u0003R)\u0000\u01eb"+
		"\u01ea\u0001\u0000\u0000\u0000\u01eb\u01ec\u0001\u0000\u0000\u0000\u01ec"+
		"\u01ed\u0001\u0000\u0000\u0000\u01ed\u01f0\u0005\"\u0000\u0000\u01ee\u01f0"+
		"\u0005#\u0000\u0000\u01ef\u01eb\u0001\u0000\u0000\u0000\u01ef\u01ee\u0001"+
		"\u0000\u0000\u0000\u01f0\u01f1\u0001\u0000\u0000\u0000\u01f1\u01f3\u0005"+
		"\f\u0000\u0000\u01f2\u01f4\u0003\u00aaU\u0000\u01f3\u01f2\u0001\u0000"+
		"\u0000\u0000\u01f3\u01f4\u0001\u0000\u0000\u0000\u01f4\u01f5\u0001\u0000"+
		"\u0000\u0000\u01f5\u01f6\u0005\r\u0000\u0000\u01f6\u01f7\u0005\u0003\u0000"+
		"\u0000\u01f79\u0001\u0000\u0000\u0000\u01f8\u01f9\u0005\u0013\u0000\u0000"+
		"\u01f9\u01fa\u0003X,\u0000\u01fa;\u0001\u0000\u0000\u0000\u01fb\u01fc"+
		"\u0005$\u0000\u0000\u01fc\u0202\u0003V+\u0000\u01fd\u01ff\u0005\f\u0000"+
		"\u0000\u01fe\u0200\u0003>\u001f\u0000\u01ff\u01fe\u0001\u0000\u0000\u0000"+
		"\u01ff\u0200\u0001\u0000\u0000\u0000\u0200\u0201\u0001\u0000\u0000\u0000"+
		"\u0201\u0203\u0005\r\u0000\u0000\u0202\u01fd\u0001\u0000\u0000\u0000\u0202"+
		"\u0203\u0001\u0000\u0000\u0000\u0203=\u0001\u0000\u0000\u0000\u0204\u0209"+
		"\u0003@ \u0000\u0205\u0206\u0005\n\u0000\u0000\u0206\u0208\u0003@ \u0000"+
		"\u0207\u0205\u0001\u0000\u0000\u0000\u0208\u020b\u0001\u0000\u0000\u0000"+
		"\u0209\u0207\u0001\u0000\u0000\u0000\u0209\u020a\u0001\u0000\u0000\u0000"+
		"\u020a\u020e\u0001\u0000\u0000\u0000\u020b\u0209\u0001\u0000\u0000\u0000"+
		"\u020c\u020e\u0003B!\u0000\u020d\u0204\u0001\u0000\u0000\u0000\u020d\u020c"+
		"\u0001\u0000\u0000\u0000\u020e?\u0001\u0000\u0000\u0000\u020f\u0210\u0005"+
		"s\u0000\u0000\u0210\u0211\u0005 \u0000\u0000\u0211\u0212\u0003B!\u0000"+
		"\u0212A\u0001\u0000\u0000\u0000\u0213\u0217\u0003\u0088D\u0000\u0214\u0217"+
		"\u0003<\u001e\u0000\u0215\u0217\u0003D\"\u0000\u0216\u0213\u0001\u0000"+
		"\u0000\u0000\u0216\u0214\u0001\u0000\u0000\u0000\u0216\u0215\u0001\u0000"+
		"\u0000\u0000\u0217C\u0001\u0000\u0000\u0000\u0218\u0221\u0005\t\u0000"+
		"\u0000\u0219\u021e\u0003B!\u0000\u021a\u021b\u0005\n\u0000\u0000\u021b"+
		"\u021d\u0003B!\u0000\u021c\u021a\u0001\u0000\u0000\u0000\u021d\u0220\u0001"+
		"\u0000\u0000\u0000\u021e\u021c\u0001\u0000\u0000\u0000\u021e\u021f\u0001"+
		"\u0000\u0000\u0000\u021f\u0222\u0001\u0000\u0000\u0000\u0220\u021e\u0001"+
		"\u0000\u0000\u0000\u0221\u0219\u0001\u0000\u0000\u0000\u0221\u0222\u0001"+
		"\u0000\u0000\u0000\u0222\u0223\u0001\u0000\u0000\u0000\u0223\u0224\u0005"+
		"\u000b\u0000\u0000\u0224E\u0001\u0000\u0000\u0000\u0225\u022a\u0003H$"+
		"\u0000\u0226\u0227\u0005\n\u0000\u0000\u0227\u0229\u0003H$\u0000\u0228"+
		"\u0226\u0001\u0000\u0000\u0000\u0229\u022c\u0001\u0000\u0000\u0000\u022a"+
		"\u0228\u0001\u0000\u0000\u0000\u022a\u022b\u0001\u0000\u0000\u0000\u022b"+
		"G\u0001\u0000\u0000\u0000\u022c\u022a\u0001\u0000\u0000\u0000\u022d\u022f"+
		"\u0003<\u001e\u0000\u022e\u022d\u0001\u0000\u0000\u0000\u022f\u0232\u0001"+
		"\u0000\u0000\u0000\u0230\u022e\u0001\u0000\u0000\u0000\u0230\u0231\u0001"+
		"\u0000\u0000\u0000\u0231\u0233\u0001\u0000\u0000\u0000\u0232\u0230\u0001"+
		"\u0000\u0000\u0000\u0233\u0234\u0003\u0014\n\u0000\u0234\u0235\u0003J"+
		"%\u0000\u0235\u0238\u0005s\u0000\u0000\u0236\u0237\u0005 \u0000\u0000"+
		"\u0237\u0239\u0003\u0088D\u0000\u0238\u0236\u0001\u0000\u0000\u0000\u0238"+
		"\u0239\u0001\u0000\u0000\u0000\u0239I\u0001\u0000\u0000\u0000\u023a\u023d"+
		"\u0003N\'\u0000\u023b\u023d\u0003P(\u0000\u023c\u023a\u0001\u0000\u0000"+
		"\u0000\u023c\u023b\u0001\u0000\u0000\u0000\u023d\u0242\u0001\u0000\u0000"+
		"\u0000\u023e\u023f\u0005%\u0000\u0000\u023f\u0241\u0005&\u0000\u0000\u0240"+
		"\u023e\u0001\u0000\u0000\u0000\u0241\u0244\u0001\u0000\u0000\u0000\u0242"+
		"\u0240\u0001\u0000\u0000\u0000\u0242\u0243\u0001\u0000\u0000\u0000\u0243"+
		"K\u0001\u0000\u0000\u0000\u0244\u0242\u0001\u0000\u0000\u0000\u0245\u024a"+
		"\u0003J%\u0000\u0246\u0247\u0005\n\u0000\u0000\u0247\u0249\u0003J%\u0000"+
		"\u0248\u0246\u0001\u0000\u0000\u0000\u0249\u024c\u0001\u0000\u0000\u0000"+
		"\u024a\u0248\u0001\u0000\u0000\u0000\u024a\u024b\u0001\u0000\u0000\u0000"+
		"\u024bM\u0001\u0000\u0000\u0000\u024c\u024a\u0001\u0000\u0000\u0000\u024d"+
		"\u024e\u0007\u0001\u0000\u0000\u024eO\u0001\u0000\u0000\u0000\u024f\u0251"+
		"\u0003V+\u0000\u0250\u0252\u0003R)\u0000\u0251\u0250\u0001\u0000\u0000"+
		"\u0000\u0251\u0252\u0001\u0000\u0000\u0000\u0252Q\u0001\u0000\u0000\u0000"+
		"\u0253\u0254\u0005\u001d\u0000\u0000\u0254\u0259\u0003T*\u0000\u0255\u0256"+
		"\u0005\n\u0000\u0000\u0256\u0258\u0003T*\u0000\u0257\u0255\u0001\u0000"+
		"\u0000\u0000\u0258\u025b\u0001\u0000\u0000\u0000\u0259\u0257\u0001\u0000"+
		"\u0000\u0000\u0259\u025a\u0001\u0000\u0000\u0000\u025a\u025c\u0001\u0000"+
		"\u0000\u0000\u025b\u0259\u0001\u0000\u0000\u0000\u025c\u025d\u0005\u001e"+
		"\u0000\u0000\u025dS\u0001\u0000\u0000\u0000\u025e\u0265\u0003J%\u0000"+
		"\u025f\u0262\u00053\u0000\u0000\u0260\u0261\u0007\u0002\u0000\u0000\u0261"+
		"\u0263\u0003J%\u0000\u0262\u0260\u0001\u0000\u0000\u0000\u0262\u0263\u0001"+
		"\u0000\u0000\u0000\u0263\u0265\u0001\u0000\u0000\u0000\u0264\u025e\u0001"+
		"\u0000\u0000\u0000\u0264\u025f\u0001\u0000\u0000\u0000\u0265U\u0001\u0000"+
		"\u0000\u0000\u0266\u026b\u0005s\u0000\u0000\u0267\u0268\u00054\u0000\u0000"+
		"\u0268\u026a\u0005s\u0000\u0000\u0269\u0267\u0001\u0000\u0000\u0000\u026a"+
		"\u026d\u0001\u0000\u0000\u0000\u026b\u0269\u0001\u0000\u0000\u0000\u026b"+
		"\u026c\u0001\u0000\u0000\u0000\u026cW\u0001\u0000\u0000\u0000\u026d\u026b"+
		"\u0001\u0000\u0000\u0000\u026e\u0272\u0005\t\u0000\u0000\u026f\u0271\u0003"+
		"Z-\u0000\u0270\u026f\u0001\u0000\u0000\u0000\u0271\u0274\u0001\u0000\u0000"+
		"\u0000\u0272\u0270\u0001\u0000\u0000\u0000\u0272\u0273\u0001\u0000\u0000"+
		"\u0000\u0273\u0275\u0001\u0000\u0000\u0000\u0274\u0272\u0001\u0000\u0000"+
		"\u0000\u0275\u0276\u0005\u000b\u0000\u0000\u0276Y\u0001\u0000\u0000\u0000"+
		"\u0277\u0289\u0003X,\u0000\u0278\u0289\u0003\\.\u0000\u0279\u0289\u0003"+
		"^/\u0000\u027a\u0289\u0003`0\u0000\u027b\u0289\u0003b1\u0000\u027c\u0289"+
		"\u0003h4\u0000\u027d\u0289\u0003j5\u0000\u027e\u0289\u0003l6\u0000\u027f"+
		"\u0289\u0003r9\u0000\u0280\u0289\u0003t:\u0000\u0281\u0289\u0003v;\u0000"+
		"\u0282\u0289\u0003x<\u0000\u0283\u0289\u0003z=\u0000\u0284\u0289\u0003"+
		"|>\u0000\u0285\u0289\u0003\u0082A\u0000\u0286\u0289\u0003\u0084B\u0000"+
		"\u0287\u0289\u0005\u0003\u0000\u0000\u0288\u0277\u0001\u0000\u0000\u0000"+
		"\u0288\u0278\u0001\u0000\u0000\u0000\u0288\u0279\u0001\u0000\u0000\u0000"+
		"\u0288\u027a\u0001\u0000\u0000\u0000\u0288\u027b\u0001\u0000\u0000\u0000"+
		"\u0288\u027c\u0001\u0000\u0000\u0000\u0288\u027d\u0001\u0000\u0000\u0000"+
		"\u0288\u027e\u0001\u0000\u0000\u0000\u0288\u027f\u0001\u0000\u0000\u0000"+
		"\u0288\u0280\u0001\u0000\u0000\u0000\u0288\u0281\u0001\u0000\u0000\u0000"+
		"\u0288\u0282\u0001\u0000\u0000\u0000\u0288\u0283\u0001\u0000\u0000\u0000"+
		"\u0288\u0284\u0001\u0000\u0000\u0000\u0288\u0285\u0001\u0000\u0000\u0000"+
		"\u0288\u0286\u0001\u0000\u0000\u0000\u0288\u0287\u0001\u0000\u0000\u0000"+
		"\u0289[\u0001\u0000\u0000\u0000\u028a\u028b\u0003\u0014\n\u0000\u028b"+
		"\u028c\u0003J%\u0000\u028c\u0291\u0003(\u0014\u0000\u028d\u028e\u0005"+
		"\n\u0000\u0000\u028e\u0290\u0003(\u0014\u0000\u028f\u028d\u0001\u0000"+
		"\u0000\u0000\u0290\u0293\u0001\u0000\u0000\u0000\u0291\u028f\u0001\u0000"+
		"\u0000\u0000\u0291\u0292\u0001\u0000\u0000\u0000\u0292\u0294\u0001\u0000"+
		"\u0000\u0000\u0293\u0291\u0001\u0000\u0000\u0000\u0294\u0295\u0005\u0003"+
		"\u0000\u0000\u0295]\u0001\u0000\u0000\u0000\u0296\u0297\u0003\u0088D\u0000"+
		"\u0297\u0298\u0005\u0003\u0000\u0000\u0298_\u0001\u0000\u0000\u0000\u0299"+
		"\u029a\u00055\u0000\u0000\u029a\u029b\u0005\f\u0000\u0000\u029b\u029c"+
		"\u0003\u0088D\u0000\u029c\u029d\u0005\r\u0000\u0000\u029d\u02a0\u0003"+
		"Z-\u0000\u029e\u029f\u00056\u0000\u0000\u029f\u02a1\u0003Z-\u0000\u02a0"+
		"\u029e\u0001\u0000\u0000\u0000\u02a0\u02a1\u0001\u0000\u0000\u0000\u02a1"+
		"a\u0001\u0000\u0000\u0000\u02a2\u02a3\u00057\u0000\u0000\u02a3\u02a4\u0005"+
		"\f\u0000\u0000\u02a4\u02a5\u0003\u0088D\u0000\u02a5\u02a6\u0005\r\u0000"+
		"\u0000\u02a6\u02aa\u0005\t\u0000\u0000\u02a7\u02a9\u0003d2\u0000\u02a8"+
		"\u02a7\u0001\u0000\u0000\u0000\u02a9\u02ac\u0001\u0000\u0000\u0000\u02aa"+
		"\u02a8\u0001\u0000\u0000\u0000\u02aa\u02ab\u0001\u0000\u0000\u0000\u02ab"+
		"\u02ae\u0001\u0000\u0000\u0000\u02ac\u02aa\u0001\u0000\u0000\u0000\u02ad"+
		"\u02af\u0003f3\u0000\u02ae\u02ad\u0001\u0000\u0000\u0000\u02ae\u02af\u0001"+
		"\u0000\u0000\u0000\u02af\u02b0\u0001\u0000\u0000\u0000\u02b0\u02b1\u0005"+
		"\u000b\u0000\u0000\u02b1c\u0001\u0000\u0000\u0000\u02b2\u02b3\u00058\u0000"+
		"\u0000\u02b3\u02b4\u0003\u0088D\u0000\u02b4\u02b8\u00059\u0000\u0000\u02b5"+
		"\u02b7\u0003Z-\u0000\u02b6\u02b5\u0001\u0000\u0000\u0000\u02b7\u02ba\u0001"+
		"\u0000\u0000\u0000\u02b8\u02b6\u0001\u0000\u0000\u0000\u02b8\u02b9\u0001"+
		"\u0000\u0000\u0000\u02b9e\u0001\u0000\u0000\u0000\u02ba\u02b8\u0001\u0000"+
		"\u0000\u0000\u02bb\u02bc\u0005!\u0000\u0000\u02bc\u02c0\u00059\u0000\u0000"+
		"\u02bd\u02bf\u0003Z-\u0000\u02be\u02bd\u0001\u0000\u0000\u0000\u02bf\u02c2"+
		"\u0001\u0000\u0000\u0000\u02c0\u02be\u0001\u0000\u0000\u0000\u02c0\u02c1"+
		"\u0001\u0000\u0000\u0000\u02c1g\u0001\u0000\u0000\u0000\u02c2\u02c0\u0001"+
		"\u0000\u0000\u0000\u02c3\u02c4\u0005:\u0000\u0000\u02c4\u02c5\u0005\f"+
		"\u0000\u0000\u02c5\u02c6\u0003\u0088D\u0000\u02c6\u02c7\u0005\r\u0000"+
		"\u0000\u02c7\u02c8\u0003Z-\u0000\u02c8i\u0001\u0000\u0000\u0000\u02c9"+
		"\u02ca\u0005;\u0000\u0000\u02ca\u02cb\u0003Z-\u0000\u02cb\u02cc\u0005"+
		":\u0000\u0000\u02cc\u02cd\u0005\f\u0000\u0000\u02cd\u02ce\u0003\u0088"+
		"D\u0000\u02ce\u02cf\u0005\r\u0000\u0000\u02cf\u02d0\u0005\u0003\u0000"+
		"\u0000\u02d0k\u0001\u0000\u0000\u0000\u02d1\u02d2\u0005<\u0000\u0000\u02d2"+
		"\u02d4\u0005\f\u0000\u0000\u02d3\u02d5\u0003n7\u0000\u02d4\u02d3\u0001"+
		"\u0000\u0000\u0000\u02d4\u02d5\u0001\u0000\u0000\u0000\u02d5\u02d6\u0001"+
		"\u0000\u0000\u0000\u02d6\u02d8\u0005\u0003\u0000\u0000\u02d7\u02d9\u0003"+
		"\u0088D\u0000\u02d8\u02d7\u0001\u0000\u0000\u0000\u02d8\u02d9\u0001\u0000"+
		"\u0000\u0000\u02d9\u02da\u0001\u0000\u0000\u0000\u02da\u02dc\u0005\u0003"+
		"\u0000\u0000\u02db\u02dd\u0003p8\u0000\u02dc\u02db\u0001\u0000\u0000\u0000"+
		"\u02dc\u02dd\u0001\u0000\u0000\u0000\u02dd\u02de\u0001\u0000\u0000\u0000"+
		"\u02de\u02df\u0005\r\u0000\u0000\u02df\u02e0\u0003Z-\u0000\u02e0m\u0001"+
		"\u0000\u0000\u0000\u02e1\u02e4\u0003\\.\u0000\u02e2\u02e4\u0003\u0086"+
		"C\u0000\u02e3\u02e1\u0001\u0000\u0000\u0000\u02e3\u02e2\u0001\u0000\u0000"+
		"\u0000\u02e4o\u0001\u0000\u0000\u0000\u02e5\u02e6\u0003\u0086C\u0000\u02e6"+
		"q\u0001\u0000\u0000\u0000\u02e7\u02e8\u0005<\u0000\u0000\u02e8\u02e9\u0005"+
		"\f\u0000\u0000\u02e9\u02ea\u0003\u0014\n\u0000\u02ea\u02eb\u0003J%\u0000"+
		"\u02eb\u02ec\u0005s\u0000\u0000\u02ec\u02ed\u00059\u0000\u0000\u02ed\u02ee"+
		"\u0003\u0088D\u0000\u02ee\u02ef\u0005\r\u0000\u0000\u02ef\u02f0\u0003"+
		"Z-\u0000\u02f0s\u0001\u0000\u0000\u0000\u02f1\u02f3\u0005=\u0000\u0000"+
		"\u02f2\u02f4\u0003\u0088D\u0000\u02f3\u02f2\u0001\u0000\u0000\u0000\u02f3"+
		"\u02f4\u0001\u0000\u0000\u0000\u02f4\u02f5\u0001\u0000\u0000\u0000\u02f5"+
		"\u02f6\u0005\u0003\u0000\u0000\u02f6u\u0001\u0000\u0000\u0000\u02f7\u02f9"+
		"\u0005>\u0000\u0000\u02f8\u02fa\u0005s\u0000\u0000\u02f9\u02f8\u0001\u0000"+
		"\u0000\u0000\u02f9\u02fa\u0001\u0000\u0000\u0000\u02fa\u02fb\u0001\u0000"+
		"\u0000\u0000\u02fb\u02fc\u0005\u0003\u0000\u0000\u02fcw\u0001\u0000\u0000"+
		"\u0000\u02fd\u02ff\u0005?\u0000\u0000\u02fe\u0300\u0005s\u0000\u0000\u02ff"+
		"\u02fe\u0001\u0000\u0000\u0000\u02ff\u0300\u0001\u0000\u0000\u0000\u0300"+
		"\u0301\u0001\u0000\u0000\u0000\u0301\u0302\u0005\u0003\u0000\u0000\u0302"+
		"y\u0001\u0000\u0000\u0000\u0303\u0304\u0005@\u0000\u0000\u0304\u0305\u0003"+
		"\u0088D\u0000\u0305\u0306\u0005\u0003\u0000\u0000\u0306{\u0001\u0000\u0000"+
		"\u0000\u0307\u0308\u0005A\u0000\u0000\u0308\u0312\u0003X,\u0000\u0309"+
		"\u030b\u0003~?\u0000\u030a\u0309\u0001\u0000\u0000\u0000\u030b\u030c\u0001"+
		"\u0000\u0000\u0000\u030c\u030a\u0001\u0000\u0000\u0000\u030c\u030d\u0001"+
		"\u0000\u0000\u0000\u030d\u030f\u0001\u0000\u0000\u0000\u030e\u0310\u0003"+
		"\u0080@\u0000\u030f\u030e\u0001\u0000\u0000\u0000\u030f\u0310\u0001\u0000"+
		"\u0000\u0000\u0310\u0313\u0001\u0000\u0000\u0000\u0311\u0313\u0003\u0080"+
		"@\u0000\u0312\u030a\u0001\u0000\u0000\u0000\u0312\u0311\u0001\u0000\u0000"+
		"\u0000\u0313}\u0001\u0000\u0000\u0000\u0314\u0315\u0005B\u0000\u0000\u0315"+
		"\u0316\u0005\f\u0000\u0000\u0316\u0317\u0003\u0014\n\u0000\u0317\u0318"+
		"\u0003J%\u0000\u0318\u0319\u0005s\u0000\u0000\u0319\u031a\u0005\r\u0000"+
		"\u0000\u031a\u031b\u0003X,\u0000\u031b\u007f\u0001\u0000\u0000\u0000\u031c"+
		"\u031d\u0005C\u0000\u0000\u031d\u031e\u0003X,\u0000\u031e\u0081\u0001"+
		"\u0000\u0000\u0000\u031f\u0320\u0005\u001a\u0000\u0000\u0320\u0321\u0005"+
		"\f\u0000\u0000\u0321\u0322\u0003\u0088D\u0000\u0322\u0323\u0005\r\u0000"+
		"\u0000\u0323\u0324\u0003X,\u0000\u0324\u0083\u0001\u0000\u0000\u0000\u0325"+
		"\u0326\u0005D\u0000\u0000\u0326\u0329\u0003\u0088D\u0000\u0327\u0328\u0005"+
		"9\u0000\u0000\u0328\u032a\u0003\u0088D\u0000\u0329\u0327\u0001\u0000\u0000"+
		"\u0000\u0329\u032a\u0001\u0000\u0000\u0000\u032a\u032b\u0001\u0000\u0000"+
		"\u0000\u032b\u032c\u0005\u0003\u0000\u0000\u032c\u0085\u0001\u0000\u0000"+
		"\u0000\u032d\u0332\u0003\u0088D\u0000\u032e\u032f\u0005\n\u0000\u0000"+
		"\u032f\u0331\u0003\u0088D\u0000\u0330\u032e\u0001\u0000\u0000\u0000\u0331"+
		"\u0334\u0001\u0000\u0000\u0000\u0332\u0330\u0001\u0000\u0000\u0000\u0332"+
		"\u0333\u0001\u0000\u0000\u0000\u0333\u0087\u0001\u0000\u0000\u0000\u0334"+
		"\u0332\u0001\u0000\u0000\u0000\u0335\u0336\u0003\u008aE\u0000\u0336\u0089"+
		"\u0001\u0000\u0000\u0000\u0337\u033d\u0003\u008eG\u0000\u0338\u0339\u0003"+
		"\u00a8T\u0000\u0339\u033a\u0003\u008cF\u0000\u033a\u033b\u0003\u0088D"+
		"\u0000\u033b\u033d\u0001\u0000\u0000\u0000\u033c\u0337\u0001\u0000\u0000"+
		"\u0000\u033c\u0338\u0001\u0000\u0000\u0000\u033d\u008b\u0001\u0000\u0000"+
		"\u0000\u033e\u033f\u0007\u0003\u0000\u0000\u033f\u008d\u0001\u0000\u0000"+
		"\u0000\u0340\u0346\u0003\u0090H\u0000\u0341\u0342\u00053\u0000\u0000\u0342"+
		"\u0343\u0003\u0088D\u0000\u0343\u0344\u00059\u0000\u0000\u0344\u0345\u0003"+
		"\u008eG\u0000\u0345\u0347\u0001\u0000\u0000\u0000\u0346\u0341\u0001\u0000"+
		"\u0000\u0000\u0346\u0347\u0001\u0000\u0000\u0000\u0347\u034d\u0001\u0000"+
		"\u0000\u0000\u0348\u0349\u0003\u0090H\u0000\u0349\u034a\u0005Q\u0000\u0000"+
		"\u034a\u034b\u0003\u008eG\u0000\u034b\u034d\u0001\u0000\u0000\u0000\u034c"+
		"\u0340\u0001\u0000\u0000\u0000\u034c\u0348\u0001\u0000\u0000\u0000\u034d"+
		"\u008f\u0001\u0000\u0000\u0000\u034e\u0353\u0003\u0092I\u0000\u034f\u0350"+
		"\u0005R\u0000\u0000\u0350\u0352\u0003\u0092I\u0000\u0351\u034f\u0001\u0000"+
		"\u0000\u0000\u0352\u0355\u0001\u0000\u0000\u0000\u0353\u0351\u0001\u0000"+
		"\u0000\u0000\u0353\u0354\u0001\u0000\u0000\u0000\u0354\u0091\u0001\u0000"+
		"\u0000\u0000\u0355\u0353\u0001\u0000\u0000\u0000\u0356\u035b\u0003\u0094"+
		"J\u0000\u0357\u0358\u0005S\u0000\u0000\u0358\u035a\u0003\u0094J\u0000"+
		"\u0359\u0357\u0001\u0000\u0000\u0000\u035a\u035d\u0001\u0000\u0000\u0000"+
		"\u035b\u0359\u0001\u0000\u0000\u0000\u035b\u035c\u0001\u0000\u0000\u0000"+
		"\u035c\u0093\u0001\u0000\u0000\u0000\u035d\u035b\u0001\u0000\u0000\u0000"+
		"\u035e\u0363\u0003\u0096K\u0000\u035f\u0360\u0005T\u0000\u0000\u0360\u0362"+
		"\u0003\u0096K\u0000\u0361\u035f\u0001\u0000\u0000\u0000\u0362\u0365\u0001"+
		"\u0000\u0000\u0000\u0363\u0361\u0001\u0000\u0000\u0000\u0363\u0364\u0001"+
		"\u0000\u0000\u0000\u0364\u0095\u0001\u0000\u0000\u0000\u0365\u0363\u0001"+
		"\u0000\u0000\u0000\u0366\u036b\u0003\u0098L\u0000\u0367\u0368\u0005U\u0000"+
		"\u0000\u0368\u036a\u0003\u0098L\u0000\u0369\u0367\u0001\u0000\u0000\u0000"+
		"\u036a\u036d\u0001\u0000\u0000\u0000\u036b\u0369\u0001\u0000\u0000\u0000"+
		"\u036b\u036c\u0001\u0000\u0000\u0000\u036c\u0097\u0001\u0000\u0000\u0000"+
		"\u036d\u036b\u0001\u0000\u0000\u0000\u036e\u0373\u0003\u009aM\u0000\u036f"+
		"\u0370\u0005\u001f\u0000\u0000\u0370\u0372\u0003\u009aM\u0000\u0371\u036f"+
		"\u0001\u0000\u0000\u0000\u0372\u0375\u0001\u0000\u0000\u0000\u0373\u0371"+
		"\u0001\u0000\u0000\u0000\u0373\u0374\u0001\u0000\u0000\u0000\u0374\u0099"+
		"\u0001\u0000\u0000\u0000\u0375\u0373\u0001\u0000\u0000\u0000\u0376\u037b"+
		"\u0003\u009cN\u0000\u0377\u0378\u0007\u0004\u0000\u0000\u0378\u037a\u0003"+
		"\u009cN\u0000\u0379\u0377\u0001\u0000\u0000\u0000\u037a\u037d\u0001\u0000"+
		"\u0000\u0000\u037b\u0379\u0001\u0000\u0000\u0000\u037b\u037c\u0001\u0000"+
		"\u0000\u0000\u037c\u009b\u0001\u0000\u0000\u0000\u037d\u037b\u0001\u0000"+
		"\u0000\u0000\u037e\u0383\u0003\u009eO\u0000\u037f\u0380\u0007\u0005\u0000"+
		"\u0000\u0380\u0382\u0003\u009eO\u0000\u0381\u037f\u0001\u0000\u0000\u0000"+
		"\u0382\u0385\u0001\u0000\u0000\u0000\u0383\u0381\u0001\u0000\u0000\u0000"+
		"\u0383\u0384\u0001\u0000\u0000\u0000\u0384\u009d\u0001\u0000\u0000\u0000"+
		"\u0385\u0383\u0001\u0000\u0000\u0000\u0386\u038b\u0003\u00a0P\u0000\u0387"+
		"\u0388\u0007\u0006\u0000\u0000\u0388\u038a\u0003\u00a0P\u0000\u0389\u0387"+
		"\u0001\u0000\u0000\u0000\u038a\u038d\u0001\u0000\u0000\u0000\u038b\u0389"+
		"\u0001\u0000\u0000\u0000\u038b\u038c\u0001\u0000\u0000\u0000\u038c\u009f"+
		"\u0001\u0000\u0000\u0000\u038d\u038b\u0001\u0000\u0000\u0000\u038e\u0393"+
		"\u0003\u00a2Q\u0000\u038f\u0390\u0007\u0007\u0000\u0000\u0390\u0392\u0003"+
		"\u00a2Q\u0000\u0391\u038f\u0001\u0000\u0000\u0000\u0392\u0395\u0001\u0000"+
		"\u0000\u0000\u0393\u0391\u0001\u0000\u0000\u0000\u0393\u0394\u0001\u0000"+
		"\u0000\u0000\u0394\u00a1\u0001\u0000\u0000\u0000\u0395\u0393\u0001\u0000"+
		"\u0000\u0000\u0396\u039b\u0003\u00a4R\u0000\u0397\u0398\u0007\b\u0000"+
		"\u0000\u0398\u039a\u0003\u00a4R\u0000\u0399\u0397\u0001\u0000\u0000\u0000"+
		"\u039a\u039d\u0001\u0000\u0000\u0000\u039b\u0399\u0001\u0000\u0000\u0000"+
		"\u039b\u039c\u0001\u0000\u0000\u0000\u039c\u00a3\u0001\u0000\u0000\u0000"+
		"\u039d\u039b\u0001\u0000\u0000\u0000\u039e\u039f\u0007\t\u0000\u0000\u039f"+
		"\u03a3\u0003\u00a4R\u0000\u03a0\u03a3\u0003\u00a6S\u0000\u03a1\u03a3\u0003"+
		"\u00a8T\u0000\u03a2\u039e\u0001\u0000\u0000\u0000\u03a2\u03a0\u0001\u0000"+
		"\u0000\u0000\u03a2\u03a1\u0001\u0000\u0000\u0000\u03a3\u00a5\u0001\u0000"+
		"\u0000\u0000\u03a4\u03a5\u0005\f\u0000\u0000\u03a5\u03a6\u0003J%\u0000"+
		"\u03a6\u03a7\u0005\r\u0000\u0000\u03a7\u03a8\u0003\u00a4R\u0000\u03a8"+
		"\u00a7\u0001\u0000\u0000\u0000\u03a9\u03aa\u0006T\uffff\uffff\u0000\u03aa"+
		"\u03ab\u0003\u00acV\u0000\u03ab\u03ca\u0001\u0000\u0000\u0000\u03ac\u03ad"+
		"\n\u0007\u0000\u0000\u03ad\u03ae\u00054\u0000\u0000\u03ae\u03af\u0005"+
		"s\u0000\u0000\u03af\u03b1\u0005\f\u0000\u0000\u03b0\u03b2\u0003\u00aa"+
		"U\u0000\u03b1\u03b0\u0001\u0000\u0000\u0000\u03b1\u03b2\u0001\u0000\u0000"+
		"\u0000\u03b2\u03b3\u0001\u0000\u0000\u0000\u03b3\u03c9\u0005\r\u0000\u0000"+
		"\u03b4\u03b5\n\u0006\u0000\u0000\u03b5\u03b6\u0005%\u0000\u0000\u03b6"+
		"\u03b7\u0003\u0088D\u0000\u03b7\u03b8\u0005&\u0000\u0000\u03b8\u03c9\u0001"+
		"\u0000\u0000\u0000\u03b9\u03ba\n\u0005\u0000\u0000\u03ba\u03bc\u0005\f"+
		"\u0000\u0000\u03bb\u03bd\u0003\u00aaU\u0000\u03bc\u03bb\u0001\u0000\u0000"+
		"\u0000\u03bc\u03bd\u0001\u0000\u0000\u0000\u03bd\u03be\u0001\u0000\u0000"+
		"\u0000\u03be\u03c9\u0005\r\u0000\u0000\u03bf\u03c0\n\u0004\u0000\u0000"+
		"\u03c0\u03c1\u00054\u0000\u0000\u03c1\u03c9\u0005s\u0000\u0000\u03c2\u03c3"+
		"\n\u0003\u0000\u0000\u03c3\u03c9\u0005f\u0000\u0000\u03c4\u03c5\n\u0002"+
		"\u0000\u0000\u03c5\u03c9\u0005g\u0000\u0000\u03c6\u03c7\n\u0001\u0000"+
		"\u0000\u03c7\u03c9\u0005h\u0000\u0000\u03c8\u03ac\u0001\u0000\u0000\u0000"+
		"\u03c8\u03b4\u0001\u0000\u0000\u0000\u03c8\u03b9\u0001\u0000\u0000\u0000"+
		"\u03c8\u03bf\u0001\u0000\u0000\u0000\u03c8\u03c2\u0001\u0000\u0000\u0000"+
		"\u03c8\u03c4\u0001\u0000\u0000\u0000\u03c8\u03c6\u0001\u0000\u0000\u0000"+
		"\u03c9\u03cc\u0001\u0000\u0000\u0000\u03ca\u03c8\u0001\u0000\u0000\u0000"+
		"\u03ca\u03cb\u0001\u0000\u0000\u0000\u03cb\u00a9\u0001\u0000\u0000\u0000"+
		"\u03cc\u03ca\u0001\u0000\u0000\u0000\u03cd\u03d2\u0003\u0088D\u0000\u03ce"+
		"\u03cf\u0005\n\u0000\u0000\u03cf\u03d1\u0003\u0088D\u0000\u03d0\u03ce"+
		"\u0001\u0000\u0000\u0000\u03d1\u03d4\u0001\u0000\u0000\u0000\u03d2\u03d0"+
		"\u0001\u0000\u0000\u0000\u03d2\u03d3\u0001\u0000\u0000\u0000\u03d3\u00ab"+
		"\u0001\u0000\u0000\u0000\u03d4\u03d2\u0001\u0000\u0000\u0000\u03d5\u03f0"+
		"\u0003\u00ba]\u0000\u03d6\u03f0\u0005s\u0000\u0000\u03d7\u03f0\u0005\""+
		"\u0000\u0000\u03d8\u03f0\u0005#\u0000\u0000\u03d9\u03da\u0005\f\u0000"+
		"\u0000\u03da\u03db\u0003\u0088D\u0000\u03db\u03dc\u0005\r\u0000\u0000"+
		"\u03dc\u03f0\u0001\u0000\u0000\u0000\u03dd\u03de\u0005j\u0000\u0000\u03de"+
		"\u03f0\u0003\u00aeW\u0000\u03df\u03e0\u0003J%\u0000\u03e0\u03e1\u0005"+
		"4\u0000\u0000\u03e1\u03e2\u0005\u0004\u0000\u0000\u03e2\u03f0\u0001\u0000"+
		"\u0000\u0000\u03e3\u03e4\u0003J%\u0000\u03e4\u03e5\u00054\u0000\u0000"+
		"\u03e5\u03e6\u0005#\u0000\u0000\u03e6\u03e7\u00054\u0000\u0000\u03e7\u03e8"+
		"\u0005s\u0000\u0000\u03e8\u03f0\u0001\u0000\u0000\u0000\u03e9\u03ea\u0005"+
		"#\u0000\u0000\u03ea\u03eb\u00054\u0000\u0000\u03eb\u03f0\u0005s\u0000"+
		"\u0000\u03ec\u03f0\u0003V+\u0000\u03ed\u03f0\u0003\u00b0X\u0000\u03ee"+
		"\u03f0\u0003\u00b6[\u0000\u03ef\u03d5\u0001\u0000\u0000\u0000\u03ef\u03d6"+
		"\u0001\u0000\u0000\u0000\u03ef\u03d7\u0001\u0000\u0000\u0000\u03ef\u03d8"+
		"\u0001\u0000\u0000\u0000\u03ef\u03d9\u0001\u0000\u0000\u0000\u03ef\u03dd"+
		"\u0001\u0000\u0000\u0000\u03ef\u03df\u0001\u0000\u0000\u0000\u03ef\u03e3"+
		"\u0001\u0000\u0000\u0000\u03ef\u03e9\u0001\u0000\u0000\u0000\u03ef\u03ec"+
		"\u0001\u0000\u0000\u0000\u03ef\u03ed\u0001\u0000\u0000\u0000\u03ef\u03ee"+
		"\u0001\u0000\u0000\u0000\u03f0\u00ad\u0001\u0000\u0000\u0000\u03f1\u03f2"+
		"\u0003P(\u0000\u03f2\u03f4\u0005\f\u0000\u0000\u03f3\u03f5\u0003\u00aa"+
		"U\u0000\u03f4\u03f3\u0001\u0000\u0000\u0000\u03f4\u03f5\u0001\u0000\u0000"+
		"\u0000\u03f5\u03f6\u0001\u0000\u0000\u0000\u03f6\u03f8\u0005\r\u0000\u0000"+
		"\u03f7\u03f9\u0003\u001e\u000f\u0000\u03f8\u03f7\u0001\u0000\u0000\u0000"+
		"\u03f8\u03f9\u0001\u0000\u0000\u0000\u03f9\u0414\u0001\u0000\u0000\u0000"+
		"\u03fa\u03ff\u0003J%\u0000\u03fb\u03fc\u0005%\u0000\u0000\u03fc\u03fd"+
		"\u0003\u0088D\u0000\u03fd\u03fe\u0005&\u0000\u0000\u03fe\u0400\u0001\u0000"+
		"\u0000\u0000\u03ff\u03fb\u0001\u0000\u0000\u0000\u0400\u0401\u0001\u0000"+
		"\u0000\u0000\u0401\u03ff\u0001\u0000\u0000\u0000\u0401\u0402\u0001\u0000"+
		"\u0000\u0000\u0402\u0407\u0001\u0000\u0000\u0000\u0403\u0404\u0005%\u0000"+
		"\u0000\u0404\u0406\u0005&\u0000\u0000\u0405\u0403\u0001\u0000\u0000\u0000"+
		"\u0406\u0409\u0001\u0000\u0000\u0000\u0407\u0405\u0001\u0000\u0000\u0000"+
		"\u0407\u0408\u0001\u0000\u0000\u0000\u0408\u0414\u0001\u0000\u0000\u0000"+
		"\u0409\u0407\u0001\u0000\u0000\u0000\u040a\u040d\u0003J%\u0000\u040b\u040c"+
		"\u0005%\u0000\u0000\u040c\u040e\u0005&\u0000\u0000\u040d\u040b\u0001\u0000"+
		"\u0000\u0000\u040e\u040f\u0001\u0000\u0000\u0000\u040f\u040d\u0001\u0000"+
		"\u0000\u0000\u040f\u0410\u0001\u0000\u0000\u0000\u0410\u0411\u0001\u0000"+
		"\u0000\u0000\u0411\u0412\u0003\u00b8\\\u0000\u0412\u0414\u0001\u0000\u0000"+
		"\u0000\u0413\u03f1\u0001\u0000\u0000\u0000\u0413\u03fa\u0001\u0000\u0000"+
		"\u0000\u0413\u040a\u0001\u0000\u0000\u0000\u0414\u00af\u0001\u0000\u0000"+
		"\u0000\u0415\u0416\u0003\u00b2Y\u0000\u0416\u0417\u0005\u000e\u0000\u0000"+
		"\u0417\u0418\u0003\u00b4Z\u0000\u0418\u00b1\u0001\u0000\u0000\u0000\u0419"+
		"\u0427\u0005s\u0000\u0000\u041a\u0423\u0005\f\u0000\u0000\u041b\u0420"+
		"\u0003H$\u0000\u041c\u041d\u0005\n\u0000\u0000\u041d\u041f\u0003H$\u0000"+
		"\u041e\u041c\u0001\u0000\u0000\u0000\u041f\u0422\u0001\u0000\u0000\u0000"+
		"\u0420\u041e\u0001\u0000\u0000\u0000\u0420\u0421\u0001\u0000\u0000\u0000"+
		"\u0421\u0424\u0001\u0000\u0000\u0000\u0422\u0420\u0001\u0000\u0000\u0000"+
		"\u0423\u041b\u0001\u0000\u0000\u0000\u0423\u0424\u0001\u0000\u0000\u0000"+
		"\u0424\u0425\u0001\u0000\u0000\u0000\u0425\u0427\u0005\r\u0000\u0000\u0426"+
		"\u0419\u0001\u0000\u0000\u0000\u0426\u041a\u0001\u0000\u0000\u0000\u0427"+
		"\u00b3\u0001\u0000\u0000\u0000\u0428\u042b\u0003\u0088D\u0000\u0429\u042b"+
		"\u0003X,\u0000\u042a\u0428\u0001\u0000\u0000\u0000\u042a\u0429\u0001\u0000"+
		"\u0000\u0000\u042b\u00b5\u0001\u0000\u0000\u0000\u042c\u042d\u0003J%\u0000"+
		"\u042d\u042e\u0005k\u0000\u0000\u042e\u042f\u0005s\u0000\u0000\u042f\u0438"+
		"\u0001\u0000\u0000\u0000\u0430\u0431\u0005#\u0000\u0000\u0431\u0432\u0005"+
		"k\u0000\u0000\u0432\u0438\u0005s\u0000\u0000\u0433\u0434\u0003J%\u0000"+
		"\u0434\u0435\u0005k\u0000\u0000\u0435\u0436\u0005j\u0000\u0000\u0436\u0438"+
		"\u0001\u0000\u0000\u0000\u0437\u042c\u0001\u0000\u0000\u0000\u0437\u0430"+
		"\u0001\u0000\u0000\u0000\u0437\u0433\u0001\u0000\u0000\u0000\u0438\u00b7"+
		"\u0001\u0000\u0000\u0000\u0439\u0445\u0005\t\u0000\u0000\u043a\u043f\u0003"+
		"\u0088D\u0000\u043b\u043c\u0005\n\u0000\u0000\u043c\u043e\u0003\u0088"+
		"D\u0000\u043d\u043b\u0001\u0000\u0000\u0000\u043e\u0441\u0001\u0000\u0000"+
		"\u0000\u043f\u043d\u0001\u0000\u0000\u0000\u043f\u0440\u0001\u0000\u0000"+
		"\u0000\u0440\u0443\u0001\u0000\u0000\u0000\u0441\u043f\u0001\u0000\u0000"+
		"\u0000\u0442\u0444\u0005\n\u0000\u0000\u0443\u0442\u0001\u0000\u0000\u0000"+
		"\u0443\u0444\u0001\u0000\u0000\u0000\u0444\u0446\u0001\u0000\u0000\u0000"+
		"\u0445\u043a\u0001\u0000\u0000\u0000\u0445\u0446\u0001\u0000\u0000\u0000"+
		"\u0446\u0447\u0001\u0000\u0000\u0000\u0447\u0448\u0005\u000b\u0000\u0000"+
		"\u0448\u00b9\u0001\u0000\u0000\u0000\u0449\u044a\u0007\n\u0000\u0000\u044a"+
		"\u00bb\u0001\u0000\u0000\u0000v\u00bf\u00c1\u00c8\u00cf\u00d4\u00db\u00df"+
		"\u00e3\u00eb\u00ef\u00f8\u0102\u0109\u010c\u0113\u0116\u0119\u011f\u0127"+
		"\u012f\u0134\u013f\u0147\u014e\u0155\u015f\u0165\u016f\u0174\u017e\u0186"+
		"\u0192\u019a\u01a3\u01aa\u01b5\u01bd\u01c5\u01cd\u01d2\u01d9\u01e0\u01e5"+
		"\u01eb\u01ef\u01f3\u01ff\u0202\u0209\u020d\u0216\u021e\u0221\u022a\u0230"+
		"\u0238\u023c\u0242\u024a\u0251\u0259\u0262\u0264\u026b\u0272\u0288\u0291"+
		"\u02a0\u02aa\u02ae\u02b8\u02c0\u02d4\u02d8\u02dc\u02e3\u02f3\u02f9\u02ff"+
		"\u030c\u030f\u0312\u0329\u0332\u033c\u0346\u034c\u0353\u035b\u0363\u036b"+
		"\u0373\u037b\u0383\u038b\u0393\u039b\u03a2\u03b1\u03bc\u03c8\u03ca\u03d2"+
		"\u03ef\u03f4\u03f8\u0401\u0407\u040f\u0413\u0420\u0423\u0426\u042a\u0437"+
		"\u043f\u0443\u0445";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
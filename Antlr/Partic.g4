grammar Partic;

program
    : (importDeclaration | typeDeclaration | functionDeclaration)*
    ;

importDeclaration
    : 'import' qualifiedName ('as' IDENTIFIER)? ';'
    ;

typeDeclaration
    : classDeclaration
    | interfaceDeclaration
    | enumDeclaration
    ;

classDeclaration
    : annotation* modifiers 'class' IDENTIFIER
      typeParameters?
      ('extends' type)?
      ('implements' typeList)?
      classBody
    ;

interfaceDeclaration
    : modifiers 'interface' IDENTIFIER
      typeParameters?
      ('extends' typeList)?
      interfaceBody
    ;

enumDeclaration
    : modifiers 'enum' IDENTIFIER
      ('implements' typeList)?
      enumBody
    ;

enumBody
    : '{' enumConstant (',' enumConstant)* (',' enumBodyDeclaration*)? '}'
    ;

enumConstant
    : IDENTIFIER ('(' argumentList? ')')? classBody?
    ;

enumBodyDeclaration
    : ';' (classBodyDeclaration)*
    ;

functionDeclaration
    : modifiers type IDENTIFIER '(' parameterList? ')' (block | '->' expression ';')
    ;

modifiers
    : modifier*
    ;

modifier
    : 'pub'
    | 'priv'
    | 'prot'
    | 'pack'
    | 'static'
    | 'final'
    | 'abstract'
    | 'sealed'
    | 'open'
    | 'override'
    | 'native'
    | 'synchronized'
    | 'transient'
    | 'volatile'
    ;

typeParameters
    : '<' typeParameter (',' typeParameter)* '>'
    ;

typeParameter
    : IDENTIFIER ('extends' typeBound)?
    ;

typeBound
    : type ('&' type)*
    ;

classBody
    : '{' classBodyDeclaration* '}'
    ;

classBodyDeclaration
    : fieldDeclaration
    | methodDeclaration
    | constructorDeclaration
    | staticBlock
    | ';'
    ;

interfaceBody
    : '{' interfaceMemberDeclaration* '}'
    ;

interfaceMemberDeclaration
    : constantDeclaration
    | abstractMethodDeclaration
    | defaultMethodDeclaration
    | staticMethodDeclaration
    | ';'
    ;

fieldDeclaration
    : annotation* modifiers type variableDeclarator (',' variableDeclarator)* ';'
    ;

variableDeclarator
    : IDENTIFIER ('=' expression)?
    ;

constantDeclaration
    : modifiers type IDENTIFIER '=' expression ';'
    ;

methodDeclaration
    : annotation* modifiers type IDENTIFIER '(' parameterList? ')' (block | '->' expression ';' | ';')
    ;

abstractMethodDeclaration
    : modifiers type IDENTIFIER '(' parameterList? ')' ';'
    ;

defaultMethodDeclaration
    : modifiers 'default' type IDENTIFIER '(' parameterList? ')' (block | '->' expression ';')
    ;

staticMethodDeclaration
    : modifiers 'static' type IDENTIFIER '(' parameterList? ')' (block | '->' expression ';')
    ;

constructorDeclaration
    : annotation* modifiers IDENTIFIER '(' parameterList? ')' constructorBody
    ;

constructorBody
    : '{' explicitConstructorInvocation? statement* '}'
    ;

explicitConstructorInvocation
    : (typeArguments? 'this' | 'super') '(' argumentList? ')' ';'
    ;

staticBlock
    : 'static' block
    ;

annotation
    : '@' qualifiedName ('(' elementValuePairs? ')')?
    ;

elementValuePairs
    : elementValuePair (',' elementValuePair)*
    | elementValue
    ;

elementValuePair
    : IDENTIFIER '=' elementValue
    ;

elementValue
    : expression
    | annotation
    | elementValueArrayInitializer
    ;

elementValueArrayInitializer
    : '{' (elementValue (',' elementValue)*)? '}'
    ;

parameterList
    : parameter (',' parameter)*
    ;

parameter
    : annotation* modifiers type IDENTIFIER ('=' expression)?
    ;

type
    : (primitiveType | classType) ('[' ']')*
    ;

typeList
    : type (',' type)*
    ;

primitiveType
    : 'int' | 'long' | 'short' | 'byte'
    | 'float' | 'double'
    | 'char'
    | 'bool'
    | 'str'
    | 'void'
    ;

classType
    : qualifiedName typeArguments?
    ;

typeArguments
    : '<' typeArgument (',' typeArgument)* '>'
    ;

typeArgument
    : type
    | '?' (('extends' | 'super') type)?
    ;

qualifiedName
    : IDENTIFIER ('.' IDENTIFIER)*
    ;

block
    : '{' statement* '}'
    ;

statement
    : block
    | variableDeclarationStatement
    | expressionStatement
    | ifStatement
    | switchStatement
    | whileStatement
    | doWhileStatement
    | forStatement
    | forEachStatement
    | returnStatement
    | breakStatement
    | continueStatement
    | throwStatement
    | tryStatement
    | synchronizedStatement
    | assertStatement
    | ';'
    ;

variableDeclarationStatement
    : modifiers type variableDeclarator (',' variableDeclarator)* ';'
    ;

expressionStatement
    : expression ';'
    ;

ifStatement
    : 'if' '(' expression ')' statement ('else' statement)?
    ;

switchStatement
    : 'switch' '(' expression ')' '{' switchCase* defaultCase? '}'
    ;

switchCase
    : 'case' expression ':' statement*
    ;

defaultCase
    : 'default' ':' statement*
    ;

whileStatement
    : 'while' '(' expression ')' statement
    ;

doWhileStatement
    : 'do' statement 'while' '(' expression ')' ';'
    ;

forStatement
    : 'for' '(' forInit? ';' expression? ';' forUpdate? ')' statement
    ;

forInit
    : variableDeclarationStatement
    | expressionList
    ;

forUpdate
    : expressionList
    ;

forEachStatement
    : 'for' '(' modifiers type IDENTIFIER ':' expression ')' statement
    ;

returnStatement
    : 'return' expression? ';'
    ;

breakStatement
    : 'break' IDENTIFIER? ';'
    ;

continueStatement
    : 'continue' IDENTIFIER? ';'
    ;

throwStatement
    : 'throw' expression ';'
    ;

tryStatement
    : 'try' block (catchClause+ finallyBlock? | finallyBlock)
    ;

catchClause
    : 'catch' '(' modifiers type IDENTIFIER ')' block
    ;

finallyBlock
    : 'finally' block
    ;

synchronizedStatement
    : 'synchronized' '(' expression ')' block
    ;

assertStatement
    : 'assert' expression (':' expression)? ';'
    ;

expressionList
    : expression (',' expression)*
    ;

expression
    : assignmentExpression
    ;

assignmentExpression
    : conditionalExpression
    | postfixExpression assignmentOperator expression
    ;

assignmentOperator
    : '=' | '+=' | '-=' | '*=' | '/=' | '%='
    | '&=' | '|=' | '^=' | '<<=' | '>>=' | '>>>=' | '??='
    ;

conditionalExpression
    : conditionalOrExpression ('?' expression ':' conditionalExpression)?
    | conditionalOrExpression '??' conditionalExpression
    ;

conditionalOrExpression
    : conditionalAndExpression ('||' conditionalAndExpression)*
    ;

conditionalAndExpression
    : inclusiveOrExpression ('&&' inclusiveOrExpression)*
    ;

inclusiveOrExpression
    : exclusiveOrExpression ('|' exclusiveOrExpression)*
    ;

exclusiveOrExpression
    : andExpression ('^' andExpression)*
    ;

andExpression
    : equalityExpression ('&' equalityExpression)*
    ;

equalityExpression
    : relationalExpression (('==' | '!=' | '===' | '!==') relationalExpression)*
    ;

relationalExpression
    : shiftExpression (('<' | '>' | '<=' | '>=' | 'instanceof' | 'in') shiftExpression)*
    ;

shiftExpression
    : additiveExpression (('<<' | '>>' | '>>>') additiveExpression)*
    ;

additiveExpression
    : multiplicativeExpression (('+' | '-') multiplicativeExpression)*
    ;

multiplicativeExpression
    : unaryExpression (('*' | '/' | '%') unaryExpression)*
    ;

unaryExpression
    : ('+' | '-' | '++' | '--' | '!' | '~') unaryExpression
    | castExpression
    | postfixExpression
    ;

castExpression
    : '(' type ')' unaryExpression
    ;

postfixExpression
    : primary
    | postfixExpression '.' IDENTIFIER '(' argumentList? ')'
    | postfixExpression '[' expression ']'
    | postfixExpression '(' argumentList? ')'
    | postfixExpression '.' IDENTIFIER
    | postfixExpression '++'
    | postfixExpression '--'
    | postfixExpression '!'
    ;

argumentList
    : expression (',' expression)*
    ;

primary
    : literal
    | IDENTIFIER
    | 'this'
    | 'super'
    | '(' expression ')'
    | 'new' creator
    | type '.' 'class'
    | type '.' 'super' '.' IDENTIFIER
    | 'super' '.' IDENTIFIER
    | qualifiedName
    | lambdaExpression
    | methodReference
    ;

creator
    : classType '(' argumentList? ')' classBody?
    | type ('[' expression ']')+ ('[' ']')*
    | type ('[' ']')+ arrayInitializer
    ;

lambdaExpression
    : lambdaParameters '->' lambdaBody
    ;

lambdaParameters
    : IDENTIFIER
    | '(' (parameter (',' parameter)*)? ')'
    ;

lambdaBody
    : expression
    | block
    ;

methodReference
    : type '::' IDENTIFIER
    | 'super' '::' IDENTIFIER
    | type '::' 'new'
    ;

arrayInitializer
    : '{' (expression (',' expression)* ','?)? '}'
    ;

literal
    : IntegerLiteral
    | FloatingPointLiteral
    | BooleanLiteral
    | CharacterLiteral
    | StringLiteral
    | NullLiteral
    | TextBlockLiteral
    ;

IntegerLiteral
    : DecimalIntegerLiteral
    | HexIntegerLiteral
    | OctalIntegerLiteral
    | BinaryIntegerLiteral
    ;

fragment DecimalIntegerLiteral
    : '0'
    | [1-9] [0-9]* [lL]?
    ;

fragment HexIntegerLiteral
    : '0' [xX] [0-9a-fA-F]+ [lL]?
    ;

fragment OctalIntegerLiteral
    : '0' [0-7]+ [lL]?
    ;

fragment BinaryIntegerLiteral
    : '0' [bB] [01]+ [lL]?
    ;

FloatingPointLiteral
    : DecimalFloatingPointLiteral
    | HexadecimalFloatingPointLiteral
    ;

fragment DecimalFloatingPointLiteral
    : [0-9]* '.' [0-9]+ ([eE] [+-]? [0-9]+)? [fFdD]?
    | [0-9]+ [eE] [+-]? [0-9]+ [fFdD]?
    | [0-9]+ [fFdD]
    ;

fragment HexadecimalFloatingPointLiteral
    : '0' [xX] [0-9a-fA-F]* '.' [0-9a-fA-F]+ [pP] [+-]? [0-9]+ [fFdD]?
    | '0' [xX] [0-9a-fA-F]+ [pP] [+-]? [0-9]+ [fFdD]?
    ;

BooleanLiteral
    : 'true'
    | 'false'
    ;

CharacterLiteral
    : '\'' (~['\\\r\n] | EscapeSequence) '\''
    ;

StringLiteral
    : '"' (~["\\\r\n] | EscapeSequence)* '"'
    ;

TextBlockLiteral
    : '"""' [ \t]* [\r\n] .*? '"""'
    ;

NullLiteral
    : 'null'
    ;

fragment EscapeSequence
    : '\\' [btnfr"'\\]
    | '\\' [0-7] [0-7]? [0-7]?
    | '\\' 'u' [0-9a-fA-F] [0-9a-fA-F] [0-9a-fA-F] [0-9a-fA-F]
    ;

IDENTIFIER
    : [a-zA-Z_$] [a-zA-Z0-9_$]*
    ;

WS : [ \t\r\n\u000C]+ -> skip;
COMMENT : '/*' .*? '*/' -> skip;
LINE_COMMENT : '//' ~[\r\n]* -> skip;
grammar Minijava;

goal	
:	mainClass classDeclaration* EOF
;


mainClass	
:	'class' Identifier '{' 'public' 'static' 'void' 'main' '(' 'String' '[' ']' Identifier ')' '{' statement '}' '}';

classDeclaration	
:	'class' Identifier ( 'extends' Identifier )? '{' varDeclaration* methodDeclaration* '}';

varDeclaration	
:	type Identifier ';';

methodDeclaration	
:	'public' type Identifier '(' parameterList? ')' '{' methodBody '}';

parameterList
:   parameter (',' parameter)*
;

parameter
:   type Identifier
;

methodBody
:	varDeclaration* statement* RETURN expression ';'
;

type	
:	'int' '[' ']'
//#intArrayType
|	'boolean'
//#booleanType
|	'int'
//#intType
|	Identifier
//#referenceType
;	

statement	
:	'{' statement* '}'
#nestedStatement
|	'if' LP expression RP ifBlock 'else' elseBlock
#ifElseStatement
|	'while' LP expression RP statement
#whileStatement
|	'System.out.println' LP  expression RP ';'
#printStatement
|	Identifier '=' expression ';'
#variableAssignmentStatement
|	Identifier LSB expression RSB '=' expression ';'
#arrayAssignmentStatement
;	

ifBlock
:	statement
;

elseBlock
:	statement
;

expression
:   expression AND expression
# andExpression
|   expression LT expression
# ltExpression  
|   expression PLUS expression
# addExpression
|   expression MINUS expression
# subExpression
|   expression TIMES expression
# mulExpression
|	expression POWER expression
# powExpression
|   expression LSB expression RSB
# arrayAccessExpression
|   expression DOTLENGTH
# arrayLengthExpression
|   expression '.' Identifier '(' ( expression ( ',' expression )* )? ')'
# methodCallExpression
|   IntegerLiteral
# intLitExpression
|   BooleanLiteral
# booleanLitExpression
|   Identifier
# identifierExpression
|   'this'
# thisExpression
|   'new' 'int' LSB expression RSB
# arrayInstantiationExpression
|   'new' Identifier '(' ')'
# objectInstantiationExpression
|   NOT expression
# notExpression
|   '(' expression ')'
# parenExpression
;

AND:'&&';
LT:'<';
PLUS:'+';
MINUS:'-';
TIMES:'*';
POWER:'**';
NOT:'!';
LSB:'[';
RSB:']';
DOTLENGTH:'.length';
LP:'(';
RP:')';
RETURN: 'return';

//expression	
//:	expression ( '&&' | '<' | '+' | '-' | '*' | '**' ) expression 
//|   expression '[' expression ']'
//|	expression '.' 'length'
//|	expression '.' Identifier '(' ( expression ( ',' expression )* )? ')'
//|	IntegerLiteral
//|	'true'
//|	'false'
//|	Identifier
//|	'this'
//|	'new' 'int' '[' expression ']'
//|	'new' Identifier '(' ')'
//|	'!' expression
//|	'(' expression ')'
//;	

BooleanLiteral
:	'true'
|	'false'
;

Identifier
:	JavaLetter JavaLetterOrDigit*
;

fragment
JavaLetter
:	[a-zA-Z$_] // these are the 'java letters' below 0xFF
;

fragment
JavaLetterOrDigit
:	[a-zA-Z0-9$_] // these are the 'java letters or digits' below 0xFF
;

IntegerLiteral
:	DecimalIntegerLiteral
;

fragment
DecimalIntegerLiteral
:	DecimalNumeral IntegertypeSuffix?
;

fragment
IntegertypeSuffix
:	[lL]
;

fragment
DecimalNumeral
	:	'0'
|	NonZeroDigit (Digits? | Underscores Digits)
	;

	fragment
	Digits
	:	Digit (DigitsAndUnderscores? Digit)?
	;

	fragment
	Digit
	:	'0'
	|	NonZeroDigit
	;

	fragment
	NonZeroDigit
	:	[1-9]
	;

	fragment
	DigitsAndUnderscores
	:	DigitOrUnderscore+
	;

	fragment
	DigitOrUnderscore
	:	Digit
	|	'_'
	;

	fragment
	Underscores
	:	'_'+
	;

	WS
	:   [ \r\t\n]+ -> skip
	;   

	MULTILINE_COMMENT
	:  '/*' .*? '*/' -> skip
	;
	LINE_COMMENT
	:  '//' .*? '\n' -> skip
	;

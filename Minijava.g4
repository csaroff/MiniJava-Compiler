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
:	varDeclaration* statement* 'return' expression ';'
;

type	
:	'int' '[' ']'
|	'boolean'
|	'int'
|	Identifier
;	

statement	
:	'{' statement* '}'
#nestedStatement
|	'if' '(' expression ')' ifBlock 'else' elseBlock
#ifElseStatement
|	'while' '(' expression ')' statement
#whileStatement
|	'System.out.println' '('  expression ')' ';'
#printStatement
|	Identifier '=' expression ';'
#variableAssignmentStatement
|	Identifier '[' expression ']' '=' expression ';'
#arrayAssignmentStatement
;	

ifBlock
:	statement
;

elseBlock
:	statement
;

expression
:   expression '&&' expression
# andExpression
|   expression '<'  expression
# ltExpression  
|   expression '+'  expression
# addExpression
|   expression '-'  expression
# subExpression
|   expression '*'  expression
# mulExpression
|	expression '**' expression
# powExpression
|   expression '[' expression ']'
# arrayAccessExpression
|   expression '.' 'length'
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
|   'new' 'int' '[' expression ']'
# arrayInstantiationExpression
|   'new' Identifier '(' ')'
# objectInstantiationExpression
|   '!' expression
# notExpression
|   '(' expression ')'
# parenExpression
;

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

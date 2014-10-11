grammar test;
Expression  :   Expression  ExpressionPrime
|   IntegerLiteral
|   'true'
|   'false'
|   Identifier
|   'this'
|   'new' 'int' '[' Expression ']' 
|   'new' Identifier '(' ')' 
|   '!' Expression
|   '(' Expression ')' 
;   
ExpressionPrime   :   ( '&&' | '<' | '+' | '-' | '*' )  Expression
|'[' Expression ']'
|'.' 'length'
|'.' Identifier '(' [ Expression { ',' Expression } ] ')'
;


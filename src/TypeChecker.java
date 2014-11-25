import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.ParseTree;
import java.util.*;

public class TypeChecker extends MinijavaBaseVisitor<Klass> {
	final Map<String, Klass> klasses;
	ParseTreeProperty<Scope> scopes;
	Scope currentScope;
	MinijavaParser parser;
	Klass INT;
	Klass INTARRAY;
	Klass BOOLEAN;
	public TypeChecker(final Map<String, Klass> klasses, ParseTreeProperty<Scope> scopes, MinijavaParser parser)throws Exception{
		INT = klasses.get("int");
		this.klasses = klasses;
		this.scopes=scopes;        
		INT = klasses.get("int");
		INTARRAY = klasses.get("int[]");
		BOOLEAN = klasses.get("boolean");
		this.parser=parser;
		//throw new Exception("");
	}
	//@Override protected Klass aggregateResult(Klass aggregate, Klass nextResult){
	//	if(aggregate==null){
	//		return nextResult;
	//	}
	//	return aggregate;
	//}
	//public Klass visitChildren(@NotNull RuleNode node){
	//	Klass result = defaultResult();
	//	for(int i=0; i< node.getChildCount(); i++){
	//		if (!shouldVisitNextChild(node, result)) {
	//			break;
	//		}
	//		ParseTree c = node.getChild(i);
	//		RuleNode internalNode;
	//		if(c instanceof RuleNode){
	//		internalNode = (RuleNode)c;
	//		result = aggregateResult(result, visitChildren(internalNode));
	//		}
	//	}
	//	return result;
	//}
	@Override public Klass visitMainClass(@NotNull MinijavaParser.MainClassContext ctx){
		return scopingCall(ctx);
	} 
	@Override public Klass visitClassDeclaration(@NotNull MinijavaParser.ClassDeclarationContext ctx) { 
		//currentScope = scopes.get(ctx);
		//System.out.println("currentScope = " + currentScope);
		//visitChildren(ctx);
		//currentScope = currentScope.getEnclosingScope();
		//return null;
		return scopingCall(ctx); 
	}
	@Override public Klass visitMethodDeclaration(@NotNull MinijavaParser.MethodDeclarationContext ctx) { 
		//currentScope = scopes.get(ctx);
		//visitChildren(ctx);
		//currentScope = currentScope.getEnclosingScope();
		//return null;
		return scopingCall(ctx); 
	}
	@Override public Klass visitMethodBody(@NotNull MinijavaParser.MethodBodyContext ctx) {
		//visitChildren(ctx);
		for(MinijavaParser.VarDeclarationContext pCtx : ctx.varDeclaration()){visit(pCtx);}
		for(MinijavaParser.StatementContext pCtx : ctx.statement()){visit(pCtx);}
		Klass formalReturnType = Scope.getEnclosingMethod(currentScope).getType();
		//if(formalReturnType==null){System.err.println("The formal return type is null, which should never happen.");}
		Klass actualReturnType = visit(ctx.expression());
		//System.out.println("The children are: " + ctx.children);
		if(formalReturnType!=actualReturnType){
			ErrorPrinter.printRequiredFoundError(
				"error: incompatible types.", parser, ctx.RETURN().getSymbol(), formalReturnType.toString(), actualReturnType.toString());
		}
		return null;
	}
		

	@Override public Klass visitType(@NotNull MinijavaParser.TypeContext ctx) {
		if(ctx.Identifier()!=null){//it is a reference type
	        //ErrorPrinter.printFileNameAndLineNumber(ctx.Identifier().getSymbol());
	        String name = ctx.Identifier().getSymbol().getText();
	        Klass var = klasses.get(name);
	        if(var==null){
	        	ErrorPrinter.printUnresolvedSymbolError(parser, ctx.Identifier().getSymbol(), "class", Scope.getEnclosingKlass(currentScope));
	        }
	        return var;
	    }
	    return null;
	}
	@Override public Klass visitIfElseStatement(@NotNull MinijavaParser.IfElseStatementContext ctx) {
		visit(ctx.ifBlock());
		visit(ctx.elseBlock());
		Klass booleanExpression = visit(ctx.expression());
		if(booleanExpression!=BOOLEAN){
		ErrorPrinter.printRequiredFoundError(
			"error: incompatible types.", parser, ctx.LP().getSymbol(), BOOLEAN.toString(), booleanExpression.toString());
		}
		return null;
	}
	@Override public Klass visitWhileStatement(@NotNull MinijavaParser.WhileStatementContext ctx) {
		visit(ctx.statement());
		Klass booleanExpression = visit(ctx.expression());
		if(booleanExpression!=BOOLEAN){
		ErrorPrinter.printRequiredFoundError(
			"error: incompatible types.", parser, ctx.LP().getSymbol(), BOOLEAN.toString(), booleanExpression.toString());
		}
		return null;
	}
	@Override public Klass visitPrintStatement(@NotNull MinijavaParser.PrintStatementContext ctx) {

		Klass printContents = visit(ctx.expression());
		if(printContents!=INT){
		ErrorPrinter.printRequiredFoundError(
			"error: incompatible types.", parser, ctx.LP().getSymbol(), INT.toString(), printContents.toString());
		}
		return null;
	}
    @Override public Klass visitVariableAssignmentStatement(MinijavaParser.VariableAssignmentStatementContext ctx) {
        String name = ctx.Identifier().getSymbol().getText();
        Symbol var = currentScope.resolve(name);
        Klass rightSide = visit(ctx.expression());
        if ( var==null ) {
        	ErrorPrinter.printUnresolvedSymbolError(parser, ctx.Identifier().getSymbol(), "variable", Scope.getEnclosingKlass(currentScope));
        }else if(rightSide!=null && var.getType()!=rightSide){
	        	ErrorPrinter.printRequiredFoundError("error: incompatible types.", parser, ctx.Identifier().getSymbol(), var.getType().toString(), (rightSide.toString()));
    	}
       	return null;
    }
	@Override public Klass visitArrayAssignmentStatement(@NotNull MinijavaParser.ArrayAssignmentStatementContext ctx) {
        String name = ctx.Identifier().getSymbol().getText();
        Symbol var = currentScope.resolve(name);
        Klass index = visit(ctx.expression(0));
        Klass rightSide = visit(ctx.expression(1));
        if ( var==null ) {
        	ErrorPrinter.printUnresolvedSymbolError(parser, ctx.Identifier().getSymbol(), "variable", Scope.getEnclosingKlass(currentScope));
        }else if(rightSide!=null && INT!=rightSide){
	       	ErrorPrinter.printRequiredFoundError("error: incompatible types.", parser, ctx.Identifier().getSymbol(), var.toString(), (rightSide.toString()));
        }else if(index!=INT){
        	ErrorPrinter.printRequiredFoundError("error: incompatible type.", parser, ctx.LSB().getSymbol(), INT.toString(), index.toString());
        }
        return null;
	}
    //@Override public void exitMethodCallExpression(MinijavaParser.MethodCallExpressionContext ctx) {
    //    // can only handle f(...) not expr(...)
    //    //ErrorPrinter.printFileNameAndLineNumber(ctx.Identifier().getSymbol());
    //	Klass klass = new TypeChecker(klasses, scopes, parser).visit(ctx.expression(0));
    //    String funcName = ctx.Identifier().getText()+"()";
    //    Symbol meth = klass.resolve(funcName);
    //}
	//@Override public void exitIdentifierExpression(@NotNull MinijavaParser.IdentifierExpressionContext ctx) {
	//
	//}
	@Override public Klass visitAndExpression(@NotNull MinijavaParser.AndExpressionContext ctx) { 
	    ErrorPrinter.binaryOperatorTypeError(parser, ctx, ctx.AND().getSymbol(), visit(ctx.expression(0)), visit(ctx.expression(1)), BOOLEAN, BOOLEAN);
		return BOOLEAN; 
	}
	@Override public Klass visitLtExpression(@NotNull MinijavaParser.LtExpressionContext ctx) { 
	    ErrorPrinter.binaryOperatorTypeError(parser, ctx, ctx.LT().getSymbol(), visit(ctx.expression(0)), visit(ctx.expression(1)), INT, INT);
		return BOOLEAN;
	}
	@Override public Klass visitAddExpression(@NotNull MinijavaParser.AddExpressionContext ctx) {
	    ErrorPrinter.binaryOperatorTypeError(parser, ctx, ctx.PLUS().getSymbol(), visit(ctx.expression(0)), visit(ctx.expression(1)), INT, INT);
		return INT;
	}
	@Override public Klass visitSubExpression(@NotNull MinijavaParser.SubExpressionContext ctx) {
	    ErrorPrinter.binaryOperatorTypeError(parser, ctx, ctx.MINUS().getSymbol(), visit(ctx.expression(0)), visit(ctx.expression(1)), INT, INT);
		return INT;
	}
	@Override public Klass visitMulExpression(@NotNull MinijavaParser.MulExpressionContext ctx) {
	    ErrorPrinter.binaryOperatorTypeError(parser, ctx, ctx.TIMES().getSymbol(), visit(ctx.expression(0)), visit(ctx.expression(1)), INT, INT);
		return INT;
	}
	@Override public Klass visitPowExpression(@NotNull MinijavaParser.PowExpressionContext ctx) { 
	    ErrorPrinter.binaryOperatorTypeError(parser, ctx, ctx.POWER().getSymbol(), visit(ctx.expression(0)), visit(ctx.expression(1)), INT, INT);
		return INT;
	}
	@Override public Klass visitArrayAccessExpression(@NotNull MinijavaParser.ArrayAccessExpressionContext ctx) {
		Klass array = visit(ctx.expression(0));
		Klass index = visit(ctx.expression(1));
		if(array!=INTARRAY){
			ErrorPrinter.printFileNameAndLineNumber(ctx.LSB().getSymbol());
			System.err.println("error: array required, but " + array + " found");
			ErrorPrinter.underlineError(parser, ctx.LSB().getSymbol());
		}
		if(index!=INT){
			ErrorPrinter.printRequiredFoundError("error: incompatible type.", parser, ctx.LSB().getSymbol(), INT.toString(), index.toString());
		}
		//ErrorPrinter.binaryOperatorTypeError(parser, ctx, ctx.LSB().getSymbol(), visit(ctx.expression(0)), visit(ctx.expression(1)), INTARRAY, INT);
		return INT;
	}
	@Override public Klass visitArrayLengthExpression(@NotNull MinijavaParser.ArrayLengthExpressionContext ctx) {
		Klass intArr = visit(ctx.expression());
		if(intArr!=INTARRAY){
			ErrorPrinter.printFileNameAndLineNumber(ctx.DOTLENGTH().getSymbol());
			System.err.println("error: bad operand type " + intArr + " for unary operator '.length'");
			ErrorPrinter.underlineError(parser, ctx.DOTLENGTH().getSymbol());
		}
		return INT;
	}
	@Override public Klass visitMethodCallExpression(@NotNull MinijavaParser.MethodCallExpressionContext ctx) {
		Klass type = visit(ctx.expression(0));
        //String funcName = ctx.Identifier().getText()+"()";
        //Symbol meth = type.resolve(funcName);
		if(type==null){
			return null;
		}
		String methodName = ctx.Identifier().getText() +"()";
		Klass.Method method = (Klass.Method)(type.resolve(methodName));
		//for(String symbol : type.symTable.keySet()){
		//	System.out.println("Symbol: " + symbol);
		//}
        if (method==null ) {
        		//System.out.println("currentScope = " + currentScope);
        		//ErrorPrinter.printFileNameAndLineNumber(ctx.Identifier().getSymbol());
        		ErrorPrinter.printUnresolvedSymbolError(parser, ctx.Identifier().getSymbol(), "method", type);
        		return null;
    	}else{
			List<Klass> parameterList = new ArrayList<Klass>();
			//System.out.println("type of first parameter = " + visit(ctx.expression(1)));
			//parameterList.add(visit(ctx.expression(1)));
			for(MinijavaParser.ExpressionContext expCtx : ctx.expression().subList(1, ctx.expression().size())){
				parameterList.add(visit(expCtx));
			}
			List<Klass> parameterListDefinition = method.getParameterListDefinition();
			if(!parameterList.equals(parameterListDefinition)){
				ErrorPrinter.printRequiredFoundError(
					"error: method call parameters of method " + method.getName() + " do not match method definition.",
					parser, ctx.Identifier().getSymbol(), parameterListDefinition.toString(), parameterList.toString());
			}
			return method.getType();
		}
	}

	@Override public Klass visitIntLitExpression(@NotNull MinijavaParser.IntLitExpressionContext ctx) { visitChildren(ctx); return INT;}
	@Override public Klass visitBooleanLitExpression(@NotNull MinijavaParser.BooleanLitExpressionContext ctx) { visitChildren(ctx); return BOOLEAN; }
	@Override public Klass visitIdentifierExpression(@NotNull MinijavaParser.IdentifierExpressionContext ctx) {
		String name = ctx.Identifier().getSymbol().getText();
        Symbol var = currentScope.resolve(name);
        if ( var==null ){
        	ErrorPrinter.printUnresolvedSymbolError(parser, ctx.Identifier().getSymbol(), "variable", Scope.getEnclosingKlass(currentScope));
        	return null;
        }
		return var.getType();
	}
	@Override public Klass visitThisExpression(@NotNull MinijavaParser.ThisExpressionContext ctx) { visitChildren(ctx); return Scope.getEnclosingKlass(currentScope);}
	
	@Override public Klass visitArrayInstantiationExpression(@NotNull MinijavaParser.ArrayInstantiationExpressionContext ctx) {
		Klass type = visit(ctx.expression());
		if(type!=INT){
			ErrorPrinter.printRequiredFoundError("error: incompatible types.", parser, ctx.LSB().getSymbol(), INT.toString(), type.toString());
		}
		return INTARRAY;
	}
	@Override public Klass visitObjectInstantiationExpression(@NotNull MinijavaParser.ObjectInstantiationExpressionContext ctx) {
		Klass type = klasses.get(ctx.Identifier().getText());
        if ( type==null ){
        	ErrorPrinter.printUnresolvedSymbolError(parser, ctx.Identifier().getSymbol(), "class", Scope.getEnclosingKlass(currentScope));
        }
		return type;
	}

	@Override public Klass visitNotExpression(@NotNull MinijavaParser.NotExpressionContext ctx) {
		Klass bool = visit(ctx.expression());
		if(bool!=BOOLEAN){
			ErrorPrinter.printFileNameAndLineNumber(ctx.NOT().getSymbol());
			System.err.println("error: bad operand type " + bool + " for unary operator '!'");
            ErrorPrinter.underlineError(parser, ctx.NOT().getSymbol());
		}
		return BOOLEAN; 
	}
	@Override public Klass visitParenExpression(@NotNull MinijavaParser.ParenExpressionContext ctx) {
		return visit(ctx.expression());
	}


	//----------------------------------------------------------------------
	public Klass scopingCall(ParserRuleContext ctx){
		currentScope = scopes.get(ctx);
		Klass result =  visitChildren(ctx);
		currentScope = currentScope.getEnclosingScope();
		return null;
	}
	//public Klass getEnclosingKlass(Scope scope){
	//	while(scope.getEnclosingScope()!=null){
	//		scope=scope.getEnclosingScope();
	//	}
	//	return (Klass)scope;//The outermost scope will always be a class.
	//}

}
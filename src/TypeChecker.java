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
	ParseTreeProperty<Klass> callerTypes;
	MinijavaParser parser;
	Klass INT;
	Klass INTARRAY;
	Klass BOOLEAN;
	public TypeChecker(final Map<String, Klass> klasses, ParseTreeProperty<Scope> scopes, ParseTreeProperty<Klass> callerTypes, MinijavaParser parser){
		INT = klasses.get("int");
		this.klasses = klasses;
		this.scopes=scopes;
		this.callerTypes = callerTypes;        
		INT = klasses.get("int");
		INTARRAY = klasses.get("int[]");
		BOOLEAN = klasses.get("boolean");
		this.parser=parser;
	}
	@Override public Klass visitMainClass(@NotNull MinijavaParser.MainClassContext ctx){
		return scopingCall(ctx);
	} 
	@Override public Klass visitClassDeclaration(@NotNull MinijavaParser.ClassDeclarationContext ctx) { 
		return scopingCall(ctx); 
	}
	@Override public Klass visitMethodDeclaration(@NotNull MinijavaParser.MethodDeclarationContext ctx) { 
		currentScope = scopes.get(ctx);

		Klass originalKlass = ((Klass)(currentScope.getEnclosingScope())).getSuperKlass();
		Method originalMethod;
		if(originalKlass==null){ originalMethod=null; }
		else{ originalMethod=(Method)originalKlass.lookup(currentScope.getScopeName());}
			
		Method currentMethod = (Method)currentScope;
		Klass currentKlass = (Klass)currentMethod.getEnclosingScope();
        if(originalMethod!=null && originalMethod.getType() != currentMethod.getType()){
            ErrorPrinter.printIncompatibleReturnTypeError(parser, ctx.Identifier().getSymbol(), originalKlass, currentKlass, originalMethod, currentMethod);
        }

		Klass result =  visitChildren(ctx);
		currentScope = currentScope.getEnclosingScope();
        return null;
	}
	@Override public Klass visitMethodBody(@NotNull MinijavaParser.MethodBodyContext ctx) {
        //The return type type-check is working correctly with inheritence.
		for(MinijavaParser.LocalDeclarationContext pCtx : ctx.localDeclaration()){visit(pCtx);}
		for(MinijavaParser.StatementContext pCtx : ctx.statement()){visit(pCtx);}
		Klass formalReturnType = Scope.getEnclosingMethod(currentScope).getType();
		Klass actualReturnType = visit(ctx.expression());
		if(actualReturnType!=null && !actualReturnType.isInstanceOf(formalReturnType)){
			ErrorPrinter.printRequiredFoundError(
				"error: incompatible types.", parser, ctx.RETURN().getSymbol(), formalReturnType.toString(), actualReturnType.toString());
		}
		return null;
	}
	@Override public Klass visitType(@NotNull MinijavaParser.TypeContext ctx) {
        //Correctly reported error during variable intialization test with non-existent class.
		if(ctx.Identifier()!=null){//it is a reference type
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
        //Correctly reported error with int instead of boolean.
		Klass booleanExpression = visit(ctx.expression());
		visit(ctx.ifBlock());
		visit(ctx.elseBlock());
		if(booleanExpression!=BOOLEAN){
		ErrorPrinter.printRequiredFoundError(
			"error: incompatible types.", parser, ctx.LP().getSymbol(), BOOLEAN.toString(), booleanExpression.toString());
		}
		return null;
	}
	@Override public Klass visitWhileStatement(@NotNull MinijavaParser.WhileStatementContext ctx) {
        //Correctly reported error with int instead of boolean.
		Klass booleanExpression = visit(ctx.expression());
		visit(ctx.whileBlock());
		if(booleanExpression!=BOOLEAN){
		ErrorPrinter.printRequiredFoundError(
			"error: incompatible types.", parser, ctx.LP().getSymbol(), BOOLEAN.toString(), booleanExpression.toString());
		}
		return null;
	}
	@Override public Klass visitPrintStatement(@NotNull MinijavaParser.PrintStatementContext ctx) {
        //Correctly reported error of boolean instead of int.
		Klass printContents = visit(ctx.expression());
		if(printContents!=null && printContents!=INT){
		ErrorPrinter.printRequiredFoundError(
			"error: incompatible types.", parser, ctx.LP().getSymbol(), INT.toString(), printContents.toString());
		}
		return null;
	}
    @Override public Klass visitVariableAssignmentStatement(MinijavaParser.VariableAssignmentStatementContext ctx){
        //correctly reported errors in all cases.
        String name = ctx.Identifier().getSymbol().getText();
        Symbol var = currentScope.lookup(name);

        Klass rightSide = visit(ctx.expression());
        if ( var==null ) {
        	ErrorPrinter.printUnresolvedSymbolError(parser, ctx.Identifier().getSymbol(), "variable", Scope.getEnclosingKlass(currentScope));
        }else if(rightSide!=null && !rightSide.isInstanceOf(var.getType())){
	        	ErrorPrinter.printRequiredFoundError("error: incompatible types.", parser, ctx.Identifier().getSymbol(), var.getType().toString(), (rightSide.toString()));
    	}
       	return null;
    }
	@Override public Klass visitArrayAssignmentStatement(@NotNull MinijavaParser.ArrayAssignmentStatementContext ctx) {
        //correctly reported errors in all cases
		//ErrorPrinter.printFileNameAndLineNumber(ctx.Identifier().getSymbol());
        String name = ctx.Identifier().getSymbol().getText();
        Symbol var = currentScope.lookup(name);
        Klass index = visit(ctx.expression(0));
        Klass rightSide = visit(ctx.expression(1));
        if ( var==null ) {
        	ErrorPrinter.printUnresolvedSymbolError(parser, ctx.Identifier().getSymbol(), "variable", Scope.getEnclosingKlass(currentScope));
        }else if(var.getType()!=INTARRAY){
	       	ErrorPrinter.printRequiredFoundError("error: incompatible types.", parser, ctx.LSB().getSymbol(), INTARRAY.toString(), (var.getType().toString()));
        }else if(rightSide!=null && INT!=rightSide){
	       	ErrorPrinter.printRequiredFoundError("error: incompatible types.", parser, ctx.EQ().getSymbol(), INT.toString(), (rightSide.toString()));
        }else if(index!=INT){
        	ErrorPrinter.printRequiredFoundError("error: incompatible types.", parser, ctx.LSB().getSymbol(), INT.toString(), index.toString());
        }
        return null;
	}
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
        //Error reporting test successful.
	    ErrorPrinter.binaryOperatorTypeError(parser, ctx, ctx.POWER().getSymbol(), visit(ctx.expression(0)), visit(ctx.expression(1)), INT, INT);
		return INT;
	}
	@Override public Klass visitArrayAccessExpression(@NotNull MinijavaParser.ArrayAccessExpressionContext ctx) {
        //Error reporting test successful.
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
		callerTypes.put(ctx, type);
		if(type==null){
			return null;
		}
		String methodName = ctx.Identifier().getText() +"()";
		Method method = (Method)(type.lookup(methodName));
        if (method==null ) {
        		ErrorPrinter.printUnresolvedSymbolError(parser, ctx.Identifier().getSymbol(), "method", type);
        		return null;
    	}else{
			List<Klass> parameterList = new ArrayList<Klass>();
			for(MinijavaParser.ExpressionContext expCtx : ctx.expression().subList(1, ctx.expression().size())){
				parameterList.add(visit(expCtx));
			}
			List<Klass> parameterListDefinition = method.getParameterListDefinition();
            if(parameterListDefinition.size()!=parameterList.size()){
				ErrorPrinter.printRequiredFoundError(
					"error: method call parameters of method " + method.getName() + " do not match method definition.",
					parser, ctx.Identifier().getSymbol(), parameterListDefinition.toString(), parameterList.toString());
                System.err.println("reason: actual and formal argument lists differ in length.");
                return method.getType();
            }
            for(int i=0; i<parameterListDefinition.size(); i++){
                if(!parameterList.get(i).isInstanceOf(parameterListDefinition.get(i))){
				ErrorPrinter.printRequiredFoundError(
					"error: method call parameters of method " + method.getName() + " do not match method definition.",
					parser, ctx.Identifier().getSymbol(), parameterListDefinition.toString(), parameterList.toString());
                }
            }
			return method.getType();
		}
	}

	@Override public Klass visitIntLitExpression(@NotNull MinijavaParser.IntLitExpressionContext ctx) { 
		visitChildren(ctx); 
		return INT;
	}
	@Override public Klass visitBooleanLitExpression(@NotNull MinijavaParser.BooleanLitExpressionContext ctx) { 
		visitChildren(ctx); 
		return BOOLEAN;
	}
	@Override public Klass visitIdentifierExpression(@NotNull MinijavaParser.IdentifierExpressionContext ctx) {
		String name = ctx.Identifier().getSymbol().getText();
        Symbol var = currentScope.lookup(name);
        if ( var==null ){
        	ErrorPrinter.printUnresolvedSymbolError(parser, ctx.Identifier().getSymbol(), "variable", Scope.getEnclosingKlass(currentScope));
        	return null;
        }
		return var.getType();
	}
	@Override public Klass visitThisExpression(@NotNull MinijavaParser.ThisExpressionContext ctx) { 
		visitChildren(ctx); 
		return Scope.getEnclosingKlass(currentScope);
	}
	
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
}

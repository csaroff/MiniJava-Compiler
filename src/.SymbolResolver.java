import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import java.util.*;

public class SymbolResolver extends MinijavaBaseListener{
	MinijavaParser parser;
	Map<String, Klass> klasses;
    ParseTreeProperty<Scope> scopes;
    Scope currentScope; // resolve symbols starting in this scope

    public SymbolResolver(Map<String, Klass> klasses, ParseTreeProperty<Scope> scopes, MinijavaParser parser) {
        this.scopes = scopes;
        this.klasses = klasses;
        this.parser=parser;
    }
    @Override public void enterClassDeclaration(@NotNull MinijavaParser.ClassDeclarationContext ctx) {
        currentScope = scopes.get(ctx);
    }
    @Override public void exitClassDeclaration(@NotNull MinijavaParser.ClassDeclarationContext ctx) {
        currentScope = currentScope.getEnclosingScope();
    }
    @Override public void enterMethodDeclaration(MinijavaParser.MethodDeclarationContext ctx) {
        currentScope = scopes.get(ctx);
    }
    @Override public void exitMethodDeclaration(MinijavaParser.MethodDeclarationContext ctx) {
        currentScope = currentScope.getEnclosingScope();
    }
    //@Override public void enterNestedStatement(@NotNull MinijavaParser.NestedStatementContext ctx){
    //    currentScope = scopes.get(ctx);
    //}
    //@Override public void exitNestedStatement(@NotNull MinijavaParser.NestedStatementContext ctx){
    //    currentScope = currentScope.getEnclosingScope();
    //}
    //@Override public void enterIfBlock(@NotNull MinijavaParser.IfBlockContext ctx){
    //    currentScope = scopes.get(ctx);
    //}
    //@Override public void exitIfBlock(@NotNull MinijavaParser.IfBlockContext ctx){
    //    currentScope = currentScope.getEnclosingScope();
    //}
    //@Override public void enterWhileStatement(@NotNull MinijavaParser.WhileStatementContext ctx){
    //    currentScope = scopes.get(ctx);
    //}
    //@Override public void exitWhileStatement(@NotNull MinijavaParser.WhileStatementContext ctx){
    //    currentScope = currentScope.getEnclosingScope();
    //}

	@Override public void exitType(@NotNull MinijavaParser.TypeContext ctx) {
		if(ctx.Identifier()!=null){
	        //ErrorPrinter.printFileNameAndLineNumber(ctx.Identifier().getSymbol());
	        String name = ctx.Identifier().getSymbol().getText();
	        Klass var = klasses.get(name);
	        if(var==null){
	        	ErrorPrinter.unresolvedSymbolError(parser, ctx, ctx.Identifier().getSymbol(), "class", Scope.getEnclosingKlass(currentScope));
	        }
	    }
	}
    @Override public void exitVariableAssignmentStatement(MinijavaParser.VariableAssignmentStatementContext ctx) {
        String name = ctx.Identifier().getSymbol().getText();
        Symbol var = currentScope.resolve(name);
        if ( var==null ) {
        	ErrorPrinter.unresolvedSymbolError(parser, ctx, ctx.Identifier().getSymbol(), "variable", Scope.getEnclosingKlass(currentScope));
        }
    }
	@Override public void exitArrayAssignmentStatement(@NotNull MinijavaParser.ArrayAssignmentStatementContext ctx) {
        String name = ctx.Identifier().getSymbol().getText();
        Symbol var = currentScope.resolve(name);
        if ( var==null ) {
        	ErrorPrinter.unresolvedSymbolError(parser, ctx, ctx.Identifier().getSymbol(), "variable", Scope.getEnclosingKlass(currentScope));
        }
	}
    @Override public void exitMethodCallExpression(MinijavaParser.MethodCallExpressionContext ctx) {
        // can only handle f(...) not expr(...)
        ErrorPrinter.printFileNameAndLineNumber(ctx.Identifier().getSymbol());
    	Klass klass = new TypeChecker(klasses, scopes, parser).visit(ctx.expression(0));
        String funcName = ctx.Identifier().getText()+"()";
        Symbol meth = klass.resolve(funcName);
        if ( meth==null ) {
        	ErrorPrinter.unresolvedSymbolError(parser, ctx, ctx.Identifier().getSymbol(), "method", Scope.getEnclosingKlass(currentScope));
        }
    }
	@Override public void exitIdentifierExpression(@NotNull MinijavaParser.IdentifierExpressionContext ctx) {
        String name = ctx.Identifier().getSymbol().getText();
        Symbol var = currentScope.resolve(name);
        if ( var==null ){
        	ErrorPrinter.unresolvedSymbolError(parser, ctx, ctx.Identifier().getSymbol(), "variable", Scope.getEnclosingKlass(currentScope));
        }
	}
	@Override public void exitObjectInstantiationExpression(@NotNull MinijavaParser.ObjectInstantiationExpressionContext ctx) {
        String name = ctx.Identifier().getSymbol().getText();
        Klass var = klasses.get(name);
        if(var==null){
        	ErrorPrinter.unresolvedSymbolError(parser, ctx, ctx.Identifier().getSymbol(), "class", Scope.getEnclosingKlass(currentScope));
        }
	}
}
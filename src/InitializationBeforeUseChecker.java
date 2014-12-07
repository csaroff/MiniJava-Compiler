import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import java.util.*;

public class InitializationBeforeUseChecker extends MinijavaBaseVisitor<Set<Symbol>> {
    MinijavaParser parser;
    final Map<String, Klass> klasses;
    //Stack<Scope> scopes = new Stack<Scope>();
    ParseTreeProperty<Scope> scopes;
    Scope currentScope = null;
    //Set<Symbol> initializedInIf = null;
    //Set<Symbol> initializedInElse = null;

    public InitializationBeforeUseChecker(final Map<String, Klass> klasses, ParseTreeProperty<Scope> scopes, MinijavaParser parser){
        this.scopes=scopes;
        this.klasses=klasses;
        this.parser=parser;
    }
    @Override public Set<Symbol> visitClassDeclaration(@NotNull MinijavaParser.ClassDeclarationContext ctx) { 
        enterScope(ctx);
        visitChildren(ctx);
        exitScope();
        return null; 
    }

    @Override public Set<Symbol> visitMethodDeclaration(@NotNull MinijavaParser.MethodDeclarationContext ctx) {
        enterScope(ctx);
        visitChildren(ctx);
        exitScope();
        return null; 
    }
    //@Override public void enterIfElseStatement(@NotNull MinijavaParser.IfElseStatementContext ctx) {}
    @Override public Set<Symbol> visitIfElseStatement(@NotNull MinijavaParser.IfElseStatementContext ctx) {
        visit(ctx.expression());
        Set<Symbol> initializedVariables = visit(ctx.ifBlock());
        initializedVariables.retainAll(visit(ctx.elseBlock()));
        for(Symbol sym : initializedVariables){
            currentScope.initialize(sym);
        }
        return initializedVariables;
    }
    @Override public Set<Symbol> visitIfBlock(@NotNull MinijavaParser.IfBlockContext ctx) { 
        enterScope(ctx);
        visitChildren(ctx);
        Set<Symbol> ifInit = currentScope.getInitializedVariables();
        exitScope();
        return ifInit;
    }
    @Override public Set<Symbol> visitElseBlock(@NotNull MinijavaParser.ElseBlockContext ctx) { 
        enterScope(ctx);
        visitChildren(ctx);
        Set<Symbol> elseInit = currentScope.getInitializedVariables();
        exitScope();
        return elseInit;
    }
    @Override public Set<Symbol> visitWhileStatement(@NotNull MinijavaParser.WhileStatementContext ctx) { 
        enterScope(ctx);
        visitChildren(ctx);
        exitScope();
        return null;
    }
    @Override public Set<Symbol> visitVariableAssignmentStatement(@NotNull MinijavaParser.VariableAssignmentStatementContext ctx) {
        Set<Symbol> sym = visitChildren(ctx);
        currentScope.initialize(currentScope.resolve(ctx.Identifier().getText()));
        return sym;
    }
    @Override public Set<Symbol> visitIdentifierExpression(@NotNull MinijavaParser.IdentifierExpressionContext ctx) {
        String identifier = ctx.Identifier().getText();
        if(!currentScope.hasBeenInitialized(identifier)){
            ErrorPrinter.printVariableMayNotHaveBeenInitializedError(parser, ctx.Identifier().getSymbol(), identifier);
        }
        return visitChildren(ctx);
    }
    public void enterScope(ParserRuleContext ctx){
        currentScope = scopes.get(ctx);
    }
    private void exitScope(){
        currentScope = currentScope.getEnclosingScope();
    }
}

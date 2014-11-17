import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import java.util.*;

public class AssignmentListener extends MinijavaBaseListener {
    MinijavaParser parser;
    Map<String, Klass> klasses;
    //Klass currentKlass;
    //Klass.Method currentMethod = null;
    Stack<Scope> scopes = new Stack<Scope>();
    Scope currentScope = null;
    public AssignmentListener(Map<String, Klass> klasses, MinijavaParser parser){
        this.klasses=klasses;
        this.parser=parser;
    }
    @Override public void enterClassDeclaration(@NotNull MinijavaParser.ClassDeclarationContext ctx) { 
        Klass klass = klasses.get(ctx.Identifier(0).getText());
        currentScope = klass;
        scopes.push(currentScope);
        Klass superKlass;
        if(ctx.Identifier().size()>1){
            superKlass = klasses.get(ctx.Identifier(1).getText());
            if(superKlass==null){
                System.err.println("error: cannot find symbol.");
                ErrorPrinter.underlineError(parser, ctx.Identifier(1).getSymbol());
                System.err.println("symbol:   class " + ctx.Identifier(1).getText());
            }
        }else{
            superKlass=null;
        }
        klass.superKlass = superKlass;
    }
    @Override public void exitClassDeclaration(@NotNull MinijavaParser.ClassDeclarationContext ctx) {
        //currentKlass=null;
        currentScope = scopes.pop();
    }
    @Override public void enterMainClass(@NotNull MinijavaParser.MainClassContext ctx) {
    }
    @Override public void enterVarDeclaration(@NotNull MinijavaParser.VarDeclarationContext ctx) {
        String typeName = ctx.type().getText();
        String varName = ctx.Identifier().getText();
        currentScope.define(new Symbol(varName, klasses.get(typeName)));
    }
    @Override public void enterMethodDeclaration(@NotNull MinijavaParser.MethodDeclarationContext ctx) {
        Klass returnType = klasses.get(ctx.type().getText());
        if(returnType==null){
            //The return type of this method is undefined.  
            //error(ctx.Identifier().getSymbol(), "The return type of method: " + currentMethod.name + " is not defined.");
            //Token t = ctx.Identifier().getSymbol();
            //ErrorPrinter.underlineError(parser, t, t.getLine(), t.getCharPositionInLine());
            ErrorPrinter.printFullError(parser, ctx.type().Identifier().getSymbol(),
                "error: cannot find symbol.",
                "symbol:   class " + ctx.type().getText(),
                "location: class " + currentScope.getScopeName()
            );
        }
        String methodName = Main.getMethodSignature(ctx);
        Scope owner = currentScope;
        Klass.Method method = new Klass.Method(returnType, methodName, owner);
        currentScope.define(method);
        scopes.push(method);
        currentScope = method;
        //System.out.println("Current Klass: " + currentKlass + " and currentMethod = " + currentMethod);
    }
    @Override public void exitMethodDeclaration(@NotNull MinijavaParser.MethodDeclarationContext ctx) {
        currentScope = scopes.pop();
    }
    @Override public void enterParameter(@NotNull MinijavaParser.ParameterContext ctx) {
        Klass parameterType = klasses.get(ctx.type().getText());
        Symbol parameter;
        if(parameterType==null){
            //report error.  Undefined type.
            //error(ctx.Identifier().getSymbol(), "Error, undefined type.");
            ErrorPrinter.printFullError(parser, ctx.type().Identifier().getSymbol(),
                "error: cannot find symbol.",
                "symbol:   class " + ctx.type().getText(),
                "location: class " + currentScope.getEnclosingScope().getScopeName()
            );
        }
        parameter = new Symbol(ctx.Identifier().getText(), parameterType);
        ((Klass.Method)currentScope).addParameter(parameter);
    }
    void enterNestedStatement(@NotNull MinijavaParser.NestedStatementContext ctx){
        createBlock();
    }
    void exitNestedStatement(@NotNull MinijavaParser.NestedStatementContext ctx){
        currentScope = scopes.pop();
    }
    void enterWhileStatement(@NotNull MinijavaParser.WhileStatementContext ctx){
        createBlock();
    }
    void exitWhileStatement(@NotNull MinijavaParser.WhileStatementContext ctx){
        currentScope = scopes.pop();
    }

    public void createBlock(){
        Klass.Block explicitScope = new Klass.Block(currentScope);
        currentScope.define(explicitScope);
        currentScope = explicitScope;
        scopes.push(currentScope);
    }
}

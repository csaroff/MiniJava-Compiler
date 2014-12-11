import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import java.util.*;

public class AssignmentListener extends MinijavaBaseListener {
    MinijavaParser parser;
    final Map<String, Klass> klasses;
    //Stack<Scope> scopes = new Stack<Scope>();
    ParseTreeProperty<Scope> scopes;
    Scope currentScope = null;
    boolean isField;
    boolean isArg;
    int argCount=-1;
    public AssignmentListener(final Map<String, Klass> klasses, ParseTreeProperty<Scope> scopes, MinijavaParser parser){
        this.scopes=scopes;
        this.klasses=klasses;
        this.parser=parser;
    }
    void saveScope(ParserRuleContext ctx, Scope s) { scopes.put(ctx, s); }
    @Override public void enterMainClass(@NotNull MinijavaParser.MainClassContext ctx) { 
        Klass klass = klasses.get(ctx.Identifier(0).getText());
        currentScope = klass;
        saveScope(ctx, currentScope);
    }
    @Override public void enterClassDeclaration(@NotNull MinijavaParser.ClassDeclarationContext ctx) { 
        Klass klass = klasses.get(ctx.Identifier(0).getText());
        currentScope = klass;
        saveScope(ctx, currentScope);
        //scopes.push(currentScope);
        Klass superKlass;
        if(ctx.Identifier().size()>1){
            superKlass = klasses.get(ctx.Identifier(1).getText());
            if(superKlass==null){
                ErrorPrinter.printFileNameAndLineNumber(ctx.Identifier(1).getSymbol());
                System.err.println("error: cannot find symbol.");
                ErrorPrinter.underlineError(parser, ctx.Identifier(1).getSymbol());
                System.err.println("symbol:   class " + ctx.Identifier(1).getText());
            }
        }else{
            superKlass=null;
        }
        klass.superKlass = superKlass;
        ErrorPrinter.reportCyclicInheritance(parser, ctx, klass);
    }
    @Override public void exitClassDeclaration(@NotNull MinijavaParser.ClassDeclarationContext ctx) {
        //currentKlass=null;
        currentScope = currentScope.getEnclosingScope();
    }

    @Override public void enterFieldDeclaration(@NotNull MinijavaParser.FieldDeclarationContext ctx) {isField=true;}
    @Override public void exitFieldDeclaration(@NotNull MinijavaParser.FieldDeclarationContext ctx) {isField=false;}
    //@Override public void enterLocalDeclaration(@NotNull MinijavaParser.LocalDeclarationContext ctx) {isField=false;}
    @Override public void enterVarDeclaration(@NotNull MinijavaParser.VarDeclarationContext ctx) {
        String typeName = ctx.type().getText();
        String varName = ctx.Identifier().getText();
        if(currentScope.resolveLocally(varName)!=null){
            ErrorPrinter.printSymbolAlreadyDefinedError(parser, ctx.Identifier().getSymbol(), "variable", varName, currentScope.getScopeName());
        }
        currentScope.define(new Symbol(varName, klasses.get(typeName), isField));
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
        String methodName = Method.getMethodSignature(ctx);
        //System.out.println("Current Scope = " + currentScope);
        //System.out.println("Method Name = " + methodName);
        if(currentScope.resolveLocally(methodName)!=null){
            ErrorPrinter.printSymbolAlreadyDefinedError(parser, ctx.Identifier().getSymbol(), "method", methodName, currentScope.getScopeName());
        }
        Scope owner = currentScope;
        Method method = new Method(returnType, methodName, owner);
        currentScope.define(method);
        currentScope = method;
        saveScope(ctx, currentScope);
        //scopes.push(method);
        //System.out.println("Current Klass: " + currentKlass + " and currentMethod = " + currentMethod);
    }
    @Override public void exitMethodDeclaration(@NotNull MinijavaParser.MethodDeclarationContext ctx) {
        currentScope = currentScope.getEnclosingScope();
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
        //parameter is not a field
        parameter = new Symbol(ctx.Identifier().getText(), parameterType, false);
        ((Method)currentScope).addParameter(parameter);
    }
    @Override public void enterNestedStatement(@NotNull MinijavaParser.NestedStatementContext ctx){
        enterScope(ctx);
    }
    @Override public void exitNestedStatement(@NotNull MinijavaParser.NestedStatementContext ctx){
        exitScope();
    }
    @Override public void enterIfBlock(@NotNull MinijavaParser.IfBlockContext ctx){
        enterScope(ctx);
    }
    @Override public void exitIfBlock(@NotNull MinijavaParser.IfBlockContext ctx){
        exitScope();
    }
    @Override public void enterElseBlock(@NotNull MinijavaParser.ElseBlockContext ctx){
        enterScope(ctx);
    }
    @Override public void exitElseBlock(@NotNull MinijavaParser.ElseBlockContext ctx){
        exitScope();
    }
    @Override public void enterWhileStatement(@NotNull MinijavaParser.WhileStatementContext ctx){
        enterScope(ctx);
    }
    @Override public void exitWhileStatement(@NotNull MinijavaParser.WhileStatementContext ctx){
        exitScope();
    }
    public void enterScope(ParserRuleContext ctx){
        Block explicitScope = new Block(currentScope);
        //Do parent scopes need to know about their children?
        //currentScope.define(explicitScope);
        currentScope = explicitScope;
        saveScope(ctx, currentScope);
    }
    public void exitScope(){
        currentScope = currentScope.getEnclosingScope();
    }
}

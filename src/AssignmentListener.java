import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import java.util.*;

/**
 * While ClassNamer created Klass objects and assigned them names, 
 * AssignmentListener fills those Klasses with their contents
 * eg. Methods, fields, etc.
 * It also fills Methods with their contents
 * eg. Locals, subscopes, etc.
 * Lastly, it reports certain types of errors, such as cyclic inheritance errors.
 */
public class AssignmentListener extends MinijavaBaseListener {
    
    //The parser that generate the parse tree that this listener will walk.
    //Used exclusively for error reporting.
    MinijavaParser parser;

    //A symbol table representation of the classes in the program that is being compiled.
    final Map<String, Klass> klasses;

    //The global symbol table, mapping parse tree nodes, to their scope.
    ParseTreeProperty<Scope> scopes;

    //The scope of the parse tree node that is currently being traversed.
    Scope currentScope = null;

    //Signifies whether or not the current variable declaration is a field declaration.
    boolean isField;

    /**
     * Creates a new AssignmentListener object, 
     * initializing its fields with the program state and
     * the parser that generated the parse tree that it will traverse.
     */
    public AssignmentListener(final Map<String, Klass> klasses, ParseTreeProperty<Scope> scopes, MinijavaParser parser){
        this.scopes=scopes;
        this.klasses=klasses;
        this.parser=parser;
    }

    /**
     * Saves Scope s in scopes, associating it with the given ParserRuleContext, ctx.
     * This allows us to determine which scope we are in, 
     * when in a particular parse tree node.  This is the basis for symbol lookup, 
     * since we must know which symbols are accessible from a particular scope and 
     * which scope is associated with a particular parse tree node.
     */
    private void saveScope(ParserRuleContext ctx, Scope s) { scopes.put(ctx, s); }

    /**
     * Creates the Main class scope and saves it in the global symbol-table
     */
    @Override public void enterMainClass(@NotNull MinijavaParser.MainClassContext ctx) { 
        //Get the Main class from the collection of Klasses
        Klass klass = klasses.get(ctx.Identifier(0).getText());
        currentScope = klass;

        //Associate this parse tree node with the Main class
        saveScope(ctx, currentScope);
    }

    /**
     * Creates a class level scope and saves it in the global symbol-table.
     */
    @Override public void enterClassDeclaration(@NotNull MinijavaParser.ClassDeclarationContext ctx) { 
        Klass klass = klasses.get(ctx.Identifier(0).getText());
        currentScope = klass;
        
        //Associate this parse tree node with klass.
        saveScope(ctx, currentScope);

        //Ensure that if this class extends another class,
        //the other klass exists.
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

        klass.setSuperKlass(superKlass);
        
        //Check for and report cyclic inheritance.
        //If cyclic inheritance occurs, stop compiling immediately.
        //This prevents infinite loops in cyclic inheritence checks.
        ErrorPrinter.reportCyclicInheritance(parser, ctx, klass);
    }

    /**
     * Exits the current scope.
     */
    @Override public void exitClassDeclaration(@NotNull MinijavaParser.ClassDeclarationContext ctx) {
        currentScope = currentScope.getEnclosingScope();
    }

    //Set the isField flag to true.  
    //This is used in symbol construction and will be applied
    //for future use in code generation
    @Override public void enterFieldDeclaration(@NotNull MinijavaParser.FieldDeclarationContext ctx) {isField=true;}
    @Override public void exitFieldDeclaration(@NotNull MinijavaParser.FieldDeclarationContext ctx) {isField=false;}
    

    /**
     * Define a new symbol in the current scope.
     * If the symbol is already defined, print an error message.
     */
    @Override public void enterVarDeclaration(@NotNull MinijavaParser.VarDeclarationContext ctx) {
        String typeName = ctx.type().getText();
        String varName = ctx.Identifier().getText();
        if(currentScope.lookupLocally(varName)!=null){
            ErrorPrinter.printSymbolAlreadyDefinedError(parser, ctx.Identifier().getSymbol(), "variable", varName, currentScope.getScopeName());
        }
        currentScope.define(new Symbol(varName, klasses.get(typeName), isField));
    }

    /**
     * Define a new method in the current class.
     * If the return type is undefined, print an error
     * If a method with the same name is already defined 
     * in this class, print an error message. 
     */
    @Override public void enterMethodDeclaration(@NotNull MinijavaParser.MethodDeclarationContext ctx) {
        //The return type of the method that is being defined.
        Klass returnType = klasses.get(ctx.type().getText());

        //If the return type of this method is not a klass in our symbol table.          
        if(returnType==null){
            ErrorPrinter.printFullError(parser, ctx.type().Identifier().getSymbol(),
                "error: cannot find symbol.",
                "symbol:   class " + ctx.type().getText(),
                "location: class " + currentScope.getScopeName()
            );
        }

        //Get this method's name.  This is a method call in case
        //we want to allow overloading in the future.
        String methodName = Method.getMethodSignature(ctx);

        //If a method with this name is already defined in this class,
        //print an error message to std err.
        if(currentScope.lookupLocally(methodName)!=null){
            ErrorPrinter.printSymbolAlreadyDefinedError(parser, ctx.Identifier().getSymbol(), "method", methodName, currentScope.getScopeName());
        }

        //The klass that contains this method
        Scope owner = currentScope;

        //Construct a symbol-table representation of the method in this
        //parse tree node.
        Method method = new Method(returnType, methodName, owner);
        
        //Define the method in this class.
        currentScope.define(method);
        currentScope = method;

        //Associate this parse tree node with method.
        saveScope(ctx, currentScope);
    }
    
    @Override public void exitMethodDeclaration(@NotNull MinijavaParser.MethodDeclarationContext ctx) {
        currentScope = currentScope.getEnclosingScope();
    }
    /**
     * Define a parameter in the current scope.
     * If the type is undefined, print an error message.
     */
    @Override public void enterParameter(@NotNull MinijavaParser.ParameterContext ctx) {
        //The symbol to be defined.
        Symbol parameter;

        //The type of the parameter that is being defined.
        Klass parameterType = klasses.get(ctx.type().getText());

        //If the parameter type is not a klass in our symbol table
        if(parameterType==null){
            //Print undefined type error
            ErrorPrinter.printFullError(parser, ctx.type().Identifier().getSymbol(),
                "error: cannot find symbol.",
                "symbol:   class " + ctx.type().getText(),
                "location: class " + currentScope.getEnclosingScope().getScopeName()
            );
        }

        parameter = new Symbol(ctx.Identifier().getText(), parameterType, false);
        //parameter is not a field, hence the false ----------------------^
        
        //Define the parameter in the current scope.
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

    /**
     * Associates the given parse tree node with a new explicit scope.
     * This will be used in checking if a variable was initialized before use.
     * It could also be used to extend scoping rules to allow variables to be 
     * declared inside statements
     * eg. while(booleanExpression){
     *         int x;
     *         //more code...
     *     } 
     * @param ctx The parse tree node (context) to associate with the generated scope.
     */
    public void enterScope(ParserRuleContext ctx){
        Block explicitScope = new Block(currentScope);
        currentScope = explicitScope;
        saveScope(ctx, currentScope);
    }
    public void exitScope(){
        currentScope = currentScope.getEnclosingScope();
    }
}

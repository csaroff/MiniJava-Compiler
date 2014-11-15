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
    Klass currentKlass;
    Klass.Method currentMethod = null;
    public AssignmentListener(Map<String, Klass> klasses, MinijavaParser parser){
        this.klasses=klasses;
        this.parser=parser;
    }
    @Override public void enterClassDeclaration(@NotNull MinijavaParser.ClassDeclarationContext ctx) { 
        currentKlass = klasses.get(ctx.Identifier(0).getText());
        if(ctx.Identifier().size()>1){
            Klass superKlass = klasses.get(ctx.Identifier(1).getText());
            if(superKlass==null){
                    System.err.println("error: cannot find symbol.");
                    ErrorPrinter.underlineError(parser, ctx.Identifier(1).getSymbol());
                    System.err.println("symbol:   class " + ctx.Identifier(1).getText());
                );
            }
        }

    }
    @Override public void exitClassDeclaration(@NotNull MinijavaParser.ClassDeclarationContext ctx) {
        currentKlass=null;
    }
    @Override public void enterMainClass(@NotNull MinijavaParser.MainClassContext ctx) {
    }
    @Override public void enterVarDeclaration(@NotNull MinijavaParser.VarDeclarationContext ctx) {
        if(currentMethod==null){
            currentKlass.fields.put(ctx.Identifier().getText(), new Klass.Variable(klasses.get(ctx.type().getText()), ctx.Identifier().getText()));
        }else{
            //Do method stuff.
        }
    }
    @Override public void enterMethodDeclaration(@NotNull MinijavaParser.MethodDeclarationContext ctx) {
        currentMethod = new Klass.Method(Main.getMethodSignature(ctx));
        currentMethod = currentKlass.methods.get(Main.getMethodSignature(ctx));
        //System.out.println("Current Klass: " + currentKlass + " and currentMethod = " + currentMethod);
        Klass returnType = klasses.get(ctx.type().getText());
        if(returnType==null){
            //The return type of this method is undefined.  
            //error(ctx.Identifier().getSymbol(), "The return type of method: " + currentMethod.name + " is not defined.");
            //Token t = ctx.Identifier().getSymbol();
            //ErrorPrinter.underlineError(parser, t, t.getLine(), t.getCharPositionInLine());
            ErrorPrinter.printFullError(parser, ctx.type().Identifier().getSymbol(),
                "error: cannot find symbol.",
                "symbol:   class " + ctx.type().getText(),
                "location: class " + currentKlass.name
            );
        }else{
            currentMethod.returnType=returnType;
        }
    }
    @Override public void exitMethodDeclaration(@NotNull MinijavaParser.MethodDeclarationContext ctx) {
        currentMethod = null;
    }
    @Override public void enterParameter(@NotNull MinijavaParser.ParameterContext ctx) {
        Klass type = klasses.get(ctx.type().getText());
        Klass.Variable param;
        if(type==null){
            //report error.  Undefined type.
            //error(ctx.Identifier().getSymbol(), "Error, undefined type.");
            ErrorPrinter.printFullError(parser, ctx.type().Identifier().getSymbol(),
                "error: cannot find symbol.",
                "symbol:   class " + ctx.type().getText(),
                "location: class " + currentKlass.name
            );
        }else{
            param = new Klass.Variable(type, ctx.Identifier().getText());
            currentMethod.parameters.add(param);
        }

    }
    //public static void error(Token t, String msg) {
    //    System.err.printf("line %d:%d %s\n", t.getLine(), t.getCharPositionInLine(), msg);
    //}
}

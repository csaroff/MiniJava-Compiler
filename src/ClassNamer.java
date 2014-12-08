import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import java.util.*;
public class ClassNamer extends MinijavaBaseListener {
    Map<String, Klass> klasses;
    MinijavaParser parser;
    //Klass currentKlass;
    //Klass.Method currentMethod = null;
    public ClassNamer(Map<String, Klass> klasses, MinijavaParser parser){
        this.klasses = klasses;
        this.parser  = parser;
    }
    @Override public void enterClassDeclaration(@NotNull MinijavaParser.ClassDeclarationContext ctx) { 
        Klass currentKlass = new Klass(ctx.Identifier(0).getText(), true);
        if(klasses.put(currentKlass.getScopeName(), currentKlass)!=null){
            ErrorPrinter.printDuplicateClassError(parser, ctx.Identifier(0).getSymbol(), currentKlass.getScopeName());
        }
    }
    @Override public void exitClassDeclaration(@NotNull MinijavaParser.ClassDeclarationContext ctx) {
    }
    @Override public void enterMainClass(@NotNull MinijavaParser.MainClassContext ctx) {
        Klass currentKlass;
        currentKlass = new Klass("int[]", true);
        klasses.put(currentKlass.getScopeName(),currentKlass);
        currentKlass = new Klass("int", false);
        klasses.put(currentKlass.getScopeName(),currentKlass);
        //System.out.println("Klasses.get(\"int\" = " + klasses.get("int"));
        
        currentKlass = new Klass("boolean", false);
        klasses.put(currentKlass.getScopeName(),currentKlass);
        
        currentKlass = new Klass(ctx.Identifier(0).getText(), true);
        klasses.put(currentKlass.getScopeName(), currentKlass);
    }
    @Override public void enterVarDeclaration(@NotNull MinijavaParser.VarDeclarationContext ctx) {
        //if(currentMethod==null){
        //    currentKlass.fields.put(ctx.Identifier().getText(), null);
        //}
    }
    @Override public void enterMethodDeclaration(@NotNull MinijavaParser.MethodDeclarationContext ctx) {
        //System.out.println("The method signature for class " + currentKlass + " is " + Main.getMethodSignature(ctx));
        //currentMethod = new Klass.Method(Main.getMethodSignature(ctx));
    }
    @Override public void exitMethodDeclaration(@NotNull MinijavaParser.MethodDeclarationContext ctx) {
        //currentKlass.methods.put(currentMethod.name, currentMethod);
        //currentMethod = null;
    }
    //@Override public void enterParameterList(@NotNull MinijavaParser.ParameterListContext ctx) {currentMethod.name+="(";}
    //@Override public void exitParameterList(@NotNull MinijavaParser.ParameterListContext ctx) {currentMethod.name+=")";}
    @Override public void enterParameter(@NotNull MinijavaParser.ParameterContext ctx) {
    }
}

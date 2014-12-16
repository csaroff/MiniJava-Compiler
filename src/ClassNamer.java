import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import java.util.*;

/**
 * A listener mechanism for creating and naming the klasses in the symbol 
 * table.  When the ParseTreeWalker visits a node in the parse tree, 
 * it calls the enterRule method associated with that node.  
 * It then does the same thing to all children of that node.
 * When the ParseTreeWalker exits a node, it calls the exitRule
 * method associated with that node.  
 *
 * This class is listening for the ParseTreeWalker to visit each node.
 * When the ParseTreeWalker enters a class declaration, the 
 * enterClassDeclaration method is called.  All children are visited 
 * in the same manner and then the exitClassDeclaration method is called.
 *
 * This results in an in-order traversal of the parse tree.
 */
public class ClassNamer extends MinijavaBaseListener {
    //The symbol-table collection of classes
    private Map<String, Klass> klasses;

    //The parser that generated the parse tree that this listener
    //is attached to.  Useful for reporting the line number 
    //and column number of the offending token.
    private MinijavaParser parser;

    /**
     * [ClassNamer description]
     * @param  klasses The symbol-table collection of classes
     * @param  parser  The parser that generated the parse tree 
     *                 that this listener is attached to.
     */
    public ClassNamer(Map<String, Klass> klasses, MinijavaParser parser){
        this.klasses = klasses;
        this.parser  = parser;
    }
    /**
     * Creates a klass in the symbol-table with the name derived from the
     * context.  Prints a duplicateClassError if the symbol-table already 
     * contains a klass with that name.
     */
    @Override public void enterClassDeclaration(@NotNull MinijavaParser.ClassDeclarationContext ctx) { 
        Klass currentKlass = new Klass(ctx.Identifier(0).getText());
        if(klasses.put(currentKlass.getScopeName(), currentKlass)!=null){
            ErrorPrinter.printDuplicateClassError(parser, ctx.Identifier(0).getSymbol(), currentKlass.getScopeName());
        }
    }
    /**
     * Adds the main class along with the primitive types to the symbol-table
     */
    @Override public void enterMainClass(@NotNull MinijavaParser.MainClassContext ctx) {
        Klass currentKlass;
        currentKlass = new Klass("int[]");
        klasses.put(currentKlass.getScopeName(),currentKlass);
        currentKlass = new Klass("int");
        klasses.put(currentKlass.getScopeName(),currentKlass);
        //System.out.println("Klasses.get(\"int\" = " + klasses.get("int"));
        
        currentKlass = new Klass("boolean");
        klasses.put(currentKlass.getScopeName(),currentKlass);
        
        currentKlass = new Klass(ctx.Identifier(0).getText());
        klasses.put(currentKlass.getScopeName(), currentKlass);
    }
}

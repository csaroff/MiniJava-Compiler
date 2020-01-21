/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
***/
import org.antlr.v4.runtime.*;
import java.util.*;

/**
 * An error listener for printing information about syntax errors
 * This ErrorListener should be added to a parser.  
 * Whenever a syntax error occurs, the syntaxError() method is called.
 * The follows follows set for the previous token is printed and
 * the offending token is underlined. 
 */
public class UnderlineListener extends BaseErrorListener {


    /**
     * @{inheritDoc}
     */
	@Override public void syntaxError(Recognizer<?,?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e){
        ErrorPrinter.printFileNameAndLineNumber((Token)offendingSymbol);
        System.err.println("line " + line + ":" + charPositionInLine + " " + msg);
        ErrorPrinter.underlineError(recognizer,(Token)offendingSymbol
                        //,line, charPositionInLine
                       );
    }
    ///**
    // * Prints the offending token and underlines in the traditional javac style.
    // * @param recognizer         Which parser got the error.  Gives access
    // *                           to the context and the token input stream.
    // * @param offendingToken     The token that was not in the follows set 
    // *                           and created the parse error.
    // * @param line               The line number of the input where the error occured
    // * @param charPositionInLine the character position in that line where the error occured
    // */
    //protected void underlineError(Recognizer recognizer, Token offendingToken, int line, int charPositionInLine) {
    //    CommonTokenStream tokens = (CommonTokenStream)recognizer.getInputStream();
    //    String input = tokens.getTokenSource().getInputStream().toString();
    //    String[] lines = input.split("\n");
    //    String errorLine = lines[line - 1];
    //    System.err.println(errorLine);
    //    for (int i=0; i<charPositionInLine; i++) System.err.print(" ");
    //    int start = offendingToken.getStartIndex();
    //    int stop = offendingToken.getStopIndex();
    //    if ( start>=0 && stop>=0 ) {
    //        for (int i=start; i<=stop; i++) System.err.print("^");
    //    }
    //    System.err.println();
    //}
}

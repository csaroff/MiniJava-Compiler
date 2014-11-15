import org.antlr.v4.runtime.misc.Nullable;
import org.antlr.v4.runtime.*;
import java.util.*;

public final class ErrorPrinter{
	private ErrorPrinter(){}
	public static void printFullError(Recognizer recognizer, Token offendingToken, String message, String symbol, String location){
		System.err.println(message);
		underlineError(recognizer, offendingToken);
		printErrorMessage(symbol);
		printErrorMessage(location);
	}
	public static void printErrorMessage(String message){
		System.err.println("  " + message);
	}
    public static void underlineError(Recognizer recognizer, Token offendingToken) {
    	int line = offendingToken.getLine();
    	int charPositionInLine = offendingToken.getCharPositionInLine();
        CommonTokenStream tokens = (CommonTokenStream)recognizer.getInputStream();
        String input = tokens.getTokenSource().getInputStream().toString();
        String[] lines = input.split("\n");
        String errorLine = lines[line - 1];
        System.err.println(errorLine);
        for (int i=0; i<charPositionInLine; i++) System.err.print(" ");
        int start = offendingToken.getStartIndex();
        int stop = offendingToken.getStopIndex();
        if ( start>=0 && stop>=0 ) {
            for (int i=start; i<=stop; i++) System.err.print("^");
        }
        System.err.println();
    }

}
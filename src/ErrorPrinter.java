import org.antlr.v4.runtime.misc.Nullable;
import org.antlr.v4.runtime.*;
import java.util.*;

public final class ErrorPrinter{
    private static boolean hasError = false;
    private static int errorCount=0;
	//private ErrorPrinter(){
    //}
    public static boolean noErrors(){
        return !hasError;
    }
    public static void reportError(){
        hasError=true;
        errorCount++;
    }
    public static int getErrorCount(){return errorCount;}
    public static void resetErrorCount(){errorCount=0;}
    public static void printFileNameAndLineNumber(Token offendingToken){
        reportError();
        System.err.print(Main.getFileName()+":"+offendingToken.getLine()+": ");

    }
    public static void printFullError(Recognizer recognizer, Token offendingToken, String message, String symbol, String location){
        printFileNameAndLineNumber(offendingToken);
        System.err.println(message);
        underlineError(recognizer, offendingToken);
        System.err.println("  " + symbol);
        System.err.println("  " + location);
    }
    public static void printVariableMayNotHaveBeenInitializedError(Recognizer recognizer, Token offendingToken, String symbolName){
        ErrorPrinter.printFileNameAndLineNumber(offendingToken);
        System.err.println("error: " + symbolName + " might not have been initialized");
        ErrorPrinter.underlineError(recognizer, offendingToken);
    }
    public static void printSymbolAlreadyDefinedError(Recognizer recognizer, Token offendingToken, String symbolType, String symbol, String className){
        ErrorPrinter.printFileNameAndLineNumber(offendingToken);
        System.err.println("error: " + symbolType + " " + symbol + " already defined in class " + className);
        ErrorPrinter.underlineError(recognizer, offendingToken);
    }
    public static void printRequiredFoundError(String message, Recognizer recognizer, Token offendingToken, String required, String found){
        ErrorPrinter.printFileNameAndLineNumber(offendingToken);
        System.err.println(message);
        ErrorPrinter.underlineError(recognizer, offendingToken);
        System.err.println("  required: " + required);
        System.err.println("  found:    " + found);
    }
    public static void binaryOperatorTypeError(Recognizer recognizer, ParserRuleContext ctx, Token operator, Klass foundLeft, Klass foundRight, Klass expectedLeft, Klass expectedRight){
        if(!(foundLeft==expectedLeft && foundRight==expectedRight)){
            ErrorPrinter.printFileNameAndLineNumber(operator);
            System.err.println("error: bad operand types for binary operator '" + operator.getText() + "'");
            ErrorPrinter.underlineError(recognizer, operator);
            System.err.println("  first type:   " + foundLeft);
            System.err.println("  second type:  " + foundRight);
        }
    }
    public static void printUnresolvedSymbolError(Recognizer recognizer, Token offendingToken, String symbolType, Klass location){
        printFileNameAndLineNumber(offendingToken);
        System.err.println("error: cannot find symbol");
        ErrorPrinter.underlineError(recognizer, offendingToken);
        System.err.println("  symbol:   " + symbolType + " " + offendingToken.getText());
        System.err.println("  location: " + "class" + " " + location);

    }
    public static void underlineError(Recognizer recognizer, Token offendingToken) {
        int line = offendingToken.getLine();
        int charPositionInLine = offendingToken.getCharPositionInLine();
        CommonTokenStream tokens = (CommonTokenStream)recognizer.getInputStream();
        String input = tokens.getTokenSource().getInputStream().toString();
        String[] lines = input.split("\n");
        String errorLine = lines[line - 1];
        System.err.println(errorLine);
        for (int i=0; i<charPositionInLine; i++){
            if(errorLine.charAt(i)=='\t'){
                System.err.print("\t");
            }else{
                System.err.print(" ");
            }
        }
        int start = offendingToken.getStartIndex();
        int stop = offendingToken.getStopIndex();
        if ( start>=0 && stop>=0 ) {
            for (int i=start; i<=stop; i++) System.err.print("^");
        }
        System.err.println();
    }
    //public static void printErrorMessage(String message){
    //  System.err.println("  " + message);
    //}
    //public static void printFileName(){
    //    System.err.print(Main.getFileName());
    //}
    //public static void reportCyclicInheritance(Klass klass, MinijavaParser.ClassDeclarationContext ctx){
    //    Klass original = klass;
    //    while(klass!=null){
    //        klass = klass.getSuperKlass();
    //        if(klass==original){
    //            
    //            System.err.println("error: cyclic inheritance.");
    //            ErrorPrinter.underlineError(parser, ctx.Identifier(1).getSymbol());
    //            //System.exit(1);
    //        }
    //    }
    //}
}

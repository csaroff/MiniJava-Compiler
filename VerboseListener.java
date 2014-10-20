import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.*;
import java.util.*;
public class VerboseListener extends BaseErrorListener {

    @Override

    public void syntaxError(Recognizer<?, ?> recognizer,

            Object offendingSymbol,

            int line, int charPositionInLine,

            String msg,

            RecognitionException e)

    {

        List<String> stack = ((Parser)recognizer).getRuleInvocationStack();

        Collections.reverse(stack);

        System.err.println("rule stack: "+stack);

        System.err.println("line "+line+":"+charPositionInLine+" at "+

                offendingSymbol+": "+msg);

    }

}

import org.antlr.v4.runtime.misc.Nullable;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.PredictionMode;

public class Main{
	public static void main(String[] args) throws Exception {
		ANTLRInputStream input = new ANTLRInputStream(System.in);
		MinijavaLexer lexer = new MinijavaLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		MinijavaParser parser = new MinijavaParser(tokens);
		parser.removeErrorListeners(); // remove ConsoleErrorListener
		parser.addErrorListener(new DiagnosticErrorListener());
		parser.getInterpreter()
		.setPredictionMode(PredictionMode.LL_EXACT_AMBIG_DETECTION);
		parser.addErrorListener(new UnderlineListener());
		//parser.addErrorListener(new VerboseListener());
		parser.goal();
	}
}

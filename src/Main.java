import org.antlr.v4.runtime.misc.Nullable;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import java.io.*;
import java.util.*;

public class Main{
	public static void main(String[] args) throws Exception {
		String inputFile = null;
		if ( args.length>0 ) inputFile = args[0];
		InputStream is = System.in;
		if ( inputFile!=null ) {
		    is = new FileInputStream(inputFile);
		}
		ANTLRInputStream input = new ANTLRInputStream(is);
		Map<String, Klass> klasses = new HashMap<String, Klass>();//Symbol Table
		MinijavaLexer lexer = new MinijavaLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		MinijavaParser parser = new MinijavaParser(tokens);
		parser.removeErrorListeners(); // remove ConsoleErrorListener
		parser.addErrorListener(new DiagnosticErrorListener());
		parser.getInterpreter()
		.setPredictionMode(PredictionMode.LL_EXACT_AMBIG_DETECTION);
		parser.addErrorListener(new UnderlineListener());
		//parser.addErrorListener(new VerboseListener());
		ParseTree tree = parser.goal();
        ClassNamer namer = new ClassNamer(klasses); 
        AssignmentListener assigner = new AssignmentListener(klasses, parser);
        
        ParseTreeWalker.DEFAULT.walk(namer, tree);
        ParseTreeWalker.DEFAULT.walk(assigner, tree);

	}
    public static String getMethodSignature(MinijavaParser.MethodDeclarationContext ctx){
        return ctx.Identifier().getText() + "()";
    }
    //public static String getMethodSignature(MinijavaParser.MethodDeclarationContext ctx){
    //    String methodName = ctx.Identifier().getText() + "(";
    //    if(ctx.parameterList()!=null){
    //        List<MinijavaParser.ParameterContext> paramCtxs = ctx.parameterList().parameter();
    //        for(MinijavaParser.ParameterContext paramCtx : paramCtxs){
    //            methodName+= paramCtx.type().getText() + ", ";
    //        }
    //        methodName = methodName.substring(0, methodName.length()-2);
    //    }
    //    //System.out.println("ctx.parameterList().getText() = " + ctx.parameterList().getText());
    //    methodName += ")";
	//	System.out.println("method name: " + methodName);
    //    return methodName;
    //}
}

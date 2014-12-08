import org.antlr.v4.runtime.misc.Nullable;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import java.io.*;
import java.util.*;

public class Main{
	private static String inputFile = null;
	public static void main(String[] args) throws Exception {
		if ( args.length>0 ) inputFile = args[0];
		InputStream is = System.in;
		if ( inputFile!=null ) {
		    is = new FileInputStream(inputFile);
		}
		ANTLRInputStream input = new ANTLRInputStream(is);
		Map<String, Klass> klasses = new HashMap<String, Klass>();//Symbol Table
	    ParseTreeProperty<Scope> scopes = new ParseTreeProperty<Scope>();
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

        //SymbolResolver resolver = new SymbolResolver(klasses, scopes, parser);

 
        if(!ErrorPrinter.noErrors()){
            System.err.println(ErrorPrinter.getErrorCount() + " errors.");
            System.exit(1);
        }
        ClassNamer namer = new ClassNamer(klasses, parser); 
        ParseTreeWalker.DEFAULT.walk(namer, tree);
        
        if(!ErrorPrinter.noErrors()){
            System.err.println(ErrorPrinter.getErrorCount() + " errors.");
            System.exit(1);
        }
        AssignmentListener assigner = new AssignmentListener(klasses, scopes, parser);
        ParseTreeWalker.DEFAULT.walk(assigner, tree); 
        
        if(!ErrorPrinter.noErrors()){
            System.err.println(ErrorPrinter.getErrorCount() + " errors.");
            System.exit(1);
        }
        TypeChecker typeChecker = new TypeChecker(klasses, scopes, parser);
        typeChecker.visit(tree);
        
        if(!ErrorPrinter.noErrors()){
            System.err.println(ErrorPrinter.getErrorCount() + " errors.");
            System.exit(1);
        }
        InitializationBeforeUseChecker iBeforeUChecker = new InitializationBeforeUseChecker(klasses, scopes, parser);
        iBeforeUChecker.visit(tree);
        
        if(!ErrorPrinter.noErrors()){
            System.err.println(ErrorPrinter.getErrorCount() + " errors.");
            System.exit(1);
        }
        CodeGenerator codeGenerator = new CodeGenerator(klasses, scopes, parser);
        ParseTreeWalker.DEFAULT.walk(codeGenerator, tree);
        
        if(!ErrorPrinter.noErrors()){
            System.err.println(ErrorPrinter.getErrorCount() + " errors.");
            System.exit(1);
        }
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
    public static String getFileName(){
    	return inputFile;
    }
}

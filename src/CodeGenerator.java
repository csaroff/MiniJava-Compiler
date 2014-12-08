import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import java.util.*;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.GeneratorAdapter;
import java.io.*;
//import org.objectweb.asm.commons.PrintStream;


public class CodeGenerator extends MinijavaBaseListener implements Opcodes{
    MinijavaParser parser;
    final Map<String, Klass> klasses;
    ParseTreeProperty<Scope> scopes;
    Scope currentScope = null;
	ClassWriter cw = null;
	org.objectweb.asm.commons.Method currentMethod;
	GeneratorAdapter methodGenerator;

	FileOutputStream fos;
    
    public CodeGenerator(final Map<String, Klass> klasses, final ParseTreeProperty<Scope> scopes, final MinijavaParser parser){
        this.scopes=scopes;
        this.klasses=klasses;
        this.parser=parser;
    }
	@Override public void enterMainClass(@NotNull MinijavaParser.MainClassContext ctx){
		Klass mainKlass = klasses.get(ctx.Identifier(0).getText());
		String mainKlassName = mainKlass.getScopeName();
		cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cw.visit(V1_1, ACC_PUBLIC, mainKlassName, null, "java/lang/Object", null);

		currentMethod = org.objectweb.asm.commons.Method.getMethod("void <init> ()");
        methodGenerator = new GeneratorAdapter(ACC_PUBLIC, currentMethod, null, null, cw);
        methodGenerator.loadThis();
        methodGenerator.invokeConstructor(Type.getType(Object.class), currentMethod);
        methodGenerator.returnValue();
        methodGenerator.endMethod();
	}
	@Override public void enterPrintStatement(@NotNull MinijavaParser.PrintStatementContext ctx){
        currentMethod = org.objectweb.asm.commons.Method.getMethod("void main (String[])");
        methodGenerator = new GeneratorAdapter(ACC_PUBLIC + ACC_STATIC, currentMethod, null, null, cw);
        methodGenerator.getStatic(Type.getType(System.class), "out", Type.getType(PrintStream.class));
	}
	@Override public void enterIntLitExpression(@NotNull MinijavaParser.IntLitExpressionContext ctx){
		methodGenerator.push(Integer.parseInt(ctx.IntegerLiteral().getText()));
	}
	@Override public void exitPrintStatement(@NotNull MinijavaParser.PrintStatementContext ctx){
        methodGenerator.invokeVirtual(Type.getType(PrintStream.class), org.objectweb.asm.commons.Method.getMethod("void println (int)"));
        methodGenerator.returnValue();
        methodGenerator.endMethod();
	}
	@Override public void exitMainClass(@NotNull MinijavaParser.MainClassContext ctx){
		Klass mainKlass = klasses.get(ctx.Identifier(0).getText());
		String mainKlassName = mainKlass.getScopeName();
		
		try{
			fos = new FileOutputStream(new File(mainKlassName + ".class"));
			fos.write(cw.toByteArray());
	        fos.close();
		}catch(FileNotFoundException fnfe){
			fnfe.printStackTrace();
			System.exit(1);
		}catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(1);
		}
        
	}

}
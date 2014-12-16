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


public class CodeGenerator extends MinijavaBaseListener implements Opcodes{
    public final org.objectweb.asm.commons.Method INIT(){
        return org.objectweb.asm.commons.Method.getMethod("void <init> ()");
    }
    MinijavaParser parser;
    final Map<String, Klass> klasses;
    ParseTreeProperty<Scope> scopes;
    ParseTreeProperty<Klass> callerTypes;
    Scope currentScope = null;
	ClassWriter cw = null;
	org.objectweb.asm.commons.Method currentMethod;
	GeneratorAdapter methodGenerator;
	FileOutputStream fos;
	Stack<Label> labelStack = new Stack<Label>();
    //boolean isArg;
    int argCount;
    public CodeGenerator(final Map<String, Klass> klasses, final ParseTreeProperty<Scope> scopes, final ParseTreeProperty<Klass> callerTypes, final MinijavaParser parser){
        this.klasses=klasses;
        this.scopes=scopes;
        this.callerTypes=callerTypes;
        this.parser=parser;
    }
	
    /*---------------------------------Generates Code for Main Class--------------------------------*/
	@Override public void enterMainClass(@NotNull MinijavaParser.MainClassContext ctx){
		Klass mainKlass = klasses.get(ctx.Identifier(0).getText());
		String mainKlassName = mainKlass.getScopeName();
		cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cw.visit(V1_1, ACC_PUBLIC, mainKlassName, null, "java/lang/Object", null);

        methodGenerator = new GeneratorAdapter(ACC_PUBLIC, INIT(), null, null, cw);
        methodGenerator.loadThis();
        methodGenerator.invokeConstructor(Type.getType(Object.class), INIT());
        methodGenerator.returnValue();
        methodGenerator.endMethod();

        methodGenerator = new GeneratorAdapter(ACC_PUBLIC + ACC_STATIC, org.objectweb.asm.commons.Method.getMethod("void main (String[])"), null, null, cw);
	}
	@Override public void exitMainClass(@NotNull MinijavaParser.MainClassContext ctx){
        methodGenerator.returnValue();
        methodGenerator.endMethod();
		cw.visitEnd();
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
    /*---------------------------------Generates Code for Main Class--------------------------------*/

    /*----------------------------------Generates Code for Classes----------------------------------*/
	@Override public void enterClassDeclaration(@NotNull MinijavaParser.ClassDeclarationContext ctx){
        enterScope(ctx);

		Klass klass = klasses.get(ctx.Identifier(0).getText());
		String klassName = klass.getScopeName();
		String superKlassName = klass.getSuperKlass()!=null ?
			klass.getSuperKlass().getScopeName() :
			"java/lang/Object";
		//System.out.println("super = " + superKlassName);

		cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cw.visit(V1_1, ACC_PUBLIC, klassName, null, superKlassName, null);

        methodGenerator = new GeneratorAdapter(ACC_PUBLIC, INIT(), null, null, cw);
        methodGenerator.loadThis();
        methodGenerator.invokeConstructor(Type.getObjectType(superKlassName), INIT());
        methodGenerator.returnValue();
        methodGenerator.endMethod();

        //currentMethod = org.objectweb.asm.commons.Method.getMethod("void main (String[])");
        //methodGenerator = new GeneratorAdapter(ACC_PUBLIC + ACC_STATIC, currentMethod, null, null, cw);
	}

	@Override public void exitClassDeclaration(@NotNull MinijavaParser.ClassDeclarationContext ctx){
		cw.visitEnd();
		Klass klass = klasses.get(ctx.Identifier(0).getText());
		String klassName = klass.getScopeName();
		
		try{
			fos = new FileOutputStream(new File(klassName + ".class"));
			fos.write(cw.toByteArray());
	        fos.close();
		}catch(FileNotFoundException fnfe){
			fnfe.printStackTrace();
			System.exit(1);
		}catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(1);
		}
		exitScope();
	}
	//@Override public void enterLocalDeclaration(@NotNull MinijavaParser.LocalDeclarationContext ctx) { }
	@Override public void exitLocalDeclaration(@NotNull MinijavaParser.LocalDeclarationContext ctx) {
        Symbol var = currentScope.lookup(ctx.varDeclaration().Identifier().getText());
        Type type = var.getType().asAsmType();
    	if(!var.hasLocalIdentifier()){
        	var.setLocalIdentifier(methodGenerator.newLocal(type));
    	}else{
    		System.out.println("Error: variable was declared twice.");
    	}
	}
	@Override public void exitVarDeclaration(@NotNull MinijavaParser.VarDeclarationContext ctx){
        Symbol var = currentScope.lookup(ctx.Identifier().getText());
        if(var.isField()){
			cw.visitField(
				ACC_PROTECTED,
				var.getName(),
				var.getType().asAsmType().getDescriptor(),
				null,
				null)
			.visitEnd();
        }
	}
    /*----------------------------------Generates Code for Classes----------------------------------*/

	@Override public void enterMethodDeclaration(@NotNull MinijavaParser.MethodDeclarationContext ctx){
		enterScope(ctx);
		Method methodRepresentation = (Method)currentScope;
        currentMethod = methodRepresentation.asAsmMethod();
        methodGenerator = new GeneratorAdapter(ACC_PUBLIC, currentMethod, null, null, cw);
	}
	@Override public void exitMethodDeclaration(@NotNull MinijavaParser.MethodDeclarationContext ctx){
		methodGenerator.returnValue();
        methodGenerator.endMethod();
		exitScope();
	}
	@Override public void enterParameterList(@NotNull MinijavaParser.ParameterListContext ctx){
		argCount=0;
	}
	//@Override public void exitParameterList(@NotNull MinijavaParser.ParameterListContext ctx){}
	@Override public void enterParameter(@NotNull MinijavaParser.ParameterContext ctx){
		currentScope.lookup(ctx.Identifier().getText()).setParameterIdentifier(argCount);
		argCount++;
	}
	//@Override public void exitParameter(@NotNull MinijavaParser.ParameterContext ctx){}


	/*------------------------------Generates Code for Printing Integers----------------------------*/
	@Override public void enterPrintStatement(@NotNull MinijavaParser.PrintStatementContext ctx){
        methodGenerator.getStatic(Type.getType(System.class), "out", Type.getType(PrintStream.class));
	}
	@Override public void exitPrintStatement(@NotNull MinijavaParser.PrintStatementContext ctx){
        methodGenerator.invokeVirtual(Type.getType(PrintStream.class), org.objectweb.asm.commons.Method.getMethod("void println (int)"));
	}
	/*------------------------------Generates Code for Printing Integers----------------------------*/

	/** --------------Generates code for doing field and local variable assignment.-----------------*/
    @Override public void enterVariableAssignmentStatement(@NotNull MinijavaParser.VariableAssignmentStatementContext ctx){
        Symbol var = currentScope.lookup(ctx.Identifier().getText());
        if(var.isField()){
            methodGenerator.loadThis();
        }
    }
    @Override public void exitVariableAssignmentStatement(@NotNull MinijavaParser.VariableAssignmentStatementContext ctx){
        Symbol var = currentScope.lookup(ctx.Identifier().getText());
        Type type = var.getType().asAsmType();
        if(var.isField()){
        	//System.out.println("Variable " + var.getName() + " is a field");
            Type owner = ((Klass)currentScope.getEnclosingScope()).asAsmType();
            //methodGenerator.loadThis();
        	methodGenerator.putField(owner, var.getName(), type);
        }else if(var.isParameter()){
        	methodGenerator.storeArg(var.getParameterListIdentifier());
        }else{
        	methodGenerator.storeLocal(var.getLocalIdentifier(), type);
        }
    }
	@Override public void enterArrayAssignmentStatement(@NotNull MinijavaParser.ArrayAssignmentStatementContext ctx){
        Symbol var = currentScope.lookup(ctx.Identifier().getText());
        Type type = var.getType().asAsmType();
        if(var.isField()){
            Type owner = ((Klass)currentScope.getEnclosingScope()).asAsmType();
            methodGenerator.loadThis();
        	methodGenerator.getField(owner, var.getName(), type);
        }else if(var.isParameter()){
        	methodGenerator.loadArg(var.getParameterListIdentifier());
        }else{
        	methodGenerator.loadLocal(var.getLocalIdentifier(), type);
        }
	}
	@Override public void exitArrayAssignmentStatement(@NotNull MinijavaParser.ArrayAssignmentStatementContext ctx){
		methodGenerator.arrayStore(Type.INT_TYPE);
	}

	/** --------------Generates code for doing field and local variable assignment.-----------------*/

	@Override public void enterIfElseStatement(@NotNull MinijavaParser.IfElseStatementContext ctx){
		Label enterElse = methodGenerator.newLabel();
		Label exitElse  = methodGenerator.newLabel();
		//methodGenerator.ifZCmp(GeneratorAdapter.EQ, enterElse);
		labelStack.push(exitElse);
		labelStack.push(enterElse);
		labelStack.push(exitElse);
		labelStack.push(enterElse);
	}
	@Override public void enterIfBlock(@NotNull MinijavaParser.IfBlockContext ctx) {
		methodGenerator.ifZCmp(GeneratorAdapter.EQ, labelStack.pop());
	}
	@Override public void exitIfBlock(@NotNull MinijavaParser.IfBlockContext ctx){
		methodGenerator.goTo(labelStack.pop());
	}
	@Override public void enterElseBlock(@NotNull MinijavaParser.ElseBlockContext ctx) {
		methodGenerator.mark(labelStack.pop());
	}
	@Override public void exitElseBlock(@NotNull MinijavaParser.ElseBlockContext ctx) {
		methodGenerator.mark(labelStack.pop());
	}

	@Override public void enterWhileStatement(@NotNull MinijavaParser.WhileStatementContext ctx) {
		Label enterWhile = methodGenerator.mark();
		Label exitWhile = methodGenerator.newLabel();
		labelStack.push(exitWhile);
		labelStack.push(enterWhile);
		labelStack.push(exitWhile);
	}
	@Override public void enterWhileBlock(@NotNull MinijavaParser.WhileBlockContext ctx){
		methodGenerator.ifZCmp(GeneratorAdapter.EQ, labelStack.pop());
	}
	@Override public void exitWhileStatement(@NotNull MinijavaParser.WhileStatementContext ctx) {
		methodGenerator.goTo(labelStack.pop());
		methodGenerator.mark(labelStack.pop());
	}


	@Override public void exitAndExpression(@NotNull MinijavaParser.AndExpressionContext ctx) {
		methodGenerator.math(GeneratorAdapter.AND, Type.BOOLEAN_TYPE);
	}
	@Override public void exitLtExpression(@NotNull MinijavaParser.LtExpressionContext ctx) {
		//Just do the roundabout long and hard way.
		Label trueLabel = methodGenerator.newLabel();
		Label endLabel = methodGenerator.newLabel();
		methodGenerator.ifCmp(Type.INT_TYPE, GeneratorAdapter.LT, trueLabel);
		methodGenerator.push(false);
		methodGenerator.goTo(endLabel);
		methodGenerator.mark(trueLabel);
		methodGenerator.push(true);
		methodGenerator.mark(endLabel);
	}
	@Override public void exitAddExpression(@NotNull MinijavaParser.AddExpressionContext ctx) {
		methodGenerator.math(GeneratorAdapter.ADD, Type.INT_TYPE);
	}
	@Override public void exitSubExpression(@NotNull MinijavaParser.SubExpressionContext ctx) {
		methodGenerator.math(GeneratorAdapter.SUB, Type.INT_TYPE);
	}
	@Override public void exitMulExpression(@NotNull MinijavaParser.MulExpressionContext ctx) {
		methodGenerator.math(GeneratorAdapter.MUL, Type.INT_TYPE);
	}
	//@Override public void enterPowExpression(@NotNull MinijavaParser.PowExpressionContext ctx){}
	@Override public void exitPowExpression(@NotNull MinijavaParser.PowExpressionContext ctx){
		Symbol base = new Symbol("base", klasses.get("int"), false);
        Type type = Type.INT_TYPE;
    	base.setLocalIdentifier(methodGenerator.newLocal(type));
		Symbol pow = new Symbol("pow", klasses.get("int"), false);
    	pow.setLocalIdentifier(methodGenerator.newLocal(type));
		//Symbol result = new Symbol("result", klasses.get("int"), false)	;
    	//result.setLocalIdentifier(methodGenerator.newLocal(type));
    	//methodGenerator.storeLocal(result.getLocalIdentifier(), type);
    	methodGenerator.storeLocal(pow.getLocalIdentifier(), type);
    	methodGenerator.storeLocal(base.getLocalIdentifier(), type);
    	methodGenerator.push(1);
    	Label end = methodGenerator.newLabel();
    	Label loop = methodGenerator.mark();
    	methodGenerator.loadLocal(pow.getLocalIdentifier(), type); 
    	methodGenerator.ifZCmp(GeneratorAdapter.EQ, end);
    	methodGenerator.loadLocal(base.getLocalIdentifier(), type); 
    	methodGenerator.math(GeneratorAdapter.MUL, Type.INT_TYPE);
    	methodGenerator.loadLocal(pow.getLocalIdentifier(), type);
    	methodGenerator.push(1);
    	methodGenerator.math(GeneratorAdapter.SUB, Type.INT_TYPE);
    	methodGenerator.storeLocal(pow.getLocalIdentifier(), type);
    	methodGenerator.goTo(loop);
    	methodGenerator.mark(end);
	}
	//@Override public void enterArrayAccessExpression(@NotNull MinijavaParser.ArrayAccessExpressionContext ctx) { }
	@Override public void exitArrayAccessExpression(@NotNull MinijavaParser.ArrayAccessExpressionContext ctx){
		methodGenerator.arrayLoad(Type.INT_TYPE);
	}
	//@Override public void enterArrayLengthExpression(@NotNull MinijavaParser.ArrayLengthExpressionContext ctx) { }
	@Override public void exitArrayLengthExpression(@NotNull MinijavaParser.ArrayLengthExpressionContext ctx){
		methodGenerator.arrayLength();
	}

	@Override public void exitMethodCallExpression(@NotNull MinijavaParser.MethodCallExpressionContext ctx){
		Klass klass = callerTypes.get(ctx);
		methodGenerator.invokeVirtual(klass.asAsmType(), ((Method)klass.lookup(ctx.Identifier().getText() + "()")).asAsmMethod());
	}
	@Override public void enterIntLitExpression(@NotNull MinijavaParser.IntLitExpressionContext ctx){
		methodGenerator.push(Integer.parseInt(ctx.IntegerLiteral().getText()));
	}
	@Override public void enterBooleanLitExpression(@NotNull MinijavaParser.BooleanLitExpressionContext ctx) {
		boolean predicate = Boolean.parseBoolean(ctx.BooleanLiteral().getText());
		methodGenerator.push(predicate);
	}
	@Override public void exitIdentifierExpression(@NotNull MinijavaParser.IdentifierExpressionContext ctx){
		Symbol var = currentScope.lookup(ctx.Identifier().getText());
        Type type = var.getType().asAsmType();
        if(var.isParameter()){
        	methodGenerator.loadArg(var.getParameterListIdentifier());
        }else if(var.isField()){
        	//System.out.println("Variable " + var.getName() + " is a field");
            Type owner = ((Klass)currentScope.getEnclosingScope()).asAsmType();
            methodGenerator.loadThis();
        	methodGenerator.getField(owner, var.getName(), type);
        }else{
        	methodGenerator.loadLocal(var.getLocalIdentifier(), type); 
        }
	}
	@Override public void exitThisExpression(@NotNull MinijavaParser.ThisExpressionContext ctx){
		methodGenerator.loadThis();
	}
	//@Override public void enterArrayInstantiationExpression(@NotNull MinijavaParser.ArrayInstantiationExpressionContext ctx) { }
	@Override public void exitArrayInstantiationExpression(@NotNull MinijavaParser.ArrayInstantiationExpressionContext ctx){
		methodGenerator.newArray(Type.INT_TYPE);
	}
    @Override public void enterObjectInstantiationExpression(@NotNull MinijavaParser.ObjectInstantiationExpressionContext ctx) {
        Type type = Type.getObjectType(ctx.Identifier().getText());
        methodGenerator.newInstance(type);
        methodGenerator.dup();
        methodGenerator.invokeConstructor(type, INIT());
    }
	@Override public void exitNotExpression(@NotNull MinijavaParser.NotExpressionContext ctx){
		methodGenerator.not();
	}
    public void enterScope(ParserRuleContext ctx){
        currentScope = scopes.get(ctx);
    }
    private void exitScope(){
        currentScope = currentScope.getEnclosingScope();
    }
}

import java.util.*;
public class Method extends Symbol implements Scope{
	private LinkedHashMap<String, Symbol> parameters = new LinkedHashMap<String, Symbol>();
	private Scope owner;
	private Scope body;
	private Map<String, Symbol> locals = new HashMap<String, Symbol>();
	private Map<String, Symbol> initializedVariables = new HashMap<String, Symbol>();

	public Method(Klass returnType, String name, Scope owner){
		//methods are fields
		super(name, returnType, true);
		this.owner=owner;
	}
	
	@Override public String getScopeName(){
		return this.name;
	}
	
	@Override public Scope getEnclosingScope(){
		return owner;
	}
	
	@Override public void define(Symbol sym){
		locals.put(sym.getName(), sym);
	}

    @Override public void initialize(Symbol sym){
        initializedVariables.put(sym.getName(), sym);
    }

	@Override public Symbol lookup(String name){
		if(parameters.containsKey(name)){
			return parameters.get(name);
		}else if(locals.containsKey(name)){
			return locals.get(name);
		}else{
			return this.getEnclosingScope().lookup(name);
		}
	}

	@Override public Symbol lookupLocally(String name){
		if(parameters.containsKey(name)){
			return parameters.get(name);
		}else{
			return locals.get(name);
		}
	}

    @Override public boolean hasBeenInitialized(String name){
        if(initializedVariables.containsKey(name) || parameters.containsKey(name)){
            return true;
    	}else{
            //return false;
            return this.getEnclosingScope().hasBeenInitialized(name);
        }
    }

    @Override public Set<Symbol> getInitializedVariables(){
        return new HashSet<Symbol>(this.initializedVariables.values())
        //.addAll(this.parameters.values())
        ;
    }
    //@Override public void uninitializeSymbols(){
    //    for(Symbol var : initializedVariables.values()){
    //        var.uninitialize();
    //    }
    //    initializedVariables.clear();
    //}
    //@Override public void addIfElseInitialization(Set<Symbol> symbols){
    //    if(ifElseInitialized==null){
    //        ifElseInitialized=symbols;
    //    }else{
    //        ifElseInitialized.retainAll(symbols);
    //    }
    //}
    //
    //@Override public void clearIfElseInitialization(){
    //    ifElseInitialized=null;
    //}

	public void addParameter(Symbol parameter){
		parameters.put(parameter.getName(), parameter);
	}
	
	public List<Symbol> getParameterList(){
		return new ArrayList<Symbol>(parameters.values());
	}
	
	public List<Klass> getParameterListDefinition(){
		List<Symbol> parameterList = getParameterList();
		List<Klass> parameterListDefinition = new ArrayList<Klass>();
		for(Symbol var: parameterList){
			parameterListDefinition.add(var.getType());
		}
		return parameterListDefinition;
	}
	//public String getMethodSignature(){
	//}
	public String toString(){
		return name;
	}
	public String fullName(){
		String fullName = this.getType().toString() + " ";
		fullName += name;
		fullName = fullName.substring(0, fullName.length()-1);
		boolean hasParameter = false;
		for(Symbol parameter : parameters.values()){
			fullName += parameter.getType().getScopeName() + ", ";
			hasParameter=true;
		}
		if(hasParameter){
			fullName = fullName.substring(0, fullName.length()-2);
		}
		fullName += ")";
		//System.out.println(fullName);
		return fullName;
	}
	public org.objectweb.asm.commons.Method asAsmMethod(){
	/**	Returns an asm.commons.Method representation of this Method.*/
		return org.objectweb.asm.commons.Method.getMethod(this.fullName(), true);
	
	}
	/** -----------------------------------------------------------------
    |            				Static Methods.  				         |
     -------------------------------------------------------------------*/
	public static String getMethodSignature(MinijavaParser.MethodDeclarationContext ctx){
		//System.out.println("Method Name = " + ctx.Identifier().getText()+"()");
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
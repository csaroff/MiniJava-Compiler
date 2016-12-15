import java.util.*;

/**
 * A symbol-table representation of a minijava method.
 * Methods are uniquely both a symbol and a scope, 
 * since the can be referenced as symbols (a.f()),
 * and may contain symbols.
 */
public class Method extends Symbol implements Scope{
	private LinkedHashMap<String, Symbol> parameters = new LinkedHashMap<String, Symbol>();
	private Scope owner;
	private Scope body;
	private Map<String, Symbol> locals = new HashMap<String, Symbol>();
	private Map<String, Symbol> initializedVariables = new HashMap<String, Symbol>();

	/**
	 * Constructs a new symbol-table representation of a Method.
	 * @param returnType The return type of this method representation
	 * @param name the name of this method representation including "()",
	 * 			   but without the rest of the parameter list.
	 * @param owner The name of the class that has this method.
	 */
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
            return this.getEnclosingScope().hasBeenInitialized(name);
        }
    }

    @Override public Set<Symbol> getInitializedVariables(){
        return new HashSet<Symbol>(this.initializedVariables.values());
    }

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

	public String toString(){
		return name;
	}
	
	/**
	 * @return the full name of this method including return type and argument list.
	 */
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
		return fullName;
	}

	/**
	 * @return an asm Method representation of this method.
	 */
	public org.objectweb.asm.commons.Method asAsmMethod(){
		return org.objectweb.asm.commons.Method.getMethod(this.fullName(), true);
	
	}
	/** -----------------------------------------------------------------
    |            				Static Methods.  				         |
     -------------------------------------------------------------------*/
    /**
     * @param   ctx The parse tree node (context) to get the method signature from.
     * @return  the method signature of the method associated with the given
     *          parse tree node (context)
     */
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
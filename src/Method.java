import java.util.*;
public class Method extends Symbol implements Scope{
	private LinkedHashMap<String, Symbol> parameters = new LinkedHashMap<String, Symbol>();
	private Scope owner;
	private Scope body;
	private Map<String, Symbol> locals = new HashMap<String, Symbol>();
	private Map<String, Symbol> initializedVariables = new HashMap<String, Symbol>();
    //private Set<Symbols> ifElseInitialized= null;

	public Method(Klass returnType, String name, Scope owner){
		super(name, returnType);
		this.owner=owner;
	}
	
	@Override public String getScopeName(){
		return owner.getScopeName() + "." + this.name;
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

	@Override public Symbol resolve(String name){
		if(parameters.containsKey(name)){
			return parameters.get(name);
		}else if(locals.containsKey(name)){
			return locals.get(name);
		}else{
			return this.getEnclosingScope().resolve(name);
		}
	}

	@Override public Symbol resolveLocally(String name){
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
	
	public String toString(){
		return name;
	}

	/** -----------------------------------------------------------------
    |            				Static Methods.  				         |
     -------------------------------------------------------------------*/
	public static String getMethodSignature(MinijavaParser.MethodDeclarationContext ctx){
		//System.out.println("Method Name = " + ctx.Identifier().getText()+"()");
		return ctx.Identifier().getText() + "()";
	}
}
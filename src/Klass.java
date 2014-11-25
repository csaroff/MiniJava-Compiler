import java.util.*;
public class Klass implements Scope{
	Klass superKlass;
	boolean isReference;
	String name;
	//Map<String, Symbol> fields = new HashMap<String, Symbol>();
	//Map<String, Method> methods = new HashMap<String, Method>();
	Map<String, Symbol> symTable = new HashMap<String, Symbol>();
	//Klass componentType;
    public Klass(String name, boolean isReference){
       this.name = name; 
       this.isReference = isReference;
    }
    public Klass getSuperKlass(){
    	return this.superKlass;
    }
    public int getNumScopes(){
    	return -1;//I have no idea how many methods are in each klass.
    }
    public String getScopeName(){
    	return name;
    }
    /** This is the highest level of scope, so return null.  */
    public Scope getEnclosingScope(){return null;}

    /** Define a symbol in the current scope */
    public void define(Symbol sym){
    	symTable.put(sym.getName(), sym);
		//throw new Exception("method define in class Klass was called, but the implementation was intentionally left empty.")
    }
    //public void defineField(String name, Klass type){
    //	fields.put(name, type);
    //}
    /** Look up name in this scope or in enclosing scope if not here */
    public Symbol resolve(String name){
    	//assert !(fields.contains(name) && methods.contains(name));
    	Symbol symbol = null;
    	for(Klass klass = this; symbol==null&&klass!=null; klass=klass.getSuperKlass()){
    		symbol = klass.symTable.get(name);
			//if(!klass.symTable.containsKey(name)){
			//	for(String sym : klass.symTable.keySet()){
			//		System.out.println("Symbol: " + sym);
			//	}
			//}else{
			//	System.out.println("Symbol table contains " + name);
			//}
    	}
    	return symbol;
    	//if(fields.contains(name)){
    	//	return fields.get(name);
    	//}else{
    	//	return methods.contains(name);
    	//}
    }
    public Symbol resolveLocally(String name){
    	return symTable.get(name);
    }
    public String toString(){
    	return name;
    }

	public static class Method extends Symbol implements Scope{
		//private String name;
		private LinkedHashMap<String, Symbol> parameters = new LinkedHashMap<String, Symbol>();
		//private Klass returnType;
		private Scope owner;
		private Scope body;
		private Map<String, Symbol> locals = new HashMap<String, Symbol>();
		public Method(Klass returnType, String name, Scope owner){
			super(name, returnType);
			this.owner=owner;
		}
		public void addParameter(Symbol parameter){
			parameters.put(parameter.getName(), parameter);
		}
		public String getScopeName(){
			return owner.getScopeName() + "." + this.name;
		}
		public Scope getEnclosingScope(){
			return owner;
		}
	    @Override public void define(Symbol sym){
	    	locals.put(sym.getName(), sym);
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
		//public void define(Symbol sym){//One should only ever define blocks.  
		//	System.err.println("method \"define\" in class Method was called, but the implementation was intentionally left empty.");
		//	System.exit(1);
		//	//throw new Exception("method \"define\" in class Method was called, but the implementation was intentionally left empty.");
		//}

		//public void define(Scope block)throws Exception{//One should only ever define blocks.  
		//	if(this.body!=null){
		//		throw new Exception("Cannot call method define in class Method more than once.  Each Method object should have only one body.");
		//	}else{
		//		this.body=block;
		//	}
		//}
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
		public String toString(){
			return name;
		}
	}
	public static class Block implements Scope{
		private Map<String, Symbol> locals = new HashMap<String, Symbol>();
		private List<Block> statements;
		private Scope enclosingScope;
		private String scopeName = "local";
		public Block(Scope enclosingScope){
			this.enclosingScope = enclosingScope;
			//if(numScopes>0){
			//	this.scopeName = enclosingScope.getScopeName() + ".$" + numScopes;
			//}else{
			//	this.scopeName = enclosingScope.getScopeName();
			//}
		}
		public int getNumScopes(){
			return statements.size();
		}
	    public String getScopeName(){
	    	return scopeName;
	    }
	    /** Where to look next for symbols;  */
	    public Scope getEnclosingScope(){
	    	return enclosingScope;
	    }

	    /** Define a symbol in the current scope */
	    @Override public void define(Symbol sym){
	    	locals.put(sym.getName(), sym);
	    }
	    //public void define(Scope scope){
	    //	statements.add(scope);
	    //}

	    /** Look up name in this scope or in enclosing scope if not here */
	    public Symbol resolve(String name){
	    	if(locals.containsKey(name)){
	    		return locals.get(name);
	    	}else{
	    		return this.getEnclosingScope().resolve(name);
	    	}
	    }
		@Override public Symbol resolveLocally(String name){
			return locals.get(name);
		}
	}
	public static String getMethodSignature(MinijavaParser.MethodDeclarationContext ctx){
		//System.out.println("Method Name = " + ctx.Identifier().getText()+"()");
        return ctx.Identifier().getText() + "()";
    }
}

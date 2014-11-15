import java.util.*;
public class Klass implements Scope{
	Klass superKlass;
	boolean isReference;
	String name;
	Map<String, Symbol> fields = new HashMap<String, Symbol>();
	Map<String, Method> methods = new HashMap<String, Method>();
	Klass componentType;
    public Klass(String name, boolean isReference){
       this.name = name; 
       this.isReference = isReference;
    }
    public String getScopeName(){
    	return name;
    }
    /** This is the highest level of scope, so return null.  */
    public Scope getEnclosingScope(){return null;}

    /** Define a symbol in the current scope */
    public void define(Symbol sym){
		throw new Exception("method define in class Klass was called, but the implementation was intentionally left empty.")
    }
    public void defineField(String name, Klass type){
    	fields.put(name, type);
    }
    /** Look up name in this scope or in enclosing scope if not here */
    public Symbol resolve(String name){
    	//assert !(fields.contains(name) && methods.contains(name));
    	if(fields.contains(name)){
    		return fields.get(name);
    	}else{
    		return methods.contains(name);
    	}
    }
    public String toString(){
    	return name;
    }

	public static class Method implements Scope{
		String name;
		LinkedHashMap<String, Symbol> parameters = new LinkedHashMap<String, Symbol>();
		Klass returnType;
		Klass owner;
		Block body;
		public Method(String name, Klass owner, Klass returnType){
			this.name=name;
		}
		public String getScopeName(){
			return owner.name + "." + this.name;
		}
		public Scope getEnclosingScope(){
			return owner;
		}
		public void define(Symbol sym){//One should only ever define blocks.  
			throw new Exception("method \"define\" in class Method was called, but the implementation was intentionally left empty.")
		}
		public void define(Block block){//One should only ever define blocks.  
			if(this.body!=null){
				throw new Exception("Cannot call method define in class Method more than once.  Each Method object should have only one body.")
			}else{
				this.body=block;
			}
		}
		public void resolve(String name){
			if(parameters.contains(name)){
				return parameters.get(name);
			}else{
				this.getEnclosingScope().resolve(name);
			}
		}
		public String toString(){
			return name;
		}
	}
	private class Block implements Scope{
		private Map<String, Symbols> locals;
		private List<Scope> statements;
		private Scope enclosingScope;
		private String scopeName;
		public Block(Scope enclosingScope, int numScopes){
			locals = new HashMap<Symbols>();
			this.enclosingScope = enclosingScope;
			if(numScope>0){
				this.scopeName = enclosingScope.getScopeName() + ".$" + numScopes;
			}else{
				this.scopeName = enclosingScope.getScopeName();
			}
		}
	    public String getScopeName(){
	    	return scopeName();
	    }
	    /** Where to look next for symbols;  */
	    public Scope getEnclosingScope(){
	    	return enclosingScope();
	    }

	    /** Define a symbol in the current scope */
	    public void define(Symbol sym){
	    	statement.add(sym);
	    }

	    /** Look up name in this scope or in enclosing scope if not here */
	    public Symbol resolve(String name){
	    	if(locals.contains(name)){
	    		return locals.get(name);
	    	}else{
	    		return this.getEnclosingScope().resolve(name);
	    	}
	    }
	}
}

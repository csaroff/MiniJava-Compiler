import java.util.*;
public class Block implements Scope{
	private Map<String, Symbol> locals = new HashMap<String, Symbol>();
	private Map<String, Symbol> initializedVariables = new HashMap<String, Symbol>();
    //private Set<Symbols> ifElseInitialized= null;
	private Scope enclosingScope;
	private String scopeName = "local";

	public Block(Scope enclosingScope){
		this.enclosingScope = enclosingScope;
	}

    @Override public String getScopeName(){
    	return scopeName;
    }

    /** Where to look next for symbols;  */
    @Override public Scope getEnclosingScope(){
    	return enclosingScope;
    }

    /** Define a symbol in the current scope */
    @Override public void define(Symbol sym){
    	locals.put(sym.getName(), sym);
    }

    @Override public void initialize(Symbol sym){
        initializedVariables.put(sym.getName(), sym);
    }

    /** Look up name in this scope or in enclosing scope if not here */
    @Override public Symbol resolve(String name){
    	if(locals.containsKey(name)){
    		return locals.get(name);
    	}else{
    		return this.getEnclosingScope().resolve(name);
    	}
    }

	@Override public Symbol resolveLocally(String name){
		return locals.get(name);
	}
    
    @Override public boolean hasBeenInitialized(String name){
        if(initializedVariables.containsKey(name)){
            return true;
        }else{
            return this.getEnclosingScope().hasBeenInitialized(name);
        }
    }

    @Override public Set<Symbol> getInitializedVariables(){
        return new HashSet<Symbol>(this.initializedVariables.values());
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
}
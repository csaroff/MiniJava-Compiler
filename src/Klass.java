import java.util.*;
public class Klass implements Scope{
	public Klass superKlass;
	private boolean isReference;
	private String name;
	private Map<String, Symbol> symTable = new HashMap<String, Symbol>();
    //private Map<String, Symbol> initializedVariables = new HashMap<String, Symbol>();

    public Klass(String name, boolean isReference){
       this.name = name; 
       this.isReference = isReference;
    }

    public Klass getSuperKlass(){
    	return this.superKlass;
    }

    @Override public String getScopeName(){
    	return name;
    }
    
    /** This is the highest level of scope, so return null.  */
    @Override public Scope getEnclosingScope(){return null;}

    /** Define a symbol in the current scope */
    @Override public void define(Symbol sym){
    	symTable.put(sym.getName(), sym);
    }
    
    @Override public void initialize(Symbol sym){
        assert false;
    }

    public boolean isInstanceOf(Klass other){
        if(this.superKlass==null&&other!=this){
            return false;
        }else if(other==this){
            return true;
        }else{
            return this.superKlass.isInstanceOf(other);
        }
    }

    /** Look up name in this scope or in enclosing scope if not here */
    @Override public Symbol resolve(String name){
    	//assert !(fields.contains(name) && methods.contains(name));
    	Symbol symbol = null;
    	for(Klass klass = this; symbol==null&&klass!=null; klass=klass.getSuperKlass()){
    		symbol = klass.symTable.get(name);
    	}
    	return symbol;
    }

    @Override public Symbol resolveLocally(String name){
    	return symTable.get(name);
    }
    
    @Override public boolean hasBeenInitialized(String name){
        return this.resolve(name)!=null;
    }
    
    @Override public Set<Symbol> getInitializedVariables(){
        assert false;
        return null;
    }
    //@Override public void uninitializeSymbols(){
    //    assert false;
    //}
    //@Override public void addIfElseInitialization(Set<Symbol> symbols){
    //   assert false; 
    //}
    //
    //@Override public void clearIfElseInitialization(){
    //    assert false;
    //}

    public String toString(){
    	return name;
    }
}
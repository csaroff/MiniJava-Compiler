import java.util.*;
import org.objectweb.asm.*;

/**
 * A symbol-table representation of a type. 
 */
public class Klass implements Scope{
    //The type that this class directly extends, 
    //or null if it extends Object or isn't a reference type.
	private Klass superKlass;

    //The name of this Klass.
	private String name;

    //The symbol table containing all fields and methods.  
    //Methods in this symbol table always have a () on the
    //end of their name to distinguish them from variables.
    //eg.
    //int f;
    //is different and distinguishable from
    //int f(){
    //  return 0;
    //}
	private Map<String, Symbol> symTable = new HashMap<String, Symbol>();

    /**
     * Constructs a new Klass with the given name
     * @param  name The name of the class that you are representing
     */
    public Klass(String name){
       this.name = name; 
    }

    /**
     * sets the superclass of this klass
     * @param superKlass the super of this klass
     */
    public void setSuperKlass(Klass superKlass){
        this.superKlass = superKlass;
    }
    
    /**
     * @return the symbol-table representation of this klass's superclass
     */
    public Klass getSuperKlass(){
    	return this.superKlass;
    }

    @Override public String getScopeName(){
    	return name;
    }
    
    /** This is the highest scope level, so this method returns null.  */
    @Override public Scope getEnclosingScope(){return null;}

    /**
     * Define a symbol in the current scope
     * @param sym The symbol that you want to define.
     */
    @Override public void define(Symbol sym){
    	symTable.put(sym.getName(), sym);
    }
    
    /**
     * Since all fields of a class are initialized to 0, 
     * false or null by default, this method should never be called.
     */
    @Override public void initialize(Symbol sym){
        assert false;
    }

    /**
     * @param other the klass that may be this klass's ancestor
     * @return true if this klass is a descendant(direct or indirect) of other,  false otherwise
     */
    public boolean isDescendantOf(Klass other){
        if(this.superKlass==null&&other!=this){
            return false;
        }else if(other==this){
            return true;
        }else{
            return this.superKlass.isDescendantOf(other);
        }
    }

    /**
     * Searches this klass for a symbol with the given name.
     * If no such symbol exists, 
     * returns getSuperKlass()==null? null : getSuperKlass().lookup(name)
     * @param name The name of the symbol to search for
     * @return the symbol with symbolName equal to name.
     */
    @Override public Symbol lookup(String name){
    	Symbol symbol = null;
    	for(Klass klass = this; symbol==null&&klass!=null; klass=klass.getSuperKlass()){
    		symbol = klass.symTable.get(name);
    	}
    	return symbol;
    }

    /**
     * Searches this klass for a symbol with the given name.
     * If no such symbol exists, return null.
     * @param name The name of the symbol to search for.
     * @return the symbol with the given name, or null if no such symbol exists.
     */
    @Override public Symbol lookupLocally(String name){
    	return symTable.get(name);
    }
    
    /**
     * Since all field variables are intialized upon construction of an object, 
     * this method returns true if this.lookup(name)!=null
     * and false otherwise.
     * @param name The name of the symbol to search for.
     * @return true if this klass contains the field with the given name.
     */
    @Override public boolean hasBeenInitialized(String name){
        return this.lookup(name)!=null;
    }
    
    /**
     * This method should never be called.  
     * @return null
     */
    @Override public Set<Symbol> getInitializedVariables(){
        assert false;
        return null;
    }

    /**
     * @return The name of this klass
     */
    public String toString(){
    	return name;
    }

    /**
     * @return an asm Type representation of this class.
     */
    public Type asAsmType(){
        if(this.name.equals("int")){
            return Type.INT_TYPE;
        }else if(this.name.equals("boolean")){
            return Type.BOOLEAN_TYPE;
        }else if(this.name.equals("int[]")){
            return Type.getType(int[].class);
        }else{
            return Type.getType("L" + this.name + ";");
        }
    }
}
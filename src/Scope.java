import java.util.*;

/** 
 * An interface for defining scopes and scoping rules for Minijava. 
 */
public interface Scope {
    /**
     * @return the name of this scope.
     */
    public String getScopeName();

    /** 
     * Where to look next for symbols;
     * @return the scope that contains this scope.
     */
    public Scope getEnclosingScope();

    /** 
     * Define a symbol in the current scope
     * @param sym A symbol to be defined in this scope.
     */
    public void define(Symbol sym);

    /**
     * Adds sym to a collection of initialied variables in this scope.
     * @param sym The symbol to be initialized
     */
    public void initialize(Symbol sym);

    /** 
     * Searches for the symbol in the scope.  If this scope does not contain
     * a symbol with the symbolName, name, 
     * return getEnclosingScope==null ? null : getEnclosingScope().lookup(name);
     * @param  name the name of the symbol that you would like to lookup
     * @return      the symbol that is being referenced, or null if it is
     *              not in the symbol-table
     */
    public Symbol lookup(String name);
    
    /**
     * Searches for the symbol in the current scope.  
     * If this scope does not contain a symbol with the symbolName name, 
     * return null;
     * @param  name the name of the symbol that you would like to lookup
     * @return      the symbol that is being referenced, 
     *              or null if it is not in the current scope.
     */
    public Symbol lookupLocally(String name);
    
    /**
     * @param  name the name of the symbol that may
     *              or may not have been initialized
     * @return      true if the symbol with the given name has been 
     *              initialized and false otherwise.
     */
    public boolean hasBeenInitialized(String name);
    
    /**
     * @return a set of all of the initialized variables in the this scope.
     */
    public Set<Symbol> getInitializedVariables();

    /** -----------------------------------------------------------------
    |            Static methods don't need to be overwritten.            |
     -------------------------------------------------------------------*/

     /**
      * @param  scope A Scope for which you want to know 
      *               the Klass that contains it.
      * @return       the Klass that contains scope.
      *               If a Method m is overwritten in a subclass, 
      *               getEnclosingKlass(m) returns the subclass
      */
	public static Klass getEnclosingKlass(Scope scope){
		while(!(scope instanceof Klass)){
			scope=scope.getEnclosingScope();
		}
		return (Klass)scope;//The outermost scope will always be a class.
	}

    /**
     * @param  scope A scope for which you want to know the Method that contains it.
     * @return       the Method that contains scope.
     */
    public static Method getEnclosingMethod(Scope scope){
        while(!(scope instanceof Method)){
            scope = scope.getEnclosingScope();
        }
        return (Method)scope;
    }
}

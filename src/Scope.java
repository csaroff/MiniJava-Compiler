import java.util.*;

/** An interface for defining scopes and scoping rules for Minijava. 
*/
public interface Scope {
    public String getScopeName();

    /** Where to look next for symbols;  */
    public Scope getEnclosingScope();

    /** Define a symbol in the current scope */
    public void define(Symbol sym);

    public void initialize(Symbol sym);

    /** Look up name in this scope or in enclosing scope if not here */
    public Symbol resolve(String name);
    
    public Symbol resolveLocally(String name);
    
    public boolean hasBeenInitialized(String name);
    
    public Set<Symbol> getInitializedVariables();

    //public void uninitializeSymbols();
    //public void addIfElseInitialization(Set<Symbol> symbols);
    //
    //public void clearIfElseInitialization();

    /** -----------------------------------------------------------------
    |            Static methods don't need to be overwritten.            |
     -------------------------------------------------------------------*/
	public static Klass getEnclosingKlass(Scope scope){
		while(!(scope instanceof Klass)){
			scope=scope.getEnclosingScope();
		}
		return (Klass)scope;//The outermost scope will always be a class.
	}
    public static Method getEnclosingMethod(Scope scope){
        while(!(scope instanceof Method)){
            scope = scope.getEnclosingScope();
        }
        return (Method)scope;
    }
}

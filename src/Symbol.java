public class Symbol { // A generic programming language symbol
    String name;      // All symbols at least have a name
    Klass type;
    private boolean hasBeenInitialized = false;
    //private boolean hasIfInitialization = false;
    //private boolean hasElseInitialization = false;
    public void initialize(){
        hasBeenInitialized=true;
    }
    //public void uninitialize(){
    //    hasBeenInitialized=false;
    //}
    public boolean hasBeenInitialized(){
        return hasBeenInitialized;
    }
    //public void initializeIf(){
    //    hasIfInitialization=true;
    //}
    //public boolean hasIfInitialization(){
    //    return hasBeenInitialized || hasIfInitialization;
    //}
    //public void initializeElse(){
    //    if(hasIfInitialization){ initialize(); }
    //    hasElseInitialization=true;
    //}
    //public boolean hasElseInitialization(){
    //    return hasBeenInitialized || hasElseInitialization; 
    //}

    public Symbol(String name) { this.name = name; }
    public Symbol(String name, Klass type) {this(name); this.type = type;}
    public Klass getType(){return type;}
    public String getName() { return name; }
    public String toString() {
        if ( type!=null ) return '<'+getName()+":"+type+'>';
        return getName();
    }
}

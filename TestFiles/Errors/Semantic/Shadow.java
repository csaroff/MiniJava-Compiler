class Shadow {
    public static void main(String[] args) {
        System.out.println(1);
    }
}

class A {
    int x;

    public int f()      { return 0; }
    public int g(int x) { return 1; }
}

class B extends A {
    int x;

    public boolean f()      { return true; }
    public int g(boolean x) { return 0; }
}

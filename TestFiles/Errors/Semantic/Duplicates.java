class Duplicates {
    public static void main(String[] args) {
        System.out.println(1);
    }
}

class D {
    int field;
    int field;

    public int f() { return 1; }
    public int f() { return 2; }
    public int g() {
        int x;
        int x;
        boolean x;

        return 0;
    }
    public int h(int x, int x, int y, int x) {
        int y;

        return y;
    }
}

class D {
    public boolean f() { return  true; }
    public boolean f() { return false; }
}

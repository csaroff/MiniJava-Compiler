class MethodArguments {
    public static void main(String[] args) {
        System.out.println(new MA().f(2));
    }
}

class MA {
    public int f() {
        return this.g(true);
    }
    public int g(int x) {
        return 1;
    }
}

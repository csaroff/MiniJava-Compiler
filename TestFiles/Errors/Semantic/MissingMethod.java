class MissingMethod {
    public static void main(String[] args) {
        System.out.println(new C().f());
    }
}

class A {}
class B extends A {}
class C extends B {}

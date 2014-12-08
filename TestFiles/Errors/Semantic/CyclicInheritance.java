class CyclicInheritance {
    public static void main(String[] args) {
        System.out.println(1);
    }
}

class A extends B {}
class B extends B {}
class C extends D {}
class D extends C {}
class E extends B {}

class MissingType {
    public static void main(String[] args) {
        System.out.println(new Foobar().foo());
    }
}

class Foo {
    Bar x;
}

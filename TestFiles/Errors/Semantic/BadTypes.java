class BadTypes {
    public static void main(String[] args) {
        System.out.println(new BT().run(true));
    }
}

class BT {
    public int run(int n) {
        if (!n) {
            n = n - true;
        } else {
            n[1] = n;
        }

        return false;
    }

    public int really_bad() {
        int x;
        int[] y;
        x = this.run(5) - true;

        y = new int[4];
        y[true] = false;

        return 3 + true && 1 * new BT() < 2 + !3 * (x - x);
    }
}

class BadReturn {
    public static void main(String[] args) {
        System.out.println(1);
    }
}

class BR {
    public int f() {
        return true;
    }

    public int g() {
        int x;

        x = 0;
    }

    public int h(int x) {
        if (0 < x) {
            return 0;
        } else {
            recur (x < 0) ? (x+1) : x;
        }
    }
}

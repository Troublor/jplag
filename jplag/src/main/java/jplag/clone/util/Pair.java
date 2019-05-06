package jplag.clone.util;

public class Pair<T1, T2> {
    private T1 val1;
    private T2 val2;

    public Pair(T1 c1, T2 c2) {
        this.val1 = c1;
        this.val2 = c2;
    }

    public T1 getVal1() {
        return val1;
    }

    public T2 getVal2() {
        return val2;
    }

    @Override
    public String toString() {
        return val1.toString() + val2.toString();
    }
}

package javapp.data;

import javapp.core.S;

public class VectorTransition<T extends Number> extends Transition<Vector<T>> {
    @SuppressWarnings("unchecked")
    public VectorTransition(Vector<T> t, double d) {
        super(t, d, (a, b, c) -> new Vector<T>((T) S.add(S.mult(a.x, S.subtract(1, c)), S.mult(b.x, c)),
                (T) S.add(S.mult(a.y, S.subtract(1, c)), S.mult(b.y, c))));
    }

    public VectorTransition(T x, T y, double d) {
        this(new Vector<T>(x, y), d);
    }
}

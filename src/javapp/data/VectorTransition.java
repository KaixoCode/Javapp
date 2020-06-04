package javapp.data;

public class VectorTransition extends Transition<Vector<Double>> {

    public VectorTransition(Vector<Double> t, double d) {
        super(t, d, (a, b, c) -> new Vector<Double>(a.x * (1.0 - c) + b.x * c, a.y * (1.0 - c) + b.y * c));
    }

    public VectorTransition(double x, double y, double d) {
        this(new Vector<Double>(x, y), d);
    }
}

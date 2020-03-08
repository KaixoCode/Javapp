package javapp.data;

import javapp.core.S;

public class Vector<I extends Number> {

    public I x;
    public I y;
    public I z;

    public Vector(I x, I y, I z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector(I x, I y) {
        this(x, y, y);
    }

    public Vector() {
    }

    public Vector<I> add(Vector<I> other) {
        this.x = S.add(this.x, other.x);
        this.y = S.add(this.y, other.y);
        this.z = S.add(this.z, other.z);
        return this;
    }

    public Vector<I> mult(Vector<I> other) {
        this.x = S.mult(this.x, other.x);
        this.y = S.mult(this.y, other.y);
        this.z = S.mult(this.z, other.z);
        return this;
    }

    public Vector<I> mult(I other) {
        this.x = S.mult(this.x, other);
        this.y = S.mult(this.y, other);
        this.z = S.mult(this.z, other);
        return this;
    }

    public static <I extends Number> Vector<I> add(Vector<I> v1, Vector<I> v2) {
        Vector<I> res = new Vector<I>();
        res.x = S.add(v1.x, v2.x);
        res.y = S.add(v1.y, v2.y);
        res.z = S.add(v1.z, v2.z);
        return res;
    }

    public static <I extends Number> Vector<I> mult(Vector<I> v1, Vector<I> v2) {
        Vector<I> res = new Vector<I>();
        res.x = S.mult(v1.x, v2.x);
        res.y = S.mult(v1.y, v2.y);
        res.z = S.mult(v1.z, v2.z);
        return res;
    }

    public static <I extends Number> Vector<I> mult(Vector<I> v1, I o) {
        Vector<I> res = new Vector<I>();
        res.x = S.mult(v1.x, o);
        res.y = S.mult(v1.y, o);
        res.z = S.mult(v1.z, o);
        return res;
    }
}

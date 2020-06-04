package javapp.data;

import javapp.core.Functions;

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
        this.x = Functions.add(this.x, other.x);
        this.y = Functions.add(this.y, other.y);
        this.z = Functions.add(this.z, other.z);
        return this;
    }

    public Vector<I> mult(Vector<I> other) {
        this.x = Functions.mult(this.x, other.x);
        this.y = Functions.mult(this.y, other.y);
        this.z = Functions.mult(this.z, other.z);
        return this;
    }

    public Vector<I> mult(I other) {
        this.x = Functions.mult(this.x, other);
        this.y = Functions.mult(this.y, other);
        this.z = Functions.mult(this.z, other);
        return this;
    }

    public static <I extends Number> Vector<I> add(Vector<I> v1, Vector<I> v2) {
        Vector<I> res = new Vector<I>();
        res.x = Functions.add(v1.x, v2.x);
        res.y = Functions.add(v1.y, v2.y);
        res.z = Functions.add(v1.z, v2.z);
        return res;
    }

    public static <I extends Number> Vector<I> mult(Vector<I> v1, Vector<I> v2) {
        Vector<I> res = new Vector<I>();
        res.x = Functions.mult(v1.x, v2.x);
        res.y = Functions.mult(v1.y, v2.y);
        res.z = Functions.mult(v1.z, v2.z);
        return res;
    }

    public static <I extends Number> Vector<I> mult(Vector<I> v1, I o) {
        Vector<I> res = new Vector<I>();
        res.x = Functions.mult(v1.x, o);
        res.y = Functions.mult(v1.y, o);
        res.z = Functions.mult(v1.z, o);
        return res;
    }

    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
    }
}

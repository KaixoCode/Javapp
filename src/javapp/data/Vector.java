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
}

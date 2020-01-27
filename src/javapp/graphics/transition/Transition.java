package javapp.graphics.transition;

public abstract class Transition<T> {

    protected double speed = 0.1;

    protected T value;
    protected T target;

    public Transition(T t, double d) {
        this.value = t;
        this.target = t;
        speed = d;
    }

    public abstract T getValue();

    public void morph(T t) {
        this.target = t;
    }

}

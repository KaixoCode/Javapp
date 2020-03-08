package javapp.data;

public class Transition<T> {

    private double speed = 0.1;

    private T value;
    private T target;
    private Lerp<T> lerp;

    public Transition(T t, double d, Lerp<T> lerp) {
        this.value = t;
        this.target = t;
        speed = d;
        this.lerp = lerp;
    }

    public T getValue() {
        return value = lerp.lerp(value, target, speed);
    }

    public void morph(T t) {
        this.target = t;
    }

    public String toString() {
        return value.toString();
    }

    public interface Lerp<T> {
        public T lerp(T a, T b, double amt);
    }

}

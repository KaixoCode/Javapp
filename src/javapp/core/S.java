package javapp.core;

public final class S {
    /**
     * Constrains a value between the two other values.
     * 
     * @param a initial value
     * @param b min value
     * @param c max value
     * @return a > b ? a ? c < a : c : b;
     */
    public static <T extends Comparable<T>> T constrain(T a, T b, T c) {
        return a.compareTo(b) > 0 ? a.compareTo(c) < 0 ? a : c : b;
    }

    /**
     * Maps a value from the first range to the second range
     * 
     * @param value
     * @param start1
     * @param stop1
     * @param start2
     * @param stop2
     * @return the value mapped from start1-stop1 to start2-stop2
     */
    public static double map(double value, double start1, double stop1, double start2, double stop2) {
        return start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1));
    }

    public static float map(float value, float start1, float stop1, float start2, float stop2) {
        return start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1));
    }

    /**
     * Maps a value from the first range to the second range
     * 
     * @param value
     * @param start1
     * @param stop1
     * @param start2
     * @param stop2
     * @return the value mapped from start1-stop1 to start2-stop2
     */
    public static double mapstrain(double value, double start1, double stop1, double start2, double stop2) {
        return constrain(map(value, start1, stop1, start2, stop2), start2, stop2);
    }

    public static float mapstrain(float value, float start1, float stop1, float start2, float stop2) {
        return constrain(map(value, start1, stop1, start2, stop2), start2, stop2);
    }
}

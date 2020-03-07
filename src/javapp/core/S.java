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
    public static int constrain(int a, int b, int c) {
        return a > b ? a < c ? a : c : b;
    }

    public static float constrain(float a, float b, float c) {
        return a > b ? a < c ? a : c : b;
    }

    public static double constrain(double a, double b, double c) {
        return a > b ? a < c ? a : c : b;
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

    /**
     * Adds 2 Numbers
     * 
     * @param a Number 1
     * @param b Number 2
     * @return the sum of these Number objects
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> T add(T a, T b) {
        if (a instanceof Double) {
            return (T) (Number) (a.doubleValue() + b.doubleValue());
        } else if (a instanceof Float) {
            return (T) (Number) (a.floatValue() + b.floatValue());
        } else if (a instanceof Integer) {
            return (T) (Number) (a.intValue() + b.intValue());
        } else if (a instanceof Byte) {
            return (T) (Number) (a.byteValue() + b.byteValue());
        } else if (a instanceof Long) {
            return (T) (Number) (a.longValue() + b.longValue());
        } else if (a instanceof Short) {
            return (T) (Number) (a.shortValue() + b.shortValue());
        }
        return null;
    }

    /**
     * Subtracts 2 Numbers
     * 
     * @param a Number 1
     * @param b Number 2
     * @return the difference of these Number objects
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> T subtract(T a, T b) {
        if (a instanceof Double) {
            return (T) (Number) (a.doubleValue() - b.doubleValue());
        } else if (a instanceof Float) {
            return (T) (Number) (a.floatValue() - b.floatValue());
        } else if (a instanceof Integer) {
            return (T) (Number) (a.intValue() - b.intValue());
        } else if (a instanceof Byte) {
            return (T) (Number) (a.byteValue() - b.byteValue());
        } else if (a instanceof Long) {
            return (T) (Number) (a.longValue() - b.longValue());
        } else if (a instanceof Short) {
            return (T) (Number) (a.shortValue() - b.shortValue());
        }
        return null;
    }

    /**
     * Multiplies 2 Numbers
     * 
     * @param a Number 1
     * @param b Number 2
     * @return the product of these Number objects
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> T mult(T a, T b) {
        if (a instanceof Double) {
            return (T) (Number) (a.doubleValue() * b.doubleValue());
        } else if (a instanceof Float) {
            return (T) (Number) (a.floatValue() * b.floatValue());
        } else if (a instanceof Integer) {
            return (T) (Number) (a.intValue() * b.intValue());
        } else if (a instanceof Byte) {
            return (T) (Number) (a.byteValue() * b.byteValue());
        } else if (a instanceof Long) {
            return (T) (Number) (a.longValue() * b.longValue());
        } else if (a instanceof Short) {
            return (T) (Number) (a.shortValue() * b.shortValue());
        }
        return null;
    }

    /**
     * Divides 2 Numbers
     * 
     * @param a Number 1
     * @param b Number 2
     * @return the quotient of these Number objects
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> T divide(T a, T b) {
        if (a instanceof Double) {
            return (T) (Number) (a.doubleValue() / b.doubleValue());
        } else if (a instanceof Float) {
            return (T) (Number) (a.floatValue() / b.floatValue());
        } else if (a instanceof Integer) {
            return (T) (Number) (a.intValue() / b.intValue());
        } else if (a instanceof Byte) {
            return (T) (Number) (a.byteValue() / b.byteValue());
        } else if (a instanceof Long) {
            return (T) (Number) (a.longValue() / b.longValue());
        } else if (a instanceof Short) {
            return (T) (Number) (a.shortValue() / b.shortValue());
        }
        return null;
    }

    /**
     * Lerps the value a towards b by f percent
     * 
     * @param a initial value
     * @param b target value
     * @param f percentage
     * @return the lerped value
     */
    public static double lerp(double a, double b, double f) {
        return (a * (1 - f) + b * f);
    }

    public static float lerp(float a, float b, float f) {
        return (a * (1 - f) + b * f);
    }
}

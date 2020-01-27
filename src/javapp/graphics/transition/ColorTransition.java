package javapp.graphics.transition;

import java.awt.Color;

public class ColorTransition extends Transition<Color> {

    public ColorTransition(Color t, double d) {
        super(t, d);
    }

    @Override
    public Color getValue() {
        int r1 = value.getRed();
        int g1 = value.getGreen();
        int b1 = value.getBlue();
        int a1 = value.getAlpha();

        int r2 = target.getRed();
        int g2 = target.getGreen();
        int b2 = target.getBlue();
        int a2 = target.getAlpha();

        int r = (int) (r1 * (1 - speed) + r2 * speed);
        int g = (int) (g1 * (1 - speed) + g2 * speed);
        int b = (int) (b1 * (1 - speed) + b2 * speed);
        int a = (int) (a1 * (1 - speed) + a2 * speed);

        value = new Color(r, g, b, a);
        return value;
    }

}

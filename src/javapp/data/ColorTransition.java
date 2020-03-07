package javapp.data;

import java.awt.Color;

import javapp.core.S;

public class ColorTransition extends Transition<Color> {

    public ColorTransition(Color t, double d) {
        super(t, d);
    }

    @Override
    public Color getValue() {
        float[] val = value.getComponents(null);
        float[] tar = target.getComponents(null);
        float r = S.lerp(val[0], tar[0], (float) speed);
        float g = S.lerp(val[1], tar[1], (float) speed);
        float b = S.lerp(val[2], tar[2], (float) speed);
        float a = S.lerp(val[3], tar[3], (float) speed);
        value = new Color(r, g, b, a);
        return value;
    }
}
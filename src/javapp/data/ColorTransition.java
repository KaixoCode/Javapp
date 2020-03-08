package javapp.data;

import java.awt.Color;

import javapp.core.S;

public class ColorTransition extends Transition<Color> {

    public ColorTransition(Color t, double d) {
        super(t, d, (value, target, speed) -> {
            float[] val = value.getComponents(null);
            float[] tar = target.getComponents(null);
            float r = (float) S.lerp(val[0], tar[0], speed);
            float g = (float) S.lerp(val[1], tar[1], speed);
            float b = (float) S.lerp(val[2], tar[2], speed);
            float a = (float) S.lerp(val[3], tar[3], speed);
            return new Color(r, g, b, a);
        });
    }
}
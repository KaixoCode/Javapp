package javapp.objects.visuals;

import java.awt.Graphics2D;

import javapp.objects.Pressable;

public abstract class PressableGraphics extends HoverableGraphics {

    // All states of a clickable visual
    public static final int PRESSING = 2;

    /**
     * Create visuals for a clickable object.
     * 
     * @param c the object
     */
    public PressableGraphics(Pressable c) {
        super(c);
    }

    /**
     * Draw the pressed state of the object.
     * 
     * @param g2d    the Graphics2D
     * @param width  the width of the object
     * @param height the height of the object
     */
    public abstract void drawPressed(Graphics2D g2d, int width, int height);

    @Override
    public void draw(Graphics2D g2d) {
        if (getState() == HOVERING) {
            drawHovering(g2d, getObject().getWidth(), getObject().getHeight());
        } else if (getState() == PRESSING) {
            drawPressed(g2d, getObject().getWidth(), getObject().getHeight());
        } else {
            drawIdle(g2d, getObject().getWidth(), getObject().getHeight());
        }
    }
}

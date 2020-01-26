package javapp.objects.visuals;

import java.awt.Graphics2D;

import javapp.objects.Drawable;
import javapp.objects.Hoverable;

public abstract class HoverableGraphics implements Drawable {

    // All states of a clickable visual
    public static final int IDLE = 0;
    public static final int HOVERING = 1;

    // The current state
    private int state = 0;

    // The object the visuals are for
    private Hoverable object;

    /**
     * Create visuals for a clickable object.
     * 
     * @param c the object
     */
    public HoverableGraphics(Hoverable c) {
        object = c;
    }

    /**
     * Sets the state of the visual.
     * 
     * @param state the state
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * Returns the state of the visual.
     * 
     * @return the state
     */
    public int getState() {
        return state;
    }

    /**
     * Returns the object for which these visuals are.
     * 
     * @return the object
     */
    public Hoverable getObject() {
        return object;
    }

    /**
     * Draw the hovering state of the object.
     * 
     * @param g2d    the Graphics2D
     * @param width  the width of the object
     * @param height the height of the object
     */
    public abstract void drawHovering(Graphics2D g2d, int width, int height);

    /**
     * Draw the idle state of the object.
     * 
     * @param g2d    the Graphics2D
     * @param width  the width of the object
     * @param height the height of the object
     */
    public abstract void drawIdle(Graphics2D g2d, int width, int height);

    @Override
    public void draw(Graphics2D g2d) {
        if (state == HOVERING) {
            drawHovering(g2d, getObject().getWidth(), getObject().getHeight());
        } else {
            drawIdle(g2d, getObject().getWidth(), getObject().getHeight());
        }
    }
}

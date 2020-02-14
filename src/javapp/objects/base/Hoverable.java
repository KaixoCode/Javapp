package javapp.objects.base;

import java.awt.event.MouseEvent;

public abstract class Hoverable implements Drawable {

    private boolean hovering = false;

    /**
     * Returns true if the given (x, y) are within the bounds of the Hoverable
     * Object.
     * 
     * @param x x-location
     * @param y y-location
     * @return true if (x, y) is within the bounds of the Object
     */
    public boolean withinBounds(int x, int y) {
        return x > getX() && x < getX() + getWidth() && y > getY() && y < getY() + getHeight();
    }

    /**
     * Returns the width of the object.
     * 
     * @return width
     */
    public abstract int getWidth();

    /**
     * Returns the height of the object.
     * 
     * @return height
     */
    public abstract int getHeight();

    /**
     * Returns the X position of this Hoverable.
     * 
     * @return x position
     */
    public abstract int getX();

    /**
     * Returns the Y position of this Hoverable.
     * 
     * @return y position
     */
    public abstract int getY();

    public abstract void setPosition(int x, int y);

    /**
     * This gets called by an EventHandler and lets the Hoverable object know if the
     * mouse entered this object.
     */
    final public void mouseEnter() {
        hovering = true;

        mouseEntered();
    }

    /**
     * This gets called after mouseEnter().
     */
    public abstract void mouseEntered();

    /**
     * This gets called by an EventHandler and lets the Hoverable object know if the
     * mouse exited this object.
     */
    final public void mouseExit() {
        hovering = false;

        mouseExited();
    }

    /**
     * This gets called after mouseExit().
     */
    public abstract void mouseExited();

    /**
     * This gets called by an EventHandler and lets the Hoverable object know the
     * mouse was moved.
     *
     * @param e mouse event
     */
    public abstract void mouseMoved(MouseEvent e);

    /**
     * Returns the cursor to display when hovering over this Hoverable.
     * 
     * @return the cursor
     */
    public abstract int getCursor();

    /**
     * Should return true if mouseEnter() was called and mouseExit has not yet been
     * called.
     * 
     * @return true when mouse hovering over Hoverable
     */
    final public boolean isHovering() {
        return hovering;
    }
}

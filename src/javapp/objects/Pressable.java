package javapp.objects;

import java.awt.event.MouseEvent;

public abstract class Pressable extends Hoverable {

    private boolean pressed = false;

    /**
     * This gets called by an EventHandler when the pressable object is pressed.
     * Takes into account depth.
     * 
     * @param e mouse event
     */
    public void mousePress(MouseEvent e) {
        pressed = true;

        mousePressed(e);
    }

    /**
     * This gets called after mousePress(MouseEvent e).
     * 
     * @param e mouse event
     */
    public abstract void mousePressed(MouseEvent e);

    /**
     * This gets called by an EventHandler when the pressable object is clicked on.
     * Takes into account depth.
     * 
     * @param e mouse event
     */
    public abstract void mouseClicked(MouseEvent e);

    /**
     * This gets called by an EventHandler whenever the mouse has been released.
     * Happens to every Pressable object anytime a mouseRelease event happens.
     * 
     * @param e mouse event
     */
    public void mouseRelease(MouseEvent e) {
        pressed = false;

        mouseReleased(e);
    }

    /**
     * This gets called after mouseRelease(MouseEvent e).
     * 
     * @param e mouse event
     */
    public abstract void mouseReleased(MouseEvent e);

    
    /**
     * This should return true if the object is pressed right now, and the mouse is
     * still down.
     * 
     * @return true if is pressed.
     */
    public boolean isPressed() {
        return pressed;
    }
}
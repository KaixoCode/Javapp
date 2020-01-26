package javapp.objects;

import java.awt.event.MouseEvent;

public interface Pressable extends Hoverable {

    /**
     * This gets called by an EventHandler when the pressable object is pressed.
     * Takes into account depth.
     * 
     * @param e mouse event
     */
    public void mousePress(MouseEvent e);

    /**
     * This gets called by an EventHandler when the pressable object is clicked on.
     * Takes into account depth.
     * 
     * @param e mouse event
     */
    public void mouseClick(MouseEvent e);

    /**
     * This gets called by an EventHandler whenever the mouse has been released.
     * Happens to every Pressable object anytime a mouseRelease event happens.
     * 
     * @param e mouse event
     */
    public void mouseRelease(MouseEvent e);

    /**
     * This gets called by an EventHandler when the Draggable object has been
     * pressed upon and the mouse is now dragging.
     * 
     * @param e event
     */
    public void drag(MouseEvent e);

    /**
     * This should return true if the object is pressed right now, and the mouse is
     * still down.
     * 
     * @return true if is pressed.
     */
    public boolean isPressed();
}
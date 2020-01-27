package javapp.objects;

import java.awt.event.MouseEvent;

public abstract class Focusable extends Pressable {

    private boolean focused = false;

    /**
     * Sets the focus of the object to false.
     */
    public void unfocus() {
        focused = false;
    }

    /**
     * Sets the focus of this object to true.
     */
    public void focus() {
        focused = true;
    }

    /**
     * Returns true when this object has focus.
     * 
     * @return true when object has focus
     */
    public boolean isFocused() {
        return focused;
    }

    /**
     * This gets called by an EventHandler when the Draggable object has been
     * pressed upon and the mouse is now dragging.
     * 
     * @param e event
     */
    public abstract void drag(MouseEvent e);

}

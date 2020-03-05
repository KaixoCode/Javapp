package javapp.objects.base;

import java.awt.event.MouseEvent;

public abstract class Focusable extends Pressable {

    private boolean focused = false;
    private Focusable tabObject = null;
    private Focusable backTabObject = null;

    /**
     * Sets the tab object of this focusable
     * 
     * @param o tabobject
     */
    public void setTabObject(Focusable o) {
        tabObject = o;
        tabObject.setBackTabObject(this);
    }

    /**
     * Sets the tab object of this focusable
     * 
     * @param o tabobject
     */
    public void setBackTabObject(Focusable o) {
        backTabObject = o;
    }

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
    final public boolean isFocused() {
        return focused;
    }

    /**
     * This gets called by an EventHandler when the Draggable object has been
     * pressed upon and the mouse is now dragging.
     * 
     * @param e event
     */
    public abstract void mouseDragged(MouseEvent e);

    /**
     * Tab to the tabobject
     */
    final public boolean tab() {
        if (tabObject != null && isFocused()) {
            unfocus();
            tabObject.focus();
            return true;
        }
        return false;
    }

    /**
     * Backtab to the backtabobject
     */
    final public boolean backTab() {
        if (backTabObject != null && isFocused()) {
            unfocus();
            backTabObject.focus();
            return true;
        }
        return false;
    }

    final public Focusable getBackTabObject() {
        return backTabObject;
    }

    final public Focusable getTabObject() {
        return tabObject;
    }
}

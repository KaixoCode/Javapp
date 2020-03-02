package javapp.event;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.EventObject;

import javapp.objects.base.Drawable;
import javapp.objects.base.Focusable;
import javapp.objects.base.Hoverable;
import javapp.objects.base.Pressable;
import javapp.objects.base.Typeable;

/**
 * Handles all incoming events.
 */
public class EventHandler {

    // The object
    private EventDistributer distributer;

    // The queue of events
    private ArrayList<EventObject> eventQueue = new ArrayList<EventObject>();

    private Focusable focused;
    private Hoverable hovering;

    /**
     * Returns the Drawable where the mouse is currently hovering over.
     * 
     * @return drawable
     */
    public Hoverable getHovering() {
        return hovering;
    }

    /**
     * Returns the Drawable that currently has focus.
     * 
     * @return drawable
     */
    public Focusable getFocused() {
        return focused;
    }

    /**
     * Create EventHandler a Canvas.
     * 
     * @param window the window where the events came from.
     */
    public EventHandler(EventDistributer canvas) {
        this.distributer = canvas;
    }

    /**
     * Adds an event object to the queue.
     * 
     * @param event EventObject
     */
    public void add(EventObject event) {
        eventQueue.add(event);
    }

    /**
     * Runs all the events that are now in the event queue
     */
    public void handleEvents() {

        // Make sure the focused object is in the focused variable
        ArrayList<Drawable> focusables = new ArrayList<Drawable>(distributer.getDrawables());

        // Remove all non focusables
        focusables.removeIf((c) -> !(c instanceof Focusable));

        // Find the focused object
        for (Drawable d : focusables) {
            if (((Focusable) d).isFocused()) {
                focused = (Focusable) d;
            }
        }

        // Do events
        while (eventQueue.size() > 0) {
            EventObject o = eventQueue.get(0);

            if (o instanceof MouseWheelEvent) {
                mouseWheelEvent((MouseWheelEvent) o);
            } else if (o instanceof MouseEvent) {
                mouseEvent((MouseEvent) o);
            } else if (o instanceof KeyEvent) {
                keyEvent((KeyEvent) o);
            }
            eventQueue.remove(o);
        }
    }

    // Mouse events
    private void mouseEvent(MouseEvent event) {

        // Calculate the relative position of all elements inside this eventhandler,
        // taking into account the transformation and the position of the event
        // distributer.
        int newx = event.getX() - distributer.getX();
        int newy = event.getY() - distributer.getY();

        // Translate the coords of the mouse event relative to the canvas
        event = new MouseEvent(event.getComponent(), event.getID(), event.getWhen(), event.getModifiersEx(), newx, newy,
                event.getXOnScreen(), event.getYOnScreen(), event.getClickCount(), event.isPopupTrigger(),
                event.getButton());
        // Add the events to an EventDistributer if the mouse is over it.
        if (hovering != null && hovering instanceof EventDistributer) {
            ((EventDistributer) hovering).addEvent(event);
        }
        /**
         * Distribute the mouse events.
         */
        switch (event.getID()) {
        case MouseEvent.MOUSE_CLICKED:
            mouseClicked(event);
            break;
        case MouseEvent.MOUSE_MOVED:
            mouseMoved(event);
            break;
        case MouseEvent.MOUSE_PRESSED:
            mousePressed(event);
            break;
        case MouseEvent.MOUSE_DRAGGED:
            mouseDragged(event);
            break;
        case MouseEvent.MOUSE_RELEASED:
            mouseReleased(event);
            break;
        }

    }

    private void mouseClicked(MouseEvent event) {

        // If the mouse is over an object, click on that object
        if (hovering != null && hovering instanceof Pressable) {
            ((Pressable) hovering).mouseClicked(event);
        }
    }

    private void mouseMoved(MouseEvent event) {

        // Get all the drawables from the canvas
        ArrayList<Drawable> hoverables = new ArrayList<Drawable>(distributer.getDrawables());

        // Remove all non hoverables
        hoverables.removeIf((c) -> !(c instanceof Hoverable));

        // Find the hoverable object that was drawn last and is under the mouse, aka
        // last in the list.
        Hoverable mouseOver = null;
        for (Drawable d : hoverables) {

            // If the mouseX and mouseY are within the bounds, the mouse is over the object
            if (((Hoverable) d).withinBounds(event.getX(), event.getY())) {
                mouseOver = (Hoverable) d;
            }
        }
        hovering = mouseOver;

        // If the mouse is over an object, set hover to true
        if (hovering != null && hovering instanceof Hoverable) {
            if (!((Hoverable) hovering).isHovering()) {
                ((Hoverable) hovering).mouseEnter();
            }
            hovering.mouseMoved(event);
        }

        // Set hover to false in all the other hoverables
        for (Drawable d : hoverables) {
            if (d != hovering) {
                if (((Hoverable) d).isHovering()) {
                    ((Hoverable) d).mouseExit();
                }
            }
        }

    }

    private void mousePressed(MouseEvent event) {

        // If the mouse is over an object, click on that object
        if (hovering != null && hovering instanceof Pressable) {
            ((Pressable) hovering).mousePress(event);
        }

        // Unfocus any focused object
        if (focused != null && focused != hovering) {
            ((Focusable) focused).unfocus();
            focused = null;
        }

        // Set the focus to hovering if it's focusable
        if (hovering instanceof Focusable && focused == null) {
            focused = ((Focusable) hovering);
            ((Focusable) focused).focus();
        }
    }

    private void mouseDragged(MouseEvent event) {

        // If the mouse is over an object, click on that object
        if (focused != null && focused instanceof Focusable) {
            ((Focusable) focused).drag(event);
        }
    }

    private void mouseReleased(MouseEvent event) {

        // Get all the drawables from the canvas
        ArrayList<Drawable> pressables = new ArrayList<Drawable>(distributer.getDrawables());

        // Remove all non pressables
        pressables.removeIf((c) -> !(c instanceof Pressable));

        // Release all the pressable objects
        for (Drawable d : pressables) {
            ((Pressable) d).mouseRelease(event);
        }
    }

    // True when a tab event occured, stops the keyTyped event from firing.
    private boolean tabbed = false;

    // Key events
    private void keyEvent(KeyEvent event) {

        // Stop the event when a tab event occured
        if (tabbed) {
            tabbed = false;
            return;
        }

        // Tab object event stuff
        if (event.getID() == KeyEvent.KEY_PRESSED && focused != null && event.getKeyCode() == KeyEvent.VK_TAB) {
            if (event.isShiftDown() && focused.backTab()) {
                focused = focused.getBackTabObject();
                tabbed = true;
                return;
            } else if (!event.isShiftDown() && focused.tab()) {
                focused = focused.getTabObject();
                tabbed = true;
                return;
            } else if (focused.getBackTabObject() != null || focused.getTabObject() != null) {
                tabbed = true;
                return;
            }
        }

        // Event stuff
        switch (event.getID()) {
        case KeyEvent.KEY_PRESSED:
            keyPressed(event);
            break;
        case KeyEvent.KEY_RELEASED:
            keyReleased(event);
            break;
        case KeyEvent.KEY_TYPED:
            keyTyped(event);
            break;
        }

        // Add the events to an EventDistributer if the mouse is over it.
        if (focused != null && focused instanceof EventDistributer) {
            ((EventDistributer) focused).addEvent(event);
        }
    }

    private void keyPressed(KeyEvent event) {
        if (focused != null && focused instanceof Typeable) {
            ((Typeable) focused).keyPress(event);
        }

    }

    private void keyReleased(KeyEvent event) {
        if (focused != null && focused instanceof Typeable) {
            ((Typeable) focused).keyRelease(event);
        }
    }

    private void keyTyped(KeyEvent event) {
        if (focused != null && focused instanceof Typeable) {
            ((Typeable) focused).keyType(event);
        }
    }

    // Mouse wheel events
    private void mouseWheelEvent(MouseWheelEvent event) {

        // If the mouse is over an object, click on that object
        if (hovering != null) {
            (hovering).mouseWheel(event);
        }
    }

}

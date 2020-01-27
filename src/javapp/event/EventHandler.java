package javapp.event;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.EventObject;

import javapp.objects.Drawable;
import javapp.objects.Focusable;
import javapp.objects.Hoverable;
import javapp.objects.Pressable;
import javapp.objects.Typeable;

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
    public Pressable getFocused() {
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
        while (eventQueue.size() > 0) {
            EventObject o = eventQueue.get(0);
            if (o instanceof MouseEvent) {
                mouseEvent((MouseEvent) o);
            } else if (o instanceof KeyEvent) {
                keyEvent((KeyEvent) o);
            } else if (o instanceof MouseWheelEvent) {
                mouseWheelEvent((MouseWheelEvent) o);
            }
            eventQueue.remove(o);
        }
    }

    // Mouse events
    private void mouseEvent(MouseEvent event) {

        // Translate the coords of the mouse event relative to the canvas
        event = new MouseEvent(event.getComponent(), event.getID(), event.getWhen(), event.getModifiersEx(),
                event.getX() - distributer.getX(), event.getY() - distributer.getY(), event.getXOnScreen(),
                event.getYOnScreen(), event.getClickCount(), event.isPopupTrigger(), event.getButton());

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

        // Add the events to an EventDistributer if the mouse is over it.
        if (hovering != null && hovering instanceof EventDistributer) {
            ((EventDistributer) hovering).addEvent(event);
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
            hovering.mouseMove(event);
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
        if (focused != null) {
            ((Focusable) focused).unfocus();
            focused = null;
        }

        // Set the focus to hovering if it's focusable
        if (hovering instanceof Focusable) {
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

    // Key events
    private void keyEvent(KeyEvent event) {
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

    }

}

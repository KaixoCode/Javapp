package javapp.objects.button;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javapp.core.Method;
import javapp.objects.Drawable;
import javapp.objects.Focusable;
import javapp.objects.visuals.HoverableGraphics;
import javapp.objects.visuals.PressableGraphics;

/**
 * Simple Button class.
 */
public class Button implements Drawable, Focusable {

    // Location and size of the button
    private int x;
    private int y;
    private int width;
    private int height;

    // True if the button is currently being pressed
    private boolean pressed = false;
    private boolean hovering = false;
    private boolean focused = false;

    // The method to execute when the button is clicked on
    private Method method;

    // The visuals of the button
    private Drawable visuals;

    /**
     * Create Button.
     * 
     * @param m the Method to run
     */
    public Button(Method m) {
        this(m, 0, 0, 80, 40);
    }

    /**
     * Create Button.
     * 
     * @param m the Method to run
     * @param x x position
     * @param y y position
     */
    public Button(Method m, int x, int y) {
        this(m, x, y, 80, 40);
    }

    /**
     * Create Button.
     * 
     * @param m      the Method to run
     * @param x      x position
     * @param y      y position
     * @param width  width
     * @param height height
     */
    public Button(Method m, int x, int y, int width, int height) {
        method = m;
        setLocation(x, y);
        setSize(width, height);
        visuals = new ButtonGraphics(this);
    }

    /**
     * Create Button.
     * 
     * @param m the Method to run
     * @param v the visuals
     */
    public Button(Method m, Drawable v) {
        this(m, v, 0, 0, 80, 40);
    }

    /**
     * Create Button.
     * 
     * @param m the Method to run
     * @param v the visuals
     * @param x x position
     * @param y y position
     */
    public Button(Method m, Drawable v, int x, int y) {
        this(m, v, x, y, 80, 40);
    }

    /**
     * Create Button.
     * 
     * @param m      the Method to run
     * @param v      the visuals
     * @param x      x position
     * @param y      y position
     * @param width  width
     * @param height height
     */
    public Button(Method m, Drawable v, int x, int y, int width, int height) {
        method = m;
        setLocation(x, y);
        setSize(width, height);
        visuals = v;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    /**
     * Sets the location of the button.
     * 
     * @param x x position
     * @param y y position
     */
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the size of the button.
     * 
     * @param width  width
     * @param height height
     */
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Sets the visuals of the button.
     * 
     * @param v the visuals
     */
    public void setVisuals(Drawable v) {
        visuals = v;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.translate(x, y);
        visuals.draw(g2d);
        g2d.translate(-x, -y);
    }

    @Override
    public boolean withinBounds(int x, int y) {
        return x > this.x && x < this.x + this.width && y > this.y && y < this.y + this.height;
    }

    @Override
    public void mouseClick(MouseEvent e) {
        if (method != null) {
            method.execute();
        }
    }

    @Override
    public void mouseEnter() {
        hovering = true;
        if (visuals instanceof HoverableGraphics) {
            ((HoverableGraphics) visuals).setState(HoverableGraphics.HOVERING);
        }
    }

    @Override
    public void mouseExit() {
        hovering = false;
        if (visuals instanceof HoverableGraphics) {
            ((HoverableGraphics) visuals).setState(HoverableGraphics.IDLE);
        }
    }

    @Override
    public void mousePress(MouseEvent e) {
        pressed = true;
        if (visuals instanceof PressableGraphics) {
            ((PressableGraphics) visuals).setState(PressableGraphics.PRESSING);
        }
    }

    @Override
    public void mouseRelease(MouseEvent e) {
        pressed = false;
        if (visuals instanceof HoverableGraphics) {
            if (hovering) {
                ((HoverableGraphics) visuals).setState(HoverableGraphics.HOVERING);
            } else {
                ((HoverableGraphics) visuals).setState(HoverableGraphics.IDLE);
            }
        }
    }

    @Override
    public boolean isPressed() {
        return pressed;
    }

    @Override
    public int getCursor() {
        return Cursor.HAND_CURSOR;
    }

    @Override
    public boolean isHovering() {
        return hovering;
    }

    @Override
    public void mouseMove(MouseEvent e) {

    }

    @Override
    public void unfocus() {
        focused = false;
    }

    @Override
    public void focus() {
        focused = true;
    }

    @Override
    public boolean isFocused() {
        return focused;
    }

    @Override
    public void drag(MouseEvent e) {

    }

}

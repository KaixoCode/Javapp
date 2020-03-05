package javapp.objects;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javapp.core.Method;
import javapp.graphics.ButtonGraphics;
import javapp.graphics.HoverableGraphics;
import javapp.graphics.PressableGraphics;
import javapp.objects.base.Drawable;
import javapp.objects.base.Focusable;

/**
 * Simple Button class.
 */
public class Button extends Focusable {

    // Location and size of the button
    private int x;
    private int y;
    private int width;
    private int height;

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
        this(m, 0, 0, 70, 35);
    }

    /**
     * Create Button.
     * 
     * @param m the Method to run
     * @param x x position
     * @param y y position
     */
    public Button(Method m, int x, int y) {
        this(m, x, y, 70, 35);
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
        setPosition(x, y);
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
        this(m, v, 0, 0, 70, 35);
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
        this(m, v, x, y, 70, 35);
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
        setPosition(x, y);
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

    public Method getAction() {
        return method;
    }

    /**
     * Sets the location of the button.
     * 
     * @param x x position
     * @param y y position
     */
    public void setPosition(int x, int y) {
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
    public void mouseClicked(MouseEvent e) {
        if (method != null) {
            method.execute();
        }
    }

    @Override
    public void mouseEntered() {
        if (visuals instanceof HoverableGraphics) {
            ((HoverableGraphics) visuals).setState(HoverableGraphics.HOVERING);
        }
    }

    @Override
    public void mouseExited() {
        if (visuals instanceof HoverableGraphics) {
            ((HoverableGraphics) visuals).setState(HoverableGraphics.IDLE);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (visuals instanceof PressableGraphics) {
            ((PressableGraphics) visuals).setState(PressableGraphics.PRESSING);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (visuals instanceof HoverableGraphics) {
            if (isHovering()) {
                ((HoverableGraphics) visuals).setState(HoverableGraphics.HOVERING);
            } else {
                ((HoverableGraphics) visuals).setState(HoverableGraphics.IDLE);
            }
        }
    }

    @Override
    public int getCursor() {
        return Cursor.HAND_CURSOR;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseWheel(MouseWheelEvent event) {
        // TODO Auto-generated method stub
        
    }

}

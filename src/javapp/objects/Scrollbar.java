package javapp.objects;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javapp.core.Functions;
import javapp.data.ColorTransition;
import javapp.objects.base.Focusable;

public class Scrollbar extends Focusable {

    public static final int VERTICAL = 0;
    public static final int HORIZONTAL = 1;

    private final int type;

    private boolean visible = true;

    private int pressX = 0;
    private int pressY = 0;
    private float presspos = 0;

    private int padding = 2;

    private float position = 0;
    private float barsize;
    private float barpos;
    private int x;
    private int y;
    private int size = 1;
    private int thickness = 25;
    private int realsize = 1;

    private ColorTransition color;

    public Scrollbar(int t, int size, int realsize, int x, int y) {
        type = t;
        this.x = x;
        this.y = y;
        this.size = size;
        this.realsize = realsize;
        color = new ColorTransition(new Color(240, 240, 240), 0.2);
    }

    @Override
    public void draw(Graphics2D g2d) {

        // Don't display a bar if the realsize is smaller than the size because there's
        // nothing to scroll.
        if (realsize <= size || !visible) {
            return;
        }

        barsize = (size * size) / realsize;
        barpos = ((size - barsize) * position) / (realsize - size);

        // Draw the bar depending on the type.
        if (type == VERTICAL) {
            vertical(g2d);
        } else {
            horizontal(g2d);
        }

        if (isPressed()) {
            color.morph(new Color(130, 130, 130));
        } else if (isHovering()) {
            color.morph(new Color(170, 170, 170));
        } else {
            color.morph(new Color(190, 190, 190));
        }
    }

    private void vertical(Graphics2D g2d) {
        g2d.setColor(new Color(240, 240, 240));
        g2d.fillRect(x, y, thickness, size);
        g2d.setColor(color.getValue());
        g2d.fillRect(x + padding, y + (int) barpos + padding, thickness - 2 * padding, (int) barsize - 2 * padding);
    }

    private void horizontal(Graphics2D g2d) {
        g2d.setColor(new Color(240, 240, 240));
        g2d.fillRect(x, y, size, thickness);
        g2d.setColor(color.getValue());
        g2d.fillRect(x + (int) barpos + padding, y + padding, (int) barsize - 2 * padding, thickness - 2 * padding);
    }

    public void setRealSize(int r) {
        realsize = r;
        position = Functions.constrain(position, 0f, (float) realsize - size);
    }

    public boolean visible() {
        return realsize - size > 0;
    }

    public void setSize(int r) {
        size = r;
        position = Functions.constrain(position, 0f, (float) realsize - size);
    }

    public void scroll(float amt) {
        position = Functions.constrain(position + amt, 0, realsize - size);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressX = e.getX() - x;
        pressY = e.getY() - y;
        presspos = barpos;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public boolean withinBounds(int x, int y) {
        if (type == VERTICAL) {
//            y -= position;
            return x > this.x && x < this.x + thickness && y > this.y + barpos && y < this.y + barpos + barsize;
        } else {
//            x -= position;
            return y > this.y && y < this.y + thickness && x > this.x + barpos && x < this.x + barpos + barsize;
        }
    }

    @Override
    public int getWidth() {
        if (type == VERTICAL) {
            return thickness;
        } else {
            return size;
        }
    }

    @Override
    public int getHeight() {
        if (type == HORIZONTAL) {
            return thickness;
        } else {
            return size;
        }
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return x;
    }

    @Override
    public void mouseEntered() {
    }

    @Override
    public void mouseExited() {
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public int getCursor() {
        return Cursor.DEFAULT_CURSOR;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (type == VERTICAL) {
            position = (int) Functions.mapstrain((e.getY() - pressY - y) + presspos, 0, size - barsize, 0, realsize - size);
        } else {
            position = (int) Functions.mapstrain((e.getX() - pressX - x) + presspos, 0, size - barsize, 0, realsize - size);
        }
    }

    public int getValue() {
        return (int) position;
    }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setVisible(boolean b) {
        visible = b;
    }

    public void setPadding(int i) {
        padding = i;
    }

    public void setScroll(int amt) {
        position = Functions.constrain(amt, 0, realsize - size);
    }

    @Override
    public void mouseWheel(MouseWheelEvent event) {
        scroll((float) (event.getPreciseWheelRotation() * 30));
    }
}

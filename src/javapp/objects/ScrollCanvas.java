package javapp.objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.util.EventObject;

import javapp.core.Canvas;

public abstract class ScrollCanvas extends Canvas {

    private Scrollbar hor;
    private Scrollbar ver;

    public ScrollCanvas(int w, int h) {
        super(w, h);

        hor = new Scrollbar(Scrollbar.HORIZONTAL, w, w * 2, getX(), getY() + h - 25);
        hor.setPadding(3);
        ver = new Scrollbar(Scrollbar.VERTICAL, h, h * 2, getX() + w - 25, getY());
        ver.setPadding(3);
        setSize(w, h);
        setCanvasSize(w, h);
    }

    public void setCanvasSize(int w, int h) {
        hor.setRealSize(w);
        ver.setRealSize(h);
    }

    public void displayScrollbars(boolean b) {
        hor.setVisible(b);
        ver.setVisible(b);
    }

    public void setSize(int w, int h) {
        super.setSize(w, h);
        if (hor != null && ver != null) {
            hor.setSize(w);
            ver.setSize(h);
        }
    }

    public void scrollX(int amt) {
        hor.scroll(amt);
    }

    public void scrollY(int amt) {
        ver.scroll(amt);
    }

    public void setScrollX(int amt) {
        hor.setScroll(amt);
    }

    public void setScrollY(int amt) {
        ver.setScroll(amt);
    }

    public int getScrolledX() {
        return hor.getValue();
    }

    public int getScrolledY() {
        return ver.getValue();
    }

    @Override
    public void mouseWheel(MouseWheelEvent e) {
        ver.scroll((int) (e.getPreciseWheelRotation() * 10));
    }

    @Override
    final public void draw(Graphics2D g) {

        // All draw stuff for when extending this class
        draw();

        // Reset the translate
        draw((g2d) -> {
            g2d.setTransform(new AffineTransform());
        });

        // Draw the scrollbars
        draw(hor);
        draw(ver);

        // Draw the panel border
        draw((g2d) -> {
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(Color.BLACK);
            g2d.drawRect(-1, -1, getWidth(), getHeight());
        });

        // Offset the scrollbars so the right mouse coords get send to them when
        // handling events (I know, bit cheaty...)
        hor.setPosition(hor.getValue(), getHeight() - 25 + ver.getValue());
        ver.setPosition(getWidth() - 25 + hor.getValue(), ver.getValue());

        // Handle all the events
        getEventHandler().handleEvents();

        // Set the cursor
        if (getEventHandler().getHovering() == null) {
            setCursor(Cursor.DEFAULT_CURSOR);
        } else {
            setCursor(getEventHandler().getHovering().getCursor());
        }

        // Now select the actual draw positions of the scrollbars
        hor.setPosition(0, getHeight() - 25);
        ver.setPosition(getWidth() - 25, 0);

        // Apply all draw events to the graphics
        getDrawables().forEach((a) -> a.draw(getGraphics()));

        // Clear the draw events for the next redraw
        getDrawables().clear();

        // Draw the canvas on the graphics
        g.drawImage(getImage(), getX(), getY(), null);

        // Make sure the bars don't overlap when both show
        if (hor.visible() && ver.visible()) {
            hor.setSize(getWidth() - 25);
            ver.setSize(getHeight() - 25);
        } else {
            hor.setSize(getWidth());
            ver.setSize(getHeight());
        }

        // Translate to the scroll
        draw((g2d) -> {
            g2d.translate(-hor.getValue(), -ver.getValue());
        });
    }

    @Override
    public void addEvent(EventObject e) {

        // Translate all mouse events as well, if it's not equal to the scrollbars
        if (e instanceof MouseEvent) {
            MouseEvent event = (MouseEvent) e;

            // Calculate the relative position given the scroll
            int newx = event.getX() + hor.getValue();
            int newy = event.getY() + ver.getValue();

            // Translate the coords of the mouse event relative to the canvas
            e = new MouseEvent(event.getComponent(), event.getID(), event.getWhen(), event.getModifiersEx(), newx, newy,
                    event.getXOnScreen(), event.getYOnScreen(), event.getClickCount(), event.isPopupTrigger(),
                    event.getButton());
        }

        // Add the event
        getEventHandler().add(e);
    }
}

package javapp.objects.test;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javapp.graphics.transition.ColorTransition;
import javapp.objects.Focusable;

public class DragThing extends Focusable {

    private int x = 0;
    private int y = 0;
    private int dx = 0;
    private int dy = 0;
    private int width = 100;
    private int height = 100;

    private ColorTransition color;

    private Color idle = new Color(0, 0, 0);
    private Color hover = new Color(30, 30, 30);
    private Color drag = new Color(90, 90, 90);

    public DragThing() {
        color = new ColorTransition(idle, 0.2);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        color.morph(drag);
        this.dx = this.x - e.getX();
        this.dy = this.y - e.getY();
    }

    @Override
    public boolean withinBounds(int x, int y) {
        return x > this.x && x < this.x + this.width && y > this.y && y < this.y + this.height;
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

    @Override
    public void drag(MouseEvent e) {
        this.x = e.getX() + dx;
        this.y = e.getY() + dy;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color.getValue());
        g2d.fillRect(x, y, width, height);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isHovering()) {
            color.morph(hover);
        } else {
            color.morph(idle);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public int getCursor() {
        return Cursor.MOVE_CURSOR;
    }

    @Override
    public void mouseEntered() {
        color.morph(hover);
    }

    @Override
    public void mouseExited() {
        color.morph(idle);
    }

    @Override
    public void mouseMove(MouseEvent e) {

    }
}

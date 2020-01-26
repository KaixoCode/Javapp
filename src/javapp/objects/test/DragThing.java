package javapp.objects.test;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javapp.objects.Focusable;

public class DragThing implements Focusable {

    private int x = 0;
    private int y = 0;
    private int dx = 0;
    private int dy = 0;
    private int width = 100;
    private int height = 100;

    private boolean pressed = false;
    private boolean hovering = false;
    private boolean focused = false;

    public DragThing() {

    }

    @Override
    public void mousePress(MouseEvent e) {
        this.dx = this.x - e.getX();
        this.dy = this.y - e.getY();

        pressed = true;
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
        g2d.setColor(Color.BLACK);
        g2d.fillRect(x, y, width, height);
    }

    @Override
    public boolean isPressed() {
        return pressed;
    }

    @Override
    public void mouseRelease(MouseEvent e) {
        pressed = false;
    }

    @Override
    public void mouseClick(MouseEvent e) {

    }

    @Override
    public int getCursor() {
        return Cursor.MOVE_CURSOR;
    }

    @Override
    public void mouseEnter() {
        hovering = true;
    }

    @Override
    public void mouseExit() {
        hovering = false;
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
}

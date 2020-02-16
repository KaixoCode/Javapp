package javapp.objects.text;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javapp.objects.ScrollCanvas;
import javapp.objects.base.Focusable;

public class TextArea extends ScrollCanvas {

    private TextDisplayer displayer;

    public TextArea(int x, int y, int w, int h) {
        super(w, h);
        setPosition(x, y);
        displayer = new TextDisplayer(0, 0, w, h);
        getDisplayer().getContainer();
    }

    public TextArea(int x, int y) {
        this(x, y, 300, 300);
    }

    @Override
    public void draw() {
        int newW = Math.max(displayer.getBiggestX(), getWidth());
        int newH = Math.max(displayer.getBiggestY(), getHeight());

        setCanvasSize(newW, newH);
        displayer.setSize(newW, newH);

        draw(displayer);

        if (update > 0) {
            updateScroll();
            update--;
        }
    }

    public void updateScroll() {
        // Make sure the typepos is always on the screen by scrolling there if
        // necessary.
        int x = displayer.getTypeX();
        int y = displayer.getTypeY() * (displayer.getTextleading() + displayer.getFont().getSize());
        if (x > getScrolledX() + getWidth() - getDisplayer().getPaddingX() * 2) {
            setScrollX(x - getWidth() + getDisplayer().getPaddingX() * 2);
        } else if (x < getScrolledX() + displayer.getPaddingX()) {
            setScrollX(x - displayer.getPaddingX());
        }

        if (y > getScrolledY() + getHeight() - getDisplayer().getPaddingY() * 2) {
            setScrollY(y - getHeight() + getDisplayer().getPaddingY() * 2);
        } else if (y < getScrolledY()) {
            setScrollY(y);
        }
    }

    private int update = 0;

    @Override
    public void keyType(KeyEvent e) {
        update = 10;
    }

    @Override
    public void keyPress(KeyEvent e) {

    }

    @Override
    public void drag(MouseEvent e) {
        if (displayer.isFocused())
            updateScroll();
    }

    public TextDisplayer getDisplayer() {
        return displayer;
    }

    @Override
    public void focus() {
        displayer.focus();
        super.focus();
    }

    public void unfocus() {
        displayer.unfocus();
        super.unfocus();
    }

    public void setTabObject(Focusable o) {
        displayer.setTabObject(o);
    }

    public void setBackTabObject(Focusable o) {
        displayer.setBackTabObject(o);
    }
}

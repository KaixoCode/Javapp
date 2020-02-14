package javapp.objects.text;

import java.awt.event.KeyEvent;
import javapp.objects.ScrollCanvas;

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
    }

    @Override
    public void keyPress(KeyEvent e) {
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

    public TextDisplayer getDisplayer() {
        return displayer;
    }

}

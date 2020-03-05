package javapp.objects.text;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javapp.objects.ScrollCanvas;
import javapp.objects.base.Focusable;

public class TextArea extends ScrollCanvas {

    private DataDisplayer<String> displayer;

    public DataDisplayer<String>.Style style;

    public TextArea(int x, int y, int w, int h) {
        super(w, h);
        setPosition(x, y);
        displayer = new DataDisplayer<String>(0, 0, w, h, (s) -> s) {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                update = 10;
            }
        };
        style = displayer.style;
        getDisplayer().getContainer();
    }

    public TextArea(int x, int y) {
        this(x, y, 300, 300);
    }

    @Override
    public void draw() {

        int newW = Math.max(displayer.getBiggestX(), getWidth());
        int newH = Math.max(displayer.getBiggestY(), getHeight());
        if (style.wrap.wrap()) {
            newW = getWidth();
        }
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
        int y = displayer.getTypeY();
        if (x > getScrolledX() + getWidth() - getDisplayer().style.padding * 2) {
            setScrollX(x - getWidth() + getDisplayer().style.padding * 2);
        } else if (x < getScrolledX() + displayer.style.padding) {
            setScrollX(x - displayer.style.padding);
        }

        if (y > getScrolledY() + getHeight() - getDisplayer().style.padding * 2 - style.font.getSize()) {
            setScrollY(y - getHeight() + getDisplayer().style.padding * 2 + style.font.getSize());
        } else if (y < getScrolledY() + getDisplayer().style.padding) {
            setScrollY(y - getDisplayer().style.padding);
        }
    }

    private int update = 0;

    @Override
    public void mouseDragged(MouseEvent e) {
        if (displayer.isFocused())
            updateScroll();
    }

    public DataDisplayer<String> getDisplayer() {
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

    public void setContent(String string) {
        displayer.setContent(string);
    }

    public String getContent() {
        return displayer.getContent();
    }
}

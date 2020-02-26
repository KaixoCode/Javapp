package javapp.objects.text;

import java.awt.Color;
import java.awt.event.KeyEvent;

import javapp.objects.ScrollCanvas;
import javapp.objects.base.Focusable;

public class TextField extends ScrollCanvas {

    private DataDisplayer<String> displayer;
    private DataContainer<String> container;

    public DataDisplayer<String>.Style style;

    public int scrolled = 0;
    public boolean submit = false;

    public TextField(int x, int y, int w, int h) {
        super(w, h);
        setPosition(x, y);
        displayer = new DataDisplayer<String>(0, 0, w, h, s -> s) {
            public void keyType(KeyEvent e) {
                if (e.getKeyChar() != '\n') {
                    super.keyType(e);
                } else {
                    submit = true;
                }
            }
        };
        container = getDisplayer().getContainer();
        style = displayer.style;
        displayScrollbars(false);
    }

    public boolean enter() {
        if (submit) {
            submit = false;
            return true;
        }
        return false;
    }

    public TextField(int x, int y) {
        this(x, y, 300, 28);
    }

    @Override
    public void draw() {
        draw((g2d) -> {
            int w = g2d.getFontMetrics().stringWidth(container.getContent().substring(0, container.getTypeIndex()));
            if (w > scrolled + getWidth() - getDisplayer().style.padding * 2) {
                scrolled = w - getWidth() + getDisplayer().style.padding * 2;
            } else if (w < scrolled) {
                scrolled = w;
            }
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, getWidth() + scrolled, getHeight());
        });
        setCanvasSize(getWidth() + scrolled, getHeight());
        displayer.setSize(getWidth() + scrolled, getHeight());
        scrollX(scrolled);

        draw(displayer);
    }

    public DataDisplayer<String> getDisplayer() {
        return displayer;
    }

    @Override
    public void focus() {
        displayer.focus();
        super.focus();
    }

    @Override
    public void unfocus() {
        displayer.unfocus();
        super.unfocus();
    }

    public void setTabObject(Focusable o) {
        displayer.setTabObject(o);
        super.setTabObject(o);
    }

    public void setBackTabObject(Focusable o) {
        displayer.setBackTabObject(o);
        super.setBackTabObject(o);
    }

    public void setContent(String string) {
        displayer.setContent(string);
    }

    public String getContent() {
        return displayer.getContent();
    }
}

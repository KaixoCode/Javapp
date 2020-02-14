package javapp.objects.text;

import java.awt.Color;
import javapp.objects.ScrollCanvas;

public class TextField extends ScrollCanvas {

    private TextDisplayer displayer;
    private TextContainer container;

    public int scrolled = 0;

    public TextField(int x, int y, int w, int h) {
        super(w, h);
        setPosition(x, y);
        displayer = new TextDisplayer(0, 0, w, h);
        container = getDisplayer().getContainer();
        displayScrollbars(false);
    }

    public TextField(int x, int y) {
        this(x, y, 300, 28);
    }

    @Override
    public void draw() {
        draw((g2d) -> {
            int w = g2d.getFontMetrics().stringWidth(container.getContent().substring(0, container.getTypeIndex()));
            if (w > scrolled + getWidth() - getDisplayer().getPaddingX() * 2) {
                scrolled = w - getWidth() + getDisplayer().getPaddingX() * 2;
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

    public TextDisplayer getDisplayer() {
        return displayer;
    }
}

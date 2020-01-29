package javapp.objects.text;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javapp.core.Canvas;

public class TextField extends TextDisplayer {

    private TextContainer container;

    public int scrolled = 0;

    public TextField(int x, int y, int w, int h) {
        super(x, y, w, h, new TextContainer());
        container = getContainer();
    }

    @Override
    public void draw(Graphics2D g) {
        Canvas canvas = getCanvas();

        canvas.draw((g2d) -> {
            int w = g2d.getFontMetrics().stringWidth(container.getContent().substring(0, container.getTypeIndex()));
            if (w > scrolled + getWidth() - getPaddingX() * 2) {
                scrolled = w - getWidth() + getPaddingX() * 2;
            } else if (w < scrolled) {
                scrolled = w;
            }
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.translate(-scrolled, 0);
        });

        super.draw(g);

        canvas.draw((g2d) -> {
            g2d.translate(scrolled, 0);

            // Draw the border
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(Color.BLACK);
            g2d.drawRect(-1, -1, getWidth(), getHeight());
        });
        canvas.redraw();

    }

    public void keyType(KeyEvent e) {

        // TextField does not support multiline
        if (e.getKeyChar() != KeyEventHandler.ENTER) {
            super.keyType(e);
        }
    }

}

package javapp.objects.text;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;

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

//    @Override
//    public void draw(Graphics2D g) {
//        draw((g2d) -> {
//            int w = g2d.getFontMetrics().stringWidth(container.getContent().substring(0, container.getTypeIndex()));
//            if (w > scrolled + getWidth() - getDisplayer().getPaddingX() * 2) {
//                scrolled = w - getWidth() + getDisplayer().getPaddingX() * 2;
//            } else if (w < scrolled) {
//                scrolled = w;
//            }
//            g2d.setColor(Color.WHITE);
//            g2d.fillRect(0, 0, getWidth() + scrolled, getHeight());
//
//        });
//        setCanvasSize(getWidth() + scrolled, getHeight());
//        displayer.setSize(getWidth() + scrolled, getHeight());
//        scrollX(scrolled);
//
//        draw(displayer);
//
//        draw((g2d) -> {
//            g2d.setTransform(new AffineTransform());
//
//            // Draw the border
//            g2d.setStroke(new BasicStroke(2));
//            g2d.setColor(Color.BLACK);
//            g2d.drawRect(-1, -1, getWidth(), getHeight());
//        });
//        super.draw(g);
//        redraw();
//    }

    public void keyType(KeyEvent e) {

        // TextField does not support multiline
        if (e.getKeyChar() != KeyEventHandler.ENTER) {
            getDisplayer().keyType(e);
        }
    }

    public TextDisplayer getDisplayer() {
        return displayer;
    }

}

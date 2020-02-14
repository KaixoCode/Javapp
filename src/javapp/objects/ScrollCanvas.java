package javapp.objects;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javapp.core.Canvas;

public class ScrollCanvas extends Canvas {

    private Scrollbar hor;
    private Scrollbar ver;

    public ScrollCanvas(int w, int h) {
        super(w, h);

        hor = new Scrollbar(Scrollbar.HORIZONTAL, w, w * 2, getX(), getY() + h - 25);
        ver = new Scrollbar(Scrollbar.VERTICAL, h, h * 2, getX() + w - 25, getY());
    }

    public void setCanvasSize(int w, int h) {
        hor.setRealSize(w);
        ver.setRealSize(h);
    }

    public void setSize(int w, int h) {
        super.setSize(w, h);
        if (hor != null && ver != null) {
            hor.setSize(w);
            ver.setSize(h);
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        getGraphics().translate(-hor.getValue(), -ver.getValue());
        if (hor.visible() && ver.visible()) {
            hor.setSize(getWidth() - 25);
            ver.setSize(getHeight() - 25);
        } else {
            hor.setSize(getWidth());
            ver.setSize(getHeight());
        }

        hor.setPosition(hor.getValue(), getHeight() - 25 + ver.getValue());
        draw(hor);
        ver.setPosition(getWidth() - 25 + hor.getValue(), ver.getValue());
        draw(ver);
        redraw();
        g2d.drawImage(getImage(), getX(), getY(), null);
        getGraphics().setTransform(new AffineTransform());
    }
}

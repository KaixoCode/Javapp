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

    @Override
    public void draw() {
        int newW = Math.max(displayer.getBiggestX(), getWidth());
        int newH = Math.max(displayer.getBiggestY(), getHeight());

        setCanvasSize(newW, newH);
        displayer.setSize(newW, newH);
//        displayer.setPosition(-getScrolledX(), -getScrolledY());

        draw(displayer);
    }

    public void keyType(KeyEvent e) {
        getDisplayer().keyType(e);
    }

    public TextDisplayer getDisplayer() {
        return displayer;
    }

}

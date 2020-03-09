
import java.awt.Color;
import java.awt.event.MouseEvent;

import javapp.core.S;
import javapp.core.Window;
import javapp.data.Transition;
import javapp.data.Vector;
import javapp.data.VectorTransition;
import javapp.objects.text.TextArea;

public class Main extends Window {

    public static void main(String... a) {
        new Main(800, 800);
    }

    public Main(int w, int h) {
        super(w, h);
    }

    VectorTransition position;

    public void setup() {
        position = new VectorTransition(0, 0, 0.02);
    }

    public void draw() {
        draw((g2d) -> {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.setColor(Color.WHITE);
        });

        Vector<Double> pos = position.getValue();
        draw(g2d -> g2d.fillOval(pos.x.intValue() - 25, pos.y.intValue() - 25, 50, 50));
    }

    public void mouseMoved(MouseEvent e) {
        position.morph(new Vector<Double>((double) e.getX(), ((double) e.getY())));
    }

    public void mouseDragged(MouseEvent e) {
        position.morph(new Vector<Double>((double) e.getX(), ((double) e.getY())));
    }
}

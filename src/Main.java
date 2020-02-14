
import java.awt.Color;
import java.util.ArrayList;

import javapp.core.Window;
import javapp.data.ColorTransition;
import javapp.objects.Button;
import javapp.objects.DragThing;
import javapp.objects.ScrollCanvas;
import javapp.objects.Scrollbar;
import javapp.objects.text.TextArea;
import javapp.objects.text.TextField;

public class Main extends Window {

    public static void main(String... a) {
        new Main(800, 800);
    }

    public Main(int w, int h) {
        super(w, h);
    }

    Button button1, button2;

    ArrayList<DragThing> a;

    TextArea text;

    ScrollCanvas canvas;
    ColorTransition color;

    Scrollbar verbar;
    Scrollbar horbar;

    public void setup() {
        button1 = new Button(() -> System.out.println("apple"), -10, 7);
        button2 = new Button(() -> System.out.println("carrot"), 50, 50);

        canvas = new ScrollCanvas(600, 600);
        canvas.setPosition(100, 100);
        color = new ColorTransition(new Color(100, 100, 100), 0.2);

        text = new TextArea(100, 100, 300, 300);

        verbar = new Scrollbar(Scrollbar.VERTICAL, 500, 1000, 600, 100);
        horbar = new Scrollbar(Scrollbar.HORIZONTAL, 500, 1000, 100, 600);

        a = new ArrayList<DragThing>();
        for (int i = 0; i < 10; i++) {
            a.add(new DragThing());
            a.get(i).setPosition((int) (Math.random() * getWidth()), (int) (Math.random() * getHeight()));
        }
    }

    public void draw() {
        draw((g2d) -> {
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        });

        canvas.draw((g2d) -> {
            g2d.setColor(new Color(240, 240, 240));
            g2d.fillRect(0, 0, 1000, 1000);
        });

        canvas.draw((g2d) -> {
            g2d.setColor(Color.RED);
            g2d.fillRect(0, 0, 100, 100);
        });

        canvas.draw(text);
        canvas.draw(button2);
        canvas.setCanvasSize(1000, 1000);
        draw(canvas);

//        draw(verbar);
//        draw(horbar);
//        for (DragThing b : a) {
//            draw(b);
//        }
    }
}

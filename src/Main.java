
import java.awt.Color;

import javapp.core.Canvas;
import javapp.core.Window;
import javapp.objects.button.Button;
import javapp.objects.test.DragThing;
import javapp.objects.text.TextField;

public class Main extends Window {

    public static void main(String... a) {
        new Main(800, 800);
    }

    public Main(int w, int h) {
        super(w, h);
    }

    Button button1, button2;

    DragThing a, b, c, d;

    TextField text;

    Canvas canvas;

    public void setup() {
        button1 = new Button(() -> System.out.println("apple"), -10, 7);
        button2 = new Button(() -> System.out.println("carrot"), 300, 300);

        canvas = new Canvas(100, 100);
        canvas.setLocation(200, 200);

        text = new TextField(10, 500, 500);

        a = new DragThing();
        b = new DragThing();
        c = new DragThing();
        d = new DragThing();
    }

    public void draw() {

        draw((g2d) -> {
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        });

        canvas.draw((g2d) -> {
            g2d.setColor(Color.BLUE);
            g2d.fillRect(0, 0, 320, 400);
        });
        draw(a);
        draw(b);
        draw(c);
        draw(d);

        draw(button2);

        canvas.draw(button1);
        canvas.redraw();
        draw(canvas);

        draw(text);

        draw((g2d) -> {
            g2d.setColor(Color.CYAN);
            g2d.fillRect(140, 130, 100, 100);
        });
    }
}

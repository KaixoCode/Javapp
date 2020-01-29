
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javapp.core.Canvas;
import javapp.core.Window;
import javapp.graphics.transition.ColorTransition;
import javapp.objects.Pressable;
import javapp.objects.button.Button;
import javapp.objects.test.DragThing;
import javapp.objects.text.TextDisplayer;
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

    TextField text;

    Canvas canvas;

    public void setup() {
        button1 = new Button(() -> System.out.println("apple"), -10, 7);
        button2 = new Button(() -> System.out.println("carrot"), 50, 300);

        canvas = new Canvas(300, 300);
        canvas.setLocation(200, 200);

        text = new TextField(100, 100, 300, 36);

        a = new ArrayList<DragThing>();
        for (int i = 0; i < 100; i++) {
            a.add(new DragThing());
            a.get(i).setPosition((int) (Math.random() * getWidth()), (int) (Math.random() * getHeight()));
        }
    }

    public void draw() {
//        draw((g2d) -> {
//            g2d.setColor(Color.WHITE);
//            g2d.fillRect(0, 0, getWidth(), getHeight());
//        });
//
//        draw((g2d) -> {
//            g2d.setColor(new Color(255, 25, 150));
//            g2d.fillRect(0, 0, 800, 400);
//        });
//
//        draw(thing);
//
//        draw(button2);

//        canvas.draw(button1);
//        canvas.redraw();
//        draw(canvas);

        draw(text);

//        for (DragThing b : a) {
//            draw(b);
//        }

//        draw((g2d) -> {
//            g2d.setColor(Color.CYAN);
//            g2d.fillRect(140, 130, 100, 100);
//        });
    }
}

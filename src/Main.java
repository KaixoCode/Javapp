
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.Timer;

import javapp.core.Console;
import javapp.core.Window;
import javapp.data.ColorTransition;
import javapp.objects.Button;
import javapp.objects.DragThing;
import javapp.objects.ScrollCanvas;
import javapp.objects.Scrollbar;
import javapp.objects.text.DataContainer;
import javapp.objects.text.DataDisplayer;
import javapp.objects.text.TextArea;
import javapp.objects.text.TextField;

public class Main extends Window {

    public static void main(String... a) {
        new Main(800, 800);
    }

    Console c;

    public int apple = 1;

    public Main(int w, int h) {
        super(w, h);
        new Console().setObject(this);
    }

    public class Woof {

        public Apple a = new Apple(this);
        public int oof = 19;

        public Woof() {
            new Timer(1, (a) -> {
                oof++;
                if (oof % 10 == 0) {
                    this.a.things++;
                }
            }).start();
        }

        public class Apple {
            public int things = 0;
            public Woof w;

            public Apple(Woof f) {
                w = f;
            }
        }
    }

    public Button button1, button2;

    public ArrayList<DragThing> a;

    public TextArea text;
    public TextField field1;
    public TextField field2;
    public TextField field3;

    public ScrollCanvas canvas;
    public ColorTransition color;

    public Scrollbar verbar;
    public Scrollbar horbar;

    public TextArea integ;

    private void aiofnoeinafaien() {
        System.out.println("You got me!");
    }

    public void setup() {
        button1 = new Button(() -> System.out.println("apple"), -10, 7);
        button2 = new Button(() -> System.out.println("carrot"), 450, 50);

        canvas = new ScrollCanvas(600, 600) {

            @Override
            public void draw() {
                draw((g2d) -> {
                    g2d.setColor(new Color(240, 240, 240));
                    g2d.fillRect(0, 0, 1000, 1000);
                });

                draw((g2d) -> {
                    g2d.setColor(Color.RED);
                    g2d.fillRect(0, 0, 500, 500);
                });

                draw(field1);
                draw(field2);
                draw(field3);
                draw(text);
                draw(button2);
                setCanvasSize(1000, 1000);
            }
        };
        canvas.setPosition(100, 100);
        color = new ColorTransition(new Color(100, 100, 100), 0.2);

        text = new TextArea(100, 140);
        field1 = new TextField(100, 50);
        field2 = new TextField(100, 80);
        field3 = new TextField(100, 110);
        field1.setTabObject(field2);
        field2.setTabObject(field3);

        integ = new TextArea(0, 0, 500, 500);
        integ.setContent("Woof");
        integ.style.font = new Font("Consolas", Font.PLAIN, 16);
        integ.style.background = new Color(0, 0, 0);
        integ.style.color = new Color(255, 255, 255);
        integ.style.selection = new Color(255, 255, 255, 50);

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

        draw(integ);
        draw(button1);
        draw(button2);

        draw(canvas);

        draw(verbar);
        draw(horbar);
//        for (DragThing b : a) {
//            draw(b);
//        }
    }
}

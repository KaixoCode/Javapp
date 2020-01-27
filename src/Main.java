
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javapp.core.Canvas;
import javapp.core.Window;
import javapp.graphics.transition.ColorTransition;
import javapp.objects.Pressable;
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

    Pressable thing = new Pressable() {
        int x = 50;
        int y = 50;
        int width = 320;
        int height = 400;

        ColorTransition color = new ColorTransition(Color.BLUE, 0.3);

        @Override
        public void draw(Graphics2D g2d) {
            g2d.setColor(color.getValue());
            g2d.fillRect(x, y, width, height);
        }

        @Override
        public boolean withinBounds(int x, int y) {
            return x > this.x && x < this.x + this.width && y > this.y && y < this.y + this.height;
        }

        @Override
        public int getWidth() {
            return width;
        }

        @Override
        public int getHeight() {
            return height;
        }

        @Override
        public int getX() {
            return x;
        }

        @Override
        public int getY() {
            return y;
        }

        @Override
        public void mouseEntered() {
            color.morph(Color.BLACK);
        }

        @Override
        public void mouseExited() {
            color.morph(Color.BLUE);
        }

        @Override
        public void mouseMove(MouseEvent e) {

        }

        @Override
        public int getCursor() {
            return Cursor.DEFAULT_CURSOR;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            color.morph(Color.RED);
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (isHovering()) {
                color.morph(Color.BLACK);
            } else {
                color.morph(Color.BLUE);    
            }
        }
    };

    public void setup() {
        button1 = new Button(() -> System.out.println("apple"), -10, 7);
        button2 = new Button(() -> System.out.println("carrot"), 50, 300);

        canvas = new Canvas(300, 300);
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

        draw(thing);

        draw(button2);

//        canvas.draw(button1);
//        canvas.redraw();
//        draw(canvas);

        draw(a);
        draw(b);
        draw(c);

        draw(text);
        draw(d);

        draw((g2d) -> {
            g2d.setColor(Color.CYAN);
            g2d.fillRect(140, 130, 100, 100);
        });
    }
}

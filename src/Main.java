
import java.awt.Color;
import javapp.core.S;
import javapp.core.Window;
import javapp.data.Transition;
import javapp.objects.text.TextArea;

public class Main extends Window {

    public static void main(String... a) {
        new Main(800, 800);
    }

    public Main(int w, int h) {
        super(w, h);
    }

    TextArea text;

    public void setup() {
        text = new TextArea(0, 0, 300, 300);
    }

    public void draw() {

        draw((g2d) -> {
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        });

        draw(text);

    }
}

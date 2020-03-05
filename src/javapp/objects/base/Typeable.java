package javapp.objects.base;

import java.awt.event.KeyEvent;

public abstract class Typeable extends Focusable {

    public abstract void keyPressed(KeyEvent e);

    public abstract void keyReleased(KeyEvent e);

    public abstract void keyTyped(KeyEvent e);
}

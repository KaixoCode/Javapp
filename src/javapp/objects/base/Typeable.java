package javapp.objects.base;

import java.awt.event.KeyEvent;

public abstract class Typeable extends Focusable {

    public abstract void keyPress(KeyEvent e);

    public abstract void keyRelease(KeyEvent e);

    public abstract void keyType(KeyEvent e);

}

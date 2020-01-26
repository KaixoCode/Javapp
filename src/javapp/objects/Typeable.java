package javapp.objects;

import java.awt.event.KeyEvent;

public interface Typeable extends Focusable {

    public void keyPress(KeyEvent e);

    public void keyRelease(KeyEvent e);

    public void keyType(KeyEvent e);

}

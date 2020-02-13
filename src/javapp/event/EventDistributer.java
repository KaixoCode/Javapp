package javapp.event;

import java.util.ArrayList;
import java.util.EventObject;

import javapp.objects.Drawable;

public interface EventDistributer {
    public void addEvent(EventObject e);

    public ArrayList<Drawable> getDrawables();
    
    public int getX();
    
    public int getY();
}

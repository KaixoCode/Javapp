package javapp.event;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.EventObject;

import javapp.objects.base.Drawable;

public interface EventDistributer {
    public void addEvent(EventObject e);

    public ArrayList<Drawable> getDrawables();
    
    public Graphics2D getGraphics();
    
    public int getX();
    
    public int getY();
}

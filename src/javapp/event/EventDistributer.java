package javapp.event;

import java.util.ArrayList;
import java.util.EventObject;

import javapp.objects.Drawable;
import javapp.objects.Pressable;

public interface EventDistributer extends Pressable {
    public void addEvent(EventObject e);

    public ArrayList<Drawable> getDrawables();
}

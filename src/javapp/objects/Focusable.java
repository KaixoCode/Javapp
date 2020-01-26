package javapp.objects;

public interface Focusable extends Pressable {
    
    public void unfocus();

    public void focus();

    public boolean isFocused();
}

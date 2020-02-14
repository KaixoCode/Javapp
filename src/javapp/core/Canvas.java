package javapp.core;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EventObject;

import javapp.event.EventDistributer;
import javapp.event.EventHandler;
import javapp.objects.base.Drawable;
import javapp.objects.base.Focusable;
import javapp.objects.base.Hoverable;
import javapp.objects.base.Typeable;

public class Canvas extends Typeable implements EventDistributer {

    // The size of the canvas
    private int width;
    private int height;
    private int x;
    private int y;

    // The cursor on this Canvas
    private int cursor;

    // The graphics to where everything is drawn
    private Graphics2D graphics;
    private BufferedImage image;

    // All the stuff called to be drawn
    private ArrayList<Drawable> drawables = new ArrayList<Drawable>();

    // Handles all the events
    private EventHandler handler;

    /**
     * Creates a canvas.
     * 
     * @param w width
     * @param h height
     */
    public Canvas(int w, int h) {
        handler = new EventHandler(this);
        setSize(w, h);
    }

    @Override
    public void unfocus() {

        // Unfocus any sub object that had focus
        Focusable f = handler.getFocused();
        if (f != null) {
            f.unfocus();
        }

        // Unfocus this object
        super.unfocus();
    }

    /**
     * Returns the BufferedImage associated with this Canvas.
     * 
     * @return BufferedImage
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Returns the Graphics2D associated with this Canvas.
     * 
     * @return Graphics2D
     */
    public Graphics2D getGraphics() {
        return graphics;
    }

    public EventHandler getEventHandler() {
        return handler;
    }

    /**
     * Sets the size of this canvas.
     * 
     * @param w width
     * @param h height
     */
    public void setSize(int w, int h) {

        // Only resize if the width and height actually change
        if ((w != width || h != height) && w > 0 && h > 0) {
            width = w;
            height = h;

            // Create the BufferedImage with the right width and height
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            graphics = image.createGraphics();

            // Enable antialiasing on text
            RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            rh.add(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
            graphics.setRenderingHints(rh);
        }
    }

    /**
     * Sets the location of the canvas on the window
     * 
     * @param x x-location
     * @param y y-location
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns all drawables that were drawn on the canvas.
     * 
     * @return all drawables that were drawn on the canvas
     */
    public ArrayList<Drawable> getDrawables() {
        return drawables;
    }

    /**
     * Adds a drawable object to the paintable object.
     * 
     * @param drawable the drawable object
     */
    public void draw(Drawable drawable) {
        getDrawables().add(drawable);
    }

    /**
     * Redraws the canvas.
     */
    final public void redraw() {

        // Handle all the events
        getEventHandler().handleEvents();

        // Set the cursor
        if (getEventHandler().getHovering() == null) {
            cursor = Cursor.DEFAULT_CURSOR;
        } else {
            cursor = getEventHandler().getHovering().getCursor();
        }

        // Apply all draw events to the graphics
        drawables.forEach((a) -> a.draw(graphics));

        // Clear the draw events for the next redraw
        drawables.clear();
    }

    @Override
    public void addEvent(EventObject e) {
        handler.add(e);
    }

    @Override
    public void draw(Graphics2D g2d) {

        // Put the image on the JPanel
        BufferedImage image = getImage();
        g2d.drawImage(image, x, y, null);
        redraw();
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int getCursor() {
        return cursor;
    }

    public void setCursor(int c) {
        cursor = c;
    }

    @Override
    public void mouseEntered() {
    }

    @Override
    public void mouseExited() {

        // Get all the drawables from the canvas
        ArrayList<Drawable> hoverables = new ArrayList<Drawable>(getDrawables());

        // Remove all non pressables
        hoverables.removeIf((c) -> !(c instanceof Hoverable));

        // Set hover to false in all the hoverables
        for (Drawable d : hoverables) {
            ((Hoverable) d).mouseExit();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void drag(MouseEvent e) {

    }

    @Override
    public void keyPress(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyRelease(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyType(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
}

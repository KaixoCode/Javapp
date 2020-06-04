package javapp.objects.text;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.Timer;

import javapp.core.Functions;
import javapp.objects.base.Typeable;

public class DataDisplayer<T> extends Typeable {

    private int width;
    private int height;
    private int x = 100;
    private int y = 100;

    private int biggestX = 0;
    private int biggestY = 0;

    private int typeX = 0;
    private int typeline = 0;

    private int typeIndexX = 0;
    private int typeIndexY = 0;

    private Graphics2D graphics;

    private DataContainer<T> container;

    /**
     * All style stuff
     */
    public final Style style = new Style();

    /**
     * Create TextDisplayer.
     * 
     * @param x x-pos
     * @param y y-pos
     * @param w width
     * @param h height
     */
    public DataDisplayer(int x, int y, int w, int h, DataType<T> t) {
        this(x, y, w, h, new DataContainer<T>(t));
    }

    /**
     * Create TextDisplayer.
     * 
     * @param x x-pos
     * @param y y-pos
     * @param w width
     * @param h height
     * @param c TextContainer
     */
    public DataDisplayer(int x, int y, int w, int h, DataContainer<T> c) {
        this.container = c;
        this.width = w;
        this.height = h;
        this.x = x;
        this.y = y;

        new Timer(2, (a) -> typeline++).start();
    }

    /**
     * Returns the TextContainer.
     * 
     * @return container
     */
    public DataContainer<T> getContainer() {
        return container;
    }

    public int getBiggestX() {
        return biggestX;
    }

    public int getBiggestY() {
        return biggestY;
    }

    @Override
    public void draw(Graphics2D g2d) {
        graphics = g2d;

        g2d.setFont(style.font);
        int textheight = style.font.getSize() + style.textleading;

        // Calculate the position of the type index
        int[] t = indexToPosition(container.getTypeIndex());
        typeIndexX = t[0];
        typeIndexY = t[1];

        // Calculate the position of the lowest select index
        int[] s = indexToPosition(container.lowestSelectIndex());
        int sx = s[0];
        int sy = s[1];

        // Calculate the position of the highest select index
        int[] e = indexToPosition(container.highestSelectIndex());
        int ex = e[0];
        int ey = e[1];

        // Display background
        g2d.setColor(style.background);
        g2d.fillRect(0, 0, width, height);

        // Display selection
        g2d.setColor(style.selection);
        if (sy == ey) {
            g2d.fillRect(sx, sy, ex - sx, textheight);
        } else {
            g2d.fillRect(sx, sy, width, textheight);
            g2d.fillRect(style.padding, sy + textheight, width, (ey - sy) - textheight);
            g2d.fillRect(style.padding, ey, ex - style.padding, textheight);
        }

        // Display text in lines
        g2d.setColor(style.color);
        String[] lines = getSplit();
        biggestX = 0;
        biggestY = 0;
        for (int i = 0; i < lines.length; i++) {

            // Calculate the biggest X coordinate
            int w = stringWidth(lines[i]);
            if (w > biggestX)
                biggestX = w;

            // Draw the string
            g2d.drawString(lines[i], style.padding, (textheight) * (i + 1) - style.textleading / 3);
        }
        biggestX += style.padding * 2;
        biggestY = lines.length * (textheight) + style.padding * 2;

        // Typeline
        g2d.setStroke(new BasicStroke(1));
        if (typeline < 75 && isFocused()) {
            g2d.drawLine(typeIndexX, typeIndexY, typeIndexX, typeIndexY + style.font.getSize());
        }

        if (typeline > 150) {
            typeline = 0;
        }
    }

    public String[] getSplit() {
        return getSplit(container.getContentAsString());
    }

    public String[] getSplit(String s) {
        if (style.wrap.equals(Wrap.WORD)) {
            String content = "";
            String thisline = "";

            String[] words = (s + ".").split(" ");
            for (String word : words) {
                if (word.contains("\n")) {
                    String[] enters = (word + " ").split("\n");
                    content += thisline;
                    for (int i = 0; i < enters.length - 1; i++) {
                        content += enters[i] + " \n";
                    }
                    thisline = enters[enters.length - 1];
                } else if (stringWidth(thisline + word + "  ") >= getWidth()) {
                    content += (thisline.length() > 0 ? thisline.substring(0, thisline.length() - 1) : " ") + " \n";
                    thisline = word + " ";
                } else {
                    thisline += word + " ";
                }
            }
            content += thisline.length() > 0 ? thisline.substring(0, thisline.length() - 1) : "";
            return (content.substring(0, content.length() - 1) + " ").split("\n");
        } else {
            return (s + " ").split("\n");
        }
    }

    private int stringWidth(String string) {
        return graphics.getFontMetrics().stringWidth(string);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        typeline = 0;
        KeyEventHandler.keyPress(e, container);
        typeIndex(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        KeyEventHandler.keyRelease(e, container);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        KeyEventHandler.keyType(e, container);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        container.setSelectStop(positionToIndex(e.getX() - x, e.getY() - y));
        updateTypeX();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        typeline = 0;
        container.setTypeIndex(positionToIndex(e.getX() - x, e.getY() - y));
        updateTypeX();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

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

    }

    @Override
    public void mouseExited() {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public int getCursor() {
        return Cursor.TEXT_CURSOR;
    }

    /**
     * Updates the x-position to which the up and down arrows will go to.
     */
    private void updateTypeX() {
        typeX = indexToPosition(container.getTypeIndex())[0];
    }

    /**
     * Since the up and down keys require the knowledge about what font metrics are
     * used, this is not implemented by KeyEventHandler, and thus handled right
     * here.
     * 
     * @param e key event
     */
    private void typeIndex(KeyEvent e) {
        int textheight = style.font.getSize() + style.textleading;
        int newi = container.getSelectStop();

        // Find the new index when pressing up arrow
        if (e.getKeyCode() == KeyEventHandler.UP) {
            newi = positionToIndex(typeX, -(textheight) + indexToPosition(container.getTypeIndex())[1]);

            // Find the new index when pressing down arrow
        } else if (e.getKeyCode() == KeyEventHandler.DOWN) {
            newi = positionToIndex(typeX, textheight + indexToPosition(container.getTypeIndex())[1]);

            // Otherwise update the x-position to which the up/down arrow will go.
        } else {
            updateTypeX();
            return;
        }

        // If shift is down, it means we're selecting text, so only update the select
        // stop index. Otherwise update the entire type index.
        if (e.isShiftDown()) {
            container.setSelectStop(newi);
        } else {
            container.setTypeIndex(newi);
        }
    }

    /**
     * Converts a position on screen to an index in the content of the container.
     * 
     * @param x x-pos
     * @param y y-pos
     * @return index in container
     */
    public int positionToIndex(int x, int y) {

        // Split the content of the container into lines
        String[] lines = getSplit();

        // Get the line index by dividing the y position minus the padding by the text
        // height. Also contrain the index to make sure no IndexOutOfBounds is thrown
        int textheight = style.font.getSize() + style.textleading;
        int line = (y - style.padding) / (textheight);
        line = Functions.constrain(line, 0, lines.length - 1);

        // Increment the index up to the found line, do +1 because we split it on "\n"
        // so that isn't part of the lines anymore.
        int index = 0;
        for (int i = 0; i < line; i++) {
            index += lines[i].length() + 1;
        }

        // Now calculate the index in the line by checking each character's x-position
        // until we passes the given x-position.
        int nx = 0;
        for (int i = 0; i < lines[line].length(); i++) {

            // Width of the current character
            int w = graphics.getFontMetrics().charWidth((lines[line].charAt(i)));

            // If the current width of the line + half the width of the current character +
            // paddingX is bigger than the x, we've found our index.
            if (nx + w / 2 + style.padding > x) {
                return index + i;
            }

            // Increament the width of this line by the character width
            nx += w;
        }

        // If the given x-position is even bigger, it means it's after the end of the
        // line, so add the entire length of the line and return.
        return index + lines[line].length();
    }

    /**
     * Converts an index in the container to a position on the screen.
     * 
     * @param index the index
     * @return position
     */
    public int[] indexToPosition(int index) {

        // Get the font metrics of the Graphics2D
        int textheight = style.font.getSize() + style.textleading;

        // Calculate the position using the index and the font metrics
        int ex = style.padding + stringWidth(getLineFromIndex(index));
        int ey = style.padding + getLineIndexFromIndex(index) * textheight;
        return new int[] { ex, ey };
    }

    /**
     * Returns the line where index is at in the content.
     * 
     * @param index the index in the content
     * @return the line of the content that contains that index
     */
    public String getLineFromIndex(int index) {

        // Get all the lines prior to the index
        String[] strings = getSplit(container.getContentAsString().substring(0, index));

        // Get the last line
        String lastline = strings[strings.length - 1];

        // Remove the added space
        return lastline.substring(0, lastline.length() - 1);
    }

    /**
     * Returns the line index give the index in the entire content.
     * 
     * @param index index in the content
     * @return index of the line
     */
    public int getLineIndexFromIndex(int index) {

        // Get all the lines prior to the given index
        String[] strings = getSplit(container.getContentAsString().substring(0, index));

        // Return the amount of lines
        return strings.length - 1;
    }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(int w, int h) {
        width = w;
        height = h;
    }

    public int getTypeX() {
        return typeIndexX;
    }

    public int getTypeY() {
        return typeIndexY;
    }

    public void focus() {
        typeline = 0;
        super.focus();
    }

    public void setContent(T t) {
        container.setContent(t.toString());
    }

    public class Style {
        public Color background = new Color(255, 255, 255);
        public Color selection = new Color(200, 220, 240);
        public Color color = new Color(0, 0, 0);
        public Font font = new Font("Roboto", Font.PLAIN, 16);
        public Wrap wrap = Wrap.NONE;
        public int padding = 5;
        public int textleading = 2;
    }

    public enum Wrap {
        CHAR(true), WORD(true), NONE(false);

        private boolean wrap;

        private Wrap(boolean r) {
            wrap = r;
        }

        public boolean wrap() {
            return wrap;
        }
    }

    public T getContent() {
        return container.getContent();
    }

    @Override
    public void mouseWheel(MouseWheelEvent event) {
        // TODO Auto-generated method stub

    }

}

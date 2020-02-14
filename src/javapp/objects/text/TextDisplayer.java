package javapp.objects.text;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.Timer;

import javapp.core.Canvas;
import javapp.core.S;
import javapp.objects.base.Typeable;

public class TextDisplayer extends Typeable {

    private int width;
    private int height;
    private int x = 100;
    private int y = 100;
    private int paddingX = 5;
    private int paddingY = 5;
    private int textleading = 2;

    private int typeX = 0;

    private int typeline = 0;

    private Font font;
    private Canvas canvas;

    private TextContainer container;

    /**
     * Create TextDisplayer.
     * 
     * @param x x-pos
     * @param y y-pos
     * @param w width
     * @param h height
     */
    public TextDisplayer(int x, int y, int w, int h) {
        this(x, y, w, h, new TextContainer());
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
    public TextDisplayer(int x, int y, int w, int h, TextContainer c) {
        this.container = c;
        this.font = new Font("Arial", Font.PLAIN, 24);
        this.width = w;
        this.height = h;
        this.x = x;
        this.y = y;
        this.canvas = new Canvas(width, height);

        new Timer(5, (a) -> typeline++).start();
    }

    /**
     * Returns the TextContainer.
     * 
     * @return container
     */
    public TextContainer getContainer() {
        return container;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public int getPaddingX() {
        return paddingX;
    }

    public int getPaddingY() {
        return paddingY;
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(canvas.getImage(), getX(), getY(), null);

        canvas.draw((g2d) -> {
            g2d.setFont(font);
            int textheight = font.getSize() + textleading;

            // Calculate the position of the type index
            int[] t = indexToPosition(container.getTypeIndex());
            int tx = t[0];
            int ty = t[1];

            // Calculate the position of the lowest select index
            int[] s = indexToPosition(container.lowestSelectIndex());
            int sx = s[0];
            int sy = s[1];

            // Calculate the position of the highest select index
            int[] e = indexToPosition(container.highestSelectIndex());
            int ex = e[0];
            int ey = e[1];

            // Display background
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);

            // Display selection
            g2d.setColor(new Color(200, 220, 240));
            if (sy == ey) {
                g2d.fillRect(sx, sy, ex - sx, textheight);
            } else {
                g2d.fillRect(sx, sy, width, textheight);
                g2d.fillRect(paddingX, sy + textheight, width, (ey - sy) - textheight);
                g2d.fillRect(paddingX, ey, ex - paddingX, textheight);
            }

            // Display text in lines
            g2d.setColor(Color.BLACK);
            String[] lines = container.getContent().split("\n");
            for (int i = 0; i < lines.length; i++) {
                g2d.drawString(lines[i], paddingX, (textheight) * (i + 1) - textleading);
            }

            g2d.setStroke(new BasicStroke(1));
            if (typeline < 100 && isFocused()) {
                g2d.drawLine(tx, ty, tx, ty + font.getSize());
            }
            if (typeline > 200) {
                typeline = 0;
            }
        });
        canvas.redraw();
    }

    @Override
    public void keyPress(KeyEvent e) {
        typeline = 0;
        KeyEventHandler.keyPress(e, container);
        typeIndex(e);
    }

    @Override
    public void keyRelease(KeyEvent e) {
        KeyEventHandler.keyRelease(e, container);
    }

    @Override
    public void keyType(KeyEvent e) {
        KeyEventHandler.keyType(e, container);
    }

    @Override
    public void drag(MouseEvent e) {
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
        int textheight = font.getSize() + textleading;
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
        String[] lines = (container.getContent() + " ").split("\n");

        // Get the line index by dividing the y position minus the padding by the text
        // height. Also contrain the index to make sure no IndexOutOfBounds is thrown
        int textheight = font.getSize() + textleading;
        int line = (y - paddingY) / (textheight);
        line = S.constrain(line, 0, lines.length - 1);

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
            int w = canvas.getGraphics().getFontMetrics().charWidth((lines[line].charAt(i)));

            // If the current width of the line + half the width of the current character +
            // paddingX is bigger than the x, we've found our index.
            if (nx + w / 2 + paddingX > x) {
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
        FontMetrics m = canvas.getGraphics().getFontMetrics();
        int textheight = font.getSize() + textleading;

        // Calculate the position using the index and the font metrics
        int ex = paddingX + m.stringWidth(container.getLineFromIndex(index));
        int ey = paddingY + container.getLineIndexFromIndex(index) * textheight;
        return new int[] { ex, ey };
    }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

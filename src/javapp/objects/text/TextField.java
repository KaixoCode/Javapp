package javapp.objects.text;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javapp.core.Canvas;
import javapp.objects.Typeable;

public class TextField implements Typeable {

    private TypingHandler content;

    private Font font;

    private Graphics2D graphics;

    private Canvas canvas;

    private int width;
    private int height;
    private int x;
    private int y;

    private boolean pressed = false;
    private boolean hovering = false;
    private boolean focused = false;

    private int textLeading;

    private int paddingX = 5;

    private int paddingY = 5;

    private int textSize;

    public TextField(int x, int y, int width) {
        this(x, y, width, new Font("Arial", Font.PLAIN, 24));
    }

    public TextField(int x, int y, int width, Font font) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = font.getSize() + paddingY * 2;
        content = new TypingHandler();
        canvas = new Canvas(this.width, this.height);
        this.font = font;
    }

    @Override
    public void mousePress(MouseEvent e) {
        pressed = true;

        content.typePos = getIndexFromCoords(e.getX(), e.getY());
        content.selectPos = getIndexFromCoords(e.getX(), e.getY());
    }

    @Override
    public void mouseClick(MouseEvent e) {

    }

    @Override
    public void mouseRelease(MouseEvent e) {
        pressed = false;
    }

    @Override
    public void mouseEnter() {
        hovering = true;
    }

    @Override
    public void mouseExit() {
        hovering = false;
    }

    @Override
    public void keyPress(KeyEvent e) {
        content.pressKey(e);
    }

    @Override
    public void keyRelease(KeyEvent e) {
        content.releaseKey(e);
    }

    @Override
    public void keyType(KeyEvent e) {
        content.typeKey(e);
    }

    @Override
    public boolean isPressed() {
        return pressed;
    }

    @Override
    public boolean isHovering() {
        return hovering;
    }

    @Override
    public boolean withinBounds(int x, int y) {
        return x > this.x && x < this.x + this.width && y > this.y && y < this.y + this.height;
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
    public int getCursor() {
        return Cursor.TEXT_CURSOR;
    }

    @Override
    public void draw(Graphics2D g) {
        graphics = canvas.getGraphics();

        g.drawImage(canvas.getImage(), x, y, null);

        canvas.draw((g2d) -> {
            g2d.setFont(font);
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);

            int[] coords = getCoordsFromIndex(content.typePos);
            if (coords[0] > width - paddingX * 2) {
                g2d.translate(-(coords[0] - width + paddingX * 2), 0);
            }

            int typeX = g2d.getFontMetrics().stringWidth(content.text.substring(0, content.typePos)) + paddingX;
            int selectX = g2d.getFontMetrics().stringWidth(content.text.substring(0, content.selectPos)) + paddingX;
            g2d.setColor(new Color(200, 220, 240));
            if (typeX < selectX) {
                g2d.fillRect(typeX, paddingY, selectX - typeX, height - paddingY * 2);
            } else {
                g2d.fillRect(selectX, paddingY, typeX - selectX, height - paddingY * 2);
            }

            int x = paddingX;
            int y = (height / 2) + (g2d.getFontMetrics().getHeight() / 4);
            g2d.setColor(Color.BLACK);
            g2d.drawString(content.text, x, y);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(1));
            if (focused) {
                g2d.drawLine(typeX, paddingY / 2, typeX, height - paddingY);
            }

            if (coords[0] > width - paddingX * 2) {
                g2d.translate((coords[0] - width + paddingX * 2), 0);
            }

            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRect(-1, -1, width, height);
        });

        canvas.redraw();

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
    public void mouseMove(MouseEvent e) {

    }

    @Override
    public void unfocus() {
        focused = false;
    }

    @Override
    public void focus() {
        focused = true;
    }

    @Override
    public boolean isFocused() {
        return focused;
    }

    // Get the index from the coords on the textarea
    private int getIndexFromCoords(double x, double y) {

        // Now store the line using the found line index as a String.
        String line = content.text;

        int index = 0;

        // Go through all the characters in the line to see which one
        // is closest to the given x. Also adjust for padding.
        double xoffset = paddingX * 2;
        for (int i = 0; i < line.length(); i++) {

            // Store the textWidth of current char
            double textW = graphics.getFontMetrics().charWidth(line.charAt(i));

            // If the xoffset + half of the letter width has surpassed
            // the given x position, return the found index.
            if (x < xoffset + textW) {
                return index;
            }

            // Else add the complete letter width to the xoffset
            // and increment the index
            xoffset += textW;
            index++;
        }

        // If the x and y are so big that it got to here, this will
        // return the last index of text.
        return index;
    }

    // Get the x, y on the pane using the index in the text
    private int[] getCoordsFromIndex(int i) {

        // Get all text before given index. Add a space to make
        // sure when the index is at the start of a line it doesn't
        // get removed when we split at "\n".
        String s = content.text.substring(0, constrain(i, 0, Math.max(content.text.length(), 0))) + " ";

        // Split into lines so we can use the textleading to calculate
        // the y position of the text
        String[] l = s.split("\n");
        double y = (l.length - 1) * textLeading;

        // The last String in the array is the line in which the index is
        // located, and we cut it at the given index, so the textWidth of
        // that String is the x position.
        double x = graphics.getFontMetrics().stringWidth(l[l.length - 1]);

        // Adjust for padding and the added " " to make up for the removed enter
        double xp = x + paddingX - graphics.getFontMetrics().stringWidth(" ");
        double yp = y + paddingY + (graphics.getFontMetrics().getAscent() + graphics.getFontMetrics().getDescent()) / 2
                - textSize / 2;

        // Return the vector
        return new int[] { (int) xp, (int) yp };
    }

    private int constrain(int a, int b, int c) {
        return Math.max(Math.min(a, c), b);
    }

    @Override
    public void drag(MouseEvent e) {
        content.typePos = getIndexFromCoords(e.getX(), e.getY());
    }

}

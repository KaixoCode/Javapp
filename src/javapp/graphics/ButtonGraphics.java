package javapp.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javapp.objects.base.Pressable;

public class ButtonGraphics extends PressableGraphics {

    public Font font;
    public Stroke stroke;
    public Color border;
    public Color fill;
    public Color color;
    public String text;

    public ButtonGraphics(Pressable c) {
        super(c);
        stroke = new BasicStroke(2);
        font = new Font("Roboto", Font.PLAIN, 16);
        border = Color.BLACK;
        fill = new Color(200, 200, 200);
        color = new Color(0, 0, 0);
        text = "Button";
    }

    public ButtonGraphics(Pressable c, String t) {
        super(c);
        stroke = new BasicStroke(2);
        font = new Font("Roboto", Font.PLAIN, 16);
        border = Color.BLACK;
        fill = new Color(200, 200, 200);
        color = new Color(0, 0, 0);
        text = t;
    }

    @Override
    public void drawPressed(Graphics2D g2d, int width, int height) {
        g2d.setColor(border.darker());
        g2d.setStroke(stroke);
        g2d.drawRect(0, 0, width, height);
        g2d.setColor(fill.darker());
        g2d.fillRect(1, 1, width, height);
        g2d.setFont(font);
        int x = (width / 2) - (g2d.getFontMetrics().stringWidth(text) / 2);
        int y = (height / 2) + (g2d.getFontMetrics().getHeight() / 4);
        g2d.setColor(color.brighter());
        g2d.drawString(text, x + 1, y + 1);
    }

    @Override
    public void drawHovering(Graphics2D g2d, int width, int height) {
        g2d.setColor(border.darker());
        g2d.setStroke(stroke);
        g2d.drawRect(0, 0, width, height);
        g2d.setColor(fill.darker());
        g2d.fillRect(0, 0, width, height);
        g2d.setFont(font);
        int x = (width / 2) - (g2d.getFontMetrics().stringWidth(text) / 2);
        int y = (height / 2) + (g2d.getFontMetrics().getHeight() / 4);
        g2d.setColor(color.darker());
        g2d.drawString(text, x, y);
    }

    @Override
    public void drawIdle(Graphics2D g2d, int width, int height) {
        g2d.setColor(border);
        g2d.setStroke(stroke);
        g2d.drawRect(0, 0, width, height);
        g2d.setColor(fill);
        g2d.fillRect(0, 0, width, height);
        g2d.setFont(font);
        int x = (width / 2) - (g2d.getFontMetrics().stringWidth(text) / 2);
        int y = (height / 2) + (g2d.getFontMetrics().getHeight() / 4);
        g2d.setColor(color);
        g2d.drawString(text, x, y);
    }

}

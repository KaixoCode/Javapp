package javapp.objects;

import java.awt.Graphics2D;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

import javapp.core.S;
import javapp.objects.base.Drawable;
import javapp.objects.base.Focusable;

public class Knob extends Focusable {

    private Robot bot;
    private boolean turning;
    private double speed;
    private double value;
    private int absMouseX;
    private int absMouseY;
    private int size;
    private int pressmouseY;

    private int x;
    private int y;

    public Knob(int x, int y) {
        visuals = (g2d) -> {

        };
    }

    private Drawable visuals;
    private ArrayList<Double> snapPoints;
    private double snapRange;
    private int intervals;

    public void removeSnapPoint(Double s) {
        snapPoints.remove(s);
    }

    public void addSnapPoint(Double s) {
        snapPoints.add(s);
    }

    public void setSnapRange(double s) {
        snapRange = s;
    }

    public void setSpeed(double s) {
        speed = s;
    }

    public void setInterval(int s) {
        intervals = s;
    }

    public double getValue() {
        if (intervals != -1) {
            return (double) (Math.floor(value * (intervals - 1))) / (double) (intervals - 1);
        }

        for (Double d : snapPoints) {
            if (Math.abs(value - d) < snapRange)
                return d;
        }

        return value;
    }

    public void setSize(int i) {
        size = i;
    }

    @Override
    public void draw(Graphics2D g2d) {
        visuals.draw(g2d);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (turning) {
            double sensitivity = speed;
            double add = ((pressmouseY - e.getY()) * sensitivity) / size;
            value = S.constrain(value + add, 0, 1);
            bot.mouseMove(absMouseX, absMouseY);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isHovering()) {
            turning = true;

            absMouseX = e.getLocationOnScreen().x;
            absMouseY = e.getLocationOnScreen().y;
            pressmouseY = e.getY();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        turning = false;
    }

    @Override
    public int getWidth() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getHeight() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getX() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getY() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setPosition(int x, int y) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered() {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited() {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getCursor() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void mouseWheel(MouseWheelEvent e) {
        if (isHovering()) {
            value = S.constrain(value + (double) e.getPreciseWheelRotation() * (double) 0.01, 0, 1);
        }
    }

    public void setValue(double d) {
        value = d;
    }

}

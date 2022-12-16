package ro.comanitza.platformer.entities;

import ro.comanitza.platformer.util.Constants;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {

    protected double x;
    protected double y;

    protected int width;
    protected int height;

    protected Rectangle2D.Double hitBox;


    public Entity (final double x, final double y, int width, int height) {
        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;
    }

    protected void initHitBox(final double x, final double y, double width, double height) {
        this.hitBox = new Rectangle2D.Double(x, y, width, height);
    }

    public Rectangle2D.Double getHitBox() {
        return hitBox;
    }

    protected void drawHitBox(Graphics g, int levelOffset) {

        g.setColor(Color.PINK);
        g.drawRect((int)hitBox.x - levelOffset, (int)hitBox.y, (int)hitBox.width, (int)hitBox.height);
    }
}

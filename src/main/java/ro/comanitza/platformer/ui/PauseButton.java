package ro.comanitza.platformer.ui;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class PauseButton {

    protected final int x;
    protected final int y;
    protected final int width;
    protected final int height;

    protected final Rectangle bounds;

    protected PauseButton(int x, int y, int width, int height) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        bounds = new Rectangle(x, y, width, height);
    }

    public abstract void update();

    public abstract void render(Graphics g);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}

package ro.comanitza.platformer.ui;

import ro.comanitza.platformer.util.Constants;
import ro.comanitza.platformer.util.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UrmButton extends PauseButton {

    private final BufferedImage[] images;

    private int index;

    private boolean mouseOver;
    private boolean mousePressed;

    protected UrmButton(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);


        images = loadImages(rowIndex);
    }

    private BufferedImage[] loadImages(int rowIndex) {

        BufferedImage[] imgs = new BufferedImage[3];

        BufferedImage temp = LoadSave.getUrmButtonsAtlas();

        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = temp.getSubimage(i * Constants.UI.UrmButtons.URM_DEFAULT_SIZE, rowIndex * Constants.UI.UrmButtons.URM_DEFAULT_SIZE, Constants.UI.UrmButtons.URM_DEFAULT_SIZE, Constants.UI.UrmButtons.URM_DEFAULT_SIZE);
        }

        return imgs;
    }

    @Override
    public void update() {

        if (mouseOver) {
            index = 1;
        } else if (mousePressed) {
            index = 2;
        } else {
            index = 0;
        }

    }

    @Override
    public void render(Graphics g) {

        g.drawImage(images[index],x, y, width, height, null);
    }

    public void resetBooleans () {
        mouseOver = false;
        mousePressed = false;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
}

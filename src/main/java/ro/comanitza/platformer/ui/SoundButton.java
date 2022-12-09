package ro.comanitza.platformer.ui;

import ro.comanitza.platformer.util.Constants;
import ro.comanitza.platformer.util.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SoundButton extends PauseButton {

    private final BufferedImage[][] buttons;


    private boolean mouseOver;
    private boolean mousePressed;
    private boolean muted;

    private int rowIndex;
    private int columnIndex;

    protected SoundButton(int x, int y, int width, int height) {
        super(x, y, width, height);

        buttons = loadImages();
    }

    @Override
    public void update() {


        if (muted) {
            rowIndex = 1;
        } else {
            rowIndex = 0;
        }

        if (mouseOver) {
            columnIndex = 1;
        } else if (mousePressed) {
            columnIndex = 2;
        } else {
            columnIndex = 0;
        }
    }

    @Override
    public void render(Graphics g) {

        g.drawImage(buttons[rowIndex][columnIndex], x, y, width, height, null);
    }

    private BufferedImage[][] loadImages () {

        BufferedImage[][] images = new BufferedImage[2][3];

        BufferedImage temp = LoadSave.getSoundButtons();

        for (int i = 0; i < images.length; i++) {

            for (int j = 0; j < images[0].length; j++) {

                images[i][j] = temp.getSubimage(j * Constants.UI.PauseButtons.SOUND_BUTTON_DEFAULT_SIZE, i * Constants.UI.PauseButtons.SOUND_BUTTON_DEFAULT_SIZE, Constants.UI.PauseButtons.SOUND_BUTTON_DEFAULT_SIZE, Constants.UI.PauseButtons.SOUND_BUTTON_DEFAULT_SIZE);
            }
        }

        return images;
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

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public void resetBooleans() {
        this.mousePressed = false;
        this.mouseOver = false;
    }
}

package ro.comanitza.platformer.ui;

import ro.comanitza.platformer.util.Constants;
import ro.comanitza.platformer.util.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class PausedOverlay {

    private final BufferedImage backGroundImage;

    private final int backGroundX;
    private final int backGroundY;
    private final int backGroundWidth;
    private final int backGroundHeight;

    private final SoundButton musicButton;
    private final SoundButton sfxButton;

    public PausedOverlay() {

        backGroundImage = LoadSave.getPauseMenuBackground();

        backGroundWidth = (int) (backGroundImage.getWidth() * Constants.Game.SCALE);
        backGroundHeight = (int) (backGroundImage.getHeight() * Constants.Game.SCALE);

        backGroundX = Constants.Game.GAME_WIDTH / 2 - backGroundWidth / 2;
        backGroundY = (int)(20 * Constants.Game.SCALE);

        musicButton = new SoundButton((int)(450 * Constants.Game.SCALE), (int)(140 * Constants.Game.SCALE), Constants.UI.PauseButtons.SOUND_BUTTON_DEFAULT_SIZE, Constants.UI.PauseButtons.SOUND_BUTTON_DEFAULT_SIZE);
        sfxButton = new SoundButton((int)(450 * Constants.Game.SCALE), (int)(186 * Constants.Game.SCALE), Constants.UI.PauseButtons.SOUND_BUTTON_DEFAULT_SIZE, Constants.UI.PauseButtons.SOUND_BUTTON_DEFAULT_SIZE);

    }

    public void update() {

        musicButton.update();
        sfxButton.update();
    }

    public void render(Graphics g) {

        g.drawImage(backGroundImage, backGroundX, backGroundY, backGroundWidth, backGroundHeight, null);

        musicButton.render(g);
        sfxButton.render(g);
    }



    public void mousePressed(MouseEvent e) {

        if (isButtonOver(e, musicButton)) {
            musicButton.setMousePressed(true);
        } else if (isButtonOver(e, sfxButton)) {
            sfxButton.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {

        if (isButtonOver(e, musicButton)) {

            if (musicButton.isMousePressed()) {
                musicButton.setMuted(!musicButton.isMuted());
            }

        } else if (isButtonOver(e, sfxButton)) {

            if (sfxButton.isMousePressed()) {
                sfxButton.setMuted(!sfxButton.isMuted());
            }
        }

        musicButton.resetBooleans();
        sfxButton.resetBooleans();
    }

    public void mouseMoved(MouseEvent e) {

        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);

        if (isButtonOver(e, musicButton)) {
            musicButton.setMouseOver(true);
        } else if (isButtonOver(e, sfxButton)) {
            sfxButton.setMouseOver(true);
        }
    }

    protected boolean isButtonOver(MouseEvent e, PauseButton button) {

        return button.getBounds().contains(e.getX(), e.getY());
    }
}

package ro.comanitza.platformer.ui;

import ro.comanitza.platformer.gamestates.GameState;
import ro.comanitza.platformer.gamestates.Playing;
import ro.comanitza.platformer.util.Constants;
import ro.comanitza.platformer.util.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static ro.comanitza.platformer.util.Utils.isButtonOver;

public class PausedOverlay {

    private final BufferedImage backGroundImage;

    private final int backGroundX;
    private final int backGroundY;
    private final int backGroundWidth;
    private final int backGroundHeight;

    private final SoundButton musicButton;
    private final SoundButton sfxButton;

    private final UrmButton menuButton;
    private final UrmButton replayButton;
    private final UrmButton unpauseButton;

    private final Playing playing;

    public PausedOverlay(Playing playing) {

        backGroundImage = LoadSave.getPauseMenuBackground();

        backGroundWidth = (int) (backGroundImage.getWidth() * Constants.Game.SCALE);
        backGroundHeight = (int) (backGroundImage.getHeight() * Constants.Game.SCALE);

        backGroundX = Constants.Game.GAME_WIDTH / 2 - backGroundWidth / 2;
        backGroundY = (int)(20 * Constants.Game.SCALE);

        musicButton = new SoundButton((int)(450 * Constants.Game.SCALE), (int)(140 * Constants.Game.SCALE), Constants.UI.PauseButtons.SOUND_BUTTON_DEFAULT_SIZE, Constants.UI.PauseButtons.SOUND_BUTTON_DEFAULT_SIZE);
        sfxButton = new SoundButton((int)(450 * Constants.Game.SCALE), (int)(186 * Constants.Game.SCALE), Constants.UI.PauseButtons.SOUND_BUTTON_DEFAULT_SIZE, Constants.UI.PauseButtons.SOUND_BUTTON_DEFAULT_SIZE);

        menuButton = new UrmButton((int)(313 * Constants.Game.SCALE), (int)(325 * Constants.Game.SCALE), Constants.UI.UrmButtons.URM_SIZE, Constants.UI.UrmButtons.URM_SIZE, 2);
        replayButton = new UrmButton((int)(387 * Constants.Game.SCALE), (int)(325 * Constants.Game.SCALE), Constants.UI.UrmButtons.URM_SIZE, Constants.UI.UrmButtons.URM_SIZE, 1);
        unpauseButton = new UrmButton((int)(462 * Constants.Game.SCALE), (int)(325 * Constants.Game.SCALE), Constants.UI.UrmButtons.URM_SIZE, Constants.UI.UrmButtons.URM_SIZE, 0);

        this.playing = playing;
    }

    public void update() {

        musicButton.update();
        sfxButton.update();

        menuButton.update();
        replayButton.update();
        unpauseButton.update();
    }

    public void render(Graphics g) {

        g.drawImage(backGroundImage, backGroundX, backGroundY, backGroundWidth, backGroundHeight, null);

        musicButton.render(g);
        sfxButton.render(g);

        menuButton.render(g);
        replayButton.render(g);
        unpauseButton.render(g);
    }



    public void mousePressed(MouseEvent e) {

        if (isButtonOver(e, musicButton)) {
            musicButton.setMousePressed(true);
        } else if (isButtonOver(e, sfxButton)) {
            sfxButton.setMousePressed(true);
        } else if (isButtonOver(e, menuButton)) {
            menuButton.setMousePressed(true);
        } else if (isButtonOver(e, replayButton)) {
            replayButton.setMousePressed(true);
        } else if (isButtonOver(e, unpauseButton)) {
            unpauseButton.setMousePressed(true);
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
        } else if (isButtonOver(e, menuButton)) {

            if (menuButton.isMousePressed()) {
                GameState.gameState = GameState.MENU;
                playing.unpauseGame();
            }
        } else if (isButtonOver(e, replayButton)) {

            if (replayButton.isMousePressed()) {
                playing.resetAll();
            }
        } else if (isButtonOver(e, unpauseButton)) {

            if (unpauseButton.isMousePressed()) {
                playing.unpauseGame();
            }
        }

        musicButton.resetBooleans();
        sfxButton.resetBooleans();

        menuButton.resetBooleans();
        unpauseButton.resetBooleans();
        replayButton.resetBooleans();
    }

    public void mouseMoved(MouseEvent e) {

        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);

        menuButton.setMouseOver(false);
        unpauseButton.setMouseOver(false);
        replayButton.setMouseOver(false);

        if (isButtonOver(e, musicButton)) {
            musicButton.setMouseOver(true);
        } else if (isButtonOver(e, sfxButton)) {
            sfxButton.setMouseOver(true);
        } else if (isButtonOver(e, menuButton)) {
            menuButton.setMouseOver(true);
        } else if (isButtonOver(e, unpauseButton)) {
            unpauseButton.setMouseOver(true);
        } else if (isButtonOver(e, replayButton)) {
            replayButton.setMouseOver(true);
        }
    }
}

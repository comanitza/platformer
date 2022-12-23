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

    private final UrmButton menuButton;
    private final UrmButton replayButton;
    private final UrmButton unpauseButton;

    private final AudioOptions audioOptions;

    private final Playing playing;

    public PausedOverlay(Playing playing) {

        backGroundImage = LoadSave.getPauseMenuBackground();

        backGroundWidth = (int) (backGroundImage.getWidth() * Constants.Game.SCALE);
        backGroundHeight = (int) (backGroundImage.getHeight() * Constants.Game.SCALE);

        backGroundX = Constants.Game.GAME_WIDTH / 2 - backGroundWidth / 2;
        backGroundY = (int)(20 * Constants.Game.SCALE);

        this.audioOptions = playing.getGame().getAudioOptions();

        menuButton = new UrmButton((int)(313 * Constants.Game.SCALE), (int)(325 * Constants.Game.SCALE), Constants.UI.UrmButtons.URM_SIZE, Constants.UI.UrmButtons.URM_SIZE, 2);
        replayButton = new UrmButton((int)(387 * Constants.Game.SCALE), (int)(325 * Constants.Game.SCALE), Constants.UI.UrmButtons.URM_SIZE, Constants.UI.UrmButtons.URM_SIZE, 1);
        unpauseButton = new UrmButton((int)(462 * Constants.Game.SCALE), (int)(325 * Constants.Game.SCALE), Constants.UI.UrmButtons.URM_SIZE, Constants.UI.UrmButtons.URM_SIZE, 0);

        this.playing = playing;
    }

    public void update() {



        menuButton.update();
        replayButton.update();
        unpauseButton.update();

        audioOptions.update();
    }

    public void render(Graphics g) {

        g.drawImage(backGroundImage, backGroundX, backGroundY, backGroundWidth, backGroundHeight, null);



        menuButton.render(g);
        replayButton.render(g);
        unpauseButton.render(g);

        audioOptions.render(g);
    }



    public void mousePressed(MouseEvent e) {

        if (isButtonOver(e, menuButton)) {
            menuButton.setMousePressed(true);
        } else if (isButtonOver(e, replayButton)) {
            replayButton.setMousePressed(true);
        } else if (isButtonOver(e, unpauseButton)) {
            unpauseButton.setMousePressed(true);
        } else {
            audioOptions.mousePressed(e);
        }
    }

    public void mouseReleased(MouseEvent e) {

        if (isButtonOver(e, menuButton)) {

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
        } else {
            audioOptions.mouseReleased(e);
        }

        menuButton.resetBooleans();
        unpauseButton.resetBooleans();
        replayButton.resetBooleans();
    }

    public void mouseMoved(MouseEvent e) {

        menuButton.setMouseOver(false);
        unpauseButton.setMouseOver(false);
        replayButton.setMouseOver(false);

        if (isButtonOver(e, menuButton)) {
            menuButton.setMouseOver(true);
        } else if (isButtonOver(e, unpauseButton)) {
            unpauseButton.setMouseOver(true);
        } else if (isButtonOver(e, replayButton)) {
            replayButton.setMouseOver(true);
        } else {
            audioOptions.mouseMoved(e);
        }
    }
}

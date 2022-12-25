package ro.comanitza.platformer.ui;

import ro.comanitza.platformer.gamestates.GameState;
import ro.comanitza.platformer.gamestates.Playing;
import ro.comanitza.platformer.util.Constants;
import ro.comanitza.platformer.util.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static ro.comanitza.platformer.util.Utils.isButtonOver;

public class LevelCompletedOverlay {

    private final Playing playing;

    private final UrmButton menuButton;
    private  final UrmButton nextButton;

    private final BufferedImage image;

    private final int imageX;
    private final int imageY;
    private final int imageW;
    private final int imageH;


    public LevelCompletedOverlay(Playing playing) {
        this.playing = playing;

        image = LoadSave.getLevelCompletedImage();

        imageW = (int)(image.getWidth() * Constants.Game.SCALE);
        imageH = (int)(image.getHeight() * Constants.Game.SCALE);

        imageX = (Constants.Game.GAME_WIDTH / 2) - (imageW / 2);
        imageY = (int)(80 * Constants.Game.SCALE);

        nextButton = new UrmButton((int)(330 * Constants.Game.SCALE), (int)(195 * Constants.Game.SCALE), Constants.UI.UrmButtons.URM_SIZE, Constants.UI.UrmButtons.URM_SIZE, 0);
        menuButton = new UrmButton((int)(445 * Constants.Game.SCALE), (int)(195 * Constants.Game.SCALE), Constants.UI.UrmButtons.URM_SIZE, Constants.UI.UrmButtons.URM_SIZE, 2);
    }

    public void update() {

        nextButton.update();
        menuButton.update();
    }

    public void render(Graphics g) {

        g.drawImage(image, imageX, imageY, imageW, imageH, null);

        nextButton.render(g);
        menuButton.render(g);
    }

    public void mouseMoved(MouseEvent e) {
        nextButton.setMouseOver(false);
        menuButton.setMouseOver(false);

        if (isButtonOver(e, nextButton)) {
            nextButton.setMouseOver(true);
        } else if (isButtonOver(e, menuButton)) {
            menuButton.setMouseOver(true);
        }
    }

    public void mousePressed(MouseEvent e) {

        if (isButtonOver(e, nextButton)) {
            nextButton.setMousePressed(true);
        } else if (isButtonOver(e, menuButton)) {
            menuButton.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {

        if (isButtonOver(e, nextButton)) {
            if (nextButton.isMousePressed()) {
                playing.getGame().getAudioPlayer().setLevelMusic(playing.getLevelManager().getCurrentLevelIndex());
                playing.loadNextLevel();
            }

        } else if (isButtonOver(e, menuButton)) {
            if (menuButton.isMousePressed()) {

                playing.resetAll();
                GameState.setGameState(GameState.MENU, playing.getGame());
            }
        }

        nextButton.resetBooleans();
        menuButton.resetBooleans();
    }
}

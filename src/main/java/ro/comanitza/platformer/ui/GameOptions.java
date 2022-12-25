package ro.comanitza.platformer.ui;

import ro.comanitza.platformer.core.Game;
import ro.comanitza.platformer.gamestates.GameState;
import ro.comanitza.platformer.gamestates.State;
import ro.comanitza.platformer.util.Constants;
import ro.comanitza.platformer.util.LoadSave;
import ro.comanitza.platformer.util.Utils;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GameOptions extends State {

    private final AudioOptions audioOptions;
    private final BufferedImage optionsBackgroundImage;

    private final int bgX;
    private final int bgY;
    private final int bgW;
    private final int bgH;

    private final UrmButton menuButton;

    public GameOptions(Game game) {
        super(game);

        this.audioOptions = game.getAudioOptions();

        optionsBackgroundImage = LoadSave.getOptionMenuBackground();

        bgW = (int)(optionsBackgroundImage.getWidth() * Constants.Game.SCALE);
        bgH = (int)(optionsBackgroundImage.getHeight() * Constants.Game.SCALE);
        bgX = Constants.Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int)(33 * Constants.Game.SCALE);

        menuButton = new UrmButton((int)(387 * Constants.Game.SCALE), (int)(325 * Constants.Game.SCALE), Constants.UI.UrmButtons.URM_SIZE, Constants.UI.UrmButtons.URM_SIZE, 2);
    }

    @Override
    public void update() {

        menuButton.update();
        audioOptions.update();
    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(optionsBackgroundImage, bgX, bgY, bgW, bgH, null);
        menuButton.render(g);
        audioOptions.render(g);
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (Utils.isButtonOver(e, menuButton)) {
            menuButton.setMousePressed(true);
        } else {
            audioOptions.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (Utils.isButtonOver(e, menuButton) && menuButton.isMousePressed()) {
            GameState.setGameState(GameState.MENU, getGame());
            menuButton.resetBooleans();
        } else {
            audioOptions.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        menuButton.setMouseOver(false);

        if (Utils.isButtonOver(e, menuButton)) {
            menuButton.setMouseOver(true);
        } else {
            audioOptions.mouseMoved(e);
        }
    }
}

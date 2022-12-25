package ro.comanitza.platformer.ui;

import ro.comanitza.platformer.core.Game;
import ro.comanitza.platformer.gamestates.GameState;
import ro.comanitza.platformer.util.Constants;
import ro.comanitza.platformer.util.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuButton {

    private final int x;
    private final int y;
    private final int rowIndex;
    private GameState gameState;

    private final int xOffsetCenter = Constants.UI.Buttons.WIDTH / 2;

    private int index;

    private boolean mouseIsOver;
    private  boolean mouseIsPressed;

    private Rectangle bounds;

    private final BufferedImage[] imageAtlas;

    public MenuButton(int x, int y, int rowIndex, GameState gameState) {

        this.x = x;
        this.y = y;
        this.rowIndex = rowIndex;
        this.gameState = gameState;

        this.imageAtlas = loadImages();

        bounds = new Rectangle(x - xOffsetCenter, y, Constants.UI.Buttons.WIDTH, Constants.UI.Buttons.HEIGHT);
    }

    private BufferedImage[] loadImages() {
        BufferedImage[] imgs = new BufferedImage[3];

        BufferedImage temp = LoadSave.getMenuAtlas();

        for (int i = 0; i < imgs.length; i++) {

            imgs[i] = temp.getSubimage(i * Constants.UI.Buttons.DEFAULT_WIDTH, rowIndex * Constants.UI.Buttons.DEFAULT_HEIGHT, Constants.UI.Buttons.DEFAULT_WIDTH, Constants.UI.Buttons.DEFAULT_HEIGHT);
        }

        return imgs;
    }

    public void render(Graphics g) {

        g.drawImage(imageAtlas[index], x - xOffsetCenter, y, Constants.UI.Buttons.WIDTH, Constants.UI.Buttons.HEIGHT, null);

    }

    public void update() {

        index = 0;

        if (mouseIsOver) {
            index = 1;
        } else if (mouseIsPressed) {
            index = 2;
        }
    }

    public boolean isMouseIsOver() {
        return mouseIsOver;
    }

    public boolean isMouseIsPressed() {
        return mouseIsPressed;
    }

    public void applyGameState(Game game) {
        GameState.setGameState(gameState, game);
    }


    public void resetBooleans() {
        mouseIsOver = false;
        mouseIsPressed = false;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setMouseIsOver(boolean mouseIsOver) {
        this.mouseIsOver = mouseIsOver;
    }

    public void setMouseIsPressed(boolean mouseIsPressed) {
        this.mouseIsPressed = mouseIsPressed;
    }

    public GameState getGameState() {
        return gameState;
    }
}

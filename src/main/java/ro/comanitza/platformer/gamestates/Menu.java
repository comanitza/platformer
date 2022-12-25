package ro.comanitza.platformer.gamestates;

import ro.comanitza.platformer.core.Game;
import ro.comanitza.platformer.ui.MenuButton;
import ro.comanitza.platformer.util.Constants;
import ro.comanitza.platformer.util.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Menu extends State {

    private MenuButton[] buttons = new MenuButton[3];
    private BufferedImage menuBackground;

    private int menuX;
    private int menuY;
    private int menuWidth;
    private int menuHeight;

    public Menu(Game game) {
        super(game);

        loadButtons();

        loadBackground();
    }

    private void loadButtons() {
        buttons[0] = new MenuButton(Constants.Game.GAME_WIDTH /2, (int) (150 * Constants.Game.SCALE), 0, GameState.PLAYING);
        buttons[1] = new MenuButton(Constants.Game.GAME_WIDTH /2, (int) (220 * Constants.Game.SCALE), 1, GameState.OPTION);
        buttons[2] = new MenuButton(Constants.Game.GAME_WIDTH /2, (int) (290 * Constants.Game.SCALE), 2, GameState.QUIT);
    }

    private void loadBackground() {
        menuBackground = LoadSave.getMenuBackground();

        menuWidth = (int) (menuBackground.getWidth() * Constants.Game.SCALE);
        menuHeight = (int) (menuBackground.getHeight() * Constants.Game.SCALE);
        menuX = (Constants.Game.GAME_WIDTH /2) - (menuWidth / 2);
        menuY = (int)(45 * Constants.Game.SCALE);
    }

    @Override
    public void update() {

        for (MenuButton b: buttons) {
            b.update();
        }
    }

    @Override
    public void draw(Graphics g) {

//        g.setColor(Color.BLACK);
//        g.drawString("MENU", Constants.Game.GAME_HEIGHT / 2, 200);

        g.drawImage(menuBackground, menuX, menuY, menuWidth, menuHeight, null);

        for (MenuButton b: buttons) {
            b.render(g);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            GameState.setGameState(GameState.PLAYING, getGame());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

        for (MenuButton b: buttons) {
            if (isButtonPressed(e, b)) {
                b.setMouseIsPressed(true);

                return;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        for (MenuButton b: buttons) {
            if (isButtonPressed(e, b) && b.isMouseIsPressed()) {

                b.applyGameState(getGame());

                break;
            }
        }

        resetButtons();


    }

    private void resetButtons() {

        for (MenuButton b: buttons) {

            b.resetBooleans();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        for (MenuButton b: buttons) {
            b.setMouseIsOver(false);
        }

        for (MenuButton b: buttons) {
            if (isButtonPressed(e, b)) {
                b.setMouseIsOver(true);

                break;
            }
        }

    }
}

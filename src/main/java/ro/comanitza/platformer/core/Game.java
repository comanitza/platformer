package ro.comanitza.platformer.core;

import ro.comanitza.platformer.gamestates.GameState;
import ro.comanitza.platformer.gamestates.Menu;
import ro.comanitza.platformer.gamestates.Playing;


import java.awt.*;

public class Game {

    private final GameWindow window;
    private final GamePanel gamePanel;

    private final Playing playing;
    private final Menu menu;

    public Game () {

        playing = new Playing(this);
        menu = new Menu(this);

        gamePanel = new GamePanel(this);
        window = new GameWindow(gamePanel);

        /*
         * this line should be under the GameWindow creation
         */
        gamePanel.requestFocus();

        /*
         * start the game loop
         */
        startGameLoop();
    }

    public void render(Graphics g) {

        switch (GameState.gameState) {

            case PLAYING:
                playing.draw(g);
                break;
            case MENU:
                menu.draw(g);
                break;
            default:
                break;
        }
    }

    private void startGameLoop () {

        new Thread(new GameLoop(gamePanel, playing.getPlayer(), playing.getLevelManager()), "game-loop-thread").start();
    }

//    public Player getPlayer() {
//        return player;
//    }

    public void windowFocusLost() {

        if (GameState.gameState == GameState.PLAYING) {
            playing.windowFocusLost();
        }
    }

    public Playing getPlaying() {
        return playing;
    }

    public Menu getMenu() {
        return menu;
    }
}

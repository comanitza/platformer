package ro.comanitza.platformer.core;

import ro.comanitza.platformer.entities.Player;
import ro.comanitza.platformer.levels.LevelManager;

import java.awt.*;

public class Game {

    private final GameWindow window;
    private final GamePanel gamePanel;

    private final Player player;
    private final LevelManager levelManager;


    public static final int TILES_DEFAULT_SIZE = 32;
    public static final int TILES_IN_WIDTH = 26;
    public static final int TILES_IN_HEIGHT = 14;
    public static final double SCALE = 1.5d;
    public static final int TILES_SIZE = (int)(TILES_DEFAULT_SIZE * SCALE);
    public static final int GAME_WIDTH = TILES_IN_WIDTH * TILES_SIZE;
    public static final int GAME_HEIGHT = TILES_IN_HEIGHT * TILES_SIZE;

    public Game () {
        player = new Player(200, 200);
        levelManager = new LevelManager(this);

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

        levelManager.render(g);
        player.render(g);
    }

    private void startGameLoop () {

        new Thread(new GameLoop(gamePanel, player, levelManager), "game-loop-thread").start();
    }

    public Player getPlayer() {
        return player;
    }

    public void windowFocusLost() {
        player.setLeft(false);
        player.setUp(false);
        player.setRight(false);
        player.setDown(false);
    }
}

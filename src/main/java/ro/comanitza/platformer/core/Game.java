package ro.comanitza.platformer.core;

import ro.comanitza.platformer.entities.Player;

import java.awt.*;

public class Game {

    private final GameWindow window;
    private final GamePanel gamePanel;

    private Player player;

    public Game () {
        player = new Player(200, 200);

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

        player.render(g);
    }

    private void startGameLoop () {

        new Thread(new GameLoop(gamePanel, player), "game-loop-thread").start();

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

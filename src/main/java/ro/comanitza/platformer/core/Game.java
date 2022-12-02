package ro.comanitza.platformer.core;

public class Game {

    private final GameWindow window;
    private final GamePanel gamePanel;

    public Game () {
        gamePanel = new GamePanel();
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

    private void startGameLoop () {

        new Thread(new GameLoop(gamePanel), "game-loop-thread").start();

    }
}

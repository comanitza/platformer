package ro.comanitza.platformer.core;

import ro.comanitza.platformer.entities.Player;
import ro.comanitza.platformer.gamestates.GameState;
import ro.comanitza.platformer.gamestates.Menu;
import ro.comanitza.platformer.levels.LevelManager;

public class GameLoop implements Runnable {

    private final int FPS = 120;
    private final int UPS = 200;

    private final GamePanel gamePanel;
    private final Player player;
    private final LevelManager levelManager;
    private final Menu menu;

    public GameLoop(final GamePanel gamePanel, final Player player, final LevelManager levelManager, final Menu menu) {

        this.gamePanel = gamePanel;
        this.player = player;
        this.levelManager = levelManager;
        this.menu = menu;
    }

    public void update() {

        switch (GameState.gameState) {
            case PLAYING:
                player.update();
                levelManager.update();
                break;
            case MENU:
                menu.update();
                break;
            case OPTION:
            case QUIT:
                System.exit(0);
            default:
                break;
        }
    }

    @Override
    public void run() {

        double timePerFrame = 1000000000d / FPS;
        double timePerUpdate = 1000000000d / UPS;

        long lastFrame = System.nanoTime();

        long previousTime = System.nanoTime();
        double deltaUpdate = 0;

        while (true) {

            long now = System.nanoTime();
            long currentTime = System.nanoTime();

            deltaUpdate += (currentTime - previousTime) / timePerUpdate;
            previousTime = currentTime;

            if (deltaUpdate >= 1d) {
                update();
                deltaUpdate--;
            }

            if (now - lastFrame >= timePerFrame) {

                gamePanel.repaint();
                lastFrame = now;
            }
        }
    }
}

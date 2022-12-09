package ro.comanitza.platformer.core;

import ro.comanitza.platformer.gamestates.GameState;
import ro.comanitza.platformer.gamestates.Menu;
import ro.comanitza.platformer.gamestates.Playing;

public class GameLoop implements Runnable {

    private final int FPS = 120;
    private final int UPS = 200;

    private final GamePanel gamePanel;
    private final Menu menu;

    private final Playing playing;

    public GameLoop(final GamePanel gamePanel, final Playing playing, final Menu menu) {

        this.gamePanel = gamePanel;
        this.menu = menu;
        this.playing = playing;
    }

    public void update() {

        switch (GameState.gameState) {
            case PLAYING:
                playing.update();
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

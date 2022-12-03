package ro.comanitza.platformer.core;

import ro.comanitza.platformer.entities.Player;

public class GameLoop implements Runnable {

    private final int FPS = 120;
    private final int UPS = 200;

    private final GamePanel gamePanel;
    private final Player player;

    public GameLoop(final GamePanel gamePanel, final Player player) {

        this.gamePanel = gamePanel;
        this.player = player;
    }

    public void update() {
        player.update();
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

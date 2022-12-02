package ro.comanitza.platformer.core;

public class GameLoop implements Runnable {

    private final GamePanel gamePanel;

    public GameLoop(final GamePanel gamePanel) {

        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {

        double timePerFrame = 1000000000d / 120;
        long lastFrame = System.nanoTime();

        while (true) {

            long now = System.nanoTime();
            if (now - lastFrame >= timePerFrame) {

                gamePanel.repaint();
                lastFrame = now;
            }
        }
    }
}

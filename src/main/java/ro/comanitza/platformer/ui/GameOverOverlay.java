package ro.comanitza.platformer.ui;

import ro.comanitza.platformer.gamestates.GameState;
import ro.comanitza.platformer.gamestates.Playing;
import ro.comanitza.platformer.util.Constants;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOverOverlay {

    private final Playing playing;

    public GameOverOverlay(Playing playing) {
        this.playing = playing;
    }

    public void render(Graphics g) {

        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Constants.Game.GAME_WIDTH, Constants.Game.GAME_HEIGHT);

        g.setColor(Color.WHITE);
        g.drawString("Game Over", Constants.Game.GAME_WIDTH / 2, 160);
        g.drawString("Press ESC to get to menu", Constants.Game.GAME_WIDTH / 2, 320);
    }

    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            playing.resetAll();
            GameState.gameState = GameState.MENU;
        }
    }
}

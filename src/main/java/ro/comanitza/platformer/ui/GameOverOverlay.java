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
        g.setFont(new Font("TimesRoman", Font.BOLD, 120));
        g.drawString("Game Over", (int)(Constants.Game.GAME_WIDTH * 0.35), 320);
        g.drawString("Press ESC to get to menu", (int)(Constants.Game.GAME_WIDTH * 0.08), 480);
    }

    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            playing.resetAll();
            GameState.setGameState(GameState.MENU, playing.getGame());
        }
    }
}

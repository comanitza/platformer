package ro.comanitza.platformer.ui;

import ro.comanitza.platformer.gamestates.Playing;
import ro.comanitza.platformer.util.Constants;

import java.awt.*;

public class InfoOverlay {

    private final Playing playing;

    public InfoOverlay(Playing playing) {
        this.playing = playing;
    }

    public void render(Graphics g) {

        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.BOLD, 80));
        g.drawString("Crazy Pirate Adventures", (int)(Constants.Game.GAME_WIDTH * 0.2), 200);
        g.setFont(new Font("TimesRoman", Font.BOLD, 60));
        g.drawString("A and D to move", (int)(Constants.Game.GAME_WIDTH * 0.2), 280);
        g.drawString("J to jump, K to attack", (int)(Constants.Game.GAME_WIDTH * 0.2), 360);
        g.drawString("Kill all the enemies to complete the level.", (int)(Constants.Game.GAME_WIDTH * 0.2), 440);
    }
}

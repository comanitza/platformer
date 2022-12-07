package ro.comanitza.platformer.gamestates;

import ro.comanitza.platformer.core.Game;
import ro.comanitza.platformer.util.Constants;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Menu extends State {

    public Menu(Game game) {
        super(game);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {

        g.setColor(Color.BLACK);
        g.drawString("MENU", Constants.Game.GAME_HEIGHT / 2, 200);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            GameState.gameState = GameState.PLAYING;
        }
    }
}

package ro.comanitza.platformer.input;

import ro.comanitza.platformer.core.GamePanel;
import ro.comanitza.platformer.gamestates.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs implements KeyListener {

    private final GamePanel gamePanel;

    public KeyboardInputs (GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        System.out.println("create KeyboardInputs " + this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

        System.out.println("key typed: " + e);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (GameState.gameState) {
            case PLAYING -> gamePanel.getGame().getPlaying().keyPressed(e);
            case MENU -> gamePanel.getGame().getMenu().keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        switch (GameState.gameState) {
            case PLAYING -> gamePanel.getGame().getPlaying().keyReleased(e);
            case MENU -> gamePanel.getGame().getMenu().keyReleased(e);
        }
    }
}

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

//        switch (e.getKeyCode()) {
//            case KeyEvent.VK_W -> gamePanel.getGame().getPlayer().setUp(true);
//            case KeyEvent.VK_A -> gamePanel.getGame().getPlayer().setLeft(true);
//            case KeyEvent.VK_S -> gamePanel.getGame().getPlayer().setDown(true);
//            case KeyEvent.VK_D -> gamePanel.getGame().getPlayer().setRight(true);
//            case KeyEvent.VK_J -> gamePanel.getGame().getPlayer().setAttacking(true);
//            case KeyEvent.VK_L -> gamePanel.getGame().getPlayer().setJump(true);
//        }

        switch (GameState.gameState) {
            case PLAYING -> gamePanel.getGame().getPlaying().keyPressed(e);
            case MENU -> gamePanel.getGame().getMenu().keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

//        switch (e.getKeyCode()) {
//            case KeyEvent.VK_W -> gamePanel.getGame().getPlayer().setUp(false);
//            case KeyEvent.VK_A -> gamePanel.getGame().getPlayer().setLeft(false);
//            case KeyEvent.VK_S -> gamePanel.getGame().getPlayer().setDown(false);
//            case KeyEvent.VK_D -> gamePanel.getGame().getPlayer().setRight(false);
//            case KeyEvent.VK_J -> gamePanel.getGame().getPlayer().setAttacking(false);
//            case KeyEvent.VK_L -> gamePanel.getGame().getPlayer().setJump(false);
//        }

        switch (GameState.gameState) {
            case PLAYING -> gamePanel.getGame().getPlaying().keyReleased(e);
            case MENU -> gamePanel.getGame().getMenu().keyReleased(e);
        }
    }
}

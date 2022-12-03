package ro.comanitza.platformer.input;

import ro.comanitza.platformer.core.GamePanel;

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

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> gamePanel.getGame().getPlayer().setUp(true);
            case KeyEvent.VK_A -> gamePanel.getGame().getPlayer().setLeft(true);
            case KeyEvent.VK_S -> gamePanel.getGame().getPlayer().setDown(true);
            case KeyEvent.VK_D -> gamePanel.getGame().getPlayer().setRight(true);
            case KeyEvent.VK_J -> gamePanel.getGame().getPlayer().setAttacking(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> gamePanel.getGame().getPlayer().setUp(false);
            case KeyEvent.VK_A -> gamePanel.getGame().getPlayer().setLeft(false);
            case KeyEvent.VK_S -> gamePanel.getGame().getPlayer().setDown(false);
            case KeyEvent.VK_D -> gamePanel.getGame().getPlayer().setRight(false);
            case KeyEvent.VK_J -> gamePanel.getGame().getPlayer().setAttacking(false);
        }
    }
}

package ro.comanitza.platformer.input;

import ro.comanitza.platformer.core.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static ro.comanitza.platformer.util.Constants.Directions.*;

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

//        System.out.println("key pressed: " + e);

        switch (e.getKeyCode()) {

            case KeyEvent.VK_W:
//                System.out.println("UP");
//                gamePanel.changeYDelta(-5);
                gamePanel.setDirection(UP);
                break;
            case KeyEvent.VK_A:
//                System.out.println("LEFT");
//                gamePanel.changeXDelta(-5);
                gamePanel.setDirection(LEFT);
                break;
            case KeyEvent.VK_S:
//                System.out.println("DOWN");
//                gamePanel.changeYDelta(5);
                gamePanel.setDirection(DOWN);
                break;
            case KeyEvent.VK_D:
//                System.out.println("RIGHT");
//                gamePanel.changeXDelta(5);
                gamePanel.setDirection(RIGHT);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D -> gamePanel.setMoving(false);
        }
    }
}

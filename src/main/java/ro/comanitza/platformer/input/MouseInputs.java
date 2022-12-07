package ro.comanitza.platformer.input;

import ro.comanitza.platformer.core.GamePanel;
import ro.comanitza.platformer.gamestates.GameState;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputs implements MouseListener, MouseMotionListener {

    private final GamePanel gamePanel;

    public MouseInputs(final GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

//        System.out.println("mouse clicked " + e);
//        gamePanel.getGame().getPlayer().setRectPosition(e.getX(), e.getY());


        switch (GameState.gameState) {
            case MENU -> gamePanel.getGame().getMenu().mouseClicked(e);
            case PLAYING -> gamePanel.getGame().getPlaying().mouseClicked(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
//        gamePanel.setRectPosition(e.getX(), e.getY());
    }
}

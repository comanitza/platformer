package ro.comanitza.platformer.gamestates;

import ro.comanitza.platformer.core.Game;
import ro.comanitza.platformer.ui.MenuButton;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class State implements StateAware {

    private final Game game;

    public State(final Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public abstract void update();

    @Override
    public abstract void draw(Graphics g);

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public boolean isButtonPressed(MouseEvent e, MenuButton button) {

        return button.getBounds().contains(e.getX(), e.getY());
    }
}

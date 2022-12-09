package ro.comanitza.platformer.gamestates;

import ro.comanitza.platformer.core.Game;
import ro.comanitza.platformer.entities.Player;
import ro.comanitza.platformer.levels.LevelManager;
import ro.comanitza.platformer.ui.PausedOverlay;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static ro.comanitza.platformer.util.Constants.Game.*;

public class Playing extends State {

    private final Player player;
    private final LevelManager levelManager;

    private boolean paused = true;
    private final PausedOverlay pausedOverlay;

    public Playing(final Game game) {
        super(game);

        levelManager = new LevelManager(game);

        player = new Player(200, 200, (int)(64 * SCALE), (int)(40 * SCALE));
        player.loadLevelData(levelManager.getCurrentLevel().getLevelData());

        pausedOverlay = new PausedOverlay();
    }

    @Override
    public void update() {
        levelManager.update();
        player.update();
        pausedOverlay.update();
    }

    @Override
    public void draw(Graphics g) {
        levelManager.render(g);
        player.render(g);

        pausedOverlay.render(g);
    }


    public void windowFocusLost() {

        player.setLeft(false);
        player.setUp(false);
        player.setRight(false);
        player.setDown(false);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> player.setUp(true);
            case KeyEvent.VK_A -> player.setLeft(true);
            case KeyEvent.VK_S -> player.setDown(true);
            case KeyEvent.VK_D -> player.setRight(true);
            case KeyEvent.VK_J -> player.setAttacking(true);
            case KeyEvent.VK_L -> player.setJump(true);
            case KeyEvent.VK_ESCAPE -> GameState.gameState = GameState.MENU;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> player.setUp(false);
            case KeyEvent.VK_A -> player.setLeft(false);
            case KeyEvent.VK_S -> player.setDown(false);
            case KeyEvent.VK_D -> player.setRight(false);
            case KeyEvent.VK_J -> player.setAttacking(false);
            case KeyEvent.VK_L -> player.setJump(false);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (paused) {
            pausedOverlay.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (paused) {
            pausedOverlay.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        if (paused) {
            pausedOverlay.mouseMoved(e);
        }
    }
}

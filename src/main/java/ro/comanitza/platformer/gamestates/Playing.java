package ro.comanitza.platformer.gamestates;

import ro.comanitza.platformer.core.Game;
import ro.comanitza.platformer.entities.Player;
import ro.comanitza.platformer.levels.LevelManager;
import ro.comanitza.platformer.ui.PausedOverlay;
import ro.comanitza.platformer.util.Constants;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static ro.comanitza.platformer.util.Constants.Game.*;

public class Playing extends State {

    private final Player player;
    private final LevelManager levelManager;

    private boolean paused;
    private final PausedOverlay pausedOverlay;

    private int levelOffset;
    private int leftBorder = (int)(0.2 * GAME_WIDTH);
    private int rightBorder = (int)(0.8 * GAME_WIDTH);
    private final int levelTilesWidth;//getLevelManager().getCurrentLevel().getLevelData()[0].length;
    private final int maxTilesOffset;
    private final int maxTilesOffsetInPixels;

    public Playing(final Game game) {
        super(game);

        levelManager = new LevelManager(game);

        player = new Player(200, 200, (int)(64 * SCALE), (int)(40 * SCALE));
        player.loadLevelData(levelManager.getCurrentLevel().getLevelData());

        pausedOverlay = new PausedOverlay(this);

        levelTilesWidth = getLevelManager().getCurrentLevel().getLevelData()[0].length;

        maxTilesOffset = levelTilesWidth - TILES_IN_WIDTH;
        maxTilesOffsetInPixels = maxTilesOffset * TILES_SIZE;
    }

    @Override
    public void update() {

        if (paused) {
            pausedOverlay.update();
        } else {
            levelManager.update();
            player.update();
            checkCloseToBorder();
        }
    }

    private void checkCloseToBorder() {

        int playerX = (int) player.getHitBox().x;
        int diff = playerX - levelOffset;

        if (diff > rightBorder) {

            levelOffset += diff - rightBorder;
        } else if (diff < leftBorder) {
            levelOffset += diff - leftBorder;
        }

        if (levelOffset > maxTilesOffsetInPixels) {
            levelOffset = maxTilesOffsetInPixels;
        }

        if (levelOffset < 0) {
            levelOffset = 0;
        }
    }

    @Override
    public void draw(Graphics g) {
        levelManager.render(g, levelOffset);
        player.render(g, levelOffset);

        if (paused) {
            g.setColor(new Color(0, 0, 0, 95));
            g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

            pausedOverlay.render(g);
        }
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
            case KeyEvent.VK_ESCAPE -> paused = !paused;
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

    public void unpauseGame () {
        paused = false;
    }
}

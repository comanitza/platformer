package ro.comanitza.platformer.gamestates;

import ro.comanitza.platformer.core.Game;
import ro.comanitza.platformer.entities.EnemyManager;
import ro.comanitza.platformer.entities.Player;
import ro.comanitza.platformer.items.ItemsManager;
import ro.comanitza.platformer.levels.LevelManager;
import ro.comanitza.platformer.ui.GameOverOverlay;
import ro.comanitza.platformer.ui.InfoOverlay;
import ro.comanitza.platformer.ui.LevelCompletedOverlay;
import ro.comanitza.platformer.ui.PausedOverlay;
import ro.comanitza.platformer.util.Constants;
import ro.comanitza.platformer.util.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import static ro.comanitza.platformer.util.Constants.Game.*;

public class Playing extends State {

    private final Player player;
    private final LevelManager levelManager;
    private final EnemyManager enemyManager;

    private boolean paused;
    private final PausedOverlay pausedOverlay;

    private int levelOffset;
    private int leftBorder = (int)(0.2 * GAME_WIDTH);
    private int rightBorder = (int)(0.8 * GAME_WIDTH);

    private final BufferedImage backGroundImage = LoadSave.getPlayingBackground();
    private final BufferedImage bigCloudsImage = LoadSave.getBigClouds();

    private boolean gameOver;

    private final GameOverOverlay gameOverOverlay;
    private final LevelCompletedOverlay levelCompletedOverlay;
    private final InfoOverlay infoOverlay;

    private boolean levelCompleted;

    private final ItemsManager itemsManager;
    private long infoOverLayStart = 0;

    public Playing(final Game game) {
        super(game);

        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);

        player = new Player(200, 200, (int)(64 * SCALE), (int)(40 * SCALE), this);
        player.loadLevelData(levelManager.getCurrentLevel().getLevelData());

        pausedOverlay = new PausedOverlay(this);
        infoOverlay = new InfoOverlay(this);

        gameOverOverlay = new GameOverOverlay(this);
        levelCompletedOverlay = new LevelCompletedOverlay(this);

        enemyManager.loadCrabs(levelManager.getCurrentLevel());
        enemyManager.loadSharkies(levelManager.getCurrentLevel());
        enemyManager.loadBlackPirates(levelManager.getCurrentLevel());

        itemsManager = new ItemsManager(this);
    }

    @Override
    public void update() {

        if (gameOver) {
            return;
        }

        if (paused) {
            pausedOverlay.update();
        } else if (levelCompleted) {
            levelCompletedOverlay.update();
        } else {
            levelManager.update();
            player.update();
            itemsManager.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData());
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

        if (levelOffset > levelManager.getCurrentLevel().getLevelOffset()) {
            levelOffset = levelManager.getCurrentLevel().getLevelOffset();
        }

        if (levelOffset < 0) {
            levelOffset = 0;
        }
    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(backGroundImage, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);

        for (int i = 0; i < 8; i++) {
            g.drawImage(bigCloudsImage, i * Constants.Environment.BIG_CLOUD_WIDTH - (int)(levelOffset * 0.4), (int) (204 * SCALE), Constants.Environment.BIG_CLOUD_WIDTH, Constants.Environment.BIG_CLOUD_HEIGHT, null);
        }

        levelManager.render(g, levelOffset);
        player.render(g, levelOffset);
        enemyManager.render(g, levelOffset);
        itemsManager.render(g, levelOffset);


        if (infoOverLayStart == 0) {
            infoOverLayStart = System.currentTimeMillis();
        }

        if (infoOverLayStart + 5000 >= System.currentTimeMillis()) {
            infoOverlay.render(g);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g.drawString("A and D to move, J to jump and K to attack. Kill all the enemies to complete the level. | Level " + (levelManager.getCurrentLevelIndex() + 1), 20, GAME_HEIGHT - 40);
        g.drawString("Crazy Pirate Adventures by Stefan Comanita, github.com/comanitza, inspired by github.com/KaarinGaming ", 20, GAME_HEIGHT - 20);

        if (paused) {
            g.setColor(new Color(0, 0, 0, 120));
            g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

            pausedOverlay.render(g);
        } else if (gameOver) {
            gameOverOverlay.render(g);
        } else if (levelCompleted) {
            levelCompletedOverlay.render(g);
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

        if (gameOver) {
            gameOverOverlay.keyPressed(e);

            return;
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> player.setUp(true);
            case KeyEvent.VK_A -> player.setLeft(true);
            case KeyEvent.VK_S -> player.setDown(true);
            case KeyEvent.VK_D -> player.setRight(true);
            case KeyEvent.VK_J -> player.setJump(true);
            case KeyEvent.VK_K -> player.setAttacking(true);
            case KeyEvent.VK_ESCAPE -> paused = !paused;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (gameOver) {
            gameOverOverlay.keyPressed(e);

            return;
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> player.setUp(false);
            case KeyEvent.VK_A -> player.setLeft(false);
            case KeyEvent.VK_S -> player.setDown(false);
            case KeyEvent.VK_D -> player.setRight(false);
            case KeyEvent.VK_J -> player.setJump(false);
            case KeyEvent.VK_K -> player.setAttacking(false);
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

        if (gameOver) {
            return;
        }

        if (paused) {
            pausedOverlay.mousePressed(e);
        } else if (levelCompleted) {
            levelCompletedOverlay.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (gameOver) {
            return;
        }

        if (paused) {
            pausedOverlay.mouseReleased(e);
        } else if (levelCompleted) {
            levelCompletedOverlay.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        if (gameOver) {
            return;
        }

        if (paused) {
            pausedOverlay.mouseMoved(e);
        } else if (levelCompleted) {
            levelCompletedOverlay.mouseMoved(e);
        }
    }

    public void unpauseGame () {
        paused = false;
    }

    public void resetAll() {

        gameOver = false;
        paused = false;
        levelCompleted = false;

        player.reset();
        enemyManager.resetAllEnemies();

        itemsManager.resetAllItems();
    }

    public void loadNextLevel() {
        resetAll();
        levelManager.loadNextLevel();
        itemsManager.loadNextLevel();
    }

    public void checkEnemyHit() {
        enemyManager.checkEnemyHit(player.getAttackBox());
    }

    public void setGameOver(boolean b) {
        this.gameOver = b;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public void setLevelOffset(int levelOffset) {
        this.levelOffset = levelOffset;
    }

    public void setLevelCompleted(boolean b) {
        this.levelCompleted = b;
    }

    public ItemsManager getItemsManager() {
        return itemsManager;
    }
}

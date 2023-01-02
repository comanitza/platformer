package ro.comanitza.platformer.entities;

import ro.comanitza.platformer.audio.AudioPlayer;
import ro.comanitza.platformer.gamestates.Playing;
import ro.comanitza.platformer.levels.Level;
import ro.comanitza.platformer.util.Constants;
import ro.comanitza.platformer.util.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ro.comanitza.platformer.util.Constants.Enemy.*;
import static ro.comanitza.platformer.util.Constants.Game.SCALE;

public class EnemyManager {

    private Playing playing;
    private final BufferedImage[][] crabbyImages;
    private final BufferedImage[][] sharkyImages;
    private final BufferedImage[][] blackPirateImages;

    private List<Crabby> crabbies = Collections.emptyList();
    private List<Sharky> sharkies = Collections.emptyList();
    private List<BlackPirate> blackPirates = Collections.emptyList();

    public EnemyManager(Playing playing) {
        this.playing = playing;

        crabbyImages = loadCrabbyImages();
        sharkyImages = loadSharkyImages();
        blackPirateImages = loadBlackPirateImages();
    }

    private BufferedImage[][] loadCrabbyImages() {

        BufferedImage[][] imgs = new BufferedImage[5][9];

        BufferedImage temp = LoadSave.getCrabbyEnemy();

        for (int i = 0; i < imgs.length; i++) {
            for (int j = 0; j < imgs[i].length; j++) {
                imgs[i][j] = temp.getSubimage(j * Constants.Enemy.CRABBY_WIDTH_DEFAULT, i * Constants.Enemy.CRABBY_HEIGHT_DEFAULT, Constants.Enemy.CRABBY_WIDTH_DEFAULT, Constants.Enemy.CRABBY_HEIGHT_DEFAULT);
            }
        }

        return imgs;
    }

    private BufferedImage[][] loadBlackPirateImages() {

        BufferedImage bufferedImage = LoadSave.getBlackPirate();

        BufferedImage[][] imgs = new BufferedImage[7][8];

        for (int j = 0; j < imgs.length; j++) {
            for (int i = 0; i < imgs[j].length; i++) {
                imgs[j][i] = bufferedImage.getSubimage(i * 64, j * 40, 64, 40);
            }
        }

        return imgs;
    }

    private BufferedImage[][] loadSharkyImages() {

        BufferedImage[][] imgs = new BufferedImage[5][8];

        BufferedImage temp = LoadSave.getSharkyEnemy();

        for (int i = 0; i < imgs.length; i++) {

            for (int j = 0; j < imgs[i].length; j++) {

                imgs[i][j] = temp.getSubimage(j * Constants.Enemy.SHARKY_WIDTH_DEFAULT, i * Constants.Enemy.SHARKY_HEIGHT_DEFAULT, Constants.Enemy.SHARKY_WIDTH_DEFAULT, Constants.Enemy.SHARKY_HEIGHT_DEFAULT);
            }
        }

        return imgs;
    }

    public void loadCrabs(Level level) {
        this.crabbies = level.getCrabs();
    }

    public void loadSharkies(Level level) {
        this.sharkies = level.getSharkies();
    }

    public void loadBlackPirates(Level level) {
        this.blackPirates = level.getBlackPirates();
    }

    public void update(int[][] levelData) {

        boolean isAnyActive = false;

        for(Crabby c: crabbies) {

            if (!c.isActive()) {
                continue;
            }

            c.update(levelData, playing.getPlayer());

            isAnyActive = true;
        }

        for (Sharky s: sharkies) {

            if (!s.isActive()) {
                continue;
            }

            s.update(levelData, playing.getPlayer());

            isAnyActive = true;
        }

        for (BlackPirate b: blackPirates) {

            if (!b.isActive()) {
                continue;
            }

            b.update(levelData, playing.getPlayer());

            isAnyActive = true;
        }

        if (!isAnyActive) {
            playing.getGame().getAudioPlayer().playEffect(AudioPlayer.LEVEL_COMPLETED);
            playing.setLevelCompleted(true);
        }
    }

    public void render(Graphics g, int levelOffset) {

        for(Crabby c: crabbies) {

            if (!c.isActive()) {
                continue;
            }

            g.drawImage(crabbyImages[c.getEnemyState()][c.getAnimationIndex()], ((int)(c.hitBox.x - CRRABY_X_OFFSET)) - levelOffset + c.flipX(), (int)(c.hitBox.y - CRRABY_Y_OFFSET), Constants.Enemy.CRABBY_WIDTH * c.flipW(), Constants.Enemy.CRABBY_HEIGHT, null);

            /*
             * draw hitbox
             */
            g.setColor(Color.MAGENTA);
            g.drawRect((int)c.getHitBox().x - levelOffset, (int)c.getHitBox().y, (int)c.getHitBox().width, (int)c.getHitBox().height);

            g.setColor(Color.GREEN);
            g.drawRect((int)(c.getAttackBox().x) - levelOffset, (int)c.getAttackBox().y, (int)c.getAttackBox().width, (int)c.getAttackBox().height);
        }

        for (Sharky s: sharkies) {

            if (!s.isActive()) {
                continue;
            }

            g.drawImage(sharkyImages[s.getEnemyState()][s.getAnimationIndex()],
                    (((int)(s.getHitBox().getX() - SHARKY_X_OFFSET)) - levelOffset + s.flipX()),
                    (int)(s.getHitBox().getY() - SHARKY_Y_OFFSET),
                    SHARKY_WIDTH * s.flipW(),
                    SHARKY_HEIGHT, null);

            /*
             * draw hitbox
             */
            g.setColor(Color.MAGENTA);
            g.drawRect((int)s.getHitBox().x - levelOffset, (int)s.getHitBox().y, (int)s.getHitBox().width, (int)s.getHitBox().height);

            g.setColor(Color.GREEN);
            g.drawRect((int)(s.getAttackBox().x) - levelOffset, (int)s.getAttackBox().y, (int)s.getAttackBox().width, (int)s.getAttackBox().height);
        }

        for (BlackPirate b: blackPirates) {

            if (!b.isActive()) {
                continue;
            }

            g.drawImage(blackPirateImages[convertToBlackPirate(b.getEnemyState())][b.getAnimationIndex()],
                    (((int)(b.getHitBox().getX() - BLACK_PIRATE_X_OFFSET)) - levelOffset + b.flipX()),
                    (int)(b.getHitBox().getY() - BLACK_PIRATE_Y_OFFSET),
                    (int)(64 * SCALE) * b.flipW(),
                    (int)(40 * SCALE),
                    null);

            /*
             * draw hitbox
             */
            g.setColor(Color.MAGENTA);
            g.drawRect((int)b.getHitBox().x - levelOffset, (int)b.getHitBox().y, (int)b.getHitBox().width, (int)b.getHitBox().height);

            g.setColor(Color.GREEN);
            g.drawRect((int)(b.getAttackBox().x) - levelOffset, (int)b.getAttackBox().y, (int)b.getAttackBox().width, (int)b.getAttackBox().height);
        }
    }

    private int convertToBlackPirate(int enemyState) {
            return switch (enemyState) {
                case IDLE -> Constants.Player.IDLE;
                case RUNNING -> Constants.Player.RUNNING;
                case ATTACK -> Constants.Player.ATTACK_1;
                case HIT -> Constants.Player.HIT;
                case DEAD -> Constants.Player.DEAD;
                default -> 1;
            };
    }

    public void checkEnemyHit(Rectangle2D.Double playerAttackBox) {

        for (Crabby c: crabbies) {

            if (!c.isActive()) {
                continue;
            }

            if (playerAttackBox.intersects(c.getHitBox())) {

                c.hurt(10);
                return;
            }
        }

        for (Sharky s: sharkies) {

            if (!s.isActive()) {
                continue;
            }

            if (playerAttackBox.intersects(s.getHitBox())) {

                s.hurt(10);
                return;
            }
        }

        for (BlackPirate b: blackPirates) {

            if (!b.isActive()) {
                continue;
            }

            if (playerAttackBox.intersects(b.getHitBox())) {

                b.hurt(10);
                return;
            }

        }
    }

    public void resetAllEnemies() {

        for (Crabby c: crabbies) {
            c.reset();
        }

        for (Sharky s: sharkies) {
            s.reset();
        }
    }
}

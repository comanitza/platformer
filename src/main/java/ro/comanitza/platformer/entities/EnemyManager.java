package ro.comanitza.platformer.entities;

import ro.comanitza.platformer.audio.AudioPlayer;
import ro.comanitza.platformer.gamestates.Playing;
import ro.comanitza.platformer.levels.Level;
import ro.comanitza.platformer.util.Constants;
import ro.comanitza.platformer.util.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;

import static ro.comanitza.platformer.util.Constants.Enemy.*;

public class EnemyManager {

    private Playing playing;
    private final BufferedImage[][] crabbyImages;
    private final BufferedImage[][] sharkyImages;

    private List<Crabby> crabbies = Collections.emptyList();
    private List<Sharky> sharkies = Collections.emptyList();

    public EnemyManager(Playing playing) {
        this.playing = playing;

        crabbyImages = loadCrabbyImages();
        sharkyImages = loadSharkyImages();
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

            g.drawImage(sharkyImages[s.getEnemyState()][s.getAnimationIndex()], (((int)(s.getHitBox().getX() - SHARKY_X_OFFSET)) - levelOffset + s.flipX()),(int)(s.getHitBox().getY() - SHARKY_Y_OFFSET), SHARKY_WIDTH * s.flipW(), SHARKY_HEIGHT, null);

            /*
             * draw hitbox
             */
            g.setColor(Color.MAGENTA);
            g.drawRect((int)s.getHitBox().x - levelOffset, (int)s.getHitBox().y, (int)s.getHitBox().width, (int)s.getHitBox().height);

            g.setColor(Color.GREEN);
            g.drawRect((int)(s.getAttackBox().x) - levelOffset, (int)s.getAttackBox().y, (int)s.getAttackBox().width, (int)s.getAttackBox().height);
        }
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

package ro.comanitza.platformer.entities;

import ro.comanitza.platformer.gamestates.Playing;
import ro.comanitza.platformer.util.Constants;
import ro.comanitza.platformer.util.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;

import static ro.comanitza.platformer.util.Constants.Enemy.CRRABY_X_OFFSET;
import static ro.comanitza.platformer.util.Constants.Enemy.CRRABY_Y_OFFSET;

public class EnemyManager {

    private Playing playing;
    private final BufferedImage[][] crabbyImages;

    private List<Crabby> crabbies;

    public EnemyManager(Playing playing) {
        this.playing = playing;

        crabbyImages = loadCrabbyImages();

        crabbies = LoadSave.getCrabbies("/level_one_data_long.png");
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

    public void update(int[][] levelData) {

        for(Crabby c: crabbies) {

            if (!c.isActive()) {
                continue;
            }

            c.update(levelData, playing.getPlayer());
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

    }

    public void resetAllEnemies() {

        for (Crabby c: crabbies) {
            c.reset();
        }
    }
}

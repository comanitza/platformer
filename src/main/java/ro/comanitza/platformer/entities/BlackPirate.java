package ro.comanitza.platformer.entities;

import ro.comanitza.platformer.util.Constants;

import java.awt.geom.Rectangle2D;

import static ro.comanitza.platformer.util.Constants.Directions.LEFT;
import static ro.comanitza.platformer.util.Constants.Game.SCALE;

public class BlackPirate extends Enemy {

    public BlackPirate(double x, double y) {
        super(x, y, (int)(64 * SCALE), (int)(40 * SCALE), Constants.Enemy.BLACK_PIRATE);

        //20 * SCALE, 28 * SCALE
        initHitBox(x, y, (int)(20 * SCALE), (int)(28 * SCALE));
    }

    @Override
    protected Rectangle2D.Double createAttackBox() {
        return new Rectangle2D.Double(x, y, (int)(40 * Constants.Game.SCALE), (int)(19 * Constants.Game.SCALE));
    }

    public void update(int[][] levelData, Player player) {
        updateBehaviour(levelData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {

        if (walkingDirection == Constants.Directions.RIGHT) {
            attackBox.x = hitBox.x + hitBox.width + (int)(SCALE * 10);
        } else if (walkingDirection == Constants.Directions.LEFT) {
            attackBox.x = hitBox.x - hitBox.width - (int)(SCALE * 10);
        }

        attackBox.y = hitBox.y + (int)(SCALE * 10);
    }

    protected int getMaxHealth() {
        return 30;
    }

    public int flipW() {

        if (walkingDirection == LEFT) {
            return -1;
        }

        return 1;
    }

    public int flipX() {

        if (walkingDirection == LEFT) {
            return width;
        }

        return 0;
    }
}

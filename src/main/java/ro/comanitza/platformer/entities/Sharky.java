package ro.comanitza.platformer.entities;

import ro.comanitza.platformer.util.Constants;

import java.awt.geom.Rectangle2D;

import static ro.comanitza.platformer.util.Constants.Game.SCALE;

public class Sharky extends Enemy {

    private final int attackOffset = (int)(30 * Constants.Game.SCALE);

    @Override
    protected Rectangle2D.Double createAttackBox() {
        return new Rectangle2D.Double(x, y, (int)(40 * Constants.Game.SCALE), (int)(19 * Constants.Game.SCALE));
    }

    protected int getMaxHealth() {
        return 20;
    }

    public Sharky(double x, double y) {
        super(x, y, Constants.Enemy.SHARKY_WIDTH, Constants.Enemy.SHARKY_HEIGHT, Constants.Enemy.SHARKY);

        initHitBox(x, y, (int)(22 * Constants.Game.SCALE), (int)(19 * Constants.Game.SCALE));
    }

    public void update(int[][] levelData, Player player) {
        updateBehaviour(levelData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {

        if (walkingDirection == Constants.Directions.RIGHT) {
            attackBox.x = hitBox.x + (width / 2) + (int)(SCALE * 5);
        } else if (walkingDirection == Constants.Directions.LEFT) {
            attackBox.x = hitBox.x - (width + (int)(SCALE * 5));
        }

        attackBox.y = hitBox.y;
    }
}

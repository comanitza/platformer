package ro.comanitza.platformer.entities;

import ro.comanitza.platformer.util.Constants;
import ro.comanitza.platformer.util.Utils;

import java.awt.geom.Rectangle2D;

import static ro.comanitza.platformer.util.Constants.Directions.LEFT;
import static ro.comanitza.platformer.util.Constants.Directions.RIGHT;
import static ro.comanitza.platformer.util.Utils.isFloor;

public class Crabby extends Enemy {

    private Rectangle2D.Double attackBox;
    private final int attackOffset = (int)(30 * Constants.Game.SCALE);

    public Crabby(double x, double y) {
        super(x, y, Constants.Enemy.CRABBY_WIDTH, Constants.Enemy.CRABBY_HEIGHT, Constants.Enemy.CRABBY);

        initHitBox(x, y, (int)(22 * Constants.Game.SCALE), (int)(19 * Constants.Game.SCALE));
        attackBox = new Rectangle2D.Double(x, y, (int)(82 * Constants.Game.SCALE), (int)(19 * Constants.Game.SCALE));
    }

    public void update(int[][] levelData, Player player) {

        updateBehaviour(levelData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {

        attackBox.x = hitBox.x - attackOffset;
        attackBox.y = hitBox.y;
    }

    public void updateBehaviour(int[][] levelData, Player player) {

        if (firstUpdate) {
            firstUpdatedCheck(levelData);
        }

        if (inAir) {

            updateInAir(levelData);

        } else {

            switch (enemyState) {
                case Constants.Enemy.IDLE -> newState(Constants.Enemy.RUNNING);
                case Constants.Enemy.RUNNING -> {

                    if (canSeePlayer(levelData, player)) {
                        moveTowardsPlayer(player);

                        if (isPlayerInAttackRange(player)) {
                            newState(Constants.Enemy.ATTACK);
                        }
                    }

                    move(levelData, player);
                }
                case Constants.Enemy.ATTACK -> {

                    if (animationIndex == 1) {
                        attackChecked = false;
                    }

                    if (animationIndex == 3 && !attackChecked) {

                        checkPlayerHit(player, attackBox);
                        attackChecked = true;
                    }
                }
                case Constants.Enemy.HIT -> {

                }
            }
        }
    }

    public int flipX() {

        if (walkingDirection == RIGHT) {
            return width;
        }

        return 0;
    }

    public int flipW() {

        if (walkingDirection == RIGHT) {
            return -1;
        }

        return 1;

    }

    public Rectangle2D.Double getAttackBox() {
        return attackBox;
    }


}

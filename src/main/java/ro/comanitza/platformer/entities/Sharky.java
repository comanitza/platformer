package ro.comanitza.platformer.entities;

import ro.comanitza.platformer.util.Constants;

import java.awt.geom.Rectangle2D;

import static ro.comanitza.platformer.util.Constants.Game.SCALE;

public class Sharky extends Enemy {

    private final Rectangle2D.Double attackBox;
    private final int attackOffset = (int)(30 * Constants.Game.SCALE);

    protected int getMaxHealth() {
        return 20;
    }

    public Sharky(double x, double y) {
        super(x, y, Constants.Enemy.SHARKY_WIDTH, Constants.Enemy.SHARKY_HEIGHT, Constants.Enemy.SHARKY);

        //todo adapt values better
        initHitBox(x, y, (int)(22 * Constants.Game.SCALE), (int)(19 * Constants.Game.SCALE));
        attackBox = new Rectangle2D.Double(x, y, (int)(40 * Constants.Game.SCALE), (int)(19 * Constants.Game.SCALE));
    }

    public void update(int[][] levelData, Player player) {
        updateBehaviour(levelData, player);
        updateAnimationTick();
        updateAttackBox();
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

    private void updateAttackBox() {

        if (walkingDirection == Constants.Directions.RIGHT) {
            attackBox.x = hitBox.x + (width / 2) + (int)(SCALE * 5);
        } else if (walkingDirection == Constants.Directions.LEFT) {
            attackBox.x = hitBox.x - (width + (int)(SCALE * 5));
        }

        attackBox.y = hitBox.y;
    }

    public Rectangle2D.Double getAttackBox() {
        return attackBox;
    }
}

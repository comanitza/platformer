package ro.comanitza.platformer.entities;

import ro.comanitza.platformer.util.Constants;
import ro.comanitza.platformer.util.Utils;

import static ro.comanitza.platformer.util.Constants.Directions.LEFT;
import static ro.comanitza.platformer.util.Utils.isFloor;

public class Crabby extends Enemy {

    public Crabby(double x, double y) {
        super(x, y, Constants.Enemy.CRABBY_WIDTH, Constants.Enemy.CRABBY_HEIGHT, Constants.Enemy.CRABBY);

        initHitBox(x, y, (int)(22 * Constants.Game.SCALE), (int)(19 * Constants.Game.SCALE));
    }

    public void update(int[][] levelData, Player player) {

        updateMove(levelData, player);
        updateAnimationTick();
    }

    public void updateMove(int[][] levelData, Player player) {

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
                    }

                    if (isPlayerInAttackRange(player)) {
                        newState(Constants.Enemy.ATTACK);
                    }

                    move(levelData, player);
                }
            }
        }
    }
}

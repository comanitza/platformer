package ro.comanitza.platformer.entities;

import ro.comanitza.platformer.util.Constants;
import ro.comanitza.platformer.util.Utils;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static ro.comanitza.platformer.util.Constants.Directions.LEFT;
import static ro.comanitza.platformer.util.Constants.Directions.RIGHT;
import static ro.comanitza.platformer.util.Utils.isFloor;

public abstract class Enemy extends Entity {

    protected int animationIndex;
    protected int enemyState = Constants.Enemy.IDLE;
    protected final int enemyType;
    protected int animationTick;
    protected int animationSpeed = 25;
    protected boolean firstUpdate = true;
    protected boolean inAir = true;
    protected double fallSpeed;
    protected double gravity = 0.04 * Constants.Game.SCALE;
    protected int walkingDirection = LEFT;
    protected double walkingSpeed = 1d * Constants.Game.SCALE;
    protected int enemyTileY;

    protected double attackDistance = Constants.Game.TILES_IN_WIDTH;
    protected double visualRange = attackDistance * 5;

    public Enemy(double x, double y, int width, int height, int enemyType) {
        super(x, y, width, height);

        initHitBox(x, y, width, height);

        this.enemyType = enemyType;
    }

    protected void updateAnimationTick() {
        animationTick++;

        if (animationTick >= animationSpeed) {
            animationTick = 0;

            animationIndex++;

            if (animationIndex >= Constants.Enemy.getSpriteAmount(enemyType, enemyState)) {
                animationIndex = 0;

                if (enemyState == Constants.Enemy.ATTACK) {
                    enemyState = Constants.Enemy.IDLE;
                }
            }
        }
    }

    protected void firstUpdatedCheck(int[][] levelData) {
        if (!Utils.isEntityOnFloor(getHitBox(), levelData)) {

            inAir = true;
            firstUpdate = false;
        }
    }

    protected void updateInAir(int[][] levelData) {
        if (Utils.canMoveHere(hitBox.x, hitBox.y, hitBox.width, hitBox.height, levelData)) {

            hitBox.y += fallSpeed;
            fallSpeed += gravity;
        } else {
            inAir = false;
            enemyTileY = (int) ((getHitBox().y - 3)  / Constants.Game.TILES_SIZE);
        }
    }

    protected void newState(int enemyState) {
        this.enemyState = enemyState;
        animationTick = 0;
        animationIndex = 0;
    }

    protected void moveTowardsPlayer(Player player) {

        if (player.getHitBox().x > getHitBox().x) {

            walkingDirection = RIGHT;
        } else {
            walkingDirection = LEFT;
        }
    }

    protected boolean canSeePlayer(int[][] levelData, Player player) {

        int playerTileY = (int)player.getHitBox().y / Constants.Game.TILES_SIZE;

        if (playerTileY != enemyTileY) {
            return false;
        }

        return isPlayerInRange(player) && Utils.isSightClear(levelData, player.getHitBox(), getHitBox(), playerTileY);
    }



    protected boolean isPlayerInRange(Player player) {
        double distance = Math.abs(player.getHitBox().x - getHitBox().x);

        return distance <= visualRange;
    }

    protected boolean isPlayerInAttackRange(Player player) {
        double distance = Math.abs(player.getHitBox().x - getHitBox().x);

        return distance <= attackDistance;
    }

    protected void move(int[][] levelData, Player player) {

        double speed = 0;

        if (walkingDirection == LEFT) {
            speed -= walkingSpeed;
        } else {
            speed += walkingSpeed;
        }

        //todo aici era beleua, era prea sus, ar trebui sa-l pun cu picioarele pe podea
        if (Utils.canMoveHere(hitBox.x + speed, hitBox.y - 3, hitBox.width, hitBox.height, levelData)) {

            if (isFloor(hitBox, speed, levelData)) {

                hitBox.x += speed;
                return;
            }
        }

        changeWalkingDirection();
    }

    protected void changeWalkingDirection() {
        if (walkingDirection == LEFT) {
            walkingDirection = RIGHT;
        } else {
            walkingDirection = LEFT;
        }
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public int getEnemyType() {
        return enemyType;
    }

    public int getEnemyState() {
        return enemyState;
    }
}

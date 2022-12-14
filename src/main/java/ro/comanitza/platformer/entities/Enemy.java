package ro.comanitza.platformer.entities;

import ro.comanitza.platformer.util.Constants;
import ro.comanitza.platformer.util.Utils;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static ro.comanitza.platformer.util.Constants.Directions.LEFT;
import static ro.comanitza.platformer.util.Constants.Directions.RIGHT;
import static ro.comanitza.platformer.util.Utils.isFloor;

public abstract class Enemy extends Entity {

    private int animationIndex;
    private int enemyState = Constants.Enemy.IDLE;
    private final int enemyType;
    private int animationTick;
    private int animationSpeed = 25;
    private boolean firstUpdate = true;
    private boolean inAir = true;
    private double fallSpeed;
    private double gravity = 0.04 * Constants.Game.SCALE;
    private int walkingDirection = LEFT;
    private double walkingSpeed = 1d * Constants.Game.SCALE;

    public Enemy(double x, double y, int width, int height, int enemyType) {
        super(x, y, width, height);

        initHitBox(x, y, width, height);

        this.enemyType = enemyType;
    }

    private void updateAnimationTick() {
        animationTick++;

        if (animationTick >= animationSpeed) {
            animationTick = 0;

            animationIndex++;

            if (animationIndex >= Constants.Enemy.getSpriteAmount(enemyType, enemyState)) {
                animationIndex = 0;
            }
        }

    }

    public void update(int[][] levelData) {

        updateMove(levelData);
        updateAnimationTick();
    }

    public void updateMove(int[][] levelData) {

        if (firstUpdate) {
            if (!Utils.isEntityOnFloor(getHitBox(), levelData)) {

                inAir = true;
                firstUpdate = false;
            }
        }

        if (inAir) {

            if (Utils.canMoveHere(hitBox.x, hitBox.y, hitBox.width, hitBox.height, levelData)) {

                hitBox.y += fallSpeed;
                fallSpeed += gravity;
            } else {
                inAir = false;
            }

        } else {

            switch (enemyState) {
                case Constants.Enemy.IDLE -> enemyState = Constants.Enemy.RUNNING;
                case Constants.Enemy.RUNNING -> {

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
            }
        }
    }

    protected void changeWalkingDirection() {
        if (walkingDirection == LEFT) {
            walkingDirection = RIGHT;
        } else {
            walkingDirection = LEFT;
        }
    }


    private void patrol() {
    }

    public void render(Graphics g) {

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

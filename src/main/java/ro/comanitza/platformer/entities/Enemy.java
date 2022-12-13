package ro.comanitza.platformer.entities;

import ro.comanitza.platformer.util.Constants;

import java.awt.*;

public abstract class Enemy extends Entity {

    private int animationIndex;
    private int enemyState;
    private final int enemyType;
    private int animationTick;
    private int animationSpeed = 25;

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

    public void update() {

        updateAnimationTick();
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

package ro.comanitza.platformer.entities;

import ro.comanitza.platformer.core.Game;
import ro.comanitza.platformer.util.LoadSave;
import ro.comanitza.platformer.util.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import static ro.comanitza.platformer.util.Constants.Directions.*;
import static ro.comanitza.platformer.util.Constants.Player.*;
import static ro.comanitza.platformer.util.Constants.Game.*;

public class Player extends Entity {

    private BufferedImage[][] animations;

    private int animationTick;
    private int animationIndex;
    private final int animationSpeed = 15;
    private int playerAction = IDLE;
    private int playerDirection = STANDING_STILL;

    private boolean moving;
    private boolean attacking;

    private boolean left;
    private boolean up;
    private boolean right;
    private boolean down;
    private boolean jump;

    /*
     * jumping and gravity stuff
     */
    private double airSpeed = 0d;
    private static final double gravity = 0.04d;
    private final double jumpSpeed = -2.25 * SCALE;
    private final static double fallSpeedAfterCollision = 0.5d * SCALE;

    private boolean inAir;

    private double playerSpeed = 1d * SCALE;

    private int[][] levelData;

    private double xDrawOffset = 21d * SCALE;
    private double yDrawOffset = 4d * SCALE;

    public Player(double x, double y, int width, int height) {
        super(x, y, width, height);

        initHitBox(x, y, 20 * SCALE, 28 * SCALE);
        loadAnimations();
    }

    public void update() {

        updatePosition();

        setAnimation();

        updateAnimationTick();
    }

    public void render(Graphics g) {

        g.drawImage(animations[playerAction][animationIndex], (int)(hitBox.x - xDrawOffset), (int)(hitBox.y - yDrawOffset), width, height, null);
//        drawHitBox(g);
    }

    private void loadAnimations() {


            BufferedImage bufferedImage = LoadSave.getPlayerAtlas();

            animations = new BufferedImage[9][6];

            for (int j = 0; j < animations.length; j++) {
                for (int i = 0; i < animations[j].length; i++) {
                    animations[j][i] = bufferedImage.getSubimage(i * 64, j * 40, 64, 40);
                }
            }
    }

    private void setAnimation () {

        int initialAnimation = playerAction;

        if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }

        if (inAir) {

            if (airSpeed > 0) {
                playerAction = FALL;
            } else {
                playerAction = JUMP;
            }
        }

        if (attacking) {
            playerAction = ATTACK_1;
        }

        if (initialAnimation != playerAction) {
            animationTick = 0;
            animationIndex = 0;
        }
    }

    private void updateAnimationTick() {

        animationTick++;

        if (animationTick >= animationSpeed) {

            animationTick = 0;

            if (animationIndex + 1 >= getSpriteAmount(playerAction)) {
                animationIndex = 0;
                attacking = false;
            } else {
                animationIndex++;
            }
        }
    }

    public void updatePosition() {

        moving = false;

        if (!left && !right && !inAir) {
            return;
        }

        if (jump) {
            jump();
        }

        double xSpeed = 0;

        if (left) {
            xSpeed -= playerSpeed;
        }

        if (right) {
            xSpeed += playerSpeed;
        }

        if (Utils.isEntityOnFloor(hitBox, levelData)) {

            inAir = true;
        }

        if (inAir) {

            /*
             * can move in air
             */
            if (Utils.canMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, levelData)) {

                hitBox.y += airSpeed;
                airSpeed += gravity;

                updateXPosition(xSpeed);
            } else {

                /*
                 * we are in the air because we have air speed, and we hit something
                 *
                 * we probably hit the floor or roof
                 */
                if (airSpeed > 0) {
                    inAir = false;
                    airSpeed = 0;
                }
                
                airSpeed = fallSpeedAfterCollision;
                
                updateXPosition(xSpeed);
            }

        } else {

            updateXPosition(xSpeed);
        }

        moving = true;
    }

    private void jump() {

        if (inAir) {
            return;
        }

        inAir = true;
        airSpeed = jumpSpeed;

    }

    private void updateXPosition(double xSpeed) {

        if (Utils.canMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, levelData)) {
            hitBox.x += xSpeed;
        }
    }

    public void setRectPosition(final int x, final int y) {

        this.x = x;
        this.x = y;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void loadLevelData(int[][] levelData) {
        this.levelData = levelData;

        if (Utils.isEntityOnFloor(hitBox, levelData)) {
            inAir = true;
        }
    }

    public boolean isJump() {
        return jump;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }
}

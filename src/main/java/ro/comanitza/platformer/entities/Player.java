package ro.comanitza.platformer.entities;

import ro.comanitza.platformer.util.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import static ro.comanitza.platformer.util.Constants.Directions.*;
import static ro.comanitza.platformer.util.Constants.Player.*;

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

    private double playerSpeed = 2d;

    public Player(double x, double y) {
        super(x, y);

        loadAnimations();
    }

    public void update() {

        updatePosition();


        setAnimation();


        updateAnimationTick();
    }

    public void render(Graphics g) {

        g.drawImage(animations[playerAction][animationIndex], (int) x, (int) y, 256, 160, null);
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

        if (left && !right) {
            x -= playerSpeed;

            moving = true;
        } else if (right && !left) {
            x += playerSpeed;

            moving = true;
        }

        if (up && !down) {
            y -= playerSpeed;

            moving = true;
        } else if (down && !up) {
            y += playerSpeed;

            moving = true;
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
}

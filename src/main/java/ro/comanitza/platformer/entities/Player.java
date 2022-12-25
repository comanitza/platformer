package ro.comanitza.platformer.entities;

import ro.comanitza.platformer.audio.AudioPlayer;
import ro.comanitza.platformer.core.Game;
import ro.comanitza.platformer.gamestates.Playing;
import ro.comanitza.platformer.util.LoadSave;
import ro.comanitza.platformer.util.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
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

    private final BufferedImage statusBarImage;

    private final int statusBarWidth = (int) (192 * SCALE);
    private final int statusBarHeight = (int) (58 * SCALE);
    private final int statusBarX = (int) (10 * SCALE);
    private final int statusBarY = (int) (10 * SCALE);

    private final int healthBarWidth = (int) (150 * SCALE);
    private final int healthBarHeight = (int) (4 * SCALE);
    private final int healthBarXStart = (int) (34 * SCALE);
    private final int healthBarYStart = (int) (14 * SCALE);

    private final int maxHealth = 100;
    private int currentHealth = 60;
    private int healthWidth = healthBarWidth;

    private int flipX = 0;
    private int flipW = 1;

    private boolean attackChecked;

    private Rectangle2D.Double attackBox;

    private final Playing playing;
    private long lastSpikeAttack = 0;

    public Player(double x, double y, int width, int height, Playing playing) {
        super(x, y, width, height);

        initHitBox(x, y, 20 * SCALE, 28 * SCALE);
        loadAnimations();

        statusBarImage = loadStatusBarImage();

        attackBox = new Rectangle2D.Double(x, y, (int)(20 * SCALE), (int)(20 * SCALE));

        this.playing = playing;
    }

    private void updateHealthBar() {
        healthWidth = (int)((currentHealth / (double)maxHealth) * healthBarWidth);
    }

    public void update() {

        updatePosition();

        updateAttackBox();

        if (attacking) {
            checkAttack();

            playing.getItemsManager().checkItemHit(getAttackBox());
        }

        if (moving) {
            playing.getItemsManager().checkItemTouchedPlayer(getHitBox());
        }

        if (System.currentTimeMillis() - lastSpikeAttack >= 500) {
            playing.getItemsManager().checkSpikesTouched(this);
            lastSpikeAttack = System.currentTimeMillis();
        }



        setAnimation();

        updateAnimationTick();

        updateHealthBar();

        if (currentHealth <= 0) {

            playing.getGame().getAudioPlayer().stopMusic();
            playing.getGame().getAudioPlayer().playEffect(AudioPlayer.DIE);
            playing.getGame().getAudioPlayer().playEffect(AudioPlayer.GAME_OVER);
            playing.setGameOver(true);
        }
    }

    private void checkAttack() {

        if (animationIndex != 1 || attackChecked) {
            return;
        }

        attackChecked = true;

        playing.checkEnemyHit();
    }

    private void updateAttackBox() {

        if (right) {
            attackBox.x = hitBox.x + hitBox.width + (int)(SCALE * 10);
        } else if (left) {
            attackBox.x = hitBox.x - hitBox.width - (int)(SCALE * 10);
        }

        attackBox.y = hitBox.y + (int)(SCALE * 10);
    }

    public void changeHealth(int value) {
        currentHealth += value;

        if (currentHealth <= 0) {
            //game over
            currentHealth = 0;
            return;
        }

        if (currentHealth > maxHealth) {
            currentHealth = maxHealth;
        }
    }

    private void renderUi(Graphics g) {
        g.drawImage(statusBarImage, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g.setColor(Color.RED);
        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
    }

    public void render(Graphics g, int levelOffset) {

        g.drawImage(animations[playerAction][animationIndex], (int)(hitBox.x - xDrawOffset) - levelOffset + flipX, (int)(hitBox.y - yDrawOffset), width * flipW, height, null);
        drawHitBox(g, levelOffset);
        renderUi(g);
        drawAttackBox(g, levelOffset);
    }

    private void drawAttackBox(Graphics g, int levelOffset) {
        g.setColor(Color.BLUE);
        g.drawRect((int)attackBox.x - levelOffset, (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
    }

    private void loadAnimations() {

        BufferedImage bufferedImage = LoadSave.getPlayerAtlas();

        animations = new BufferedImage[7][8];

        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = bufferedImage.getSubimage(i * 64, j * 40, 64, 40);
            }
        }
    }

    private BufferedImage loadStatusBarImage() {
        return LoadSave.getStatusBar();
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
            playing.getGame().getAudioPlayer().playAttackSound();
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
                attackChecked = false;
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
            flipX = width;
            flipW = -1;
        }

        if (right) {
            xSpeed += playerSpeed;

            flipX = 0;
            flipW = 1;
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

        playing.getGame().getAudioPlayer().playEffect(AudioPlayer.JUMP);

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

    public Rectangle2D.Double getAttackBox() {
        return attackBox;
    }

    public void reset() {

        setLeft(false);
        setUp(false);
        setRight(false);
        setDown(false);

        inAir = true;
        attacking = false;
        moving = false;
        playerAction = IDLE;
        currentHealth = maxHealth;

        hitBox.x = x;
        hitBox.y = y;

    }
}

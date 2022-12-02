package ro.comanitza.platformer.core;

import ro.comanitza.platformer.input.KeyboardInputs;
import ro.comanitza.platformer.input.MouseInputs;
import ro.comanitza.platformer.util.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import static ro.comanitza.platformer.util.Constants.Player.*;
import static ro.comanitza.platformer.util.Constants.Directions.*;

public class GamePanel extends JPanel {

    private double xDelta = 100d;
    private double yDelta = 100d;

    private BufferedImage bufferedImage;
    private BufferedImage[][] animations;

    private int animationTick;
    private int animationIndex;
    private final int animationSpeed = 15;
    private int playerAction = IDLE;
    private int playerDirection = STANDING_STILL;
    private boolean moving;

    public GamePanel() {


        MouseInputs mouseInputs = new MouseInputs(this);

        importImage();
        loadAnimations();

        setPanelSize();

        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    public void setPanelSize() {
        setPreferredSize(new Dimension(1280, 800));
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        /*
         * this must be present, or else we will not move
         */
        requestFocus(true);

        setAnimation();

        updatePosition();

        updateAnimationTick();

        g.drawImage(animations[playerAction][animationIndex], (int) xDelta, (int) yDelta, 256, 160, null);
    }

    public void setDirection(final int direction) {
        this.playerDirection = direction;

        this.moving = true;
    }

    public void setMoving(final boolean moving) {
        this.moving = moving;
    }

    public void updatePosition() {

        if (moving) {

            switch (playerDirection) {
                case LEFT -> xDelta -= 5;
                case UP -> yDelta -= 5;
                case RIGHT -> xDelta += 5;
                case DOWN -> yDelta += 5;
            }
        }

    }

    public void setRectPosition(final int x, final int y) {

        this.xDelta = x;
        this.yDelta = y;
    }

//    public void changeXDelta(int delta) {
//        xDelta += delta;
//    }
//
//    public void changeYDelta(int delta) {
//        yDelta += delta;
//    }

    private void importImage () {

        try (InputStream is = getClass().getResourceAsStream("/player_sprites.png")) {
            bufferedImage = ImageIO.read(is);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAnimations() {

        animations = new BufferedImage[9][6];

        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = bufferedImage.getSubimage(i * 64, j * 40, 64, 40);
            }
        }
    }

    private void setAnimation () {
        if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }
    }

    private void updateAnimationTick() {

        animationTick++;

        if (animationTick >= animationSpeed) {

            animationTick = 0;

            if (animationIndex + 1 >= getSpriteAmount(playerAction)) {
                animationIndex = 0;
            } else {
                animationIndex++;
            }
        }
    }
}

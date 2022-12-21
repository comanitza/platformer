package ro.comanitza.platformer.items;

import ro.comanitza.platformer.util.Constants;

import java.awt.geom.Rectangle2D;

public abstract class GameItem {

    protected int x;
    protected int y;

    protected boolean doAnimation;
    protected int itemType;

    protected boolean active = true;

    protected Rectangle2D.Double hitBox;

    protected int animationIndex;
    protected int animationTick;

    protected int xDrawOffset;
    protected int yDrawOffset;

    protected int animationSpeed = 25;

    public GameItem(int x, int y, int itemType) {

        this.x = x;
        this.y = y;
        this.itemType = itemType;

    }

    protected void initHitBox(double width, double height) {
        this.hitBox = new Rectangle2D.Double(x, y, width, height);
    }

    protected void updateAnimationTick() {
        animationTick++;

        if (animationTick >= animationSpeed) {
            animationTick = 0;

            animationIndex++;

            if (animationIndex >= Constants.Items.getSpriteAmount(itemType)) {
                animationIndex = 0;

                if (itemType == Constants.Items.BARREL || itemType == Constants.Items.BOX) {
                    doAnimation = false;
                    active = false;
                }
            }
        }
    }

    public void reset() {
        animationIndex = 0;
        animationTick = 0;
        active = true;

        if (itemType == Constants.Items.BARREL || itemType == Constants.Items.BOX) {
            doAnimation = false;
        } else {
            doAnimation = true;
        }
    }

    public boolean isActive() {
        return active;
    }

    public Rectangle2D.Double getHitBox() {
        return hitBox;
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public int getXDrawOffset() {
        return xDrawOffset;
    }

    public int getYDrawOffset() {
        return yDrawOffset;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getItemType() {
        return itemType;
    }

    public void setDoAnimation(boolean doAnimation) {
        this.doAnimation = doAnimation;
    }
}

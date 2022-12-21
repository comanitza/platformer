package ro.comanitza.platformer.items;

import ro.comanitza.platformer.util.Constants;

import java.awt.geom.Rectangle2D;

public class Container extends GameItem {

    public Container(int x, int y, int itemType) {
        super(x, y, itemType);

        initHitBox(Constants.Items.CONTAINER_WIDTH, Constants.Items.CONTAINER_HEIGHT);
        createHitBox(itemType);

    }

    protected void createHitBox(int itemType) {

        if (itemType == Constants.Items.BOX) {

            initHitBox(Constants.Items.CONTAINER_WIDTH, Constants.Items.CONTAINER_HEIGHT);

            xDrawOffset = (int)(7 * Constants.Game.SCALE);
            yDrawOffset = 0;//(int)(2 * Constants.Game.SCALE);

            return;
        }

        if (itemType == Constants.Items.BARREL) {

            initHitBox(23, 25);

            xDrawOffset = (int)(8 * Constants.Game.SCALE);
            yDrawOffset = (int)(5 * Constants.Game.SCALE);
        }

        hitBox.y += yDrawOffset + (int)(2 * Constants.Game.SCALE);
        hitBox.x += (int)(xDrawOffset / 2);
    }

    public void update() {

        if (doAnimation) {
            updateAnimationTick();
        }
    }



}

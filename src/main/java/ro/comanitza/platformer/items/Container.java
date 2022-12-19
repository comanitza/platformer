package ro.comanitza.platformer.items;

import ro.comanitza.platformer.util.Constants;

import java.awt.geom.Rectangle2D;

public class Container extends GameItem {

    public Container(int x, int y, int itemType) {
        super(x, y, itemType);

        initHitBox(Constants.Items.CONTAINER_WIDTH, Constants.Items.CONTAINER_HEIGHT);

    }

    protected void createHitBox(int itemType) {

        if (itemType == Constants.Items.BOX) {

            initHitBox(25, 18);

            xDrawOffset = (int)(7 * Constants.Game.SCALE);
            yDrawOffset = (int)(12 * Constants.Game.SCALE);

            return;
        }

        if (itemType == Constants.Items.BARREL) {

            initHitBox(23, 25);

            xDrawOffset = (int)(8 * Constants.Game.SCALE);
            yDrawOffset = (int)(5 * Constants.Game.SCALE);
        }
    }

    public void update() {

        if (doAnimation) {
            updateAnimationTick();
        }
    }
}

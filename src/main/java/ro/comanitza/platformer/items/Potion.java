package ro.comanitza.platformer.items;

import ro.comanitza.platformer.util.Constants;

import java.awt.*;

public class Potion extends GameItem {

    private double hoverOffset;
    private int maxHoverOffset;
    private int hoverDirection = 1;

    public Potion(int x, int y, int itemType) {
        super(x, y, itemType);

        doAnimation = true;

        initHitBox(7 * Constants.Game.SCALE, 14 * Constants.Game.SCALE);

        xDrawOffset = (int)(3 * Constants.Game.SCALE);
        yDrawOffset = (int)(2 * Constants.Game.SCALE);

        maxHoverOffset = (int) (10 * Constants.Game.SCALE);
    }

    public void update() {
        updateAnimationTick();
        updateHover();
    }

    private void updateHover() {

        hoverOffset += 0.1d * Constants.Game.SCALE * hoverDirection;

        if (hoverOffset >= maxHoverOffset) {
            hoverDirection = -1;
        } else if (hoverOffset < 0) {
            hoverDirection = 1;
        }

        hitBox.y = y + hoverOffset;
    }
}

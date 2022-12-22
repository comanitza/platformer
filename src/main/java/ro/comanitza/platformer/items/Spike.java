package ro.comanitza.platformer.items;

import ro.comanitza.platformer.util.Constants;

public class Spike extends GameItem {

    public Spike(int x, int y, int itemType) {
        super(x, y, itemType);

        initHitBox(32, 16);

        xDrawOffset = 0;
        yDrawOffset = (int)(Constants.Game.SCALE * 16);

        hitBox.y += yDrawOffset;
    }
}

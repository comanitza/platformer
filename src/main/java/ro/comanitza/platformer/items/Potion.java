package ro.comanitza.platformer.items;

import ro.comanitza.platformer.util.Constants;

import java.awt.*;

public class Potion extends GameItem {

    public Potion(int x, int y, int itemType) {
        super(x, y, itemType);

        doAnimation = true;

        initHitBox(7 * Constants.Game.SCALE, 14 * Constants.Game.SCALE);

        xDrawOffset = (int)(3 * Constants.Game.SCALE);
        yDrawOffset = (int)(2 * Constants.Game.SCALE);
    }

    public void update() {
        updateAnimationTick();
    }

    public void render(Graphics g) {

    }
}

package ro.comanitza.platformer.entities;

import ro.comanitza.platformer.util.Constants;

public class Crabby extends Enemy {

    public Crabby(double x, double y) {
        super(x, y, Constants.Enemy.CRABBY_WIDTH, Constants.Enemy.CRABBY_HEIGHT, Constants.Enemy.CRABBY);
    }
}

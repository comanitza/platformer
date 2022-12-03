package ro.comanitza.platformer.entities;

public abstract class Entity {

    protected double x;
    protected double y;

    public Entity (final double x, final double y) {
        this.x = x;
        this.y = y;
    }
}

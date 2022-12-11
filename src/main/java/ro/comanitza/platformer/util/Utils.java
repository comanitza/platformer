package ro.comanitza.platformer.util;

import ro.comanitza.platformer.core.Game;

import java.awt.geom.Rectangle2D;

import static ro.comanitza.platformer.util.Constants.Game.*;

public class Utils {

    public static boolean canMoveHere(double x, double y, double width, double height, int[][] levelData) {

        /*
         * top left
         */
        if (isSolid(x, y, levelData)) {
            return false;
        }

        /*
         * bottom right
         */
        if (isSolid(x + width, y + height, levelData)) {
            return false;
        }

        /*
         * top right
         */
        if (isSolid(x + width, y, levelData)) {
            return false;
        }

        /*
         * bottom left
         */
        if (isSolid(x, y + height, levelData)) {
            return false;
        }

        return true;
    }

    private static boolean isSolid(double x, double y, int[][] levelData) {

        int levelWidth = levelData[0].length * TILES_SIZE;

        if (x < 0 || x >= levelWidth) {
            return true;
        }

        if (y < 0 || y >= GAME_HEIGHT) {
            return true;
        }

        double xIndex = x / TILES_SIZE;
        double yIndex = y / TILES_SIZE;

        int value = levelData[(int)yIndex][(int)xIndex];

        return value >= 0 && value <= 49 && value != 11;
    }

    public static boolean isEntityOnFloor(Rectangle2D.Double hitbox, int[][] levelData) {

        if (isSolid(hitbox.x, hitbox.y + hitbox.height + 2, levelData)) {
            return true;
        }

        return !isSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 2, levelData);
    }
}

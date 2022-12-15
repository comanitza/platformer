package ro.comanitza.platformer.util;

import ro.comanitza.platformer.core.Game;
import ro.comanitza.platformer.entities.Player;

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

        return isTileSolid(levelData, (int) xIndex, (int)yIndex);
    }

    public static boolean isEntityOnFloor(Rectangle2D.Double hitbox, int[][] levelData) {

        if (isSolid(hitbox.x, hitbox.y + hitbox.height + 2, levelData)) {
            return true;
        }

        return !isSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 2, levelData);
    }

    public static boolean isFloor(Rectangle2D.Double hitBox, double speed, int[][] levelData) {
        return isSolid(hitBox.x + speed, hitBox.y + hitBox.height + 1, levelData);
    }

    public static boolean isTileSolid(int[][] levelData, int xTile, int yTile) {

        int value = levelData[yTile][xTile];

        return value >= 0 && value <= 49 && value != 11;
    }

    public static boolean isSightClear(int[][] levelData, Rectangle2D.Double first, Rectangle2D.Double second, int tileY) {

        int firstTileX = (int)(first.x / TILES_SIZE);
        int secondTileX = (int)(second.x / TILES_SIZE);

        if (firstTileX == secondTileX) {
            return true;
        }

        if (firstTileX > secondTileX) {

            for (int i = secondTileX; i < firstTileX; i++) {

                if (isTileSolid(levelData, i, tileY)) {
                    return false;
                }

                if (!isTileSolid(levelData, i, tileY + 1)) {
                    return false;
                }
            }

        } else {

            for (int i = firstTileX; i < secondTileX; i++) {

                if (isTileSolid(levelData, i, tileY)) {
                    return false;
                }

                if (!isTileSolid(levelData, i, tileY + 1)) {
                    return false;
                }
            }
        }

        return true;
    }
}

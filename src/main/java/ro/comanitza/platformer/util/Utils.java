package ro.comanitza.platformer.util;

import ro.comanitza.platformer.core.Game;

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

        if (x < 0 || x >= Game.GAME_WIDTH) {
            return true;
        }

        if (y < 0 || y >= Game.GAME_HEIGHT) {
            return true;
        }

        double xIndex = x / Game.TILES_SIZE;
        double yIndex = y / Game.TILES_SIZE;

        int value = levelData[(int)yIndex][(int)xIndex];

        return value >= 0 && value <= 49 && value != 11;
    }
}

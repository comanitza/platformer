package ro.comanitza.platformer.levels;

public class Level {

    private final int[][] levelData;

    public Level(final int[][] levelData) {
        this.levelData = levelData;
    }

    public int getSpriteIndex (int x, int y) {

        return levelData[y][x];
    }

    public int[][] getLevelData() {
        return levelData;
    }
}

package ro.comanitza.platformer.levels;

import ro.comanitza.platformer.entities.Crabby;
import ro.comanitza.platformer.util.Constants;
import ro.comanitza.platformer.util.LoadSave;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Level {

    private final BufferedImage image;
    private final int[][] levelData;

    private List<Crabby> crabs = new ArrayList<>();

    private int levelTilesWidth;
    private int maxTilesOffset;
    private int maxTilesOffsetInPixels;

    public Level(BufferedImage image) {
        this.image = image;
        this.levelData = createLevelData(image);

        this.crabs = createCrabbies(image);

        levelTilesWidth = image.getWidth();
        maxTilesOffset = levelTilesWidth - Constants.Game.TILES_IN_WIDTH;
        maxTilesOffsetInPixels = Constants.Game.TILES_SIZE * maxTilesOffset;

    }

    public int getSpriteIndex (int x, int y) {

        return levelData[y][x];
    }

    private int[][] createLevelData(BufferedImage image) {
        return LoadSave.getLevelData(image);
    }

    public int[][] getLevelData() {
        return levelData;
    }

    private List<Crabby> createCrabbies(BufferedImage image) {
        return LoadSave.getCrabbies(image);
    }

    public int getLevelOffset() {
        return maxTilesOffsetInPixels;
    }

    public List<Crabby> getCrabs() {
        return crabs;
    }
}

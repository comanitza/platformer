package ro.comanitza.platformer.levels;

import ro.comanitza.platformer.entities.Crabby;
import ro.comanitza.platformer.entities.Sharky;
import ro.comanitza.platformer.items.Container;
import ro.comanitza.platformer.items.Potion;
import ro.comanitza.platformer.items.Spike;
import ro.comanitza.platformer.util.Constants;
import ro.comanitza.platformer.util.LoadSave;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Level {

    private final BufferedImage image;
    private final int[][] levelData;

    private List<Crabby> crabs;
    private List<Potion> potions;
    private List<Container> containers;
    private List<Spike> spikes;
    private List<Sharky> sharkies;

    private int levelTilesWidth;
    private int maxTilesOffset;
    private int maxTilesOffsetInPixels;

    public Level(BufferedImage image) {
        this.image = image;
        this.levelData = createLevelData(image);

        this.crabs = createCrabbies(image);
        this.potions = createPotions();
        this.containers = createContainers();
        this.spikes = createSpikes();
        this.sharkies = createSharkies(image);

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

    private List<Sharky> createSharkies(BufferedImage image) {

        return LoadSave.getSharkies(image);
    }

    private List<Potion> createPotions() {
        return LoadSave.getPotions(image);
    }

    private List<Container> createContainers() {
        return LoadSave.getContainer(image);
    }

    private List<Spike> createSpikes() {
        return LoadSave.getSpikes(image);
    }

    public int getLevelOffset() {
        return maxTilesOffsetInPixels;
    }

    public List<Crabby> getCrabs() {
        return crabs;
    }

    public List<Sharky> getSharkies() {
        return sharkies;
    }

    public List<Potion> getPotions() {
        return potions;
    }

    public List<Container> getContainers() {
        return containers;
    }

    public List<Spike> getSpikes() {
        return spikes;
    }
}

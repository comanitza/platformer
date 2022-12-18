package ro.comanitza.platformer.levels;

import ro.comanitza.platformer.core.Game;
import ro.comanitza.platformer.gamestates.GameState;
import ro.comanitza.platformer.util.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static ro.comanitza.platformer.util.Constants.Game.*;

public class LevelManager {

    private final Game game;
    private final BufferedImage[] outsideLevelAtlas;

    private final java.util.List<Level> levels = new ArrayList<>();

    private int currentLevelIndex;

    public LevelManager(final Game game) {
        this.game = game;

        outsideLevelAtlas = importOutsideLevelSprites(); //

//        levelOne = new Level(LoadSave.getLevelData("/level_one_data_long.png"));

        for(BufferedImage levelImage: LoadSave.getAllLevels()) {
            levels.add(new Level(levelImage));
        }
    }

    private BufferedImage[] importOutsideLevelSprites() {

        BufferedImage[] arr = new BufferedImage[48];

        BufferedImage temp = LoadSave.getLevelAtlas();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 12; j++) {

                arr[12 * i + j] = temp.getSubimage(j * 32, i * 32, 32, 32);
            }
        }

        return arr;
    }

    public void render(final Graphics g, final int levelOffset) {

        for (int i = 0; i < TILES_IN_HEIGHT; i++) {
            for (int j = 0; j < getCurrentLevel().getLevelData()[0].length; j++) {
                int index = levels.get(currentLevelIndex).getSpriteIndex(j, i);
                g.drawImage(outsideLevelAtlas[index], (TILES_SIZE * j) - levelOffset, TILES_SIZE * i, TILES_SIZE, TILES_SIZE, null);
            }
        }


    }

    public void update() {

    }

    public Level getCurrentLevel() {
        return levels.get(currentLevelIndex);
    }


    public void loadNextLevel() {
        currentLevelIndex++;

        if (currentLevelIndex >= levels.size()) {
            currentLevelIndex = 0;
            GameState.gameState = GameState.MENU;
        }

        game.getPlaying().getEnemyManager().loadCrabs(getCurrentLevel());
        game.getPlaying().getPlayer().loadLevelData(getCurrentLevel().getLevelData());
        game.getPlaying().setLevelOffset(getCurrentLevel().getLevelOffset());
    }
}

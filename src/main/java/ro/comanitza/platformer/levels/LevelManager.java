package ro.comanitza.platformer.levels;

import ro.comanitza.platformer.core.Game;
import ro.comanitza.platformer.util.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static ro.comanitza.platformer.util.Constants.Game.*;

public class LevelManager {

    private final Game game;
    private final BufferedImage[] outsideLevelAtlas;

    private final Level levelOne;

    public LevelManager(final Game game) {
        this.game = game;

        outsideLevelAtlas = importOutsideLevelSprites(); //

        levelOne = new Level(LoadSave.getLevelData("/level_one_data.png"));
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

    public void render(final Graphics g) {

        for (int i = 0; i < TILES_IN_HEIGHT; i++) {
            for (int j = 0; j < TILES_IN_WIDTH; j++) {
                int index = levelOne.getSpriteIndex(j, i);
                g.drawImage(outsideLevelAtlas[index], TILES_SIZE * j, TILES_SIZE * i, TILES_SIZE, TILES_SIZE, null);
            }
        }


    }

    public void update() {

    }

    public Level getCurrentLevel() {
        return levelOne;
    }


}

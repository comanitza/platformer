package ro.comanitza.platformer.levels;

import ro.comanitza.platformer.core.Game;
import ro.comanitza.platformer.util.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

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

        for (int i = 0; i < Game.TILES_IN_HEIGHT; i++) {
            for (int j = 0; j < Game.TILES_IN_WIDTH; j++) {
                int index = levelOne.getSpriteIndex(j, i);
                g.drawImage(outsideLevelAtlas[index], Game.TILES_SIZE * j, Game.TILES_SIZE * i, Game.TILES_SIZE, Game.TILES_SIZE, null);
            }
        }


    }

    public void update() {

    }


}

package ro.comanitza.platformer.util;

import ro.comanitza.platformer.core.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import static ro.comanitza.platformer.util.Constants.Game.*;

public class LoadSave {

    private LoadSave() {}


    public static BufferedImage getAtlas(String imagePath) {

        try (InputStream is = LoadSave.class.getResourceAsStream(imagePath)) {
            return ImageIO.read(is);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedImage getPlayerAtlas() {

        return getAtlas("/player_sprites.png");
    }

    public static BufferedImage getLevelAtlas() {

        return getAtlas("/outside_sprites.png");
    }

    public static int[][] getLevelData(final String levelPath) {

        int[][] levelData = new int[TILES_IN_HEIGHT][TILES_IN_WIDTH];

        BufferedImage img = LoadSave.getAtlas(levelPath);

        for (int i = 0; i < img.getHeight(); i++) {

            for (int j = 0; j < img.getWidth(); j++) {

                Color color = new Color(img.getRGB(j, i));

                int colorValue = color.getRed();

                if (colorValue >= 48) {
                    colorValue = 0;
                }

                levelData[i][j] = colorValue;
            }
        }

        return levelData;
    }

    public static BufferedImage getMenuAtlas() {
        return getAtlas("/button_atlas.png");
    }

    public static BufferedImage getMenuBackground() {
        return getAtlas("/menu_background.png");
    }
}

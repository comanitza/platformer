package ro.comanitza.platformer.util;

import ro.comanitza.platformer.entities.Crabby;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

    public static List<Crabby> getCrabbies(final String levelPath) {

        BufferedImage img = LoadSave.getAtlas(levelPath);

        List<Crabby> crabbies = new ArrayList<>();

        for (int i = 0; i < img.getHeight(); i++) {

            for (int j = 0; j < img.getWidth(); j++) {

                Color color = new Color(img.getRGB(j, i));

                int colorValue = color.getGreen();

                if (colorValue == Constants.Enemy.CRABBY) {
                    crabbies.add(new Crabby(j * TILES_SIZE, i * TILES_SIZE));
                }
            }
        }

        return crabbies;
    }

    public static int[][] getLevelData(final String levelPath) {

        BufferedImage img = LoadSave.getAtlas(levelPath);
        int[][] levelData = new int[img.getHeight()][img.getWidth()];

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

    public static BufferedImage getPauseMenuBackground() {
        return getAtlas("/pause_menu.png");
    }

    public static BufferedImage getSoundButtons() {
        return getAtlas("/sound_button.png");
    }

    public static BufferedImage getUrmButtonsAtlas() {
        return getAtlas("/urm_buttons.png");
    }

    public static BufferedImage getPlayingBackground() {
        return getAtlas("/playing_bg_img.png");
    }

    public static BufferedImage getBigClouds() {
        return getAtlas("/big_clouds.png");
    }

    public static BufferedImage getSmallClouds() {
        return getAtlas("/small_clouds.png");
    }

    public static BufferedImage getCrabbyEnemy() {
        return getAtlas("/crabby_sprite.png");
    }
}

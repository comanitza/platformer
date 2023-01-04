package ro.comanitza.platformer.util;

import ro.comanitza.platformer.entities.BlackPirate;
import ro.comanitza.platformer.entities.Crabby;
import ro.comanitza.platformer.entities.Sharky;
import ro.comanitza.platformer.items.Container;
import ro.comanitza.platformer.items.Potion;
import ro.comanitza.platformer.items.Spike;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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

    public static List<Crabby> getCrabbies(BufferedImage img) {

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

    public static List<Potion> getPotions(BufferedImage img) {

        List<Potion> potions = new ArrayList<>();


        for (int i = 0; i < img.getHeight(); i++) {

            for (int j = 0; j < img.getWidth(); j++) {

                Color color = new Color(img.getRGB(j, i));

                int colorValue = color.getBlue();

                if (colorValue == Constants.Items.RED_POTION) {
                    potions.add(new Potion(j * TILES_SIZE, i * TILES_SIZE, Constants.Items.RED_POTION));
                }

                if (colorValue == Constants.Items.BLUE_POTION) {
                    potions.add(new Potion(j * TILES_SIZE, i * TILES_SIZE, Constants.Items.BLUE_POTION));
                }
            }
        }

        return potions;
    }

    public static List<Container> getContainer(BufferedImage img) {

        List<Container> containers = new ArrayList<>();

        for (int i = 0; i < img.getHeight(); i++) {

            for (int j = 0; j < img.getWidth(); j++) {

                Color color = new Color(img.getRGB(j, i));

                int colorValue = color.getBlue();

                if (colorValue == Constants.Items.BOX) {
                    containers.add(new Container(j * TILES_SIZE, i * TILES_SIZE, Constants.Items.BOX));
                }

                if (colorValue == Constants.Items.BARREL) {
                    containers.add(new Container(j * TILES_SIZE, i * TILES_SIZE, Constants.Items.BARREL));
                }
            }
        }

        return containers;
    }

    public static int[][] getLevelData(BufferedImage img) {

//        BufferedImage img = LoadSave.getAtlas(levelPath);
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

    public static BufferedImage getStatusBar() {
        return getAtlas("/health_bar.png");
    }

    public static BufferedImage getLevelCompletedImage() {
        return getAtlas("/completed_sprite.png");
    }

    public static BufferedImage[] getAllLevels() {

        URL levelsUrl = LoadSave.class.getResource("/levels");

        File folder = null;

        try {
            folder = new File(levelsUrl.toURI());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        File[] files = folder.listFiles();

        Arrays.sort(files);

        BufferedImage[] levels = new BufferedImage[files.length];

        for(int i = 0; i < levels.length; i++) {

            File f = files[i];

            try {
                levels[i] = ImageIO.read(f);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return levels;
    }

    public static BufferedImage getContainersImage() {
        return getAtlas("/objects_sprites.png");
    }

    public static BufferedImage getPotionsImage() {
        return getAtlas("/potions_sprites.png");
    }

    public static BufferedImage getSpikeImage() {
        return getAtlas("/trap_atlas.png");
    }

    public static List<Spike> getSpikes(BufferedImage img) {
        List<Spike> spikes = new ArrayList<>();

        for (int i = 0; i < img.getHeight(); i++) {

            for (int j = 0; j < img.getWidth(); j++) {

                Color color = new Color(img.getRGB(j, i));

                int colorValue = color.getBlue();

                if (colorValue == Constants.Items.SPIKE) {
                    spikes.add(new Spike(j * TILES_SIZE, i * TILES_SIZE, Constants.Items.SPIKE));
                }
            }
        }

        return spikes;
    }

    public static BufferedImage getOptionMenuBackground() {
        return getAtlas("/options_background.png");
    }

    public static BufferedImage getSharkyEnemy() {
        return getAtlas("/shark_atlas.png");
    }

    public static List<Sharky> getSharkies(BufferedImage img) {

        List<Sharky> sharkies = new ArrayList<>();

        for (int i = 0; i < img.getHeight(); i++) {

            for (int j = 0; j < img.getWidth(); j++) {

                Color color = new Color(img.getRGB(j, i));

                int colorValue = color.getGreen();

                if (colorValue == Constants.Enemy.SHARKY) {
                    sharkies.add(new Sharky(j * TILES_SIZE, i * TILES_SIZE));
                }
            }
        }

        return sharkies;

    }

    public static BufferedImage getBlackPirate() {
        return getAtlas("/black_pirate.png");
    }

    public static List<BlackPirate> getBlackPirates(BufferedImage img) {

        List<BlackPirate> blackPirates = new ArrayList<>();

        for (int i = 0; i < img.getHeight(); i++) {

            for (int j = 0; j < img.getWidth(); j++) {

                Color color = new Color(img.getRGB(j, i));

                int colorValue = color.getGreen();

                if (colorValue == Constants.Enemy.BLACK_PIRATE) {
                    blackPirates.add(new BlackPirate(j * TILES_SIZE, i * TILES_SIZE));
                }
            }
        }

        return blackPirates;
    }

    public static BufferedImage getMenuBackgroundImage() {
        return getAtlas("/menu-bg.png");
    }
}

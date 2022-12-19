package ro.comanitza.platformer.items;

import ro.comanitza.platformer.gamestates.Playing;
import ro.comanitza.platformer.util.Constants;
import ro.comanitza.platformer.util.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ItemsManager {

    private final Playing playing;

    private final BufferedImage[][] containersImages;
    private final BufferedImage[][] potionsImage;

    private List<Potion> potions = new ArrayList<>();
    private List<Container> containers = new ArrayList<>();


    public ItemsManager(Playing playing) {
        this.playing = playing;

        containersImages = getContainerImages();
        potionsImage = getPotionImages();

        potions.add(new Potion(300, 300, Constants.Items.BLUE_POTION));
        potions.add(new Potion(320, 300, Constants.Items.RED_POTION));

        containers.add(new Container(200, 200, Constants.Items.BOX));
        containers.add(new Container(280, 200, Constants.Items.BARREL));
    }


    private BufferedImage[][] getPotionImages() {

        BufferedImage potionAtlas = LoadSave.getPotionsImage();

        BufferedImage[][] imgs = new BufferedImage[2][7];

        for (int i = 0; i < imgs.length; i++) {

            for (int j = 0; j < imgs[0].length; j++) {
                imgs[i][j] = potionAtlas.getSubimage(j * Constants.Items.POTION_WIDTH_DEFAULT, i * Constants.Items.POTION_HEIGHT_DEFAULT, Constants.Items.POTION_WIDTH_DEFAULT, Constants.Items.POTION_HEIGHT_DEFAULT);
            }
        }

        return imgs;
    }

    private BufferedImage[][] getContainerImages() {

        BufferedImage containerAtlas = LoadSave.getContainersImage();

        BufferedImage[][] imgs = new BufferedImage[2][8];

        for (int i = 0; i < imgs.length; i++) {

            for (int j = 0; j < imgs[0].length; j++) {
                imgs[i][j] = containerAtlas.getSubimage(j * Constants.Items.CONTAINER_WIDTH_DEFAULT, i * Constants.Items.CONTAINER_HEIGHT_DEFAULT, Constants.Items.CONTAINER_WIDTH_DEFAULT, Constants.Items.CONTAINER_HEIGHT_DEFAULT);
            }
        }

        return imgs;
    }

    public void update() {

        for (Potion p: potions) {

            if (p.isActive()) {
                p.update();
            }
        }

        for (Container c: containers) {

            if (c.isActive()) {
                c.update();
            }
        }
    }

    public void render(Graphics g, int levelOffset) {

        renderPotions(g, levelOffset);
        renderContainers(g, levelOffset);
    }

    private void renderContainers(Graphics g, int levelOffset) {

        for (Container c: containers) {

            if (!c.isActive()) {
                continue;
            }

            g.drawImage(containersImages[(c.getItemType() == Constants.Items.BOX) ? 0 : 1][c.getAnimationIndex()],
                    (int)(c.getHitBox().x - c.getXDrawOffset() - levelOffset),
                    (int)(c.getHitBox().y - c.getYDrawOffset()),
                    Constants.Items.CONTAINER_WIDTH,
                    Constants.Items.CONTAINER_HEIGHT,
                    null);
        }
    }

    private void renderPotions(Graphics g, int levelOffset) {

        for (Potion p: potions) {

            if (!p.isActive()) {
                continue;
            }

            g.drawImage(potionsImage[(p.getItemType() == Constants.Items.BLUE_POTION) ? 0 : 1][p.getAnimationIndex()],
                    (int)(p.getHitBox().x - p.getXDrawOffset() - levelOffset),
                    (int)(p.getHitBox().y - p.getYDrawOffset()),
                    Constants.Items.POTION_WIDTH,
                    Constants.Items.POTION_HEIGHT,
                    null);
        }
    }
}

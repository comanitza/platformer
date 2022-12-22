package ro.comanitza.platformer.items;

import ro.comanitza.platformer.entities.Player;
import ro.comanitza.platformer.gamestates.Playing;
import ro.comanitza.platformer.util.Constants;
import ro.comanitza.platformer.util.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ItemsManager {

    private final Playing playing;

    private final BufferedImage[][] containersImages;
    private final BufferedImage[][] potionsImage;
    private final BufferedImage spikesImage;

    private List<Potion> potions;
    private List<Container> containers;
    private List<Spike> spikes;


    public ItemsManager(Playing playing) {
        this.playing = playing;

        containersImages = getContainerImages();
        potionsImage = getPotionImages();
        spikesImage = LoadSave.getSpikeImage();


        loadNextLevel();
    }

    public void loadNextLevel() {
        potions = new ArrayList<>(playing.getLevelManager().getCurrentLevel().getPotions());
        containers = playing.getLevelManager().getCurrentLevel().getContainers();
        spikes = playing.getLevelManager().getCurrentLevel().getSpikes();
    }

    public void checkSpikesTouched(Player player) {
        for (Spike s: spikes) {

            if (s.getHitBox().intersects(player.getHitBox())) {
                player.changeHealth(-20);
            }
        }
    }

    public void checkItemTouchedPlayer(Rectangle2D.Double hitBox) {

        for (Potion p: potions) {

            if (!p.isActive()) {
                continue;
            }

            if (hitBox.intersects(p.getHitBox())) {

                applyEffectToPlayer(p);
                p.setActive(false);
                return;
            }
        }

    }

    private void applyEffectToPlayer(Potion p) {

        if (p.getItemType() == Constants.Items.RED_POTION) {
            playing.getPlayer().changeHealth(Constants.Items.RED_POTION_VALUE);
        }
    }

    public void checkItemHit(Rectangle2D.Double attackBox) {


        for (Container c: containers) {
            if (!c.isActive()) {
                continue;
            }

            if (attackBox.intersects(c.getHitBox())) {
                c.setDoAnimation(true);
                c.setActive(false);

                potions.add(new Potion(
                        (int)(c.getHitBox().getX() + c.getHitBox().getWidth() / 2),
                        (int)(c.getHitBox().getY()),
                        Constants.Items.RED_POTION)
                    );

                return;
            }
        }
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
        renderSpikes(g, levelOffset);
    }

    private void renderSpikes(Graphics g, int levelOffset) {
        for (Spike s: spikes) {
            g.drawImage(spikesImage, (int)(s.getHitBox().getX() - levelOffset), (int)(s.getHitBox().getY() - s.getYDrawOffset()), Constants.Items.SPIKE_WIDTH, Constants.Items.SPIKE_HEIGHT, null);
        }
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

    public void resetAllItems() {

        potions.clear();

        potions.addAll(playing.getLevelManager().getCurrentLevel().getPotions());

        for(Potion p: potions) {
            p.reset();
        }

        for (Container c: containers) {
            c.reset();
        }
    }
}

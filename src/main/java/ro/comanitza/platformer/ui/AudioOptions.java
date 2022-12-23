package ro.comanitza.platformer.ui;

import ro.comanitza.platformer.gamestates.GameState;
import ro.comanitza.platformer.util.Constants;

import java.awt.*;
import java.awt.event.MouseEvent;

import static ro.comanitza.platformer.util.Utils.isButtonOver;

public class AudioOptions {

    private final SoundButton musicButton;
    private final SoundButton sfxButton;

    public AudioOptions() {

        musicButton = new SoundButton((int)(450 * Constants.Game.SCALE), (int)(140 * Constants.Game.SCALE), Constants.UI.PauseButtons.SOUND_BUTTON_SIZE, Constants.UI.PauseButtons.SOUND_BUTTON_SIZE);
        sfxButton = new SoundButton((int)(450 * Constants.Game.SCALE), (int)(186 * Constants.Game.SCALE), Constants.UI.PauseButtons.SOUND_BUTTON_SIZE, Constants.UI.PauseButtons.SOUND_BUTTON_SIZE);
    }

    public void update () {
        musicButton.update();
        sfxButton.update();
    }

    public void render(Graphics g) {
        musicButton.render(g);
        sfxButton.render(g);
    }

    public void mousePressed(MouseEvent e) {

        if (isButtonOver(e, musicButton)) {
            musicButton.setMousePressed(true);
        } else if (isButtonOver(e, sfxButton)) {
            sfxButton.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {

        if (isButtonOver(e, musicButton)) {

            if (musicButton.isMousePressed()) {
                musicButton.setMuted(!musicButton.isMuted());
            }

        } else if (isButtonOver(e, sfxButton)) {

            if (sfxButton.isMousePressed()) {
                sfxButton.setMuted(!sfxButton.isMuted());
            }
        }

        musicButton.resetBooleans();
        sfxButton.resetBooleans();
    }

    public void mouseMoved(MouseEvent e) {

        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);



        if (isButtonOver(e, musicButton)) {
            musicButton.setMouseOver(true);
        } else if (isButtonOver(e, sfxButton)) {
            sfxButton.setMouseOver(true);
        }
    }
}

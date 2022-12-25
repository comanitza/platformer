package ro.comanitza.platformer.audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class AudioPlayer {

    public static final int MENU_1 = 0;
    public static final int LEVEL_1 = 1;
    public static final int LEVEL_2 = 2;

    public static final int DIE = 0;
    public static final int JUMP = 1;
    public static final int GAME_OVER = 2;
    public static final int LEVEL_COMPLETED = 3;
    public static final int ATTACK_ONE = 4;
    public static final int ATTACK_TWO = 5;
    public static final int ATTACK_THREE = 6;

    private static final double musicVolume = 0.6d;
    private static final double effectsVolume = 0.8d;

    private final Clip[] music;
    private final Clip[] effects;

    private int currentMusicIndex;

    private boolean musicMuted;
    private boolean effectsMuted;

    private final Random rand = new Random();

    public AudioPlayer() {
        music = new Clip[3];

        music[MENU_1] = getClip("menu.wav");
        music[LEVEL_1] = getClip("level1.wav");
        music[LEVEL_2] = getClip("level2.wav");

        effects = new Clip[7];

        effects[DIE] = getClip("die.wav");
        effects[JUMP] = getClip("jump.wav");
        effects[GAME_OVER] = getClip("gameover.wav");
        effects[LEVEL_COMPLETED] = getClip("levelcompleted.wav");
        effects[ATTACK_ONE] = getClip("attack1.wav");
        effects[ATTACK_TWO] = getClip("attack2.wav");
        effects[ATTACK_THREE] = getClip("attack3.wav");

        updateMusicVolume();
        updateEffectsVolume();

        playMusic(MENU_1);
    }

    private Clip getClip(String fileName) {
        URL url = getClass().getResource("/audio/" + fileName);

        try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url)) {
            Clip clip = AudioSystem.getClip();

            clip.open(audioInputStream);

            return clip;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void updateMusicVolume() {

        for (Clip c: music) {
            FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            double range = gainControl.getMaximum() - gainControl.getMinimum();
            double gain = (range * musicVolume) + gainControl.getMinimum();

            gainControl.setValue((float) gain);
        }
    }

    private void updateEffectsVolume() {

        for(Clip c: effects) {

            FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            double range = gainControl.getMaximum() - gainControl.getMinimum();
            double gain = (range * effectsVolume) + gainControl.getMinimum();

            gainControl.setValue((float)gain);
        }
    }

    public void toggleMusicMute() {

        this.musicMuted = !musicMuted;

        for(Clip c: music) {
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(musicMuted);
        }
    }

    public void toggleEffectsMute() {

        this.effectsMuted = !effectsMuted;

        for(Clip c: effects) {
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(effectsMuted);
        }

        if (!effectsMuted) {
            playEffect(JUMP);
        }
    }

    public void playEffect(int effect) {
        effects[effect].setMicrosecondPosition(0L);
        effects[effect].start();
    }

    public void playMusic(int musicIndex) {
        stopMusic();

        currentMusicIndex = musicIndex;

        music[currentMusicIndex].setMicrosecondPosition(0L);
        music[currentMusicIndex].loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void playAttackSound() {

        int r = rand.nextInt(3);

        playEffect(ATTACK_ONE + r);
    }

    public void stopMusic() {

        if (music[currentMusicIndex].isActive()) {
            music[currentMusicIndex].stop();
        }
    }

    public void setLevelMusic(int levelIndex) {

        if (levelIndex % 2 == 0) {
            playMusic(LEVEL_1);
        } else {
            playMusic(LEVEL_2);
        }
    }

    public void levelCompleted() {
        stopMusic();
        playEffect(LEVEL_COMPLETED);
    }
}

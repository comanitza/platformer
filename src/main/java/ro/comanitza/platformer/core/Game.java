package ro.comanitza.platformer.core;

import ro.comanitza.platformer.gamestates.GameState;
import ro.comanitza.platformer.gamestates.Menu;
import ro.comanitza.platformer.gamestates.Playing;
import ro.comanitza.platformer.ui.AudioOptions;
import ro.comanitza.platformer.ui.GameOptions;


import java.awt.*;

public class Game {

    private final GameWindow window;
    private final GamePanel gamePanel;

    private final Playing playing;
    private final Menu menu;
    private final GameOptions gameOptions;
    private final AudioOptions audioOptions;

    public Game () {

        audioOptions = new AudioOptions();
        playing = new Playing(this);
        menu = new Menu(this);
        gameOptions = new GameOptions(this);

        gamePanel = new GamePanel(this);
        window = new GameWindow(gamePanel);

        /*
         * this line should be under the GameWindow creation
         */
        gamePanel.requestFocus();

        /*
         * start the game loop
         */
        startGameLoop();
    }

    public void render(Graphics g) {

        switch (GameState.gameState) {

            case PLAYING:
                playing.draw(g);
                break;
            case MENU:
                menu.draw(g);
                break;
            case OPTION:
                gameOptions.draw(g);
                break;
            default:
                break;
        }
    }

    private void startGameLoop () {

        new Thread(new GameLoop(gamePanel, playing, menu), "game-loop-thread").start();
    }

    public void windowFocusLost() {

        if (GameState.gameState == GameState.PLAYING) {
            playing.windowFocusLost();
        }
    }

    public Playing getPlaying() {
        return playing;
    }

    public Menu getMenu() {
        return menu;
    }

    public AudioOptions getAudioOptions() {
        return audioOptions;
    }

    public GameOptions getGameOptions() {
        return gameOptions;
    }
}

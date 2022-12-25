package ro.comanitza.platformer.gamestates;

import ro.comanitza.platformer.audio.AudioPlayer;
import ro.comanitza.platformer.core.Game;

public enum GameState {

    PLAYING, MENU, OPTION, QUIT;

    public static GameState gameState = MENU;

    public static void setGameState(GameState gameState, Game game) {
        GameState.gameState = gameState;

        switch (gameState) {
            case MENU -> game.getAudioPlayer().playMusic(AudioPlayer.MENU_1);
            case PLAYING -> game.getAudioPlayer().setLevelMusic(game.getPlaying().getLevelManager().getCurrentLevelIndex());
        }
    }
}

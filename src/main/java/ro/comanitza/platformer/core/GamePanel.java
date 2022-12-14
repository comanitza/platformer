package ro.comanitza.platformer.core;

import ro.comanitza.platformer.input.KeyboardInputs;
import ro.comanitza.platformer.input.MouseInputs;
import ro.comanitza.platformer.util.Constants;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private final Game game;

    public GamePanel(final Game game) {

        this.game = game;

        MouseInputs mouseInputs = new MouseInputs(this);

        setPanelSize();

        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    public void setPanelSize() {
        setPreferredSize(new Dimension(Constants.Game.GAME_WIDTH, Constants.Game.GAME_HEIGHT));
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        /*
         * this must be present, or else we will not move
         */
        setFocusable(true);
        requestFocus(true);

        game.render(g);
    }

    public Game getGame() {
        return game;
    }
}

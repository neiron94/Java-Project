package ui.selection;

import creatures.Player;
import gamestates.Play;
import gamestates.Selection;
import levels.Level;
import main.Game;
import main.Main;
import my_utils.Constants;
import my_utils.Images;
import my_utils.LoadSave;
import my_utils.MyLogger;
import ui.Button;

import java.awt.*;
import java.io.IOException;

import static my_utils.Constants.ButtonTypes.*;
import static my_utils.Constants.UIConstants.SelectionConstants.*;

/**
 * Button on the Selection screen. Load a level and a player and goes to the Play when is clicked.
 */
public class SelectionPlayButton extends Button {
    private static final MyLogger LOGGER = new MyLogger(SelectionPlayButton.class);

    public SelectionPlayButton() {
        super(PLAY_BUTTON_X, PLAY_BUTTON_Y, REAL_BUTTON_WIDTH, REAL_BUTTON_HEIGHT);
    }

    @Override
    public void render(Graphics g) { render(g, Images.getButtons()[getIndexByButtonType(PLAY)][0], Images.getButtons()[getIndexByButtonType(PLAY)][1]); }

    /**
     * Loads level and player from files and starts the game.
     */
    @Override
    public void doWhenClicked() {
        if (Selection.getChosenCharacter() == null || Selection.getChosenLevel() == null)
            return;

        // Load level
        Level level = null;
        try {
            level = LoadSave.loadLevel(Selection.getChosenLevel());
        }
        catch (IOException | ClassNotFoundException e) {
            LOGGER.fatal("Can't load the level from the file " + Selection.getChosenLevel());
            Main.fatalExit(e);
        }

        // Load player
        Player player = null;
        try {
            if (Selection.getChosenCharacter().equals("New character.ser"))
                player = new Player();
            else
                player = LoadSave.loadPlayer(Selection.getChosenCharacter());
        }
        catch (IOException | ClassNotFoundException e) {
            LOGGER.fatal("Can't load the player from the file " + Selection.getChosenCharacter());
            Main.fatalExit(e);
        }

        // Set level and player
        Play.setLevel(level);
        Play.setPlayer(player);

        // Remove all labels
        for (SelectionCharacterButton button : Selection.getCharacterButtons())
            Game.getGamePanel().remove(button.getLabel());

        for (SelectionLevelButton button : Selection.getLevelButtons())
            Game.getGamePanel().remove(button.getLabel());
        // Go to the Play
        Game.state = Constants.StatesEnum.PLAY;
    }
}

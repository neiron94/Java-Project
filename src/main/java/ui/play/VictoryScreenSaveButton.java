package ui.play;

import gamestates.Play;
import main.Game;
import my_utils.Constants;
import my_utils.Images;
import my_utils.LoadSave;
import ui.Button;

import java.awt.*;

import static my_utils.Constants.ButtonTypes.*;
import static my_utils.Constants.UIConstants.PlayConstants.*;

/**
 * Button on the Victory screen. Goes to the Menu after saving the player when is clicked.
 */
public class VictoryScreenSaveButton extends Button {
    public VictoryScreenSaveButton() {
        super(SAVE_BUTTON_X, SAVE_BUTTON_Y, REAL_SAVE_BUTTON_WIDTH, REAL_SAVE_BUTTON_HEIGHT);
    }

    @Override
    public void render(Graphics g) {
        render(g, Images.getButtons()[getIndexByButtonType(SAVE)][0], Images.getButtons()[getIndexByButtonType(SAVE)][1]);
    }

    /**
     * Saves the player to the file and goes to the Menu.
     */
    @Override
    public void doWhenClicked() {
        if (VictoryScreen.getTextField().getText().equals("") || VictoryScreen.getTextField().getText().equals("New character"))
            return;

        Play.getPlayer().setHasKey(false);
        LoadSave.savePlayer(Play.getPlayer(), VictoryScreen.getTextField().getText());

        Game.getGamePanel().remove(VictoryScreen.getTextField());
        Play.setVictory(false);
        Game.state = Constants.StatesEnum.MENU;
    }
}

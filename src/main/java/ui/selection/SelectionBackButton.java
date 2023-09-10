package ui.selection;

import gamestates.Selection;
import main.Game;
import my_utils.Constants;
import my_utils.Images;
import ui.Button;

import java.awt.*;

import static my_utils.Constants.ButtonTypes.*;
import static my_utils.Constants.UIConstants.SelectionConstants.*;

/**
 * Button on the Selection screen. Goes to the Menu when is clicked.
 */
public class SelectionBackButton extends Button {
    public SelectionBackButton() {
        super(BACK_BUTTON_X, BACK_BUTTON_Y, REAL_BUTTON_WIDTH, REAL_BUTTON_HEIGHT);
    }

    @Override
    public void render(Graphics g) { render(g, Images.getButtons()[getIndexByButtonType(BACK)][0], Images.getButtons()[getIndexByButtonType(BACK)][1]); }

    /**
     * Removes labels from the window and goes to the Menu.
     */
    @Override
    public void doWhenClicked() {
        for (SelectionCharacterButton button : Selection.getCharacterButtons())
            Game.getGamePanel().remove(button.getLabel());

        for (SelectionLevelButton button : Selection.getLevelButtons())
            Game.getGamePanel().remove(button.getLabel());

        Game.state = Constants.StatesEnum.MENU;
    }
}

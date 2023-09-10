package ui.menu;

import gamestates.Selection;
import main.Game;
import my_utils.Constants;
import my_utils.Images;
import ui.selection.SelectionCharacterButton;
import ui.selection.SelectionLevelButton;

import java.awt.*;

import static my_utils.Constants.ButtonTypes.*;
import static my_utils.Constants.UIConstants.MenuConstants.*;

/**
 * Button in the Menu. Goes to the Selection screen when is clicked.
 */
public class MenuPlayButton extends MenuButton {

    public MenuPlayButton() {
        super(BUTTON_X, PLAY_BUTTON_Y);
    }

    @Override
    public void render(Graphics g) {
        render(g, Images.getButtons()[getIndexByButtonType(PLAY)][0], Images.getButtons()[getIndexByButtonType(PLAY)][1]);
    }

    @Override
    protected void updateGameState() { Game.state = Constants.StatesEnum.SELECTION; }

    /**
     * Goes to the Selection screen and refreshes it.
     */
    @Override
    public void doWhenClicked() {
        super.doWhenClicked();

        Selection.getInstance().update();

        Selection.setChosenLevel(null);
        Selection.setChosenCharacter(null);

        for (SelectionCharacterButton button : Selection.getCharacterButtons()) {
            button.setChosen(false);
            Game.getGamePanel().add(button.getLabel());
        }

        for (SelectionLevelButton button : Selection.getLevelButtons()) {
            button.setChosen(false);
            Game.getGamePanel().add(button.getLabel());
        }
    }
}

package ui.menu;

import main.Game;
import my_utils.Constants;
import my_utils.Images;

import java.awt.*;

import static my_utils.Constants.ButtonTypes.*;
import static my_utils.Constants.UIConstants.MenuConstants.*;

/**
 * Button in the Menu. Goes to the "Editor mode" when is clicked.
 */
public class MenuEditorButton extends MenuButton {
    public MenuEditorButton() {
        super(BUTTON_X, EDITOR_BUTTON_Y);
    }

    @Override
    public void render(Graphics g) {
        render(g, Images.getButtons()[getIndexByButtonType(EDITOR)][0], Images.getButtons()[getIndexByButtonType(EDITOR)][1]);
    }

    @Override
    protected void updateGameState() { Game.state = Constants.StatesEnum.EDITOR; }
}

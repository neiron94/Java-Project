package ui.menu;

import main.Game;
import my_utils.Constants;
import my_utils.Images;

import java.awt.*;

import static my_utils.Constants.ButtonTypes.*;
import static my_utils.Constants.UIConstants.MenuConstants.*;

/**
 * Button in the Menu. Ends the application when is clicked.
 */
public class MenuExitButton extends MenuButton {
    public MenuExitButton() {
        super(BUTTON_X, EXIT_BUTTON_Y);
    }

    @Override
    public void render(Graphics g) {
        render(g, Images.getButtons()[getIndexByButtonType(EXIT)][0], Images.getButtons()[getIndexByButtonType(EXIT)][1]);
    }

    @Override
    protected void updateGameState() { Game.state = Constants.StatesEnum.EXIT; }
}

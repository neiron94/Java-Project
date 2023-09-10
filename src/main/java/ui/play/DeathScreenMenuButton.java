package ui.play;

import main.Game;
import my_utils.Constants;
import my_utils.Images;
import ui.Button;

import java.awt.*;

import static my_utils.Constants.ButtonTypes.*;
import static my_utils.Constants.UIConstants.PlayConstants.*;

/**
 * Button on the Death screen. Goes to the Menu without saving the player when is clicked.
 */
public class DeathScreenMenuButton extends Button {
    public DeathScreenMenuButton() {
        super(MENU_BUTTON_X, MENU_BUTTON_Y, REAL_MENU_BUTTON_WIDTH, REAL_MENU_BUTTON_HEIGHT);
    }

    @Override
    public void render(Graphics g) {
        render(g, Images.getButtons()[getIndexByButtonType(MENU)][0], Images.getButtons()[getIndexByButtonType(MENU)][1]);
    }

    /**
     * Goes to the menu without saving the player.
     */
    @Override
    public void doWhenClicked() {
        Game.state = Constants.StatesEnum.MENU;
    }
}

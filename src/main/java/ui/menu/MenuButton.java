package ui.menu;

import ui.Button;

import static my_utils.Constants.UIConstants.MenuConstants.*;

/**
 * This class is common for all buttons in the Menu.
 */
public abstract class MenuButton extends Button {
    public MenuButton(float x, float y) {
        super(x, y, REAL_BUTTON_WIDTH, REAL_BUTTON_HEIGHT);
    }

    /**
     * Sets new game state.
     */
    @Override
    public void doWhenClicked() { updateGameState(); }

    protected abstract void updateGameState();
}

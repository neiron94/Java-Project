package ui.editor;

import gamestates.Editor;
import gamestates.editor_stages.SavingStage;
import main.Game;
import my_utils.Constants;
import my_utils.Images;
import my_utils.LoadSave;
import ui.Button;

import java.awt.*;

import static my_utils.Constants.ButtonTypes.*;
import static my_utils.Constants.UIConstants.EditorConstants.*;

/**
 * Button in the "Saving stage" of the "Editor mode". Saves the level when is clicked.
 */
public class EditorSaveButton extends Button {

    public EditorSaveButton() {
        super(SAVE_BUTTON_X, SAVE_BUTTON_Y, REAL_SAVE_BUTTON_WIDTH, REAL_SAVE_BUTTON_HEIGHT);
    }

    @Override
    public void render(Graphics g) {
        render(g, Images.getButtons()[getIndexByButtonType(SAVE)][0], Images.getButtons()[getIndexByButtonType(SAVE)][1]);
    }

    /**
     * Saves the level to the file, makes the "Back button" action and goes to the Menu.
     */
    @Override
    public void doWhenClicked() {
        LoadSave.saveLevel(Editor.getLevel(), SavingStage.getTextField().getText());
        SavingStage.getBackButton().doWhenClicked();
        Game.state = Constants.StatesEnum.MENU;
        Editor.setFirstUpdate(true);
    }
}

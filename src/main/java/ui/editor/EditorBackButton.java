package ui.editor;

import gamestates.Editor;
import gamestates.editor_stages.SavingStage;
import main.Game;
import my_utils.Images;
import ui.Button;

import java.awt.*;

import static my_utils.Constants.EditStages.CREATING;
import static my_utils.Constants.UIConstants.EditorConstants.*;
import static my_utils.Constants.ButtonTypes.*;

/**
 * Button in the "Saving stage" of the "Editor mode". Goes to the "Creation stage" when is clicked.
 */
public class EditorBackButton extends Button {

    public EditorBackButton() {
        super(BACK_BUTTON_X, BACK_BUTTON_Y, REAL_BACK_BUTTON_WIDTH, REAL_BACK_BUTTON_HEIGHT);
    }

    @Override
    public void render(Graphics g) { render(g, Images.getButtons()[getIndexByButtonType(BACK)][0], Images.getButtons()[getIndexByButtonType(BACK)][1]); }

    /**
     * Goes from the "Saving stage" in the "Editor mode" to the "Creation stage".
     */
    @Override
    public void doWhenClicked() {
        Editor.setStage(CREATING);
        Game.getGamePanel().remove(SavingStage.getTextField());
        SavingStage.getTextField().setText("");
    }
}

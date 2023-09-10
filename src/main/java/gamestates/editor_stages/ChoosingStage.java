package gamestates.editor_stages;

import gamestates.Editor;
import gamestates.GameState;
import my_utils.HelpMethods;
import my_utils.Images;
import ui.editor.EditorChooseButton;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static my_utils.Constants.Entities.*;
import static my_utils.Constants.UIConstants.EditorConstants.*;
import static my_utils.HelpMethods.isMouseOverButton;
import static my_utils.Constants.EditStages.*;

/**
 * Singleton class which represents the "Choosing stage" of the "Editor mode", where entity for the "Creation stage" can be chosen.
 */
public class ChoosingStage implements GameState {
    private static ChoosingStage stageInstance = null;

    private static final List<EditorChooseButton> choosingButtons;
    static {
        choosingButtons = new ArrayList<>();
        choosingButtons.add(new EditorChooseButton(CHOOSING_BUTTON_X1, CHOOSING_BUTTON_Y, SPEARMAN));
        choosingButtons.add(new EditorChooseButton(CHOOSING_BUTTON_X2, CHOOSING_BUTTON_Y, KNIGHT));
        choosingButtons.add(new EditorChooseButton(CHOOSING_BUTTON_X3, CHOOSING_BUTTON_Y, BARREL));
        choosingButtons.add(new EditorChooseButton(CHOOSING_BUTTON_X4, CHOOSING_BUTTON_Y, STONE));
    }

    private ChoosingStage() {}

    public static ChoosingStage getInstance() {
        if (stageInstance == null)
            stageInstance = new ChoosingStage();

        return stageInstance;
    }

    /*--------------------------------------Methods for rendering--------------------------------------*/

    public void render(Graphics g) {
        g.drawImage(Images.getEditorChoosingFrame(), (int)CHOOSING_FRAME_X, (int)CHOOSING_FRAME_Y,
                (int)REAL_CHOOSING_FRAME_WIDTH, (int)REAL_CHOOSING_FRAME_HEIGHT, null);
        for (EditorChooseButton button : choosingButtons)
            button.render(g);
    }

    /*---------------------------------Methods for working with input---------------------------------*/

    @Override
    public void mousePressed(MouseEvent e) {
        for (EditorChooseButton button : choosingButtons)
            button.setPressed(isMouseOverButton(button, e));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (EditorChooseButton button : choosingButtons)
            HelpMethods.buttonReleased(button, isMouseOverButton(button, e) && button.isPressed());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (EditorChooseButton button : choosingButtons)
            button.setMouseAbove(isMouseOverButton(button, e));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ESCAPE)
            Editor.setStage(CREATING);
    }

    /*---------------------------------------Not used methods---------------------------------------*/

    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void update() {}
    @Override
    public void mouseClicked(MouseEvent e) {}
}

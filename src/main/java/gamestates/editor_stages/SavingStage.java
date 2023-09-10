package gamestates.editor_stages;

import gamestates.Editor;
import gamestates.GameState;
import my_utils.HelpMethods;
import my_utils.Images;
import ui.editor.EditorBackButton;
import ui.editor.EditorSaveButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static my_utils.Constants.UIConstants.EditorConstants.*;
import static my_utils.Constants.WindowConstants.SCALE;
import static my_utils.HelpMethods.isMouseOverButton;

/**
 * Singleton class which represents the "Saving stage" of the "Editor mode", where created level can be saved to the file.
 */
public class SavingStage implements GameState {
    private static SavingStage stageInstance = null;

    private static final JTextField textField;
    private static final EditorSaveButton saveButton;
    private static final EditorBackButton backButton;
    static {
        saveButton = new EditorSaveButton();
        backButton = new EditorBackButton();
        textField = new JTextField();
        textField.setBackground(new Color(100, 65, 50));
        textField.setForeground(Color.BLACK);
        textField.setFont(new Font(null, Font.PLAIN, (int)(20 * SCALE)));
        textField.setBounds((int)TEXT_FIELD_X, (int)TEXT_FIELD_Y, (int)REAL_TEXT_FIELD_WIDTH, (int)REAL_TEXT_FIELD_HEIGHT);
    }

    private SavingStage() {}

    public static SavingStage getInstance() {
        if (stageInstance == null)
            stageInstance = new SavingStage();

        return stageInstance;
    }

    /*--------------------------------------Methods for rendering--------------------------------------*/

    public void render(Graphics g) {
        g.drawImage(Images.getEditorSavingFrame(), (int)SAVING_FRAME_X, (int)SAVING_FRAME_Y,
                (int)REAL_SAVING_FRAME_WIDTH, (int)REAL_SAVING_FRAME_HEIGHT, null);
        saveButton.render(g);
        backButton.render(g);
    }

    /*---------------------------------Methods for working with input---------------------------------*/

    @Override
    public void mousePressed(MouseEvent e) {
        saveButton.setPressed(isMouseOverButton(saveButton, e));
        backButton.setPressed(isMouseOverButton(backButton, e));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        boolean saveCondition = isMouseOverButton(saveButton, e) && saveButton.isPressed() &&
                !textField.getText().equals("") && !textField.getText().equals("New character") && Editor.getLevel().isFinalRoomAlreadyExist();
        HelpMethods.buttonReleased(saveButton, saveCondition);

        boolean backCondition = isMouseOverButton(backButton, e) && backButton.isPressed();
        HelpMethods.buttonReleased(backButton, backCondition);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        saveButton.setMouseAbove(isMouseOverButton(saveButton, e));
        backButton.setMouseAbove(isMouseOverButton(backButton, e));
    }

    /*---------------------------------------Getters and setters---------------------------------------*/

    public static EditorBackButton getBackButton() { return backButton; }
    public static JTextField getTextField() { return textField; }

    /*---------------------------------------Not used methods---------------------------------------*/

    @Override
    public void update() {}
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}

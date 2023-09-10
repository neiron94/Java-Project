package ui.play;

import my_utils.HelpMethods;
import my_utils.Images;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

import static my_utils.Constants.UIConstants.PlayConstants.*;
import static my_utils.Constants.WindowConstants.SCALE;
import static my_utils.HelpMethods.isMouseOverButton;

/**
 * Singleton class which represents a screen shown after player's victory.
 */
public class VictoryScreen {
    private static VictoryScreen victoryScreenInstance = null;

    private static final VictoryScreenSaveButton saveButton;
    private static final JTextField textField;
    static {
        saveButton = new VictoryScreenSaveButton();
        textField = new JTextField();
        textField.setBackground(new Color(100, 65, 50));
        textField.setForeground(Color.BLACK);
        textField.setFont(new Font(null, Font.PLAIN, (int)(15 * SCALE)));
        textField.setBounds((int)TEXT_FIELD_X, (int)TEXT_FIELD_Y, (int)REAL_TEXT_FIELD_WIDTH, (int)REAL_TEXT_FIELD_HEIGHT);
    }

    public static VictoryScreen getInstance() {
        if (victoryScreenInstance == null)
            victoryScreenInstance = new VictoryScreen();

        return victoryScreenInstance;
    }

    private VictoryScreen() {}

    public void render(Graphics g) {
        g.drawImage(Images.getVictoryScreenFrame(), (int) VICTORY_SCREEN_X, (int) VICTORY_SCREEN_Y, (int)VICTORY_SCREEN_WIDTH, (int)VICTORY_SCREEN_HEIGHT, null);
        saveButton.render(g);
    }


    public void mousePressed(MouseEvent e) { saveButton.setPressed(isMouseOverButton(saveButton, e)); }

    public void mouseReleased(MouseEvent e) {
        HelpMethods.buttonReleased(saveButton, isMouseOverButton(saveButton, e) && saveButton.isPressed());
    }

    public void mouseMoved(MouseEvent e) { saveButton.setMouseAbove(isMouseOverButton(saveButton, e)); }

    public static JTextField getTextField() { return textField; }
}

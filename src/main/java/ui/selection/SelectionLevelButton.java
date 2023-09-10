package ui.selection;

import gamestates.Selection;
import ui.Button;

import javax.swing.*;
import java.awt.*;

import static my_utils.Constants.UIConstants.SelectionConstants.*;
import static my_utils.Constants.WindowConstants.SCALE;

/**
 * Button on the Selection screen. Represents one of the possible level object files, which can be loaded.
 */
public class SelectionLevelButton extends Button {
    private final String name;
    private final JLabel label;
    private boolean isChosen;

    /**
     * Configures a button and creates a label for it.
     * @param filename name of the file with level object.
     * @param index index of this button.
     */
    public SelectionLevelButton(String filename, int index) {
        super(LEVEL_BUTTON_X, LEVEL_BUTTON_Y + index * LEVEL_BUTTON_HEIGHT, LEVEL_BUTTON_WIDTH, LEVEL_BUTTON_HEIGHT);
        isChosen = false;
        this.name = filename.substring(0, filename.lastIndexOf(".ser"));
        label = new JLabel(" " + name);
        label.setFont(new Font(null, Font.PLAIN, (int)(10 * SCALE)));
        label.setBounds((int)x, (int)y, (int)hitBox.width, (int)hitBox.height);
    }

    @Override
    public void render(Graphics g) {
        if (isChosen || isMouseAbove || isPressed) {
            g.setColor(Color.BLACK);
            g.fillRect((int)x, (int)y, (int)hitBox.width, (int)hitBox.height);
        }
    }

    /**
     * Sets chosen level to this.name.
     */
    @Override
    public void doWhenClicked() {
        Selection.setChosenLevel(name + ".ser");
        isChosen = true;
        // Only one button can be chosen
        for (SelectionLevelButton button : Selection.getLevelButtons())
            if (button != this)
                button.setChosen(false);
    }

    public void setChosen(boolean chosen) {
        isChosen = chosen;
    }

    public JLabel getLabel() {
        return label;
    }
}

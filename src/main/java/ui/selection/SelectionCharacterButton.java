package ui.selection;

import gamestates.Selection;
import ui.Button;

import javax.swing.*;
import java.awt.*;

import static my_utils.Constants.UIConstants.SelectionConstants.*;
import static my_utils.Constants.WindowConstants.SCALE;

/**
 * Button on the Selection screen. Represents one of the possible player object files, which can be loaded.
 */
public class SelectionCharacterButton extends Button {
    private final String name;
    private final JLabel label;
    private boolean isChosen;

    /**
     * Configures a button and creates a label for it.
     * @param filename name of the file with player object.
     * @param index index of this button.
     */
    public SelectionCharacterButton(String filename, int index) {
        super(CHARACTER_BUTTON_X, CHARACTER_BUTTON_Y + index * CHARACTER_BUTTON_HEIGHT, CHARACTER_BUTTON_WIDTH, CHARACTER_BUTTON_HEIGHT);
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
     * Sets chosen character to this.name.
     */
    @Override
    public void doWhenClicked() {
        Selection.setChosenCharacter(name + ".ser");
        isChosen = true;
        // Only one button can be chosen
        for (SelectionCharacterButton button : Selection.getCharacterButtons())
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

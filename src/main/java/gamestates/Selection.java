package gamestates;

import my_utils.HelpMethods;
import my_utils.Images;
import ui.Button;
import ui.selection.SelectionBackButton;
import ui.selection.SelectionCharacterButton;
import ui.selection.SelectionLevelButton;
import ui.selection.SelectionPlayButton;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static my_utils.Constants.UIConstants.SelectionConstants.*;
import static my_utils.Constants.WindowConstants.*;
import static my_utils.HelpMethods.isMouseOverButton;

/**
 * Singleton class which represents state where player and level are selected for the game.
 */
public class Selection implements GameState {
    private static Selection selectionInstance = null;

    private static final SelectionBackButton backButton;
    private static final SelectionPlayButton playButton;
    private static List<SelectionLevelButton> levelButtons;
    private static List<SelectionCharacterButton> characterButtons;

    private static int currentLevelFilesCount;
    private static int currentCharacterFilesCount;

    private static String chosenLevel = null;
    private static String chosenCharacter = null;

    static {
        backButton = new SelectionBackButton();
        playButton = new SelectionPlayButton();
        initLevelButtons();
        initCharacterButtons();
    }

    public static Selection getInstance() {
        if (selectionInstance == null)
            selectionInstance = new Selection();

        return selectionInstance;
    }

    private Selection() {}

    private static void initCharacterButtons() {
        currentCharacterFilesCount = 0;
        characterButtons = new ArrayList<>();

        // Add "special" button
        SelectionCharacterButton newCharacterButton = new SelectionCharacterButton("New character.ser", currentCharacterFilesCount);
        characterButtons.add(newCharacterButton);

        File[] files = CHARACTERS_FOLDER.listFiles();
        if (files == null)
            return;

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".ser")) {
                currentCharacterFilesCount++;
                SelectionCharacterButton newButton = new SelectionCharacterButton(file.getName(), currentCharacterFilesCount);
                characterButtons.add(newButton);
            }
        }
    }

    private static void initLevelButtons() {
        currentLevelFilesCount = 0;
        levelButtons = new ArrayList<>();

        File[] files = LEVELS_FOLDER.listFiles();
        if (files == null)
            return;

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".ser")) {
                SelectionLevelButton newButton = new SelectionLevelButton(file.getName(), currentLevelFilesCount);
                levelButtons.add(newButton);
                currentLevelFilesCount++;
            }
        }
    }

    /*--------------------------------------Methods for rendering--------------------------------------*/

    @Override
    public void render(Graphics g) {
        renderBackground(g);
        renderButtons(g);
    }

    private void renderBackground(Graphics g) {
        // Background image
        g.drawImage(Images.getSelectionBackground(), 0,0, REAL_WINDOW_WIDTH, REAL_WINDOW_HEIGHT, null);
        // Selection frame image
        g.drawImage(Images.getSelectionFrame(), SELECTION_FRAME_X, SELECTION_FRAME_Y, SELECTION_FRAME_WIDTH, SELECTION_FRAME_HEIGHT, null);
        // Background of panel with selection buttons
        for (int i = 0; i < MAX_BUTTONS_AMOUNT; i++) {
            if (i % 2 == 1) g.setColor(PANEL_COLOR_1);
            else            g.setColor(PANEL_COLOR_2);

            g.fillRect(LEVEL_BUTTON_X, LEVEL_BUTTON_Y + i * LEVEL_BUTTON_HEIGHT, LEVEL_BUTTON_WIDTH, LEVEL_BUTTON_HEIGHT);
            g.fillRect(CHARACTER_BUTTON_X, CHARACTER_BUTTON_Y + i * CHARACTER_BUTTON_HEIGHT, CHARACTER_BUTTON_WIDTH, CHARACTER_BUTTON_HEIGHT);
        }
        g.setColor(Color.BLACK);
        g.drawRect(LEVEL_BUTTON_X, LEVEL_BUTTON_Y, LEVEL_BUTTON_WIDTH, LEVEL_BUTTON_HEIGHT * MAX_BUTTONS_AMOUNT);
        g.drawRect(CHARACTER_BUTTON_X, CHARACTER_BUTTON_Y, CHARACTER_BUTTON_WIDTH, CHARACTER_BUTTON_HEIGHT * MAX_BUTTONS_AMOUNT);
    }

    private void renderButtons(Graphics g) {
        backButton.render(g);
        playButton.render(g);

        for (SelectionCharacterButton characterButton : characterButtons) characterButton.render(g);
        for (SelectionLevelButton levelButton : levelButtons) levelButton.render(g);
    }

    /*-------------------------------------Methods for updating-------------------------------------*/

    @Override
    public void update() {
        // Re-init buttons if new files are found
        if (currentCharacterFilesCount != HelpMethods.countFiles(CHARACTERS_FOLDER))
            initCharacterButtons();

        if (currentLevelFilesCount != HelpMethods.countFiles(LEVELS_FOLDER))
            initLevelButtons();
    }

    /*---------------------------------Methods for working with input---------------------------------*/

    @Override
    public void mousePressed(MouseEvent e) {
        playButton.setPressed(isMouseOverButton(playButton, e));
        backButton.setPressed(isMouseOverButton(backButton, e));

        for (Button button : levelButtons)
            button.setPressed(isMouseOverButton(button, e));

        for (Button button : characterButtons)
            button.setPressed(isMouseOverButton(button, e));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        HelpMethods.buttonReleased(playButton, isMouseOverButton(playButton, e) && playButton.isPressed());
        HelpMethods.buttonReleased(backButton, isMouseOverButton(backButton, e) && backButton.isPressed());

        for (Button button : levelButtons)
            HelpMethods.buttonReleased(button, isMouseOverButton(button, e) && button.isPressed());

        for (Button button : characterButtons)
            HelpMethods.buttonReleased(button, isMouseOverButton(button, e) && button.isPressed());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        playButton.setMouseAbove(isMouseOverButton(playButton, e));
        backButton.setMouseAbove(isMouseOverButton(backButton, e));

        for (Button button : levelButtons)
            button.setMouseAbove(isMouseOverButton(button, e));

        for (Button button : characterButtons)
            button.setMouseAbove(isMouseOverButton(button, e));
    }

    /*---------------------------------------Getters and setters---------------------------------------*/

    public static String getChosenLevel() { return chosenLevel; }
    public static String getChosenCharacter() { return chosenCharacter; }
    public static List<SelectionLevelButton> getLevelButtons() { return levelButtons; }
    public static List<SelectionCharacterButton> getCharacterButtons() { return characterButtons; }

    public static void setChosenLevel(String chosenLevel) { Selection.chosenLevel = chosenLevel; }
    public static void setChosenCharacter(String chosenCharacter) { Selection.chosenCharacter = chosenCharacter; }

    /*---------------------------------------Not used methods---------------------------------------*/

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}

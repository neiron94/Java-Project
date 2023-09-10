package my_utils;

import gamestates.Editor;
import gamestates.Selection;
import gamestates.editor_stages.CreationStage;
import gamestates.editor_stages.SavingStage;
import main.Game;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ui.editor.EditorBackButton;
import ui.editor.EditorChooseButton;
import ui.menu.MenuEditorButton;
import ui.menu.MenuExitButton;
import ui.menu.MenuPlayButton;
import ui.play.DeathScreenMenuButton;
import ui.selection.SelectionBackButton;

import static my_utils.Constants.EditStages.*;
import static my_utils.Constants.StatesEnum.*;
import static org.junit.jupiter.api.Assertions.*;

class HelpMethodsButtonReleasedTest {

    @BeforeAll
    static void loadImages() {
        assertDoesNotThrow(Images::loadAll);
    }

    @Test
    void editorBackButtonReleasedIsOk() {
        EditorBackButton backButton = new EditorBackButton();
        HelpMethods.buttonReleased(backButton, true);

        assertAll(() -> assertEquals(CREATING, Editor.getStage()),
                () -> assertEquals("", SavingStage.getTextField().getText()),
                () -> assertFalse(backButton.isMouseAbove()),
                () -> assertFalse(backButton.isPressed()));
    }

    @ParameterizedTest
    @EnumSource (Constants.Entities.class)
    void editorChooseButtonReleasedIsOk(Constants.Entities entityType) {
        EditorChooseButton chooseButton = new EditorChooseButton(0, 0, entityType);
        HelpMethods.buttonReleased(chooseButton, true);

        assertAll(() -> assertEquals(CREATING, Editor.getStage()),
                () -> assertEquals(entityType, CreationStage.getChosenEntity()),
                () -> assertFalse(chooseButton.isMouseAbove()),
                () -> assertFalse(chooseButton.isPressed()));
    }

    @Test
    void menuEditorButtonReleasedIsOk() {
        MenuEditorButton editorButton = new MenuEditorButton();
        HelpMethods.buttonReleased(editorButton, true);

        assertAll(() -> assertEquals(EDITOR, Game.state),
                () -> assertFalse(editorButton.isMouseAbove()),
                () -> assertFalse(editorButton.isPressed()));
    }

    @Test
    void menuExitButtonReleasedIsOk() {
        MenuExitButton exitButton = new MenuExitButton();
        HelpMethods.buttonReleased(exitButton, true);

        assertAll(() -> assertEquals(EXIT, Game.state),
                () -> assertFalse(exitButton.isMouseAbove()),
                () -> assertFalse(exitButton.isPressed()));
    }

    @Test
    void menuPlayButtonReleasedIsOk() {
        MenuPlayButton playButton = new MenuPlayButton();
        HelpMethods.buttonReleased(playButton, true);

        assertAll(() -> assertEquals(SELECTION, Game.state),
                () -> assertNull(Selection.getChosenLevel()),
                () -> assertNull(Selection.getChosenCharacter()),
                () -> assertFalse(playButton.isMouseAbove()),
                () -> assertFalse(playButton.isPressed()));
    }

    @Test
    void deathScreenMenuButtonReleasedIsOk() {
        DeathScreenMenuButton menuButton = new DeathScreenMenuButton();
        HelpMethods.buttonReleased(menuButton, true);

        assertAll(() -> assertEquals(MENU, Game.state),
                () -> assertFalse(menuButton.isMouseAbove()),
                () -> assertFalse(menuButton.isPressed()));
    }

    @Test
    void selectionBackButtonReleasedIsOk() {
        SelectionBackButton backButton = new SelectionBackButton();
        HelpMethods.buttonReleased(backButton, true);

        assertAll(() -> assertEquals(MENU, Game.state),
                () -> assertFalse(backButton.isMouseAbove()),
                () -> assertFalse(backButton.isPressed()));
    }
}
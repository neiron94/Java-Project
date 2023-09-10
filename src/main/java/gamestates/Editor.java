package gamestates;

import gamestates.editor_stages.ChoosingStage;
import gamestates.editor_stages.CreationStage;
import gamestates.editor_stages.SavingStage;
import levels.Level;
import levels.rooms.CommonRoom;
import my_utils.Constants;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import my_utils.Constants.EditStages;

import static my_utils.Constants.EditStages.*;

/**
 * Singleton class which represents the "Editor mode", where level can be created and saved.
 */
public class Editor implements GameState {
    private static Editor editorInstance = null;

    private static boolean isFirstUpdate = true;
    private static EditStages stage = CREATING;
    private static Level level = null;

    public static Editor getInstance() {
        if (editorInstance == null)
            editorInstance = new Editor();

        return editorInstance;
    }

    private Editor() {}

    /*--------------------------------------Methods for rendering--------------------------------------*/

    @Override
    public void render(Graphics g) {
        // should be updated at least once before rendering
        if (isFirstUpdate)
            return;

        level.getCurrentRoom().render(g);
        switch(stage) {
            case CREATING -> CreationStage.getInstance().render(g);
            case CHOOSING -> ChoosingStage.getInstance().render(g);
            case SAVING -> SavingStage.getInstance().render(g);
        }
    }

    /*-------------------------------------Methods for updating-------------------------------------*/

    @Override
    public void update() {
        if (isFirstUpdate) {
            firstUpdate();
            isFirstUpdate = false;
        }
    }

    private void firstUpdate() {
        // Create and configure new level object
        level = new Level();
        level.setStartRoom(new CommonRoom(Constants.REAL_ROOM_MATRIX_SIZE / 2, Constants.REAL_ROOM_MATRIX_SIZE / 2));
        level.setCurrentRoom(level.getStartRoom());
        CreationStage.setChosenEntity(null);
    }

    /*---------------------------------Methods for working with input---------------------------------*/

    @Override
    public void mousePressed(MouseEvent e) {
        switch (stage) {
            case CREATING -> CreationStage.getInstance().mousePressed(e);
            case CHOOSING -> ChoosingStage.getInstance().mousePressed(e);
            case SAVING -> SavingStage.getInstance().mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (stage) {
            case CREATING -> CreationStage.getInstance().mouseReleased(e);
            case CHOOSING -> ChoosingStage.getInstance().mouseReleased(e);
            case SAVING -> SavingStage.getInstance().mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (stage) {
            case CREATING -> CreationStage.getInstance().mouseMoved(e);
            case CHOOSING -> ChoosingStage.getInstance().mouseMoved(e);
            case SAVING -> SavingStage.getInstance().mouseMoved(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (stage) {
            case CREATING -> CreationStage.getInstance().keyReleased(e);
            case CHOOSING -> ChoosingStage.getInstance().keyReleased(e);
            case SAVING -> SavingStage.getInstance().keyReleased(e);
        }
    }

    /*---------------------------------------Getters and setters---------------------------------------*/

    public static void setStage(EditStages stage) { Editor.stage = stage; }
    public static void setFirstUpdate(boolean firstUpdate) { isFirstUpdate = firstUpdate; }

    public static Level getLevel() { return level; }

    public static EditStages getStage() { return stage; }
    /*---------------------------------------Not used methods---------------------------------------*/

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {}
}

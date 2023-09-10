package inputs;

import gamestates.Editor;
import gamestates.Menu;
import gamestates.Play;
import gamestates.Selection;
import main.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Reads inputs from keyboard.
 */
public class KeyboardInput implements KeyListener {
    @Override
    public void keyPressed(KeyEvent e) {
        switch(Game.state) {
            case MENU -> Menu.getInstance().keyPressed(e);
            case PLAY -> Play.getInstance().keyPressed(e);
            case EDITOR -> Editor.getInstance().keyPressed(e);
            case SELECTION -> Selection.getInstance().keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(Game.state) {
            case MENU -> Menu.getInstance().keyReleased(e);
            case PLAY -> Play.getInstance().keyReleased(e);
            case EDITOR -> Editor.getInstance().keyReleased(e);
            case SELECTION -> Selection.getInstance().keyReleased(e);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}

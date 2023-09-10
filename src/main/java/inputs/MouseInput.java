package inputs;

import gamestates.Editor;
import gamestates.Menu;
import gamestates.Play;
import gamestates.Selection;
import main.Game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Reads inputs from mouse.
 */
public class MouseInput implements MouseListener, MouseMotionListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        switch(Game.state) {
            case MENU -> Menu.getInstance().mouseClicked(e);
            case PLAY -> Play.getInstance().mouseClicked(e);
            case EDITOR -> Editor.getInstance().mouseClicked(e);
            case SELECTION -> Selection.getInstance().mouseClicked(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch(Game.state) {
            case MENU -> Menu.getInstance().mousePressed(e);
            case PLAY -> Play.getInstance().mousePressed(e);
            case EDITOR -> Editor.getInstance().mousePressed(e);
            case SELECTION -> Selection.getInstance().mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch(Game.state) {
            case MENU -> Menu.getInstance().mouseReleased(e);
            case PLAY -> Play.getInstance().mouseReleased(e);
            case EDITOR -> Editor.getInstance().mouseReleased(e);
            case SELECTION -> Selection.getInstance().mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch(Game.state) {
            case MENU -> Menu.getInstance().mouseMoved(e);
            case PLAY -> Play.getInstance().mouseMoved(e);
            case EDITOR -> Editor.getInstance().mouseMoved(e);
            case SELECTION -> Selection.getInstance().mouseMoved(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}
}

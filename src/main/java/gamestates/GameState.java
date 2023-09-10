package gamestates;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * The GameState interface should be implemented by all classes which represent game states.
 */
public interface GameState {
    void render(Graphics g);
    void update();
    void mouseClicked(MouseEvent e);
    void mousePressed(MouseEvent e);
    void mouseReleased(MouseEvent e);
    void mouseMoved(MouseEvent e);
    void keyPressed(KeyEvent e);
    void keyReleased(KeyEvent e);
}

package ui.play;

import my_utils.HelpMethods;
import my_utils.Images;

import java.awt.*;
import java.awt.event.MouseEvent;

import static my_utils.Constants.UIConstants.PlayConstants.*;
import static my_utils.HelpMethods.isMouseOverButton;

/**
 * Singleton class which represents a screen shown after player's death.
 */
public class DeathScreen {
    private static DeathScreen deathScreenInstance = null;

    private static final DeathScreenMenuButton menuButton;
    static {
        menuButton = new DeathScreenMenuButton();
    }

    public static DeathScreen getInstance() {
        if (deathScreenInstance == null)
            deathScreenInstance = new DeathScreen();

        return deathScreenInstance;
    }

    private DeathScreen() {}

    public void render(Graphics g) {
        g.drawImage(Images.getDeathScreenFrame(), (int) DEATH_SCREEN_X, (int) DEATH_SCREEN_Y, (int)DEATH_SCREEN_WIDTH, (int)DEATH_SCREEN_HEIGHT, null);
        menuButton.render(g);
    }


    public void mousePressed(MouseEvent e) { menuButton.setPressed(isMouseOverButton(menuButton, e)); }

    public void mouseReleased(MouseEvent e) {
        HelpMethods.buttonReleased(menuButton, isMouseOverButton(menuButton, e) && menuButton.isPressed());
    }

    public void mouseMoved(MouseEvent e) { menuButton.setMouseAbove(isMouseOverButton(menuButton, e)); }
}

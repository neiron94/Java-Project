package gamestates;

import my_utils.HelpMethods;
import my_utils.Images;
import ui.menu.MenuButton;
import ui.menu.MenuEditorButton;
import ui.menu.MenuExitButton;
import ui.menu.MenuPlayButton;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static my_utils.Constants.WindowConstants.*;
import static my_utils.Constants.UIConstants.MenuConstants.*;
import static my_utils.HelpMethods.isMouseOverButton;

/**
 * Singleton class which represents start game state, from which other game states can be reached.
 */
public class Menu implements GameState {
    private static Menu menuInstance = null;

    private final static List<MenuButton> buttons;
    static {
        buttons = new ArrayList<>();
        buttons.add(new MenuPlayButton());
        buttons.add(new MenuEditorButton());
        buttons.add(new MenuExitButton());
    }

    private Menu() {}

    public static Menu getInstance() {
        if (menuInstance == null)
            menuInstance = new Menu();

        return menuInstance;
    }

    /*--------------------------------------Methods for rendering--------------------------------------*/

    @Override
    public void render(Graphics g) {
        g.drawImage(Images.getMenuBackground(), 0,0, REAL_WINDOW_WIDTH, REAL_WINDOW_HEIGHT, null);
        g.drawImage(Images.getMenuFrame(), MENU_FRAME_X, MENU_FRAME_Y, MENU_FRAME_WIDTH, MENU_FRAME_HEIGHT, null);

        for (MenuButton button : buttons)
            button.render(g);
    }

    /*---------------------------------Methods for working with input---------------------------------*/

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton button : buttons)
            button.setPressed(isMouseOverButton(button, e));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton button : buttons)
            HelpMethods.buttonReleased(button, isMouseOverButton(button, e) && button.isPressed());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton button : buttons)
            button.setMouseAbove(isMouseOverButton(button, e));
    }

    /*---------------------------------------Not used methods---------------------------------------*/

    @Override
    public void update() {}
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}

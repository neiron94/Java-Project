package main;

import inputs.KeyboardInput;
import inputs.MouseInput;
import my_utils.MyLogger;

import javax.swing.*;
import java.awt.*;

import static my_utils.Constants.WindowConstants.*;

/**
 * Uses JPanel for drawing on GameWindow.
 */
public class GamePanel extends JPanel {
    private static final MyLogger LOGGER = new MyLogger(GamePanel.class);

    /**
     * Constructor where game panel is configured.
     */
    public GamePanel() {
        setFocusable(true);
        setLayout(null);
        MouseInput mouseInput = new MouseInput();
        setPanelSize();
        addKeyListener(new KeyboardInput());
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);

        LOGGER.info("The GamePanel is created and configured successfully");
    }

    private void setPanelSize() {
        Dimension size = new Dimension(REAL_WINDOW_WIDTH, REAL_WINDOW_HEIGHT);
        setPreferredSize(size);
        LOGGER.info("Panel size is set to " + REAL_WINDOW_WIDTH + "x" + REAL_WINDOW_HEIGHT);
    }

    /**
     * Method for drawing.
     * @param g the <code>Graphics</code> object needed for drawing.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Renderer.getInstance().render(g);
    }
}

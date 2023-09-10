package main;

import gamestates.Play;
import my_utils.Constants;
import my_utils.MyLogger;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

/**
 * Uses JFrame for creating GUI window.
 */
public class GameWindow {
    private static final MyLogger LOGGER = new MyLogger(GameWindow.class);
    private final JFrame jFrame;

    /**
     * Constructor where game window is configured.
     */
    public GameWindow(GamePanel gamePanel) {
        jFrame = new JFrame();

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setUndecorated(true);
        jFrame.add(gamePanel);
        jFrame.pack();
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setLayout(null);
        jFrame.setVisible(true);

        // It stops player if window focus is lost
        jFrame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                if (Game.state == Constants.StatesEnum.PLAY)
                    Play.getPlayer().resetDirBooleans();
            }
        });

        LOGGER.info("The GameWindow is created and configured successfully");
    }

    public JFrame getjFrame() { return jFrame; }
}

package main;

import gamestates.Editor;
import gamestates.Menu;
import gamestates.Play;
import gamestates.Selection;
import my_utils.MyLogger;

import java.awt.event.WindowEvent;

import static my_utils.Constants.MAX_UPS;

/**
 * Singleton runnable class for updating all game elements.
 */
public class Updater implements Runnable {
    private static Updater instance = null;
    private static final MyLogger LOGGER = new MyLogger(Updater.class);

    private Updater() {}

    public static Updater getInstance() {
        if (instance == null)
            instance = new Updater();

        return instance;
    }

    /**
     * Method updates elements based on current game state.
     */
    private void update() {
        switch (Game.state) {
            case MENU -> Menu.getInstance().update();
            case PLAY -> Play.getInstance().update();
            case EDITOR -> Editor.getInstance().update();
            case SELECTION -> Selection.getInstance().update();
            case EXIT -> Game.setIsExit(true);
        }
    }

    /**
     * Infinite loop for updating the game.
     */
    @Override
    public void run() {
        LOGGER.info("Update loop started successfully");

        double timePerUpdate = 1000000000.0 / MAX_UPS;
        long previousTime = System.nanoTime();
        double deltaU = 0;

        while (true) {
            long currentTime = System.nanoTime();
            deltaU += (currentTime - previousTime) / timePerUpdate;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                if (Game.isIsExit()) {
                    LOGGER.info("Update loop ended successfully");
                    // Close the window
                    Game.getGameWindow().getjFrame().dispatchEvent(new WindowEvent(Game.getGameWindow().getjFrame(), WindowEvent.WINDOW_CLOSING));
                    break;
                }
                deltaU--;
            }
        }
    }
}

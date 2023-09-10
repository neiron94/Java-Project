package main;

import my_utils.Images;
import my_utils.MyLogger;

import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Arrays;

/**
 * Main class where game is started.
 */
public class Main {
    private static final MyLogger LOGGER = new MyLogger(Main.class);

    /**
     * Main method where loggers are disabled/enabled and game is started.
     * @param args if args[1] equals "-log" then loggers will be enabled and their level will be set according
     *            to the configuration file, else loggers will be disabled.
     */
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("-log"))
            MyLogger.setLogOn(true);

        LOGGER.info("The program is started successfully");

        try {
            Images.loadAll();
            LOGGER.info("All images are read successfully");
        }
        catch (IOException e) {
            LOGGER.fatal("Can't read some image. Error message: " + e.getMessage() + ". Error stack trace: " + Arrays.toString(e.getStackTrace()));
        }

        try {
            Game.getInstance().startGameLoop();
        }
        catch (Exception e) {
            fatalExit(e);
        }
    }

    /**
     * Stops the program.
     * @param e fatal exception.
     */
    public static void fatalExit(Exception e) {
        LOGGER.fatal("Some fatal error crushed the program. Error message: " + e.getMessage() + ". Error stack trace: " + Arrays.toString(e.getStackTrace()));
        Game.getGameWindow().getjFrame().dispatchEvent(new WindowEvent(Game.getGameWindow().getjFrame(), WindowEvent.WINDOW_CLOSING));
    }
}

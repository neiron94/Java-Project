package main;

import static my_utils.Constants.*;
import static my_utils.Constants.StatesEnum.*;

/**
 * Singleton class for starting render and update loop and controlling current state of the game.
 */
public class Game {
    /**
     * Current state of the game.
     */
    public static StatesEnum state = MENU;

    private static Game gameInstance = null;
    private static boolean isExit = false;
    private static final GamePanel gamePanel;
    private static final GameWindow gameWindow;

    static {
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
    }

    private Game() {}

    public static Game getInstance() {
        if (gameInstance == null)
            gameInstance = new Game();

        return gameInstance;
    }

    /**
     * Method creates and starts render and update loop.
     */
    public void startGameLoop() {
        Thread renderLoop = new Thread(Renderer.getInstance(), "Render Loop");
        Thread updateLoop = new Thread(Updater.getInstance(), "Update Loop");
        updateLoop.start();
        renderLoop.start();
    }

    public static GamePanel getGamePanel() { return gamePanel; }
    public static GameWindow getGameWindow() { return gameWindow; }

    public static boolean isIsExit() { return isExit; }
    public static void setIsExit(boolean isExit) { Game.isExit = isExit; }
}

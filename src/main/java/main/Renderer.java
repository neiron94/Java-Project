package main;

import gamestates.Editor;
import gamestates.Menu;
import gamestates.Play;
import gamestates.Selection;
import my_utils.MyLogger;

import java.awt.*;

import static my_utils.Constants.MAX_FPS;

/**
 * Singleton runnable class for rendering all game elements.
 */
public class Renderer implements Runnable {
    private static Renderer instance = null;
    private static final MyLogger LOGGER = new MyLogger(Renderer.class);

    private Renderer() {}

    public static Renderer getInstance() {
        if (instance == null)
            instance = new Renderer();

        return instance;
    }

    /**
     * Method renders elements based on current game state.
     * @param g object for drawing.
     */
    public void render(Graphics g) {
        switch (Game.state) {
            case MENU -> Menu.getInstance().render(g);
            case PLAY -> Play.getInstance().render(g);
            case EDITOR -> Editor.getInstance().render(g);
            case SELECTION -> Selection.getInstance().render(g);
        }
    }

    /**
     * Infinite loop for rendering the game.
     */
    @Override
    public void run() {
        LOGGER.info("Render loop started successfully");

        double timePerFrame = 1000000000.0 / MAX_FPS;
        long previousTime = System.nanoTime();
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaF >= 1) {
                Game.getGamePanel().repaint();
                if (Game.isIsExit()) {
                    LOGGER.info("Render loop ended successfully");
                    break;
                }
                deltaF--;
            }
        }
    }
}

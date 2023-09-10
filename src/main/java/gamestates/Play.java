package gamestates;

import creatures.Player;
import levels.Level;
import main.Game;
import my_utils.Constants;
import ui.play.DeathScreen;
import ui.play.VictoryScreen;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


/**
 * Singleton class which represents state where game is played.
 */
public class Play implements GameState {
    private static Play playInstance = null;

    private static boolean isVictory = false;
    private static Player player;
    private static Level level;

    public static Play getInstance() {
        if (playInstance == null)
            playInstance = new Play();

        return playInstance;
    }

    private Play() {}

    /*--------------------------------------Methods for rendering--------------------------------------*/

    @Override
    public void render(Graphics g) {
        level.getCurrentRoom().render(g);
        player.render(g);
        if (!player.isAlive())
            DeathScreen.getInstance().render(g);
        else if (isVictory)
            VictoryScreen.getInstance().render(g);
    }

    /*-------------------------------------Methods for updating-------------------------------------*/

    @Override
    public void update() {
        if (isVictory)
            player.resetDirBooleans();
        level.getCurrentRoom().update();
        player.update();
    }

    /*---------------------------------Methods for working with input---------------------------------*/

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!player.isAlive() || isVictory)
            return;

        if (e.getButton() == MouseEvent.BUTTON1)
            player.setAttacking(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!player.isAlive() || isVictory)
            return;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> player.setUp(true);
            case KeyEvent.VK_A -> player.setLeft(true);
            case KeyEvent.VK_S -> player.setDown(true);
            case KeyEvent.VK_D -> player.setRight(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!player.isAlive() || isVictory)
            return;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> player.setUp(false);
            case KeyEvent.VK_A -> player.setLeft(false);
            case KeyEvent.VK_S -> player.setDown(false);
            case KeyEvent.VK_D -> player.setRight(false);
            case KeyEvent.VK_ESCAPE -> Game.state = Constants.StatesEnum.MENU;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!player.isAlive())
            DeathScreen.getInstance().mousePressed(e);
        else if (isVictory)
            VictoryScreen.getInstance().mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!player.isAlive())
            DeathScreen.getInstance().mouseReleased(e);
        else if (isVictory)
            VictoryScreen.getInstance().mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!player.isAlive())
            DeathScreen.getInstance().mouseMoved(e);
        else if (isVictory)
            VictoryScreen.getInstance().mouseMoved(e);
    }

    /*---------------------------------------Getters and setters---------------------------------------*/

    public static void setPlayer(Player player) { Play.player = player; }
    public static Player getPlayer() { return player; }

    public static void setLevel(Level level) { Play.level = level; }
    public static Level getLevel() { return level; }

    public static void setVictory(boolean victory) { isVictory = victory; }
    public static boolean isVictory() { return isVictory; }
}

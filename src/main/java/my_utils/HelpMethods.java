package my_utils;

import gamestates.Editor;
import gamestates.Play;
import items.boosters.ArmorBooster;
import items.boosters.DamageBooster;
import items.boosters.HealthBooster;
import items.boosters.SpeedBooster;
import items.common.Coin;
import items.common.HealPotion;
import items.common.Key;
import levels.rooms.Room;
import main.Game;
import ui.Button;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.List;

import static my_utils.Constants.StatesEnum.*;

/**
 * Contains useful static methods.
 */
public class HelpMethods {
    private static final MyLogger LOGGER = new MyLogger(HelpMethods.class);
    /**
     * Decides if creature can move x to the right and y down.
     * @param x x coordinate of the point to move.
     * @param y y coordinate of the point to move.
     * @param touchBox touch box of the creature.
     * @param obstacles obstacles in the current room.
     * @return true if creature can move, else false.
     */
    public static boolean canMove(float x, float y, Rectangle2D.Float touchBox, List<Rectangle2D.Float> obstacles) {
        // How touch box will change
        Rectangle2D.Float playerNextTouchBox = new Rectangle2D.Float(touchBox.x + x, touchBox.y + y,
                touchBox.width + x, touchBox.height + y);

        for (Rectangle2D.Float obstacle : obstacles)
            if (playerNextTouchBox.intersects(obstacle))
                return false;

        return true;
    }

    /**
     * Counts distance between two points.
     * @param point1 first point.
     * @param point2 second point.
     * @return distance between two points.
     */
    public static int countDistance(Point point1, Point point2) {
        int distanceX = Math.abs(point1.x - point2.x);
        int distanceY = Math.abs(point1.y - point2.y);
        return (int)Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
    }

    /**
     * Generate item in the current room on the given point.
     * @param type type of generated item.
     * @param dropPoint point where the center of the item's touch box will be placed.
     */
    public static void generateItemByType(Constants.ItemTypes type, Point dropPoint) {
        Room room;
        if (Game.state == PLAY)         room = Play.getLevel().getCurrentRoom();
        else if (Game.state == EDITOR)  room = Editor.getLevel().getCurrentRoom();
        else    return;

        // Shop item can be generated only in the Editor mode.
        boolean isShopItem = Game.state == EDITOR;

        switch (type) {
            case KEY -> Key.drop(dropPoint, room);
            case COIN -> Coin.drop(dropPoint, room);
            case HEAL_POTION -> HealPotion.drop(dropPoint, room, isShopItem);
            case ARMOR_BOOSTER -> ArmorBooster.drop(dropPoint, room, isShopItem);
            case SPEED_BOOSTER -> SpeedBooster.drop(dropPoint, room, isShopItem);
            case DAMAGE_BOOSTER -> DamageBooster.drop(dropPoint, room, isShopItem);
            case HEALTH_BOOSTER -> HealthBooster.drop(dropPoint, room, isShopItem);
        }
    }

    /**
     * Decides if mouse is over the particular button.
     * @param button particular button.
     * @param e mouse event, from which coordinates will be taken.
     * @return true if mouse coordinates intersects with button hit box.
     */
    public static boolean isMouseOverButton(Button button, MouseEvent e) {
        return button.getHitBox().contains(e.getX(), e.getY());
    }

    /**
     *  Does "mouse released over the button" action.
     * @param button button which will be activated, if condition is true.
     * @param condition condition for button activation.
     */
    public static void buttonReleased(Button button, boolean condition) {
        if (condition)
            button.doWhenClicked();
        button.setMouseAbove(false);
        button.setPressed(false);
    }

    /**
     * Draws two-digit number on given coordinates.
     * @param g object for drawing.
     * @param number two-digit number to draw.
     * @param numberX x coordinate of first digit.
     * @param numberY y coordinate of first digit.
     * @param numberWidth width of one digit (in pixels).
     * @param numberHeight height of one digit (in pixels).
     * @param betweenNumbers number of pixels between two digits.
     */
    public static void drawNumber(Graphics g, int number, float numberX, float numberY, float numberWidth, float numberHeight, float betweenNumbers) {
        if (number < 0) {
            LOGGER.error("Function drawNumber have got a number < 0. The number is equated to 0.");
            number = 0;
        }
        if (number > 99) {
            LOGGER.error("Function drawNumber have got a number > 99. The number is equated to 99.");
            number = 99;
        }
        g.drawImage(Images.getNumbers()[number / 10], (int)numberX, (int)numberY, (int)numberWidth, (int)numberHeight, null);
        g.drawImage(Images.getNumbers()[number % 10], (int)(numberX + betweenNumbers), (int)numberY, (int)numberWidth, (int)numberHeight, null);
    }

    /**
     * Counts serialized (.ser) files in the given folder.
     * @param folder folder with serialized files.
     * @return count of serialized files.
     */
    public static int countFiles(File folder) {
        int count = 0;
        File[] files = folder.listFiles();
        if (files == null)
            return count;

        for (File file : files)
            if (file.isFile() && file.getName().endsWith(".ser"))
                count++;

        return count;
    }
}

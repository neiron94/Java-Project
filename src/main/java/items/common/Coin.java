package items.common;

import gamestates.Play;
import items.Item;
import levels.rooms.Room;
import my_utils.Images;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static my_utils.Constants.ItemsConstants.CoinConstants.*;

/**
 * Coin. Can be spent in the shop room.
 */
public class Coin extends Item {
    /**
     * Configures coin's state. Coin can't be a "shop item".
     * @param x x coordinate of item's position.
     * @param y y coordinate of item's position.
     */
    public Coin(float x, float y) {
        super(x, y, REAL_COIN_WIDTH, REAL_COIN_HEIGHT, COST, false);
    }

    /**
     * Generates the coin object on the given point.
     * @param dropPlace the center of the item's touch box.
     * @param room room in which item will be generated.
     */
    public static void drop(Point dropPlace, Room room) {
        room.addItem(new Coin(dropPlace.x - COIN_TOUCH_BOX_WIDTH / 2 - COIN_TOUCH_BOX_OFFSET_X,
                dropPlace.y - COIN_TOUCH_BOX_HEIGHT / 2 - COIN_TOUCH_BOX_OFFSET_Y));
    }

    /**
     * Adds one coin to the player.
     */
    @Override
    public void collect() {
        Play.getPlayer().addCoin();
    }

    /**
     * Draws the coin.
     * @param g the object for drawing.
     */
    @Override
    public void render(Graphics g) {
        if (!isTaken)
            g.drawImage(Images.getCoinAnimation()[animIndex], (int)x, (int)y, (int)width, (int)height, null);
    }

    @Override
    protected void updateAnimationTick() { updateAnimationTick(COIN_SPRITE_AMOUNT); }

    @Override
    protected void initTouchBox() {
        touchBox = new Rectangle2D.Float((int)(x + COIN_TOUCH_BOX_OFFSET_X), (int)(y + COIN_TOUCH_BOX_OFFSET_Y),
                (int)COIN_TOUCH_BOX_WIDTH, (int)COIN_TOUCH_BOX_HEIGHT);
    }
}

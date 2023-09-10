package items.common;

import gamestates.Play;
import items.Item;
import levels.rooms.Room;
import my_utils.Images;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static my_utils.Constants.ItemsConstants.KeyConstants.*;

/**
 * Key. Can open the final room of the level.
 */
public class Key extends Item {
    /**
     * Configures key's state. Key can't be a "shop item".
     * @param x x coordinate of item's position.
     * @param y y coordinate of item's position.
     */
    public Key(float x, float y) {
        super(x, y, REAL_KEY_WIDTH, REAL_KEY_HEIGHT, COST, false);
    }

    /**
     * Generates the key object on the given point.
     * @param dropPlace the center of the item's touch box.
     * @param room room in which item will be generated.
     */
    public static void drop(Point dropPlace, Room room) {
        room.addItem(new Key(dropPlace.x - KEY_TOUCH_BOX_WIDTH / 2 - KEY_TOUCH_BOX_OFFSET_X,
                dropPlace.y - KEY_TOUCH_BOX_HEIGHT / 2 - KEY_TOUCH_BOX_OFFSET_Y));
    }

    /**
     * Adds the key to the player.
     */
    @Override
    public void collect() { Play.getPlayer().setHasKey(true); }

    /**
     * Draws the key.
     * @param g the object for drawing.
     */
    @Override
    public void render(Graphics g) {
        if (!isTaken)
            g.drawImage(Images.getKeyAnimation()[animIndex], (int)x, (int)y, (int)width, (int)height, null);
    }

    @Override
    protected void updateAnimationTick() { updateAnimationTick(KEY_SPRITE_AMOUNT); }

    @Override
    protected void initTouchBox() {
        touchBox = new Rectangle2D.Float((int)(x + KEY_TOUCH_BOX_OFFSET_X), (int)(y + KEY_TOUCH_BOX_OFFSET_Y),
                (int)KEY_TOUCH_BOX_WIDTH, (int)KEY_TOUCH_BOX_HEIGHT);
    }
}

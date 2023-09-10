package items.common;

import gamestates.Play;
import items.Item;
import levels.rooms.Room;
import my_utils.Images;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static my_utils.Constants.ItemsConstants.HealPotionConstants.*;

/**
 * Heal potion. Restore HP when picked up.
 */
public class HealPotion extends Item {
    /**
     * Configures heal potion's state.
     * @param x x coordinate of item's position.
     * @param y y coordinate of item's position.
     * @param isShopItem true if item is generated in the shop.
     */
    public HealPotion(float x, float y, boolean isShopItem) {
        super(x, y, REAL_HEAL_POTION_WIDTH, REAL_HEAL_POTION_HEIGHT, COST, isShopItem);
    }

    /**
     * Generates heal potion object on the given point. Method is called only during the game (state = PLAY),
     * so dropped item can't be "shop item".
     * @param dropPlace the center of the item's touch box.
     * @param room room in which item will be generated.
     * @param isShopItem true if the item is generated in the shop room.
     */
    public static void drop(Point dropPlace, Room room, boolean isShopItem) {
        room.addItem(new HealPotion(dropPlace.x - HEAL_POTION_TOUCH_BOX_WIDTH / 2 - HEAL_POTION_TOUCH_BOX_OFFSET_X,
                dropPlace.y - HEAL_POTION_TOUCH_BOX_HEIGHT / 2 - HEAL_POTION_TOUCH_BOX_OFFSET_Y, isShopItem));
    }

    /**
     * Restores player's HP.
     */
    @Override
    public void collect() {
        Play.getPlayer().heal(HEALTH_RESTORE);
    }

    /**
     * Draws the heal potion.
     * @param g the object for drawing.
     */
    @Override
    public void render(Graphics g) {
        if (!isTaken)
            g.drawImage(Images.getHealPotionAnimation()[animIndex], (int)x, (int)y, (int)width, (int)height, null);
    }

    @Override
    protected void updateAnimationTick() { updateAnimationTick(HEAL_POTION_SPRITE_AMOUNT); }

    @Override
    protected void initTouchBox() {
        touchBox = new Rectangle2D.Float((int)(x + HEAL_POTION_TOUCH_BOX_OFFSET_X), (int)(y + HEAL_POTION_TOUCH_BOX_OFFSET_Y),
                (int)HEAL_POTION_TOUCH_BOX_WIDTH, (int)HEAL_POTION_TOUCH_BOX_HEIGHT);
    }
}

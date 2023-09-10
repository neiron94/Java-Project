package items.boosters;

import gamestates.Play;
import levels.rooms.Room;
import my_utils.Images;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static my_utils.Constants.ItemsConstants.ArmorBoosterConstants.*;

/**
 * Upgrades Player's armor.
 */
public class ArmorBooster extends Booster {
    /**
     * Configures armor booster's state.
     * @param x x coordinate of item's position.
     * @param y y coordinate of item's position.
     * @param isShopItem true if item is generated in the shop.
     */
    public ArmorBooster(float x, float y, boolean isShopItem) {
        super(x, y, REAL_ARMOR_BOOSTER_WIDTH, REAL_ARMOR_BOOSTER_HEIGHT, COST, isShopItem);
    }

    /**
     * Generates armor booster object on the given point. Method is called only during the game (state = PLAY),
     * so dropped item can't be "shop item".
     * @param dropPlace the center of the item's touch box.
     * @param room room in which item will be generated.
     * @param isShopItem true if the item is generated in the shop room.
     */
    public static void drop(Point dropPlace, Room room, boolean isShopItem) {
        room.addItem(new ArmorBooster(dropPlace.x - ARMOR_BOOSTER_TOUCH_BOX_WIDTH / 2 - ARMOR_BOOSTER_TOUCH_BOX_OFFSET_X,
                dropPlace.y - ARMOR_BOOSTER_TOUCH_BOX_HEIGHT / 2 - ARMOR_BOOSTER_TOUCH_BOX_OFFSET_Y, isShopItem));
    }

    /**
     * Draws the armor booster.
     * @param g the object for drawing.
     */
    @Override
    public void render(Graphics g) {
        if (!isTaken)
            g.drawImage(Images.getArmorBoosterAnimation()[animIndex], (int)x, (int)y, (int)width, (int)height, null);
    }

    @Override
    protected void updateAnimationTick() { updateAnimationTick(ARMOR_BOOSTER_SPRITE_AMOUNT); }

    @Override
    protected void initTouchBox() {
        touchBox = new Rectangle2D.Float((int)(x + ARMOR_BOOSTER_TOUCH_BOX_OFFSET_X), (int)(y + ARMOR_BOOSTER_TOUCH_BOX_OFFSET_Y),
                (int)ARMOR_BOOSTER_TOUCH_BOX_WIDTH, (int)ARMOR_BOOSTER_TOUCH_BOX_HEIGHT);
    }

    @Override
    protected void upgradeStat() {
        Play.getPlayer().upgradeArmor(ARMOR_UP);
    }
}

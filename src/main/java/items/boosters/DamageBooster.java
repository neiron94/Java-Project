package items.boosters;

import gamestates.Play;
import levels.rooms.Room;
import my_utils.Images;

import java.awt.*;
import java.awt.geom.Rectangle2D;


import static my_utils.Constants.ItemsConstants.DamageBoosterConstants.*;


/**
 * Upgrades Player's damage.
 */
public class DamageBooster extends Booster {
    /**
     * Configures damage booster's state.
     * @param x x coordinate of item's position.
     * @param y y coordinate of item's position.
     * @param isShopItem true if item is generated in the shop.
     */
    public DamageBooster(float x, float y, boolean isShopItem) {
        super(x, y, REAL_DAMAGE_BOOSTER_WIDTH, REAL_DAMAGE_BOOSTER_HEIGHT, COST, isShopItem);
    }

    /**
     * Generates damage booster object on the given point. Method is called only during the game (state = PLAY),
     * so dropped item can't be "shop item".
     * @param dropPlace the center of the item's touch box.
     * @param room room in which item will be generated.
     * @param isShopItem true if the item is generated in the shop room.
     */
    public static void drop(Point dropPlace, Room room, boolean isShopItem) {
        room.addItem(new DamageBooster(dropPlace.x - DAMAGE_BOOSTER_TOUCH_BOX_WIDTH / 2 - DAMAGE_BOOSTER_TOUCH_BOX_OFFSET_X,
                dropPlace.y - DAMAGE_BOOSTER_TOUCH_BOX_HEIGHT / 2 - DAMAGE_BOOSTER_TOUCH_BOX_OFFSET_Y, isShopItem));
    }

    /**
     * Draws the damage booster.
     * @param g the object for drawing.
     */
    @Override
    public void render(Graphics g) {
        if (!isTaken)
            g.drawImage(Images.getDamageBoosterAnimation()[animIndex], (int)x, (int)y, (int)width, (int)height, null);
    }

    @Override
    protected void updateAnimationTick() { updateAnimationTick(DAMAGE_BOOSTER_SPRITE_AMOUNT); }

    @Override
    protected void initTouchBox() {
        touchBox = new Rectangle2D.Float((int)(x + DAMAGE_BOOSTER_TOUCH_BOX_OFFSET_X), (int)(y + DAMAGE_BOOSTER_TOUCH_BOX_OFFSET_Y),
                (int)DAMAGE_BOOSTER_TOUCH_BOX_WIDTH, (int)DAMAGE_BOOSTER_TOUCH_BOX_HEIGHT);
    }

    @Override
    public void upgradeStat() {
        Play.getPlayer().upgradeDamage(DAMAGE_UP);
    }
}

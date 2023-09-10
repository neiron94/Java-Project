package levels.rooms;

import my_utils.Constants;
import my_utils.HelpMethods;
import my_utils.Images;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static my_utils.Constants.ItemTypes.*;
import static my_utils.Constants.WindowConstants.*;
import static my_utils.Constants.RoomConstants.*;

/**
 * Here the Player can spend coins to buy items.
 */
public class ShopRoom extends Room {
    private static final List<Constants.ItemTypes> itemPool;
    static {
        itemPool = new ArrayList<>();
        itemPool.add(ARMOR_BOOSTER);
        itemPool.add(HEALTH_BOOSTER);
        itemPool.add(DAMAGE_BOOSTER);
        itemPool.add(SPEED_BOOSTER);
        itemPool.add(HEAL_POTION);
    }

    public ShopRoom(int posMatrixX, int posMatrixY) {
        super(posMatrixX, posMatrixY, false, true, Constants.RoomTypes.SHOP);
    }

    /**
     * Adds three random item from the itemPool to the shop.
     */
    public void addShopItems() {
        Random rand = new Random();
        Constants.ItemTypes[] shopItemTypes = new Constants.ItemTypes[3];

        List<Constants.ItemTypes> itemPoolCopy = new ArrayList<>(itemPool);
        for (int i = 0; i < 3; i++) {
            Constants.ItemTypes randomItemType = itemPoolCopy.get(rand.nextInt(itemPoolCopy.size()));
            shopItemTypes[i] = randomItemType;
            itemPoolCopy.remove(randomItemType);
        }

        HelpMethods.generateItemByType(shopItemTypes[0], SHOP_ITEM_POINT_1);
        HelpMethods.generateItemByType(shopItemTypes[1], SHOP_ITEM_POINT_2);
        HelpMethods.generateItemByType(shopItemTypes[2], SHOP_ITEM_POINT_3);
    }

    @Override
    protected void renderRoomBackground(Graphics g) {
        g.drawImage(Images.getShopRoomImage(), 0, 0, REAL_WINDOW_WIDTH, REAL_WINDOW_HEIGHT, null);
    }
}

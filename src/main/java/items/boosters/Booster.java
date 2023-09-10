package items.boosters;

import gamestates.Play;
import items.Item;

/**
 * This class is common for all boosters in the game.
 */
public abstract class Booster extends Item {
    public Booster(float x, float y, float width, float height, int cost, boolean isShopItem) {
        super(x, y, width, height, cost, isShopItem);
    }

    /**
     * Upgrades one player's stat and adds the booster to the player's inventory.
     */
    @Override
    public void collect() {
        upgradeStat();
        Play.getPlayer().addItem(this);
    }

    protected abstract void upgradeStat();
}

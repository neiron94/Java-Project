package items;

import creatures.Player;
import gamestates.Play;
import my_utils.Constants;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
 * This class is common for all items in the game.
 */
public abstract class Item implements Serializable {
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected Rectangle2D touchBox;

    protected int cost;
    protected boolean isTaken;
    protected boolean isShopItem;

    protected int animIndex = 0, animTick = 0, animSpeed = Constants.NORM_ANIM_SPEED;

    /**
     * Configures item's state.
     * @param x x coordinate of item's location.
     * @param y y coordinate of item's location.
     * @param width width of sprite.
     * @param height height of sprite.
     * @param cost price which will be displayed in the shop.
     * @param isShopItem true if item is generated in the shop.
     */
    public Item(float x, float y, float width, float height, int cost, boolean isShopItem) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.cost = cost;
        initTouchBox();
        this.isShopItem = isShopItem;
        isTaken = false;
    }

    /*-------------------------------------Methods for updating-------------------------------------*/

    /**
     * Calls method collect() if the player can pick up or buy the item.
     */
    public void update() {
        Player player = Play.getPlayer();

        if (!isTaken) {
            if (player.getTouchBox().intersects(touchBox)) {
                if (isShopItem) {
                    if (player.getCoinsAmount() >= cost) {
                        isTaken = true;
                        collect();
                        player.payPrice(cost);
                    }
                }
                else {
                    isTaken = true;
                    collect();
                }
            }

            updateAnimationTick();
        }
    }

    protected void updateAnimationTick(int spriteAmount) {
        animTick++;
        if (animTick >= animSpeed) {
            animTick = 0;
            animIndex++;
            if (animIndex >= spriteAmount) {
                animIndex = 0;
            }
        }
    }

    /*-------------------------------------Methods to implement-------------------------------------*/

    public abstract void collect();
    public abstract void render(Graphics g);
    protected abstract void updateAnimationTick();
    protected abstract void initTouchBox();

    /*---------------------------------------Getters and setters---------------------------------------*/

    public boolean isTaken() { return isTaken; }
    public boolean isShopItem() { return isShopItem; }
    public int getCost() { return cost; }
    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
}

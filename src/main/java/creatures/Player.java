package creatures;

import gamestates.Play;
import items.Item;
import levels.Level;
import levels.rooms.Room;
import my_utils.Constants;
import my_utils.HelpMethods;
import my_utils.Images;
import my_utils.MyLogger;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static my_utils.Constants.CreatureStates.*;
import static my_utils.Constants.Directions.*;
import static my_utils.Constants.PlayerConstants.*;
import static my_utils.Constants.RoomConstants.REAL_WALL_THICKNESS;
import static my_utils.Constants.RoomConstants.SOUTH_DOOR;
import static my_utils.Constants.RoomConstants.EAST_DOOR;
import static my_utils.Constants.UIConstants.PlayConstants.*;
import static my_utils.Constants.WindowConstants.REAL_WINDOW_HEIGHT;
import static my_utils.Constants.WindowConstants.REAL_WINDOW_WIDTH;

/**
 * Class for character under the Player's control.
 */
public class Player extends Creature implements Serializable {
    private static final MyLogger LOGGER = new MyLogger(Player.class);
    private final List<Item> items;   // items are not used for now
    private int coinsAmount;
    private boolean hasKey;

    /**
     * Configures start state of the player.
     */
    public Player() {
        super(START_X, START_Y, START_MAX_HEALTH, START_SPEED, START_DAMAGE, START_ARMOR);
        startBoxesInit();
        items = new ArrayList<>();
        coinsAmount = 0;
        hasKey = false;

        LOGGER.info("The Player is created successfully");
    }

    /*--------------------------Methods for working with touch/hit/attack box--------------------------*/

    private void startBoxesInit() {
        initTouchBox(startX + TOUCH_BOX_X_OFFSET, startY + TOUCH_BOX_Y_OFFSET, TOUCH_BOX_WIDTH, TOUCH_BOX_HEIGHT);
        initHitBox(startX + HIT_BOX_X_OFFSET, startY + HIT_BOX_Y_OFFSET, HIT_BOX_WIDTH, HIT_BOX_HEIGHT);
        initAttackBox(startX + ATTACK_BOX_X_OFFSET, startY + ATTACK_BOX_Y_OFFSET, ATTACK_BOX_WIDTH, ATTACK_BOX_HEIGHT);
    }

    private void updateBoxes() {
        updateTouchBox();
        updateHitBox();
        updateAttackBox();
    }
    private void updateTouchBox() { updateBox(TOUCH_BOX_X_OFFSET, TOUCH_BOX_Y_OFFSET, TOUCH_BOX_WIDTH, REAL_SPRITE_WIDTH, Constants.Boxes.TOUCH); }
    private void updateHitBox() { updateBox(HIT_BOX_X_OFFSET, HIT_BOX_Y_OFFSET, HIT_BOX_WIDTH, REAL_SPRITE_WIDTH, Constants.Boxes.HIT); }
    private void updateAttackBox() { updateBox(ATTACK_BOX_X_OFFSET, ATTACK_BOX_Y_OFFSET, ATTACK_BOX_WIDTH, REAL_SPRITE_WIDTH, Constants.Boxes.ATTACK); }

    /*--------------------------------------Methods for rendering--------------------------------------*/

    /**
     * Draws player, player's health bar, coin amount and key icon.
     * @param g object for drawing.
     */
    public void render(Graphics g) {
        if (!isAlive)
            animIndex = getSpriteAmount(DEAD) - 1;

        g.drawImage(Images.getPlayerAnimations()[getIntByState(state)][animIndex],(int)x + flipX, (int)y,
                REAL_SPRITE_WIDTH * flipW, REAL_SPRITE_HEIGHT, null);

        // For debugging
        //drawTouchBox(g);
        //drawHitBox(g);
        //drawAttackBox(g);

        drawHealthBar(g);
        drawCoinsAmount(g);
        if (hasKey)
            g.drawImage(Images.getKeyIcon(), (int)KEY_ICON_X, (int)KEY_ICON_Y, (int)REAL_KEY_ICON_WIDTH, (int)REAL_KEY_ICON_HEIGHT, null);
    }

    private void drawHealthBar(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int)HEALTH_BAR_X, (int)HEALTH_BAR_Y, (int)(currentHealth / maxHealth * HEALTH_BAR_WIDTH), (int)HEALTH_BAR_HEIGHT);
        g.drawImage(Images.getHealthBar(), (int)HEALTH_BAR_IMAGE_X, (int)HEALTH_BAR_IMAGE_Y, (int)REAL_HEALTH_BAR_IMAGE_WIDTH, (int)REAL_HEALTH_BAR_IMAGE_HEIGHT, null);
    }

    private void drawCoinsAmount(Graphics g) {
        g.drawImage(Images.getCoinIcon(), (int)COIN_ICON_X, (int)COIN_ICON_Y, (int)REAL_COIN_ICON_WIDTH, (int)REAL_COIN_ICON_HEIGHT, null);
        HelpMethods.drawNumber(g, coinsAmount, NUMBER_X, NUMBER_Y, REAL_NUMBER_WIDTH, REAL_NUMBER_HEIGHT, BETWEEN_NUMBERS);
    }

    /*-------------------------------------Methods for updating-------------------------------------*/

    /**
     * Updates player's state.
     */
    public void update() {
        updateState();
        updateBoxes();
        updateAnimationTick();
        setNewState();
    }

    private void updateState() {
        Level level = Play.getLevel();

        switch (state) {
            case IDLE, RUN -> {
                move(level.getCurrentRoom().getTouchBoxes(), REAL_SPRITE_WIDTH);
                changeRoom(level); // change room if touches the window's border
            }
            case ATTACK -> {
                moving = false; // can't run when attacks
                checkAttack();
            }
            case DEAD, HURT -> {
                moving = false;
                attacking = false;
            }
        }
    }

    private void updateAnimationTick() { updateAnimationTick(getSpriteAmount(state)); }

    private void changeRoom(Level level) {
        Constants.Directions nextRoomDir = null;

        // If player's touch box touches the window's border
        if (touchBox.x <= 0) nextRoomDir = WEST;
        else if (touchBox.y <= 0) nextRoomDir = NORTH;
        else if (touchBox.x + touchBox.width >= REAL_WINDOW_WIDTH) nextRoomDir = EAST;
        else if (touchBox.y + touchBox.height >= REAL_WINDOW_HEIGHT) nextRoomDir = SOUTH;

        if (nextRoomDir != null) {
            level.goToNextRoom(nextRoomDir);
            this.setPosition(nextRoomDir);
        }
    }

    private void setPosition(Constants.Directions nextRoomDir) {
        switch (nextRoomDir) {
            case SOUTH -> y = REAL_WALL_THICKNESS - TOUCH_BOX_Y_OFFSET;
            case NORTH -> y = SOUTH_DOOR.y - touchBox.height - TOUCH_BOX_Y_OFFSET;
            case WEST -> x = EAST_DOOR.x - touchBox.width - TOUCH_BOX_X_OFFSET;
            case EAST -> x = REAL_WALL_THICKNESS - TOUCH_BOX_X_OFFSET;
        }
        updateBoxes();
    }

    /**
     * Checks if the player hit an obstacle or an enemy.
     */
    public void checkAttack() {
        Room currentRoom = Play.getLevel().getCurrentRoom();

        // Check only once per attack animation and only on the particular sprite
        if (attackChecked || animIndex != 3)
            return;

        attackChecked = true;
        EnemyManager.getInstance().checkPlayerHit(attackBox, (int)damage);   // check enemies
        currentRoom.checkPlayerHit(attackBox);  // check destroyable obstacles
    }

    /*-----------------------------Other methods for game logic-----------------------------*/

    /**
     * Adds given item to the player's inventory.
     * @param item item to add.
     */
    public void addItem(Item item) { items.add(item); }

    /**
     * Increments player's coin amount by 1.
     */
    public void addCoin() {
        coinsAmount++;
        if (coinsAmount > MAX_COIN)
            coinsAmount = MAX_COIN;
    }

    /**
     * Upgrades player's armor.
     * @param armorUp armor upgrade points.
     */
    public void upgradeArmor(int armorUp) {
        armor += armorUp;

        if (armor > MAX_ARMOR)
            armor = MAX_ARMOR;
    }

    /**
     * Upgrades player's damage.
     * @param damageUp damage upgrade points.
     */
    public void upgradeDamage(int damageUp) {
        damage += damageUp;

        if (damage > MAX_DAMAGE)
            damage = MAX_DAMAGE;
    }

    /**
     * Upgrades player's max health.
     * @param healthUp health upgrade points.
     */
    public void upgradeHealth(int healthUp) {
        maxHealth += healthUp;

        if (maxHealth > MAX_HEALTH)
            maxHealth = MAX_HEALTH;
    }

    /**
     * Upgrades player's speed.
     * @param speedUp speed upgrade points.
     */
    public void upgradeSpeed(int speedUp) {
        speed += speedUp;

        if (speed > MAX_SPEED)
            speed = MAX_SPEED;
    }

    /**
     * Heals the player for the given HP amount.
     * @param healthRestore HP points to restore.
     */
    public void heal(int healthRestore) {
        currentHealth += healthRestore;
        if (currentHealth > maxHealth)
            currentHealth = maxHealth;
    }

    /**
     * Reduces the number of coins on the price of the item in the shop.
     * @param price price of the item.
     */
    public void payPrice(int price) { coinsAmount -= price; }

    /*---------------------------------------Getters and setters---------------------------------------*/

    public boolean isHasKey() { return hasKey; }
    public void setHasKey(boolean hasKey) { this.hasKey = hasKey; }

    public int getCoinsAmount() { return coinsAmount; }
    public List<Item> getItems() { return items; }
}

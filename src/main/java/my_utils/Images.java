package my_utils;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import static my_utils.Constants.PlayerConstants.SPRITE_HEIGHT;
import static my_utils.Constants.PlayerConstants.SPRITE_WIDTH;
import static my_utils.Constants.RoomConstants.*;
import static my_utils.Constants.ObstacleConstants.*;
import static my_utils.Constants.UIConstants.*;
import static my_utils.Constants.UIConstants.EditorConstants.*;
import static my_utils.Constants.ItemsConstants.ArmorBoosterConstants.*;
import static my_utils.Constants.ItemsConstants.SpeedBoosterConstants.*;
import static my_utils.Constants.ItemsConstants.DamageBoosterConstants.*;
import static my_utils.Constants.ItemsConstants.HealthBoosterConstants.*;
import static my_utils.Constants.ItemsConstants.HealPotionConstants.*;
import static my_utils.Constants.ItemsConstants.KeyConstants.*;
import static my_utils.Constants.ItemsConstants.CoinConstants.*;
import static my_utils.Constants.UIConstants.PlayConstants.NUMBER_HEIGHT;
import static my_utils.Constants.UIConstants.PlayConstants.NUMBER_WIDTH;

/**
 * Class for loading all game images.
 */
public class Images {
    // Animations
    private static BufferedImage[][] playerAnimations;
    private static BufferedImage[][] spearmanEnemyAnimations;
    private static BufferedImage[][] knightEnemyAnimations;
    // Exit hole
    private static BufferedImage[] exitHole;
    // Doors
    private static BufferedImage[] commonDoors;
    private static BufferedImage[] shopDoors;
    private static BufferedImage[] finalDoors;
    private static BufferedImage[] finalDoorsLocked;
    // Rooms
    private static BufferedImage commonRoomImage;
    private static BufferedImage shopRoomImage;
    private static BufferedImage finalRoomImage;
    // Obstacles
    private static BufferedImage stoneImage;
    private static BufferedImage[] barrelAnimation;
    // Buttons
    private static BufferedImage[][] buttons;
    // UI Menu
    private static BufferedImage menuFrame;
    private static BufferedImage menuBackground;
    // UI Selection
    private static BufferedImage selectionFrame;
    private static BufferedImage selectionBackground;
    // UI Editor
    private static BufferedImage editorChoosingFrame;
    private static BufferedImage editorSavingFrame;
    private static BufferedImage editorChoosingButton1;
    private static BufferedImage editorChoosingButton2;
    // UI Play
    private static BufferedImage deathScreenFrame;
    private static BufferedImage victoryScreenFrame;
    private static BufferedImage healthBar;
    private static BufferedImage[] numbers;
    private static BufferedImage coinIcon;
    private static BufferedImage keyIcon;
    // Items
    private static BufferedImage[] armorBoosterAnimation;
    private static BufferedImage[] speedBoosterAnimation;
    private static BufferedImage[] damageBoosterAnimation;
    private static BufferedImage[] healthBoosterAnimation;
    private static BufferedImage[] healPotionAnimation;
    private static BufferedImage[] coinAnimation;
    private static BufferedImage[] keyAnimation;

    /**
     * Loads all game images.
     * @throws FileNotFoundException is thrown if image file is not founded.
     */
    public static void loadAll() throws IOException {
        loadPlayerAnimations();
        loadSpearmanEnemyAnimations();
        loadKnightEnemyAnimations();
        loadExitHole();
        loadDoorsImages(LoadSave.COMMON_DOORS);
        loadDoorsImages(LoadSave.SHOP_DOORS);
        loadDoorsImages(LoadSave.FINAL_DOORS);
        loadDoorsImages(LoadSave.FINAL_DOORS_LOCKED);
        loadRoomsImages();
        loadStoneImage();
        loadBarrelAnimation();
        loadButtons();
        loadMenuImages();
        loadSelectionImages();
        loadEditorImages();
        loadPlayImages();
        loadItems();
    }

    private static void loadSelectionImages() throws IOException {
        selectionFrame = LoadSave.loadImageFromFile(LoadSave.SELECTION_FRAME);
        selectionBackground = LoadSave.loadImageFromFile(LoadSave.SELECTION_BACKGROUND);
    }

    private static void loadButtons() throws IOException {
        BufferedImage image = LoadSave.loadImageFromFile(LoadSave.BUTTONS);
        buttons = new BufferedImage[6][2];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i][0] = image.getSubimage(0, i * BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
            buttons[i][1] = image.getSubimage(BUTTON_WIDTH, i * BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
        }
    }

    private static void loadExitHole() throws IOException {
        BufferedImage image = LoadSave.loadImageFromFile(LoadSave.EXIT_HOLE);
        exitHole = new BufferedImage[3];
        for (int i = 0; i < exitHole.length; i++)
            exitHole[i] = image.getSubimage(i * EXIT_HOLE_WIDTH, 0, EXIT_HOLE_WIDTH, EXIT_HOLE_HEIGHT);
    }

    private static void loadPlayImages() throws IOException {
        deathScreenFrame = LoadSave.loadImageFromFile(LoadSave.DEATH_SCREEN_FRAME);
        victoryScreenFrame = LoadSave.loadImageFromFile(LoadSave.VICTORY_SCREEN_FRAME);
        healthBar = LoadSave.loadImageFromFile(LoadSave.HEALTH_BAR);
        coinIcon = LoadSave.loadImageFromFile(LoadSave.COIN_ICON);
        keyIcon = LoadSave.loadImageFromFile(LoadSave.KEY_ICON);
        numbers = new BufferedImage[10];
        BufferedImage numbersImage = LoadSave.loadImageFromFile(LoadSave.NUMBERS);
        for (int i = 0; i < numbers.length; i++)
            numbers[i] = numbersImage.getSubimage(i * NUMBER_WIDTH, 0, NUMBER_WIDTH, NUMBER_HEIGHT);
    }

    private static void loadPlayerAnimations() throws IOException {
        BufferedImage image = LoadSave.loadImageFromFile(LoadSave.PLAYER_ATLAS);
        playerAnimations = new BufferedImage[10][8];
        for (int i = 0; i < playerAnimations.length; i++)
            for (int j = 0; j < playerAnimations[i].length; j++)
                playerAnimations[i][j] = image.getSubimage(j * SPRITE_WIDTH, i * SPRITE_HEIGHT, SPRITE_WIDTH, SPRITE_HEIGHT);
    }

    private static void loadSpearmanEnemyAnimations() throws IOException {
        BufferedImage image = LoadSave.loadImageFromFile(LoadSave.SKELETON_SPEARMAN_ATLAS);
        spearmanEnemyAnimations = new BufferedImage[10][7];
        for (int i = 0; i < spearmanEnemyAnimations.length; i++)
            for (int j = 0; j < spearmanEnemyAnimations[i].length; j++)
                spearmanEnemyAnimations[i][j] = image.getSubimage(j * Constants.EnemyConstants.SpearmanConstants.SPRITE_WIDTH, i * Constants.EnemyConstants.SpearmanConstants.SPRITE_HEIGHT, Constants.EnemyConstants.SpearmanConstants.SPRITE_WIDTH, Constants.EnemyConstants.SpearmanConstants.SPRITE_HEIGHT);
    }

    private static void loadKnightEnemyAnimations() throws IOException {
        BufferedImage image = LoadSave.loadImageFromFile(LoadSave.KNIGHT_ATLAS);
        knightEnemyAnimations = new BufferedImage[11][8];
        for (int i = 0; i < knightEnemyAnimations.length; i++)
            for (int j = 0; j < knightEnemyAnimations[i].length; j++)
                knightEnemyAnimations[i][j] = image.getSubimage(j * Constants.EnemyConstants.KnightConstants.SPRITE_WIDTH, i * Constants.EnemyConstants.KnightConstants.SPRITE_HEIGHT, Constants.EnemyConstants.KnightConstants.SPRITE_WIDTH, Constants.EnemyConstants.KnightConstants.SPRITE_HEIGHT);
    }

    private static void loadDoorsImages(String doorType) throws IOException {
        int doorWidth = 0, doorHeight = 0;
        switch (doorType) {
            case LoadSave.COMMON_DOORS -> {
                doorWidth = COMMON_DOOR_WIDTH;
                doorHeight = COMMON_DOOR_HEIGHT;
            }
            case LoadSave.SHOP_DOORS -> {
                doorWidth = SHOP_DOOR_WIDTH;
                doorHeight = SHOP_DOOR_HEIGHT;
            }
            case LoadSave.FINAL_DOORS, LoadSave.FINAL_DOORS_LOCKED -> {
                doorWidth = FINAL_DOOR_WIDTH;
                doorHeight = FINAL_DOOR_HEIGHT;
            }
        }
        BufferedImage image = LoadSave.loadImageFromFile(doorType);
        BufferedImage[] doorsImages = new BufferedImage[4];
        doorsImages[0] = image.getSubimage(0,0, doorHeight, doorWidth);
        doorsImages[1] = image.getSubimage(doorHeight,0, doorHeight, doorWidth);
        doorsImages[2] = image.getSubimage(2 * doorHeight,0, doorWidth, doorHeight);
        doorsImages[3] = image.getSubimage(2 * doorHeight + doorWidth,0, doorWidth, doorHeight);

        switch (doorType) {
            case LoadSave.COMMON_DOORS -> commonDoors = doorsImages;
            case LoadSave.SHOP_DOORS -> shopDoors = doorsImages;
            case LoadSave.FINAL_DOORS -> finalDoors = doorsImages;
            case LoadSave.FINAL_DOORS_LOCKED -> finalDoorsLocked = doorsImages;
        }
    }

    private static void loadRoomsImages() throws IOException {
        commonRoomImage = LoadSave.loadImageFromFile(LoadSave.COMMON_ROOM);
        shopRoomImage = LoadSave.loadImageFromFile(LoadSave.SHOP_ROOM);
        finalRoomImage = LoadSave.loadImageFromFile(LoadSave.FINAL_ROOM);
    }

    private static void loadStoneImage() throws IOException {
        stoneImage = LoadSave.loadImageFromFile(LoadSave.STONE);
    }

    private static void loadBarrelAnimation() throws IOException {
        BufferedImage image = LoadSave.loadImageFromFile(LoadSave.BARREL);
        barrelAnimation = new BufferedImage[BARREL_SPRITE_AMOUNT];
        for (int i = 0; i < barrelAnimation.length; i++)
            barrelAnimation[i] = image.getSubimage(i * BARREL_SPRITE_WIDTH, 0, BARREL_SPRITE_WIDTH, BARREL_SPRITE_HEIGHT);
    }

    private static void loadMenuImages() throws IOException {
        menuFrame = LoadSave.loadImageFromFile(LoadSave.MENU_FRAME);
        menuBackground = LoadSave.loadImageFromFile(LoadSave.MENU_BACKGROUND);
    }

    private static void loadEditorImages() throws IOException {
        editorChoosingFrame = LoadSave.loadImageFromFile(LoadSave.EDITOR_CHOOSING_FRAME);
        editorSavingFrame = LoadSave.loadImageFromFile(LoadSave.EDITOR_SAVING_FRAME);
        BufferedImage choosingButtons = LoadSave.loadImageFromFile(LoadSave.EDITOR_CHOOSING_BUTTONS);
        editorChoosingButton1 = choosingButtons.getSubimage(0,0, CHOOSING_BUTTON_WIDTH, CHOOSING_BUTTON_HEIGHT);
        editorChoosingButton2 = choosingButtons.getSubimage(CHOOSING_BUTTON_WIDTH, 0, CHOOSING_BUTTON_WIDTH, CHOOSING_BUTTON_HEIGHT);
    }

    private static void loadItems() throws IOException {
        loadArmorBoosterAnimation();
        loadSpeedBoosterAnimation();
        loadDamageBoosterAnimation();
        loadHealthBoosterAnimation();
        loadHealPotionAnimation();
        loadKeyAnimation();
        loadCoinAnimation();
    }

    private static void loadArmorBoosterAnimation() throws IOException {
        BufferedImage image = LoadSave.loadImageFromFile(LoadSave.ARMOR_BOOSTER);
        armorBoosterAnimation = new BufferedImage[ARMOR_BOOSTER_SPRITE_AMOUNT];
        for (int i = 0; i < armorBoosterAnimation.length; i++)
            armorBoosterAnimation[i] = image.getSubimage(i * ARMOR_BOOSTER_SPRITE_WIDTH, 0, ARMOR_BOOSTER_SPRITE_WIDTH, ARMOR_BOOSTER_SPRITE_HEIGHT);
    }

    private static void loadSpeedBoosterAnimation() throws IOException {
        BufferedImage image = LoadSave.loadImageFromFile(LoadSave.SPEED_BOOSTER);
        speedBoosterAnimation = new BufferedImage[SPEED_BOOSTER_SPRITE_AMOUNT];
        for (int i = 0; i < speedBoosterAnimation.length; i++)
            speedBoosterAnimation[i] = image.getSubimage(i * SPEED_BOOSTER_SPRITE_WIDTH, 0, SPEED_BOOSTER_SPRITE_WIDTH, SPEED_BOOSTER_SPRITE_HEIGHT);
    }

    private static void loadDamageBoosterAnimation() throws IOException {
        BufferedImage image = LoadSave.loadImageFromFile(LoadSave.DAMAGE_BOOSTER);
        damageBoosterAnimation = new BufferedImage[DAMAGE_BOOSTER_SPRITE_AMOUNT];
        for (int i = 0; i < damageBoosterAnimation.length; i++)
            damageBoosterAnimation[i] = image.getSubimage(i * DAMAGE_BOOSTER_SPRITE_WIDTH, 0, DAMAGE_BOOSTER_SPRITE_WIDTH, DAMAGE_BOOSTER_SPRITE_HEIGHT);
    }

    private static void loadHealthBoosterAnimation() throws IOException {
        BufferedImage image = LoadSave.loadImageFromFile(LoadSave.HEALTH_BOOSTER);
        healthBoosterAnimation = new BufferedImage[HEALTH_BOOSTER_SPRITE_AMOUNT];
        for (int i = 0; i < healthBoosterAnimation.length; i++)
            healthBoosterAnimation[i] = image.getSubimage(i * HEALTH_BOOSTER_SPRITE_WIDTH, 0, HEALTH_BOOSTER_SPRITE_WIDTH, HEALTH_BOOSTER_SPRITE_HEIGHT);
    }

    private static void loadHealPotionAnimation() throws IOException {
        BufferedImage image = LoadSave.loadImageFromFile(LoadSave.HEAL_POTION);
        healPotionAnimation = new BufferedImage[HEAL_POTION_SPRITE_AMOUNT];
        for (int i = 0; i < healPotionAnimation.length; i++)
            healPotionAnimation[i] = image.getSubimage(i * HEAL_POTION_SPRITE_WIDTH, 0, HEAL_POTION_SPRITE_WIDTH, HEAL_POTION_SPRITE_HEIGHT);
    }

    private static void loadKeyAnimation() throws IOException {
        BufferedImage image = LoadSave.loadImageFromFile(LoadSave.KEY);
        keyAnimation = new BufferedImage[KEY_SPRITE_AMOUNT];
        for (int i = 0; i < keyAnimation.length; i++)
            keyAnimation[i] = image.getSubimage(i * KEY_SPRITE_WIDTH, 0, KEY_SPRITE_WIDTH, KEY_SPRITE_HEIGHT);
    }

    private static void loadCoinAnimation() throws IOException {
        BufferedImage image = LoadSave.loadImageFromFile(LoadSave.COIN);
        coinAnimation = new BufferedImage[COIN_SPRITE_AMOUNT];
        for (int i = 0; i < coinAnimation.length; i++)
            coinAnimation[i] = image.getSubimage(i * COIN_SPRITE_WIDTH, 0, COIN_SPRITE_WIDTH, COIN_SPRITE_HEIGHT);
    }

    // Getters
    public static BufferedImage[][] getPlayerAnimations() { return playerAnimations; }
    public static BufferedImage[][] getSpearmanEnemyAnimations() { return spearmanEnemyAnimations; }
    public static BufferedImage[][] getKnightEnemyAnimations() { return knightEnemyAnimations; }
    public static BufferedImage[] getExitHole() { return exitHole; }
    public static BufferedImage[] getCommonDoors() { return commonDoors; }
    public static BufferedImage[] getShopDoors() { return shopDoors; }
    public static BufferedImage[] getFinalDoors() { return finalDoors; }
    public static BufferedImage[] getFinalDoorsLocked() { return finalDoorsLocked; }
    public static BufferedImage getCommonRoomImage() { return commonRoomImage; }
    public static BufferedImage getShopRoomImage() { return shopRoomImage; }
    public static BufferedImage getFinalRoomImage() { return finalRoomImage; }
    public static BufferedImage getStoneImage() { return stoneImage; }
    public static BufferedImage[] getBarrelAnimation() { return barrelAnimation; }
    public static BufferedImage[][] getButtons() { return buttons; }
    public static BufferedImage getMenuBackground() { return menuBackground; }
    public static BufferedImage getMenuFrame() { return menuFrame; }
    public static BufferedImage getSelectionBackground() { return selectionBackground; }
    public static BufferedImage getSelectionFrame() { return selectionFrame; }
    public static BufferedImage getEditorChoosingFrame() { return editorChoosingFrame; }
    public static BufferedImage getEditorSavingFrame() { return editorSavingFrame; }
    public static BufferedImage getEditorChoosingButton1() { return editorChoosingButton1; }
    public static BufferedImage getEditorChoosingButton2() { return editorChoosingButton2; }
    public static BufferedImage getDeathScreenFrame() { return deathScreenFrame; }
    public static BufferedImage getVictoryScreenFrame() { return victoryScreenFrame; }
    public static BufferedImage getHealthBar() { return healthBar; }
    public static BufferedImage[] getNumbers() { return numbers; }
    public static BufferedImage getCoinIcon() {return coinIcon; }
    public static BufferedImage getKeyIcon() { return keyIcon; }
    public static BufferedImage[] getArmorBoosterAnimation() { return armorBoosterAnimation; }
    public static BufferedImage[] getSpeedBoosterAnimation() { return speedBoosterAnimation; }
    public static BufferedImage[] getDamageBoosterAnimation() { return damageBoosterAnimation; }
    public static BufferedImage[] getHealthBoosterAnimation() { return healthBoosterAnimation; }
    public static BufferedImage[] getHealPotionAnimation() { return healPotionAnimation; }
    public static BufferedImage[] getCoinAnimation() { return coinAnimation; }
    public static BufferedImage[] getKeyAnimation() { return keyAnimation; }
}

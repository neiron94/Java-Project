package my_utils;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;

import static my_utils.Constants.WindowConstants.*;

/**
 * Class for constants.
 */
public class Constants {
    public static final int MAX_FPS = 120;
    public static final int MAX_UPS = 200;
    public static final int NORM_ANIM_SPEED = 30;
    public static final int HURT_ANIM_SPEED = 40;
    public static final int DEAD_ANIM_SPEED = 80;

    /**
     * Max number of rooms that can be added to start room in one direction.
     */
    public static final int ROOM_MATRIX_LENGTH_FROM_START = 5;
    /**
     * Max length (width and height) of room matrix (field of a level object). Should be odd.
     */
    public static final int REAL_ROOM_MATRIX_SIZE = ROOM_MATRIX_LENGTH_FROM_START * 2 + 1;

    /**
     * Represents all types of buttons of the game UI (except editor choosing buttons). Used for loading
     * buttons images from png file.
     */
    public enum ButtonTypes {
        SAVE, BACK, MENU, PLAY, EDITOR, EXIT;

        /**
         * Returns index of the row in the png file, where specific button image is located.
         * @param type type of the searched button.
         * @return index of the row in the png file.
         */
        public static int getIndexByButtonType(ButtonTypes type) {
            return switch(type) {
                case SAVE -> 0;
                case BACK -> 1;
                case MENU -> 2;
                case PLAY -> 3;
                case EDITOR -> 4;
                case EXIT -> 5;
            };
        }
    }

    /**
     * Represents stages of the Editor.
     */
    public enum EditStages {
        CREATING, CHOOSING, SAVING
    }

    /**
     * Represents all items in the game.
     */
    public enum ItemTypes {
        COIN, KEY, HEAL_POTION, ARMOR_BOOSTER, DAMAGE_BOOSTER, SPEED_BOOSTER, HEALTH_BOOSTER
    }

    /**
     * Represents objects, which can be added to room in Editor mode.
     */
    public enum Entities {
        KNIGHT, SPEARMAN, STONE, BARREL
    }

    /**
     * Represents all possible game states.
     */
    public enum StatesEnum {
        MENU, PLAY, EDITOR, EXIT, SELECTION
    }

    /**
     * Represents all room types.
     */
    public enum RoomTypes {
        COMMON, SHOP, FINAL
    }

    /**
     *  Represents touchBox/hitBox/AttackBox type.
     */
    public enum Boxes {
        TOUCH, HIT, ATTACK
    }

    /**
     * Represents states of the creature.
     */
    public enum CreatureStates {
        IDLE, RUN, ATTACK, HURT, DEAD
    }

    /**
     * Represents all possible directions.
     */
    public enum Directions {
        NORTH, SOUTH, EAST, WEST;

        /**
         * Returns index of the searched door image in the png file.
         * @param direction direction in which door is turned to.
         * @return index of the door image in the png file.
         */
        public static int getDoorIndexByDir(Directions direction) {
            return switch (direction) {
                case WEST -> 0;
                case EAST -> 1;
                case SOUTH -> 2;
                case NORTH -> 3;
            };
        }
    }

    public static class ItemsConstants {
        public static class ArmorBoosterConstants {
            public static final int ARMOR_BOOSTER_SPRITE_WIDTH = 84;
            public static final int ARMOR_BOOSTER_SPRITE_HEIGHT = 110;
            public static final float REAL_ARMOR_BOOSTER_WIDTH = ARMOR_BOOSTER_SPRITE_WIDTH * 0.4f * SCALE;
            public static final float REAL_ARMOR_BOOSTER_HEIGHT = ARMOR_BOOSTER_SPRITE_HEIGHT * 0.4f * SCALE;

            public static final float ARMOR_BOOSTER_TOUCH_BOX_WIDTH = REAL_ARMOR_BOOSTER_WIDTH;
            public static final float ARMOR_BOOSTER_TOUCH_BOX_HEIGHT = 0.5f * REAL_ARMOR_BOOSTER_HEIGHT;
            public static final float ARMOR_BOOSTER_TOUCH_BOX_OFFSET_X = 0;
            public static final float ARMOR_BOOSTER_TOUCH_BOX_OFFSET_Y = REAL_ARMOR_BOOSTER_HEIGHT - ARMOR_BOOSTER_TOUCH_BOX_HEIGHT;

            public static final int ARMOR_BOOSTER_SPRITE_AMOUNT = 4;

            public static final int ARMOR_UP = 1;
            public static final int COST = 7;
        }

        public static class SpeedBoosterConstants {
            public static final int SPEED_BOOSTER_SPRITE_WIDTH = 60;
            public static final int SPEED_BOOSTER_SPRITE_HEIGHT = 104;
            public static final float REAL_SPEED_BOOSTER_WIDTH = SPEED_BOOSTER_SPRITE_WIDTH * 0.4f * SCALE;
            public static final float REAL_SPEED_BOOSTER_HEIGHT = SPEED_BOOSTER_SPRITE_HEIGHT * 0.4f * SCALE;

            public static final float SPEED_BOOSTER_TOUCH_BOX_WIDTH = REAL_SPEED_BOOSTER_WIDTH;
            public static final float SPEED_BOOSTER_TOUCH_BOX_HEIGHT = 0.5f * REAL_SPEED_BOOSTER_HEIGHT;
            public static final float SPEED_BOOSTER_TOUCH_BOX_OFFSET_X = 0;
            public static final float SPEED_BOOSTER_TOUCH_BOX_OFFSET_Y = REAL_SPEED_BOOSTER_HEIGHT - SPEED_BOOSTER_TOUCH_BOX_HEIGHT;

            public static final int SPEED_BOOSTER_SPRITE_AMOUNT = 4;

            public static final int SPEED_UP = (int)(1 * SCALE);
            public static final int COST = 5;
        }

        public static class DamageBoosterConstants {
            public static final int DAMAGE_BOOSTER_SPRITE_WIDTH = 90;
            public static final int DAMAGE_BOOSTER_SPRITE_HEIGHT = 110;
            public static final float REAL_DAMAGE_BOOSTER_WIDTH = DAMAGE_BOOSTER_SPRITE_WIDTH * 0.4f * SCALE;
            public static final float REAL_DAMAGE_BOOSTER_HEIGHT = DAMAGE_BOOSTER_SPRITE_HEIGHT * 0.4f * SCALE;

            public static final float DAMAGE_BOOSTER_TOUCH_BOX_WIDTH = REAL_DAMAGE_BOOSTER_WIDTH;
            public static final float DAMAGE_BOOSTER_TOUCH_BOX_HEIGHT = 0.5f * REAL_DAMAGE_BOOSTER_HEIGHT;
            public static final float DAMAGE_BOOSTER_TOUCH_BOX_OFFSET_X = 0;
            public static final float DAMAGE_BOOSTER_TOUCH_BOX_OFFSET_Y = REAL_DAMAGE_BOOSTER_HEIGHT - DAMAGE_BOOSTER_TOUCH_BOX_HEIGHT;

            public static final int DAMAGE_BOOSTER_SPRITE_AMOUNT = 4;

            public static final int DAMAGE_UP = 5;
            public static final int COST = 10;
        }

        public static class HealthBoosterConstants {
            public static final int HEALTH_BOOSTER_SPRITE_WIDTH = 84;
            public static final int HEALTH_BOOSTER_SPRITE_HEIGHT = 104;
            public static final float REAL_HEALTH_BOOSTER_WIDTH = HEALTH_BOOSTER_SPRITE_WIDTH * 0.4f * SCALE;
            public static final float REAL_HEALTH_BOOSTER_HEIGHT = HEALTH_BOOSTER_SPRITE_HEIGHT * 0.4f * SCALE;

            public static final float HEALTH_BOOSTER_TOUCH_BOX_WIDTH = REAL_HEALTH_BOOSTER_WIDTH;
            public static final float HEALTH_BOOSTER_TOUCH_BOX_HEIGHT = 0.5f * REAL_HEALTH_BOOSTER_HEIGHT;
            public static final float HEALTH_BOOSTER_TOUCH_BOX_OFFSET_X = 0;
            public static final float HEALTH_BOOSTER_TOUCH_BOX_OFFSET_Y = REAL_HEALTH_BOOSTER_HEIGHT - HEALTH_BOOSTER_TOUCH_BOX_HEIGHT;

            public static final int HEALTH_BOOSTER_SPRITE_AMOUNT = 4;

            public static final int HEALTH_UP = 15;
            public static final int COST = 7;
        }

        public static class HealPotionConstants {
            public static final int HEAL_POTION_SPRITE_WIDTH = 90;
            public static final int HEAL_POTION_SPRITE_HEIGHT = 110;
            public static final float REAL_HEAL_POTION_WIDTH = HEAL_POTION_SPRITE_WIDTH * 0.3f * SCALE;
            public static final float REAL_HEAL_POTION_HEIGHT = HEAL_POTION_SPRITE_HEIGHT * 0.3f * SCALE;

            public static final float HEAL_POTION_TOUCH_BOX_WIDTH = REAL_HEAL_POTION_WIDTH;
            public static final float HEAL_POTION_TOUCH_BOX_HEIGHT = 0.5f * REAL_HEAL_POTION_HEIGHT;
            public static final float HEAL_POTION_TOUCH_BOX_OFFSET_X = 0;
            public static final float HEAL_POTION_TOUCH_BOX_OFFSET_Y = REAL_HEAL_POTION_HEIGHT - HEAL_POTION_TOUCH_BOX_HEIGHT;

            public static final int HEAL_POTION_SPRITE_AMOUNT = 4;

            public static final int HEALTH_RESTORE = 10;
            public static final int COST = 3;
        }

        public static class KeyConstants {
            public static final int KEY_SPRITE_WIDTH = 14;
            public static final int KEY_SPRITE_HEIGHT = 26;
            public static final float REAL_KEY_WIDTH = KEY_SPRITE_WIDTH * SCALE;
            public static final float REAL_KEY_HEIGHT = KEY_SPRITE_HEIGHT * SCALE;

            public static final float KEY_TOUCH_BOX_WIDTH = REAL_KEY_WIDTH;
            public static final float KEY_TOUCH_BOX_HEIGHT = 0.5f * REAL_KEY_HEIGHT;
            public static final float KEY_TOUCH_BOX_OFFSET_X = 0;
            public static final float KEY_TOUCH_BOX_OFFSET_Y = REAL_KEY_HEIGHT - KEY_TOUCH_BOX_HEIGHT;

            public static final int KEY_SPRITE_AMOUNT = 4;

            public static final int COST = -1;
        }

        public static class CoinConstants {
            public static final int COIN_SPRITE_WIDTH = 18;
            public static final int COIN_SPRITE_HEIGHT = 16;
            public static final float REAL_COIN_WIDTH = COIN_SPRITE_WIDTH * 0.9f * SCALE;
            public static final float REAL_COIN_HEIGHT = COIN_SPRITE_HEIGHT * 0.9f * SCALE;

            public static final float COIN_TOUCH_BOX_WIDTH = REAL_COIN_WIDTH;
            public static final float COIN_TOUCH_BOX_HEIGHT = 0.5f * REAL_COIN_HEIGHT;
            public static final float COIN_TOUCH_BOX_OFFSET_X = 0;
            public static final float COIN_TOUCH_BOX_OFFSET_Y = REAL_COIN_HEIGHT - COIN_TOUCH_BOX_HEIGHT;

            public static final int COIN_SPRITE_AMOUNT = 4;

            public static final int COST = 1;
        }
    }

    public static class UIConstants {
        public static final int BUTTON_WIDTH = 189;
        public static final int BUTTON_HEIGHT = 101;

        public static class MenuConstants {
            public static final int MENU_FRAME_WIDTH = (int)(214 * SCALE);
            public static final int MENU_FRAME_HEIGHT = (int)(254 * SCALE);
            public static final int MENU_FRAME_X = REAL_WINDOW_CENTER.x - MENU_FRAME_WIDTH / 2;
            public static final int MENU_FRAME_Y = REAL_WINDOW_CENTER.y - MENU_FRAME_HEIGHT / 2;

            // Buttons
            public static final int REAL_BUTTON_WIDTH = (int)(BUTTON_WIDTH * 0.5 * SCALE);
            public static final int REAL_BUTTON_HEIGHT = (int)(BUTTON_HEIGHT * 0.5 * SCALE);
            public static final int BUTTON_X = REAL_WINDOW_CENTER.x - REAL_BUTTON_WIDTH / 2;
            private static final int BETWEEN_BUTTONS = (int)(6 * SCALE);
            public static final int PLAY_BUTTON_Y = MENU_FRAME_Y + (int)(55 * SCALE);
            public static final int EDITOR_BUTTON_Y = PLAY_BUTTON_Y + REAL_BUTTON_HEIGHT + BETWEEN_BUTTONS;
            public static final int EXIT_BUTTON_Y = EDITOR_BUTTON_Y + REAL_BUTTON_HEIGHT + BETWEEN_BUTTONS;
        }

        public static class SelectionConstants {
            public static String CHARACTERS_FOLDER_PATH = "src/main/resources/saves/characters";
            public static String LEVELS_FOLDER_PATH = "src/main/resources/saves/levels";
            public static final File CHARACTERS_FOLDER = new File(CHARACTERS_FOLDER_PATH);
            public static final File LEVELS_FOLDER = new File(LEVELS_FOLDER_PATH);

            public static final int SELECTION_FRAME_WIDTH = (int)(236 * SCALE * 2);
            public static final int SELECTION_FRAME_HEIGHT = (int)(189 * SCALE * 2);
            public static final int SELECTION_FRAME_X = REAL_WINDOW_CENTER.x - SELECTION_FRAME_WIDTH / 2;
            public static final int SELECTION_FRAME_Y = REAL_WINDOW_CENTER.y - SELECTION_FRAME_HEIGHT / 2;

            // Buttons
            public static final int REAL_BUTTON_WIDTH = (int)(BUTTON_WIDTH * 0.5 * SCALE);
            public static final int REAL_BUTTON_HEIGHT = (int)(BUTTON_HEIGHT * 0.5 * SCALE);

            public static final int BACK_BUTTON_X = (int) (SELECTION_FRAME_X + 0.25f * (SELECTION_FRAME_WIDTH - REAL_BUTTON_WIDTH));
            public static final int BACK_BUTTON_Y = (int) (SELECTION_FRAME_Y + 0.85f * (SELECTION_FRAME_HEIGHT - REAL_BUTTON_HEIGHT));

            public static final int PLAY_BUTTON_X = SELECTION_FRAME_X + SELECTION_FRAME_WIDTH - REAL_BUTTON_WIDTH - (BACK_BUTTON_X - SELECTION_FRAME_X);
            public static final int PLAY_BUTTON_Y = BACK_BUTTON_Y;

            public static final int MAX_BUTTONS_AMOUNT = 10;

            public static final int LEVEL_BUTTON_WIDTH = (int) (170 * SCALE);
            public static final int LEVEL_BUTTON_HEIGHT = (int) (20 * SCALE);
            public static final int LEVEL_BUTTON_X = (int) (SELECTION_FRAME_X + 0.2 * (SELECTION_FRAME_WIDTH - LEVEL_BUTTON_WIDTH));
            public static final int LEVEL_BUTTON_Y = (int) (SELECTION_FRAME_Y + 0.25 * (SELECTION_FRAME_HEIGHT - LEVEL_BUTTON_HEIGHT * MAX_BUTTONS_AMOUNT));

            public static final int CHARACTER_BUTTON_WIDTH = LEVEL_BUTTON_WIDTH;
            public static final int CHARACTER_BUTTON_HEIGHT = LEVEL_BUTTON_HEIGHT;
            public static final int CHARACTER_BUTTON_X = SELECTION_FRAME_X + SELECTION_FRAME_WIDTH - CHARACTER_BUTTON_WIDTH - (LEVEL_BUTTON_X - SELECTION_FRAME_X);
            public static final int CHARACTER_BUTTON_Y = LEVEL_BUTTON_Y;

            public static final Color PANEL_COLOR_1 = new Color(100, 65, 50);
            public static final Color PANEL_COLOR_2 = new Color(151, 107, 92);

        }

        public static class EditorConstants {
            private static final float SAVING_FRAME_WIDTH = 198;
            private static final float SAVING_FRAME_HEIGHT = 107;
            public static final float REAL_SAVING_FRAME_WIDTH = SAVING_FRAME_WIDTH * 2.5f * SCALE;
            public static final float REAL_SAVING_FRAME_HEIGHT = SAVING_FRAME_HEIGHT * 2.5f * SCALE;
            public static final float SAVING_FRAME_X = REAL_WINDOW_CENTER.x - REAL_SAVING_FRAME_WIDTH / 2;
            public static final float SAVING_FRAME_Y = REAL_WINDOW_CENTER.y - REAL_SAVING_FRAME_HEIGHT / 2;

            private static final float CHOOSING_FRAME_WIDTH = 198;
            private static final float CHOOSING_FRAME_HEIGHT = 107;
            public static final float REAL_CHOOSING_FRAME_WIDTH = CHOOSING_FRAME_WIDTH * 2.5f * SCALE;
            public static final float REAL_CHOOSING_FRAME_HEIGHT = CHOOSING_FRAME_HEIGHT * 2.5f * SCALE;
            public static final float CHOOSING_FRAME_X = REAL_WINDOW_CENTER.x - REAL_CHOOSING_FRAME_WIDTH / 2;
            public static final float CHOOSING_FRAME_Y = REAL_WINDOW_CENTER.y - REAL_CHOOSING_FRAME_HEIGHT / 2;

            public static final int CHOOSING_BUTTON_WIDTH = 68;
            public static final int CHOOSING_BUTTON_HEIGHT = 72;
            public static final float REAL_CHOOSING_BUTTON_WIDTH = CHOOSING_BUTTON_WIDTH * SCALE;
            public static final float REAL_CHOOSING_BUTTON_HEIGHT = CHOOSING_BUTTON_HEIGHT * SCALE;

            public static final float BETWEEN_CHOOSING_BUTTONS = REAL_CHOOSING_BUTTON_WIDTH + 10 * SCALE;
            public static final float CHOOSING_BUTTON_Y = CHOOSING_FRAME_Y + REAL_CHOOSING_FRAME_HEIGHT / 2 - REAL_CHOOSING_BUTTON_HEIGHT / 2;
            public static final float CHOOSING_BUTTON_X1 = CHOOSING_FRAME_X + 0.2f * (REAL_CHOOSING_FRAME_WIDTH - REAL_CHOOSING_BUTTON_WIDTH);
            public static final float CHOOSING_BUTTON_X2 = CHOOSING_BUTTON_X1 + BETWEEN_CHOOSING_BUTTONS;
            public static final float CHOOSING_BUTTON_X3 = CHOOSING_BUTTON_X2 + BETWEEN_CHOOSING_BUTTONS;
            public static final float CHOOSING_BUTTON_X4 = CHOOSING_BUTTON_X3 + BETWEEN_CHOOSING_BUTTONS;

            public static final float REAL_SAVE_BUTTON_WIDTH = (int)(BUTTON_WIDTH * 0.7 * SCALE);
            public static final float REAL_SAVE_BUTTON_HEIGHT = (int)(BUTTON_HEIGHT * 0.7 * SCALE);

            public static final float SAVE_BUTTON_X = SAVING_FRAME_X + 0.8f * (REAL_SAVING_FRAME_WIDTH - REAL_SAVE_BUTTON_WIDTH);
            public static final float SAVE_BUTTON_Y = SAVING_FRAME_Y + 0.7f * (REAL_SAVING_FRAME_HEIGHT - REAL_SAVE_BUTTON_HEIGHT);

            public static final float REAL_BACK_BUTTON_WIDTH = REAL_SAVE_BUTTON_WIDTH;
            public static final float REAL_BACK_BUTTON_HEIGHT = REAL_SAVE_BUTTON_HEIGHT;

            public static final float BACK_BUTTON_X = SAVING_FRAME_X + 0.2f * (REAL_SAVING_FRAME_WIDTH - REAL_SAVE_BUTTON_WIDTH);
            public static final float BACK_BUTTON_Y = SAVE_BUTTON_Y;

            public static final float REAL_TEXT_FIELD_WIDTH = REAL_SAVING_FRAME_WIDTH * 0.5f;
            public static final float REAL_TEXT_FIELD_HEIGHT = REAL_SAVING_FRAME_HEIGHT * 0.15f;
            public static final float TEXT_FIELD_X = SAVING_FRAME_X + REAL_SAVING_FRAME_WIDTH / 2 - REAL_TEXT_FIELD_WIDTH / 2;
            public static final float TEXT_FIELD_Y = SAVING_FRAME_Y + 0.35f * (REAL_SAVING_FRAME_HEIGHT - REAL_TEXT_FIELD_HEIGHT);
        }

        public static class PlayConstants {
            public static final float DEATH_SCREEN_WIDTH = 189 * SCALE;
            public static final float DEATH_SCREEN_HEIGHT = 236 * SCALE;
            public static final float DEATH_SCREEN_X = REAL_WINDOW_CENTER.x - DEATH_SCREEN_WIDTH / 2;
            public static final float DEATH_SCREEN_Y = REAL_WINDOW_CENTER.y - DEATH_SCREEN_HEIGHT / 2;

            public static final float VICTORY_SCREEN_WIDTH = 189 * SCALE;
            public static final float VICTORY_SCREEN_HEIGHT = 236 * SCALE;
            public static final float VICTORY_SCREEN_X = REAL_WINDOW_CENTER.x - DEATH_SCREEN_WIDTH / 2;
            public static final float VICTORY_SCREEN_Y = REAL_WINDOW_CENTER.y - DEATH_SCREEN_HEIGHT / 2;

            public static final float REAL_TEXT_FIELD_WIDTH = VICTORY_SCREEN_WIDTH - 50 * SCALE;
            public static final float REAL_TEXT_FIELD_HEIGHT = 30 * SCALE;
            public static final float TEXT_FIELD_X = VICTORY_SCREEN_X + VICTORY_SCREEN_WIDTH / 2 - REAL_TEXT_FIELD_WIDTH / 2;
            public static final float TEXT_FIELD_Y = VICTORY_SCREEN_Y + 0.55f * VICTORY_SCREEN_HEIGHT;

            public static final float REAL_MENU_BUTTON_WIDTH = BUTTON_WIDTH * 0.5f * SCALE;
            public static final float REAL_MENU_BUTTON_HEIGHT = BUTTON_HEIGHT * 0.5f * SCALE;
            public static final float MENU_BUTTON_X = DEATH_SCREEN_X + DEATH_SCREEN_WIDTH / 2 - REAL_MENU_BUTTON_WIDTH / 2;
            public static final float MENU_BUTTON_Y = DEATH_SCREEN_Y + 0.75f * (DEATH_SCREEN_HEIGHT - REAL_MENU_BUTTON_HEIGHT);

            public static final float REAL_SAVE_BUTTON_WIDTH = BUTTON_WIDTH * 0.5f * SCALE;
            public static final float REAL_SAVE_BUTTON_HEIGHT = BUTTON_HEIGHT * 0.5f * SCALE;
            public static final float SAVE_BUTTON_X = VICTORY_SCREEN_X + VICTORY_SCREEN_WIDTH / 2 - REAL_SAVE_BUTTON_WIDTH / 2;
            public static final float SAVE_BUTTON_Y = VICTORY_SCREEN_Y + 0.9f * (VICTORY_SCREEN_HEIGHT - REAL_SAVE_BUTTON_HEIGHT);

            public static final float HEALTH_BAR_IMAGE_X = 10 * SCALE;
            public static final float HEALTH_BAR_IMAGE_Y = 10 * SCALE;
            private static final float HEALTH_BAR_IMAGE_WIDTH = 384;
            private static final float HEALTH_BAR_IMAGE_HEIGHT = 96;
            public static final float REAL_HEALTH_BAR_IMAGE_WIDTH = HEALTH_BAR_IMAGE_WIDTH * SCALE * 0.5f;
            public static final float REAL_HEALTH_BAR_IMAGE_HEIGHT = HEALTH_BAR_IMAGE_HEIGHT * SCALE * 0.5f;

            public static final float HEALTH_BAR_X = HEALTH_BAR_IMAGE_X + 33 * SCALE * 0.5f;
            public static final float HEALTH_BAR_Y = HEALTH_BAR_IMAGE_Y + 33 * SCALE * 0.5f;
            public static final float HEALTH_BAR_WIDTH = REAL_HEALTH_BAR_IMAGE_WIDTH - 66 * SCALE * 0.5f;
            public static final float HEALTH_BAR_HEIGHT = REAL_HEALTH_BAR_IMAGE_HEIGHT - 66 * SCALE * 0.5f;

            private static final int COIN_ICON_WIDTH = 18;
            private static final int COIN_ICON_HEIGHT = 12;
            public static final float REAL_COIN_ICON_WIDTH = COIN_ICON_WIDTH * SCALE;
            public static final float REAL_COIN_ICON_HEIGHT = COIN_ICON_HEIGHT * SCALE;
            public static final float COIN_ICON_X = 3 * SCALE;
            public static final float COIN_ICON_Y = HEALTH_BAR_IMAGE_Y + REAL_HEALTH_BAR_IMAGE_HEIGHT + 20 * SCALE;

            private static final int KEY_ICON_WIDTH = 14;
            private static final int KEY_ICON_HEIGHT = 20;
            public static final float REAL_KEY_ICON_WIDTH = KEY_ICON_WIDTH * SCALE;
            public static final float REAL_KEY_ICON_HEIGHT = KEY_ICON_HEIGHT * SCALE;
            public static final float KEY_ICON_X = HEALTH_BAR_IMAGE_X;
            public static final float KEY_ICON_Y = COIN_ICON_Y + REAL_COIN_ICON_HEIGHT + 20 * SCALE;

            public static final int NUMBER_WIDTH = 31;
            public static final int NUMBER_HEIGHT = 44;
            public static final float REAL_NUMBER_WIDTH = NUMBER_WIDTH * SCALE * 0.2f;
            public static final float REAL_NUMBER_HEIGHT = NUMBER_HEIGHT * SCALE * 0.2f;
            public static final float NUMBER_X = COIN_ICON_X + REAL_COIN_ICON_WIDTH + 2 * SCALE;
            public static final float NUMBER_Y = COIN_ICON_Y;
            public static final float BETWEEN_NUMBERS = REAL_NUMBER_WIDTH + 2 * SCALE;
        }
    }

    public static class WindowConstants {
        public static final float SCALE = 1.75f;

        private static final int WINDOW_WIDTH = 878;
        private static final int WINDOW_HEIGHT = 494;
        public static final int REAL_WINDOW_WIDTH = (int)(WINDOW_WIDTH * SCALE);
        public static final int REAL_WINDOW_HEIGHT = (int)(WINDOW_HEIGHT * SCALE);
        public static final Point REAL_WINDOW_CENTER = new Point(REAL_WINDOW_WIDTH / 2, REAL_WINDOW_HEIGHT / 2);
    }

    public static class ObstacleConstants {
        private static final float STONE_SPRITE_WIDTH = 64;
        private static final float STONE_SPRITE_HEIGHT = 64;
        public static final float REAL_STONE_WIDTH = STONE_SPRITE_WIDTH * 1.5f * SCALE;
        public static final float REAL_STONE_HEIGHT = STONE_SPRITE_HEIGHT * 1.5f * SCALE;

        public static final float STONE_TOUCH_BOX_OFFSET_X = 20 * SCALE;
        public static final float STONE_TOUCH_BOX_OFFSET_Y = 23 * SCALE;
        public static final float STONE_TOUCH_BOX_WIDTH = 55 * SCALE;
        public static final float STONE_TOUCH_BOX_HEIGHT = 63 * SCALE;


        public static final int BARREL_SPRITE_WIDTH = 64;
        public static final int BARREL_SPRITE_HEIGHT = 64;
        public static final float REAL_BARREL_WIDTH = BARREL_SPRITE_WIDTH * 2f * SCALE;
        public static final float REAL_BARREL_HEIGHT = BARREL_SPRITE_HEIGHT * 2f * SCALE;

        public static final float BARREL_TOUCH_BOX_OFFSET_X = 48 * SCALE;
        public static final float BARREL_TOUCH_BOX_OFFSET_Y = 60 * SCALE;
        public static final float BARREL_TOUCH_BOX_WIDTH = 38 * SCALE;
        public static final float BARREL_TOUCH_BOX_HEIGHT = 34 * SCALE;

        public static final int BARREL_SPRITE_AMOUNT = 7;
    }

    public static class RoomConstants {
        private static final int WALL_THICKNESS = 40;
        private static final int DOOR_WIDTH = 73;
        public static final int REAL_WALL_THICKNESS = (int)(WALL_THICKNESS * SCALE);
        public static final int REAL_DOOR_WIDTH = (int)(DOOR_WIDTH * SCALE);

        private static final int NORTH_DOOR_X = REAL_WINDOW_CENTER.x - REAL_DOOR_WIDTH / 2;
        private static final int NORTH_DOOR_Y = 0;
        public static final Rectangle2D.Float NORTH_DOOR = new Rectangle2D.Float(NORTH_DOOR_X, NORTH_DOOR_Y, REAL_DOOR_WIDTH, REAL_WALL_THICKNESS);

        private static final int SOUTH_DOOR_X = REAL_WINDOW_CENTER.x - REAL_DOOR_WIDTH / 2;
        private static final int SOUTH_DOOR_Y = REAL_WINDOW_HEIGHT - REAL_WALL_THICKNESS;
        public static final Rectangle2D.Float SOUTH_DOOR = new Rectangle2D.Float(SOUTH_DOOR_X, SOUTH_DOOR_Y, REAL_DOOR_WIDTH, REAL_WALL_THICKNESS);

        private static final int WEST_DOOR_X = 0;
        private static final int WEST_DOOR_Y = REAL_WINDOW_CENTER.y - REAL_DOOR_WIDTH / 2;
        public static final Rectangle2D.Float WEST_DOOR = new Rectangle2D.Float(WEST_DOOR_X, WEST_DOOR_Y, REAL_WALL_THICKNESS, REAL_DOOR_WIDTH);

        private static final int EAST_DOOR_X = REAL_WINDOW_WIDTH - REAL_WALL_THICKNESS;
        private static final int EAST_DOOR_Y = REAL_WINDOW_CENTER.y - REAL_DOOR_WIDTH / 2;
        public static final Rectangle2D.Float EAST_DOOR = new Rectangle2D.Float(EAST_DOOR_X, EAST_DOOR_Y, REAL_WALL_THICKNESS, REAL_DOOR_WIDTH);

        public static final Rectangle2D.Float NORTH_WALL1 = new Rectangle2D.Float(0,0, NORTH_DOOR_X, REAL_WALL_THICKNESS);
        public static final Rectangle2D.Float NORTH_WALL2 = new Rectangle2D.Float(NORTH_WALL1.width + NORTH_DOOR.width, 0, NORTH_WALL1.width, REAL_WALL_THICKNESS);

        public static final Rectangle2D.Float SOUTH_WALL1 = new Rectangle2D.Float(0, SOUTH_DOOR_Y, NORTH_WALL1.width, REAL_WALL_THICKNESS);
        public static final Rectangle2D.Float SOUTH_WALL2 = new Rectangle2D.Float(NORTH_WALL2.x, SOUTH_DOOR_Y, NORTH_WALL1.width, REAL_WALL_THICKNESS);

        public static final Rectangle2D.Float WEST_WALL1 = new Rectangle2D.Float(0,0, REAL_WALL_THICKNESS, WEST_DOOR_Y);
        public static final Rectangle2D.Float WEST_WALL2 = new Rectangle2D.Float(0, WEST_WALL1.height + WEST_DOOR.height, REAL_WALL_THICKNESS, WEST_WALL1.height);

        public static final Rectangle2D.Float EAST_WALL1 = new Rectangle2D.Float(EAST_DOOR_X, 0, REAL_WALL_THICKNESS, EAST_DOOR_Y);
        public static final Rectangle2D.Float EAST_WALL2 = new Rectangle2D.Float(EAST_DOOR_X, WEST_WALL2.y, REAL_WALL_THICKNESS, WEST_WALL2.height);

        public static final int FINAL_DOOR_WIDTH = 49;
        public static final int FINAL_DOOR_HEIGHT = 38;

        public static final int SHOP_DOOR_WIDTH = 49;
        public static final int SHOP_DOOR_HEIGHT = 34;

        public static final int COMMON_DOOR_WIDTH = 49;
        public static final int COMMON_DOOR_HEIGHT = 33;

        public static final Point SHOP_ITEM_POINT_1 = new Point(REAL_WINDOW_CENTER);
        private static final int SHOP_ITEM_POINT_2_X = (int) (REAL_WINDOW_CENTER.x * 0.5);
        public static final Point SHOP_ITEM_POINT_2 = new Point(SHOP_ITEM_POINT_2_X, REAL_WINDOW_CENTER.y);
        private static final int SHOP_ITEM_POINT_3_X = (int) (REAL_WINDOW_CENTER.x * 1.5);
        public static final Point SHOP_ITEM_POINT_3 = new Point(SHOP_ITEM_POINT_3_X, REAL_WINDOW_CENTER.y);

        public static final int NUMBER_WIDTH = 31;
        public static final int NUMBER_HEIGHT = 44;
        public static final float REAL_NUMBER_WIDTH = NUMBER_WIDTH * SCALE * 0.2f;
        public static final float REAL_NUMBER_HEIGHT = NUMBER_HEIGHT * SCALE * 0.2f;
        public static final float NUMBER_OFFSET_Y = 5 * SCALE;
        public static final float BETWEEN_NUMBERS = REAL_NUMBER_WIDTH + 1 * SCALE;

        public static final int EXIT_HOLE_WIDTH = 194;
        public static final int EXIT_HOLE_HEIGHT =190;
        public static final float REAL_EXIT_HOLE_WIDTH = EXIT_HOLE_WIDTH * SCALE * 0.5f;
        public static final float REAL_EXIT_HOLE_HEIGHT = EXIT_HOLE_HEIGHT * SCALE * 0.5f;
        public static final float EXIT_HOLE_X = REAL_WINDOW_CENTER.x - REAL_EXIT_HOLE_WIDTH / 2;
        public static final float EXIT_HOLE_Y = REAL_WINDOW_CENTER.y - REAL_EXIT_HOLE_HEIGHT / 2;
        public static final int EXIT_HOLE_SPRITE_AMOUNT = 3;
    }

    public static class EnemyConstants {
        public static final float GO_UP_RANGE = 30 * SCALE;

        public static class SpearmanConstants {
            public static final float MAX_HEALTH = 50;
            public static final float SPEED = 0.8f * SCALE;
            public static final float DAMAGE = 5;
            public static final float ARMOR = 3;
            public static float VISION_RANGE = 200 * SCALE;
            public static float ATTACK_RANGE = 25 * SCALE;

            public static final float TOUCH_BOX_X_OFFSET = 40 * SCALE;
            public static final float TOUCH_BOX_Y_OFFSET = 110 * SCALE;
            public static final float TOUCH_BOX_WIDTH = 48 * SCALE;
            public static final float TOUCH_BOX_HEIGHT = 16 * SCALE;

            public static final float HIT_BOX_X_OFFSET = 45 * SCALE;
            public static final float HIT_BOX_Y_OFFSET = 63 * SCALE;
            public static final float HIT_BOX_WIDTH = 30 * SCALE;
            public static final float HIT_BOX_HEIGHT = 63 * SCALE;

            public static final float ATTACK_BOX_X_OFFSET = 75 * SCALE;
            public static final float ATTACK_BOX_Y_OFFSET = 90 * SCALE;
            public static final float ATTACK_BOX_WIDTH = 45 * SCALE;
            public static final float ATTACK_BOX_HEIGHT = 20 * SCALE;

            public static final int SPRITE_WIDTH = 128;
            public static final int SPRITE_HEIGHT = 128;
            public static final int REAL_SPRITE_WIDTH = (int)(SPRITE_WIDTH * SCALE);
            public static final int REAL_SPRITE_HEIGHT = (int)(SPRITE_HEIGHT * SCALE);

            public static int getIntByState(Constants.CreatureStates state) {
                return switch (state) {
                    case IDLE -> 0;
                    case RUN -> 2;
                    case ATTACK -> 4;
                    case HURT -> 8;
                    case DEAD -> 9;
                };
            }

            public static int getSpriteAmount(Constants.CreatureStates state) {
                return switch (state) {
                    case IDLE -> 7;
                    case RUN -> 6;
                    case DEAD -> 5;
                    case ATTACK -> 4;
                    case HURT -> 3;
                };
            }
        }

        public static class KnightConstants {
            public static final float MAX_HEALTH = 100;
            public static final float SPEED = 2f * SCALE;
            public static final float DAMAGE = 10;
            public static final float ARMOR = 5;
            public static float VISION_RANGE = 1000 * SCALE;
            public static float ATTACK_RANGE = 30 * SCALE;

            public static final float TOUCH_BOX_X_OFFSET = 40 * SCALE;
            public static final float TOUCH_BOX_Y_OFFSET = 110 * SCALE;
            public static final float TOUCH_BOX_WIDTH = 48 * SCALE;
            public static final float TOUCH_BOX_HEIGHT = 16 * SCALE;

            public static final float HIT_BOX_X_OFFSET = 50 * SCALE;
            public static final float HIT_BOX_Y_OFFSET = 63 * SCALE;
            public static final float HIT_BOX_WIDTH = 30 * SCALE;
            public static final float HIT_BOX_HEIGHT = 63 * SCALE;

            public static final float ATTACK_BOX_X_OFFSET = 80 * SCALE;
            public static final float ATTACK_BOX_Y_OFFSET = 80 * SCALE;
            public static final float ATTACK_BOX_WIDTH = 45 * SCALE;
            public static final float ATTACK_BOX_HEIGHT = 30 * SCALE;

            public static final int SPRITE_WIDTH = 128;
            public static final int SPRITE_HEIGHT = 128;
            public static final int REAL_SPRITE_WIDTH = (int)(SPRITE_WIDTH * SCALE);
            public static final int REAL_SPRITE_HEIGHT = (int)(SPRITE_HEIGHT * SCALE);

            public static int getIntByState(Constants.CreatureStates state) {
                return switch (state) {
                    case IDLE -> 0;
                    case RUN -> 2;
                    case ATTACK -> 4;
                    case HURT -> 8;
                    case DEAD -> 10;
                };
            }

            public static int getSpriteAmount(Constants.CreatureStates state) {
                return switch (state) {
                    case IDLE -> 4;
                    case RUN -> 7;
                    case DEAD -> 6;
                    case ATTACK -> 5;
                    case HURT -> 2;
                };
            }
        }
    }

    public static class PlayerConstants {
        public static final float START_MAX_HEALTH = 50;
        public static final float START_SPEED = 2 * SCALE;
        public static final float START_DAMAGE = 10;
        public static final float START_ARMOR = 0;
        public static final int MAX_COIN = 99;
        public static final int MAX_ARMOR = 10;
        public static final int MAX_DAMAGE = 30;
        public static final float MAX_SPEED = 5 * SCALE;
        public static final int MAX_HEALTH = 200;

        public static final float TOUCH_BOX_X_OFFSET = 40 * SCALE;
        public static final float TOUCH_BOX_Y_OFFSET = 110 * SCALE;
        public static final float TOUCH_BOX_WIDTH = 48 * SCALE;
        public static final float TOUCH_BOX_HEIGHT = 16 * SCALE;

        public static final float HIT_BOX_X_OFFSET = 45 * SCALE;
        public static final float HIT_BOX_Y_OFFSET = 68 * SCALE;
        public static final float HIT_BOX_WIDTH = 30 * SCALE;
        public static final float HIT_BOX_HEIGHT = 58 * SCALE;

        public static final float ATTACK_BOX_X_OFFSET = 80 * SCALE;
        public static final float ATTACK_BOX_Y_OFFSET = 85 * SCALE;
        public static final float ATTACK_BOX_WIDTH = 47 * SCALE;
        public static final float ATTACK_BOX_HEIGHT = 20 * SCALE;

        public static final float START_X = REAL_WINDOW_CENTER.x - TOUCH_BOX_WIDTH / 2;
        public static final float START_Y = REAL_WINDOW_CENTER.y - TOUCH_BOX_HEIGHT / 2;

        public static final int SPRITE_WIDTH = 128;
        public static final int SPRITE_HEIGHT = 128;
        public static final int REAL_SPRITE_WIDTH = (int)(SPRITE_WIDTH * SCALE);
        public static final int REAL_SPRITE_HEIGHT = (int)(SPRITE_HEIGHT * SCALE);

        public static int getIntByState(Constants.CreatureStates state) {
            return switch (state) {
                case IDLE -> 0;
                case RUN -> 2;
                case ATTACK -> 4;
                case HURT -> 8;
                case DEAD -> 9;
            };
        }

        public static int getSpriteAmount(Constants.CreatureStates state) {
            return switch (state) {
                case IDLE -> 7;
                case RUN -> 8;
                case ATTACK -> 5;
                case DEAD -> 4;
                case HURT -> 2;
            };
        }
    }
}

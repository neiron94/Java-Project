package my_utils;

import creatures.Player;
import levels.Level;
import levels.rooms.Room;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

import static my_utils.Constants.PlayerConstants.START_X;
import static my_utils.Constants.PlayerConstants.START_Y;
import static my_utils.Constants.UIConstants.SelectionConstants.*;

/**
 * Class for working with files.
 */
public class LoadSave {
    private static final MyLogger LOGGER = new MyLogger(LoadSave.class);
    // Paths to images
    public static final String PLAYER_ATLAS = "images/creatures/player/player_atlas.png";
    public static final String SKELETON_SPEARMAN_ATLAS = "images/creatures/enemies/spearman_atlas.png";
    public static final String KNIGHT_ATLAS = "images/creatures/enemies/knight_atlas.png";
    public static final String COMMON_ROOM = "images/rooms/common_room.png";
    public static final String SHOP_ROOM = "images/rooms/shop_room.png";
    public static final String FINAL_ROOM = "images/rooms/final_room.png";
    public static final String EXIT_HOLE = "images/rooms/exit_hole.png";
    public static final String COMMON_DOORS = "images/rooms/common_room_doors.png";
    public static final String FINAL_DOORS = "images/rooms/final_room_doors.png";
    public static final String FINAL_DOORS_LOCKED = "images/rooms/final_room_doors_locked.png";
    public static final String SHOP_DOORS = "images/rooms/shop_room_doors.png";
    public static final String STONE = "images/obstacles/stone.png";
    public static final String BARREL = "images/obstacles/barrel.png";
    public static final String MENU_BACKGROUND = "images/ui/menu_background.png";
    public static final String MENU_FRAME = "images/ui/menu_frame.png";
    public static final String SELECTION_FRAME = "images/ui/selection_frame.png";
    public static final String SELECTION_BACKGROUND = "images/ui/menu_background.png";
    public static final String BUTTONS = "images/ui/buttons.png";
    public static final String EDITOR_CHOOSING_FRAME = "images/ui/editor_choosing_frame.png";
    public static final String EDITOR_SAVING_FRAME = "images/ui/editor_choosing_frame.png";
    public static final String EDITOR_CHOOSING_BUTTONS = "images/ui/editor_choosing_buttons.png";
    public static final String DEATH_SCREEN_FRAME = "images/ui/death_screen_frame.png";
    public static final String VICTORY_SCREEN_FRAME = "images/ui/victory_screen_frame.png";
    public static final String ARMOR_BOOSTER = "images/items/boosters/armor_booster_animation.png";
    public static final String SPEED_BOOSTER = "images/items/boosters/speed_booster_animation.png";
    public static final String DAMAGE_BOOSTER = "images/items/boosters/damage_booster_animation.png";
    public static final String HEALTH_BOOSTER = "images/items/boosters/health_booster_animation.png";
    public static final String HEAL_POTION = "images/items/common/heal_potion_animation.png";
    public static final String KEY = "images/items/common/key_animation.png";
    public static final String COIN = "images/items/common/coin_animation.png";
    public static final String HEALTH_BAR = "images/ui/health_bar.png";
    public static final String NUMBERS = "images/ui/numbers.png";
    public static final String COIN_ICON = "images/ui/coin_icon.png";
    public static final String KEY_ICON = "images/ui/key_icon.png";

    /**
     * Loads image from file by filename.
     * @param filename name of file with image.
     * @return loaded image.
     * @throws IOException is thrown if image is not founded or image can't be read from file.
     */
    public static BufferedImage loadImageFromFile(String filename) throws IOException {
        BufferedImage image;
        try (InputStream is = LoadSave.class.getResourceAsStream("/" + filename)) {
            if (is == null) throw new FileNotFoundException("Sprite atlas is not founded");
            image = ImageIO.read(is);
        }
        catch (FileNotFoundException e) {
            LOGGER.fatal("Sprite atlas " + filename + " is not founded");
            throw e;
        }
        catch (IOException e) {
            LOGGER.fatal("Can't read image from file " + filename);
            throw e;
        }

        LOGGER.info("Sprite atlas from " + filename + " is read successfully");
        return image;
    }


    /**
     * Loads serialized level object from given file.
     * @param filename name of file with level object.
     * @return loaded level.
     * @throws IOException object can't be read from file.
     * @throws ClassNotFoundException class of the level object can't be found.
     */
    public static Level loadLevel(String filename) throws IOException, ClassNotFoundException {
        Level level;

        try (FileInputStream fileInput = new FileInputStream(LEVELS_FOLDER_PATH + "/" + filename)) {
            try (ObjectInputStream objectInput = new ObjectInputStream(fileInput)) {
                level = (Level) objectInput.readObject();
                level.setCurrentRoom(level.getStartRoom());
                for (Room room : level.getRooms())
                    room.assertNearbyRooms(level.getRoomsMatrix());
            }
        }
        catch (IOException e) {
            LOGGER.fatal("Can't read level from file " + filename);
            throw e;
        }
        catch(ClassNotFoundException e) {
            LOGGER.fatal("Can't find Level class");
            throw e;
        }

        LOGGER.info("Level from " + filename + " is read successfully");
        return level;
    }

    /**
     * Saves serialized level object to the file with given name.
     * @param level object to serialize.
     * @param name name of the level object.
     */
    public static void saveLevel(Level level, String name) {
        if (HelpMethods.countFiles(LEVELS_FOLDER) == 10) {
            LOGGER.error("To many levels are saved already, level is not saved.");
            return;
        }

        try (FileOutputStream fileOutput = new FileOutputStream(LEVELS_FOLDER_PATH + "/" + name + ".ser")) {
            try (ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput)) {
                objectOutput.writeObject(level);
            }
        }
        catch (IOException e) {
            LOGGER.error("Error while saving level " + name + " to the file. Level is not saved. Error message: " + e.getMessage() + ". Error stack trace: " + Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * Loads serialized player object from given file.
     * @param filename name of file with player object.
     * @return loaded player.
     * @throws IOException object can't be read from file.
     * @throws ClassNotFoundException class of the player object can't be found.
     */
    public static Player loadPlayer(String filename) throws IOException, ClassNotFoundException {
        Player player;

        try (FileInputStream fileInput = new FileInputStream(CHARACTERS_FOLDER_PATH + "/" + filename)) {
            try (ObjectInputStream objectInput = new ObjectInputStream(fileInput);) {
                player = (Player) objectInput.readObject();
                player.setPosition(START_X, START_Y);
            }
        }
        catch (IOException e) {
            LOGGER.fatal("Can't read player from file " + filename);
            throw e;
        }
        catch(ClassNotFoundException e) {
            LOGGER.fatal("Can't find Player class");
            throw e;
        }

        LOGGER.info("Player from " + filename + " is read successfully");
        return player;
    }

    /**
     * Saves serialized player object to the file with given name.
     * @param player object to serialize.
     * @param name name of the player object.
     */
    public static void savePlayer(Player player, String name) {
        if (HelpMethods.countFiles(CHARACTERS_FOLDER) == 9) {
            LOGGER.error("To many characters are saved already, character is not saved.");
            return;
        }

        try (FileOutputStream fileOutput = new FileOutputStream(CHARACTERS_FOLDER_PATH + "/" + name + ".ser")) {
            try (ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput)) {
                objectOutput.writeObject(player);
            }
        }
        catch (IOException e) {
            LOGGER.error("Error while saving player " + name + " to the file. Player is not saved. Error message: " + e.getMessage() + ". Error stack trace: " + Arrays.toString(e.getStackTrace()));
        }
    }
}

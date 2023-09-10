package levels.rooms;

import creatures.Enemy;
import creatures.EnemyManager;
import creatures.Player;
import gamestates.Editor;
import gamestates.Play;
import items.Item;
import levels.Level;
import my_utils.Constants;
import my_utils.HelpMethods;
import my_utils.Images;
import my_utils.MyLogger;
import obstacles.DestroyableObstacle;
import obstacles.Obstacle;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static my_utils.Constants.ItemTypes.*;
import static my_utils.Constants.RoomConstants.*;
import static my_utils.Constants.Directions.*;
import static my_utils.Constants.RoomTypes.FINAL;
import static my_utils.Constants.WindowConstants.*;

/**
 * This class is common for all types of the rooms in the game.
 */
public abstract class Room implements Serializable {
    private static final MyLogger LOGGER = new MyLogger(Room.class);
    // Enemies
    protected List<Enemy> enemies;
    protected int startEnemyCount;
    protected int currentEnemyCount;
    // Nearby rooms
    protected transient Room westRoom = null;
    protected transient Room eastRoom = null;
    protected transient Room northRoom = null;
    protected transient Room southRoom = null;
    // Obstacles
    protected List<Obstacle> obstacles;
    protected List<DestroyableObstacle> destroyableObstacles;
    protected List<Rectangle2D.Float> touchBoxes;
    // Items
    protected volatile List<Item> items;
    // Room characteristics
    protected boolean isClear;
    protected boolean isClosed;
    protected Point posInMatrix;
    protected Constants.RoomTypes type;

    protected static List<Constants.ItemTypes> dropPool;
    static {
        dropPool = new ArrayList<>();
        dropPool.add(ARMOR_BOOSTER);
        dropPool.add(HEALTH_BOOSTER);
        dropPool.add(DAMAGE_BOOSTER);
        dropPool.add(SPEED_BOOSTER);
    }

    public Room(int posMatrixX, int posMatrixY, boolean isClosed, boolean isClear, Constants.RoomTypes type) {
        // Room characteristics
        this.isClosed = isClosed;
        this.isClear = isClear;
        this.type = type;
        // Enemies
        enemies = new ArrayList<>();
        startEnemyCount = currentEnemyCount = 0;
        // Items
        items = new ArrayList<>();
        // Obstacles
        obstacles = new ArrayList<>();
        destroyableObstacles = new ArrayList<>();
        touchBoxes = new ArrayList<>();
        touchBoxes.add(NORTH_WALL1);
        touchBoxes.add(NORTH_WALL2);
        touchBoxes.add(SOUTH_WALL1);
        touchBoxes.add(SOUTH_WALL2);
        touchBoxes.add(WEST_WALL1);
        touchBoxes.add(WEST_WALL2);
        touchBoxes.add(EAST_WALL1);
        touchBoxes.add(EAST_WALL2);
        touchBoxes.add(NORTH_DOOR);
        touchBoxes.add(SOUTH_DOOR);
        touchBoxes.add(WEST_DOOR);
        touchBoxes.add(EAST_DOOR);
        // Level matrix
        posInMatrix = new Point(posMatrixX, posMatrixY);
        Editor.getLevel().getRoomsMatrix()[posInMatrix.x][posInMatrix.y] = this;
        Editor.getLevel().getRooms().add(this);
        assertNearbyRooms(Editor.getLevel().getRoomsMatrix());

        LOGGER.info("Room on position [" + posMatrixX + ", " + posMatrixY + "] with type " + type + " is created successfully");
    }

    /*-------------------------------Methods for connecting rooms------------------------------*/

    /**
     * Checks all nearby rooms in the roomMatrix and connects to this room.
     * @param roomMatrix matrix of rooms from which nearby rooms will be checked.
     */
    public  void assertNearbyRooms(Room[][] roomMatrix) {
        // Assert west
        Room room = null;
        if (posInMatrix.x != 0)
            room = roomMatrix[posInMatrix.x - 1][posInMatrix.y];

        if (room != null)
            connectRoom(room, WEST);

        // Assert east
        room = null;
        if (posInMatrix.x != Constants.REAL_ROOM_MATRIX_SIZE - 1)
            room = roomMatrix[posInMatrix.x + 1][posInMatrix.y];

        if (room != null)
            connectRoom(room, EAST);

        // Assert north
        room = null;
        if (posInMatrix.y != 0)
            room = roomMatrix[posInMatrix.x][posInMatrix.y - 1];

        if (room != null)
            connectRoom(room, NORTH);

        // Assert south
        room = null;
        if (posInMatrix.y != Constants.REAL_ROOM_MATRIX_SIZE - 1)
            room = roomMatrix[posInMatrix.x][posInMatrix.y + 1];

        if (room != null)
            connectRoom(room, SOUTH);
    }

    private void connectRoom(Room room, Constants.Directions direction) {
        switch (direction) {
            case WEST -> {
                westRoom = room;
                westRoom.eastRoom = this;

                if (!westRoom.isClosed)                     touchBoxes.remove(WEST_DOOR);
                else if (!touchBoxes.contains(WEST_DOOR))   touchBoxes.add(WEST_DOOR);

                if (!isClosed)                                      westRoom.touchBoxes.remove(EAST_DOOR);
                else if (!westRoom.touchBoxes.contains(EAST_DOOR))  westRoom.touchBoxes.add(EAST_DOOR);
            }
            case EAST -> {
                eastRoom = room;
                eastRoom.westRoom = this;

                if (!eastRoom.isClosed)                     touchBoxes.remove(EAST_DOOR);
                else if (!touchBoxes.contains(EAST_DOOR))   touchBoxes.add(EAST_DOOR);

                if (!isClosed)                                      eastRoom.touchBoxes.remove(WEST_DOOR);
                else if (!eastRoom.touchBoxes.contains(WEST_DOOR))  eastRoom.touchBoxes.add(WEST_DOOR);
            }
            case NORTH -> {
                northRoom = room;
                northRoom.southRoom = this;

                if (!northRoom.isClosed)                    touchBoxes.remove(NORTH_DOOR);
                else if (!touchBoxes.contains(NORTH_DOOR))  touchBoxes.add(NORTH_DOOR);

                if (!isClosed)                                          northRoom.touchBoxes.remove(SOUTH_DOOR);
                else if (!northRoom.touchBoxes.contains(SOUTH_DOOR))    northRoom.touchBoxes.add(SOUTH_DOOR);
            }
            case SOUTH -> {
                southRoom = room;
                southRoom.northRoom = this;

                if (!southRoom.isClosed)                    touchBoxes.remove(SOUTH_DOOR);
                else if (!touchBoxes.contains(SOUTH_DOOR))  touchBoxes.add(SOUTH_DOOR);

                if (!isClosed)                                          southRoom.touchBoxes.remove(NORTH_DOOR);
                else if (!southRoom.touchBoxes.contains(NORTH_DOOR))    southRoom.touchBoxes.add(NORTH_DOOR);
            }
        }
    }

    /*--------------------------------------Methods for rendering--------------------------------------*/

    /**
     * Draws room, obstacles, items and enemies.
     * @param g the object for drawing.
     */
    public void render(Graphics g) {
        renderRoomBackground(g);
        // Draw doors
        drawDoor(g, eastRoom, EAST);
        drawDoor(g, westRoom, WEST);
        drawDoor(g, southRoom, SOUTH);
        drawDoor(g, northRoom, NORTH);
        // Draw obstacles
        for (Obstacle obstacle : obstacles)
            obstacle.render(g);
        // Draw items
        drawItems(g);
        // Draw enemies
        EnemyManager.getInstance().render(g);
    }

    protected abstract void renderRoomBackground(Graphics g);

    protected void drawItems(Graphics g) {
        for (Item item : items) {
            item.render(g);
            if (item.isShopItem()) {
                int numberX = (int) (item.getX() + item.getWidth() / 2 - REAL_NUMBER_WIDTH);
                int numberY = (int) (item.getY() + item.getHeight() + NUMBER_OFFSET_Y);
                HelpMethods.drawNumber(g, item.getCost(), numberX, numberY, REAL_NUMBER_WIDTH, REAL_NUMBER_HEIGHT, BETWEEN_NUMBERS);
            }
        }
    }

    private void drawDoor(Graphics g, Room room, Constants.Directions direction) {
        if (room == null)
            return;

        // Type of the door
        BufferedImage[] doorsImage = switch (room.type) {
            case COMMON -> Images.getCommonDoors();
            case SHOP -> Images.getShopDoors();
            case FINAL -> room.isClosed() ? Images.getFinalDoorsLocked() : Images.getFinalDoors();
        };

        // Place to draw the door
        Rectangle2D.Float doorPlace = switch (direction) {
            case EAST -> EAST_DOOR;
            case WEST -> WEST_DOOR;
            case NORTH -> NORTH_DOOR;
            case SOUTH -> SOUTH_DOOR;
        };

        // Draw the door
        g.drawImage(doorsImage[getDoorIndexByDir(direction)], (int)doorPlace.x, (int)doorPlace.y,
                (int)doorPlace.width, (int)doorPlace.height, null);
    }

    /*-------------------------------------Methods for updating-------------------------------------*/

    /**
     * Updates room, obstacles, items and enemies.
     */
    public void update() {
        checkOpenFinalRoom();
        if (currentEnemyCount == 0 && !isClear) {
            isClear = true;
            if (type == FINAL)
                FinalRoom.openExit();
            else if (startEnemyCount != 0)
                dropLoot();
        }
        // Update items
        for (Item item : items)
            item.update();
        deleteItemsIfTaken();
        // Update obstacles
        for (Obstacle obstacle : destroyableObstacles)
            obstacle.update();
        // Update enemies
        EnemyManager.getInstance().update();
    }

    private void checkOpenFinalRoom() {
        Player player = Play.getPlayer();
        if (!player.isHasKey())
            return;

        Rectangle2D.Float touchBox = new Rectangle2D.Float(player.getTouchBox().x - 10, player.getTouchBox().y - 10,
                player.getTouchBox().width + 20, player.getTouchBox().height + 20);

        if (westRoom != null && westRoom.type == FINAL && touchBox.intersects(WEST_DOOR))     westRoom.open(WEST);
        if (eastRoom != null && eastRoom.type == FINAL && touchBox.intersects(EAST_DOOR))     eastRoom.open(EAST);
        if (northRoom != null && northRoom.type == FINAL && touchBox.intersects(NORTH_DOOR))  northRoom.open(NORTH);
        if (southRoom != null && southRoom.type == FINAL && touchBox.intersects(SOUTH_DOOR))  southRoom.open(SOUTH);
    }

    private void deleteItemsIfTaken() {
        List<Item> copyItems = new ArrayList<>(items);
        for (Item item : copyItems)
            if (item.isTaken())
                items.remove(item);
    }

    /*-----------------------------Methods for creating/deleting rooms-----------------------------*/

    /**
     * Deletes room.
     */
    public void deleteRoom() {
        if (this == Editor.getLevel().getStartRoom()) {
            LOGGER.warn("Trying to delete start room.");
            return;
        }

        if (westRoom != null) westRoom.eastRoom = null;
        if (eastRoom != null) eastRoom.westRoom = null;
        if (southRoom != null) southRoom.northRoom = null;
        if (northRoom != null) northRoom.southRoom = null;
        Editor.getLevel().getRoomsMatrix()[posInMatrix.x][posInMatrix.y] = null;
        Editor.getLevel().getRooms().remove(this);
    }

    /**
     * Creates new nearby room with given type on the given direction.
     * @param direction direction of the new room.
     * @param roomType type of the new room.
     */
    public void createRoom(Constants.Directions direction, Constants.RoomTypes roomType) {
        Level level = Editor.getLevel();
        if (roomType == FINAL && level.isFinalRoomAlreadyExist()) {
            LOGGER.warn("Trying to create final room when final room is already exists.");
            return;
        }

        Point newRoomPos = countRoomPos(direction);
        if (!assertRoomPos(newRoomPos)) {
            LOGGER.warn("Trying to create new room on position [" + posInMatrix.x + ", " + posInMatrix.y + "].");
            return;
        }

        switch (roomType) {
            case COMMON -> new CommonRoom(newRoomPos.x, newRoomPos.y);
            case SHOP -> new ShopRoom(newRoomPos.x, newRoomPos.y);
            case FINAL -> new FinalRoom(newRoomPos.x, newRoomPos.y);
        }
    }

    private Point countRoomPos(Constants.Directions direction) {
        return switch (direction) {
            case WEST -> new Point(posInMatrix.x - 1, posInMatrix.y);
            case EAST -> new Point(posInMatrix.x + 1, posInMatrix.y);
            case NORTH -> new Point(posInMatrix.x, posInMatrix.y - 1);
            case SOUTH -> new Point(posInMatrix.x, posInMatrix.y + 1);
        };
    }

    private boolean assertRoomPos(Point posInMatrix) {
        return posInMatrix.x >= 0 && posInMatrix.x < Constants.REAL_ROOM_MATRIX_SIZE && posInMatrix.y >= 0 && posInMatrix.y < Constants.REAL_ROOM_MATRIX_SIZE;
    }

    /**
     * Adds enemy to the room.
     * @param enemy enemy to add.
     */
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
        startEnemyCount++;
        currentEnemyCount++;
    }

    /**
     * Adds obstacle to the room.
     * @param obstacle obstacle to add.
     */
    public void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
        touchBoxes.add(obstacle.getTouchBox());
        if (obstacle instanceof DestroyableObstacle)
            destroyableObstacles.add((DestroyableObstacle) obstacle);
    }

    /**
     * Deletes enemy from the room.
     * @param enemy enemy to delete.
     */
    public void deleteEnemy(Enemy enemy) {
        enemies.remove(enemy);
        startEnemyCount--;
        currentEnemyCount--;
    }

    /**
     * Deletes obstacle from the room.
     * @param obstacle obstacle to delete.
     */
    public void deleteObstacle(Obstacle obstacle) {
        obstacles.remove(obstacle);
        touchBoxes.remove(obstacle.getTouchBox());
        if (obstacle instanceof DestroyableObstacle)
            destroyableObstacles.remove((DestroyableObstacle) obstacle);
    }

    /*-----------------------------Other methods for game logic-----------------------------*/

    /**
     * Checks if player hit any destroyable objet.
     * @param attackBox player's attack box.
     */
    public void checkPlayerHit(Rectangle2D.Float attackBox) {
        for (DestroyableObstacle obstacle : destroyableObstacles) {
            if (attackBox.intersects(obstacle.getTouchBox())) {
                obstacle.destroy();
                touchBoxes.remove(obstacle.getTouchBox());
            }
        }
    }

    public void resetOneEnemy() { currentEnemyCount++; }

    public void killEnemy() { currentEnemyCount--; }

    public void addItem(Item item) { items.add(item); }

    private void dropLoot() {
        int dropsCount = dropPool.size();

        Random random = new Random();
        int randInt = random.nextInt(100);
        float step = (float) 100 / dropsCount;

        float i = 0, j = step;
        for (Constants.ItemTypes type : dropPool) {
            if (randInt >= i && randInt <= j)
                HelpMethods.generateItemByType(type, REAL_WINDOW_CENTER);

            i += step;
            j += step;
        }
    }

    private void open(Constants.Directions direction) {
        isClosed = false;
        switch (direction) {
            case WEST -> eastRoom.getTouchBoxes().remove(WEST_DOOR);
            case EAST -> westRoom.getTouchBoxes().remove(EAST_DOOR);
            case NORTH -> southRoom.getTouchBoxes().remove(NORTH_DOOR);
            case SOUTH -> northRoom.getTouchBoxes().remove(SOUTH_DOOR);
        }
    }

    /*---------------------------------------Getters and setters---------------------------------------*/

    public List<Enemy> getEnemies() { return enemies; }
    public Room getWestRoom() { return westRoom; }
    public Room getEastRoom() { return eastRoom; }
    public Room getNorthRoom() { return northRoom; }
    public Room getSouthRoom() { return southRoom; }
    public boolean isClosed() { return isClosed; }
    public List<Rectangle2D.Float> getTouchBoxes() { return touchBoxes; }
    public boolean isClear() { return isClear; }
    public Point getPosInMatrix() { return posInMatrix; }
    public Constants.RoomTypes getType() { return type; }
    public List<Item> getItems() { return items; }
    public int getCurrentEnemyCount() { return currentEnemyCount; }
    public List<Obstacle> getObstacles() { return obstacles; }
}

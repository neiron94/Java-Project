package levels;

import creatures.EnemyManager;
import levels.rooms.Room;
import my_utils.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Level of the game. Consists of the rooms. Can be saved in the file or loaded from the file.
 */
public class Level implements Serializable {
    private transient boolean finalRoomAlreadyExist;
    private final List<Room> rooms;
    private final Room[][] roomsMatrix;
    private Room startRoom;
    private transient Room currentRoom;

    public Level() {
        finalRoomAlreadyExist = false;
        rooms = new ArrayList<>();
        this.roomsMatrix = new Room[Constants.REAL_ROOM_MATRIX_SIZE][Constants.REAL_ROOM_MATRIX_SIZE];
    }

    /**
     * Changes current room, also resets all the enemies, if room wasn't cleared before.
     * @param nextRoomDir direction of the new current room.
     */
    public void goToNextRoom(Constants.Directions nextRoomDir) {
        switch (nextRoomDir) {
            case SOUTH -> currentRoom = currentRoom.getSouthRoom();
            case NORTH -> currentRoom = currentRoom.getNorthRoom();
            case WEST -> currentRoom = currentRoom.getWestRoom();
            case EAST -> currentRoom = currentRoom.getEastRoom();
        }
        if (!currentRoom.isClear())
            EnemyManager.getInstance().resetEnemies();
    }

    /**
     * Decides if it's the time to drop the key.
     * @return true if all the enemies on the level were killed, false otherwise.
     */
    public boolean isKeyDrop() {
        // Key can't be dropped in the final room
        if (currentRoom.getType() == Constants.RoomTypes.FINAL)
            return false;

        for (Room room : rooms)
            if (room.getType() != Constants.RoomTypes.FINAL)    // the final room isn't counted
                if (room.getCurrentEnemyCount() != 0)
                    return false;
        return true;
    }

    public Room getStartRoom() { return startRoom; }
    public List<Room> getRooms() { return rooms; }
    public Room[][] getRoomsMatrix() { return roomsMatrix; }
    public Room getCurrentRoom() { return currentRoom; }
    public boolean isFinalRoomAlreadyExist() { return finalRoomAlreadyExist; }

    public void setStartRoom(Room startRoom) { this.startRoom = startRoom; }
    public void setCurrentRoom(Room currentRoom) { this.currentRoom = currentRoom; }
    public void setFinalRoomAlreadyExist(boolean finalRoomAlreadyExist) { this.finalRoomAlreadyExist = finalRoomAlreadyExist; }
}

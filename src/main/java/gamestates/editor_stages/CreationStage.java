package gamestates.editor_stages;

import creatures.Enemy;
import creatures.Knight;
import creatures.Spearman;
import gamestates.Editor;
import gamestates.GameState;
import levels.Level;
import levels.rooms.CommonRoom;
import levels.rooms.FinalRoom;
import levels.rooms.Room;
import levels.rooms.ShopRoom;
import main.Game;
import my_utils.Constants;
import obstacles.Barrel;
import obstacles.Obstacle;
import obstacles.Stone;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import static my_utils.Constants.RoomConstants.REAL_WALL_THICKNESS;
import static my_utils.Constants.StatesEnum.*;
import static my_utils.Constants.WindowConstants.*;
import static my_utils.Constants.EditStages.*;
import static my_utils.Constants.EnemyConstants.*;
import static my_utils.Constants.ObstacleConstants.*;
import static my_utils.Constants.Directions.*;
import static my_utils.Constants.RoomTypes.*;

/**
 * Singleton class which represents the "Creation stage" of the "Editor mode", where rooms/enemies/obstacles can be created/deleted.
 */
public class CreationStage implements GameState {
    private static CreationStage stageInstance = null;

    private static Constants.Entities chosenEntity = null;

    private CreationStage() {}

    public static CreationStage getInstance() {
        if (stageInstance == null)
            stageInstance = new CreationStage();

        return stageInstance;
    }

    /*---------------------------------Methods for working with input---------------------------------*/

    /**
     * Creates/deletes enemy/obstacle.
     * @param e current mouse event.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // Delete enemy
        List<Enemy> enemiesCopy = new ArrayList<>(Editor.getLevel().getCurrentRoom().getEnemies());
        for (Enemy enemy : enemiesCopy) {
            if (enemy.getTouchBox().contains(new Point(e.getX(), e.getY()))) {
                Editor.getLevel().getCurrentRoom().deleteEnemy(enemy);
                chosenEntity = null;
            }
        }
        // Delete obstacle
        List<Obstacle> obstaclesCopy = new ArrayList<>(Editor.getLevel().getCurrentRoom().getObstacles());
        for (Obstacle obstacle : obstaclesCopy) {
            if (obstacle.getTouchBox().contains(new Point(e.getX(), e.getY()))) {
                Editor.getLevel().getCurrentRoom().deleteObstacle(obstacle);
                chosenEntity = null;
            }
        }
        // Create enemy/obstacle
        if (chosenEntity != null)
            createEntity(e);
    }

    /**
     * Changes stages, creates/deletes rooms, changes room type.
     * @param e current mouse event.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        Level level = Editor.getLevel();
        Room currentRoom = level.getCurrentRoom();

        // Go back to menu
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Editor.setFirstUpdate(true);
            Game.state = MENU;
        }
        // Create new room or go to it, if it's already exists
        if (e.getKeyCode() == KeyEvent.VK_D) {
            if (currentRoom.getEastRoom() != null)
                level.setCurrentRoom(currentRoom.getEastRoom());
            else
                currentRoom.createRoom(EAST, COMMON);
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            if (currentRoom.getSouthRoom() != null)
                level.setCurrentRoom(currentRoom.getSouthRoom());
            else
                currentRoom.createRoom(SOUTH, COMMON);
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            if (currentRoom.getWestRoom() != null)
                level.setCurrentRoom(currentRoom.getWestRoom());
            else
                currentRoom.createRoom(WEST, COMMON);
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            if (currentRoom.getNorthRoom() != null)
                level.setCurrentRoom(currentRoom.getNorthRoom());
            else
                currentRoom.createRoom(NORTH, COMMON);
        }
        // Delete rooms
        if (e.getKeyCode() == KeyEvent.VK_UP && currentRoom.getNorthRoom() != null)
            currentRoom.getNorthRoom().deleteRoom();
        if (e.getKeyCode() == KeyEvent.VK_DOWN && currentRoom.getSouthRoom() != null)
            currentRoom.getSouthRoom().deleteRoom();
        if (e.getKeyCode() == KeyEvent.VK_LEFT && currentRoom.getWestRoom() != null)
            currentRoom.getWestRoom().deleteRoom();
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && currentRoom.getEastRoom() != null)
            currentRoom.getEastRoom().deleteRoom();
        // Go to choosing stage
        if (e.getKeyCode() == KeyEvent.VK_SPACE && currentRoom.getType() != SHOP && currentRoom != level.getStartRoom())
            Editor.setStage(CHOOSING);
        // Go to saving stage
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            Game.getGamePanel().add(SavingStage.getTextField());
            Editor.setStage(SAVING);
        }
        // Change room type
        if (currentRoom != level.getStartRoom()) {
            // Common
            if (e.getKeyCode() == KeyEvent.VK_1) {
                Point pos = currentRoom.getPosInMatrix();
                currentRoom.deleteRoom();
                level.setCurrentRoom(new CommonRoom(pos.x, pos.y));
            }
            // Shop
            if (e.getKeyCode() == KeyEvent.VK_2) {
                Point pos = currentRoom.getPosInMatrix();
                currentRoom.deleteRoom();
                ShopRoom newShopRoom = new ShopRoom(pos.x, pos.y);
                level.setCurrentRoom(newShopRoom);
                newShopRoom.addShopItems();
            }
            // Final
            if (e.getKeyCode() == KeyEvent.VK_3 && !level.isFinalRoomAlreadyExist()) {
                Point pos = currentRoom.getPosInMatrix();
                currentRoom.deleteRoom();
                level.setCurrentRoom(new FinalRoom(pos.x, pos.y));
            }
        }
    }

    private void createEntity(MouseEvent e) {
        Room currentRoom = Editor.getLevel().getCurrentRoom();

        switch (chosenEntity) {
            case SPEARMAN -> {
                if (isInRoom(new Point(e.getX(), e.getY()), SpearmanConstants.TOUCH_BOX_WIDTH, SpearmanConstants.TOUCH_BOX_HEIGHT)) {
                    float x = e.getX() - SpearmanConstants.TOUCH_BOX_X_OFFSET - SpearmanConstants.TOUCH_BOX_WIDTH / 2;
                    float y = e.getY() - SpearmanConstants.TOUCH_BOX_Y_OFFSET - SpearmanConstants.TOUCH_BOX_HEIGHT / 2;

                    currentRoom.addEnemy(new Spearman(x, y));
                }
            }
            case KNIGHT -> {
                if (isInRoom(new Point(e.getX(), e.getY()), KnightConstants.TOUCH_BOX_WIDTH, KnightConstants.TOUCH_BOX_HEIGHT)) {
                    float x = e.getX() - KnightConstants.TOUCH_BOX_X_OFFSET - KnightConstants.TOUCH_BOX_WIDTH / 2;
                    float y = e.getY() - KnightConstants.TOUCH_BOX_Y_OFFSET - KnightConstants.TOUCH_BOX_HEIGHT / 2;

                    currentRoom.addEnemy(new Knight(x, y));
                }
            }
            case BARREL -> {
                if (isInRoom(new Point(e.getX(), e.getY()), BARREL_TOUCH_BOX_WIDTH, BARREL_TOUCH_BOX_HEIGHT)) {
                    float x = e.getX() - BARREL_TOUCH_BOX_OFFSET_X - BARREL_TOUCH_BOX_WIDTH / 2;
                    float y = e.getY() - BARREL_TOUCH_BOX_OFFSET_Y - BARREL_TOUCH_BOX_HEIGHT / 2;

                    currentRoom.addObstacle(new Barrel(x, y));
                }
            }
            case STONE -> {
                if (isInRoom(new Point(e.getX(), e.getY()), STONE_TOUCH_BOX_WIDTH,
                        STONE_TOUCH_BOX_HEIGHT)) {
                    float x = e.getX() - STONE_TOUCH_BOX_OFFSET_X - STONE_TOUCH_BOX_WIDTH / 2;
                    float y = e.getY() - STONE_TOUCH_BOX_OFFSET_Y - STONE_TOUCH_BOX_HEIGHT / 2;

                    currentRoom.addObstacle(new Stone(x, y));
                }
            }
        }
    }

    private boolean isInRoom(Point point, float touchBoxWidth, float touchBoxHeight) {
        Rectangle2D.Float room = new Rectangle2D.Float(REAL_WALL_THICKNESS + touchBoxWidth / 2, REAL_WALL_THICKNESS + touchBoxHeight / 2,
                REAL_WINDOW_WIDTH - 2 * REAL_WALL_THICKNESS - touchBoxWidth, REAL_WINDOW_HEIGHT - 2 * REAL_WALL_THICKNESS - touchBoxHeight);
        return room.contains(point);
    }

    /*---------------------------------------Getters and setters---------------------------------------*/

    public static void setChosenEntity(Constants.Entities chosenEntity) { CreationStage.chosenEntity = chosenEntity; }
    public static Constants.Entities getChosenEntity() { return chosenEntity; }

    /*---------------------------------------Not used methods---------------------------------------*/

    @Override
    public void render(Graphics g) {}
    @Override
    public void update() {}
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {}
}

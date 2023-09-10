package creatures;

import gamestates.Editor;
import gamestates.Play;
import levels.rooms.Room;
import main.Game;
import my_utils.Constants;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static my_utils.Constants.CreatureStates.*;
import static my_utils.Constants.StatesEnum.*;

/**
 * Singleton class, which controls all enemies in the room.
 */
public class EnemyManager implements Serializable {
    private static EnemyManager instance = null;

    private EnemyManager() {}

    public static EnemyManager getInstance() {
        if (instance == null)
            instance = new EnemyManager();

        return instance;
    }

    /**
     * Draws all enemies in the room.
     * @param g object for drawing.
     */
    public void render(Graphics g) {
        Room room = Game.state == EDITOR ? Editor.getLevel().getCurrentRoom() : Play.getLevel().getCurrentRoom();

        for (Enemy enemy : room.getEnemies()) {
            if (enemy.isAlive)
                enemy.render(g);
        }
    }

    /**
     * Updates all enemies in the room.
     */
    public void update() {
        Room room = Play.getLevel().getCurrentRoom();

        // Enemies can't go throw doors
        List<Rectangle2D.Float> obstacles = new ArrayList<>(room.getTouchBoxes());
        obstacles.add(Constants.RoomConstants.EAST_DOOR);
        obstacles.add(Constants.RoomConstants.WEST_DOOR);
        obstacles.add(Constants.RoomConstants.SOUTH_DOOR);
        obstacles.add(Constants.RoomConstants.NORTH_DOOR);

        for (Enemy enemy : room.getEnemies())
            if (enemy.isAlive)
                enemy.update(obstacles);
    }

    /**
     * Checks if player hit any enemy.
     * @param attackBox player's attack box.
     * @param damage player's damage.
     */
    public void checkPlayerHit(Rectangle2D.Float attackBox, int damage) {
        Room room = Play.getLevel().getCurrentRoom();

        for (Enemy enemy : room.getEnemies())
            if (enemy.isAlive)
                if (enemy.state != DEAD && enemy.hitBox.intersects(attackBox))
                    enemy.hurt(damage);

    }

    /**
     * Resets all the enemies in the room.
     */
    public void resetEnemies() {
        Room room = Play.getLevel().getCurrentRoom();

        for (Enemy enemy : room.getEnemies()) {
            if (!enemy.isAlive) {
                enemy.isAlive = true;
                room.resetOneEnemy();
            }
            enemy.setStartPosition();
            enemy.setCurrentHealth(enemy.getMaxHealth());
            enemy.state = IDLE;
        }
    }
}

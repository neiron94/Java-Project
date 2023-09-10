package obstacles;

import my_utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static my_utils.Constants.ItemTypes.*;

/**
 * This class is common for all destroyable obstacles, which can appear in the room.
 */
public abstract class DestroyableObstacle extends Obstacle {
    protected boolean isDestroyed = false;
    protected boolean isAnimationOn = false;

    protected int animIndex = 0, animTick = 0, animSpeed = Constants.NORM_ANIM_SPEED;

    protected static List<Constants.ItemTypes> dropPool;
    static {
        dropPool = new ArrayList<>();
        dropPool.add(COIN);
        dropPool.add(HEAL_POTION);
    }

    public DestroyableObstacle(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    /**
     * Starts obstacle destroy animation.
     */
    public void destroy() {
        isAnimationOn = true;
    }
}

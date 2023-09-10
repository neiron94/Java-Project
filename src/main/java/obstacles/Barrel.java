package obstacles;

import main.Game;
import my_utils.Constants;
import my_utils.HelpMethods;
import my_utils.Images;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import static my_utils.Constants.ObstacleConstants.*;
import static my_utils.Constants.StatesEnum.EDITOR;

/**
 * Barrel. Can be destroyed by attacking it, also drops item.
 */
public class Barrel extends DestroyableObstacle {
    protected Point touchBoxCenter;

    public Barrel(float x, float y) {
        super(x, y, REAL_BARREL_WIDTH, REAL_BARREL_HEIGHT);
    }

    /**
     * Animates barrel, if destroy animation is started.
     * @param g the object for drawing.
     */
    public void render(Graphics g) {
        if (!isDestroyed) {
            g.drawImage(Images.getBarrelAnimation()[animIndex], (int)x, (int)y, (int)width, (int)height, null);
            if (Game.state == EDITOR)
                g.drawRect((int)touchBox.x, (int)touchBox.y, (int)touchBox.width, (int)touchBox.height);
        }
    }

    /**
     * Updates animation.
     */
    public void update() {
        if (!isDestroyed && isAnimationOn)
            updateAnimationTick();
    }

    @Override
    protected void initTouchBox() {
        touchBox = new Rectangle2D.Float((int)(x + BARREL_TOUCH_BOX_OFFSET_X), (int)(y + BARREL_TOUCH_BOX_OFFSET_Y),
                (int)BARREL_TOUCH_BOX_WIDTH, (int)BARREL_TOUCH_BOX_HEIGHT);
        touchBoxCenter = new Point((int) (touchBox.x + touchBox.width / 2), (int) (touchBox.y + touchBox.height / 2));
    }

    private void updateAnimationTick() {
        animTick++;
        if (animTick >= animSpeed) {
            animTick = 0;
            animIndex++;
            if (animIndex >= BARREL_SPRITE_AMOUNT) {
                animIndex = 0;
                // If animation ended, object is destroyed and animation is off
                isDestroyed = true;
                isAnimationOn = false;
                dropLoot();
            }
        }
    }

    private void dropLoot() {
        int dropsCount = dropPool.size();

        Random random = new Random();
        int randInt = random.nextInt(100);
        float step = (float) 100 / dropsCount;

        float i = 0, j = step;
        for (Constants.ItemTypes type : dropPool) {
            if (randInt >= i && randInt <= j)
                HelpMethods.generateItemByType(type, touchBoxCenter);

            i += step;
            j += step;
        }
    }
}

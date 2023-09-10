package obstacles;

import main.Game;
import my_utils.Constants;
import my_utils.Images;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static my_utils.Constants.ObstacleConstants.*;

/**
 * Stone. Common obstacle.
 */
public class Stone extends Obstacle {
    public Stone(float x, float y) {
        super(x, y, REAL_STONE_WIDTH, REAL_STONE_HEIGHT);
    }

    /**
     * Draws the stone.
     * @param g the object for drawing.
     */
    public void render(Graphics g) {
        g.drawImage(Images.getStoneImage(), (int)x, (int)y, (int)width, (int)height, null);
        if (Game.state == Constants.StatesEnum.EDITOR)
            g.drawRect((int)touchBox.x, (int)touchBox.y, (int)touchBox.width, (int)touchBox.height);
    }

    @Override
    protected void initTouchBox() {
        touchBox = new Rectangle2D.Float((int)(x + STONE_TOUCH_BOX_OFFSET_X), (int)(y + STONE_TOUCH_BOX_OFFSET_Y),
                (int)STONE_TOUCH_BOX_WIDTH, (int)STONE_TOUCH_BOX_HEIGHT);
    }

    @Override
    public void update() {}
}

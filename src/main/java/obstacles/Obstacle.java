package obstacles;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
 * This class is common for all obstacles, which can appear in the room.
 */
public abstract class Obstacle implements Serializable {
    protected float x, y;
    protected float width, height;
    protected Rectangle2D.Float touchBox;

    public Obstacle(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        initTouchBox();
    }

    public abstract void render(Graphics g);
    public abstract void update();
    protected abstract void initTouchBox();

    public Rectangle2D.Float getTouchBox() { return touchBox; }
}

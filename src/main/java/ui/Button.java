package ui;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * This class is common for all buttons.
 */
public abstract class Button {
    protected float x;
    protected float y;
    protected Rectangle2D.Float hitBox;
    protected boolean isMouseAbove;
    protected boolean isPressed;

    public Button(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        hitBox = new Rectangle2D.Float(x, y, width, height);
        isMouseAbove = false;
        isPressed = false;
    }

    protected void render(Graphics g, BufferedImage image1, BufferedImage image2) {
        if (isMouseAbove || isPressed)
            g.drawImage(image2, (int)x, (int)y, (int)hitBox.width, (int)hitBox.height, null);
        else
            g.drawImage(image1, (int)x, (int)y, (int)hitBox.width, (int)hitBox.height, null);
    }

    public void update() {}

    /*-------------------------------------Methods to implement-------------------------------------*/

    public abstract void render(Graphics g);
    public abstract void doWhenClicked();

    /*---------------------------------------Getters and setters---------------------------------------*/

    public float getX() { return x; }
    public float getY() { return y; }
    public Rectangle2D.Float getHitBox() { return hitBox; }
    public boolean isPressed() { return isPressed; }
    public boolean isMouseAbove() { return isMouseAbove; }

    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setMouseAbove(boolean mouseAbove) { isMouseAbove = mouseAbove; }
    public void setPressed(boolean pressed) { isPressed = pressed; }
}

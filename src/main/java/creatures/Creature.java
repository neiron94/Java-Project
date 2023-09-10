package creatures;

import my_utils.Constants;
import my_utils.HelpMethods;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.List;

import static my_utils.Constants.CreatureStates.*;
import static my_utils.Constants.*;

/**
 * This class is common for all creatures in the game.
 */
public abstract class Creature implements Serializable {
    // Position
    protected float x, y;
    protected float startX, startY;
    // touch/hit/attack boxes
    protected Rectangle2D.Float hitBox;
    protected Rectangle2D.Float touchBox;
    protected Rectangle2D.Float attackBox;
    protected Point hitBoxCenter;
    protected Point attackBoxCenter;
    protected Point touchBoxCenter;
    // Fields for animation
    protected int animTick, animIndex, animSpeed = 30;
    protected int flipX = 0, flipW = 1;
    // Fields for "code logic"
    protected Constants.CreatureStates state = IDLE;
    protected float tempSpeedX = 0, tempSpeedY = 0;
    protected boolean moving = false, attacking = false;
    protected boolean left = false, up = false, right = false, down = false;
    protected boolean attackChecked = false;
    // Fields for game logic
    protected float maxHealth;
    protected float currentHealth;
    protected boolean isAlive = true;
    protected float speed;
    protected float damage;
    protected float armor;

    /**
     * Configures start state of the creature.
     * @param startX x coordinate of start position.
     * @param startY y coordinate of start position.
     * @param maxHealth max health of the creature.
     * @param speed speed of the creature.
     * @param damage speed of the creature.
     * @param armor speed of the creature.
     */
    public Creature(float startX, float startY, float maxHealth, float speed, float damage, float armor) {
        this.maxHealth = maxHealth;
        currentHealth = maxHealth;
        this.speed = speed;
        this.damage = damage;
        this.armor = armor;
        this.startX = startX;
        this.startY = startY;
        x = startX;
        y = startY;
    }

    /*--------------------------Methods for working with touch/hit/attack box--------------------------*/
    protected void initTouchBox(float x, float y, float width, float height) {
        touchBox = new Rectangle2D.Float(x, y, width, height);
        touchBoxCenter = new Point((int)(x + width / 2), (int)(y + height / 2));
    }
    protected void initHitBox(float x, float y, float width, float height) {
        hitBox = new Rectangle2D.Float(x, y, width, height);
        hitBoxCenter = new Point((int)(x + width / 2), (int)(y + height / 2));
    }
    protected void initAttackBox(float x, float y, float width, float height) {
        attackBox = new Rectangle2D.Float(x, y, width, height);
        attackBoxCenter = new Point((int)(x + width / 2), (int)(y + height / 2));
    }

    protected void drawTouchBox(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawRect((int)touchBox.x, (int)touchBox.y, (int)touchBox.width, (int)touchBox.height);
        g.drawRect(touchBoxCenter.x - 5, touchBoxCenter.y - 5, 10, 10);
    }
    protected void drawHitBox(Graphics g) {
        g.setColor(Color.PINK);
        g.drawRect((int)hitBox.x, (int)hitBox.y, (int)hitBox.width, (int)hitBox.height);
        g.drawRect(hitBoxCenter.x - 5, hitBoxCenter.y - 5, 10, 10);
    }
    protected void drawAttackBox(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect((int)attackBox.x, (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
        g.drawRect(attackBoxCenter.x - 5, attackBoxCenter.y - 5, 10, 10);
    }

    protected void updateBox(float boxXOffset, float boxYOffset, float boxWidth, int realSpriteWidth, Constants.Boxes boxType) {
        Point2D.Float box = new Point2D.Float();
        boolean needChangeX = true;

        // If creature goes to the left, box needs to be flipped
        if (left)
            box.x = x + realSpriteWidth - boxXOffset - boxWidth;
        else if (right)
            box.x = x + boxXOffset;
        // If creature stops, box.x can't be counted, so we just don't want to change it
        else
            needChangeX = false;

        box.y = y + boxYOffset;

        switch (boxType) {
            case TOUCH -> {
                if (needChangeX)
                    touchBox.x = box.x;
                touchBox.y = box.y;
                touchBoxCenter.x = (int) (touchBox.x + touchBox.width / 2);
                touchBoxCenter.y = (int) (touchBox.y + touchBox.height / 2);
            }
            case HIT -> {
                if (needChangeX)
                    hitBox.x = box.x;
                hitBox.y = box.y;
                hitBoxCenter.x = (int) (hitBox.x + hitBox.width / 2);
                hitBoxCenter.y = (int) (hitBox.y + hitBox.height / 2);
            }
            case ATTACK -> {
                if (needChangeX)
                    attackBox.x = box.x;
                attackBox.y = box.y;
                attackBoxCenter.x = (int) (attackBox.x + attackBox.width / 2);
                attackBoxCenter.y = (int) (attackBox.y + attackBox.height / 2);
            }
        }
    }

    /*-------------------------------------Methods for updating-------------------------------------*/
    protected void updateAnimationTick(int spriteAmount) {
        animTick++;
        if (animTick >= animSpeed) {
            animTick = 0;
            animIndex++;
            if (animIndex >= spriteAmount) {
                animIndex = 0;
                afterAnimation();
            }
        }
    }

    protected void afterAnimation() {
        attacking = false;
        attackChecked = false;
        animSpeed = Constants.NORM_ANIM_SPEED;
        switch (state) {
            case HURT -> state = IDLE;
            case DEAD -> isAlive = false;
        }
    }

    protected void setNewState() {
        Constants.CreatureStates startState = state;

        // Can't do anything, if got hit or dead
        if (state != HURT && state != DEAD) {
            if (attacking) {
                state = ATTACK;
                // Only for Player. Sets its animation to the "real attack" sprite. It makes the attacks feel better.
                if (this instanceof Player) {
                    if (startState != ATTACK) {
                        animIndex = 3;
                        animTick = 0;
                        return;
                    }
                }
            }
            else if (moving)
                state = RUN;
            else
                state = IDLE;
        }

        if (startState != state) {
            animIndex = 0;
            animTick = 0;
        }
    }

    /*-------------------------------------Methods for moving-------------------------------------*/

    protected void move(List<Rectangle2D.Float> obstacles, int realSpriteWidth) {
        moving = false;
        tempSpeedX = tempSpeedY = 0;
        if (notAnyDirection())
            return;

        countTempSpeed(realSpriteWidth);

        boolean canMoveX = HelpMethods.canMove(tempSpeedX, 0, touchBox, obstacles);
        boolean canMoveY = HelpMethods.canMove(0, tempSpeedY, touchBox, obstacles);
        moveIfPossible(canMoveX, canMoveY);
        // Only for Enemy
        if (this instanceof Enemy)
            changeDirection(canMoveX, canMoveY);
    }

    private boolean notAnyDirection() {
        return !left && !right && !down && !up;
    }

    private void countTempSpeed(int realSpriteWidth) {
        if (left && !right) {
            moving = true;
            tempSpeedX = -speed;
            flipX = realSpriteWidth;
            flipW = -1;
        }
        else if (right && !left) {
            moving = true;
            tempSpeedX = speed;
            flipX = 0;
            flipW = 1;
        }

        if (up && !down) {
            moving = true;
            tempSpeedY = -speed;
        }
        else if (down && !up) {
            moving = true;
            tempSpeedY = speed;
        }

        // For moving diagonally
        if (tempSpeedX != 0 && tempSpeedY != 0) {
            tempSpeedX /= Math.sqrt(2);
            tempSpeedY /= Math.sqrt(2);
        }
    }

    private void moveIfPossible(boolean canMoveX, boolean canMoveY) {
        if (canMoveX && canMoveY) {
            x += tempSpeedX;
            y += tempSpeedY;
        }
        else if (canMoveX) {
            x += tempSpeedX;
            if (tempSpeedX == 0)
                moving = false;
        }
        else if (canMoveY) {
            y += tempSpeedY;
            if (tempSpeedY == 0)
                moving = false;
        }
        else
            moving = false;
    }

    private void changeDirection(boolean canMoveX, boolean canMoveY) {
        // Makes enemies bounce off walls
        if (!canMoveX) {
            left = !left;
            right = !right;
        }
        if (!canMoveY) {
            up = !up;
            down = !down;
        }
    }

    /*-----------------------------Other methods for game logic-----------------------------*/

    /**
     * Reduces health by the damage value.
     * @param damage health amount to reduce.
     */
    public void hurt(int damage) {
        int realDamage = (int)(damage - this.armor);
        if (realDamage < 0)
            realDamage = 0;

        currentHealth -= realDamage;
        animIndex = 0;
        animTick = 0;

        if (currentHealth <= 0) {
            currentHealth = 0;
            animSpeed = DEAD_ANIM_SPEED;
            state = DEAD;
        }
        else {
            animSpeed = HURT_ANIM_SPEED;
            state = HURT;
        }
    }

    /**
     * Sets all direction booleans to false.
     */
    public void resetDirBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    /**
     * Moves creature to the given position.
     * @param x x coordinate of new position.
     * @param y y coordinate of new position.
     */
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /*---------------------------------------Getters and setters---------------------------------------*/

    public void setAnimIndex(int animIndex) { this.animIndex = animIndex; }
    public void setState(Constants.CreatureStates state) { this.state = state; }
    public void setAttacking(boolean attacking) { this.attacking = attacking; }
    public void setLeft(boolean left) { this.left = left; }
    public void setUp(boolean up) { this.up = up; }
    public void setRight(boolean right) { this.right = right; }
    public void setArmor(float armor) { this.armor = armor; }
    public void setDown(boolean down) { this.down = down; }
    public void setDamage(float damage) { this.damage = damage; }
    public void setMaxHealth(float maxHealth) { this.maxHealth = maxHealth; }
    public void setCurrentHealth(float currentHealth) { this.currentHealth = currentHealth; }
    public void setSpeed(float speed) { this.speed = speed; }

    public float getSpeed() { return speed; }
    public Point getHitBoxCenter() { return hitBoxCenter; }
    public Rectangle2D.Float getTouchBox() { return touchBox; }
    public int getAnimIndex() { return animIndex; }
    public float getMaxHealth() { return maxHealth; }
    public boolean isAlive() { return isAlive; }
    public float getDamage() { return damage; }
    public float getArmor() { return armor; }
    public int getAnimSpeed() { return animSpeed; }
    public CreatureStates getState() { return state; }
    public float getCurrentHealth() { return currentHealth; }
}

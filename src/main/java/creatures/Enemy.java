package creatures;

import gamestates.Play;
import items.common.Key;
import my_utils.Constants;
import my_utils.HelpMethods;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Random;

import static my_utils.Constants.CreatureStates.*;
import static my_utils.Constants.EnemyConstants.*;

/**
 * This class is common for all enemies in the game.
 */
public abstract class Enemy extends Creature {
    protected float visionRange;
    protected float attackRange;
    protected boolean isLootDropped;

    /**
     * Configures start state of the enemy.
     */
    public Enemy(float startX, float startY, float maxHealth, float speed, float damage, float armor, float visionRange, float attackRange) {
        super(startX, startY, maxHealth, speed, damage, armor);
        this.visionRange = visionRange;
        this.attackRange = attackRange;
        isLootDropped = false;
        chooseRandomDirection();
    }

    private void chooseRandomDirection() {
        Random random = new Random();

        int randNumber = random.nextInt(2);
        switch (randNumber) {
            case 0 -> left = true;
            case 1 -> right = true;
        }

        randNumber = random.nextInt(2);
        switch (randNumber) {
            case 0 -> up = true;
            case 1 -> down = true;
        }
    }

    /*-------------------------------------Methods for updating-------------------------------------*/

    /**
     * Updates enemy's state.
     * @param obstacles obstacles of current room with all 4 doors.
     * @param spriteWidth sprite width of particular enemy.
     * @param spriteAmount number of sprites of current enemy's action.
     */
    protected void update(List<Rectangle2D.Float> obstacles, int spriteWidth, int spriteAmount) {
        updateState(obstacles, spriteWidth);
        updateBoxes();

        // Stop if player is dead
        if (Play.getPlayer().state == DEAD) {
            state = IDLE;
            attacking = moving = false;
        }

        updateAnimationTick(spriteAmount);
        setNewState();
    }

    protected void updateState(List<Rectangle2D.Float> obstacles, int spriteWidth) {
        switch (state) {
            case IDLE -> {
                state = RUN;
                moving = true;
            }
            case RUN -> {
                if (isPlayerInAttackRange()) {
                    attacking = true;
                } else {
                    if (canSeePlayer(obstacles))
                        turnToPlayer();
                    move(obstacles, spriteWidth);
                }
            }
            case ATTACK -> {
                // Can't move while attack
                moving = false;
                // Check hit only on particular animation sprite and only once per attack animation
                if (animIndex == 2 && !attackChecked)
                    checkEnemyHit();
            }
            case DEAD, HURT -> {
                moving = false;
                attacking = false;
            }
        }
    }

    /*---------------------------------Methods for interacting with the player---------------------------------*/

    private boolean canSeePlayer(List<Rectangle2D.Float> obstacles) {
        return isPlayerInVisionRange() && isSightClear(obstacles);
    }

    private boolean isPlayerInVisionRange() {
        // Distance is counted from player's hit box center to enemy's attack box center
        int distance = HelpMethods.countDistance(Play.getPlayer().hitBoxCenter, this.attackBoxCenter);
        return distance <= visionRange;
    }

    private boolean isSightClear(List<Rectangle2D.Float> obstacles) {
        // Line of enemy's vision goes from the player's touch box center to enemy's attack box center
        Line2D.Float line = new Line2D.Float(Play.getPlayer().touchBoxCenter, attackBoxCenter);

        // Can't see the player if he stands behind an obstacle
        for (Rectangle2D.Float obstacle : obstacles)
            if (line.intersects(obstacle))
                return false;
        return true;
    }

    private void turnToPlayer() {
        Player player = Play.getPlayer();

        resetDirBooleans();
        // If the enemy is above or below the player, he will choose only one direction,
        // so that there are no "jumps" of animation
        if (attackBoxCenter.x > player.getHitBoxCenter().x - GO_UP_RANGE && attackBoxCenter.x < player.getHitBoxCenter().x + GO_UP_RANGE) {
            if (player.getHitBoxCenter().y > attackBoxCenter.y)
                down = true;
            else if (player.getHitBoxCenter().y < attackBoxCenter.y)
                up = true;
            else if (player.getHitBoxCenter().x > attackBoxCenter.x)
                right = true;
            else
                left = true;
        }
        // If the player is at a sufficiently great distance,
        // the opponent can go diagonally (choose 2 directions)
        else {
            if (player.getHitBoxCenter().x > attackBoxCenter.x)
                right = true;
            else
                left = true;

            if (player.getHitBoxCenter().y > attackBoxCenter.y)
                down = true;
            else
                up = true;
        }
    }

    private boolean isPlayerInAttackRange() {
        // Distance is counted from player's hit box center to enemy's attack box center
        int distance = HelpMethods.countDistance(Play.getPlayer().hitBoxCenter, this.attackBoxCenter);
        return distance <= attackRange && attackBox.intersects(Play.getPlayer().hitBox);
    }

    private void checkEnemyHit() {
        if (attackBox.intersects(Play.getPlayer().hitBox))
            Play.getPlayer().hurt((int)damage);
        attackChecked = true;
    }

    /*-------------------------------------Other methods-------------------------------------*/

    /**
     * Moves the enemy to the starting point.
     */
    public void setStartPosition() {
        x = startX;
        y = startY;
        startBoxesInit();   // should move all boxes as well
    }

    /**
     * Sets the enemy's state to "IDLE" after end of the attack animation.
     */
    @Override
    protected void afterAnimation() {
        super.afterAnimation();

        if (state == ATTACK)
            state = IDLE;
    }

    /**
     * Creates item in the center of enemy's touch box after his death.
     * @param dropPool drop pol of the particular enemy.
     */
    protected void dropLoot(List<Constants.ItemTypes> dropPool) {
        // The last killed enemy on the level should drop key to the final room
        if (Play.getLevel().isKeyDrop()) {
            Key.drop(touchBoxCenter, Play.getLevel().getCurrentRoom());
            return;
        }

        // Enemy can drop item only once
        if (isLootDropped)
            return;
        isLootDropped = true;

        // Drop random item from the dropPool
        int dropsCount = dropPool.size();
        Random random = new Random();
        int randInt = random.nextInt(100);
        float step = (float) 100 / dropsCount;
        float i = 0, j = step;

        for (Constants.ItemTypes type : dropPool) {
            if (randInt >= i && randInt < j)
                HelpMethods.generateItemByType(type, touchBoxCenter);

            i += step;
            j += step;
        }
    }

    public abstract void render(Graphics g);
    public abstract void update(List<Rectangle2D.Float> obstacles);
    protected abstract void updateBoxes();
    protected abstract void startBoxesInit();
}

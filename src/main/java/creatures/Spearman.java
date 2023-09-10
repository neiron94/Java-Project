package creatures;

import gamestates.Play;
import main.Game;
import my_utils.Constants;
import my_utils.Images;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import static my_utils.Constants.CreatureStates.*;
import static my_utils.Constants.EnemyConstants.SpearmanConstants.*;
import static my_utils.Constants.ItemTypes.*;

/**
 * First type of enemies.
 */
public class Spearman extends Enemy {
    private static final List<Constants.ItemTypes> dropPool;
    static {
        dropPool = new ArrayList<>();
        dropPool.add(COIN);
        dropPool.add(HEAL_POTION);
    }

    /**
     * Configures spearman's start state.
     * @param startX x start coordinate.
     * @param startY y start coordinate.
     */
    public Spearman(float startX, float startY) {
        super(startX, startY, MAX_HEALTH, SPEED, DAMAGE, ARMOR, VISION_RANGE, ATTACK_RANGE);
        startBoxesInit();
    }

    /*--------------------------Methods for working with touch/hit/attack box--------------------------*/
    protected void startBoxesInit() {
        initTouchBox(startX + TOUCH_BOX_X_OFFSET, startY + TOUCH_BOX_Y_OFFSET, TOUCH_BOX_WIDTH, TOUCH_BOX_HEIGHT);
        initHitBox(startX + HIT_BOX_X_OFFSET, startY + HIT_BOX_Y_OFFSET, HIT_BOX_WIDTH, HIT_BOX_HEIGHT);
        initAttackBox(startX + ATTACK_BOX_X_OFFSET, startY + ATTACK_BOX_Y_OFFSET, ATTACK_BOX_WIDTH, ATTACK_BOX_HEIGHT);
    }

    protected void updateBoxes() {
        updateTouchBox();
        updateHitBox();
        updateAttackBox();
    }
    private void updateTouchBox() { updateBox(TOUCH_BOX_X_OFFSET, TOUCH_BOX_Y_OFFSET, TOUCH_BOX_WIDTH, REAL_SPRITE_WIDTH, Constants.Boxes.TOUCH); }
    private void updateHitBox() { updateBox(HIT_BOX_X_OFFSET, HIT_BOX_Y_OFFSET, HIT_BOX_WIDTH, REAL_SPRITE_WIDTH, Constants.Boxes.HIT); }
    private void updateAttackBox() { updateBox(ATTACK_BOX_X_OFFSET, ATTACK_BOX_Y_OFFSET, ATTACK_BOX_WIDTH, REAL_SPRITE_WIDTH, Constants.Boxes.ATTACK); }

    /*--------------------------------------Render and update--------------------------------------*/
    /**
     * Draws a spearman.
     * @param g object for drawing.
     */
    public void render(Graphics g) {
        g.drawImage(Images.getSpearmanEnemyAnimations()[getIntByState(state)][animIndex], (int)x + flipX, (int)y, REAL_SPRITE_WIDTH * flipW, REAL_SPRITE_HEIGHT, null);

        // If spearman is dead and on the last sprite of death animation
        if (state == DEAD && animIndex == getSpriteAmount(DEAD) - 1) {
            isAlive = false;
            Play.getLevel().getCurrentRoom().killEnemy();
            dropLoot(dropPool);
        }

        if (Game.state == Constants.StatesEnum.EDITOR)
            drawTouchBox(g);

        // For debugging
        //drawHitBox(g);
        //drawAttackBox(g);
    }

    /**
     * Updates a spearman.
     * @param obstacles obstacles of current room with all 4 doors.
     */
    public void update(List<Rectangle2D.Float> obstacles) {
        update(obstacles, REAL_SPRITE_WIDTH, getSpriteAmount(state));
    }
}

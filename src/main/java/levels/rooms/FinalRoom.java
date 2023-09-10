package levels.rooms;

import gamestates.Editor;
import gamestates.Play;
import main.Game;
import my_utils.Constants;
import my_utils.Images;
import ui.play.VictoryScreen;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import static my_utils.Constants.WindowConstants.*;
import static my_utils.Constants.RoomConstants.*;

/**
 * Final room. Can be opened by key. After clearing open exit.
 */
public class FinalRoom extends Room {
    static boolean isExitOpened;

    public FinalRoom(int posMatrixX, int posMatrixY) {
        super(posMatrixX, posMatrixY, true, false, Constants.RoomTypes.FINAL);
        Editor.getLevel().setFinalRoomAlreadyExist(true);
        isExitOpened = false;
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        if (isExitOpened)
            ExitHole.getInstance().render(g);
    }

    @Override
    public void update() {
        super.update();
        if (isExitOpened)
            ExitHole.getInstance().update();
    }

    @Override
    protected void renderRoomBackground(Graphics g) {
        g.drawImage(Images.getFinalRoomImage(), 0, 0, REAL_WINDOW_WIDTH, REAL_WINDOW_HEIGHT, null);
    }

    @Override
    public void deleteRoom() {
        super.deleteRoom();
        Editor.getLevel().setFinalRoomAlreadyExist(false);
    }

    public static void openExit() { isExitOpened = true; }

    private static class ExitHole implements Serializable {
        private static ExitHole exitHoleInstance = null;

        private final float x = EXIT_HOLE_X;
        private final float y = EXIT_HOLE_Y;
        private final float width = REAL_EXIT_HOLE_WIDTH;
        private final float height = REAL_EXIT_HOLE_HEIGHT;
        private final Rectangle2D.Float touchBox = new Rectangle2D.Float(x, y, width, height);
        private  int animTick, animIndex, animSpeed = 30;

        public static ExitHole getInstance() {
            if (exitHoleInstance == null)
                exitHoleInstance = new ExitHole();

            return exitHoleInstance;
        }

        private ExitHole() {}

        /**
         * Draws exit hole.
         * @param g the object for drawing.
         */
        public void render(Graphics g) {
            g.drawImage(Images.getExitHole()[animIndex], (int)x, (int)y, (int)width, (int)height, null);
        }

        /**
         * Set isVictory to true, if the player touches it.
         */
        public void update() {
            if (Play.isVictory())
                return;

            if (Play.getPlayer().getTouchBox().intersects(touchBox)) {
                Play.setVictory(true);
                VictoryScreen.getTextField().setText("");
                Game.getGamePanel().add(VictoryScreen.getTextField());
            }
            updateAnimationTick();
        }

        protected void updateAnimationTick() {
            animTick++;
            if (animTick >= animSpeed) {
                animTick = 0;
                animIndex++;
                if (animIndex >= EXIT_HOLE_SPRITE_AMOUNT)
                    animIndex = 0;
            }
        }
    }
}

package levels.rooms;

import my_utils.Constants;
import my_utils.Images;

import java.awt.*;

import static my_utils.Constants.WindowConstants.*;

/**
 * Common room.
 */
public class CommonRoom extends Room {
    public CommonRoom(int posMatrixX, int posMatrixY) {
        super(posMatrixX, posMatrixY, false, false, Constants.RoomTypes.COMMON);
    }

    @Override
    protected void renderRoomBackground(Graphics g) {
        g.drawImage(Images.getCommonRoomImage(), 0, 0, REAL_WINDOW_WIDTH, REAL_WINDOW_HEIGHT, null);
    }
}

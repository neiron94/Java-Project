package ui.editor;

import gamestates.Editor;
import gamestates.editor_stages.CreationStage;
import my_utils.Constants;
import my_utils.Images;
import ui.Button;

import java.awt.*;
import java.awt.image.BufferedImage;

import static my_utils.Constants.EditStages.CREATING;
import static my_utils.Constants.UIConstants.EditorConstants.*;

/**
 * Button in the "Choosing stage" of the "Editor mode". Sets entity for the "Creation stage" when is clicked.
 */
public class EditorChooseButton extends Button {
    private final Constants.Entities entityType;

    public EditorChooseButton(float x, float y, Constants.Entities entityType) {
        super(x, y, REAL_CHOOSING_BUTTON_WIDTH, REAL_CHOOSING_BUTTON_HEIGHT);
        this.entityType = entityType;
    }

    @Override
    public void render(Graphics g) {
        render(g, Images.getEditorChoosingButton1(), Images.getEditorChoosingButton2());
        g.drawImage(decideOnImage(), (int)x, (int)y, (int)REAL_CHOOSING_BUTTON_WIDTH, (int)REAL_CHOOSING_BUTTON_HEIGHT, null);
    }

    /**
     * Sets chosen entity for the "Creation stage" and goes to the "Creation stage".
     */
    @Override
    public void doWhenClicked() {
        CreationStage.setChosenEntity(entityType);
        Editor.setStage(CREATING);
    }

    private BufferedImage decideOnImage() {
        return switch (entityType) {
            case SPEARMAN -> Images.getSpearmanEnemyAnimations()[0][0];
            case KNIGHT -> Images.getKnightEnemyAnimations()[0][0];
            case STONE -> Images.getStoneImage();
            case BARREL -> Images.getBarrelAnimation()[0];
        };
    }
}

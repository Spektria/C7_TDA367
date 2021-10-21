package C7.Model.Tools;

import C7.Model.Layer.ILayer;
import C7.Model.Tools.ToolProperties.ToolPropertyFactory;
import C7.Util.Vector2D;

/**
 * Transformation Tool for translating layers.
 * @author Hugo Ekstrand
 */
class TranslationTool extends BaseTool {
    private boolean isContinuous = true;

    TranslationTool(){
        addProperties(
                ToolPropertyFactory.createBooleanProperty("Display live movement (May negatively impact performance)",
                        (b) -> isContinuous = b, () -> isContinuous)
        );
    }

    @Override
    public void apply(ILayer layer, Vector2D v0, Vector2D v1) {
        Vector2D movement = v1.sub(v0).scale(new Vector2D(1d/2, 1d/2));
        layer.setPosition(layer.getPosition().add(movement));
        layer.update();
    }

    @Override
    public boolean isContinuous() {
        return isContinuous;
    }

}

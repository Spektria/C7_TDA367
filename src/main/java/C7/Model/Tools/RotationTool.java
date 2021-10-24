package C7.Model.Tools;

import C7.Model.Layer.ILayer;
import C7.Model.Tools.ToolProperties.ToolPropertyFactory;
import C7.Util.Vector2D;

/**
 * A transformation tool used for rotating a layer.
 * @author Hugo Ekstrand
 */
class RotationTool extends BaseTool {
    private boolean isContinuous = true;

    RotationTool(){
        addProperties(
                ToolPropertyFactory.createBooleanProperty("Continuous rotation",
                        (b) -> isContinuous = b, () -> isContinuous)
        );
    }

    @Override
    public void apply(ILayer layer, Vector2D v0, Vector2D v1) {
        Vector2D center = layer.getPosition().add(layer.getLocalCenterPoint());

        // Cannot rotate if the two given points are the same or if one of the given points
        // or more are at the rotation axis.
        if(v0.equals(center) || v1.equals(center) || v0.equals(v1))
            return;

        double rot = center.sub(v0).angleBetweenWithSign(center.sub(v1));
        layer.setRotation(layer.getRotation() + rot);
        layer.update();
    }

    @Override
    public boolean isContinuous() {
        return isContinuous;
    }

}

package C7.Model.Tools;

import C7.Model.Layer.ILayer;
import C7.Model.Tools.ToolProperties.ToolPropertyFactory;
import C7.Util.Vector2D;

/**
 * A transform tool used for scaling a layer in its x- and y-axis.
 * @author Hugo Ekstrand
 */
class ScalingTool extends BaseTool {
    private boolean isContinuous = true;
    private boolean relative = true;

    ScalingTool(){
        addProperties(
                ToolPropertyFactory.createBooleanProperty("Display live scaling (May negatively impact performance)",
                        (b) -> isContinuous = b, () -> isContinuous),
                ToolPropertyFactory.createBooleanProperty("Relative to quadrant",
                        (b) -> relative = b, () -> relative)
        );
    }

    @Override
    public void apply(ILayer layer, Vector2D v0, Vector2D v1) {
        Vector2D delta = v1.sub(v0);

        // We want to add/subtract the delta length from both the width and height of the layer so
        // that when we drag a layer it "moves with the mouse".
        // To do this we calculate how much we need to add / subtract to the scale to get that distance.
        // For the x-axis the change is scaleToAdd = dx / width.
        Vector2D scaleToAdd = delta.scale(new Vector2D(1d/layer.getWidth(), 1d/layer.getHeight()));

        if(relative)
            // If the scaling is supposed to be relative to the quadrants we need to compensate for
            // whichever quadrant the given points are in. That is, the scaling in quadrant 1 should be reverse
            // to that of scaling in quadrant 4. To do this we multiply by 1 or -1 depending on if the quadrant axis
            // are negative or positive. E.g. if we are in quadrant 2 we multiply the vectors x value with -1 and
            // the y value with 1.
            scaleToAdd = scaleToAdd.scale(LayerQuadrantUtil.getGlobalQuadrantPosition(layer, v0));

        layer.setScale(layer.getScale().add(scaleToAdd));
        layer.update();
    }

    @Override
    public boolean isContinuous() {
        return isContinuous;
    }

}

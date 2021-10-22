package C7.Model.Tools;

import C7.Model.Layer.ILayer;
import C7.Model.Tools.Pattern.IPattern;
import C7.Model.Tools.StrokeInterpolation.IStrokeInterpolator;
import C7.Model.Tools.ToolProperties.ToolPropertyFactory;
import C7.Util.Color;
import C7.Util.Vector2D;

import java.util.Collection;
import java.util.Objects;

/**
 * A BlendBrush is a StrokeTool which draws a stroke with a color. The drawing is done by blending
 * the BlendBrush's color with the color on the layer.
 */
class BlendBrush extends StrokeTool {

    private Color color;

    BlendBrush(int size, Color color, IPattern strokePattern, IStrokeInterpolator strokeInterpolator) {
        super(size, strokePattern, strokeInterpolator);

        Objects.requireNonNull(color);

        this.color = color;

        addProperties(
                ToolPropertyFactory.createColorProperty("Stroke color",
                        (c) -> this.color = c, () -> this.color)
        );
    }


    @Override
    protected void affectLayer(Collection<Vector2D> pointsOnLayer, ILayer layer) {
        // For every point, blend the color at the point on the canvas and this BlendBrush's color.
        pointsOnLayer.parallelStream()
                .forEach(point -> {
                    int x = (int)point.getX();
                    int y = (int)point.getY();
                    Color beforeColor = layer.getLocalPixel(x, y, new Color(0,0,0,0));
                    Color.blend(beforeColor, this.color, beforeColor);
                    layer.setLocalPixel(x, y, beforeColor);
                });
    }

    @Override
    public boolean isContinuous() {
        return true;
    }
}

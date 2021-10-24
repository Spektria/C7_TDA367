package C7.Model.Tools;

import C7.Model.Layer.ILayer;
import C7.Model.Tools.Pattern.IPattern;
import C7.Model.Tools.StrokeInterpolation.IStrokeInterpolator;
import C7.Util.Color;
import C7.Util.Vector2D;

import java.util.Collection;
import java.util.Objects;

/**
 * An OverwriteBrush is a StrokeTool which replaces the color on a layer with a given color on its stroke.
 *
 * @author Hugo Ekstrand
 */
class OverwriteBrush extends StrokeTool {

    private Color overwriteColor;

    OverwriteBrush(int size, Color overwriteColor, IPattern strokePattern, IStrokeInterpolator strokeInterpolator) {
        super(size, strokePattern, strokeInterpolator);

        Objects.requireNonNull(overwriteColor);

        this.overwriteColor = overwriteColor;
    }

    @Override
    protected void affectLayer(Collection<Vector2D> pointsOnLayer, ILayer layer) {
        pointsOnLayer
                .parallelStream()
                .forEach(point -> {
                    int x = (int)point.getX();
                    int y = (int)point.getY();
                    layer.setLocalPixel(x, y, overwriteColor);
                });
    }

    @Override
    public boolean isContinuous() {
        return true;
    }
}

package C7.Model.Tools;

import C7.Model.Color;
import C7.Model.Layer.ILayer;
import C7.Model.Tools.Pattern.IPattern;
import C7.Model.Tools.StrokeInterpolation.IStrokeInterpolator;
import C7.Model.Tools.ToolProperties.IToolProperty;
import C7.Model.Tools.ToolProperties.ToolPropertyFactory;
import C7.Util.Vector2D;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 *
 * A Brush is a {@link ITool} which draws a finite series of patterns on a {@link ILayer}.
 * The brush if modifiable in runtime via its {@link IToolProperty properties} via the {@link #getProperties()}
 * method.
 *
 * @author Hugo Ekstrand
 */
class Brush extends BaseTool {
    // Common properties for all brushes.
    private int size;
    private double rotation = 0; // Radians
    private Vector2D scale = new Vector2D(1,1);
    private Color color;
    private double pointFrequency  = 1.5d; // points per pixel

    private final IStrokeInterpolator strokeInterpolator;

    private final IPattern strokePattern;

    Brush(int size, Color color, IPattern strokePattern, IStrokeInterpolator strokeInterpolator){
        Objects.requireNonNull(strokeInterpolator);
        Objects.requireNonNull(strokePattern);
        Objects.requireNonNull(color);

        this.strokePattern = strokePattern;
        this.strokeInterpolator = strokeInterpolator;
        this.color = color;
        this.size = size;

        addProperties(
                ToolPropertyFactory.createIntegerProperty("Stroke size",
                        (i) -> this.size = i, () -> this.size, 0, 50),
                ToolPropertyFactory.createDoubleProperty("Rotation",
                        (rot) -> this.rotation = Math.toRadians(rot), () -> Math.toDegrees(this.rotation), 0, 360),
                ToolPropertyFactory.createDoubleProperty("Y-scale",
                        (y) -> this.scale = new Vector2D(scale.getX(), y), () -> this.scale.getY(), 0, 5),
                ToolPropertyFactory.createDoubleProperty("X-scale",
                        (x) -> this.scale = new Vector2D(x, scale.getY()), () -> this.scale.getY(), 0, 5),
                ToolPropertyFactory.createColorProperty("Stroke color",
                        (c) -> this.color = c, () -> this.color),
                ToolPropertyFactory.createDoubleProperty("Point frequency",
                        (freq) -> this.pointFrequency = freq, () -> this.pointFrequency, 0, 10)
        );
    }

    private Collection<Vector2D> fetchPoints(ILayer layer){
        Vector2D inverseScale = new Vector2D(1d/layer.getScale().getY(), 1d/layer.getScale().getY());

        // Fetch pattern pixels
        return strokePattern.getPoints(size,

                // The scale of the pattern will be inverse of the layers scale if we are to perseve a consistent pattern size
                // regardless of the layer scale. I.e. if the layer is at 1/2x scale the pattern should be at 2x scale
                scale.scale(inverseScale),

                // Rotation should also not be affected by the layers current rotation,
                // so we need to compensate by rotating in the reverse direction
                rotation - layer.getRotation());
    }

    @Override
    public void apply(ILayer layer, Vector2D v0, Vector2D v1) {

        final Collection<Vector2D> patternPoints = fetchPoints(layer);

        // Convert to local space of the layer
        final Vector2D localV0 = layer.toLocalPixel(v0);
        final Vector2D localV1 = layer.toLocalPixel(v1);

        // the amount of points per pixel also needs to be compensated so that it is consistent with
        // regardless of the layers scale.
        double scaledPointFrequency = pointFrequency * layer.getScale().len();

        // Interpolate the given points so that any "holes" of empty points are filled.
        strokeInterpolator.interpolate(scaledPointFrequency, localV0, localV1)
                .parallelStream()

                // For each interpolated point...
                .forEach(interpolatedClickPoint ->
                        // For each point in the pattern
                        patternPoints.stream()
                                // Translate the pattern point to interpolated point position (i.e. mouse click)
                                .map(v -> v.add(interpolatedClickPoint))
                                // Check so that the new, translated pattern point is on the layer
                                .filter(v -> layer.isPixelOnLocalLayer((int)v.getX(), (int)v.getY()))
                                // If it is on the layer, draw the points which are on the layer.
                                .forEach(v -> layer.setLocalPixel((int)v.getX(), (int)v.getY(), color)));

        layer.update();
    }

    @Override
    public boolean isContinuous() {
        return true;
    }

}

package C7.Model.Tools;

import C7.Model.Layer.ILayer;
import C7.Model.Tools.Pattern.IPattern;
import C7.Model.Tools.StrokeInterpolation.IStrokeInterpolator;
import C7.Model.Tools.ToolProperties.IToolProperty;
import C7.Model.Tools.ToolProperties.ToolPropertyFactory;
import C7.Util.Vector2D;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * A StrokeTool is a {@link ITool} which draws a finite series of patterns on a {@link ILayer} between two given points.
 * The StrokeTool can be modified in runtime via its {@link IToolProperty properties} via the {@link #getProperties()}
 * method.
 *
 * @author Hugo Ekstrand
 */
abstract class StrokeTool extends BaseTool {
    // Common properties for all stroke tools.
    private int size;
    private double rotation = 0; // Radians
    private Vector2D scale = new Vector2D(1,1);
    private double pointFrequency  = 1.5d; // points per pixel

    private final IStrokeInterpolator strokeInterpolator;

    private final IPattern strokePattern;

    StrokeTool(int size, IPattern strokePattern, IStrokeInterpolator strokeInterpolator){
        Objects.requireNonNull(strokeInterpolator);
        Objects.requireNonNull(strokePattern);

        this.strokePattern = strokePattern;
        this.strokeInterpolator = strokeInterpolator;
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

    private Collection<Vector2D> getStrokePoints(Collection<Vector2D> patternPoints, double pointFrequency, Vector2D v0, Vector2D v1){
        // Interpolate the given points so that any "holes" of empty points are filled.
        // Then for each of these points do:
        // 1: Add points for the Tools's pattern around the given point.
        // 2: Round the points to the closest integer so that they represent pixels.
        // Lastly, after doing this collect all the created points as a set so that any duplicate points are removed.
        return strokeInterpolator.interpolate(pointFrequency, v0, v1)
                .parallelStream()
                .flatMap(interpolatedPoint -> patternPoints.stream().map(v -> v.add(interpolatedPoint)))
                .map(v -> new Vector2D((int)v.getX(), (int)v.getY()))
                //.map(v -> new Tuple2<>((int)v.getX(), (int)v.getY()))
                .collect(Collectors.toSet());
    }

    private void drawPointsOnLayer(Collection<Vector2D> points, ILayer layer){
        // For these points which we should draw,
        // 1: check that they are on the layer which we're drawing on.
        // 2: blend the current color with the color being drawn.
        // 3: write the blended color to the layer.
        List<Vector2D> pointsOnLayer = points.parallelStream()
                .filter(v -> layer.isPixelOnLocalLayer((int)v.getX(), (int)v.getY()))
                .collect(Collectors.toList());

        // If it is on the layer, draw the points which are on the layer.
        affectLayer(pointsOnLayer, layer);
    }

    protected abstract void affectLayer(Collection<Vector2D> pointsOnLayer, ILayer layer);

    @Override
    public void apply(ILayer layer, Vector2D v0, Vector2D v1) {

        final Collection<Vector2D> patternPoints = fetchPoints(layer);

        // Convert to local space of the layer
        final Vector2D localV0 = layer.toLocalPixel(v0);
        final Vector2D localV1 = layer.toLocalPixel(v1);

        // the amount of points per pixel also needs to be compensated so that it is consistent with
        // regardless of the layers scale.
        double scaledPointFrequency = pointFrequency * layer.getScale().len();

        // Get all the strokes points in local space.
        Collection<Vector2D> strokePoints = getStrokePoints(patternPoints, scaledPointFrequency, localV0, localV1);

        drawPointsOnLayer(strokePoints, layer);

        layer.update();
    }

}

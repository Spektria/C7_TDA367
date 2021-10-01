package C7.Model.Tools;

import C7.Model.Color;
import C7.Model.Layer.ILayer;
import C7.Model.Tools.Pattern.IPattern;
import C7.Model.Tools.StrokeInterpolation.IStrokeInterpolator;
import C7.Model.Tools.ToolProperties.IToolProperty;
import C7.Model.Tools.ToolProperties.ToolPropertyFactory;
import C7.Model.Vector.Vector2D;

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
class Brush implements ITool {

    private final Collection<IToolProperty> properties;

    // Common properties for all brushes.
    private int size;
    private double rotation; // Radians
    private Vector2D scale;
    private Color color;
    private double pointFrequency; // points per pixel

    private final IStrokeInterpolator strokeInterpolator;

    private final IPattern strokePattern;

    Brush(IPattern strokePattern, IStrokeInterpolator strokeInterpolator){
        Objects.requireNonNull(strokeInterpolator);
        Objects.requireNonNull(strokePattern);

        this.strokePattern = strokePattern;
        this.strokeInterpolator = strokeInterpolator;

        setToDefault();

        this.properties = Arrays.asList(
                ToolPropertyFactory.createIntegerProperty("Stroke size", "The size of the stroke",
                        (i) -> this.size = i, () -> this.size, 0, 50),
                ToolPropertyFactory.createDoubleProperty("Rotation", "The rotation of the stroke. E.g. a line could be rotated to PI/4",
                        (rot) -> this.rotation = Math.toRadians(rot), () -> Math.toDegrees(this.rotation), 0, 360),
                ToolPropertyFactory.createDoubleProperty("Y-scale", "The scale of the brush stroke in the y axis",
                        (y) -> this.scale = new Vector2D(scale.getX(), y), () -> this.scale.getY(), 0, 5),
                ToolPropertyFactory.createDoubleProperty("X-scale", "The scale of the brush stroke in the x axis",
                        (x) -> this.scale = new Vector2D(x, scale.getY()), () -> this.scale.getY(), 0, 5),
                ToolPropertyFactory.createColorProperty("Stroke color", "The color of the stroke",
                        (c) -> this.color = c, () -> this.color),
                ToolPropertyFactory.createDoubleProperty("Point frequency", "How many times the brush should draw per pixel",
                        (freq) -> this.pointFrequency = freq, () -> this.pointFrequency, 0, 10)
        );
    }

    @Override
    public Collection<IToolProperty> getProperties() {
        return properties;
    }

    @Override
    public void apply(ILayer layer, Vector2D v0, Vector2D v1) {

        // Fetch pattern pixels
        final Collection<Vector2D> points = strokePattern.getPoints(size, scale, rotation);

        // Interpolate the given points so that any "holes" of empty points are filled.
        strokeInterpolator.interpolate(pointFrequency, v0, v1)
                .parallelStream()

                // For each point, translate it to the current
                // interpolated point's position.
                .forEach(point ->
                        points.stream()
                                .parallel()
                                .map(v -> v.add(point))
                                // Then draw the translated points onto the layer
                                .filter(layer::isPointOnLayer)
                                .forEach(v -> layer.setGlobalPixel((int)v.getX(), (int)v.getY(), color)));
        layer.update();
    }

    @Override
    public boolean isContinuous() {
        return true;
    }

    @Override
    public void setToDefault() {
        this.size = 11;
        this.rotation = 0;
        this.scale = new Vector2D(1,1);
        this.color = new Color(0,0,0,1f);
        this.pointFrequency = 1.5d;
    }
}

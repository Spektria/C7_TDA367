package C7.Model.Tools;

import C7.Color;
import C7.Layer.ILayer;
import C7.Model.Tools.StrokeInterpolation.LinearStrokeInterpolator;
import C7.Model.Tools.StrokeInterpolation.StrokeInterpolator;
import C7.Model.Vector.Vector2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a brush which doesn't do anything. However, this may be modified with {@link IBrushEffect} which affects
 * what this brush will do when used on a point on a {@link ILayer layer}.
 * @author Hugo Ekstrand
 */
class BaseBrush implements ITool {

    private StrokeInterpolator interpolator = new LinearStrokeInterpolator();
    private Vector2D lastPoint;
    private ILayer layer;
    private List<IBrushEffect> effects = new ArrayList<>();

    BaseBrush(ILayer layer, IBrushEffect... effects){
        this.layer = layer;
        this.effects.addAll(List.of(effects));
    }

    private void drawPoint(Vector2D point, Color color) {
        layer.setPixel((int)point.getX(), (int)point.getY(), color);
    }

    private Map<Vector2D, Color> createStroke(Vector2D center, ILayer layer){
        Map<Vector2D, Color> pixels = new HashMap<>();
        for(var effect : effects)
            pixels = effect.handle(layer, center, pixels);

        return pixels;
    }

    @Override
    public void beginDraw(Vector2D pos) {
        lastPoint = pos;
        createStroke(pos, layer).forEach(this::drawPoint);
    }

    @Override
    public void move(Vector2D pos) {
        interpolator.interpolate(lastPoint, pos, 1)
                .parallelStream()
                .forEach(point ->
                        createStroke(point, layer)
                                .forEach(this::drawPoint));
        lastPoint = pos;

    }

    @Override
    public void endDraw(Vector2D pos) {
        move(pos);
    }
}

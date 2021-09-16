package C7.Model.Tools;

import C7.Model.Color;
import C7.Model.Layer.ILayer;

/**
 * This factory is used for creating {@link ITool ITools}.
 * @author Hugo Ekstrand
 */
public class ToolFactory {

    /**
     * Creates a brush which draws a filled circle.
     * @param layer the layer which the brush will draw on
     * @param strokeSize the diameter of the circle
     * @param color the color of the circle
     * @return the created brush
     */
    public static ITool CreateCircularBrush(ILayer layer, int strokeSize, Color color){
        BaseBrush base = new BaseBrush(layer, new DiskDrawEffect(strokeSize, color));
        return base;
    }

    /**
     * Creates a calligraphy brush which draws a line with an angle. This can be a slanted angle, such as PI/4 or any
     * other angle.
     * @param layer the layer which the line will be drawn on
     * @param strokeSize the length of the line
     * @param color the color of the line
     * @param angle the slant of the line given in radians and counterclockwise
     * @return the created brush
     */
    public static ITool CreateCalligraphyBrush(ILayer layer, int strokeSize, Color color, double angle){
        BaseBrush base = new BaseBrush(layer, new CalligraphyDrawEffect(strokeSize, color, angle));
        return base;
    }

    /**
     * Creates a fill bucket which fills all pixels under a given threshold on a layer.
     * Any pixels with a maximum singular r, g, or b difference between the given fill under or on
     * the threshold will be filled while any other colors will be unaffected.
     * @param layer the layer which will be affected
     * @param fill the color of the fill
     * @param threshold the threshold of the fill effect
     * @return the created fill bucket tool
     */
    public static ITool CreateFillBucket(ILayer layer, Color fill, float threshold){
        return new FillBucket(layer, fill, threshold);
    }
}

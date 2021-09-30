package C7.Model.Tools;

import C7.Model.Color;
import C7.Model.Tools.Pattern.PatternFactory;
import C7.Model.Tools.StrokeInterpolation.LinearStrokeInterpolator;

/**
 * This factory is used for creating {@link ITool ITools}.
 * @author Hugo Ekstrand
 */
public class ToolFactory {

    /**
     * Creates a brush which draws a filled circle.
     * @param size the size of the brush head footprint by its radius.
     * @param color the color of the brush paint
     * @return the created brush
     */
    public static ITool CreateCircularBrush(int size, Color color){
        return new Brush(size, color, PatternFactory.createDiskPattern(), new LinearStrokeInterpolator());
    }

    /**
     * Creates a calligraphy brush which draws a line with an angle. This can be a slanted angle, such as PI/4 or any
     * other angle.
     * @param size the size of the brush head footprint by its radius.
     * @param color the color of the brush paint
     * @return the created brush
     */
    public static ITool CreateCalligraphyBrush(int size, Color color){
        return new Brush(size, color, PatternFactory.createLinePattern(), new LinearStrokeInterpolator());

    }

    /**
     * Creates a fill bucket which fills all pixels under a given threshold on a layer.
     * Any pixels with a maximum singular r, g, or b difference between the given fill under or on
     * the threshold will be filled while any other colors will be unaffected.
     * @param threshold the given threshold
     * @param fill the fill color of the bucket
     * @return the created fill bucket tool
     */
    public static ITool CreateFillBucket(float threshold, Color fill){
        return new FillBucket(threshold, fill);
    }
}

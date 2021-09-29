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
     * @param strokeSize the diameter of the circle
     * @param color the color of the circle
     * @return the created brush
     */
    public static ITool CreateCircularBrush(int strokeSize, Color color){
        return new Brush(color, strokeSize, PatternFactory.createDiskPattern(), new LinearStrokeInterpolator());
    }

    /**
     * Creates a calligraphy brush which draws a line with an angle. This can be a slanted angle, such as PI/4 or any
     * other angle.
     * @param strokeSize the length of the line
     * @param color the color of the line
     * @return the created brush
     */
    public static ITool CreateCalligraphyBrush(int strokeSize, Color color){
        return new Brush(color, strokeSize, PatternFactory.createLinePattern(), new LinearStrokeInterpolator());

    }

    /**
     * Creates a fill bucket which fills all pixels under a given threshold on a layer.
     * Any pixels with a maximum singular r, g, or b difference between the given fill under or on
     * the threshold will be filled while any other colors will be unaffected.
     * @param fill the color of the fill
     * @param threshold the threshold of the fill effect
     * @return the created fill bucket tool
     */
    public static ITool CreateFillBucket(Color fill, float threshold){
        return new FillBucket(fill, threshold);
    }
}

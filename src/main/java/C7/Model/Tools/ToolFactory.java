package C7.Model.Tools;

import C7.Model.Color;
import C7.Model.Tools.Pattern.PatternFactory;
import C7.Model.Tools.StrokeInterpolation.StrokeInterpolatorFactory;

/**
 * This factory is used for creating {@link ITool ITools}.
 * @author Hugo Ekstrand
 */
public class ToolFactory {

    /**
     * Creates a brush which draws a filled circle.
     * @return the created brush
     */
    public static ITool CreateCircularBrush(){
        return new Brush(PatternFactory.createDiskPattern(), StrokeInterpolatorFactory.createLinearInterpolator());
    }

    /**
     * Creates a calligraphy brush which draws a line with an angle. This can be a slanted angle, such as PI/4 or any
     * other angle.
     * @return the created brush
     */
    public static ITool CreateCalligraphyBrush(){
        return new Brush(PatternFactory.createLinePattern(), StrokeInterpolatorFactory.createLinearInterpolator());

    }

    /**
     * Creates a fill bucket which fills all pixels under a threshold on a layer.
     * Any pixels with a maximum singular r, g, or b difference between the given fill under or on
     * the threshold will be filled while any other colors will be unaffected.
     * @return the created fill bucket tool
     */
    public static ITool CreateFillBucket(){
        return new FillBucket();
    }
}

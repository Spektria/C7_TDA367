package C7.Model.Tools;

import C7.Util.Color;
import C7.Model.Tools.Pattern.PatternFactory;
import C7.Model.Tools.StrokeInterpolation.StrokeInterpolatorFactory;

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
    public static ITool createCircularBrush(int size, Color color){
        return new BlendBrush(size, color, PatternFactory.createDiskPattern(), StrokeInterpolatorFactory.createLinearInterpolator());
    }

    /**
     * Creates a calligraphy brush which draws a line with an angle. This can be a slanted angle, such as PI/4 or any
     * other angle.
     * @param size the size of the brush head footprint by its radius.
     * @param color the color of the brush paint
     * @return the created brush
     */
    public static ITool createCalligraphyBrush(int size, Color color){
        return new BlendBrush(size, color, PatternFactory.createLinePattern(), StrokeInterpolatorFactory.createLinearInterpolator());

    }

    /**
     * Creates a fill bucket which fills all pixels under a given threshold on a layer.
     * Any pixels with a maximum singular r, g, or b difference between the given fill under or on
     * the threshold will be filled while any other colors will be unaffected.
     * @param threshold the given threshold
     * @param fill the fill color of the bucket
     * @return the created fill bucket tool
     */
    public static ITool createFillBucket(float threshold, Color fill){
        return new FillBucket(threshold, fill);
    }

    /**
     * Creates a translation too. This tool moves layers around in 2d space.
     * @return the created translation tool
     */
    public static ITool createTranslationTool(){
        return new TranslationTool();
    }

    /**
     * Creates a rotation tool. This tool rotates a layer around its center.
     * @return the created rotation tool
     */
    public static ITool createRotationTool(){
        return new RotationTool();
    }

    /**
     * Creates a scaling tool. This tool scales a layer in its x- and y-axis.
     * @return the created scaling tool
     */
    public static ITool createScalingTool(){
        return new ScalingTool();
    }

    /**
     * Creates an eraser tool with a circular cross-section. This tool removes raster data from a layer and replaces it with a zero alpha color.
     * @param size the size of the erasers diameter
     * @return the eraser tool
     */
    public static ITool createEraserTool(int size){
        return new OverwriteBrush(size, new Color(0,0,0,0), PatternFactory.createDiskPattern(), StrokeInterpolatorFactory.createLinearInterpolator());
    }
}

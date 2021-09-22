package C7.Model.Tools;

import C7.Model.Color;
import C7.Model.Layer.ILayer;
import C7.Model.Tools.ToolProperties.IToolProperty;
import C7.Model.Tools.ToolProperties.ToolPropertyFactory;
import C7.Model.Vector.Vector2D;

import java.util.Arrays;
import java.util.Collection;

/**
 * Fills an area inside of a {@link ILayer layer} to a common color. The area is determined by
 * a given threshold; pixels in the layer which are similar enough to the given fill color are filled,
 * while the rest are not.
 * <p>
 * The threshold works by filling pixels which have a maximum r, g, or b delta smaller
 * than or equal to the threshold. For example, if the threshold is 0.5f and the fill is (1f,1f,1f) then
 * (0.49f,1f,1f) will not be filled but (0.51f,0.51f,0.51f) will be filled.
 * @author Hugo Ekstrand
 */
class FillBucket implements ITool{

    private float threshold;
    private Color fill;

    private Collection<IToolProperty> properties;

    /**
     * Creates a new instance of this class
     * @param fill the color of the fill
     * @param threshold the threshold of which pixels should be filled
     */
    FillBucket(Color fill, float threshold){
        this.fill = fill;
        this.threshold = threshold;

        properties = Arrays.asList(
                ToolPropertyFactory.createFloatProperty("Threshold",
                        "If the color distance of a color c from" +
                                " the fill of bucket is less than this threshold" +
                                " then c will be changed to the buckets fill.",
                        (f) -> this.threshold = f,
                        () -> this.threshold,
                        0f, 2f),

                ToolPropertyFactory.createColorProperty("Fill color",
                        "The fill color of the bucket",
                        (c) -> this.fill = c,
                        () -> this.fill)
        );
    }


    private void floodFill(int x, int y, ILayer surface, Color selectedColor) {
        // TODO: if performance proves to be bad, the flood fill methods should
        // TODO: 4 way recursion to a stack and span based flood fill. See https://en.wikipedia.org/wiki/Flood_fill

        if(!surface.isPixelOnLayer(x, y))
            return;

        if(!shouldFill(surface.getPixel(x, y), selectedColor))
            return;


        surface.setPixel(x, y, fill);

        floodFill(x + 1, y, surface, selectedColor);
        floodFill(x - 1, y, surface, selectedColor);
        floodFill(x, y + 1, surface, selectedColor);
        floodFill(x, y - 1, surface, selectedColor);
    }

    private boolean shouldFill(Color color, Color selectedColor){

        // If it is the same as the fill, it has already been filled.
        if(fill.equals(color))
            return false;

        // If the color is the same as the color that was first selected it should be filled.
        if(color.equals(selectedColor))
            return true;

        // Else if none of these, check how different the color is
        // and if the difference is low enough fill it.
        float delta = Color.getColorDifference(selectedColor, color);;
        if(delta == 0)
            return false;
        return delta <= threshold;

    }

    @Override
    public Collection<IToolProperty> getProperties() {
        return null;
    }

    @Override
    public void apply(ILayer layer, Vector2D v0, Vector2D v1) {
        int x = (int)v0.getX();
        int y = (int)v0.getY();
        if(layer.isPixelOnLayer(x,y))
            floodFill(x, y, layer, layer.getPixel(x, y));
    }

    @Override
    public boolean isContinuous() {
        return false;
    }
}

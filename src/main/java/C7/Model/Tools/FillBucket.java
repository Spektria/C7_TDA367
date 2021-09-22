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


    private void floodFill(int x, int y, ILayer surface) {
        // TODO: if performance proves to be bad, the flood fill methods should
        // TODO: 4 way recursion to a stack and span based flood fill. See https://en.wikipedia.org/wiki/Flood_fill

        if(!surface.isPixelOnLayer(x, y))
            return;

        if(!shouldFill(surface.getPixel(x, y)))
            return;


        surface.setPixel(x, y, fill);

        floodFill(x + 1, y, surface);
        floodFill(x - 1, y, surface);
        floodFill(x, y + 1, surface);
        floodFill(x, y - 1, surface);
    }

    private boolean shouldFill(Color color){
        if(color == null)
            return true;
        if(fill.equals(color))
            return false;

        float delta = Color.getColorDifference(fill, color);;
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
        floodFill((int)v0.getX(), (int)v1.getY(), layer);
    }

    @Override
    public boolean isContinuous() {
        return false;
    }
}

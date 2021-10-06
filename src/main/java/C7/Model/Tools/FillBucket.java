package C7.Model.Tools;

import C7.Model.Color;
import C7.Model.Layer.ILayer;
import C7.Model.Tools.ToolProperties.IToolProperty;
import C7.Model.Tools.ToolProperties.ToolPropertyFactory;
import C7.Util.Vector2D;

import java.util.*;

/**
 * Fills an area inside of a {@link ILayer layer} to a common color. The area is determined by
 * a given threshold; pixels in the layer which are similar enough to the given fill color are filled,
 * while the rest are not.
 * @author Hugo Ekstrand
 */
class FillBucket implements ITool{

    private float threshold;        // The threshold decided if an adjacent pixel should be "filled".
    private Color fill;

    private final Collection<IToolProperty> properties;

    /**
     * Creates a new instance of this class
     * @param fill the color of the fill
     * @param threshold the threshold of which pixels should be filled
     */
    FillBucket(float threshold, Color fill){
        Objects.requireNonNull(fill);

        this.threshold = threshold;
        this.fill = fill;

        properties = Arrays.asList(
                ToolPropertyFactory.createDoubleProperty("Threshold",
                        "If the color distance of a color c from" +
                                " the fill of bucket is less than this threshold" +
                                " then c will be changed to the buckets fill.",
                        (d) -> this.threshold = d.floatValue(),
                        () -> (double)this.threshold,
                        0f, 2f),

                ToolPropertyFactory.createColorProperty("Fill color",
                        "The fill color of the bucket",
                        (c) -> this.fill = c,
                        () -> this.fill)
        );
    }


    private void floodFill(int inX, int inY, ILayer surface, Color selectedColor) {
        // The algorithm scanline flood fill uses can be found here: https://lodev.org/cgtutor/floodfill.html
        // Though do note that it has been modified to handle more than binary values.

        // No need to fill. The selected spot is already the correct color.
        if(fill.equals(selectedColor))
            return;


        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(inX);
        stack.push(inY);

        while(!stack.isEmpty()){
            int y = stack.pop();
            int x = stack.pop();

            // Do a scan on a horizontal line so that we find the first x which should not be filled.
            while(x >= 0 && shouldFill(surface.getLocalPixel(x, y), selectedColor)) x--;
            x++;

            while(x < surface.getWidth() && shouldFill(surface.getLocalPixel(x,y), selectedColor)){

                // Fill pixel
                surface.setLocalPixel(x, y, fill);

                // Check if the y - 1 line should be filled, if it has not already been checked.
                if(y > 0 && shouldFill(surface.getLocalPixel(x, y-1), selectedColor)){

                    // If it should be filled, push another coordinate in the stack
                    stack.push(x);
                    stack.push(y - 1);
                }

                // Do the same for below as for above. Except with y + 1.
                if(y < surface.getHeight() - 1 && shouldFill(surface.getLocalPixel(x, y+1), selectedColor)){
                    stack.push(x);
                    stack.push(y+1);
                }

                // Next pixel in the line.
                x++;
            }
        }

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
        float differance = Color.getColorDifference(selectedColor, color);;
        return differance <= threshold;

    }

    @Override
    public Collection<IToolProperty> getProperties() {
        return properties;
    }

    @Override
    public void apply(ILayer layer, Vector2D v0, Vector2D v1) {

        // Make sure the points is on the given layer.
        if(layer.isPointOnLayer(v0)){

            // If it is convert to local layer space.
            Vector2D localVec = layer.getPixelPositionAtPoint(v0);
            int x = (int)localVec.getX();
            int y = (int)localVec.getY();

            // Perform flood fill
            floodFill(x, y, layer, layer.getLocalPixel(x, y));

            layer.update();
        }

    }

    @Override
    public boolean isContinuous() {
        return false; // This tool only requires one click.
    }

    @Override
    public void setToDefault() {
        properties.forEach(IToolProperty::setToDefault);
    }
}

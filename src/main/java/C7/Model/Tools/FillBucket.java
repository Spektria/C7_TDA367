package C7.Model.Tools;

import C7.Model.Color;
import C7.Model.Layer.ILayer;
import C7.Model.Tools.ToolProperties.IToolProperty;
import C7.Model.Tools.ToolProperties.ToolPropertyFactory;
import C7.Model.Vector.Vector2D;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;

/**
 * Fills an area inside of a {@link ILayer layer} to a common color. The area is determined by
 * a given threshold; pixels in the layer which are similar enough to the given fill color are filled,
 * while the rest are not.
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
        // The algorithm scanline flood fill uses can be found here: https://lodev.org/cgtutor/floodfill.html
        // Though do note that it has been modified to handle more than binary values.

        // No need to fill. The selected spot is already the correct color.
        if(fill.equals(selectedColor))
            return;


        Deque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[]{ x, y});

        while(!stack.isEmpty()){
            int[] coords = stack.pop();
            x = coords[0];
            y = coords[1];
            int x1 = x;

            // Do a scan on a horizontal line so that we find the first x which should not be filled.
            while(x1 >= 0 && shouldFill(surface.getPixel(x1, y), selectedColor)) x1--;
            x1++;

            boolean above = false;
            boolean below = false;


            while(x1 < surface.getWidth() && shouldFill(surface.getPixel(x1,y), selectedColor)){

                // Fill pixel
                surface.setPixel(x1, y, fill);

                // Check if the y - 1 line should be filled, if it has not already been checked.
                if(!above && y > 0 && shouldFill(surface.getPixel(x1, y-1), selectedColor)){

                    // If it should be filled, push another coordinate in the stack
                    stack.push(new int[]{x1, y-1});

                    // We have now checked, so we mustn't again.
                    above = true;
                }
                // Check that we should still fill above.
                else if(above && y > 0 && surface.getPixel(x1, y-1).equals(fill)){
                    above = false;
                }

                // Do the same for below as for above. Except with y + 1.
                if(!below && y < surface.getHeight() - 1 && shouldFill(surface.getPixel(x1, y+1), selectedColor)){
                    stack.push(new int[]{ x1, y+1 });
                    below = true;
                }
                else if(below && y < surface.getHeight() - 1 && surface.getPixel(x1, y+1).equals(fill)){
                    below = false;
                }

                // Next pixel in the line.
                x1++;
            }
        }

    }

    private boolean shouldFill(Color color, Color selectedColor){

        if(color == null)
            return true;

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
        return properties;
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

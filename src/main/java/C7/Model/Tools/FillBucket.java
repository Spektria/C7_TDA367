package C7.Model.Tools;

import C7.Model.Color;
import C7.Model.Layer.ILayer;
import C7.Model.Vector.Vector2D;

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

    private final float threshold;
    private final Color fill;
    private final ILayer layer;

    /**
     * Creates a new instance of this class
     * @param layer the layer which will be affected
     * @param fill the color of the fill
     * @param threshold the threshold of which pixels should be filled
     */
    FillBucket(ILayer layer, Color fill, float threshold){
        this.layer = layer;
        this.fill = fill;
        this.threshold = threshold;
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

    private static float getBiggestRGBDelta(Color c1, Color c2){
        // Get the largest delta of the two rgb value multiplied with its alpha.

        return Math.max(
                Math.max(
                    Math.abs(c1.getBlue() - c2.getBlue()),
                    Math.abs(c1.getRed()- c2.getRed())
                ),
                Math.abs(c1.getGreen() - c2.getGreen())
        );
    }

    private boolean shouldFill(Color color){
        if(color == null)
            return true;
        if(fill.equals(color))
            return false;

        float biggestDelta = getBiggestRGBDelta(color, fill);
        if(biggestDelta == 0)
            return false;
        return biggestDelta <= threshold;

    }

    @Override
    public void beginDraw(Vector2D pos) {
        floodFill((int)pos.getX(), (int)pos.getY(), layer);
    }

    @Override
    public void move(Vector2D pos) {
        beginDraw(pos);
    }

    @Override
    public void endDraw(Vector2D pos) {

    }
}

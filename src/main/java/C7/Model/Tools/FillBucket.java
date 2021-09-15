package C7.Model.Tools;

import C7.Color;
import C7.ILayer;
import C7.Model.Vector.Vector2D;

public class FillBucket implements ITool{

    private final int threshold;
    private final Color fill;
    private final ILayer layer;

    FillBucket(ILayer layer, Color fill, int threshold){
        this.layer = layer;
        this.fill = fill;
        this.threshold = threshold;
    }


    private void floodFill(int x, int y, ILayer surface) {
        // TODO: if performance proves to be bad, the flood fill methods should
        // TODO: 4 way recursion to a stack and span based flood fill. See https://en.wikipedia.org/wiki/Flood_fill

        if(!surface.isInBounds(x, y))
            return;

        if(!shouldFill(surface.getPixel(x, y)))
            return;


        surface.setPixel(x, y, fill);

        floodFill(x + 1, y, surface);
        floodFill(x - 1, y, surface);
        floodFill(x, y + 1, surface);
        floodFill(x, y - 1, surface);
    }

    private static int getBiggestRGBDelta(Color c1, Color c2){
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

        int biggestDelta = getBiggestRGBDelta(color, fill);
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

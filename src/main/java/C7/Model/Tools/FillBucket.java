package C7.Model.Tools;

import C7.Color;
import C7.ISurface;

public class FillBucket implements ITool{

    private final int threshold;
    private final Color fill;

    FillBucket(Color fill, int threshold){
        this.fill = fill;
        this.threshold = threshold;
    }

    @Override
    public void draw(int x, int y, ISurface surface) {
        // TODO: if performance proves to be bad, the flood fill methods should
        // TODO: 4 way recursion to a stack and span based flood fill. See https://en.wikipedia.org/wiki/Flood_fill

        if(!isInBounds(x, y, 0, 0, surface.getWidth() - 1, surface.getHeight() - 1))
            return;

        if(!shouldFill(surface.getPixel(x, y)))
            return;


        surface.setPixel(x, y, fill);

        draw(x + 1, y, surface);
        draw(x - 1, y, surface);
        draw(x, y + 1, surface);
        draw(x, y - 1, surface);
    }

    private static boolean isInBounds(int px, int py, int x, int y, int width, int height){
        return px >= x && py >= y && px <= x + width && py <= y + height;
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
}

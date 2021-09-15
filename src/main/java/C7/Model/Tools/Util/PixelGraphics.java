package C7.Model.Tools.Util;

import java.util.function.BiConsumer;

/**
 * This class is used for converting 2d geometrical objects into a pixel format.
 * Disks, lines, and other 2d geometric shapes can be approximated by this class.
 */
public final class PixelGraphics {

    /**
     * Performs an action at every pixel of a pixel-approximated line between the points (x0, y0) and (x1, y1).
     * @param x0 start x coordinate of the line
     * @param y0 start y coordinate of the line
     * @param x1 end x coordinate of the line
     * @param y1 end y coordinate of the line
     * @param doOnLinePixel the action to be performed at every pixel in the approximated line.
     */
    public static void pixelLine(int x0, int y0, int x1, int y1, BiConsumer<Integer, Integer> doOnLinePixel){

        // Algorithm used can be found here.
        // https://en.wikipedia.org/wiki/Digital_differential_analyzer_(graphics_algorithm)#Program

        double dx = (x1 - x0);
        double dy = (y1 - y0);

        double steps = Math.abs(dx) > Math.abs(dy) ? dx : dy;
        steps = Math.abs(steps);

        dx/=steps;
        dy/=steps;

        double x = x0;
        double y = y0;

        // For every step, progress another delta and draw a pixel at the nearest integer value
        // of the current point.
        for (int i = 0; i <= steps; i++) {
            doOnLinePixel.accept((int)x, (int)y);
            x += dx;
            y += dy;
        }
    }

    /**
     * Performs an action at every pixel of a pixel-approximated ellipse with the center (x0, y0).
     * @param x0 the center point's x coordinate
     * @param y0 the center point's y coordinate
     * @param semiMajor the semi-major axis length of the ellipse
     * @param semiMinor the semi-minor axis length of the ellipse
     * @param doOnEveryPixel the action to be performed at evey pixel in teh approximated line.
     */
    public static void pixelDisk(int x0, int y0, double semiMajor,
                                 double semiMinor,
                                 BiConsumer<Integer, Integer> doOnEveryPixel){

        // TODO: make it so that the ellipse can be rotated.

        final double xRadius = semiMajor;
        final double yRadius = semiMinor;


        for (double x = -Math.abs(xRadius) ; x <= Math.abs(xRadius); x++) {
            for (double y = -Math.abs(yRadius); y <= Math.abs(yRadius); y++){
                if(x * x * yRadius * yRadius + y * y * xRadius * xRadius <= yRadius * yRadius * xRadius * xRadius)
                    doOnEveryPixel.accept(x0 + (int)x, y0 + (int)y);
            }
        }
    }

}

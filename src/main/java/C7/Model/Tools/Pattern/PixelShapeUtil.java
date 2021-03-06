package C7.Model.Tools.Pattern;

import java.util.function.BiConsumer;

/**
 * This class is used for converting 2d geometrical objects into a pixel format.
 * Disks, lines, and other 2d geometric shapes can be approximated by this class.
 *
 * @author Hugo Ekstrand
 */
final class PixelShapeUtil {

    /**
     * Performs an action at every pixel of a pixel-approximated line between the points (x0, y0) and (x1, y1).
     * @param x0 start x coordinate of the line
     * @param y0 start y coordinate of the line
     * @param x1 end x coordinate of the line
     * @param y1 end y coordinate of the line
     * @param doOnLinePixel the action to be performed at every pixel in the approximated line.
     */
    static void pixelLine(int x0, int y0, int x1, int y1, BiConsumer<Integer, Integer> doOnLinePixel){

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
    static void pixelDisk(int x0, int y0, double semiMajor,
                                 double semiMinor, double rotation,
                                 BiConsumer<Integer, Integer> doOnEveryPixel){

        final double sin = Math.sin(rotation);
        final double cos = Math.cos(rotation);

        // formula for non-axis aligned bounds of a rotated ellipse:
        //https://math.stackexchange.com/questions/91132/how-to-get-the-limits-of-rotated-ellipse

        final double halfWidth = Math.sqrt(Math.pow(semiMajor * cos, 2) + Math.pow(semiMinor * sin, 2));
        final double halfHeight = Math.sqrt(Math.pow(semiMajor * sin, 2) + Math.pow(semiMinor * cos, 2));

        for (double x = -Math.abs(halfWidth); x <= Math.abs(halfWidth); x++) {
            for (double y = -Math.abs(halfHeight); y <= Math.abs(halfHeight); y++){

                if(Math.pow((x * cos + y * sin) / semiMajor, 2)
                                + Math.pow((x * sin - y * cos)/semiMinor, 2) <= 1)
                    doOnEveryPixel.accept(x0 + (int)x, y0 + (int)y);
            }
        }
    }

}

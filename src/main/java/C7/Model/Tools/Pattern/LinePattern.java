package C7.Model.Tools.Pattern;

import C7.Model.Vector.Vector2D;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A Pattern which represents a straight line.
 * @author Hugo Ekstrand
 */
class LinePattern implements IPattern {

    @Override
    public Collection<Vector2D> getPoints(int size, Vector2D scale, double rotation) {

        // Represents a line from (0,0) to (endx, endy), which is half of (startx, starty) to (0,0).
        // However, since we want the line centered in (0,0) we need its extreme points which will be
        // the lines radius in x and y, that is, endx = -startx and endy = -starty.
        final double xRadius = (scale.getX() * size - 1)/2d;
        final double yRadius = (scale.getY() * size - 1)/2d;

        // Rotate the coordinates
        final double dx = Math.cos(rotation) * xRadius;
        final double dy = Math.sin(rotation) * yRadius;

        ArrayList<Vector2D> pixels = new ArrayList<>();
        PixelShapeUtil.pixelLine((int)Math.floor(dx), (int)Math.floor(dy), (int)-Math.ceil(dx), (int)-Math.ceil(dy), (ix, iy)
                -> pixels.add(new Vector2D(ix, iy)));

        return pixels;
    }
}

package C7.Model.Tools;

import C7.Model.Color;
import C7.Model.Layer.ILayer;
import C7.Model.Tools.Util.PixelGraphics;
import C7.Model.Vector.Vector2D;

import java.util.Map;

/**
 * Creates a set of pixels in a circular disk shape.
 * @author Hugo Ekstrand
 */
class DiskDrawEffect implements IBrushEffect {

    private final int strokeSize;
    private final Color color;

    /**
     * Creates a new instance of this class
     * @param strokeSize the diameter of the disk
     * @param color the color of the pixels of the disk
     */
    DiskDrawEffect(int strokeSize, Color color){
        this.strokeSize = strokeSize;
        this.color = color;
    }

    @Override
    public Map<Vector2D, Color> handle(ILayer surface, Vector2D center, Map<Vector2D, Color> pixels) {

        // We're excluding the center point from the radius.
        final double xRadius = (surface.getScale().getX() * strokeSize - 1) /2d;
        final double yRadius = (surface.getScale().getY() * strokeSize - 1) /2d;
        final int xRadiusRounded = (int)Math.round(xRadius);
        final int yRadiusRounded = (int)Math.round(yRadius);

        // draw center point
        pixels.put(center, color);

        // draw disk surrounding center point // TODO: Scaling
        PixelGraphics.pixelDisk((int)center.getX(), (int)center.getY(), xRadius, yRadius, (ix, iy)
                -> pixels.put(new Vector2D(ix, iy), color));

        return pixels;
    }
}

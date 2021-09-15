package C7.Model.Tools;

import C7.Color;
import C7.ILayer;
import C7.Model.Tools.Util.PixelGraphics;
import C7.Model.Vector.Vector2D;

import java.util.Map;

public class CalligraphyDrawEffect implements IBrushEffect {

    private final double angle;
    private final int strokeSize;
    private final Color color;

    CalligraphyDrawEffect(int strokeSize, Color color, double angle) {
        this.angle = angle;
        this.strokeSize = strokeSize;
        this.color = color;
    }


    @Override
    public Map<Vector2D, Color> handle(ILayer surface, Vector2D center, Map<Vector2D, Color> pixels) {

        final double xRadius = (surface.getScale().getX() * strokeSize - 1)/2d;
        final double yRadius = (surface.getScale().getY() * strokeSize - 1)/2d;

        final double dx = Math.cos(angle) * xRadius;
        final double dy = Math.sin(angle) * yRadius;

        final int topX = (int)(center.getX() + dx);
        final int topY = (int)(center.getY() + dy);
        final int bottomX = (int)(center.getX() - dx);
        final int bottomY = (int)(center.getY() - dy);

        PixelGraphics.pixelLine(bottomX, bottomY, topX, topY, (ix, iy)
                -> pixels.put(new Vector2D(ix, iy), color));

        return pixels;
    }
}

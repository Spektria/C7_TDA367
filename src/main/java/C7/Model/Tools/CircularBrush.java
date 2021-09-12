package C7.Model.Tools;

import C7.Color;
import C7.ISurface;
import C7.Model.Utils.PixelGraphics;

/**
 * This {@link Brush} draws a filled circle around, and including, the draw point.
 * The circle may be of 1 pixel or larger.
 */
class CircularBrush extends Brush {

    protected CircularBrush(int strokeSize, Color color) {
        super(strokeSize, color);
    }

    @Override
    public void draw(int x, int y, ISurface surface) {

        // We're excluding the center point from the radius.
        final double radius = (strokeSize - 1) / 2d;
        final int radiusRounded = (int)Math.round(radius);

        // draw center point
        surface.setPixel(x, y, color);

        // draw disk surrounding center point
        PixelGraphics.pixelDisk(x, y, radiusRounded, (xc, yc) -> {
            if(xc >= 0 && xc < surface.getWidth()
            && yc >= 0 && yc < surface.getHeight())
                surface.setPixel(xc, yc, color);
        });
    }
}

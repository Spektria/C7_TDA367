package C7.Model.Tools;

import C7.Color;
import C7.ISurface;
import C7.Model.Utils.PixelGraphics;

/**
 * This tool draws a line with a given slope relative to the drawing surface.
 * E.g. it may draw a line similar to "////////" if its slope is pi/4 and the brush is drawn
 * in a horizontal line.
 */
class Calligraphy extends Brush {

    private final double angle;

    protected Calligraphy(int strokeSize, Color color, double angle) {
        super(strokeSize, color);
        this.angle = angle;
    }

    @Override
    public void draw(int x, int y, ISurface surface) {
        final double radius = (strokeSize - 1)/2d;

        final double dx = Math.cos(angle) * radius;
        final double dy = Math.sin(angle) * radius;

        final int topX = (int)(x + dx);
        final int topY = (int)(y + dy);
        final int bottomX = (int)(x - dx);
        final int bottomY = (int)(y - dy);

        PixelGraphics.pixelLine(bottomX, bottomY, topX, topY, (ix, iy) -> {
            if(ix >= 0 && ix < surface.getWidth()
                && iy >= 0 && iy < surface.getHeight()){
                surface.setPixel(ix, iy, color);
            }
        });
    }

}

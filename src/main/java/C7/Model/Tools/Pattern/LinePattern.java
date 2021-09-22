package C7.Model.Tools.Pattern;

import C7.Model.Tools.Util.PixelGraphics;
import C7.Model.Vector.Vector2D;

import java.util.ArrayList;
import java.util.Collection;

class LinePattern implements IPatternStrategy {

    @Override
    public Collection<Vector2D> getPoints(int size, Vector2D scale, double rotation) {
        final double xRadius = (scale.getX() * size - 1)/2d;
        final double yRadius = (scale.getY() * size - 1)/2d;

        final double dx = Math.cos(rotation) * xRadius;
        final double dy = Math.sin(rotation) * yRadius;

        final int topX = (int)(scale.getX() + dx);
        final int topY = (int)(scale.getY() + dy);
        final int bottomX = (int)(scale.getX() - dx);
        final int bottomY = (int)(scale.getY() - dy);

        ArrayList<Vector2D> pixels = new ArrayList<>();
        PixelGraphics.pixelLine(bottomX, bottomY, topX, topY, (ix, iy)
                -> pixels.add(new Vector2D(ix, iy)));

        return pixels;
    }
}

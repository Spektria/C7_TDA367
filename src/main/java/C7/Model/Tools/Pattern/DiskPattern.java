package C7.Model.Tools.Pattern;

import C7.Model.Tools.Util.PixelGraphics;
import C7.Model.Vector.Vector2D;

import java.util.ArrayList;
import java.util.Collection;

class DiskPattern implements IPatternStrategy {

    @Override
    public Collection<Vector2D> getPoints(int size, Vector2D scale, double rotation) {
        ArrayList<Vector2D> points = new ArrayList<>();

        // draw center point if size is just 1
        if(size == 1){
            points.add(new Vector2D(0,0));
            return points;
        }


            // We're excluding the center point from the radius.
        final double xRadius = (scale.getX() * size - 1) /2d;
        final double yRadius = (scale.getY() * size - 1) /2d;


        PixelGraphics.pixelDisk(0,0,
                xRadius,
                yRadius,
                rotation, (x, y) -> points.add(new Vector2D(x, y)));
        return points;
    }
}

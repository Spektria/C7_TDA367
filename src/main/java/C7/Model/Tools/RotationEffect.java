package C7.Model.Tools;

import C7.Color;
import C7.Layer.ILayer;
import C7.Model.Vector.Vector2D;
import java.util.HashMap;
import java.util.Map;

/**
 * Rotates the pixels given to this IBrushEffect by a given amount.
 * @author Hugo Ekstrand
 */
class RotationEffect implements IBrushEffect {

    private final double degrees;

    /**
     * Creates a new instance of this class
     * @param degrees the rotation in radians counterclockwise.
     */
    RotationEffect(double degrees){
        this.degrees = degrees;
    }

    @Override
    public Map<Vector2D, Color> handle(ILayer surface, Vector2D center, Map<Vector2D, Color> pixels) {
        Map<Vector2D, Color> rotatedPixels = new HashMap<>(pixels.size());

        for(var point : pixels.keySet()){
            rotatedPixels.put(point.rotatedAround(center, this.degrees), pixels.get(point));
        }

        return rotatedPixels;
    }
}

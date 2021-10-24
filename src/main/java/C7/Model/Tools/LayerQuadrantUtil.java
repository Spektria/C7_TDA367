package C7.Model.Tools;

import C7.Model.Layer.ILayer;
import C7.Util.Vector2D;

final class LayerQuadrantUtil {
    /**
     * Returns a given points position relative to a given layer's global quadrants.
     * That is, the layer is considered as if it is not rotated.
     * The quadrant is represented as a vector so that
     * the vectors listed are in the order of the quadrants:
     * [1,1], [-1,1], [-1,-1], [-1,-1].
     * The output is guaranteed to be one of these vectors.
     * @param layer the given layer
     * @param position the given point
     * @return the quadrant the point is in.
     */
    static Vector2D getGlobalQuadrantPosition(ILayer layer, Vector2D position){
        Vector2D center = layer.getPosition().add(layer.getLocalCenterPoint());
        return new Vector2D(
                center.getX() < position.getX() ? 1 : -1,
                center.getY() < position.getY() ? 1 : -1
        );
    }
}

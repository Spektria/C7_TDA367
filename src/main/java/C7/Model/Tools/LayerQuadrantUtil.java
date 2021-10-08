package C7.Model.Tools;

import C7.Model.Layer.ILayer;
import C7.Util.Vector2D;

final class LayerQuadrantUtil {
    static Vector2D getQuadrantAsVector(ILayer layer, Vector2D position){
        Vector2D center = layer.getPosition().add(new Vector2D(layer.getWidth()/2d, layer.getHeight()/2d));
        return new Vector2D(
                center.getX() < position.getX() ? 1 : -1,
                center.getY() < position.getY() ? 1 : -1
        );
    }
}

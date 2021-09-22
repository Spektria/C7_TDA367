package C7.Model.Layer;

import C7.Model.Vector.Vector2D;

/**
 * <code>ILayerManager</code> is an interface for a collection of ordered layers in a picture.
 * Layer managers are responsible for creating, destroying, and providing access
 * to layers inside of an image project.
 * @author Love Gustafsson
 * @version 1.0
 */
public interface ILayerManager {

    /**
     * Creates a new layer.
     * @param width     Width of the new layer.
     * @param height    Height of the new layer.
     * @param position  Picture-space position of the new layer.
     * @param rotation  Rotation angle of the new layer, in radians.
     * @param scale     Scale of the new layer.
     * @return A new layer.
     */
    ILayer createLayer(int width, int height, Vector2D position, double rotation, Vector2D scale);

    /**
     * Destroys the specified layer and removes it from this layer manager. If
     * the layer does not exist in this layer manager, it will not be destroyed.
     * @param layer The layer to destroy.
     */
    void destroyLayer(ILayer layer);
}

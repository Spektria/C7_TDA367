package C7.Model.Layer;

import C7.Model.Vector.Vector2D;

/**
 * <code>ILayerManager</code> is an interface for a collection of ordered layers in a picture.
 * Layer managers are responsible for creating, destroying, and providing access
 * to layers inside of an image project.
 * @author Love Gustafsson
 * @version 1.1
 */
public interface ILayerManager {

    /**
     * Creates a new layer, stores it in this layer manager, and returns its ID.
     * @param width     Width of the new layer.
     * @param height    Height of the new layer.
     * @param position  Picture-space position of the new layer.
     * @param rotation  Rotation angle of the new layer, in radians.
     * @param scale     Scale of the new layer.
     * @return The ID of the created layer.
     */
    int createLayer(int width, int height, Vector2D position, double rotation, Vector2D scale);

    /**
     * Adds an existing layer to this layer manager, and returns its new layer ID.
     * If the layer already exists in this manager, the existing ID for it is
     * returned.
     * @param layer     The layer to add.
     * @return The ID of the added layer.
     */
    int addLayer(ILayer layer);

    /**
     * Destroys the specified layer and removes it from this layer manager. If
     * the layer does not exist in this layer manager, it will not be destroyed.
     * @param id The ID of the layer to destroy.
     */
    void destroyLayer(int id);

    /**
     * Gets the ID of the currently active layer.
     * @return Current active layer ID.
     */
    int getActiveLayerId();

    /**
     * Sets which layer is currently active. The ID specified must be the ID of
     * a layer that is managed by this LayerManager. If the ID is not a valid
     * layer, the active layer will not change.
     */
    void setActiveLayer(int id);

    /**
     * Gets the layer object for the specified ID.
     * @param id The ID of the layer to get.
     * @return The layer object associated with the specified ID.
     */
    ILayer getLayer(int id);

    /**
     * Gets all layer IDs currently contained in this layer manager.
     * @return Array containing layer IDs.
     */
    int[] getAllLayerIds();
}

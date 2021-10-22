package C7.Model.Layer;

import C7.Util.Color;
import C7.Util.IObservable;
import C7.Util.Tuple2;
import C7.Util.Vector2D;

/**
 * <code>ILayerManager</code> is an interface for a collection of ordered layers in a picture.
 * Layer managers are responsible for creating, destroying, and providing access
 * to layers inside of an image project.
 * @author Love Gustafsson
 * @version 1.3
 */
public interface ILayerManager extends IObservable<Tuple2<Vector2D, Vector2D>> {

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

    /**
     * Gets the color of the pixel at the specified position by blending all of
     * this layer manager's layers in order.
     * @param x X position of pixel in project-space.
     * @param y Y position of pixel in project-space.
     * @return The color of the pixel at the specified position.
     */
    Color getPixel(int x, int y, Color a);

    /**
     * Sets the index position of the specified layer. If the index is greater
     * or equal to the total number of layers, the layer is placed in the last
     * position. If the index is less than or equal to 0, the layer is placed
     * in the first position. The layer that was on the index originally will
     * be moved to the next index.
     * @param id The id of the layer to set the position of.
     * @param index The new index of the layer.
     */
    void setLayerIndex(int id, int index);

    /**
     * Sets the visibility of the specified layer.
     * @param id The ID number of the layer to set the visibility of.
     * @param visible Whether or not the layer should be visible.
     */
    void setLayerVisibility(int id, boolean visible);

    /**
     * Gets the visibility of the specified layer.
     * @param id  The ID number of the layer to get the visibility of.
     * @return Whether or not the layer is visible.
     */
    boolean getLayerVisibility(int id);
}

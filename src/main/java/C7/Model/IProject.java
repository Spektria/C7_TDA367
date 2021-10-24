package C7.Model;

import C7.Model.Layer.ILayer;
import C7.Model.Tools.ITool;
import C7.Util.Color;
import C7.Util.IObservable;
import C7.Util.Tuple2;
import C7.Util.Vector2D;

/**
 * IProject is an interface representing a complete project.
 * Included is all image data required to reconstruct previous work as well as any saved metadata.
 */
public interface IProject extends IObservable<Tuple2<Vector2D, Vector2D>> {

    /**
     * Sets the name of this project. The name must be a non-null String which is not blank or empty.
     * @throws IllegalArgumentException if the name is null or an empty or blank string.
     * @param name the name to be set to.
     */
    void setName(String name);

    /**
     * Returns this project's name
     * @return this project's name.
     */
    String getName();

    /**
     * Gets the projects' window's width
     * @return The width of the window
     */
    int getWidth();

    /**
     * Gets the projects' window's height
     * @return The height of the window
     */
    int getHeight();

    //LAYERMANAGER
    /**
     * Apply an {@link ITool} to the active layer with the specified start and stop coordinates.
     * @param tool The ITool to apply
     * @param v0 The start coordinates
     * @param v1 The end coordinates
     */
    void applyTool(ITool tool, Vector2D v0, Vector2D v1);
    /**
     * Apply an {@link ITool} to the specified layer with the specified start and stop coordinates.
     * @param tool The ITool to apply
     * @param v0 The start coordinates
     * @param v1 The end coordinates
     * @param layerID The layer to apply to
     */
    void applyTool(ITool tool, Vector2D v0, Vector2D v1, int layerID);

    /**
     * Renders project starting in x,y with the specified width and height.
     * @param x The upper left corner's x coordinate.
     * @param y The upper left corner's y coordinate.
     * @param width Width of the region.
     * @param height Height of the region.
     * @return Color matrix of the rendered region.
     * If position is out of scope of the Project the returned pixels are black.
     */
    Color[][] renderProject(int x, int y, int width, int height);

    /**
     * Renders only a specified layer
     * @param layerID The layer to render
     * @return Color matrix of the rendered region.
     * If position is out of scope of the layer the returned pixels are black.
     * If the provided layer does not exist returns null.
     */
    Color[][] renderLayer(int layerID);

    //LAYERMANAGER PASSTHROUGH
    /**
     * Adds new layer to the Project with the specified size, and position.
     * @param width     Width of the new layer.
     * @param height    Height of the new layer.
     * @param position  Picture-space position of the new layer.
     * @return The ID of the created layer.
     */
    int createLayer(int width, int height, Vector2D position);
    /**
     * Adds new layer to the Project with the specified size, position, rotation, and scale.
     * @param width     Width of the new layer.
     * @param height    Height of the new layer.
     * @param position  Picture-space position of the new layer.
     * @param rotation  Rotation angle of the new layer, in radians.
     * @param scale     Scale of the new layer.
     * @return The ID of the created layer.
     */
    int createLayer(int width, int height, Vector2D position, double rotation, Vector2D scale);
    /**
     * Adds the layer to the Project.
     * @return The ID of the added layer.
     */
    int addLayer(ILayer layer);

    int[] getAllLayerIds();

    /**
     * Gets the layer object for the specified ID.
     * @param id The ID of the layer to get.
     * @return The layer object associated with the specified ID.
     */
    ILayer getLayer(int id);

    /**
     * Removes the specified layer.
     * If the layer does not exist in this Project nothing will be done.
     * @param layerID
     */
    void removeLayer(int layerID);

    /**
     * Get the active layerID of this Project.
     * @return The active layerID
     */
    int getActiveLayerID();

    /**
     * Get the active {@link ILayer} of this Project.
     * @return The project's current active ILayer according to what Layer is set as active
     */
    ILayer getActiveLayer();

    /**
     * Sets which layer is currently active. The ID specified must be the ID of
     * a layer that is managed by this LayerManager. If the ID is not a valid
     * layer, the active layer will not change.
     */
    void setActiveLayer(int id);

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

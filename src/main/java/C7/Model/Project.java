package C7.Model;

import C7.Model.Layer.ILayer;
import C7.Model.Layer.ILayerManager;
import C7.Model.Layer.Layer;
import C7.Model.Tools.ITool;
import C7.Model.Vector.Vector2D;
import javafx.scene.image.PixelWriter;

/**
 * Project is a class representing a complete project.
 * Included is all image data required to reconstruct previous work as well as any saved metadata.
 * */
public class Project {
    ILayerManager layerManager;
    ILayer activeLayer;
    int width, height;

    /**
     * Create a Project with the specified size drawing area.
     * @param width Width of Project drawing area
     * @param height Height of Project drawing area
     */
    public Project(int width, int height){
        if (width < 1 || height < 1)
            throw new IllegalArgumentException("Width & height can not be less than 1 in a project");
        else{
            this.width = width;
            this.height = height;
        }

        //UH OH NO LAYERMANAGER AROUND
        layerManager = null;
    }

    //LAYERMANAGER
    /**
     * Apply an {@link ITool} to the active layer with the specified start and stop coordinates.
     * @param tool The ITool to apply
     * @param v0 The start coordinates
     * @param v1 The end coordinates
     */
    public void applyTool(ITool tool, Vector2D v0, Vector2D v1){
        tool.apply(activeLayer, v0, v1);
        int layerID = layerManager.getActiveLayerId();
        ILayer activeLayer = layerManager.getLayer(layerID);
        tool.apply(activeLayer, v0, v1);
    }
    /**
     * Apply an {@link ITool} to the specified layer with the specified start and stop coordinates.
     * @param tool The ITool to apply
     * @param v0 The start coordinates
     * @param v1 The end coordinates
     * @param layerID The layer to apply to
     */
    public void applyTool(ITool tool, Vector2D v0, Vector2D v1, int layerID){
        ILayer selectedLayer = layerManager.getLayer(layerID);
        tool.apply(selectedLayer, v0, v1);
    }

    /**
     * Renders specified layer in Project with provided {@link PixelWriter}.
     * @param pWriter The PixelWriter to render the layer with
     * @param layerID The layer to render
     */
    public void renderLayer(PixelWriter pWriter, int layerID){
        throw new UnsupportedOperationException();
    }

    //LAYERMANAGER PASSTHROUGH
    /**
     * Adds new layer to the Project with the specified size, and position.
     * @param width     Width of the new layer.
     * @param height    Height of the new layer.
     * @param position  Picture-space position of the new layer.
     * @return The ID of the created layer.
     */
    public int addLayer(int width, int height, Vector2D position){
        return layerManager.createLayer(width, height, position, 0, new Vector2D(1,1));
    }
    /**
     * Adds new layer to the Project with the specified size, position, rotation, and scale.
     * @param width     Width of the new layer.
     * @param height    Height of the new layer.
     * @param position  Picture-space position of the new layer.
     * @param rotation  Rotation angle of the new layer, in radians.
     * @param scale     Scale of the new layer.
     * @return The ID of the created layer.
     */
    public int addLayer(int width, int height, Vector2D position, double rotation, Vector2D scale){
        return layerManager.createLayer(width, height, position, rotation, scale);
    }
    /**
     * Adds the layer to the Project.
     * @return The ID of the added layer.
     */
    public int addLayer(ILayer layer){
        //layerManager.createLayer(layer);
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the specified layer.
     * If the layer does not exist in this Project nothing will be done.
     * @param layerID
     */
    public void removeLayer(int layerID){ layerManager.destroyLayer(layerID); }

    /**
     * Get the active layerID of this Project.
     * @return The active layerID
     */
    public int activeLayer() { return layerManager.getActiveLayerId(); }
}

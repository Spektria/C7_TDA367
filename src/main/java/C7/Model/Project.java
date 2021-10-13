package C7.Model;

import C7.Model.Layer.ILayer;
import C7.Model.Layer.ILayerManager;
import C7.Model.Layer.LayerManager;
import C7.Model.Tools.ITool;
import C7.Util.Color;
import C7.Util.Tuple2;
import C7.Util.Vector2D;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Project is a class representing a complete project.
 * Included is all image data required to reconstruct previous work as well as any saved metadata.
 * */
class Project implements IProject, IObserver<Tuple2<Vector2D, Vector2D>>, Serializable {
    final private Collection<IObserver<Tuple2<Vector2D, Vector2D>>> observers;	// Update area observers
    private ILayerManager layerManager;
    private ILayer activeLayer;
    private int width, height;
    private String name;

    /**
     * Create a Project with the specified size drawing area.
     * @param width Width of Project drawing area
     * @param height Height of Project drawing area
     * @param name The name of Project.
     */
    public Project(String name, int width, int height){
        if (width < 1 || height < 1)
            throw new IllegalArgumentException("Width & height can not be less than 1 in a project");
        else{
            this.width = width;
            this.height = height;
        }

        setName(name);

        //Specific LayerManager
        layerManager = new LayerManager();

        //Observe
        observers = new ArrayList<>();
        layerManager.addObserver(this);
    }

    @Override
    public void setName(String name) {
        Objects.requireNonNull(name);
        if(name.isEmpty() || name.isBlank())
            throw new IllegalArgumentException("Name of project must be a non-null String which is not blank or empty.");
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Gets the projects' window's width
     * @return The width of the window
     */
    @Override
    public int getWidth(){ return  width; }

    /**
     * Gets the projects' window's height
     * @return The height of the window
     */
    @Override
    public int getHeight(){ return height; }

    //LAYERMANAGER
    /**
     * Apply an {@link ITool} to the active layer with the specified start and stop coordinates.
     * @param tool The ITool to apply
     * @param v0 The start coordinates
     * @param v1 The end coordinates
     */
    @Override
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
    @Override
    public void applyTool(ITool tool, Vector2D v0, Vector2D v1, int layerID){
        ILayer selectedLayer = layerManager.getLayer(layerID);
        tool.apply(selectedLayer, v0, v1);
    }

    /**
     * Renders project starting in x,y with the specified width and height.
     * @param x The upper left corner's x coordinate.
     * @param y The upper left corner's y coordinate.
     * @param width Width of the region.
     * @param height Height of the region.
     * @return Color matrix of the rendered region.
     * If position is out of scope of the Project the returned pixels are black.
     */
    @Override
    public Color[][] renderProject(int x, int y, int width, int height){
        //No negative width/height
        if (width < 0 || height < 0)
            throw new IllegalArgumentException("Unable to render Project with negative resolution");

        Color[][] colorMatrix = new Color[width][height];

        for (int xOffset = 0; xOffset < width; xOffset++) {
            for (int yOffset = 0; yOffset < height; yOffset++) {
                colorMatrix[xOffset][yOffset] = layerManager.getPixel(x + xOffset, y + yOffset);
            }
        }

        return colorMatrix;
    }

    /**
     * Renders only a specified layer
     * @param layerID The layer to render
     * @return Color matrix of the rendered region.
     * If position is out of scope of the layer the returned pixels are black.
     * If the provided layer does not exist returns null.
     */
    @Override
    public Color[][] renderLayer(int layerID){
        ILayer layer = layerManager.getLayer(layerID);
        if (layer == null) return null;

        Color[][] colorMatrix = new Color[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Vector2D pointToGet = new Vector2D(x,y);
                //Check if out of bounds
                if (layer.isGlobalPointOnLayer(pointToGet)) {
                    colorMatrix[x][y] = layer.getLocalPixel(x,y);
                }
                //Inside of bounds
                else{
                    colorMatrix[x][y] = new Color(0,0,0,0);
                }
            }
        }

        return colorMatrix;
    }

    //LAYERMANAGER PASSTHROUGH
    /**
     * Adds new layer to the Project with the specified size, and position.
     * @param width     Width of the new layer.
     * @param height    Height of the new layer.
     * @param position  Picture-space position of the new layer.
     * @return The ID of the created layer.
     */
    @Override
    public int createLayer(int width, int height, Vector2D position){
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
    @Override
    public int createLayer(int width, int height, Vector2D position, double rotation, Vector2D scale){
        return layerManager.createLayer(width, height, position, rotation, scale);
    }
    /**
     * Adds the layer to the Project.
     * @return The ID of the added layer.
     */
    @Override
    public int addLayer(ILayer layer){
        int newLayerID = layerManager.addLayer(layer);
        return newLayerID;
    }
    @Override
    public int[] getAllLayerIds() {
        return layerManager.getAllLayerIds();
    }

    /**
     * Gets the layer object for the specified ID.
     * @param id The ID of the layer to get.
     * @return The layer object associated with the specified ID.
     */
    @Override
    public ILayer getLayer(int id){ return layerManager.getLayer(id); }

    /**
     * Removes the specified layer.
     * If the layer does not exist in this Project nothing will be done.
     * @param layerID
     */
    @Override
    public void removeLayer(int layerID){ layerManager.destroyLayer(layerID); }

    /**
     * Get the active layerID of this Project.
     * @return The active layerID
     */
    @Override
    public int getActiveLayerID() { return layerManager.getActiveLayerId(); }

    /**
     * Get the active {@link ILayer} of this Project.
     * @return The project's current active ILayer according to what Layer is set as active
     */
    @Override
    public ILayer getActiveLayer() { return layerManager.getLayer(layerManager.getActiveLayerId()); }

    /**
     * Sets which layer is currently active. The ID specified must be the ID of
     * a layer that is managed by this LayerManager. If the ID is not a valid
     * layer, the active layer will not change.
     */
    @Override
    public void setActiveLayer(int id){
        layerManager.setActiveLayer(id);
        activeLayer = layerManager.getLayer(id);
    }

    @Override
    public void addObserver(IObserver<Tuple2<Vector2D, Vector2D>> observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver<Tuple2<Vector2D, Vector2D>> observer) {
        observers.remove(observer);
    }

    @Override
    public void notify(Tuple2<Vector2D, Vector2D> data) {
        for (IObserver<Tuple2<Vector2D, Vector2D>> observer : observers){
            observer.notify(data);
        }
    }
}

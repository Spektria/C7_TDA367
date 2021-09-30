package C7.Model;

import C7.Model.Layer.ILayer;
import C7.Model.Layer.ILayerManager;
import C7.Model.Layer.Layer;
import C7.Model.Tools.ITool;
import C7.Model.Vector.Vector2D;
import javafx.scene.image.PixelWriter;

public class Project {
    ILayerManager layerManager;
    ILayer activeLayer;
    int width, height;

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
    public void applyTool(ITool tool, Vector2D v0, Vector2D v1){
        tool.apply(activeLayer, v0, v1);
        int layerID = layerManager.getActiveLayerId();
        ILayer activeLayer = layerManager.getLayer(layerID);
        tool.apply(activeLayer, v0, v1);
    }
    public void applyTool(ITool tool, Vector2D v0, Vector2D v1, int layerID){
        ILayer selectedLayer = layerManager.getLayer(layerID);
        tool.apply(selectedLayer, v0, v1);
    }

    public ILayer renderProject(PixelWriter pWriter){
        throw new UnsupportedOperationException();
    }

    //LAYERMANAGER PASSTHROUGH
    public void addLayer(int width, int height, Vector2D position){
        layerManager.createLayer(width, height, position, 0, new Vector2D(1,1));
    }
    public void addLayer(int width, int height, Vector2D position, double rotation, Vector2D scale){
        layerManager.createLayer(width, height, position, rotation, scale);
    }
    public void addLayer(ILayer layer){
        //layerManager.createLayer(layer);
        throw new UnsupportedOperationException();
    }

    public void removeLayer(int layer){ layerManager.destroyLayer(layer); }

    public int activeLayer() { return layerManager.getActiveLayerId(); }
}

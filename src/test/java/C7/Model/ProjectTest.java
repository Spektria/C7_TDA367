package C7.Model;

import C7.IO.LayerIO;
import C7.IO.ResourceIO;
import C7.Model.Layer.ILayer;
import C7.Model.Layer.Layer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProjectTest {
    @Test
    public void addAndRemoveLayers(){
        Project proj = new Project(10,10);

        ILayer createdLayer = new Layer(10,10, new Color(0,0,0,0));
        ILayer retrievedLayer;

        //Add layer and check if retrieving returns same layer
        int createdLayerID = proj.addLayer(createdLayer);

        retrievedLayer = proj.getLayer(createdLayerID);
        Assertions.assertSame(createdLayer, retrievedLayer);

        //Remove layer and check if retrieving returns null
        proj.removeLayer(createdLayerID);

        retrievedLayer = proj.getLayer(createdLayerID);
        Assertions.assertNull(retrievedLayer);
    }

    @Test
    public void changeLayer(){
        Project proj = new Project(10,10);

        ILayer createdLayer1 = new Layer(10,10, new Color(0,0,0,0));
        ILayer createdLayer2 = new Layer(10,10, new Color(0,0,0,0));

        int createdLayer1ID = proj.addLayer(createdLayer1);
        int createdLayer2ID = proj.addLayer(createdLayer2);

        //Set active layer to 1 and check if it is active
        proj.setActiveLayer(createdLayer1ID);

        Assertions.assertSame(createdLayer1, proj.getActiveLayer());

        //Set active layer to 2 and check if it is active
        proj.setActiveLayer(createdLayer2ID);

        Assertions.assertSame(createdLayer2, proj.getActiveLayer());
    }

    @Test
    public void renderProject(){
        Project proj = new Project(15,15);
        ILayer testLayer = LayerIO.layerFromFile(ResourceIO.getGlobalResource("redsquares.png").getPath());

        proj.addLayer(testLayer);

        Color[][] render = proj.renderProject(0,0, proj.getWidth(), proj.getHeight());

        //Colors of layer the right color
        Assertions.assertEquals(render[0][0], render[3][2]);
        Assertions.assertNotEquals(render[0][0], render[2][0]);
        //Colors outside of layer right color
        Assertions.assertEquals(new Color(0,0,0,0), render[5][5]);
    }

    @Test
    public void createAndPaintLayers(){
        /*Project proj = new Project(10,10);
        int layerID = proj.createLayer(10,10, new Vector2D(0,0));
        ITool tool = ToolFactory.CreateFillBucket();
        proj.setActiveLayer(layerID);
        proj.applyTool(ToolFactory.CreateFillBucket(), new Vector2D(0,0), new Vector2D(0,0));
    */}
}

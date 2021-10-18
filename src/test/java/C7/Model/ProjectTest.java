package C7.Model;

import C7.Services.ServiceFactory;
import C7.Util.ResourceIO;
import C7.Model.Layer.ILayer;
import C7.Model.Layer.Layer;
import C7.Model.Tools.ITool;
import C7.Model.Tools.ToolFactory;
import C7.Util.Color;
import C7.Util.Vector2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProjectTest {
    @Test
    public void addAndRemoveLayers(){
        Project proj = new Project("tst",10,10);

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
        Project proj = new Project("tst",10,10);

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
        Project proj = new Project("tst",15,15);
        ServiceFactory.createLayerImportService(ResourceIO.getGlobalResource("redsquares.png").getPath(), proj::addLayer).execute();

        Color[][] render = proj.renderProject(0,0, proj.getWidth(), proj.getHeight());

        //Colors of layer the right color
        Assertions.assertEquals(render[0][0], render[3][2]);
        Assertions.assertNotEquals(render[0][0], render[2][0]);
        //Colors outside of layer right color
        Assertions.assertEquals(new Color(0,0,0,0), render[5][5]);
    }

    @Test
    public void createAndRenderPaintedLayers(){
        Color[][] layer1Render;
        Color[][] layer2Render;

        Project proj = new Project("tst",10,10);

        //Create layers with different constructors
        int layer1ID = proj.createLayer(10,10, new Vector2D(0,0));
        int layer2ID = proj.createLayer(10,10,new Vector2D(1,1), 0.1, new Vector2D(1,2));

        //Are their variables different?
        Assertions.assertNotEquals(
                proj.getLayer(layer1ID).getPosition(),
                proj.getLayer(layer2ID).getPosition());
        Assertions.assertNotEquals(
                proj.getLayer(layer1ID).getRotation(),
                proj.getLayer(layer2ID).getRotation());
        Assertions.assertNotEquals(
                proj.getLayer(layer1ID).getScale(),
                proj.getLayer(layer2ID).getScale());

        //Paint layers with blue color
        ITool tool = ToolFactory.CreateFillBucket( 0.2f, new Color(0, 0, 1, 1));
        //Paint layer 1 by implicitly using active layer
        proj.setActiveLayer(layer1ID);
        proj.applyTool(tool, new Vector2D(5,5), new Vector2D(5,5));
        //Paint layer 2 by explicitly stating ID
        proj.applyTool(tool, new Vector2D(5,5), new Vector2D(5,5), layer2ID);


        //They should be different because of different rotation/scale/position
        layer1Render = proj.renderLayer(layer1ID);
        layer2Render = proj.renderLayer(layer2ID);
        Assertions.assertFalse(colorMatricesEqual(layer1Render, layer2Render));

        //Reset position, rotation, and scale and rerender. They should now be the same.
        proj.getLayer(layer2ID).setPosition(new Vector2D(0,0));
        proj.getLayer(layer2ID).setRotation(0);
        proj.getLayer(layer2ID).setScale(new Vector2D(1,1));


        layer2Render = proj.renderLayer(layer2ID);
        Assertions.assertTrue(colorMatricesEqual(layer1Render, layer2Render));
    }

    private boolean colorMatricesEqual(Color[][] array1, Color[][] array2){
        for (int x = 0; x < array1.length; x++) {
            for (int y = 0; y < array1[0].length; y++) {
                //If they are not the same return false
                if (array1[x][y].equals(array2[x][y]) == false) return false;
            }
        }

        //If we didn't return false before, they are the same.
        return true;
    }
}

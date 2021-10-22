package C7.Model;

import C7.Model.Layer.LayerFactory;
import C7.Services.ServiceFactory;
import C7.Util.Bitmap;
import C7.Util.ResourceIO;
import C7.Model.Layer.ILayer;
import C7.Model.Tools.ITool;
import C7.Model.Tools.ToolFactory;
import C7.Util.Color;
import C7.Util.Vector2D;
import C7.View.Render.RenderAdapterFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProjectTest {
    @Test
    public void addAndRemoveLayers(){
        Project proj = new Project("tst",10,10);

        ILayer createdLayer = LayerFactory.createDefaultLayer(10, 10, new Color(0, 0, 0, 0), new Vector2D(0, 0), 0, new Vector2D(1, 1));
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

        ILayer createdLayer1 = LayerFactory.createDefaultLayer(10, 10, new Color(0, 0, 0, 0), new Vector2D(0, 0), 0, new Vector2D(1, 1));
        ILayer createdLayer2 = LayerFactory.createDefaultLayer(10, 10, new Color(0, 0, 0, 0), new Vector2D(0, 0), 0, new Vector2D(1, 1));

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

        Bitmap render = proj.renderProject(0,0, proj.getWidth(), proj.getHeight());

        Color a = new Color(0,0,0,0);
        Color b = new Color(0,0,0,0);
        //Colors of layer the right color
        Assertions.assertEquals(render.getColor(a, 0,0), render.getColor(b, 3,2));
        Assertions.assertNotEquals(render.getColor(a, 0,0), render.getColor(b, 2,0));
        //Colors outside of layer right color
        Assertions.assertEquals(new Color(0,0,0,0), render.getColor(a, 5,5));
    }

    @Test
    public void createAndRenderPaintedLayers(){
        Bitmap layer1Render;
        Bitmap layer2Render;

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
        ITool tool = ToolFactory.createFillBucket( 0.2f, new Color(0, 0, 1, 1));
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

    @Test
    public void stressTest(){
        Project proj = new Project("tst",1000,1000);
        for (int i = 0; i < 1000; i++) {
            proj.renderProject(0, 0, proj.getWidth(), proj.getHeight());
        }
    }

    private boolean colorMatricesEqual(Bitmap array1, Bitmap array2){
        Color a = new Color(0,0,0,0);
        Color b = new Color(0,0,0,0);
        for (int x = 0; x < array1.getWidth(); x++) {
            for (int y = 0; y < array1.getHeight(); y++) {
                //If they are not the same return false
                if (array1.getColor(a, x, y).equals(array2.getColor(b,x,y)) == false) return false;
            }
        }

        //If we didn't return false before, they are the same.
        return true;
    }
}

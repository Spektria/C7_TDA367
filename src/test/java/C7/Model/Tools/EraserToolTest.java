package C7.Model.Tools;

import C7.Model.Layer.ILayer;
import C7.Util.Color;
import C7.Util.Vector2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EraserToolTest {

    @Test
    public void eraseTest(){
        TestISurfaceImpl surface = new TestISurfaceImpl(20, 20, new Vector2D(1,1));

        // Draw the whole area with red
        ITool circularTool = ToolFactory.createCircularBrush(100, Color.RED);
        circularTool.apply(surface, new Vector2D(20, 20), new Vector2D(50, 50));

        // CHeck its red
        assertLayerIsFullOf(surface, Color.RED);

        // Try to erase it
        ITool eraseTool = ToolFactory.createEraserTool(100);
        eraseTool.apply(surface, new Vector2D(20, 20), new Vector2D(50, 50));

        // Check its gone
        assertLayerIsFullOf(surface, new Color(0,0,0,0));
    }

    private void assertLayerIsFullOf(ILayer layer, Color expected){
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                Assertions.assertEquals(expected, layer.getLocalPixel(x,y, new Color(0,0,0,0)));
            }
        }
    }

}

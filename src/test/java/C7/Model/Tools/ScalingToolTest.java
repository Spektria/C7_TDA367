package C7.Model.Tools;

import C7.Model.Layer.LayerFactory;
import C7.Util.Color;
import C7.Model.Layer.ILayer;
import C7.Util.Vector2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ScalingToolTest {

    private ITool scalingTool;

    @BeforeEach
    public void init(){
        scalingTool = ToolFactory.createScalingTool();
    }

    @Test
    public void translateTest(){
        ILayer layer = LayerFactory.createDefaultLayer(10, 10, new Color(1,1,1,1));

        // Scale by 1/2
        Vector2D corner = new Vector2D(layer.getWidth(), layer.getHeight());
        Vector2D center = layer.getLocalCenterPoint();
        scalingTool.apply(layer, center.add(corner.mult(1/2d)), center);

        Assertions.assertEquals(new Vector2D(1/2d, 1/2d), layer.getScale());
    }

    @Test
    public void testIsContinuous(){
        // Check that default is true
        Assertions.assertTrue(scalingTool.isContinuous());
    }

    @Test
    public void resetPropertyTest(){
        // Set continuous prop to false
        scalingTool.getProperties().stream().filter(prop ->
                        prop.getName().equals("Continuous scaling"))
                .findFirst().get().setBoolean(false);
        Assertions.assertFalse(scalingTool.isContinuous());
        scalingTool.setToDefault();
        Assertions.assertTrue(scalingTool.isContinuous());
    }

}

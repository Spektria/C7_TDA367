package C7.Model.Tools;

import C7.Model.Color;
import C7.Model.Layer.ILayer;
import C7.Model.Layer.Layer;
import C7.Util.Vector2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RotationToolTest {


    private ITool rotationTool;

    @BeforeEach
    public void init(){
        rotationTool = ToolFactory.createRotationTool();
    }

    @Test
    public void rotationTest(){
        ILayer layer = new Layer(10, 10, new Color(1,1,1,1));

        Vector2D center = layer.getLocalCenterPoint();

        // Rotate by 45/2deg
        rotationTool.apply(layer, center.add(new Vector2D(1, 0)), center.add(new Vector2D(Math.cos(Math.PI / 4d), Math.sin((Math.PI / 4d)))));

        Assertions.assertEquals(Math.PI / 8d, layer.getRotation());

        // Rotate by 45/2deg
        rotationTool.apply(layer, center.add(new Vector2D(0, 1)), center.add(new Vector2D(Math.cos(Math.PI / 2d + Math.PI / 4d), Math.sin((Math.PI / 2d + Math.PI / 4d)))));

        Assertions.assertEquals(Math.PI / 4d, layer.getRotation());
    }

    @Test
    public void testIsContinuous(){
        // Check that default is true
        Assertions.assertTrue(rotationTool.isContinuous());
    }

    @Test
    public void resetPropertyTest(){
        // Set continuous prop to false
        rotationTool.getProperties().stream().filter(prop ->
                        prop.getName().equals("Continuous rotation"))
                .findFirst().get().setBoolean(false);
        Assertions.assertFalse(rotationTool.isContinuous());
        rotationTool.setToDefault();
        Assertions.assertTrue(rotationTool.isContinuous());
    }


}

package C7.Model.Tools;

import C7.Model.Layer.LayerFactory;
import C7.Util.Color;
import C7.Model.Layer.ILayer;
import C7.Util.Vector2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Hugo Ekstrand
 */
public class TranslationToolTest {

    private ITool translationTool;

    @BeforeEach
    public void init(){
        translationTool = ToolFactory.createTranslationTool();
    }

    @Test
    public void translateTest(){
        ILayer layer = LayerFactory.createDefaultLayer(10, 10, new Color(1,1,1,1));

        // Move by (15, 20)
        translationTool.apply(layer, new Vector2D(0, 0), new Vector2D(15, 20));

        Assertions.assertEquals(new Vector2D(15, 20), layer.getPosition());

        // Move by (10, 10)
        translationTool.apply(layer, new Vector2D(10, 10), new Vector2D(20,20));

        Assertions.assertEquals(new Vector2D(25, 30), layer.getPosition());
    }

    @Test
    public void testIsContinuous(){
        // Check that default is true
        Assertions.assertTrue(translationTool.isContinuous());
    }

    @Test
    public void resetPropertyTest(){
        // Set continuous prop to false
        translationTool.getProperties().stream().filter(prop ->
                        prop.getName().equals("Continuous translation"))
                .findFirst().get().setBoolean(false);
        Assertions.assertFalse(translationTool.isContinuous());
        translationTool.setToDefault();
        Assertions.assertTrue(translationTool.isContinuous());
    }


}

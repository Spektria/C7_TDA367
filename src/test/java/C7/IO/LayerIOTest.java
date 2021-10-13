package C7.IO;

import C7.Util.Color;
import C7.Model.Layer.Layer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;

public class LayerIOTest {

    URL imagePath = ResourceIO.getPackageResource(this, "redsquares.png");
    Layer importedLayer;


    @BeforeEach
    public void importTest(){
        importedLayer = LayerIO.layerFromFile(imagePath.getPath());
        Assertions.assertNotEquals(null, importedLayer);
    }

    @Test
    public void correctOrder() {
        Color[] colors = new Color[4];
        colors[0] = importedLayer.getLocalPixel(0,0);
        colors[1] = importedLayer.getLocalPixel(2,0);
        colors[2] = importedLayer.getLocalPixel(1,2);
        colors[3] = importedLayer.getLocalPixel(3,2);

        Assertions.assertTrue(colors[0].equals(colors[2]));
        Assertions.assertTrue(colors[0].equals(colors[3]));
        Assertions.assertFalse(colors[0].equals(colors[1]));
    }
}

package C7.IO;

import C7.Model.Color;
import C7.Model.Layer.Layer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;

public class LayerIOTest {

    URL imagePath = ResourceIO.getPackageResource(this, "redsquares.png");
    Layer importedLayer;


    @Test
    @Before
    public void importTest(){
        importedLayer = LayerIO.layerFromFile(imagePath.getPath());
        Assert.assertNotEquals(null, importedLayer);
    }

    @Test
    public void correctOrder() {
        Color[] colors = new Color[4];
        colors[0] = importedLayer.getPixel(0,0);
        colors[1] = importedLayer.getPixel(2,0);
        colors[2] = importedLayer.getPixel(1,2);
        colors[3] = importedLayer.getPixel(3,2);

        Assert.assertTrue(colors[0].equals(colors[2]));
        Assert.assertTrue(colors[0].equals(colors[3]));
        Assert.assertFalse(colors[0].equals(colors[1]));
    }
}

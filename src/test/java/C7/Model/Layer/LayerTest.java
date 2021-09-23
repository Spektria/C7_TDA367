package C7.Model.Layer;

import C7.Model.Color;
import org.junit.Assert;
import org.junit.Test;
import C7.Model.Layer.*;

public class LayerTest {

    @Test
    public void sizeTest() {
        ILayer layer = new Layer(200, 100, new Color(0, 0, 0, 1));

        Assert.assertEquals(200, layer.getWidth());
        Assert.assertEquals(100, layer.getHeight());
    }

    @Test
    public void defaultColorTest() {
        ILayer layer = new Layer(200, 100, new Color(0, 0, 0, 1));

        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                Assert.assertEquals(layer.getPixel(x, y), new Color(0, 0, 0, 1));
            }
        }
    }

    @Test
    public void setWidthTest() {
        ILayer layer = new Layer(200, 100, new Color(0, 0, 0, 1));

        layer.setWidth(400);

        Assert.assertEquals(400, layer.getWidth());
    }

    @Test
    public void setHeightTest() {
        ILayer layer = new Layer(200, 100, new Color(0, 0, 0, 1));

        layer.setHeight(400);

        Assert.assertEquals(400, layer.getHeight());
    }

    @Test
    public void setPixelTest() {
        ILayer layer = new Layer(200, 100, new Color(0, 0, 0, 1));

        layer.setPixel(0, 0, new Color(1, 0, 0, 1));

        Assert.assertEquals(new Color(1, 0, 0, 1), layer.getPixel(0, 0));
    }

    @Test
    public void equalsSelfTest() {
        ILayer layer = new Layer(200, 100, new Color(0, 0, 0, 1));

        Assert.assertEquals(layer, layer);
    }

    @Test
    public void equalsTest() {
        ILayer layer = new Layer(2, 2, new Color(0, 0, 0, 1));

        Assert.assertEquals(new Layer(2, 2, new Color(0, 0, 0, 1)), layer);
    }
}

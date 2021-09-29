package C7.Model.Layer;

import C7.Model.Color;
import C7.Model.Layer.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LayerTest {

    @Test
    public void sizeTest() {
        ILayer layer = new Layer(200, 100, new Color(0, 0, 0, 1));

        Assertions.assertEquals(200, layer.getWidth());
        Assertions.assertEquals(100, layer.getHeight());
    }

    @Test
    public void defaultColorTest() {
        ILayer layer = new Layer(200, 100, new Color(0, 0, 0, 1));

        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                Assertions.assertEquals(layer.getPixel(x, y), new Color(0, 0, 0, 1));
            }
        }
    }

    @Test
    public void setWidthTest() {
        ILayer layer = new Layer(200, 100, new Color(0, 0, 0, 1));

        layer.setWidth(400);

        Assertions.assertEquals(400, layer.getWidth());
    }

    @Test
    public void setHeightTest() {
        ILayer layer = new Layer(200, 100, new Color(0, 0, 0, 1));

        layer.setHeight(400);

        Assertions.assertEquals(400, layer.getHeight());
    }

    @Test
    public void setPixelTest() {
        ILayer layer = new Layer(200, 100, new Color(0, 0, 0, 1));

        layer.setPixel(0, 0, new Color(1, 0, 0, 1));

        Assertions.assertEquals(new Color(1, 0, 0, 1), layer.getPixel(0, 0));
    }

    @Test
    public void equalsSelfTest() {
        ILayer layer = new Layer(200, 100, new Color(0, 0, 0, 1));

        Assertions.assertEquals(layer, layer);
    }

    @Test
    public void equalsTest() {
        ILayer layer = new Layer(2, 2, new Color(0, 0, 0, 1));

        Assertions.assertEquals(new Layer(2, 2, new Color(0, 0, 0, 1)), layer);
    }
}

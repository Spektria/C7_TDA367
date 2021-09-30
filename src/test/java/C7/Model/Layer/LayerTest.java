package C7.Model.Layer;

import C7.Model.Color;
import C7.Model.Vector.Vector2D;
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
                Assertions.assertEquals(layer.getGlobalPixel(x, y), new Color(0, 0, 0, 1));
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

        layer.setGlobalPixel(0, 0, new Color(1, 0, 0, 1));

        Assertions.assertEquals(new Color(1, 0, 0, 1), layer.getGlobalPixel(0, 0));
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

    @Test
    public void isPointOnTranslatedAndRotatedLayerTest(){
        ILayer layer = new Layer(100, 100, new Color(0,0,0,1));

        layer.setPosition(new Vector2D(100,100));
        layer.setRotation(Math.PI/4); // Rotated 45 degrees

        Assertions.assertTrue(layer.isPointOnLayer(new Vector2D(150, 150))); // Center of layer
        Assertions.assertFalse(layer.isPointOnLayer(new Vector2D(101, 101))); // Edge of layer corner, if it were not rotated.
        Assertions.assertTrue(layer.isPointOnLayer(new Vector2D(110, 150))); // Edge of layer corner when rotated
    }

    @Test
    public void getPixelAtPointForRotatedAndTranslatedLayerTest(){
        int w = 20;
        int h = 20;
        Vector2D translation = new Vector2D(20, 20);
        ILayer layer = new Layer(w, h, new Color(0,0,0,1));


        layer.setPosition(translation);
        layer.setRotation(Math.PI/4); // Rotated 45 degrees

        // This should be the center
        var center = new Vector2D(w / 2d, h / 2d).add(translation);
        Assertions.assertEquals(new Vector2D(w / 2d, h / 2d), layer.getPixelPositionAtPoint(center));

        // This should be the rightmost edge. Which, if rotated 45 degrees, will be, if rotated back, the bottom right corner.
        var rightCorner = new Vector2D(w / 2d, h / 2d).add(translation).add(new Vector2D(w / 2d, 0).mult(Math.sqrt(2)));
        Assertions.assertEquals(new Vector2D(w, 0), layer.getPixelPositionAtPoint(rightCorner));
    }

    @Test
    public void getGlobalPixel(){
        int w = 30;
        int h = 30;
        Vector2D translation = new Vector2D(30, 30);
        ILayer layer = new Layer(w, h, new Color(0,0,0,1));

        layer.setLocalPixel(0, h - 1, new Color(1,1,1,1)); // Top left
        layer.setLocalPixel(w/2,h/2, new Color(1,0,1,1)); // Center

        layer.setPosition(translation);
        layer.setRotation(Math.PI/4); // Rotated 45 degrees

        // This should be the center
        var center = new Vector2D(w / 2d, h / 2d).add(translation);
        Assertions.assertEquals(new Color(1,0,1,1), layer.getGlobalPixel((int)center.getX(), (int)center.getY()));

        // This should be the rightmost edge. Which, if rotated 45 degrees, will be, if rotated back, the bottom right corner.
        var leftCorner = new Vector2D(w / 2d, h / 2d).add(translation).add(new Vector2D(-w / 2d, 0).mult(Math.sqrt(2) - 0.1d));
        Assertions.assertEquals(new Color(1,1,1,1), layer.getGlobalPixel((int)leftCorner.getX(), (int)leftCorner.getY()));
    }


    // TODO: scaling
    /*@Test
    public void getPixelAtPointForScaledAndTranslatedAndRotatedLayerTest(){

        int w = 14;
        int h = 14;
        ILayer layer = new Layer(w, h, new Color(1,1,1,1));

        Vector2D translation = new Vector2D(4, 7);
        double rotation = Math.PI / 4d;
        Vector2D scale = new Vector2D(1, 2); // Stretched in the y direction by 2

        layer.setRotation(rotation);
        layer.setScale(scale);
        layer.setPosition(translation);


        for (int i = 0; i < h * 2; i++) {
            for (int j = 0; j < w * 2; j++) {
                if(j == w/2d + translation.getX() && i == h/2d + translation.getY()){
                    System.out.print("@");
                }
                else if(layer.isPointOnLayer(new Vector2D(j, i))){
                    System.out.print("#");
                }
                else System.out.print("-");
                System.out.print(" ");
            }
            System.out.println();
        }

        // This should be the center point;
        Vector2D center = new Vector2D(w / 2d, h / 2d).add(translation);
        Assertions.assertEquals(new Vector2D(w/2d, h/2d), layer.getPixelPositionAtPoint(center));

        // This should be the right corner
        Vector2D rightCorner = new Vector2D(w / 2d, h / 2d).add(new Vector2D((w / 2d) * Math.cos(rotation), (h / 2d) * Math.cos(rotation)));
        Assertions.assertEquals(new Vector2D(w, 0), layer.getPixelPositionAtPoint(rightCorner));

    }*/

}

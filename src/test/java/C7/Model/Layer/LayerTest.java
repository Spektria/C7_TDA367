package C7.Model.Layer;

import C7.Util.Color;
import C7.Util.IObserver;
import C7.Util.Tuple2;
import C7.Util.Vector2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

public class LayerTest {

    @Test
    public void sizeTest() {
        ILayer layer = new Layer(200, 100, new Color(0, 0, 0, 1));

        Assertions.assertEquals(200, layer.getWidth());
        Assertions.assertEquals(100, layer.getHeight());
    }

    @Test
    public void invalidSizeConstructorTest(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Layer(-1, -1, new Color(0, 0, 0, 1)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Layer(-1, 5, new Color(0, 0, 0, 1)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Layer(5, -1, new Color(0, 0, 0, 1)));
    }

    @Test
    public void invalidColorConstructorTest(){
        Assertions.assertThrows(NullPointerException.class, () -> new Layer(5, 5, null));
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
        Assertions.assertThrows(IllegalArgumentException.class, () -> layer.setWidth(-1));
    }

    @Test
    public void setHeightTest() {
        ILayer layer = new Layer(200, 100, new Color(0, 0, 0, 1));

        layer.setHeight(400);

        Assertions.assertEquals(400, layer.getHeight());
        Assertions.assertThrows(IllegalArgumentException.class, () -> layer.setHeight(-1));
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
        Assertions.assertNotEquals(layer, new Object());
        Assertions.assertNotEquals(new Layer(3,2, new Color(0, 0, 0, 1)), layer);
        Assertions.assertNotEquals(new Layer(2,2, new Color(0, 0, 0.1f, 1)), layer);
    }

    @Test
    public void setPixelOutOfBounds(){
        ILayer layer = new Layer(2, 2, new Color(0, 0, 0, 1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> layer.setGlobalPixel(3,1, new Color(0,0,0,0)));
        layer.setPosition(new Vector2D(5,5));
        Assertions.assertThrows(IllegalArgumentException.class, () -> layer.setGlobalPixel(2,2, new Color(0,0,0,0)));

    }

    @Test
    public void isPointOnTranslatedAndRotatedLayerTest(){
        ILayer layer = new Layer(100, 100, new Color(0,0,0,1));

        layer.setPosition(new Vector2D(100,100));
        layer.setRotation(Math.PI/4); // Rotated 45 degrees

        Assertions.assertTrue(layer.isGlobalPointOnLayer(new Vector2D(150, 150))); // Center of layer
        Assertions.assertFalse(layer.isGlobalPointOnLayer(new Vector2D(101, 101))); // Edge of layer corner, if it were not rotated.
        Assertions.assertTrue(layer.isGlobalPointOnLayer(new Vector2D(110, 150))); // Edge of layer corner when rotated
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
        Assertions.assertEquals(new Vector2D(w / 2d, h / 2d), layer.toLocalPixel(center));

        // This should be the rightmost edge. Which, if rotated 45 degrees, will be, if rotated back, the bottom right corner.
        var rightCorner = new Vector2D(w / 2d, h / 2d).add(translation).add(new Vector2D(w / 2d, 0).mult(Math.sqrt(2)));
        Assertions.assertEquals(new Vector2D(w, 0), layer.toLocalPixel(rightCorner));
    }

    @Test
    public void getPixelOutOfBounds(){
        ILayer layer = new Layer(100, 100, new Color(0,0,0,1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> layer.getGlobalPixel(500, 5));
        Assertions.assertThrows(IllegalArgumentException.class, () -> layer.getGlobalPixel(5, 500));
        Assertions.assertThrows(IllegalArgumentException.class, () -> layer.getGlobalPixel(500, 500));
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

    @Test
    public void rectangleOfChangeSetPixelTest(){
        AtomicBoolean wasNotified = new AtomicBoolean(false);
        ILayer layer = new Layer(1000, 1000, new Color(1,1,1,1));
        layer.addObserver(new IObserver<Tuple2<Vector2D, Vector2D>>() {
            @Override
            public void notify(Tuple2<Vector2D, Vector2D> data) {
                Assertions.assertEquals(new Vector2D(30, 30), data.getVal1());
                Assertions.assertEquals(new Vector2D(85, 36), data.getVal2());
                wasNotified.set(true);
            }
        });

        // Set some pixels where the lowest x = 30, lowest y = 30, largest x = 85, and largest y = 36
        layer.setLocalPixel(60, 32, new Color(1,1,0,1));
        layer.setLocalPixel(85, 30, new Color(1,1,0,1));
        layer.setLocalPixel(30,30, new Color(1,1,0,1));
        layer.setLocalPixel(35, 36, new Color(1,1,0,1));
        layer.setLocalPixel(40,33, new Color(1,1,0,1));

        layer.update();
        // Should be notified directly after update call
        Assertions.assertTrue(wasNotified.get());
    }

    @Test
    public void addObserverAndRemoveObserverTest(){
        AtomicBoolean shouldBeNotified = new AtomicBoolean(true);
        ILayer layer = new Layer(1000, 1000, new Color(1,1,1,1));
        IObserver<Tuple2<Vector2D, Vector2D>> ob = new IObserver<Tuple2<Vector2D, Vector2D>>() {
            @Override
            public void notify(Tuple2<Vector2D, Vector2D> data) {
                if(shouldBeNotified.get()) {
                    Assertions.assertEquals(data.getVal1(), Vector2D.ZERO);
                    Assertions.assertEquals(data.getVal2(), Vector2D.ZERO);
                }
                else {
                    Assertions.fail();
                }
            }
        };

        layer.setLocalPixel(0,0, new Color(1,1,1,0)); // Draw a pixel so we have something to notify about
        layer.addObserver(ob);
        layer.update(); // Notify
        layer.removeObserver(ob);
        layer.setLocalPixel(1,1, new Color(1,1,1,0)); // Draw another so the "buffer" is not empty
        shouldBeNotified.set(false); // Should now not be notified since ob is not an observer anymore
        layer.update(); // Notify
    }

    @Test
    public void rectangleOfChangeRotateTest(){
        ILayer layer = new Layer(1000, 1000, new Color(1,1,1,1));
        layer.addObserver(new IObserver<Tuple2<Vector2D, Vector2D>>() {
            @Override
            public void notify(Tuple2<Vector2D, Vector2D> data) {
                Assertions.assertEquals(Vector2D.ZERO, data.getVal1());
                Assertions.assertEquals(new Vector2D(layer.getWidth(),layer.getHeight()), data.getVal2());
            }
        });
        layer.setRotation(Math.PI);
        layer.update();
    }

    @Test
    public void rectangleOfChangeTranslateTest(){
        ILayer layer = new Layer(1000, 1000, new Color(1,1,1,1));
        layer.addObserver(new IObserver<Tuple2<Vector2D, Vector2D>>() {
            @Override
            public void notify(Tuple2<Vector2D, Vector2D> data) {
                Assertions.assertEquals(new Vector2D(5, 5), data.getVal1());
                Assertions.assertEquals(new Vector2D(layer.getWidth() + 5,layer.getHeight() + 5), data.getVal2());
            }
        });
        layer.setPosition(new Vector2D(5,5));
        layer.update();
    }

    @Test
    public void getPixelFromScaledAndTranslatedLayerTest(){
        int w = 12;
        int h = 12;
        ILayer layer = new Layer(w, h, new Color(1,1,1,1));

        Vector2D translation = new Vector2D(w/2d, h/2d);
        Vector2D scale = new Vector2D(2, 2);

        layer.setPosition(translation);
        layer.setScale(scale);

        // Check that center is still at the center
        Vector2D center = new Vector2D(w / 2d, h / 2d).add(translation);
        Assertions.assertEquals(new Vector2D(w / 2d, h / 2d), layer.toLocalPixel(center));

        // Since we've scaled by 2 the top right corner should be a whole scale 1,1 square away from the center of the scaled square
        Vector2D topRightCorner = center.add(new Vector2D(w, h));
        Assertions.assertEquals(new Vector2D(w,h), layer.toLocalPixel(topRightCorner));
    }

    @Test
    public void getPixelAtPointForScaledAndTranslatedAndRotatedLayerTest(){
        int w = 14;
        int h = w;
        ILayer layer = new Layer(w, h, new Color(1,1,1,1));

        Vector2D translation = new Vector2D(4, 7);
        double rotation = Math.PI / 4d;
        Vector2D scale = new Vector2D(1.3, 2); // Stretched in the y direction by 2

        layer.setRotation(rotation);
        layer.setScale(scale);
        layer.setPosition(translation);

        // This should be the center point;
        Vector2D center = new Vector2D(w / 2d, h / 2d).add(translation);
        Assertions.assertEquals(new Vector2D(w/2d, h/2d), layer.toLocalPixel(center));

        // This should be the right corner. The layer (square) is rotated 4/pi rad and thus the right corner should be the furthest in x
        // of any corner, which should be sqrt(2) * sideLen * scale in x
        Vector2D rightCorner = center.add(new Vector2D(w/2d * Math.sqrt(2) * 1.3, 0));
        Assertions.assertEquals(new Vector2D(w, 0), layer.toLocalPixel(rightCorner));

    }

}

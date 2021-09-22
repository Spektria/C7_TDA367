package C7.Model.Tools;

import C7.Model.Color;
import C7.Model.Vector.Vector2D;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Hugo Ekstrand
 */
public class CircularBrushTest {

    @Test
    public void BrushDrawTest(){
        TestISurfaceImpl testSurface = new TestISurfaceImpl(10, 10, new Vector2D(1,1));
        var brush = ToolFactory.CreateCircularBrush(1, new Color(0,1,0,0));
        brush.apply(testSurface, new Vector2D(0,0), new Vector2D(0,0));

        System.out.println("Surface:\n" + testSurface.getContentAs2DString());

        Assert.assertNotEquals(testSurface.getPixel(0,0), testSurface.getBaseColor());
        assertEquals(testSurface.getPixel(0, 1), testSurface.getBaseColor());
        assertEquals(testSurface.getPixel(1, 0), testSurface.getBaseColor());
    }

    @Test
    public void BrushDrawCircularTest(){
        TestISurfaceImpl testSurface = new TestISurfaceImpl(20, 20, new Vector2D(1, 1));
        Color color =  new Color(1,0,0,0);
        var brush = ToolFactory.CreateCircularBrush(11, color);
        brush.apply(testSurface, new Vector2D(10, 10), new Vector2D(10,10));

        System.out.println("Surface:\n" + testSurface.getContentAs2DString());

        // Center
        Assert.assertNotEquals(testSurface.getPixel(10, 10), testSurface.getBaseColor());

        // Edges
        Assert.assertNotEquals(testSurface.getPixel(15,10), testSurface.getBaseColor());
        Assert.assertNotEquals(testSurface.getPixel(5,10), testSurface.getBaseColor());
        Assert.assertNotEquals(testSurface.getPixel(10,15), testSurface.getBaseColor());
        Assert.assertNotEquals(testSurface.getPixel(10,5), testSurface.getBaseColor());

        // Curve
        Assert.assertNotEquals(testSurface.getPixel(6,11), testSurface.getBaseColor());
        Assert.assertNotEquals(testSurface.getPixel(6,12), testSurface.getBaseColor());
        assertEquals(testSurface.getPixel(6, 14), testSurface.getBaseColor());

    }

}

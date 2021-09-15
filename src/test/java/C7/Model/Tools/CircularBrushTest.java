package C7.Model.Tools;

import C7.Color;
import C7.Model.Vector.Vector2D;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CircularBrushTest {

    @Test
    public void BrushDrawTest(){
        TestISurfaceImpl testSurface = new TestISurfaceImpl(10, 10, new Vector2D(1,1));
        var brush = ToolFactory.CreateCircularBrush(testSurface,1, new Color(0,1,0,0));
        brush.beginDraw(new Vector2D(0,0));
        brush.endDraw(new Vector2D(0,0));

        System.out.println("Surface:\n" + testSurface.getContentAs2DString());

        Assert.assertNotEquals(testSurface.getPixel(0,0), null);
        assertNull(testSurface.getPixel(0, 1));
        assertNull(testSurface.getPixel(1, 0));
    }

    @Test
    public void BrushDrawCircularTest(){
        TestISurfaceImpl testSurface = new TestISurfaceImpl(20, 20, new Vector2D(1, 1));
        Color color =  new Color(1,0,0,0);
        var brush = ToolFactory.CreateCircularBrush(testSurface,11, color);
        brush.beginDraw(new Vector2D(10, 10));
        brush.endDraw(new Vector2D(10,10));

        System.out.println("Surface:\n" + testSurface.getContentAs2DString());

        // Center
        Assert.assertNotEquals(testSurface.getPixel(10, 10), null);

        // Edges
        Assert.assertNotEquals(testSurface.getPixel(15,10), null);
        Assert.assertNotEquals(testSurface.getPixel(5,10), null);
        Assert.assertNotEquals(testSurface.getPixel(10,15), null);
        Assert.assertNotEquals(testSurface.getPixel(10,5), null);

        // Curve
        Assert.assertNotEquals(testSurface.getPixel(6,11), null);
        Assert.assertNotEquals(testSurface.getPixel(6,12), null);
        assertNull(testSurface.getPixel(6, 14));

    }

}

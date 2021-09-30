package C7.Model.Tools;

import C7.Model.Vector.Vector2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Hugo Ekstrand
 */
public class CircularBrushTest {

    @Test
    public void BrushDrawTest(){
        TestISurfaceImpl testSurface = new TestISurfaceImpl(10, 10, new Vector2D(1,1));
        var brush = ToolFactory.CreateCircularBrush();
        brush.getProperties().stream().filter(str -> str.name().equals("Stroke size")).findFirst().get().setInteger(1);
        brush.apply(testSurface, new Vector2D(0,0), new Vector2D(0,0));

        System.out.println("Surface:\n" + testSurface.getContentAs2DString());

        Assertions.assertNotEquals(testSurface.getLocalPixel(0,0), testSurface.getBaseColor());
        Assertions.assertEquals(testSurface.getLocalPixel(0, 1), testSurface.getBaseColor());
        Assertions.assertEquals(testSurface.getLocalPixel(1, 0), testSurface.getBaseColor());
    }

    @Test
    public void BrushDrawCircularTest(){
        TestISurfaceImpl testSurface = new TestISurfaceImpl(20, 20, new Vector2D(1, 1));
        var brush = ToolFactory.CreateCircularBrush();
        brush.getProperties().stream().filter(str -> str.name().equals("Stroke size")).findFirst().get().setInteger(11);
        brush.apply(testSurface, new Vector2D(10, 10), new Vector2D(10,10));

        System.out.println("Surface:\n" + testSurface.getContentAs2DString());

        // Center
        Assertions.assertNotEquals(testSurface.getLocalPixel(10, 10), testSurface.getBaseColor());

        // Edges
        Assertions.assertNotEquals(testSurface.getLocalPixel(15,10), testSurface.getBaseColor());
        Assertions.assertNotEquals(testSurface.getLocalPixel(5,10), testSurface.getBaseColor());
        Assertions.assertNotEquals(testSurface.getLocalPixel(10,15), testSurface.getBaseColor());
        Assertions.assertNotEquals(testSurface.getLocalPixel(10,5), testSurface.getBaseColor());

        // Curve
        Assertions.assertNotEquals(testSurface.getLocalPixel(6,11), testSurface.getBaseColor());
        Assertions.assertNotEquals(testSurface.getLocalPixel(6,12), testSurface.getBaseColor());
        Assertions.assertEquals(testSurface.getLocalPixel(6, 14), testSurface.getBaseColor());

    }

}

package C7.Model.Tools;

import C7.Util.Color;
import C7.Util.Vector2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Hugo Ekstrand
 */
public class CalligraphyTest {

    @Test
    public void HorizontalCalligraphyClickTest(){
        TestISurfaceImpl testISurface = new TestISurfaceImpl(20,20, new Vector2D(1,1));
        ITool calligraphy = ToolFactory.createCalligraphyBrush(11, new Color(1,1,1,1));
        calligraphy.apply(testISurface, new Vector2D(6, 1), new Vector2D(6, 1));

        System.out.println("Surface: \n" + testISurface.getContentAs2DString());

        // Check so that we have the center, top, and bottom points
        Assertions.assertNotEquals(testISurface.getLocalPixel(1, 1, new Color(0,0,0,0)), testISurface.getBaseColor());
        Assertions.assertNotEquals(testISurface.getLocalPixel(6, 1, new Color(0,0,0,0)), testISurface.getBaseColor());
        Assertions.assertNotEquals(testISurface.getLocalPixel(11, 1, new Color(0,0,0,0)), testISurface.getBaseColor());

        // Check 1 pixel off up and down of center point
        Assertions.assertEquals(testISurface.getLocalPixel(6, 0, new Color(0,0,0,0)), testISurface.getBaseColor());
        Assertions.assertEquals(testISurface.getLocalPixel(6, 2, new Color(0,0,0,0)), testISurface.getBaseColor());

        // Check the same but with the ends.

        // Top
        Assertions.assertEquals(testISurface.getLocalPixel(1, 0, new Color(0,0,0,0)), testISurface.getBaseColor());
        Assertions.assertEquals(testISurface.getLocalPixel(1, 2, new Color(0,0,0,0)), testISurface.getBaseColor());
        Assertions.assertEquals(testISurface.getLocalPixel(0, 1, new Color(0,0,0,0)), testISurface.getBaseColor());

        // Bottom
        Assertions.assertEquals(testISurface.getLocalPixel(11, 0, new Color(0,0,0,0)), testISurface.getBaseColor());
        Assertions.assertEquals(testISurface.getLocalPixel(11, 2, new Color(0,0,0,0)), testISurface.getBaseColor());
        Assertions.assertEquals(testISurface.getLocalPixel(12, 1, new Color(0,0,0,0)), testISurface.getBaseColor());
    }

    @Test
    public void Sloped45degCalligraphyStrokeTest(){
        TestISurfaceImpl testISurface = new TestISurfaceImpl(8,8, new Vector2D(1,1));
        ITool calligraphy = ToolFactory.createCalligraphyBrush(8, new Color(1,1,1,1));
        calligraphy.getProperties().stream().filter(prop -> prop.getName().equals("Rotation")).findFirst().get().setDouble(Math.toDegrees(Math.PI/4d));
        calligraphy.apply(testISurface, new Vector2D(4,4), new Vector2D(4,4));

        System.out.println("Surface: \n" + testISurface.getContentAs2DString());

        // Extreme points
        Assertions.assertNotEquals(testISurface.getLocalPixel(4,4, new Color(0,0,0,0)), testISurface.getBaseColor()); // Center
        Assertions.assertNotEquals(testISurface.getLocalPixel(1,1, new Color(0,0,0,0)), testISurface.getBaseColor()); // Top
        Assertions.assertNotEquals(testISurface.getLocalPixel(6,6, new Color(0,0,0,0)), testISurface.getBaseColor()); // Bottom

        Assertions.assertNotEquals(testISurface.getLocalPixel(3,3, new Color(0,0,0,0)), testISurface.getBaseColor()); // Extra point in line

        // Check so not cross is drawn
        Assertions.assertEquals(testISurface.getLocalPixel(1,6, new Color(0,0,0,0)), testISurface.getBaseColor());
        Assertions.assertEquals(testISurface.getLocalPixel(6,1, new Color(0,0,0,0)), testISurface.getBaseColor());

        // Check so that the points around the center are empty
        Assertions.assertEquals(testISurface.getLocalPixel(4,5, new Color(0,0,0,0)), testISurface.getBaseColor());
        Assertions.assertEquals(testISurface.getLocalPixel(5,4, new Color(0,0,0,0)), testISurface.getBaseColor());

        // Check so that the line is the correct length; that is, of 6 pixels.
        Assertions.assertEquals(testISurface.getLocalPixel(0,0, new Color(0,0,0,0)), testISurface.getBaseColor());
        Assertions.assertEquals(testISurface.getLocalPixel(7,7, new Color(0,0,0,0)), testISurface.getBaseColor());
    }

}

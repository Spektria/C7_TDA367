package C7.Model.Tools;

import C7.Model.Color;
import C7.Model.Vector.Vector2D;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Hugo Ekstrand
 */
public class CalligraphyTest {

    @Test
    public void HorizontalCalligraphyClickTest(){
        TestISurfaceImpl testISurface = new TestISurfaceImpl(20,20, new Vector2D(1,1));
        ITool calligraphy = ToolFactory.CreateCalligraphyBrush(11, new Color(1,0,0,0));
        calligraphy.apply(testISurface, new Vector2D(6, 1), new Vector2D(6, 1));

        System.out.println("Surface: \n" + testISurface.getContentAs2DString());

        // Check so that we have the center, top, and bottom points
        Assert.assertNotEquals(testISurface.getPixel(1, 1), testISurface.getBaseColor());
        Assert.assertNotEquals(testISurface.getPixel(6, 1), testISurface.getBaseColor());
        Assert.assertNotEquals(testISurface.getPixel(11, 1), testISurface.getBaseColor());

        // Check 1 pixel off up and down of center point
        Assert.assertEquals(testISurface.getPixel(6, 0), testISurface.getBaseColor());
        Assert.assertEquals(testISurface.getPixel(6, 2), testISurface.getBaseColor());

        // Check the same but with the ends.

        // Top
        Assert.assertEquals(testISurface.getPixel(1, 0), testISurface.getBaseColor());
        Assert.assertEquals(testISurface.getPixel(1, 2), testISurface.getBaseColor());
        Assert.assertEquals(testISurface.getPixel(0, 1), testISurface.getBaseColor());

        // Bottom
        Assert.assertEquals(testISurface.getPixel(11, 0), testISurface.getBaseColor());
        Assert.assertEquals(testISurface.getPixel(11, 2), testISurface.getBaseColor());
        Assert.assertEquals(testISurface.getPixel(12, 1), testISurface.getBaseColor());
    }

    @Test
    public void Sloped45degCalligraphyStrokeTest(){
        TestISurfaceImpl testISurface = new TestISurfaceImpl(8,8, new Vector2D(1,1));
        ITool calligraphy = ToolFactory.CreateCalligraphyBrush(8, new Color(1,0,0,0));
        calligraphy.getProperties().stream().filter(prop -> prop.name().equals("Rotation")).findFirst().get().setDouble(Math.toDegrees(Math.PI/4d));
        calligraphy.apply(testISurface, new Vector2D(4,4), new Vector2D(4,4));

        System.out.println("Surface: \n" + testISurface.getContentAs2DString());

        // Extreme points
        Assert.assertNotEquals(testISurface.getPixel(4,4), testISurface.getBaseColor()); // Center
        Assert.assertNotEquals(testISurface.getPixel(1,1), testISurface.getBaseColor()); // Top
        Assert.assertNotEquals(testISurface.getPixel(6,6), testISurface.getBaseColor()); // Bottom

        Assert.assertNotEquals(testISurface.getPixel(3,3), testISurface.getBaseColor()); // Extra point in line

        // Check so not cross is drawn
        Assert.assertEquals(testISurface.getPixel(1,6), testISurface.getBaseColor());
        Assert.assertEquals(testISurface.getPixel(6,1), testISurface.getBaseColor());

        // Check so that the points around the center are empty
        Assert.assertEquals(testISurface.getPixel(4,5), testISurface.getBaseColor());
        Assert.assertEquals(testISurface.getPixel(5,4), testISurface.getBaseColor());

        // Check so that the line is the correct length; that is, of 6 pixels.
        Assert.assertEquals(testISurface.getPixel(0,0), testISurface.getBaseColor());
        Assert.assertEquals(testISurface.getPixel(7,7), testISurface.getBaseColor());
    }

}

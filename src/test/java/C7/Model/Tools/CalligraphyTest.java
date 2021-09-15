package C7.Model.Tools;

import C7.Color;
import C7.Model.Vector.Vector2D;
import org.junit.Assert;
import org.junit.Test;

public class CalligraphyTest {

    @Test
    public void HorizontalCalligraphyClickTest(){
        TestISurfaceImpl testISurface = new TestISurfaceImpl(20,20, new Vector2D(1,1));
        ITool calligraphy = ToolFactory.CreateCalligraphyBrush(testISurface, 11, new Color(1,0,0,0), 0);
        calligraphy.beginDraw(new Vector2D(6, 1));

        //System.out.println("Surface: \n" + testISurface.getContentAs2DString());

        // Check so that we have the center, top, and bottom points
        Assert.assertNotEquals(testISurface.getPixel(1, 1), null);
        Assert.assertNotEquals(testISurface.getPixel(6, 1), null);
        Assert.assertNotEquals(testISurface.getPixel(11, 1), null);

        // Check 1 pixel off up and down of center point
        Assert.assertNull(testISurface.getPixel(6, 0));
        Assert.assertNull(testISurface.getPixel(6, 2));

        // Check the same but with the ends.

        // Top
        Assert.assertNull(testISurface.getPixel(1, 0));
        Assert.assertNull(testISurface.getPixel(1, 2));
        Assert.assertNull(testISurface.getPixel(0, 1));

        // Bottom
        Assert.assertNull(testISurface.getPixel(11, 0));
        Assert.assertNull(testISurface.getPixel(11, 2));
        Assert.assertNull(testISurface.getPixel(12, 1));
    }

    @Test
    public void Sloped45degCalligraphyStrokeTest(){
        TestISurfaceImpl testISurface = new TestISurfaceImpl(8,8, new Vector2D(1,1));
        ITool calligraphy = ToolFactory.CreateCalligraphyBrush(testISurface,8, new Color(1,0,0,0), Math.PI/4);
        calligraphy.beginDraw(new Vector2D(4,4));

        //System.out.println("Surface: \n" + testISurface.getContentAs2DString());

        // Extreme points
        Assert.assertNotNull(testISurface.getPixel(4,4)); // Center
        Assert.assertNotNull(testISurface.getPixel(1,1)); // Top
        Assert.assertNotNull(testISurface.getPixel(6,6)); // Bottom

        Assert.assertNotNull(testISurface.getPixel(3,3)); // Extra point in line

        // Check so not cross is drawn
        Assert.assertNull(testISurface.getPixel(1,6));
        Assert.assertNull(testISurface.getPixel(6,1));

        // Check so that the points around the center are empty
        Assert.assertNull(testISurface.getPixel(4,5));
        Assert.assertNull(testISurface.getPixel(5,4));

        // Check so that the line is the correct length; that is, of 6 pixels.
        Assert.assertNull(testISurface.getPixel(0,0));
        Assert.assertNull(testISurface.getPixel(7,7));
    }

}

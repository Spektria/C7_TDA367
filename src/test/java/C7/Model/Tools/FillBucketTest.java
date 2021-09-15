package C7.Model.Tools;

import C7.Color;
import C7.Model.Vector.Vector2D;
import org.junit.Assert;
import org.junit.Test;

public class FillBucketTest {

    @Test
    public void emptyFillTest(){
        TestISurfaceImpl testSurface = new TestISurfaceImpl(10, 10, new Vector2D(1,1));
        Color fill = new Color(0.2f, 0.5f ,0.3f, 1f);
        var brush = ToolFactory.CreateFillBucket(testSurface, fill, 0.5f);
        brush.beginDraw(new Vector2D(3,3));

        //System.out.println("Surface: \n" + testSurface.getContentAs2DString());

        for (int i = 0; i < testSurface.getWidth(); i++) {
            for (int j = 0; j < testSurface.getHeight(); j++) {
                Assert.assertNotNull(testSurface.getPixel(i, j));
            }
        }
    }

    @Test
    public void rectangleFillTest(){
        TestISurfaceImpl testSurface = new TestISurfaceImpl(10, 10, new Vector2D(1,1));

        for (int i = 0; i < testSurface.getHeight(); i++) {
            for (int j = 0; j < testSurface.getWidth(); j++) {
                if(i == 2 || j == 1 || testSurface.getHeight() - i == 1 + 1 || testSurface.getWidth() - j == 1 + 1)
                    testSurface.setPixel(j, i, new Color(0.15f, 0.15f, 0.15f, 1f));
            }
        }

        var brush = ToolFactory.CreateFillBucket(testSurface, new Color(0,0,0,1f), 0.05f);
        brush.beginDraw(new Vector2D(3,3));

        //System.out.println("Surface: \n" + testSurface.getContentAs2DString());

        for (int y = 0; y < testSurface.getHeight(); y++) {
            for (int x = 0; x < testSurface.getWidth(); x++) {
                Color pixel = testSurface.getPixel(x, y);
                if(y == 2 || x == 1 || testSurface.getHeight() - y == 2 || testSurface.getWidth() - x == 2){

                    if(pixel.getGreen() != 0.15f)
                        Assert.fail();
                }
                else if(y > 2 && x > 1 && testSurface.getHeight() - y > 2 && testSurface.getWidth() - x > 2){
                    if(pixel.getGreen() != 0)
                        Assert.fail();
                }
                else if(pixel != null){
                    Assert.fail();
                }
            }
        }
    }

    @Test
    public void thresholdFillTest(){
        TestISurfaceImpl testSurface = new TestISurfaceImpl(10, 10, new Vector2D(1,1));

        for (int x = 0; x < testSurface.getWidth(); x++) {
            for (int y = 0; y < testSurface.getHeight(); y++) {
                testSurface.setPixel(x, y, new Color(x * 0.1f, 0, 0, 1f));
            }
        }

        var brush = ToolFactory.CreateFillBucket(testSurface, new Color(0,0,0,1f), 0.5f);
        brush.beginDraw(new Vector2D(1,1));

        System.out.println("Surface: \n" + testSurface.getContentAs2DString());

        for (int x = 0; x < testSurface.getWidth(); x++) {
            for (int y = 0; y < testSurface.getHeight(); y++) {
                Color pixel = testSurface.getPixel(x, y);
                if(x < 6)
                    Assert.assertEquals(0, Float.compare(0, pixel.getRed()));
                else
                    Assert.assertEquals(0, Float.compare(x * 0.1f, pixel.getRed()));
            }
        }

    }

}

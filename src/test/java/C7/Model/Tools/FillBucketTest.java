package C7.Model.Tools;

import C7.Color;
import org.junit.Assert;
import org.junit.Test;

public class FillBucketTest {

    @Test
    public void emptyFillTest(){
        TestISurfaceImpl testSurface = new TestISurfaceImpl(10, 10);
        Color fill = new Color(20, 50 ,30, 255);
        var brush = ToolFactory.CreateFillBucket(fill, 50);
        brush.draw(3,3, testSurface);

        //System.out.println("Surface: \n" + testSurface.getContentAs2DString());

        for (int i = 0; i < testSurface.getWidth(); i++) {
            for (int j = 0; j < testSurface.getHeight(); j++) {
                Assert.assertNotNull(testSurface.getPixel(i, j));
            }
        }
    }

    @Test
    public void rectangleFillTest(){
        TestISurfaceImpl testSurface = new TestISurfaceImpl(10, 10);

        for (int i = 0; i < testSurface.getHeight(); i++) {
            for (int j = 0; j < testSurface.getWidth(); j++) {
                if(i == 2 || j == 1 || testSurface.getHeight() - i == 1 + 1 || testSurface.getWidth() - j == 1 + 1)
                    testSurface.setPixel(j, i, new Color(150, 150, 150, 255));
            }
        }

        var brush = ToolFactory.CreateFillBucket(new Color(0,0,0,255), 50);
        brush.draw(3,3, testSurface);

        //System.out.println("Surface: \n" + testSurface.getContentAs2DString());

        for (int y = 0; y < testSurface.getHeight(); y++) {
            for (int x = 0; x < testSurface.getWidth(); x++) {
                Color pixel = testSurface.getPixel(x, y);
                if(y == 2 || x == 1 || testSurface.getHeight() - y == 2 || testSurface.getWidth() - x == 2){

                    if(pixel.getGreen() != 150)
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
        TestISurfaceImpl testSurface = new TestISurfaceImpl(10, 10);

        for (int x = 0; x < testSurface.getWidth(); x++) {
            for (int y = 0; y < testSurface.getHeight(); y++) {
                testSurface.setPixel(x, y, new Color(x * 10, 0, 0, 255));
            }
        }

        var brush = ToolFactory.CreateFillBucket(new Color(0,0,0,255), 50);
        brush.draw(1,1, testSurface);

        //System.out.println("Surface: \n" + testSurface.getContentAs2DString());

        for (int x = 0; x < testSurface.getWidth(); x++) {
            for (int y = 0; y < testSurface.getHeight(); y++) {
                Color pixel = testSurface.getPixel(x, y);
                if(x < 6)
                    Assert.assertEquals(0, pixel.getRed());
                else
                    Assert.assertEquals(x * 10, pixel.getRed());
            }
        }

    }

}

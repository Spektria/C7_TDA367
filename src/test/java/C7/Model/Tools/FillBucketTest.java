package C7.Model.Tools;

import C7.Model.Color;
import C7.Model.Vector.Vector2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Hugo Ekstrand
 */
public class FillBucketTest {

    @Test
    public void emptyFillTest(){
        TestISurfaceImpl testSurface = new TestISurfaceImpl(10, 10, new Vector2D(1,1));
        Color fill = new Color(0.2f, 0.5f ,0.3f, 1f);
        var brush = ToolFactory.CreateFillBucket(fill, 0.5f);
        brush.apply(testSurface, new Vector2D(3,3),new Vector2D(3,3));

        System.out.println("Surface: \n" + testSurface.getContentAs2DString());

        for (int i = 0; i < testSurface.getWidth(); i++) {
            for (int j = 0; j < testSurface.getHeight(); j++) {
                Assertions.assertNotEquals(testSurface.getPixel(i, j), testSurface.getBaseColor());
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

        System.out.println("Surface: \n" + testSurface.getContentAs2DString());

        var brush = ToolFactory.CreateFillBucket(new Color(0,0,0,1f), 0.05f);
        brush.apply(testSurface, new Vector2D(3,3), new Vector2D(3,3));

        System.out.println("Surface: \n" + testSurface.getContentAs2DString());

        for (int y = 0; y < testSurface.getHeight(); y++) {
            for (int x = 0; x < testSurface.getWidth(); x++) {
                Color pixel = testSurface.getPixel(x, y);
                if(y == 2 || x == 1 || testSurface.getHeight() - y == 2 || testSurface.getWidth() - x == 2){

                    if(pixel.getGreen() != 0.15f)
                        Assertions.fail();
                }
                else if(y > 2 && x > 1 && testSurface.getHeight() - y > 2 && testSurface.getWidth() - x > 2){
                    if(pixel.getGreen() != 0)
                        Assertions.fail();
                }
                else if(!pixel.equals(new Color(0,0,0,0))){
                    Assertions.fail();
                }
            }
        }
    }

    @Test
    public void thresholdFillTest(){
        TestISurfaceImpl testSurface = new TestISurfaceImpl(10, 1, new Vector2D(1,1));

        for (int x = 0; x < testSurface.getWidth(); x++) {
            for (int y = 0; y < testSurface.getHeight(); y++) {
                testSurface.setPixel(x, y, new Color(x * 0.1f, 0, 0, 1f));
            }
        }

        var brush = ToolFactory.CreateFillBucket(new Color(0,1f,0f,1f), 0.501f);
        brush.apply(testSurface, new Vector2D(0,0), new Vector2D(0,0));

        System.out.println("Surface: \n" + testSurface.getContentAs2DString());

        for (int x = 0; x < testSurface.getWidth(); x++) {
            for (int y = 0; y < testSurface.getHeight(); y++) {
                Color pixel = testSurface.getPixel(x, y);

                if(x < 6)
                    Assertions.assertEquals(0, Float.compare(1f, pixel.getBlue()));
                else
                    Assertions.assertEquals(0, Float.compare(x * 0.1f, pixel.getRed()));
            }
        }

    }

}

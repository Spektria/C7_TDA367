package C7.Vector;

import org.junit.Assert;
import org.junit.Test;

public class Vector2DTest {

    @Test
    public void RotationTest(){
        Vector2D a = new Vector2D(5,5);
        Vector2D b = new Vector2D(0,0);

        Assert.assertEquals(a.rotatedAround(b, 0),a);
        Assert.assertEquals(a.rotatedAround(b, Math.PI * 201), a.mult(-1));
        Assert.assertEquals(a.rotatedAround(b,Math.PI/2d), new Vector2D(-5, 5));

        b = new Vector2D(1,4);
        Assert.assertEquals(a.rotatedAround(b,Math.PI/2d), new Vector2D(0, 8));
        Assert.assertEquals(a.rotatedAround(b,3 * Math.PI/2d), new Vector2D(2, 0));
    }

    @Test
    public void AddTest(){
        Vector2D a = new Vector2D(10, 5);
        Vector2D b = new Vector2D(5, 2);

        Assert.assertEquals(a.add(b), b.add(a));
        Assert.assertEquals(a.add(b), new Vector2D(a.getX() + b.getX(), a.getY() + b.getY()));

        a = new Vector2D(-5, -2);
        Assert.assertEquals(a.add(b), new Vector2D(0,0));

    }

    @Test
    public void SubTest(){
        Vector2D a = new Vector2D(10, 5);
        Vector2D b = new Vector2D(5, 2);

        Assert.assertEquals(a.sub(b), b.sub(a).mult(-1));
        Assert.assertEquals(a.sub(b), new Vector2D(a.getX() - b.getX(), a.getY() - b.getY()));

        a = new Vector2D(-5, -2);
        Assert.assertEquals(b.sub(a), new Vector2D(10,4));
    }

    @Test
    public void MultTest(){
        Vector2D a = new Vector2D(1, 1);

        Assert.assertEquals(a.mult(1), a);
        Assert.assertEquals(a.mult(2), a.add(a));
        Assert.assertEquals(a.mult(0), new Vector2D(0, 0));
        Assert.assertEquals(a.mult(0.5), new Vector2D(0.5, 0.5));

        a = new Vector2D(2, 6);
        Assert.assertEquals(a.mult(3), new Vector2D(6, 18));
        Assert.assertEquals(a.mult(-1), new Vector2D(-2, -6));
    }

    @Test
    public void lenTest(){
        Vector2D a = new Vector2D(5, 5);

        Assert.assertEquals(0, Double.compare(a.len(), 5d * Math.sqrt(2)));

        a = new Vector2D(0, 0);
        Assert.assertEquals(0, Double.compare(a.len(), 0d));

        a = new Vector2D(0, 1);
        Assert.assertEquals(0, Double.compare(a.len(), 1d));

        a = new Vector2D(3, 4);
        Assert.assertEquals(0, Double.compare(a.len(), 5d));

        a = new Vector2D(-5, -5);
        Assert.assertEquals(0, Double.compare(a.len(), 5d * Math.sqrt(2)));
    }

    @Test
    public void normalizeTest(){
        Vector2D a = new Vector2D(20, 50);

        Assert.assertEquals(0, Double.compare(a.normalized().len(), 1));

        a = new Vector2D(3, 4);
        Assert.assertEquals(a.normalized(), new Vector2D(3d/5, 4d/5));

        a = new Vector2D(-3, 4);
        Assert.assertEquals(a.normalized(), new Vector2D(-3d/5, 4d/5));
    }
}

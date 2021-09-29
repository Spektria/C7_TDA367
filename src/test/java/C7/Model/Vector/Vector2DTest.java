package C7.Model.Vector;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Vector2DTest {

    @Test
    public void RotationTest(){
        Vector2D a = new Vector2D(5,5);
        Vector2D b = new Vector2D(0,0);

        Assertions.assertEquals(a.rotatedAround(b, 0),a);
        Assertions.assertEquals(a.rotatedAround(b, Math.PI * 201), a.mult(-1));
        Assertions.assertEquals(a.rotatedAround(b,Math.PI/2d), new Vector2D(-5, 5));

        b = new Vector2D(1,4);
        Assertions.assertEquals(a.rotatedAround(b,Math.PI/2d), new Vector2D(0, 8));
        Assertions.assertEquals(a.rotatedAround(b,3 * Math.PI/2d), new Vector2D(2, 0));
    }

    @Test
    public void AddTest(){
        Vector2D a = new Vector2D(10, 5);
        Vector2D b = new Vector2D(5, 2);

        Assertions.assertEquals(a.add(b), b.add(a));
        Assertions.assertEquals(a.add(b), new Vector2D(a.getX() + b.getX(), a.getY() + b.getY()));

        a = new Vector2D(-5, -2);
        Assertions.assertEquals(a.add(b), new Vector2D(0,0));

    }

    @Test
    public void SubTest(){
        Vector2D a = new Vector2D(10, 5);
        Vector2D b = new Vector2D(5, 2);

        Assertions.assertEquals(a.sub(b), b.sub(a).mult(-1));
        Assertions.assertEquals(a.sub(b), new Vector2D(a.getX() - b.getX(), a.getY() - b.getY()));

        a = new Vector2D(-5, -2);
        Assertions.assertEquals(b.sub(a), new Vector2D(10,4));
    }

    @Test
    public void MultTest(){
        Vector2D a = new Vector2D(1, 1);

        Assertions.assertEquals(a.mult(1), a);
        Assertions.assertEquals(a.mult(2), a.add(a));
        Assertions.assertEquals(a.mult(0), new Vector2D(0, 0));
        Assertions.assertEquals(a.mult(0.5), new Vector2D(0.5, 0.5));

        a = new Vector2D(2, 6);
        Assertions.assertEquals(a.mult(3), new Vector2D(6, 18));
        Assertions.assertEquals(a.mult(-1), new Vector2D(-2, -6));
    }

    @Test
    public void lenTest(){
        Vector2D a = new Vector2D(5, 5);

        Assertions.assertEquals(0, Double.compare(a.len(), 5d * Math.sqrt(2)));

        a = new Vector2D(0, 0);
        Assertions.assertEquals(0, Double.compare(a.len(), 0d));

        a = new Vector2D(0, 1);
        Assertions.assertEquals(0, Double.compare(a.len(), 1d));

        a = new Vector2D(3, 4);
        Assertions.assertEquals(0, Double.compare(a.len(), 5d));

        a = new Vector2D(-5, -5);
        Assertions.assertEquals(0, Double.compare(a.len(), 5d * Math.sqrt(2)));
    }

    @Test
    public void normalizeTest(){
        Vector2D a = new Vector2D(20, 50);

        Assertions.assertEquals(0, Double.compare(a.normalized().len(), 1));

        a = new Vector2D(3, 4);
        Assertions.assertEquals(a.normalized(), new Vector2D(3d/5, 4d/5));

        a = new Vector2D(-3, 4);
        Assertions.assertEquals(a.normalized(), new Vector2D(-3d/5, 4d/5));
    }
}

package C7.Util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class C7MathTest {

    @Test
    public void minTest(){
        Assertions.assertEquals(2, C7Math.min(5, 11, 50, 2, 100, 6));
        Assertions.assertEquals(-100d, C7Math.min(5d, 11d, 50d, 2d, 100d, 6d, -100d));
        Assertions.assertEquals(4, C7Math.min(4));

        Assertions.assertThrows(IllegalArgumentException.class, () -> C7Math.min(new Comparable[]{}));
    }

    @Test
    public void maxTest(){
        Assertions.assertEquals(100, C7Math.max(5, 11, 50, 2, 100, 6));
        Assertions.assertEquals(-2d, C7Math.max(-20d, -199d, -2d, -124d));
        Assertions.assertEquals(4, C7Math.max(4));

        Assertions.assertThrows(IllegalArgumentException.class, () -> C7Math.max(new Comparable[]{}));
    }

    @Test
    public void limitTest(){
        Assertions.assertEquals(5, C7Math.limit(-10, 5, 10));
        Assertions.assertEquals(100, C7Math.limit(1000, 5, 100));
        Assertions.assertEquals(5, C7Math.limit(-10, 5, 5));
        Assertions.assertEquals(5.3d, C7Math.limit(5.3d, 5d, 10d));

        Assertions.assertThrows(IllegalArgumentException.class, () -> C7Math.limit(50, 10, 5));
    }
}

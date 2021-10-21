package C7.Model.Tools.ToolProperties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NumericalPropertyTest {

    private double d;

    @Test
    public void outOfUpperBoundsTest(){
        IToolProperty prop = ToolPropertyFactory.createDoubleProperty("d", (v) -> d = v, () -> d, 0, 1);

        Assertions.assertDoesNotThrow(() -> prop.setDouble(0.9));
        Assertions.assertDoesNotThrow(() -> prop.setDouble(1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> prop.setDouble(1.01));
    }

    @Test
    public void outOfLowerBoundsTest(){
        IToolProperty prop = ToolPropertyFactory.createDoubleProperty("d", (v) -> d = v, () -> d, -24, -4);

        Assertions.assertDoesNotThrow(() -> prop.setDouble(-20));
        Assertions.assertDoesNotThrow(() -> prop.setDouble(-24));
        Assertions.assertThrows(IllegalArgumentException.class, () -> prop.setDouble(-25));
    }
}

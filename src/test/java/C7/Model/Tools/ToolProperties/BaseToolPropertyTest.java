package C7.Model.Tools.ToolProperties;

import C7.Util.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Hugo Ekstrand
 */
public class BaseToolPropertyTest {


    private boolean bool;
    private int integer;

    @Test
    public void wrongTypeAccessInteger(){
        var prop = ToolPropertyFactory.createBooleanProperty("bool",(b) -> bool = b, () -> bool);

        Assertions.assertThrows(UnsupportedOperationException.class, prop::getInteger);
    }

    @Test
    public void wrongTypeAccessColor() {
        var prop = ToolPropertyFactory.createBooleanProperty("bool",(b) -> bool = b, () -> bool);

        Assertions.assertThrows(UnsupportedOperationException.class, prop::getColor);
    }

    @Test
    public void wrongTypeAccessDouble() {
        var prop = ToolPropertyFactory.createBooleanProperty("bool",(b) -> bool = b, () -> bool);

        Assertions.assertThrows(UnsupportedOperationException.class, prop::getDouble);
    }

    @Test
    public void wrongTypeAccessBoolean() {
        var prop = ToolPropertyFactory.createIntegerProperty("int", (i) -> integer = i, () -> integer, 0, 100);

        Assertions.assertThrows(UnsupportedOperationException.class, prop::getBoolean);
    }

    @Test
    public void wrongTypeSetInteger(){
        var prop = ToolPropertyFactory.createBooleanProperty("bool",(b) -> bool = b, () -> bool);

        Assertions.assertThrows(UnsupportedOperationException.class, () -> prop.setInteger(1));
    }

    @Test
    public void wrongTypeSetColor(){
        var prop = ToolPropertyFactory.createBooleanProperty("bool",(b) -> bool = b, () -> bool);

        Assertions.assertThrows(UnsupportedOperationException.class, () -> prop.setColor(new Color(1f,1f,1f,1f)));
    }

    @Test
    public void wrongTypeSetDouble(){
        var prop = ToolPropertyFactory.createBooleanProperty("bool",(b) -> bool = b, () -> bool);

        Assertions.assertThrows(UnsupportedOperationException.class, () -> prop.setDouble(1.2d));
    }

    @Test
    public void wrongTypeSetBoolean(){
        var prop = ToolPropertyFactory.createIntegerProperty("int",(i) -> integer = i, () -> integer, 0, 100);

        Assertions.assertThrows(UnsupportedOperationException.class, () -> prop.setBoolean(false));
    }


    @Test
    public void getNameTest(){
        var prop = ToolPropertyFactory.createBooleanProperty("bool",(b) -> bool = b, () -> bool);

        Assertions.assertEquals("bool", prop.getName());
    }

    @Test
    public void getNoneExtantUpperBoundsTest(){
        var prop = ToolPropertyFactory.createBooleanProperty("bool",(b) -> bool = b, () -> bool);

        Assertions.assertThrows(UnsupportedOperationException.class, prop::upperBound);

    }

    @Test
    public void getNoneExtantLowerBoundsTest(){
        var prop = ToolPropertyFactory.createBooleanProperty("bool",(b) -> bool = b, () -> bool);

        Assertions.assertThrows(UnsupportedOperationException.class, prop::lowerBound);
    }

    @Test
    public void setValueToDefaultTest(){
        integer = 50; // 50 should become props default.
        var prop = ToolPropertyFactory.createIntegerProperty("d", (i) -> integer = i, () -> integer, 0, 100);

        prop.setInteger(10);
        prop.setInteger(90);
        prop.setToDefault();

        Assertions.assertEquals(50, prop.getInteger());
    }

}

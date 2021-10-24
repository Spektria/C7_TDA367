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
        IToolProperty prop = ToolPropertyFactory.createBooleanProperty("bool",(b) -> bool = b, () -> bool);

        Assertions.assertThrows(UnsupportedOperationException.class, prop::getInteger);
    }

    @Test
    public void wrongTypeAccessColor() {
        IToolProperty prop = ToolPropertyFactory.createBooleanProperty("bool",(b) -> bool = b, () -> bool);

        Assertions.assertThrows(UnsupportedOperationException.class, prop::getColor);
    }

    @Test
    public void wrongTypeAccessDouble() {
        IToolProperty prop = ToolPropertyFactory.createBooleanProperty("bool",(b) -> bool = b, () -> bool);

        Assertions.assertThrows(UnsupportedOperationException.class, prop::getDouble);
    }

    @Test
    public void wrongTypeAccessBoolean() {
        IToolProperty prop = ToolPropertyFactory.createIntegerProperty("int", (i) -> integer = i, () -> integer, 0, 100);

        Assertions.assertThrows(UnsupportedOperationException.class, prop::getBoolean);
    }

    @Test
    public void wrongTypeSetInteger(){
        IToolProperty prop = ToolPropertyFactory.createBooleanProperty("bool",(b) -> bool = b, () -> bool);

        Assertions.assertThrows(UnsupportedOperationException.class, () -> prop.setInteger(1));
    }

    @Test
    public void wrongTypeSetColor(){
        IToolProperty prop = ToolPropertyFactory.createBooleanProperty("bool",(b) -> bool = b, () -> bool);

        Assertions.assertThrows(UnsupportedOperationException.class, () -> prop.setColor(new Color(1f,1f,1f,1f)));
    }

    @Test
    public void wrongTypeSetDouble(){
        IToolProperty prop = ToolPropertyFactory.createBooleanProperty("bool",(b) -> bool = b, () -> bool);

        Assertions.assertThrows(UnsupportedOperationException.class, () -> prop.setDouble(1.2d));
    }

    @Test
    public void wrongTypeSetBoolean(){
        IToolProperty prop = ToolPropertyFactory.createIntegerProperty("int",(i) -> integer = i, () -> integer, 0, 100);

        Assertions.assertThrows(UnsupportedOperationException.class, () -> prop.setBoolean(false));
    }


    @Test
    public void getNameTest(){
        IToolProperty prop = ToolPropertyFactory.createBooleanProperty("bool",(b) -> bool = b, () -> bool);

        Assertions.assertEquals("bool", prop.getName());
    }

    @Test
    public void getNoneExtantUpperBoundsTest(){
        IToolProperty prop = ToolPropertyFactory.createBooleanProperty("bool",(b) -> bool = b, () -> bool);

        Assertions.assertThrows(UnsupportedOperationException.class, prop::upperBound);

    }

    @Test
    public void getNoneExtantLowerBoundsTest(){
        IToolProperty prop = ToolPropertyFactory.createBooleanProperty("bool",(b) -> bool = b, () -> bool);

        Assertions.assertThrows(UnsupportedOperationException.class, prop::lowerBound);
    }

    @Test
    public void setValueToDefaultTest(){
        integer = 50; // 50 should become props default.
        IToolProperty prop = ToolPropertyFactory.createIntegerProperty("d", (i) -> integer = i, () -> integer, 0, 100);

        prop.setInteger(10);
        prop.setInteger(90);
        prop.setToDefault();

        Assertions.assertEquals(50, prop.getInteger());
    }

}

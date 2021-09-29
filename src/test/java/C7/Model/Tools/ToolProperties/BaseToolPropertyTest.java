package C7.Model.Tools.ToolProperties;

import C7.Model.Color;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BaseToolPropertyTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private boolean bool;
    private int integer;

    @Test
    public void wrongTypeAccessInteger(){
        var prop = ToolPropertyFactory.createBooleanProperty("bool","bool",(b) -> bool = b, () -> bool);

        exception.expect(UnsupportedOperationException.class);
        prop.getInteger();

    }

    @Test
    public void wrongTypeAccessColor() {
        var prop = ToolPropertyFactory.createBooleanProperty("bool","bool",(b) -> bool = b, () -> bool);

        exception.expect(UnsupportedOperationException.class);
        prop.getColor();
    }

    @Test
    public void wrongTypeAccessDouble() {
        var prop = ToolPropertyFactory.createBooleanProperty("bool","bool",(b) -> bool = b, () -> bool);

        exception.expect(UnsupportedOperationException.class);
        prop.getDouble();
    }

    @Test
    public void wrongTypeAccessBoolean() {
        var prop = ToolPropertyFactory.createIntegerProperty("int","a", (i) -> integer = i, () -> integer, 0, 100);

        exception.expect(UnsupportedOperationException.class);
        prop.getBoolean();
    }

    @Test
    public void wrongTypeSetInteger(){
        var prop = ToolPropertyFactory.createBooleanProperty("bool","bool",(b) -> bool = b, () -> bool);

        exception.expect(UnsupportedOperationException.class);
        prop.setInteger(1);
    }

    @Test
    public void wrongTypeSetColor(){
        var prop = ToolPropertyFactory.createBooleanProperty("bool","bool",(b) -> bool = b, () -> bool);

        exception.expect(UnsupportedOperationException.class);
        prop.setColor(new Color(1f,1f,1f,1f));
    }

    @Test
    public void wrongTypeSetDouble(){
        var prop = ToolPropertyFactory.createBooleanProperty("bool","bool",(b) -> bool = b, () -> bool);

        exception.expect(UnsupportedOperationException.class);
        prop.setDouble(2.2d);
    }

    @Test
    public void wrongTypeSetBoolean(){
        var prop = ToolPropertyFactory.createIntegerProperty("int","int",(i) -> integer = i, () -> integer, 0, 100);

        exception.expect(UnsupportedOperationException.class);
        prop.setBoolean(true);
    }


    @Test
    public void getNameTest(){
        var prop = ToolPropertyFactory.createBooleanProperty("bool","bool",(b) -> bool = b, () -> bool);

        Assert.assertEquals("bool", prop.name());
    }

    @Test
    public void getDescriptionTest(){
        var prop = ToolPropertyFactory.createBooleanProperty("bool","bool Desc",(b) -> bool = b, () -> bool);

        Assert.assertEquals("bool Desc", prop.description());
    }

    @Test
    public void getNoneExtantUpperBoundsTest(){
        var prop = ToolPropertyFactory.createBooleanProperty("bool","bool Desc",(b) -> bool = b, () -> bool);

        exception.expect(UnsupportedOperationException.class);
        prop.upperBound();

        exception.expect(UnsupportedOperationException.class);
        prop.lowerBound();
    }

    @Test
    public void getNoneExtantLowerBoundsTest(){
        var prop = ToolPropertyFactory.createBooleanProperty("bool","bool Desc",(b) -> bool = b, () -> bool);

        exception.expect(UnsupportedOperationException.class);
        prop.lowerBound();
    }

}

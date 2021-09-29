package C7.Model.Tools.ToolProperties;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BaseToolPropertyTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private boolean bool;

    @Test
    public void wrongTypeAccess(){
        var prop = ToolPropertyFactory.createBooleanProperty("bool","bool",(b) -> bool = b, () -> bool);
        prop.getBoolean();

        exception.expect(UnsupportedOperationException.class);
        prop.getInteger();
    }

    @Test
    public void wrongTypeSet(){
        var prop = ToolPropertyFactory.createBooleanProperty("bool","bool",(b) -> bool = b, () -> bool);
        prop.setBoolean(false);

        exception.expect(UnsupportedOperationException.class);
        prop.setDouble(2.2d);
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

}

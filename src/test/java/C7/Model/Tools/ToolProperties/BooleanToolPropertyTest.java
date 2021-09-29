package C7.Model.Tools.ToolProperties;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class BooleanToolPropertyTest {

    private boolean internalProp = true;
    private IToolProperty boolProp = ToolPropertyFactory.createBooleanProperty("testBoolProp", "testing", (b) -> internalProp = b, () -> internalProp);

    @BeforeEach
    public void init(){
        internalProp = true;
        boolProp.setBoolean(false);
    }

    @Test
    public void getValueTest(){
        Assert.assertEquals(internalProp, boolProp.getBoolean());
    }

    @Test
    public void setValueTest(){
        boolProp.setBoolean(true);
        Assert.assertTrue(boolProp.getBoolean());

        boolProp.setBoolean(false);
        Assert.assertFalse(boolProp.getBoolean());
    }

    @Test
    public void getTypeTest(){
        Assert.assertEquals(IToolProperty.ToolPropertyType.BOOLEAN, boolProp.getType());
    }

}

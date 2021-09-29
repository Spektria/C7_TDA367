package C7.Model.Tools.ToolProperties;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class IntegerToolPropertyTest {

    private int internalProp = 1337;
    private IToolProperty intProp = ToolPropertyFactory.createIntegerProperty("testIntProp", "testing", (i) -> internalProp = i, () -> internalProp, 5, 1e6);

    @BeforeEach
    public void init(){
        internalProp = 1337;
        intProp.setInteger(1337);
    }

    @Test
    public void getValueTest(){
        Assert.assertEquals(1337, intProp.getInteger());
    }

    @Test
    public void setValueTest(){
        intProp.setInteger(50);
        Assert.assertEquals(50, intProp.getInteger());

        intProp.setInteger(0);
        Assert.assertEquals(0, intProp.getInteger());
    }

    @Test
    public void getTypeTest(){
        Assert.assertEquals(IToolProperty.ToolPropertyType.INTEGER, intProp.getType());
    }

    @Test
    public void getLimitsTest(){
        Assert.assertEquals(5, intProp.lowerBound());
        Assert.assertEquals(1e6,intProp.upperBound());
    }

}

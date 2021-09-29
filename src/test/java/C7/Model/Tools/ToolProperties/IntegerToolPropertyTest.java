package C7.Model.Tools.ToolProperties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Assertions.assertEquals(1337, intProp.getInteger());
    }

    @Test
    public void setValueTest(){
        intProp.setInteger(50);
        Assertions.assertEquals(50, intProp.getInteger());

        intProp.setInteger(0);
        Assertions.assertEquals(0, intProp.getInteger());
    }

    @Test
    public void getTypeTest(){
        Assertions.assertEquals(IToolProperty.ToolPropertyType.INTEGER, intProp.getType());
    }

    @Test
    public void getLimitsTest(){
        Assertions.assertEquals(5, intProp.lowerBound());
        Assertions.assertEquals(1e6,intProp.upperBound());
    }

}

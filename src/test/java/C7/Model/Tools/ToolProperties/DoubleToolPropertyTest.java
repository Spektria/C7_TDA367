package C7.Model.Tools.ToolProperties;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class DoubleToolPropertyTest {

    private double internalProp  = 1.337;
    private IToolProperty doubleProp = ToolPropertyFactory.createDoubleProperty("testDoubleProp", "testing", (d) -> internalProp = d, () -> internalProp, -0.2, 5.5);

    @BeforeEach
    public void init(){
        internalProp = 1.337;
        doubleProp.setDouble(1.337);
    }

    @Test
    public void getValueTest(){
        Assert.assertEquals(0, Double.compare(1.337, doubleProp.getDouble()));
    }

    @Test
    public void setValueTest(){
        doubleProp.setDouble(5.2);
        Assert.assertEquals(0, Double.compare(5.2, doubleProp.getDouble()));

        doubleProp.setDouble(-0.01);
        Assert.assertEquals(0, Double.compare(-0.01, doubleProp.getDouble()));
    }

    @Test
    public void getTypeTest(){
        Assert.assertEquals(IToolProperty.ToolPropertyType.DOUBLE, doubleProp.getType());
    }

    @Test
    public void getLimitsTest(){
        Assert.assertEquals(-0.2, doubleProp.lowerBound());
        Assert.assertEquals(5.5, doubleProp.upperBound());
    }

}

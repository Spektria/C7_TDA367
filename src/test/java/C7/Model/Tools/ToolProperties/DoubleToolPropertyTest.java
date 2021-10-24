package C7.Model.Tools.ToolProperties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Hugo Ekstrand
 */
public class DoubleToolPropertyTest {

    private double internalProp  = 1.337;
    private IToolProperty doubleProp = ToolPropertyFactory.createDoubleProperty("testDoubleProp", (d) -> internalProp = d, () -> internalProp, -0.2, 5.5);

    @BeforeEach
    public void init(){
        internalProp = 1.337;
        doubleProp.setDouble(1.337);
    }

    @Test
    public void getValueTest(){
        Assertions.assertEquals(0, Double.compare(1.337, doubleProp.getDouble()));
    }

    @Test
    public void setValueTest(){
        doubleProp.setDouble(5.2);
        Assertions.assertEquals(0, Double.compare(5.2, doubleProp.getDouble()));

        doubleProp.setDouble(-0.01);
        Assertions.assertEquals(0, Double.compare(-0.01, doubleProp.getDouble()));
    }

    @Test
    public void getTypeTest(){
        Assertions.assertEquals(IToolProperty.ToolPropertyType.DOUBLE, doubleProp.getType());
    }

    @Test
    public void getLimitsTest(){
        Assertions.assertEquals(-0.2, doubleProp.lowerBound());
        Assertions.assertEquals(5.5, doubleProp.upperBound());
    }

}

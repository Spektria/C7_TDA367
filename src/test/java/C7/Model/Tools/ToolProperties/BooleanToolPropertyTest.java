package C7.Model.Tools.ToolProperties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Assertions.assertEquals(internalProp, boolProp.getBoolean());
    }

    @Test
    public void setValueTest(){
        boolProp.setBoolean(true);
        Assertions.assertTrue(boolProp.getBoolean());

        boolProp.setBoolean(false);
        Assertions.assertFalse(boolProp.getBoolean());
    }

    @Test
    public void getTypeTest(){
        Assertions.assertEquals(IToolProperty.ToolPropertyType.BOOLEAN, boolProp.getType());
    }

}

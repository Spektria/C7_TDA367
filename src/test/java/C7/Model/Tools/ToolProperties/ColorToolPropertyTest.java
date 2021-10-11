package C7.Model.Tools.ToolProperties;

import C7.Model.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Hugo Ekstrand
 */
public class ColorToolPropertyTest {

    private Color internalProp = new Color(1f, 1f, 1f, 1f);
    private IToolProperty boolProp = ToolPropertyFactory.createColorProperty("testColorProp", (c) -> internalProp = c, () -> internalProp);

    @BeforeEach
    public void init(){
        internalProp = new Color(1f, 1f, 1f, 1f);
        boolProp.setColor(new Color(1f, 1f, 1f, 1f));
    }

    @Test
    public void getValueTest(){
        Assertions.assertEquals(internalProp, boolProp.getColor());
    }

    @Test
    public void setValueTest(){
        boolProp.setColor(new Color(0f, 1f, 1f, 1f));
        Assertions.assertEquals(new Color(0f, 1f, 1f, 1f), boolProp.getColor());

        boolProp.setColor(new Color(0f, 0.2f, 1f, 1f));
        Assertions.assertEquals(new Color(0f, 0.2f, 1f, 1f), boolProp.getColor());
    }

    @Test
    public void getTypeTest(){
        Assertions.assertEquals(IToolProperty.ToolPropertyType.COLOR, boolProp.getType());
    }

}

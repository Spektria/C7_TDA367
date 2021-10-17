package C7.Util;


import C7.Util.ResourceIO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResourceIOTest {

    @Test
    public void globalResourceIO() {
        Assertions.assertNotEquals(null, ResourceIO.getGlobalResource("C7/Services/DeepIO"));
        Assertions.assertEquals(null, ResourceIO.getGlobalResource("DeepIO"));
        Assertions.assertNotEquals(null, ResourceIO.getGlobalResource("shallowIO"));
    }

    @Test
    public void packageResourceIO() {
        Assertions.assertNotEquals(null, ResourceIO.getPackageResource(this, "DeepIO"));
    }
}

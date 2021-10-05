package C7.IO;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResourceIOTest {

    @Test
    public void globalResourceIO() {
        Assertions.assertNotEquals(null, ResourceIO.getGlobalResource("C7/IO/DeepIO"));
        Assertions.assertEquals(null, ResourceIO.getGlobalResource("DeepIO"));
        Assertions.assertNotEquals(null, ResourceIO.getGlobalResource("ShallowIO"));
    }

    @Test
    public void packageResourceIO() {
        Assertions.assertNotEquals(null, ResourceIO.getPackageResource(this, "DeepIO"));
    }
}

package C7.IO;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResourceIOTest {

    @Test
    public void globalResourceIO() {
        Assertions.assertNotEquals(null, ResourceIO.getGlobalResource(this, "C7/IO/DeepIO"));
        Assertions.assertEquals(null, ResourceIO.getGlobalResource(this, "DeepIO"));
    }

    @Test
    public void packageResourceIOIllegalArgument(){
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> ResourceIO.getGlobalResource(this, "/DeepIO"));
    }

    @Test
    public void packageResourceIO() {
        Assertions.assertNotEquals(null, ResourceIO.getPackageResource(this, "DeepIO"));
    }
}

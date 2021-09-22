package C7.IO;

import org.junit.Assert;
import org.junit.Test;

public class ResourceIOTest {

    @Test
    public void globalResourceIO() {
        Assert.assertNotEquals(null, ResourceIO.getGlobalResource(this, "C7\\IO\\DeepIO"));
        Assert.assertEquals(null, ResourceIO.getGlobalResource(this, "DeepIO"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void packageResourceIOIllegalArgument(){
        ResourceIO.getGlobalResource(this, "/DeepIO");
    }

    @Test
    public void packageResourceIO() {
        Assert.assertNotEquals(null, ResourceIO.getPackageResource(this, "DeepIO"));
    }
}

package C7.IO;

import C7.Model.Project;
import C7.Util.Vector2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProjectIOTest {
    @Test
    public void saveLoadCombo(){
        Project a = new Project(10,10);
        a.createLayer(10,10,new Vector2D(0,0));

        String property = "java.io.tmpdir";
        String tempDir = System.getProperty(property);
        String saveLocation = tempDir + "Pro.project";

        ProjectIO.saveProject(a, saveLocation);
        a = ProjectIO.loadProject(saveLocation);
        Assertions.assertNotEquals(a, null);
    }
}

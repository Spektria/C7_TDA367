package C7.Services;

import C7.Model.IProject;
import C7.Model.ProjectFactory;
import C7.Util.Vector2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class ProjectIOTest {
    @Test
    public void saveLoadCombo() throws IOException {
        IProject project = ProjectFactory.createProject("tst",10,10);
        project.createLayer(10,10,new Vector2D(0,0));

        //Looks quite disgusting but should work for this test
        String saveLocation = File.createTempFile("projectsave", "project").getAbsolutePath();

        ProjectIO.saveProject(project, saveLocation);
        project = ProjectIO.loadProject(saveLocation);
        Assertions.assertNotEquals(project, null);
    }
}

package C7.Services;

import C7.Model.IProject;
import C7.Model.Layer.ILayer;
import C7.Model.ProjectFactory;
import C7.Model.Tools.ToolFactory;
import C7.Util.Color;
import C7.Util.Vector2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ImageExportServiceTest {

    @Test
    public void exportAndIntegrityTest(){
        IProject proj = ProjectFactory.createProjectWithBaseLayer("Testproj", 100, 100);

        // Fill with (0,1,0,1)
        proj.applyTool(ToolFactory.CreateFillBucket(2f, new Color(0,1,0,1)),
                new Vector2D(5,5), new Vector2D(5,5));

        // Draw line from (10,10) to (30,30)
        proj.applyTool(ToolFactory.CreateCircularBrush(10, new Color(1,0,0,1)),
                new Vector2D(10, 10), new Vector2D(30, 30));

        ILayer layer = proj.getLayer(proj.getActiveLayerID());
        Assertions.assertNotNull(layer);

        // Save image and then import it. Check so that both are the same.
        ServiceFactory.createImageExportService(proj, ImageFormatName.PNG, "tstImg.png").execute();
        ServiceFactory.createLayerImportService("tstImg.png", imported
                -> Assertions.assertEquals(layer, imported)).execute();
    }
}

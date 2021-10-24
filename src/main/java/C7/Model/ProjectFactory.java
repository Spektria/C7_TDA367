package C7.Model;

import C7.Model.Layer.ILayer;
import C7.Model.Layer.LayerFactory;
import C7.Util.Color;
import C7.Util.Vector2D;

/**
 * A factory for creating {@link IProject}.
 * @author Hugo Ekstrand
 */
public final class ProjectFactory {

    /**
     * Creates a new instance of a IProject.
     * @param name the name of the project
     * @param width the width of the project's drawing surface
     * @param height the height of the project's drawing surface.
     * @return the created project.
     */
    public static IProject createProject(String name, int width, int height){
        return new Project(name, width, height);
    }

    /**
     * Creates a new instance of IProject with a transparent layer the same size as the project.
     * @param name the name of the project
     * @param width the width of the project's drawing surface
     * @param height the height of the project's drawing surface.
     * @return the created project.
     */
    public static IProject createProjectWithBaseLayer(String name, int width, int height){
        IProject proj = createProject(name, width, height);

        ILayer layer = LayerFactory.createDefaultLayer(width, height, new Color(0, 0, 0, 0), new Vector2D(0, 0), 0, new Vector2D(1, 1));
        int layerId = proj.addLayer(layer);

        proj.setActiveLayer(layerId);
        return proj;
    }
}

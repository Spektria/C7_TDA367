package C7.Model;

import C7.Model.Layer.Layer;
import C7.Util.Color;

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
        proj.addLayer(new Layer(width, height, new Color(0, 0, 0, 0)));
        return proj;
    }
}

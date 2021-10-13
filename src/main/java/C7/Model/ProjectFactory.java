package C7.Model;

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
}

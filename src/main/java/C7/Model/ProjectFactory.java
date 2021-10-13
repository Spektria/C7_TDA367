package C7.Model;

public final class ProjectFactory {
    public static IProject createProject(int width, int height){
        return new Project(width, height);
    }
}

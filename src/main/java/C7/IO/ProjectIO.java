package C7.IO;

import C7.Model.Project;

import java.io.*;
import java.net.URL;

/**
 * ProjectIO contains static methods for the saving and loading of {@link Project} objects.
 */
public class ProjectIO {
    /**
     * Saves a {@link Project} at the specified path.
     * @param project the project to save
     * @param path the absolute path to save the Project at
     */
    public static void saveProject(Project project, String path) {
        try {
            FileOutputStream fileStream = new FileOutputStream(path);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject(project);
            objectStream.close();
            fileStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a {@link Project} from the specified path.
     * @param path the absolute path to load the Project from
     * @return The loaded project. Returns null if error has occurred.
     */
    public static Project loadProject(String path) {
        Project loadedproject = null;
        try {
            FileInputStream fileStream = new FileInputStream(path);
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);
            loadedproject = (Project)objectStream.readObject();
            objectStream.close();
            fileStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return loadedproject;
    }
}

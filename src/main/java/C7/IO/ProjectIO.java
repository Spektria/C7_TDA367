package C7.IO;

import C7.Model.IProject;

import java.io.*;
import java.net.URL;

/**
 * ProjectIO contains static methods for the saving and loading of {@link IProject} objects.
 */
public class ProjectIO {
    /**
     * Saves a {@link IProject} at the specified path.
     * @param project the project to save
     * @param path the absolute path to save the Project at
     */
    public static void saveProject(IProject project, String path) {
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
     * Loads a {@link IProject} from the specified path.
     * @param path the absolute path to load the Project from
     * @return The loaded project. Returns null if error has occurred.
     */
    public static IProject loadProject(String path) {
        IProject loadedproject = null;
        try {
            FileInputStream fileStream = new FileInputStream(path);
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);
            loadedproject = (IProject)objectStream.readObject();
            objectStream.close();
            fileStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return loadedproject;
    }
}

package C7.Services;

import C7.Model.IProject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.function.Consumer;

/**
 * ProjectLoaderService loads serialized projects from a file.
 *
 * @author Isak Gustafsson
 * @author Hugo Ekstrand
 */
class ProjectLoaderService implements IService {
    private final Consumer<IProject> doAfter;
    private final String path;

    ProjectLoaderService(String path, Consumer<IProject> doAfter){
        this.path = path;
        this.doAfter = doAfter;
    }


    @Override
    public void execute() {
        IProject loadedproject;
        try {
            FileInputStream fileStream = new FileInputStream(path);
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);
            loadedproject = (IProject)objectStream.readObject();
            objectStream.close();
            fileStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        doAfter.accept(loadedproject);
    }
}

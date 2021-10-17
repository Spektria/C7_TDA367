package C7.Services;

import C7.Model.IProject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * ProjectSaverService saves projects as a serialized file.
 */
class ProjectSaverService implements IService {
    private final String path;
    private final IProject project;


    ProjectSaverService(String path, IProject project) {
        this.path = path;
        this.project = project;
    }


    @Override
    public void execute() {
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
}

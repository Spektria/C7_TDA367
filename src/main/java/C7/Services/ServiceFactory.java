package C7.Services;

import C7.Model.IProject;
import C7.Model.Layer.ILayer;

import java.util.function.Consumer;

/**
 * Factory for creating {@link IService, services}.
 */
public final class ServiceFactory {

    /**
     * Creates a service for exporting images from a {@link IProject}.
     * @param project the project which image will be exported
     * @param format the exported image format
     * @param path the location and file name the image will be exported to
     * @return the service
     */
    public static IService createImageExportService(IProject project, ImageFormatName format, String path){
        return new ImageExportService(project, format, path);
    }

    /**
     * Creates a service for importing {@link ILayer, layers} from an image file.
     * @param path the location of the layer to be imported
     * @param doAfter what will be done with the imported layer
     * @return the service
     */
    public static IService createLayerImportService(String path, Consumer<ILayer> doAfter){
        return new LayerImportService(path, doAfter);
    }

    /**
     * Creates a service for loading a {@link IProject}.
     * @param path the location of the serialized project.
     * @param doAfter
     * @return
     */
    public static IService createProjectLoaderService(String path, Consumer<IProject> doAfter){
        return new ProjectLoaderService(path, doAfter);
    }

    public static IService createProjectSaverService(String path, IProject project){
        return new ProjectSaverService(path, project);
    }

}

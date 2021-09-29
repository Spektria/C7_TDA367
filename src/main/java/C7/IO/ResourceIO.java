package C7.IO;

import java.net.URL;

/**
 * ResourceIO contains static methods facilitating easy creation of {@link URL} pointing to objects in program resources.
 */
public class ResourceIO {

    /**
     * Return file system path of resource file from global scope
     * @param file The file name to retrive. Folder levels seperated by "/"
     * @return URL to resource from global scope, NULL if resource does not exist
     */
    public static URL getGlobalResource(String file){
        //Make file path global in scope unless it already is
        if (file.startsWith("/") == false) file = "/" + file;
        return ResourceIO.class.getResource(file);
    }

    /**
     * Return file system path of resource file from package scope. If parameter file starts with "/" a global scope will instead be used.
     * @param classToRetriveFrom The class to request the file from. The class' package determines the resource folder to be searched.
     * @param file The file name to retrive. Folder levels seperated by "/"
     * @return URL to resource from global scope, NULL if resource does not exist
     */
    public static URL getPackageResource(Object classToRetriveFrom, String file){
        return classToRetriveFrom.getClass().getResource(file);
    }
}

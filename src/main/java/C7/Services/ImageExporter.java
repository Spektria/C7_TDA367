package C7.Services;

import C7.Model.IProject;
import C7.Util.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A service for exporting images from an {@link IProject}.
 * @author Hugo Ekstrand
 */
public final class ImageExporter {

    private final IProject project;

    /**
     * Creates an instance of this class
     * @param project the project which will be worked on.
     */
    public ImageExporter(IProject project){
        this.project = project;
    }

    /**
     * Exports this ImageExporter's project's image to a given file path as a .png image.
     * @param path the path the image will be exported to.
     */
    public void export(String path){
        BufferedImage img = new BufferedImage(project.getWidth(),project.getHeight(),BufferedImage.TYPE_INT_ARGB_PRE);
        Color[][] pixels = project.renderProject(0,0,img.getWidth(), img.getHeight());

        for(int x = 0; x < img.getWidth(); x++){
            for(int y = 0; y < img.getHeight(); y++){
                Color color = pixels[x][y];
                img.setRGB(x,y, new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).getRGB());
            }
        }

        File file = new File(path);
        try {
            ImageIO.write(img, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

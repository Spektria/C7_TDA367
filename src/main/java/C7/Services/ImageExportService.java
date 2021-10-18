package C7.Services;

import C7.Model.IProject;
import C7.Util.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * A service for exporting images from an {@link IProject}.
 * @author Hugo Ekstrand
 */
class ImageExportService implements IService {

    private final IProject proj;
    private final ImageFormatName format;
    private final File file;

    /**
     * Creates an instance of this class
     * @param proj the project which will be worked on.
     */
    ImageExportService(IProject proj, ImageFormatName format, String exportPath){
        Objects.requireNonNull(proj);
        Objects.requireNonNull(format);
        Objects.requireNonNull(exportPath);

        this.proj = proj;
        this.format = format;
        this.file = new File(exportPath);
    }

    private void copyToBufferedImage(Color[][] pixels, BufferedImage img) {
        for(int x = 0; x < img.getWidth(); x++){
            for(int y = 0; y < img.getHeight(); y++){
                Color color = pixels[x][y];
                img.setRGB(x,y, new java.awt.Color(
                        color.getRed(),
                        color.getGreen(),
                        color.getBlue(),
                        color.getAlpha()
                ).getRGB());
            }
        }
    }

    @Override
    public void execute() {
        BufferedImage img = new BufferedImage(proj.getWidth(),proj.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
        Color[][] pixels = proj.renderProject(0,0,img.getWidth(), img.getHeight());

        copyToBufferedImage(pixels, img);

        try {
            ImageIO.write(img, format.toString(), file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

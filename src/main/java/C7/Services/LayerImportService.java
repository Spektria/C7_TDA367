package C7.Services;

import C7.Model.Layer.ILayer;
import C7.Util.Color;
import C7.Model.Layer.Layer;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

import java.io.FileInputStream;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * LayerIO contains any input/output operations to/from a {@link Layer}.
 */
class LayerImportService implements IService {

    private final String filePath;
    private final Consumer<ILayer> doAfter;

    /**
     * Constructs a new layer from a filepath
     * ONLY SUPPORTS 8bit image of type: BMP, GIF, JPEG, PNG.
     * @param path The path to the requested image
     * @return New Layer created from image data in file, if error has occurred returns NULL
     */
    LayerImportService(String path, Consumer<ILayer> doAfter){
        Objects.requireNonNull(path);
        Objects.requireNonNull(doAfter);

        this.filePath = path;
        this.doAfter = doAfter;
    }


    @Override
    public void execute() {
        try{
            FileInputStream fileStream = new FileInputStream(filePath);
            Image fileImage = new Image(fileStream);

            int width = (int)fileImage.getWidth();
            int height = (int)fileImage.getHeight();
            PixelReader reader = fileImage.getPixelReader();

            Color[][] colorData = new Color[width][];

            for (int x = 0; x < width; x++) {
                colorData[x] = new Color[height];
                for (int y = 0; y < height; y++) {
                    javafx.scene.paint.Color color = reader.getColor(x,y);

                    float r = (float)color.getRed();
                    float g = (float)color.getGreen();
                    float b = (float)color.getBlue();
                    float a = (float)color.getOpacity();

                    colorData[x][y] = new Color(r,g,b,a);
                }
            }

            doAfter.accept(new Layer(colorData));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

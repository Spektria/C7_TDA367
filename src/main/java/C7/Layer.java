package C7;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import java.io.FileInputStream;

public class Layer implements ILayer {

    Color[][] pixels;

    /**
     * Constructs a new empty layer
     * @param width The desired width of the new layer in pixels
     * @param height The desired height of the new layer in pixels
     */
    public Layer(int width, int height) {
        pixels = new Color[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[x][y] = new Color(0, 0, 0, 0);
            }
        }
    }

    /**
     * Constructs a new layer from a 2D matrix of Color
     * @param colorMatrix The matrix of color data of the new layer
     */
    public Layer(Color[][] colorMatrix) {
        /*pixels = colorMatrix; OPTIMIZATION this works faster but is technically bad because
        * it leaves an open reference somewhere to the new layer's color data
        * DEFENSIVE COPYING */

        pixels = new Color[colorMatrix.length][];

        for (int y = 0; y < colorMatrix.length; y++) {
            pixels[y] = new Color[colorMatrix[y].length];

            for (int x = 0; x < colorMatrix[y].length; x++) {
                Color oldColor = colorMatrix[y][x];
                //Would like a copy method for copying color data instead of this repeating pattern of GET
                pixels[y][x] = new Color(oldColor.getRed(), oldColor.getGreen(), oldColor.getBlue(), oldColor.getAlpha());
            }
        }
    }

    /**
     * Constructs a new layer from a filepath
     * ONLY SUPPORTS 8bit image of type: BMP, GIF, JPEG, PNG.
     * @param filePath The path to the requested image
     * @return New Layer created from image data in file, if error has occurred returns NULL
     */
    public static Layer fromFile(String filePath){
        try{
            FileInputStream fileStream = new FileInputStream(filePath);
            Image fileImage = new Image(fileStream);

            int width = (int)fileImage.getWidth();
            int height = (int)fileImage.getHeight();
            PixelReader reader = fileImage.getPixelReader();

            Color[][] colorData = new Color[height][];

            for (int y = 0; y < height; y++) {
                colorData[y] = new Color[width];
                for (int x = 0; x < width; x++) {
                    javafx.scene.paint.Color color = reader.getColor(x,y);
                    float r = (float)color.getRed();
                    float g = (float)color.getGreen();
                    float b = (float)color.getBlue();
                    float a = (float)color.getOpacity();

                    colorData[y][x] = new Color(r,g,b,a);
                }
            }

            return new Layer(colorData);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Color getPixel(int x, int y) {
        //Throws an error if out of bounds, but I think that's fine?
        return pixels[x][y];
    }

    @Override
    public void setPixel(int x, int y, Color color) {
        //Maybe better to check if in bounds explicitly?
        if ( x >= 0 && x < getWidth() && x >= 0 && x < getHeight())
        pixels[x][y] = color;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public void setWidth(int width) {

    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void setHeight(int height) {

    }
}

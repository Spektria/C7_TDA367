package C7.Model.Layer;

import C7.Model.Color;
import C7.Model.Vector.Vector2D;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents a basic image layer.
 * @author Elias Ersson
 * @author Love Gustafsson
 * @version 1.0
 */
public class Layer implements ILayer {

    private Color[][] pixels;   // This layer's pixel data.
    private int width;          // The width, in pixels, of this layer.
    private int height;         // The height, in pixels of this layer.

    /**
     * Constructs a new empty layer
     * @param width The desired width of the new layer in pixels
     * @param height The desired height of the new layer in pixels
     */
    public Layer(int width, int height, Color color) {

        pixels = new Color[width][height];
        this.width  = width;
        this.height = height;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // Copy color values
                pixels[x][y] = new Color(color);
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

        for (int x = 0; x < colorMatrix.length; x++) {
            pixels[x] = new Color[colorMatrix[x].length];

            for (int y = 0; y < colorMatrix[y].length; y++) {
                Color oldColor = colorMatrix[x][y];
                //Would like a copy method for copying color data instead of this repeating pattern of GET
                pixels[x][y] = new Color(oldColor.getRed(), oldColor.getGreen(), oldColor.getBlue(), oldColor.getAlpha());
            }
        }
    }

    @Override
    public Color getPixel(int x, int y) {

        // Check if pixel is on this layer.
        // Maybe we should change this to an exception later?
        if (!isPixelOnLayer(x, y)) {
            return null;
        }
        // Get the color at the specified position.
        Color pixelColor = pixels[x][y];

        return new Color(pixelColor);
    }

    @Override
    public void setPixel(int x, int y, Color color) {
        // Check if pixel is on this layer.
        // Maybe we should change this to an exception later?
        if (!isPixelOnLayer(x, y)) {
            return;
        }

        pixels[x][y] = new Color(color);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int width) {
        setDimensions(width, getHeight());
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        setDimensions(getWidth(), height);
    }

    @Override
    public Boolean isPixelOnLayer(int x, int y) {
        return (x >= 0 && x < width && y >= 0 && y < height);
    }

    @Override
    public void setRotation(float angle) {
        // TODO
    }

    @Override
    public float getRotation() {
        return 0;
    }

    @Override
    public void setPosition(Vector2D position) {
        // TODO
    }

    @Override
    public Vector2D getPosition() {
        return new Vector2D(0, 0);
    }

    @Override
    public void setScale(Vector2D scale) {
        // TODO
    }

    @Override
    public Vector2D getScale() {
        return new Vector2D(1, 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Layer layer = (Layer) o;
        return width == layer.width && height == layer.height && Arrays.deepEquals(pixels, layer.pixels);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(width, height);
        result = 31 * result + Arrays.hashCode(pixels);
        return result;
    }

    private void setDimensions(int width, int height) {
        Color[][] newPixels = new Color[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (!isPixelOnLayer(x, y)) {
                    newPixels[x][y] = new Color(0, 0, 0, 1);
                }

                newPixels[x][y] = getPixel(x, y);
            }
        }

        pixels      = newPixels;
        this.width  = width;
        this.height = height;
    }
}

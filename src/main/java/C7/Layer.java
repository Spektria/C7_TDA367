package C7;

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

    @Override
    public Color getPixel(int x, int y) {
        //Throws an error if out of bounds, but I think that's fine?
        return pixels[x][y];
    }

    @Override
    public void setPixel(int x, int y, Color color) {
        //Maybe better to check if in bounds explicitly?
        if ( x > 0 && x <)
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

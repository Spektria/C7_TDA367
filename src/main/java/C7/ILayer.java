package C7;

public interface ILayer {

    /**
     * Gets the current color of a pixel in the layer.
     * @param x X coordinate of the pixel
     * @param y Y coordinate of the pixel
     * @return Color of the given pixel
     */
    Color getPixel(int x, int y);

    /**
     * @param x X coordinate of the pixel
     * @param y Y coordinate of the pixel
     * @param color The color to set the pixel to
     */
    void setPixel(int x, int y, Color color);

    /**
     * Gets the current width of the layer
     * @return The width of the layer in pixels
     */
    int getWidth();

    /**
     * Sets the width of the layer
     * @param width The desired width of the layer in pixels
     */
    void setWidth(int width);

    /**
     *
     * Gets the current height of the layer
     * @return The height of the layer in pixels
     */
    int getHeight();

    /**
     * Set the height of the layer
     * @param height The desired height of the layer in pixels
     */
    void setHeight(int height);



}

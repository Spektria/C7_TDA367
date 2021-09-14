package C7.Layer;

import C7.Color;

/**
 * ILayer is an interface for layers that are part of a picture. Layers hold
 * some sort of graphical data and can have their pixel values be set and be
 * retrieved.
 *
 * @author Elias Ersson
 * @author Love Gustafsson
 * @version 1.1
 */
public interface ILayer {

    /**
     * Gets the pixel color at the specified picture-space x and y co-ordinates.
     * @param x The x co-ordinate in picture space to get the color at.
     * @param y The y co-ordinate in picture space to get the color at.
     * @return The color at the specified co-ordinates.
     */
    Color getPixel(int x, int y);

    /**
     * Sets the pixel color at the specified picture-space x and y co-ordinates.
     * @param x The x co-ordinate in picture space to set the color at.
     * @param y The y co-ordinate in picture space to set the color at.
     * @param color The color to set the pixel at the specified co-ordinates to.
     */
    void setPixel(int x, int y, Color color);

    /**
     * Gets the current width of the layer.
     * @return The width of the layer in pixels.
     */
    int getWidth();

    /**
     * Sets the width of the layer.
     * @param width The desired width of the layer in pixels.
     */
    void setWidth(int width);

    /**
     *
     * Gets the current height of the layer.
     * @return The height of the layer in pixels.
     */
    int getHeight();

    /**
     * Set the height of the layer.
     * @param height The desired height of the layer in pixels.
     */
    void setHeight(int height);

    /**
     * Checks if the pixel at the specified picture-space co-ordinate is located
     * within the boundaries of this layer.
     * @param x The x co-ordinate of the pixel.
     * @param y The y co-ordinate of the pixel.
     * @return True if the pixel is within this layer, false if the pixel is
     * outside this layer.
     */
    Boolean isPixelOnLayer(int x, int y);
}

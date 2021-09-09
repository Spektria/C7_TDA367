package C7;

/**
 * <code>Color</code> represents an RGBA8888 color value, which is the color
 * format used for color processing in C7Paint.
 */
public final class Color {

    private int r;  // Red value [0-255]
    private int g;  // Green value [0-255]
    private int b;  // Blue value [0-255]
    private int a;  // Alpha value [0-255]

    /**
     * Constructs a new RGBA888 color with the specified values for each
     * channel. The values of the channels should be in the range of [0-255],
     * and will be clamped if they are not.
     * @param r Value of red channel
     * @param b Value of blue channel
     * @param g Value of green channel
     * @param a Value of alpha channel
     */
    public Color(int r, int b, int g, int a) {
        setRed(r);
        setBlue(b);
        setGreen(g);
        setAlpha(a);
    }

    /**
     * Gets the current value of the red channel of this color. The value is
     * between 0, representing no intensity, and 255, representing maximum
     * intensity.
     * @return Value of red channel
     */
    public int getRed() {
        return r;
    }

    /**
     * Sets the current value of the red channel of this color. The value
     * should be in the range [0-255]. If the value is not in this range, it
     * will be clamped to be within the range.
     * @param r Red value to set
     */
    public void setRed(int r) {
        if (r > 255) {
            this.r = 255;
            return;
        }

        if (r < 0) {
            this.r = 0;
            return;
        }

        this.r = r;
    }

    /**
     * Gets the current value of the green channel of this color. The value is
     * between 0, representing no intensity, and 255, representing maximum
     * intensity.
     * @return Value of green channel
     */
    public int getGreen() {
        return g;
    }

    /**
     * Sets the current value of the green channel of this color. The value
     * should be in the range [0-255]. If the value is not in this range, it
     * will be clamped to be within the range.
     * @param g Green value to set
     */
    public void setGreen(int g) {
        if (g > 255) {
            this.g = 255;
            return;
        }

        if (g < 0) {
            this.g = 0;
            return;
        }

        this.g = g;
    }

    /**
     * Gets the current value of the blue channel of this color. The value is
     * between 0, representing no intensity, and 255, representing maximum
     * intensity.
     * @return Value of blue channel
     */
    public int getBlue() {
        return b;
    }

    /**
     * Sets the current value of the blue channel of this color. The value
     * should be in the range [0-255]. If the value is not in this range, it
     * will be clamped to be within the range.
     * @param b Blue value to set
     */
    public void setBlue(int b) {
        if (b > 255) {
            this.b = 255;
            return;
        }

        if (b < 0) {
            this.b = 0;
            return;
        }

        this.b = b;
    }

    /**
     * Gets the current value of the alpha channel of this color. The value is
     * between 0, representing no intensity, and 255, representing maximum
     * intensity.
     * @return Value of alpha channel
     */
    public int getAlpha() {
        return a;
    }

    /**
     * Sets the current value of the alpha channel of this color. The value
     * should be in the range [0-255]. If the value is not in this range, it
     * will be clamped to be within the range.
     * @param a Alpha value to set
     */
    public void setAlpha(int a) {
        if (a > 255) {
            this.a = 255;
            return;
        }

        if (a < 0) {
            this.a = 0;
            return;
        }

        this.a = a;
    }
}

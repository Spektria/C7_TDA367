package C7;

/**
 * <code>Color</code> represents an RGBA32f32f32f32f color value, which is the
 * color format used for color processing in C7Paint. A color has four
 * channels, red, green, blue, and alpha. These are represented by a floating
 * point value, usually between 0 and 1, where 0 is no intensity and 1 is
 * maximum intensity. The color format supports channel intensities above 1 for
 * high dynamic range imaging.
 * @author Love Gustafsson
 * @version 1.1
 */
public final class Color {

    private float r;  // Red value
    private float g;  // Green value
    private float b;  // Blue value
    private float a;  // Alpha value

    /**
     * Constructs a new RGBA32f32f32f32f color with the specified values for
     * each channel. The values of the channels should be between [0-1] for
     * regular display colors, or above 1 for high dynamic range colors. The
     * value will be clamped to 0 if it is below 0.
     * @param r Value of red channel
     * @param b Value of blue channel
     * @param g Value of green channel
     * @param a Value of alpha channel
     */
    public Color(float r, float b, float g, float a) {
        setRed(r);
        setBlue(b);
        setGreen(g);
        setAlpha(a);
    }

    /**
     * Gets the current value of the red channel of this color. The value is
     * between 0, representing no intensity, and 1, representing maximum
     * intensity, or above 1, representing colors above maximum display
     * intensity.
     * @return Value of red channel
     */
    public float getRed() {
        return r;
    }

    /**
     * Sets the current value of the red channel of this color. The value
     * should be in the range [0-1] for regular display colors, or above 0 for
     * high dynamic range colors. The value will be clamped to 0 if it is below
     * 0.
     * @param r Red value to set
     */
    public void setRed(float r) {
        if (r < 0) {
            this.r = 0;
            return;
        }

        this.r = r;
    }

    /**
     * Gets the current value of the green channel of this color. The value is
     * between 0, representing no intensity, and 1, representing maximum
     * intensity, or above 1, representing colors above maximum display
     * intensity.
     * @return Value of green channel
     */
    public float getGreen() {
        return g;
    }

    /**
     * Sets the current value of the green channel of this color. The value
     * should be in the range [0-1] for regular display colors, or above 0 for
     * high dynamic range colors. The value will be clamped to 0 if it is below
     * 0.
     * @param g Green value to set
     */
    public void setGreen(float g) {
        if (g < 0) {
            this.g = 0;
            return;
        }

        this.g = g;
    }

    /**
     * Gets the current value of the blue channel of this color. The value is
     * between 0, representing no intensity, and 1, representing maximum
     * intensity, or above 1, representing colors above maximum display
     * intensity.
     * @return Value of blue channel
     */
    public float getBlue() {
        return b;
    }

    /**
     * Sets the current value of the blue channel of this color. The value
     * should be in the range [0-1] for regular display colors, or above 0 for
     * high dynamic range colors. The value will be clamped to 0 if it is below
     * 0.
     * @param b Blue value to set
     */
    public void setBlue(float b) {
        if (b < 0) {
            this.b = 0;
            return;
        }

        this.b = b;
    }

    /**
     * Gets the current value of the alpha channel of this color. The value is
     * between 0, representing no intensity, and 1, representing maximum
     * intensity, or above 1, representing colors above maximum display
     * intensity.
     * @return Value of alpha channel
     */
    public float getAlpha() {
        return a;
    }

    /**
     * Sets the current value of the alpha channel of this color. The value
     * should be in the range [0-1] for regular display colors, or above 0 for
     * high dynamic range colors. The value will be clamped to 0 if it is below
     * 0.
     * @param a Alpha value to set
     */
    public void setAlpha(float a) {
        if (a < 0) {
            this.a = 0;
            return;
        }

        this.a = a;
    }
}

package C7.Util;

import java.io.Serializable;
import java.util.Objects;

/**
 * <code>Color</code> represents an RGBA32f32f32f32f color value, which is the
 * color format used for color processing in C7Paint. A color has four
 * channels, red, green, blue, and alpha. These are represented by a floating
 * point value, usually between 0 and 1, where 0 is no intensity and 1 is
 * maximum intensity. The color format supports channel intensities above 1 for
 * high dynamic range imaging.
 * @author Love Svalby
 * @version 1.3
 */
public final class Color implements Serializable {

    // Preset colors
    public final static Color BLACK = new Color(0, 0, 0, 1);
    public final static Color WHITE = new Color(1, 1, 1, 1);
    public final static Color RED = new Color(1, 0, 0, 1);
    public final static Color GREEN = new Color(0, 1, 0, 1);
    public final static Color BLUE = new Color(0, 0, 1, 1);
    public final static Color YELLOW = new Color(1, 1, 0, 1);
    public final static Color FUCHSIA = new Color(1, 0, 1, 1);
    public final static Color CYAN = new Color(0, 1, 1, 1);
    public final static Color ORANGE = new Color(1, 0.5f, 0, 1);
    public final static Color PURPLE = new Color(0.5f, 0, 1, 1);
    public final static Color PINK = new Color(1.0f, 0.5f, 0.5f, 1);
    public final static Color GRAY = new Color(0.5f, 0.5f, 0.5f, 1);
    public final static Color DARK_GRAY = new Color(0.25f, 0.25f, 0.25f, 1);
    public final static Color LIGHT_GRAY = new Color(0.75f, 0.75f, 0.75f, 1);
    public final static Color OFFWHITE = new Color(0.95f, 0.95f, 0.9f, 1);
    public final static Color CELADON = new Color(0.675f, 0.882f, 0.686f, 1);
    public final static Color DARK_BLUE = new Color(0, 0, 0.5f, 1);
    public final static Color DARKER_BLUE = new Color(0, 0, 0.25f, 1);
    public final static Color VERY_DARK_BLUE = new Color(0, 0, 0.1f, 1);
    public final static Color LOVES_FAVOURITE_SHADE_OF_BLUE = new Color(0.08f, 0.532f, 0.8f, 1);
    public final static Color TRANSLUCENT_LIME = new Color(0.2f, 1, 0f, 0.5f);

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
    public Color(float r, float g, float b, float a) {
        setRed(r);
        setGreen(g);
        setBlue(b);
        setAlpha(a);
    }

    /**
     * Constructs a new RGBA32f32f32f32f color object by copying an existing
     * color object.
     * @param color The color object to copy.
     */
    public Color(Color color) {
        setRed(color.getRed());
        setGreen(color.getGreen());
        setBlue(color.getBlue());
        setAlpha(color.getAlpha());
    }

    /**
     * Gets the linear difference between two colors.
     * @param color1 The first color to compare.
     * @param color2 The second color to compare.
     * @return The linear difference between the colors.
     */
    public static float getColorDifference(Color color1, Color color2) {
        float dr = color2.getRed() - color1.getRed();
        float dg = color2.getGreen() - color1.getGreen();
        float db = color2.getBlue() - color1.getBlue();
        float da = color2.getAlpha() - color1.getAlpha();

        // Get linear distance between the colors.
        return (float)Math.sqrt(dr * dr + dg * dg + db * db + da * da);
    }

    /**
     * Blends two colors using simple alpha blending.
     * The resulting color is
     * <code>result = dest * (1 - src.alpha) + src * src.alpha</code>
     * @param dest
     * @param src
     * @return
     */
    public static Color blend(Color dest, Color src) {
        float r, g, b, a;

        r = dest.getRed() * (1 - src.getAlpha()) + src.getRed() * src.getAlpha();
        g = dest.getGreen() * (1 - src.getAlpha()) + src.getGreen() * src.getAlpha();
        b = dest.getBlue() * (1 - src.getAlpha()) + src.getBlue() * src.getAlpha();
        a = dest.getAlpha() * (1 - src.getAlpha()) + src.getAlpha() * src.getAlpha();

        return new Color(r, g, b, a);
    }

    /**
     * Adds two colors.
     * The resulting color is
     * <code>result = dest + src * src.alpha</code>
     * @param dest The destination color.
     * @param src The source color.
     * @return The source color added to the destination color.
     */
    public static Color add(Color dest, Color src) {
        float r, g, b, a;

        r = dest.getRed() + src.getRed() * src.getAlpha();
        g = dest.getGreen() + src.getGreen() * src.getAlpha();
        b = dest.getBlue() + src.getBlue() * src.getAlpha();
        a = dest.getAlpha() + src.getAlpha() * src.getAlpha();

        return new Color(r, g, b, a);
    }

    /**
     * Multiplies two colors.
     * The resulting color is
     * <code>result = dest * src</code>
     * @param dest The destination color.
     * @param src The source color.
     * @return The source color multiplied by the destination color.
     */
    public static Color multiply(Color dest, Color src) {
        float r, g, b, a;

        r = dest.getRed() * src.getRed();
        g = dest.getGreen() * src.getGreen();
        b = dest.getBlue() * src.getBlue();
        a = dest.getAlpha() * src.getAlpha();

        return new Color(r, g, b, a);
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
    private void setRed(float r) {
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
    private void setGreen(float g) {
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
    private void setBlue(float b) {
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
    private void setAlpha(float a) {
        if (a < 0) {
            this.a = 0;
            return;
        }

        this.a = a;
    }

    @Override
    public String toString() {
        return "Color{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                ", a=" + a +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color = (Color) o;
        return Float.compare(color.r, r) == 0
                && Float.compare(color.g, g) == 0
                && Float.compare(color.b, b) == 0
                && Float.compare(color.a, a) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, g, b, a);
    }
}

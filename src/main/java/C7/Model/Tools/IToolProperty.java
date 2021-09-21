package C7.Model.Tools;

import C7.Model.Color;


/**
 * Represent a general property of a {@link ITool}. The property may be fetched or set.
 *
 * @author Hugo Ekstrand
 */
public interface IToolProperty {

    /**
     * Consists of all the types a {@link IToolProperty} can be.
     */
    enum ToolPropertyType {
        INTEGER, DOUBLE, FLOAT, BOOLEAN, COLOR
    }

    /**
     * Returns the name of this property.
     * @return the name of this property.
     */
    String name();

    /**
     * Returns a string with a description of what this property does.
     * @return the description as a string
     */
    String description();

    /**
     * Returns the type of this property.
     * @return the type of this property.
     */
    ToolPropertyType getType();


    // Getters and setters ----------------------------------------------------------
    // for property value  ----------------------------------------------------------

    /**
     * Sets the value of this property as an integer.
     * @throws UnsupportedOperationException if this is the wrong type of this property
     * @param i the value this property will be set to.
     */
    void setInteger(int i);

    /**
     * Returns the value of this property as an integer.
     * @throws UnsupportedOperationException if this is the wrong type of this property
     * @return the value of this property.
     */
    int getInteger();

    /**
     * Sets the value of this property as a double.
     * @throws UnsupportedOperationException if this is the wrong type of this property
     * @param d the value this property will be set to.
     */
    void setDouble(double d);

    /**
     * Returns the value of this property as a double.
     * @throws UnsupportedOperationException if this is the wrong type of this property
     * @return the value of this property.
     */
    double getDouble();

    /**
     * Sets the value of this property as a float.
     * @throws UnsupportedOperationException if this is the wrong type of this property
     * @param f the value this property will be set to.
     */
    void setFloat(float f);

    /**
     * Returns the value of this property as a float.
     * @throws UnsupportedOperationException if this is the wrong type of this property
     * @return the value of this property.
     */
    float getFloat();

    /**
     * Sets the value of this property as a boolean.
     * @throws UnsupportedOperationException if this is the wrong type of this property
     * @param bool the value this property will be set to.
     */
    void setBoolean(boolean bool);

    /**
     * Returns the value of this property as a boolean.
     * @throws UnsupportedOperationException if this is the wrong type of this property
     * @return the value of this property.
     */
    boolean getBoolean();

    /**
     * Sets the value of this property as an {@link Color}.
     * @throws UnsupportedOperationException if this is the wrong type of this property
     * @param color the value this property will be set to.
     */
    void setColor(Color color);

    /**
     * Returns the value of this property as an {@link Color}.
     * @throws UnsupportedOperationException if this is the wrong type of this property
     * @return the value of this property.
     */
    Color getColor();

}

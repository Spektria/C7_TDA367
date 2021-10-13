package C7.Model.Tools.ToolProperties;

import C7.Util.Color;
import C7.Model.Tools.ITool;


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
        INTEGER, DOUBLE, BOOLEAN, COLOR
    }

    /**
     * Returns the name of this property.
     * @return the name of this property.
     */
    String getName();

    /**
     * Returns the type of this property.
     * @return the type of this property.
     */
    ToolPropertyType getType();


    /**
     * Returns the max value this property can have if it is a number.
     * @throws UnsupportedOperationException if the property is not a number type.
     * @return returns the max allowed numerical value of this property.
     */
    Number upperBound();

    /**
     * Returns the minimum value this property can have if it is a number.
     * @throws UnsupportedOperationException if the property is not a number type.
     * @return returns the minimum allowed numerical value of this property.
     */
    Number lowerBound();

    // Getters and setters ----------------------------------------------------------
    // for property value  ----------------------------------------------------------

    /**
     * Sets the value of this property as an integer.
     * The value must be in the range given by the methods {@link #upperBound()} and {@link #lowerBound()}.
     * @throws UnsupportedOperationException if this is the wrong type of this property
     * @throws IllegalArgumentException if the value is outside this property's range.
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
     * The value must be in the range given by the methods {@link #upperBound()} and {@link #lowerBound()}.
     * @throws UnsupportedOperationException if this is the wrong type of this property
     * @throws IllegalArgumentException if the value is outside this property's range.
     * @param d the value this property will be set to.
     */
    void setDouble(double d);

    /**
     * Returns the value of this property as a double.
     * @throws UnsupportedOperationException if this is the wrong type of this property
     * @throws IllegalArgumentException if the value is outside this property's range.
     * @return the value of this property.
     */
    double getDouble();

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

    /**
     * Resets this property to its default, start, value.
     * This value is the first value this property registered.
     */
    void setToDefault();
}

package C7.Model.Tools.ToolProperties;

import C7.Model.Color;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Factory for {@link IToolProperty IToolProperties}.
 * @author Hugo Ekstrand
 */
public class ToolPropertyFactory {

    /**
     * Creates a property for a Double.
     * Note that the getter must lead to a not null object.
     * @param name the name of the property
     * @param description the description of what the property does
     * @param setter the setter method for the property
     * @param getter the getter method for the property
     * @param min the minimum allowed value of this property
     * @param max the minimum allowed value of this property
     * @return the property
     */
    public static IToolProperty createDoubleProperty(String name, String description, Consumer<Double> setter, Supplier<Double> getter, double min, double max){
        return new DoubleToolProperty(name, description, setter, getter, min, max);
    }

    /**
     * Creates a property for an Integer.
     * Note that the getter must lead to a not null object.
     * @param name the name of the property
     * @param description the description of what the property does
     * @param setter the setter method for the property
     * @param getter the getter method for the property
     * @param min the minimum allowed value of this property
     * @param max the minimum allowed value of this property
     * @return the property
     */
    public static IToolProperty createIntegerProperty(String name, String description, Consumer<Integer> setter, Supplier<Integer> getter, int min, int max){
        return new IntegerToolProperty(name, description, setter, getter, min, max);
    }

    /**
     * Creates a property for a Boolean.
     * Note that the getter must lead to a not null object.
     * @param name the name of the property
     * @param description the description of what the property does
     * @param setter the setter method for the property
     * @param getter the getter method for the property
     * @return the property
     */
    public static IToolProperty createBooleanProperty(String name, String description, Consumer<Boolean> setter, Supplier<Boolean> getter){
        return new BooleanToolProperty(name, description, setter, getter);
    }

    /**
     * Creates a property for a Color.
     * Note that the getter must lead to a not null object.
     * @param name the name of the property
     * @param description the description of what the property does
     * @param setter the setter method for the property
     * @param getter the getter method for the property
     * @return the property
     */
    public static IToolProperty createColorProperty(String name, String description, Consumer<Color> setter, Supplier<Color> getter){
        return new ColorToolProperty(name, description, setter, getter);
    }
}

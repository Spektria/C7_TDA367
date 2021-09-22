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
     * Creates a property for a Float.
     * @param name the name of the property
     * @param description the description of what the property does
     * @param setter the setter method for the property
     * @param getter the getter method for the property
     * @param min the minimum allowed value of this property
     * @param max the minimum allowed value of this property
     * @return the property
     */
    public static IToolProperty createFloatProperty(String name, String description, Consumer<Float> setter, Supplier<Float> getter, Number min, Number max){
        return new FloatToolProperty(name, description, setter, getter, min, max);
    }

    /**
     * Creates a property for a Double.
     * @param name the name of the property
     * @param description the description of what the property does
     * @param setter the setter method for the property
     * @param getter the getter method for the property
     * @param min the minimum allowed value of this property
     * @param max the minimum allowed value of this property
     * @return the property
     */
    public static IToolProperty createDoubleProperty(String name, String description, Consumer<Double> setter, Supplier<Double> getter, Number min, Number max){
        return new DoubleToolProperty(name, description, setter, getter, min, max);
    }

    /**
     * Creates a property for a Integer.
     * @param name the name of the property
     * @param description the description of what the property does
     * @param setter the setter method for the property
     * @param getter the getter method for the property
     * @param min the minimum allowed value of this property
     * @param max the minimum allowed value of this property
     * @return the property
     */
    public static IToolProperty createIntegerProperty(String name, String description, Consumer<Integer> setter, Supplier<Integer> getter, Number min, Number max){
        return new IntegerToolProperty(name, description, setter, getter, min, max);
    }

    /**
     * Creates a property for a Boolean.
     * @param name the name of the property
     * @param description the description of what the property does
     * @param setter the setter method for the property
     * @param getter the getter method for the property
     * @return the property
     */
    public static IToolProperty createBooleanProperty(String name, String description, Consumer<Boolean> setter, Supplier<Boolean> getter, Number min, Number max){
        return new BooleanToolProperty(name, description, setter, getter);
    }

    /**
     * Creates a property for a Color.
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

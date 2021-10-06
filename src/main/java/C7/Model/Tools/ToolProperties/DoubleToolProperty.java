package C7.Model.Tools.ToolProperties;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A property representing a double.
 * @author Hugo Ekstrand
 */
class DoubleToolProperty extends NumericalToolProperty<Double> {


    DoubleToolProperty(String name, String description, Consumer<Double> setter, Supplier<Double> getter, double min, double max) {
        super(name, description, setter, getter, min, max);
    }

    @Override
    public ToolPropertyType getType() {
        return ToolPropertyType.DOUBLE;
    }

    @Override
    public double getDouble() {
        return this.getter.get();
    }

    @Override
    public void setDouble(double d) {
        requireBounds(d);
        this.setter.accept(d);
    }
}

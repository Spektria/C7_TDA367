package C7.Model.Tools.ToolProperties;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A property representing an integer.
 * @author Hugo Ekstrand
 */
class IntegerToolProperty extends NumericalToolProperty<Integer> {


    IntegerToolProperty(String name, String description, Consumer<Integer> setter, Supplier<Integer> getter, int min, int max) {
        super(name, description, setter, getter, min, max);
    }

    @Override
    public ToolPropertyType getType() {
        return ToolPropertyType.INTEGER;
    }

    @Override
    public int getInteger() {
        return this.getter.get();
    }

    @Override
    public void setInteger(int i) {
        requireBounds(i);
        this.setter.accept(i);
    }
}

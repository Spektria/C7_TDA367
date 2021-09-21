package C7.Model.Tools.ToolProperties;

import java.util.function.Consumer;
import java.util.function.Supplier;

abstract class NumericalToolProperty<T extends Number> extends BaseToolProperty<T> {

    private final Number min;
    private final Number max;

    NumericalToolProperty(String name, String description, Consumer<T> setter, Supplier<T> getter, Number min, Number max) {
        super(name, description, setter, getter);
        this.max = max;
        this.min = min;
    }

    @Override
    public Number upperBound() {
        return this.max;
    }

    @Override
    public Number lowerBound() {
        return this.min;
    }
}

package C7.Model.Tools.ToolProperties;

import java.util.function.Consumer;
import java.util.function.Supplier;

class IntegerToolProperty extends NumericalToolProperty<Integer> {


    IntegerToolProperty(String name, String description, Consumer<Integer> setter, Supplier<Integer> getter, Number min, Number max) {
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
        this.setter.accept(i);
    }
}

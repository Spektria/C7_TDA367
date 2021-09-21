package C7.Model.Tools.ToolProperties;

import java.util.function.Consumer;
import java.util.function.Supplier;

class FloatToolProperty extends NumericalToolProperty<Float> {


    FloatToolProperty(String name, String description, Consumer<Float> setter, Supplier<Float> getter, Number min, Number max) {
        super(name, description, setter, getter, min, max);
    }

    @Override
    public ToolPropertyType getType() {
        return ToolPropertyType.FLOAT;
    }

    @Override
    public float getFloat() {
        return this.getter.get();
    }

    @Override
    public void setFloat(float f) {
        this.setter.accept(f);
    }
}

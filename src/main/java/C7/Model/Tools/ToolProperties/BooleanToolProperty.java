package C7.Model.Tools.ToolProperties;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A property representing a boolean.
 * @author Hugo Ekstrand
 */
class BooleanToolProperty extends BaseToolProperty<Boolean> {


    BooleanToolProperty(String name, Consumer<Boolean> setter, Supplier<Boolean> getter) {
        super(name, setter, getter);
    }

    @Override
    public ToolPropertyType getType() {
        return ToolPropertyType.BOOLEAN;
    }

    @Override
    public void setBoolean(boolean bool) {
        this.setter.accept(bool);
    }

    @Override
    public boolean getBoolean() {
        return this.getter.get();
    }
}

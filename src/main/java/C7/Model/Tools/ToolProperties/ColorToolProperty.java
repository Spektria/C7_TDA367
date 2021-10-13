package C7.Model.Tools.ToolProperties;

import C7.Util.Color;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A property representing a {@link Color}.
 * @author Hugo Ekstrand
 */
class ColorToolProperty extends BaseToolProperty<Color> {
    ColorToolProperty(String name, Consumer<Color> setter, Supplier<Color> getter) {
        super(name, setter, getter);
    }

    @Override
    public ToolPropertyType getType() {
        return ToolPropertyType.COLOR;
    }

    @Override
    public void setColor(Color color) {
        this.setter.accept(color);
    }

    @Override
    public Color getColor() {
        return this.getter.get();
    }
}

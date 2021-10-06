package C7.Model.Tools.ToolProperties;

import C7.Model.Color;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A property representing a {@link Color}.
 * @author Hugo Ekstrand
 */
class ColorToolProperty extends BaseToolProperty<Color> {
    ColorToolProperty(String name, String description, Consumer<Color> setter, Supplier<Color> getter) {
        super(name, description, setter, getter);
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

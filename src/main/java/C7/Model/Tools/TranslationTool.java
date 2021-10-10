package C7.Model.Tools;

import C7.Model.Layer.ILayer;
import C7.Model.Tools.ToolProperties.IToolProperty;
import C7.Model.Tools.ToolProperties.ToolPropertyFactory;
import C7.Util.Vector2D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Transformation Tool for translating layers.
 * @author Hugo Ekstrand
 */
class TranslationTool implements ITool {
    private final Collection<IToolProperty> properties = new ArrayList<>();
    private boolean isContinuous = true;

    TranslationTool(){
        properties.addAll(Arrays.asList(
                ToolPropertyFactory.createBooleanProperty("Continuous scaling", "The scaling is continuously updated on the screen during the rotation action.",
                        (b) -> isContinuous = b, () -> isContinuous)
        ));
    }

    @Override
    public Collection<IToolProperty> getProperties() {
        return properties;
    }

    @Override
    public void apply(ILayer layer, Vector2D v0, Vector2D v1) {
        Vector2D movement = v1.sub(v0).scale(new Vector2D(1d/2, 1d/2));
        layer.setPosition(layer.getPosition().add(movement));
        layer.update();
    }

    @Override
    public boolean isContinuous() {
        return isContinuous;
    }

    @Override
    public void setToDefault() {
        properties.forEach(IToolProperty::setToDefault);
    }
}

package C7.Model.Tools;

import C7.Model.Layer.ILayer;
import C7.Model.Tools.ToolProperties.IToolProperty;
import C7.Util.Vector2D;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Tool for translating layers.
 * @author Hugo Ekstrand
 */
class TranslationTool implements ITool {
    private final Collection<IToolProperty> properties = new ArrayList<>();

    TranslationTool(){

    }

    @Override
    public Collection<IToolProperty> getProperties() {
        return properties;
    }

    @Override
    public void apply(ILayer layer, Vector2D v0, Vector2D v1) {
        Vector2D movement = v1.sub(v0);
        layer.setPosition(layer.getPosition().add(movement));
        layer.update();
    }

    @Override
    public boolean isContinuous() {
        return false;
    }

    @Override
    public void setToDefault() {
        properties.forEach(IToolProperty::setToDefault);
    }
}

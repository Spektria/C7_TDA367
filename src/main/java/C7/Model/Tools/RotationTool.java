package C7.Model.Tools;

import C7.Model.Layer.ILayer;
import C7.Model.Tools.ToolProperties.IToolProperty;
import C7.Util.Vector2D;

import java.util.ArrayList;
import java.util.Collection;

class RotationTool implements ITool {
    private final Collection<IToolProperty> properties = new ArrayList<>();

    @Override
    public Collection<IToolProperty> getProperties() {
        return properties;
    }

    @Override
    public void apply(ILayer layer, Vector2D v0, Vector2D v1) {
        Vector2D center = layer.getPosition().add(new Vector2D(layer.getWidth() / 2d, layer.getHeight() / 2d));
        if(v0.equals(center) || v1.equals(center) || v0.equals(v1))
            return;
        double rot = center.sub(v0).angleBetween(center.sub(v1)) / 2d;
        layer.setRotation(layer.getRotation() + rot);
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

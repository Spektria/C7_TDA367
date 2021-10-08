package C7.Model.Tools;

import C7.Model.Layer.ILayer;
import C7.Model.Tools.ToolProperties.IToolProperty;
import C7.Util.Vector2D;

import java.util.ArrayList;
import java.util.Collection;

public class ScalingTool implements ITool {
    private final Collection<IToolProperty> properties = new ArrayList<>();

    @Override
    public Collection<IToolProperty> getProperties() {
        return properties;
    }

    @Override
    public void apply(ILayer layer, Vector2D v0, Vector2D v1) {
        Vector2D delta = v1.sub(v0);

        // We want to add/subtract the delta length from both the width and height of the layer so
        // that when we drag a layer it "moves with the mouse".
        // To do this we calculate how much we need to add / subtract to the scale to get that distance.
        // For the x-axis the change is scaleToAdd = dx / width.
        Vector2D scaleToAdd = delta.scale(new Vector2D(1d/layer.getWidth(), 1d/layer.getHeight()));

        scaleToAdd = scaleToAdd.scale(LayerQuadrantUtil.getQuadrantAsVector(layer, v0));

        layer.setScale(layer.getScale().add(scaleToAdd));
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

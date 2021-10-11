package C7.Model.Tools;

import C7.Model.Tools.ToolProperties.IToolProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Abstract base implementation of {@link ITool}. It abstracts
 * away the properties methods since they must be the same for all ITools.
 * @author Hugo Ekstrand
 */
abstract class BaseTool implements ITool {
    private final Collection<IToolProperty> properties = new ArrayList<>();

    protected void addProperties(IToolProperty... props){
        properties.addAll(Arrays.asList(props));
    }

    @Override
    public Collection<IToolProperty> getProperties() {
        return this.properties;
    }

    @Override
    public void setToDefault() {
        this.properties.forEach(IToolProperty::setToDefault);
    }
}

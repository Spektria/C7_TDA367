package C7.Controller.Properties;

import C7.Model.Tools.ToolProperties.IToolProperty;
import C7.Model.Tools.ToolProperties.ToolPropertyFactory;
import javafx.scene.layout.AnchorPane;

public class ToolPropertyViewFactory{

    public static AnchorPane createFrom(IToolProperty property) {
        switch (property.getType()) {
            case COLOR -> {
                return new ColorProperty(property);
            }

            case DOUBLE -> {
                return new SliderProperty(property);
            }

            case BOOLEAN -> {
                return new CheckboxProperty(property);
            }

            case INTEGER -> {
                return new IntProperty(property);
            }

            default -> {
                System.out.println("Unrecognized property type: " + property.getType());
                return null;
            }
        }
    }
}

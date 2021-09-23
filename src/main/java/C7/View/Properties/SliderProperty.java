package C7.View.Properties;

import C7.Model.Tools.ToolProperties.IToolProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SliderProperty extends AnchorPane {
    @FXML
    Slider slider;
    @FXML
    Label label;
    @FXML Label valueLabel;

    public SliderProperty(IToolProperty prop) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SliderProperty.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        label.setText(prop.name());

        double value = prop.getDouble();
        slider.setValue(value);
        valueLabel.setText("" + value);

        slider.setMin(prop.lowerBound().doubleValue());
        slider.setMax(prop.upperBound().doubleValue());

        slider.valueProperty().addListener((observableValue, oldVal, newVal) -> {
            prop.setDouble(newVal.doubleValue());
            var valueDisplayStr = "" + newVal;
            // TODO: Change to some form of Math.round(value, decimals) method instead of substring since if
            // TODO: the value is > 999 it will not be displayed properly.
            valueLabel.setText(valueDisplayStr.length() > 3 ? valueDisplayStr.substring(0, 4) : valueDisplayStr);
        });
    }
}

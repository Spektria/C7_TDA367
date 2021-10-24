package C7.Controller.Properties;

import C7.Model.Tools.ToolProperties.IToolProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Controls a tool property widget of the corresponding type.
 * @author Elias Ersson
 */
class IntProperty extends AnchorPane {
    @FXML
    Spinner spinner;
    @FXML
    Label label;

    public IntProperty(IToolProperty prop) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/IntProperty.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        label.setText(prop.getName());

        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(prop.lowerBound().intValue(), prop.upperBound().intValue(), prop.getInteger()));

        spinner.valueProperty().addListener((observableValue, oldVal, newVal) -> {
                prop.setInteger((Integer) newVal);
        });
    }
}

package C7.Controller.Properties;

import C7.Model.Tools.ToolProperties.IToolProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Controls a tool property widget of the corresponding type.
 * @author Elias Ersson
 */
class CheckboxProperty extends AnchorPane {
    @FXML
    CheckBox checkBox;

    public CheckboxProperty(IToolProperty prop) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CheckboxProperty.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        checkBox.setText(prop.getName());
        checkBox.selectedProperty().setValue(prop.getBoolean());

        checkBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                prop.setBoolean(checkBox.isSelected());
            }
        });
    }
}

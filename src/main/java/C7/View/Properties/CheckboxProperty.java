package C7.View.Properties;

import C7.Model.Tools.ToolProperties.IToolProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;

public class CheckboxProperty extends AnchorPane {
    @FXML
    CheckBox checkBox;

    public CheckboxProperty(IToolProperty prop) {
        checkBox.setText(prop.name());

        checkBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                prop.setBoolean(checkBox.isSelected());
            }
        });
    }
}

package C7.View.Properties;

import C7.Model.Tools.ToolProperties.IToolProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.AnchorPane;

public class SliderProperty extends AnchorPane {
    @FXML
    Slider slider;
    @FXML
    Label label;

    public SliderProperty(IToolProperty prop) {
        label.setText(prop.name());

        slider.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                prop.setDouble(slider.getValue());
            }
        });
    }
}

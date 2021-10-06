package C7.Controller.Properties;

import C7.Model.Tools.ToolProperties.IToolProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.IOException;

class ColorProperty extends AnchorPane {
    @FXML
    ColorPicker colorPicker;
    @FXML
    Label label;

    public ColorProperty(IToolProperty prop) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ColorProperty.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        label.setText(prop.getName());

        C7.Model.Color color = prop.getColor();
        colorPicker.setValue(new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()));


        colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Color color = colorPicker.getValue();
                prop.setColor(new C7.Model.Color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), (float)color.getOpacity()));
            }
        });
    }
}

package C7.Controller.Properties;

import C7.Model.Tools.ITool;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * @author Elias Ersson
 */
class ResetProperties extends AnchorPane {
    @FXML Button button;

    public ResetProperties(ITool tool, EventHandler<ActionEvent> interfaceUpdateHandler) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ResetProperties.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }


        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                tool.setToDefault();
                interfaceUpdateHandler.handle(actionEvent);
            }
        });


    }
}

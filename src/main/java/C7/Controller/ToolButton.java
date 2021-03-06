package C7.Controller;

import C7.Model.Tools.ITool;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Controls a button that when pressed sets its assigned tool as the current one.
 * @author Elias Ersson
 */
public class ToolButton extends AnchorPane {

    @FXML Button button;

    ITool tool;

    public ToolButton(ITool tool, String text, ToolsController controller) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ToolButton.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        button.setText(text);

        this.tool = tool;

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.setCurrentTool(tool);
            }
        });

    }
}

package C7.Controller.Properties;

import C7.Model.Tools.ITool;
import C7.Model.Tools.ToolProperties.IToolProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.io.IOException;

/**
 * Controls a panel containing widgets for modifying the properties of the currently selected tool.
 * @author Elias Ersson
 */
public class PropertiesController extends ScrollPane {
    @FXML
    FlowPane flowPaneProperties;

    public PropertiesController(AnchorPane parent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/PropertiesView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }

        parent.getChildren().add(this);


        AnchorPane.setTopAnchor(this, 0d);
        AnchorPane.setBottomAnchor(this, 0d);
    }

    public void update(ITool tool) {
        flowPaneProperties.getChildren().clear();
        for (IToolProperty property:
                tool.getProperties()) {

            flowPaneProperties.getChildren().add(ToolPropertyViewFactory.createFrom(property));
        }

        //This might be a crime against nature, I'm in new territory here
        ResetProperties reset = new ResetProperties(tool, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //This looks like recursion but it isn't
                update(tool);
            }
        });

        flowPaneProperties.getChildren().add(reset);
    }
}

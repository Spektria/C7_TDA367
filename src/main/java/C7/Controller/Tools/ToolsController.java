package C7.Controller.Tools;

import C7.Controller.IMainController;
import C7.Model.Tools.ITool;
import C7.Model.Tools.ToolFactory;
import C7.Util.Color;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.io.IOException;

public class ToolsController extends ScrollPane {

    private @FXML FlowPane flowPaneTools;

    private IMainController controller;

    public ToolsController(AnchorPane parent, IMainController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ToolsView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }

        this.controller = controller;

        parent.getChildren().add(this);

        AnchorPane.setTopAnchor(this, 0d);
        AnchorPane.setBottomAnchor(this, 0d);

        //This really needs to be reworked
        ITool toolThatWeCreateSeparatelyToSetItAsTheDefault = ToolFactory.createCircularBrush(5, new Color(0, 0, 0, 1));
        setCurrentTool(toolThatWeCreateSeparatelyToSetItAsTheDefault);
        flowPaneTools.getChildren().add(new ToolButton(toolThatWeCreateSeparatelyToSetItAsTheDefault, "Circle", this));
        flowPaneTools.getChildren().add(new ToolButton(ToolFactory.createCalligraphyBrush(5, new Color(0, 0, 0, 1)), "Calligraphy", this));
        flowPaneTools.getChildren().add(new ToolButton(ToolFactory.createFillBucket( 0.2f, new Color(0, 0, 0, 1)), "Fill", this));

        flowPaneTools.getChildren().add(new ToolButton(ToolFactory.createTranslationTool(), "Move", this));
        flowPaneTools.getChildren().add(new ToolButton(ToolFactory.createScalingTool(), "Scale", this));
        flowPaneTools.getChildren().add(new ToolButton(ToolFactory.createRotationTool(), "Rotate", this));

        flowPaneTools.getChildren().add(new ToolButton(ToolFactory.createEraserTool(5), "Eraser", this));
    }

    public void setCurrentTool(ITool tool){
        controller.setCurrentTool(tool);
    }
}

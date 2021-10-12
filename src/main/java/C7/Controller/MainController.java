package C7.Controller;

import C7.IO.LayerIO;
import C7.Model.Layer.ILayer;
import C7.Model.Project;
import C7.Model.Tools.ITool;
import C7.Util.Vector2D;
import C7.Controller.Properties.*;
import C7.View.IView;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.*;
import javafx.stage.FileChooser;


import java.io.File;
import java.util.List;
import java.util.Objects;


/**
 *
 */
class MainController implements IMainController {
    private @FXML Canvas canvas;
    private @FXML ScrollPane scrollPaneCanvas;
    private @FXML SplitPane splitPaneToolsProps;
    private @FXML AnchorPane contentPaneToolsProps;
    private @FXML AnchorPane contentPaneTools;
    private @FXML ScrollPane scrollPaneTools;
    private @FXML AnchorPane contentPaneProperties;
    private @FXML ScrollPane scrollPaneProperties;
    private @FXML AnchorPane layersArea;
    private @FXML MenuBar menuBar;

    private ToolsController toolsController;

    private PropertiesController propertiesController;

    private Project project; //Only one for now
    //private ILayerManager manager; //Only one for now
    //ILayer layer; //Only one for now

    private IView view;

    private ITool currentTool;

    private Vector2D oldPos;
    private Vector2D pressedPos;

    public MainController(IView view, Project project, AnchorPane root) throws Exception {
        Objects.requireNonNull(view);
        Objects.requireNonNull(project);
        Objects.requireNonNull(root);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MainWindow.fxml"));
        fxmlLoader.setRoot(root);
        fxmlLoader.setController(this);
        fxmlLoader.load();

        this.project = project;
        this.view = view;

        scrollPaneCanvas.widthProperty().addListener((observable, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        scrollPaneCanvas.heightProperty().addListener((observable, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        view.setGraphicsContext(canvas.getGraphicsContext2D());
        view.setBounds(canvas.widthProperty(), canvas.heightProperty());

        scrollPaneCanvas.setContent(canvas);

        view.render();

        propertiesController = new PropertiesController(contentPaneProperties);

        toolsController = new ToolsController(contentPaneTools, this);

        splitPaneToolsProps.prefHeightProperty().bind(contentPaneToolsProps.heightProperty());

        layersArea.getChildren().add(new LayersController(project));

    }

    public void setCurrentTool(ITool tool) {
        this.currentTool = tool;

        propertiesController.update(tool);
    }


    void importFileAsLayer(File file) {
        ILayer importedLayer = LayerIO.layerFromFile(file.getPath());
        if (importedLayer != null) {
            project.setActiveLayer(project.addLayer(importedLayer));
            view.render();
        }
    }

    private void getScene() {

    }

    @FXML
    private void onNew (Event event) {

    }

    @FXML
    private void onOpen (Event event) {

    }

    @FXML
    private void onSave (Event event) {

    }

    @FXML
    private void onImport (Event event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose image to import");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Images", List.of("*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif")));

        File file = fileChooser.showOpenDialog(menuBar.getScene().getWindow());
        if (file != null) importFileAsLayer(file);
    }

    @FXML
    private void onExport (Event event) {

    }

    @FXML
    private void onCanvasDragOver (DragEvent event) {
        if (event.getGestureSource() != canvas
                && event.getDragboard().hasFiles()) {
            /* allow for both copying and moving, whatever user chooses */
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }


    @FXML
    private void onCanvasDragDropped (DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;

            List<File> files = db.getFiles();

            for (int i = 0; i < files.size(); i++) {
                importFileAsLayer(files.get(i));
            }
        }

        event.setDropCompleted(success);

        event.consume();
    }


    @FXML
    private void onCanvasMouseDragged (MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            Vector2D point = new Vector2D(event.getX(), event.getY());
            if(currentTool.isContinuous())
                project.applyTool(currentTool, oldPos, point);
            oldPos = point;
        }
    }


    @FXML
    private void onCanvasMousePressed (MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            var point = new Vector2D(event.getX(), event.getY());
            if(currentTool.isContinuous())
                project.applyTool(currentTool, point, point);
            oldPos = point;
            pressedPos = point;
        }
    }


    @FXML
    private void onCanvasMouseReleased (MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            var point = new Vector2D(event.getX(), event.getY());
            if(currentTool.isContinuous())
                project.applyTool(currentTool, point, point);
            else
                project.applyTool(currentTool, pressedPos, point);
        }
    }

}

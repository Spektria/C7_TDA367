package C7.Controller;

import C7.Services.ImageFormatName;
import C7.Model.IProject;
import C7.Model.Layer.ILayer;
import C7.Model.Tools.ITool;
import C7.Services.ServiceFactory;
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


import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.security.Provider;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


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
    private LayersController layersController;

    private IProject project; //Only one for now
    //private ILayerManager manager; //Only one for now
    //ILayer layer; //Only one for now

    private IView view;

    private ITool currentTool;

    private Vector2D oldPos;
    private Vector2D pressedPos;

    public MainController(IView view, IProject project, AnchorPane root) throws Exception {
        Objects.requireNonNull(view);
        Objects.requireNonNull(project);
        Objects.requireNonNull(root);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MainWindow.fxml"));
        fxmlLoader.setRoot(root);
        fxmlLoader.setController(this);
        fxmlLoader.load();

        this.project = project;
        this.view = view;

        //scrollPaneCanvas.widthProperty().addListener((observable, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        //scrollPaneCanvas.heightProperty().addListener((observable, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));
        canvas.setWidth(project.getWidth());
        canvas.setHeight(project.getHeight());


        view.setGraphicsContext(canvas.getGraphicsContext2D());
        view.setBounds(scrollPaneCanvas.widthProperty(), scrollPaneCanvas.heightProperty());

        scrollPaneCanvas.setContent(canvas);

        view.render();

        propertiesController = new PropertiesController(contentPaneProperties);

        toolsController = new ToolsController(contentPaneTools, this);

        splitPaneToolsProps.prefHeightProperty().bind(contentPaneToolsProps.heightProperty());

        layersController = new LayersController(layersArea, project);

    }

    public void setCurrentTool(ITool tool) {
        this.currentTool = tool;

        propertiesController.update(tool);
    }


    void importFileAsLayer(File file) {
        ServiceFactory.createLayerImportService(file.getPath(), (layer) -> {
            project.setActiveLayer(project.addLayer(layer));
            view.render();
        }).execute();

        layersController.updateLayers();
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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose where to save exported image");
        List<String> formats = Arrays.stream(ImageFormatName.values()).map(ImageFormatName::toString).map(str -> "*." + str).collect(Collectors.toList());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Images", formats));
        fileChooser.setInitialFileName(project.getName());
        fileChooser.setInitialDirectory(FileSystemView.getFileSystemView().getDefaultDirectory());

        File file = fileChooser.showSaveDialog(menuBar.getScene().getWindow());
        if(file != null) {
            String chosenExtStr = fileChooser.getSelectedExtensionFilter().getExtensions().get(0).substring(2);
            ImageFormatName chosenExt = ImageFormatName.valueOf(ImageFormatName.class, chosenExtStr.toUpperCase());
            ServiceFactory.createImageExportService(project, chosenExt, file.getPath()).execute();
        }
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

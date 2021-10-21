package C7.Controller;

import C7.Model.ProjectFactory;
import C7.Services.ImageFormatName;
import C7.Model.IProject;
import C7.Model.Tools.ITool;
import C7.Services.ProjectFormatName;
import C7.Services.ServiceFactory;
import C7.Util.Vector2D;
import C7.Controller.Properties.*;
import C7.View.IView;
import C7.View.Render.RenderAdapterFactory;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.*;
import javafx.stage.FileChooser;


import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @author Elias Ersson
 */
class MainController implements IMainController {
    private @FXML Canvas canvas;
    private @FXML ScrollPane scrollPaneCanvas;
    private @FXML SplitPane splitPaneToolsProps;
    private @FXML AnchorPane contentPaneToolsProps;
    private @FXML AnchorPane contentPaneTools;
    private @FXML AnchorPane contentPaneProperties;
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
        bindView();

        canvas.setWidth(project.getWidth());
        canvas.setHeight(project.getHeight());

        scrollPaneCanvas.setContent(canvas);

        view.render();

        propertiesController = new PropertiesController(contentPaneProperties);

        toolsController = new ToolsController(contentPaneTools, this);

        splitPaneToolsProps.prefHeightProperty().bind(contentPaneToolsProps.heightProperty());

        layersController = new LayersController(layersArea, project);

    }

    /**
     * Connects the view with this controller.
     * Does any necessary operation to make the view work with this controller.
     */
    private void bindView(){
        ChangeListener<Number> reRenderIfBigger = (observable, oldValue, newValue) -> {
            if(oldValue.intValue() < newValue.intValue())
                view.render();
        };

        // We want to rerender the view if we change the size of the window.
        // We also only want to rerender if it gets bigger since it is otherwise already rendered.
        scrollPaneCanvas.widthProperty().addListener(reRenderIfBigger);
        scrollPaneCanvas.heightProperty().addListener(reRenderIfBigger);

        view.setGraphicsContext(canvas.getGraphicsContext2D());
        view.setBounds(scrollPaneCanvas.widthProperty(), scrollPaneCanvas.heightProperty());
    }

    public void setCurrentTool(ITool tool) {
        this.currentTool = tool;

        propertiesController.update(tool);
    }

    void setProject(IProject newProject) {
        this.project = newProject;
        canvas.setWidth(project.getWidth());
        canvas.setHeight(project.getHeight());
        view.setIRenderSource(RenderAdapterFactory.createAdapter(newProject));
        //TODO RECONNECT THUMBNAIL OBSERVERS
        view.render();
        layersController = new LayersController(layersArea, project);
        //This does not work, who knows, maybe it will one day :(
        //layersController.setIProject(importedProject);
        layersController.updateLayers();
    }

    void importFileAsProject(File file) {
        ServiceFactory.createProjectLoaderService(file.getPath(), (importedProject) -> {
            setProject(importedProject);
        }).execute();

    }

    void importFileAsLayer(File file) {
        ServiceFactory.createLayerImportService(file.getPath(), (layer) -> {
            project.setActiveLayer(project.addLayer(layer));
            view.render();
        }).execute();

        layersController.updateLayers();
    }

    @FXML
    private void onNew (Event event) {
        NewSurfaceDialog dialog = new NewSurfaceDialog(project.getWidth(), project.getHeight());
        dialog.setTitle("New project");
        dialog.setHeaderText("Unsaved changes to the current project will be lost");

        dialog.showAndWait()
                .ifPresent(result -> setProject(ProjectFactory.createProjectWithBaseLayer("New project", result.getVal1(), result.getVal2())));
    }

    @FXML
    private void onOpen (Event event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose project to import");
        List<String> formats = Arrays.stream(ProjectFormatName.values()).map(ProjectFormatName::toString).map(str -> "*." + str).collect(Collectors.toList());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PaintQlone Project", formats));

        File file = fileChooser.showOpenDialog(menuBar.getScene().getWindow());
        if (file != null) importFileAsProject(file);
        view.render();
    }

    @FXML
    private void onSave (Event event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose where to save the project");
        List<String> formats = Arrays.stream(ProjectFormatName.values()).map(ProjectFormatName::toString).map(str -> "*." + str).collect(Collectors.toList());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PaintQlone Project", formats));
        fileChooser.setInitialFileName(project.getName());
        fileChooser.setInitialDirectory(FileSystemView.getFileSystemView().getDefaultDirectory());

        File file = fileChooser.showSaveDialog(menuBar.getScene().getWindow());
        if(file != null) {
            ServiceFactory.createProjectSaverService(file.getPath(), project).execute();
        }
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
        if (project.getLayer(project.getActiveLayerID()) == null) return;
        if (event.getButton() == MouseButton.PRIMARY) {
            Vector2D point = new Vector2D(event.getX(), event.getY());
            if(currentTool.isContinuous())
                project.applyTool(currentTool, oldPos, point);
            oldPos = point;
        }
    }


    @FXML
    private void onCanvasMousePressed (MouseEvent event) {
        if (project.getLayer(project.getActiveLayerID()) == null) return;
        if (event.getButton() == MouseButton.PRIMARY) {
            Vector2D point = new Vector2D(event.getX(), event.getY());
            oldPos = point;
            pressedPos = point;
        }
    }


    @FXML
    private void onCanvasMouseReleased (MouseEvent event) {
        if (project.getLayer(project.getActiveLayerID()) == null) return;
        if (event.getButton() == MouseButton.PRIMARY) {
            Vector2D point = new Vector2D(event.getX(), event.getY());
            if(currentTool.isContinuous())
                project.applyTool(currentTool, point, point);
            else
                project.applyTool(currentTool, pressedPos, point);
        }
    }

}

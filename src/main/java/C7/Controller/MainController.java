package C7.Controller;

import C7.IO.LayerIO;
import C7.Model.Layer.ILayer;
import C7.Model.Layer.Layer;
import C7.Model.Tools.ITool;
import C7.Model.Tools.ToolFactory;
import C7.Model.Tools.ToolProperties.IToolProperty;
import C7.Model.Vector.Vector2D;
import C7.Controller.Properties.*;
import C7.View.IView;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.canvas.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;


import java.io.File;
import java.util.List;
import java.util.Objects;


public class MainController {
    @FXML Canvas canvas;
    @FXML ScrollPane scrollPaneCanvas;
    @FXML SplitPane splitPaneToolsProps;
    @FXML AnchorPane contentPaneToolsProps;
    @FXML AnchorPane contentPaneTools;
    @FXML ScrollPane scrollPaneTools;
    @FXML FlowPane flowPaneTools;
    @FXML AnchorPane contentPaneProperties;
    @FXML ScrollPane scrollPaneProperties;
    @FXML AnchorPane layersArea;

    PropertiesController propertiesController;

    private IView view;

    private ILayer layer; //Only one for now

    private ITool currentTool;

    private Vector2D oldPos;

    public MainController(IView view, ILayer layer, GridPane root) throws Exception {
        Objects.requireNonNull(view);
        Objects.requireNonNull(layer);
        Objects.requireNonNull(root);

        this.view = view;
        this.layer = layer;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MainWindow.fxml"));
        fxmlLoader.setRoot(root);
        fxmlLoader.setController(this);
        fxmlLoader.load();

        canvas.setWidth(layer.getWidth());
        canvas.setHeight(layer.getHeight());

        view.setGraphicsContext(canvas.getGraphicsContext2D());
        view.setCanvas(canvas);

        scrollPaneCanvas.setContent(canvas);

        view.render();

        splitPaneToolsProps.prefHeightProperty().bind(contentPaneToolsProps.heightProperty());
        scrollPaneTools.prefHeightProperty().bind(contentPaneTools.heightProperty());
        scrollPaneProperties.prefHeightProperty().bind(contentPaneProperties.heightProperty());

        layersArea.getChildren().add(new LayersController());

        propertiesController = new PropertiesController(contentPaneProperties);

        setCurrentTool(ToolFactory.CreateCircularBrush(5, new C7.Model.Color(1, 0, 0, 1)));

        //Maybe shouldn't send controller? Couldn't come up with a better solution off the top of my head
        flowPaneTools.getChildren().add(new ToolButton(currentTool, "Circle", this));
        flowPaneTools.getChildren().add(new ToolButton(ToolFactory.CreateCalligraphyBrush(5, new C7.Model.Color(0, 1, 0, 1)), "Calligraphy", this));
        flowPaneTools.getChildren().add(new ToolButton(ToolFactory.CreateFillBucket( 0.2f, new C7.Model.Color(0, 0, 1, 1)), "Fill", this));

    }

    public void setCurrentTool(ITool tool) {
        this.currentTool = tool;

        propertiesController.update(tool);
    }


    void importFileAsLayer(File file) {
        Layer importedLayer = LayerIO.layerFromFile(file.getPath());
        if (importedLayer != null) {
            view.setLayer(importedLayer);
            view.render();
        }
    }

    @FXML
    void onImport (Event event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose image to import");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Images", List.of("*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif")));

        //Feels very hacky to get the scene from an arbitrary node
        File file = fileChooser.showOpenDialog(scrollPaneCanvas.getScene().getWindow());
        if (file != null) importFileAsLayer(file);
    }

    @FXML void onCanvasDragOver (DragEvent event) {
            if (event.getGestureSource() != canvas
                    && event.getDragboard().hasFiles()) {
                /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        }


    @FXML void onCanvasDragDropped (DragEvent event) {
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


    @FXML void onCanvasMouseDragged (MouseEvent event) {
            if (event.getButton() == MouseButton.PRIMARY) {
                Vector2D point = new Vector2D(event.getX(), event.getY());
                currentTool.apply(layer, oldPos, point);
                oldPos = point;
            }
        }


    @FXML void onCanvasMousePressed (MouseEvent event) {
            if (event.getButton() == MouseButton.PRIMARY) {
                var point = new Vector2D(event.getX(), event.getY());
                currentTool.apply(layer, point, point);
                oldPos = point;
            }
        }


@FXML void onCanvasMouseReleased (MouseEvent event) {
            if (event.getButton() == MouseButton.PRIMARY) {
                var point = new Vector2D(event.getX(), event.getY());
                currentTool.apply(layer, point, point);
            }
        }
}

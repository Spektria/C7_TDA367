package C7.Controller;

import C7.IO.LayerIO;
import C7.Model.Layer.ILayer;
import C7.Model.Layer.Layer;
import C7.Model.Tools.ITool;
//import C7.Model.Tools.PixelPen;
import C7.Model.Tools.ToolFactory;
import C7.Model.Tools.ToolProperties.IToolProperty;
import C7.Model.Vector.Vector2D;
import C7.Controller.Properties.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;


import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class C7PaintView implements Initializable {
    @FXML AnchorPane canvasPane;
    @FXML Canvas canvas;
    GraphicsContext gc;
    @FXML FlowPane flowPaneTools;
    @FXML FlowPane flowPaneProperties;
    @FXML AnchorPane layersArea;

    ILayer layer; //Only one for now

    public void setCurrentTool(ITool tool) {
        this.currentTool = tool;

        updatePropertiesView();
    }

    void updatePropertiesView() {
        flowPaneProperties.getChildren().clear();
        for (IToolProperty property:
                currentTool.getProperties()) {
            AnchorPane widget = null;
            switch (property.getType())
            {
                case COLOR -> widget = new ColorProperty(property);

                case DOUBLE -> widget = new SliderProperty(property);

                case BOOLEAN -> widget = new CheckboxProperty(property);

                case INTEGER -> widget = new IntProperty(property);

                default -> { System.out.println("Unrecognized property type: " + property.getType());
                    continue; }
            }

            flowPaneProperties.getChildren().add(widget);
        }

        //This might be a crime against nature, I'm in new territory here
        ResetProperties reset = new ResetProperties(currentTool, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //This looks like recursion but it isn't
                updatePropertiesView();
            }
        });

        flowPaneProperties.getChildren().add(reset);
    }

    ITool currentTool;

    private Vector2D oldPos;

    public void initialize(URL location, ResourceBundle resources) {
        gc = canvas.getGraphicsContext2D();

        layer = new Layer(600, 400, new C7.Model.Color(0, 0, 0, 0));

        updateView();

        setCurrentTool(ToolFactory.CreateCircularBrush());

        //Maybe shouldn't send controller? Couldn't come up with a better solution off the top of my head
        flowPaneTools.getChildren().add(new ToolButton(currentTool, "Circle", this));
        flowPaneTools.getChildren().add(new ToolButton(ToolFactory.CreateCalligraphyBrush(), "Calligraphy", this));
        flowPaneTools.getChildren().add(new ToolButton(ToolFactory.CreateFillBucket(), "Fill", this));


        canvasPane.setOnDragOver(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != canvasPane
                        && event.getDragboard().hasFiles()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        canvasPane.setOnDragDropped(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;

                    List<File> files = db.getFiles();

                    for (int i = 0; i < files.size(); i++) {
                        Layer importedLayer = LayerIO.layerFromFile(files.get(i).getPath());
                        if (importedLayer != null) {
                            layer = importedLayer;
                            gc.clearRect(0,0,canvas.getWidth(), canvas.getHeight());
                            updateView();
                        }
                    }
                }

                event.setDropCompleted(success);

                event.consume();
            }
        });

        canvasPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //Move stuff like this to the controller later
                Vector2D point = new Vector2D(event.getX(), event.getY());
                currentTool.apply(layer, oldPos, point);
                oldPos = point;

                // Rotation for debugging
                //layer.setRotation((layer.getRotation() + Math.PI / 100) % (Math.PI * 2));
                updateView();
            }
        });

        canvasPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    var point = new Vector2D(event.getX(), event.getY());
                    currentTool.apply(layer, point, point);
                    oldPos = point;

                    updateView();
                }
            }
        });

        canvasPane.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    var point = new Vector2D(event.getX(), event.getY());
                    currentTool.apply(layer, point, point);
                }
            }
        });

        layersArea.getChildren().add(new LayersView());

    }


    void updateView() {
        PixelWriter pw = gc.getPixelWriter();
        for (int i = 0; i < layer.getHeight(); i++) {
            for (int j = 0; j < layer.getWidth(); j++) {
                C7.Model.Color color = new C7.Model.Color(0,0,0,0);

                // If the point is on the layer, take the layers color, else put transparent.
                if(layer.isPointOnLayer(new Vector2D(j, i))){
                    color = layer.getGlobalPixel(j, i);
                }

                pw.setColor((j), (i), new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()));
            }
        }
    }
}

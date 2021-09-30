package C7.Controller;

import C7.IO.LayerIO;
import C7.Model.Layer.ILayer;
import C7.Model.Layer.Layer;
import C7.Model.Tools.ITool;
import C7.Model.Tools.ToolFactory;
import C7.Model.Tools.ToolProperties.IToolProperty;
import C7.Model.Vector.Vector2D;
import C7.Controller.Properties.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
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
    //@FXML AnchorPane canvas;
    Canvas canvas;
    @FXML ScrollPane scrollPane;
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

        layer = new Layer(600, 500, new C7.Model.Color(0, 0, 0, 0));

        canvas = new Canvas();

        canvas.setWidth(layer.getWidth());
        canvas.setHeight(layer.getHeight());

        gc = canvas.getGraphicsContext2D();

        scrollPane.setContent(canvas);


        updateView();

        setCurrentTool(ToolFactory.CreateCircularBrush());

        //Maybe shouldn't send controller? Couldn't come up with a better solution off the top of my head
        flowPaneTools.getChildren().add(new ToolButton(currentTool, "Circle", this));
        flowPaneTools.getChildren().add(new ToolButton(ToolFactory.CreateCalligraphyBrush(), "Calligraphy", this));
        flowPaneTools.getChildren().add(new ToolButton(ToolFactory.CreateFillBucket(), "Fill", this));


        canvas.setOnDragOver(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != canvas
                        && event.getDragboard().hasFiles()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        canvas.setOnDragDropped(new EventHandler<DragEvent>() {

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

        canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //Move stuff like this to the controller later
                Vector2D point = new Vector2D(event.getX(), event.getY());
                currentTool.apply(layer, oldPos, point);
                oldPos = point;
                updateView();
            }
        });

        canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
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

        canvas.setOnMouseReleased(new EventHandler<MouseEvent>() {
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

    void updateView(int x, int y, int width, int height) {
        PixelWriter pw = gc.getPixelWriter();
        for (int i = 0; i < height*2; i++) {
            for (int j = 0; j < width*2; j++) {
                C7.Model.Color color = layer.getPixel(x+j, y+i);
                if (color == null) continue;
                //Update to take into account canvas transform
                pw.setColor((x+j-width), (y+i-height), new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()));
            }
        }
    }

    void updateView() {
        PixelWriter pw = gc.getPixelWriter();
        for (int i = 0; i < layer.getHeight(); i++) {
            for (int j = 0; j < layer.getWidth(); j++) {
                C7.Model.Color color = layer.getPixel(j, i);
                //Update to take into account canvas transform
                pw.setColor((j), (i), new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()));
            }
        }
    }
}

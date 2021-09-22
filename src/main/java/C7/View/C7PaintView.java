package C7.View;

import C7.Model.Layer.ILayer;
import C7.Model.Layer.Layer;
import C7.Model.Tools.ITool;
//import C7.Model.Tools.PixelPen;
import C7.Model.Tools.ToolFactory;
import C7.Model.Vector.Vector2D;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;

import javax.tools.Tool;
import java.net.URL;
import java.util.ResourceBundle;

public class C7PaintView implements Initializable {
    @FXML Pane canvasPane;
    @FXML Canvas canvas;
    GraphicsContext gc;
    @FXML FlowPane flowPaneTools;
    @FXML FlowPane flowPaneProperties;

    ILayer layer; //Only one for now

    public void setCurrentTool(ITool tool) {
        this.currentTool = tool;
    }

    ITool currentTool;

    private Vector2D oldPos;

    public void initialize(URL location, ResourceBundle resources) {
        gc = canvas.getGraphicsContext2D();

        layer = new Layer(700, 500, new C7.Model.Color(0, 0, 0, 0));

        updateView();

        currentTool = ToolFactory.CreateCircularBrush(5, new C7.Model.Color(1, 0, 0, 1));
        //currentTool = new PixelPen(layer);

        //Maybe shouldn't send controller? Couldn't come up with a better solution off the top of my head
        flowPaneTools.getChildren().add(new ToolButton(currentTool, "Circle", this));
        flowPaneTools.getChildren().add(new ToolButton(ToolFactory.CreateCalligraphyBrush(5, new C7.Model.Color(0, 1, 0, 1)), "Calligraphy", this));
        flowPaneTools.getChildren().add(new ToolButton(ToolFactory.CreateFillBucket(new C7.Model.Color(0, 0, 1, 1), 0.2f), "Fill", this));

        canvasPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //Move stuff like this to the controller later
                Vector2D point = new Vector2D(event.getX(), event.getY());
                currentTool.apply(layer, oldPos, point);
                oldPos = point;
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

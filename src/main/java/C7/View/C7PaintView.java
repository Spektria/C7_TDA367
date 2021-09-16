package C7.View;

import C7.Layer.ILayer;
import C7.Layer.Layer;
import C7.Model.Tools.ITool;
import C7.Model.Tools.ToolFactory;
import C7.Model.Vector.Vector2D;
import C7.Project;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class C7PaintView implements Initializable {
    @FXML Pane canvasPane;
    @FXML Canvas canvas;
    GraphicsContext gc;
    @FXML FlowPane flowPaneTools;
    @FXML FlowPane flowPaneProperties;

    ILayer layer; //Only one for now

    ITool currentTool;

    public void initialize(URL location, ResourceBundle resources) {
        gc = canvas.getGraphicsContext2D();

        layer = new Layer(700, 500, new C7.Color(1, 1, 1, 0));

        currentTool = ToolFactory.CreateCircularBrush(layer, 1, new C7.Color(1, 0, 0, 1));

        canvasPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //Move stuff like this to the controller later
                currentTool.move(new Vector2D(event.getX(), event.getY()));
                //Arbitrary
                int updateMargin = 50;
                updateView(((int)event.getX() - updateMargin), ((int)event.getX() + updateMargin), ((int)event.getY() - updateMargin), ((int)event.getY() + updateMargin));
                //Code for updating the entire canvas, much slower but seems to give the same result
                //updateView(0, 0, layer.getWidth(), layer.getHeight());
            }
        });

        canvasPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("click");
                if (event.getButton() == MouseButton.PRIMARY) {
                    currentTool.beginDraw(new Vector2D(event.getX(), event.getY()));
                }
            }
        });

        canvasPane.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    currentTool.endDraw(new Vector2D(event.getX(), event.getY()));
                }
            }
        });

        //Test buttons
        for (int i = 0; i < 20; i++) {
            ToolButton button = new ToolButton();
            flowPaneTools.getChildren().add(button);
            System.out.println(button.getLayoutX());
        }
    }

    void updateView(int x, int y, int x1, int y1) {
        for (int i = 0; i < y1; i++) {
            for (int j = 0; j < x1; j++) {
                C7.Color color = layer.getPixel(x+j, y+i);
                if (color == null) continue;
                System.out.println(x+j);
                gc.setFill(new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()));
                //Update to take into account canvas transform
                gc.fillRect(x+j, y+i, x+j, y+i);
            }
        }
    }
}

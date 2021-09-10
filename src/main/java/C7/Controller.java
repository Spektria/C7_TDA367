package C7;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML Pane canvasPane;
    @FXML Canvas canvas;
    GraphicsContext gc;

    boolean primaryPressed;
    boolean secondaryPressed;

    Project currentProject;

    public void initialize(URL location, ResourceBundle resources) {
        gc = canvas.getGraphicsContext2D();

        canvasPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (primaryPressed) {
                    useTool(event.getX(), event.getY(), 0);
                } else if (secondaryPressed) {
                    useTool(event.getX(), event.getY(), 1);
                }
            }
        });

        canvasPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    secondaryPressed = false;
                    primaryPressed = true;
                    useTool(event.getX(), event.getY(), 0);
                } else if (event.getButton() == MouseButton.SECONDARY) {
                    primaryPressed = false;
                    secondaryPressed = true;
                    useTool(event.getX(), event.getY(), 1);
                }
            }
        });

        canvasPane.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    primaryPressed = false;
                }  else if (event.getButton() == MouseButton.SECONDARY) {
                    secondaryPressed = false;
                }
            }
        });

        
    }

    public void useTool(double x, double y, int button) {
        //Wait for tool interface
    }

    void updateView(int x, int y, int x1, int y1) {
        for (int i = 0; i < y1; i++) {
            for (int j = 0; j < x1; j++) {
                C7.Color color = currentProject.getPixel(x+j, x+i);
                gc.setFill(new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()));
                //Update to take into account canvas transform
                gc.fillRect(x+j, y+i, x+j, y+i);
            }
        }
    }
}

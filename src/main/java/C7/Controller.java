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

    public void initialize(URL location, ResourceBundle resources) {
        gc = canvas.getGraphicsContext2D();

        canvasPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (primaryPressed) {
                    draw(event.getX(), event.getY());
                } else if (secondaryPressed) {
                    erase(event.getX(), event.getY());
                }
            }
        });

        canvasPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    primaryPressed = true;
                    draw(event.getX(), event.getY());
                } else if (event.getButton() == MouseButton.SECONDARY) {
                    secondaryPressed = true;
                    erase(event.getX(), event.getY());
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

    public void draw(double x, double y) {
        //Change to use the current tool
        //gc.setFill(Color.BLACK);
        double size = 10;
        gc.fillOval(x - size/2,y - size/2,size,size);
    }

    public void erase(double x, double y) {
        //Probably make erasing just a tool and remove this
        double size = 10;
        //gc.setFill(Color.TRANSPARENT);
        //gc.fillOval(x - size/2,y - size/2,size,size);
        gc.clearRect(x - size/2,y - size/2,size,size);
    }
}

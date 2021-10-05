package C7;

import C7.Controller.MainController;
import C7.IO.ResourceIO;
import C7.Model.Color;
import C7.Model.Layer.ILayer;
import C7.Model.Layer.Layer;
import C7.View.IView;
import C7.View.ViewFactory;
import com.sun.tools.javac.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PaintApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        AnchorPane root = new AnchorPane();

        ILayer layer = new Layer(600, 600, new Color(0,0,0,0));
        IView view = ViewFactory.createView(layer);
        MainController controller = new MainController(view, layer, root);

        primaryStage.setTitle("PaintQlone");
        Scene scene = new Scene(root, 1280, 720+25);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

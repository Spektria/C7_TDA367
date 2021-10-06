package C7;

import C7.Controller.ControllerFactory;
import C7.Controller.IMainController;
import C7.Model.Layer.ILayerManager;
import C7.Model.Layer.LayerManager;
import C7.Model.Vector.Vector2D;
import C7.View.IView;
import C7.View.ViewFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PaintApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        // Create model
        ILayerManager manager = new LayerManager();
        manager.setActiveLayer(manager.createLayer(
                600, 600,
                new Vector2D(1,1),
                0,
                new Vector2D(1,1))
        );

        // Create view and controller
        IView view = ViewFactory.createView(manager);
        AnchorPane root = new AnchorPane();
        IMainController controller = ControllerFactory.createController(view, manager, root);

        // Setup window
        primaryStage.setTitle("PaintQlone");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

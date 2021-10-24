package C7;

import C7.Controller.ControllerFactory;
import C7.Controller.IMainController;
import C7.Model.IProject;
import C7.Model.ProjectFactory;
import C7.Util.ResourceIO;
import C7.View.IView;
import C7.View.ViewFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PaintApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        // Create model
        IProject project = ProjectFactory.createProjectWithBaseLayer("Project1", 700, 500);

        // Create view and controller
        IView view = ViewFactory.createView(project);
        AnchorPane root = new AnchorPane();
        IMainController controller = ControllerFactory.createController(view, project, root);

        // Setup window
        primaryStage.setTitle("C7Paint");
        Scene scene = new Scene(root);
        Image i = new Image(ResourceIO.getGlobalResource("icon.png").toString());
        primaryStage.getIcons().add(i);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

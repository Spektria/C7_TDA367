package C7.Controller;

import C7.Model.Layer.ILayerManager;
import C7.View.IView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * Factory for creating the controller in this application.
 * @author Hugo Ekstrand
 */
public class ControllerFactory {

    public static IMainController createController(IView view, ILayerManager manager, AnchorPane root) throws Exception {
        return new MainController(view, manager, root);
    }
}

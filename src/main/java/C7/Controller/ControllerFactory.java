package C7.Controller;

import C7.Model.IProject;
import C7.View.IView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * Factory for creating the controller in this application.
 * @author Hugo Ekstrand
 */
public class ControllerFactory {

    public static IMainController createController(IView view, IProject project, AnchorPane root) throws Exception {
        return new MainController(view, project, root);
    }
}

package C7; /***
 * C7.C7Paint simply initializes the application.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class C7Paint extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        var a = getClass().getResource("sample.fxml");
        Parent root = FXMLLoader.load(getClass().getResource("../sample.fxml"));
        primaryStage.setTitle("PaintQlon");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

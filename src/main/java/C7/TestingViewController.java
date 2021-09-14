package C7;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestingViewController extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        var a = getClass().getResource("C7Paint.fxml");
        Parent root = FXMLLoader.load(getClass().getResource("../C7Paint.fxml"));
        primaryStage.setTitle("PaintQlone");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
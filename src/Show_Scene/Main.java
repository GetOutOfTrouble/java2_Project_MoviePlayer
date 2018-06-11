package Show_Scene;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("ShowScene.fxml"));
            Scene scene = new Scene(root, 925, 600);
            scene.getStylesheets().add(getClass().getResource("showScene.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setWidth(925);
            primaryStage.setHeight(600);
            primaryStage.setResizable(true);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
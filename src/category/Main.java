package category;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = null;
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("posterCategory.fxml"));
                PosterCategory pos = new PosterCategory();
                pos.setCategory("惊悚");
                pos.setLanguage("中文");
                loader.setController(pos);
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root, 925, 600);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setWidth(925);
            primaryStage.setHeight(600);
            primaryStage.setResizable(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

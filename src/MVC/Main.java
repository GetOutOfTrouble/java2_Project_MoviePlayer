package MVC;

import Scraping.InformationGetter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class Main extends Application {
    private  final  static  String  PropertyPATH = Controller.class.getClassLoader().getResource("").getPath()+"Properties/";
    @Override
    public void start(Stage primaryStage) throws Exception{
        ResourceBundle bundle = InformationGetter.PropertyReader(PropertyPATH,"zh");// language :"zh" , "en"
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
        Parent root =loader.load();
        loader.setResources(bundle);
        String title =((PropertyResourceBundle) bundle).handleGetObject("title").toString();
        primaryStage.setTitle(title);
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

    }
}

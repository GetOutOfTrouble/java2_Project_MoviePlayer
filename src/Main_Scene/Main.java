package Main_Scene;

import Scraping.InformationGetter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class Main extends Application {
    private static FXMLLoader loader;
    private static Controller con;
    private Scene MainScene;
    private HashMap<String, Stage> stageHashMap = new HashMap<>();
    private  final  static  String  PropertyPATH = new File("Properties").getAbsolutePath()+"\\";
    private Stage primaryStage;
    public static Main[] instances = new Main[1];
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage=primaryStage;
        instances[0] = this;
        con = new Controller();
        String lang = con.getLanguage();
        ResourceBundle bundle = InformationGetter.PropertyReader(PropertyPATH,"Main",lang);// language :"中文" , "English"
        loader= new FXMLLoader();
        loader.setLocation(getClass().getResource("flexibleMain.fxml"));
        loader.setResources(bundle);
        con = new Controller();
        loader.setController(con);
        Parent root=loader.load();
//        Rectangle2D screenBounds
//                = Screen.getPrimary().getVisualBounds();
        MainScene = new Scene(root, 925, 600);
        primaryStage.setTitle(getTitle(loader));
        MainScene.getStylesheets().add(new File("CSS").listFiles()[1].toURI().toURL().toExternalForm());
        primaryStage.setScene(MainScene);
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.show();
        primaryStage.setOnCloseRequest((o)->{Controller.stop();});

    }

    private Parent getRoot() throws IOException {
        String lang = con.getLanguage();
        ResourceBundle bundle = InformationGetter.PropertyReader(PropertyPATH,"Main",lang);// language :"中文" , "English"
        loader= new FXMLLoader();
        loader.setLocation(getClass().getResource("flexibleMain.fxml"));
        loader.setResources(bundle);
        con = new Controller();
        loader.setController(con);
        return loader.load();
    }

    public void changeSceneLanguage() throws IOException {
        con.stop();
        this.MainScene = new Scene(getRoot(), 925, 600);
        MainScene.getStylesheets().add(new File("CSS").listFiles()[1].toURI().toURL().toExternalForm());
        primaryStage.setTitle(getTitle(loader));
        primaryStage.setScene(MainScene);

    }
    private String getTitle(FXMLLoader loader) {
        String title =    ((PropertyResourceBundle) loader.getResources()).handleGetObject("title").toString();
        return title;
    }



    public static void main(String[] args) {
        launch(args);

    }

    public Scene getMainScene() {
        return MainScene;
    }
}

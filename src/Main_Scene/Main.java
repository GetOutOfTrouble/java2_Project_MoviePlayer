package Main_Scene;

import Scraping.InformationGetter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class
Main extends Application {
    /**
     * path of property folder
     */
    public final static String PropertyPATH = new File("Properties").getAbsolutePath() + "\\";
    /**
     *
     */
    public static Main[] instances = new Main[1];
    /**
     * current loader of main scene
     */
    private static FXMLLoader loader;
    /**
     * current controller of main scene
     */
    private static Controller con;
    public String[] images = new String[12];
    public int totalTime;
    /**
     * Main scene instance
     */
    private Scene MainScene;
    /**
     * the only stage
     */
    private Stage primaryStage;

    /**
     * @return the current only controller
     */
    public static Controller getCon() {
        return con;
    }

    /**
     * @param args none
     */
    public static void main(String[] args) {
        launch(args);

    }

    /**
     * @param primaryStage initial stage to start
     * @throws IOException if property file can not be read
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        instances[0] = this;
        con = new Controller();
        String lang = con.getLanguage();
        ResourceBundle bundle = InformationGetter.propertyReader(PropertyPATH, "Main", lang);// language :"中文" , "English"
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("flexibleMain.fxml"));
        loader.setResources(bundle);
        con = new Controller();
        loader.setController(con);
        Parent root = loader.load();
//        Rectangle2D screenBounds
//                = Screen.getPrimary().getVisualBounds();
        MainScene = new Scene(root, 925, 600);


        primaryStage.setTitle(getTitle(loader));
        MainScene.getStylesheets().add(new File("CSS\\theme.css").toURI().toURL().toExternalForm());
        primaryStage.setScene(MainScene);
        primaryStage.initStyle(StageStyle.DECORATED);
        con.getSearch().requestFocus();
        primaryStage.show();
        primaryStage.setOnCloseRequest((o) -> {
            Controller.stop();
        });

    }

    /**
     * @return root scene
     * @throws IOException if property file can not be read
     */
    private Parent getRoot() throws IOException {
        String lang = con.getLanguage();
        ResourceBundle bundle = InformationGetter.propertyReader(PropertyPATH, "Main", lang);// language :"中文" , "English"
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("flexibleMain.fxml"));
        loader.setResources(bundle);
        con = new Controller();
        con.setLanguage(lang);
        loader.setController(con);
        return loader.load();
    }

    /**
     * called by controller
     *
     * @throws IOException if property file can not be read
     */
    void changeSceneLanguage() throws IOException {
        Controller.stop();
        this.MainScene = new Scene(getRoot(), 925, 600);
        MainScene.getStylesheets().add(new File("CSS\\theme.css").toURI().toURL().toExternalForm());
        primaryStage.setTitle(getTitle(loader));
        primaryStage.setScene(MainScene);

    }

    /**
     * @return get the main scene of the only stage
     */
    public Scene getMainScene() {
        return MainScene;
    }

    /**
     *
     * @return get current title  of the only stage
     */
    private String getTitle(FXMLLoader loader) {
        String title = ((PropertyResourceBundle) loader.getResources()).handleGetObject("title").toString();
        return title;
    }

    /**
     *
     * @return get the only stage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
}

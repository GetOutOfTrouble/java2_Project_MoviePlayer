package category;


import Main_Scene.Controller;
import Main_Scene.Main;
import Show_Scene.ShowSceneController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;


public class PosterCategory implements Initializable {
    private Lighting light;
    private String category;
    private String language;
    @FXML
    private ScrollPane sp;
    @FXML
    private ImageView m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12;

    public PosterCategory() {
        Light.Distant distant = new Light.Distant();
        distant.setAzimuth(45);
        distant.setElevation(45);
        distant.setColor(Color.rgb(255, 255, 255));
        light = new Lighting();
        light.setLight(distant);
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void showPoster() {
        ImageView[] ImageViews = {m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12};
        String filename = Main.PropertyPATH + "Category_" + language + ".properties";
        String filename1 = Main.PropertyPATH + "Film_" + language + ".properties";
        Properties p = new Properties();
        FileInputStream fis;
        InputStreamReader isr;
        Properties p1 = new Properties();
        FileInputStream fi;
        InputStreamReader is;
        try {
            fis = new FileInputStream(filename);
            isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            p.load(br);
            fi = new FileInputStream(filename1);
            is = new InputStreamReader(fi, "UTF-8");
            BufferedReader b = new BufferedReader(is);
            p1.load(b);
            String[] films = p.getProperty(category).split("\\|");
            String[] posters = new String[films.length];
            for (int i = films.length; i < ImageViews.length; i++) {

                ImageView vip = ImageViews[i];
                vip.setFocusTraversable(false);
                vip.mouseTransparentProperty().setValue(true);
            }
            for (int i = 0; i < films.length; i++) {

                ImageView vip = ImageViews[i];
                vip.setFocusTraversable(true);
                vip.mouseTransparentProperty().setValue(false);
                vip.focusedProperty().addListener((o) -> {
                    {
                        if (vip.isFocused())
                            vip.setEffect(light);
                        else vip.setEffect(null);
                    }

                });
                vip.hoverProperty().addListener((o) -> {
                    {
                        if (vip.isHover())
                            vip.setEffect(light);
                        else vip.setEffect(null);
                    }
                });
            }
            for (int i = 0; i < films.length; i++) {
                String[] u = p1.getProperty(films[i]).split("\\|");
                File file = new File("Poster\\" + u[u.length - 1]);
                String localUrl = file.toURI().toURL().toString();
                Image image1 = new Image(localUrl);
                ImageViews[i].setImage(image1);
            }


        } catch (FileNotFoundException e1) {
            System.out.println("The file contain director->film is not found");
            e1.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        for (ImageView a : ImageViews
                ) {
            a.setOnMouseClicked((o) -> {
                String[] sp = a.imageProperty().get().impl_getUrl().split("/");
                String Image = sp[sp.length - 1];
                Image = Image.substring(0, Image.length() - 4);
                Controller con = Main.getCon();
                try {
                    openShowScene(con.isNight(), con.getLanguage(), Image);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            });
            a.setOnKeyPressed((o) -> {
                if (o.getCode().equals(KeyCode.ENTER)) {
                    String[] sp = a.imageProperty().get().impl_getUrl().split("/");
                    String Image = sp[sp.length - 1];
                    Image = Image.substring(0, Image.length() - 4);
                    Controller con = Main.getCon();
                    try {
                        openShowScene(con.isNight(), con.getLanguage(), Image);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        sp.getStyleClass().add("edge-to-edge");
        sp.setFocusTraversable(false);
        showPoster();
        sp.setOnKeyPressed((o) -> {
            if (o.getCode().equals(KeyCode.ESCAPE
            )) {
                Main.instances[0].getPrimaryStage().setScene(Main.instances[0].getMainScene());
            }
        });
    }

    public void openShowScene(boolean isNight, String language, String target) throws MalformedURLException {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../Show_Scene/ShowScene.fxml"));
            ShowSceneController con = new ShowSceneController();
            con.setLanguage(language);
            con.setTarget(target);
            loader.setController(con);
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (isNight) {
            root.getStylesheets().add(new File("CSS\\showstage_dark.css").toURI().toURL().toExternalForm());
        } else {
            root.getStylesheets().add(new File("CSS\\showstage.css").toURI().toURL().toExternalForm());
        }
        Scene scene = new Scene(root, 925, 600);
        Main.instances[0].getPrimaryStage().setScene(scene);
    }

    public void setCategory(String category) {
        this.category = category;
    }
}



























































































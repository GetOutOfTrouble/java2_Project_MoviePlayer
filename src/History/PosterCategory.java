package History;


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

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;


public class PosterCategory implements Initializable {
    private Lighting light;
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

    public void showPoster() throws MalformedURLException {
        ImageView[] ImageViews = {m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12};
        String[] history = Main.instances[0].images;
        for (int i = 0; i < ImageViews.length; i++) {
            ImageView vip = ImageViews[i];
            vip.setFocusTraversable(false);
            vip.mouseTransparentProperty().setValue(true);
        }
        for (int i = 0; i < Main.instances[0].totalTime && i < history.length; i++) {
            ImageViews[i].setImage(new Image(new File("Poster\\" + history[i]).toURI().toURL().toExternalForm()));
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


    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        sp.setFocusTraversable(false);
        try {
            showPoster();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ImageView[] ImageViews = {m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12};
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

}

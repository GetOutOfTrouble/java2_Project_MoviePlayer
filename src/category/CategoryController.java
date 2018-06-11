package category;

import Main_Scene.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class CategoryController implements Initializable {

    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private Button button5;
    @FXML
    private Button button6;
    @FXML
    private Button button7;
    @FXML
    private Button button8;
    @FXML
    private Button button9;
    @FXML
    private Button button10;
    @FXML
    private Button button11;
    @FXML
    private Button button12;
    @FXML
    private Button button13;
    @FXML
    private Button button14;

    @FXML
    private GridPane grid;
    @FXML
    private ScrollPane sp;
    private String language;

    public void setLanguage(String language) {
        this.language = language;
    }

    public void category() {

        Button[] buttons = {button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12, button13, button14};
        String filename = Main.PropertyPATH + "Category_" + language + ".properties";
        Properties p = new Properties();
        FileInputStream fis;
        InputStreamReader isr;
        try {
            fis = new FileInputStream(filename);
            isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            p.load(br);
            Object[] cate = p.keySet().toArray();
            for (int i = 0; i < buttons.length; i++) {
                String a = cate[i].toString();
                buttons[i].setText(a);
            }

        } catch (FileNotFoundException e1) {
            System.out.println("The file contain director->film is not found");
            e1.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }


    public void initialize(URL location, ResourceBundle resources) {
        category();
        Button[] buttons = {button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12, button13, button14};
        for (Button en : buttons
                ) {
            en.setOnMouseClicked((o) -> {
                Main_Scene.Controller s = Main_Scene.Main.getCon();
                try {
                    openShowPoster(s.isNight(), s.getLanguage(), en.getText());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            });
            en.setOnKeyPressed((o) -> {
                if (o.getCode().equals(KeyCode.ENTER)) {
                    Main_Scene.Controller s = Main_Scene.Main.getCon();
                    try {
                        openShowPoster(s.isNight(), s.getLanguage(), en.getText());
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

    private void openShowPoster(boolean isNight, String language, String category) throws MalformedURLException {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("posterCategory.fxml"));
            PosterCategory pos = new PosterCategory();
            pos.setCategory(category);
            pos.setLanguage(language);
            loader.setController(pos);
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (isNight) {
            root.getStylesheets().add(new File("CSS\\postershow_dark.css").toURI().toURL().toExternalForm());
        } else {
            root.getStylesheets().add(new File("CSS\\postershow.css").toURI().toURL().toExternalForm());
        }
        Scene scene = new Scene(root, 925, 600);
        Main.instances[0].getPrimaryStage().setScene(scene);
    }
}

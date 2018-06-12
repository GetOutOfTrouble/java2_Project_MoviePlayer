package Show_Scene;

import Main_Scene.Main;
import Player.MainController;
import Scraping.InformationGetter;
import Search_Scene.Search;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import static Main_Scene.Main.PropertyPATH;

public class ShowSceneController implements Initializable {
    @FXML
    private ImageView poster;
    @FXML
    private ScrollPane sp;

    @FXML
    private Text text1;
    @FXML
    private Text text2;
    @FXML
    private Text text3;

    @FXML
    private Text text4;
    @FXML
    private Text text5;
    @FXML
    private Text text6;
    @FXML
    private Text text7;
    @FXML
    private Text text8;
    @FXML
    private Text text9;
    @FXML
    private Text text10;
    @FXML
    private Text text11;
    @FXML
    private Text text12;
    @FXML
    private Text text13;
    @FXML
    private Text text14;
    @FXML
    private Text text15;

    @FXML
    private Text text16;

    @FXML
    private Text title;
    @FXML
    private Button button;
    @FXML
    private Button button2;
    /**
     * current language
     */
    private String language;
    /**
     * movie target
     */
    private String target;

    /**
     * @param target clicked movie
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * set showing language
     *
     * @param language current language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * initialize information
     *only for English language
     * @throws MalformedURLException if there is some false when load property
     */
    public void info() throws MalformedURLException {
        String filename = PropertyPATH + "Film_" + language + ".properties";
        Properties p = new Properties();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"))) {
            p.load(br);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] mm = p.getProperty(target.replace("_", " ")).split("\\|");

        String[] m = new String[mm.length - 1];
        for (int i = 0; i < mm.length - 1; i++) {
            m[i] = mm[i];
        }
        String[] n = new String[2 * m.length];
        for (int i = 0; i < m.length; i++) {
            String[] s = m[i].split(":");
            n[2 * (i + 1) - 2] = s[0];
            n[2 * (i + 1) - 1] = s[1].replaceAll("_", ", ");
        }
        title.setText(target);
        text1.setText(n[0]);
        text2.setText(n[1]);
        text3.setText(n[2]);
        text4.setText(n[3]);
        text5.setText(n[4]);
        text6.setText(n[5]);
        text7.setText(n[6]);
        text8.setText(n[7]);
        text9.setText(n[8]);
        text10.setText(n[9]);
        text11.setText(n[10]);
        text12.setText(n[11]);
        text13.setText(n[12]);
        text14.setText(n[13]);
        text16.setText(n[14]);
        text15.setText("    " + n[15]);
        File file = new File("Poster\\" + mm[mm.length - 1]);
        String localUrl = file.toURI().toURL().toString();
        Image image1 = new Image(localUrl);
        poster.setImage(image1);
        sp.getStyleClass().add("edge-to-edge");
    }

    /**
     * for chinese language
     * @throws MalformedURLException url false
     */
    public void infoChinese() throws MalformedURLException {
        String filename = PropertyPATH + "Film_" + language + ".properties";
        Properties p = new Properties();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"))) {
            p.load(br);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (p.getProperty(target) == null) {
            target=target.replace(" ", "_");
            target = Search.getChineseName(target);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(PropertyPATH + "Film_" + language + ".properties"), "UTF-8"))) {
                p.clear();
                p.load(br);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String[] mm = p.getProperty(target.replace("_", " ")).split("\\|");

        String[] m = new String[mm.length - 1];
        for (int i = 0; i < mm.length - 1; i++) {
            m[i] = mm[i];
        }
        String[] n = new String[2 * m.length];
        for (int i = 0; i < m.length; i++) {
            String[] s = m[i].split(":");
            n[2 * (i + 1) - 2] = s[0];
            n[2 * (i + 1) - 1] = s[1].replaceAll("_", ", ");
        }
        title.setText(target);
        text1.setText(n[0]);
        text2.setText(n[1]);
        text3.setText(n[2]);
        text4.setText(n[3]);
        text5.setText(n[4]);
        text6.setText(n[5]);
        text7.setText(n[6]);
        text8.setText(n[7]);
        text9.setText(n[8]);
        text10.setText(n[9]);
        text11.setText(n[10]);
        text12.setText(n[11]);
        text13.setText(n[12]);
        text14.setText(n[13]);
        text16.setText(n[14]);
        text15.setText("    " + n[15]);
        File file = new File("Poster\\" + mm[mm.length - 1]);
        String localUrl = file.toURI().toURL().toString();
        Image image1 = new Image(localUrl);
        poster.setImage(image1);
        sp.getStyleClass().add("edge-to-edge");
    }

    /**
     * @param location  fxml location
     * @param resources property
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sp.setFocusTraversable(true);
        if (language.equals("English")) {
            try {
                info();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                infoChinese();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        button.setOnMouseClicked((o) -> {
            String a = Search.getFilmAbsolutePath(target);

            target = Search.getEnglishName(target);
            Main.instances[0].images[Main.instances[0].totalTime % 12] = target + ".jpg";
            Main.instances[0].totalTime++;
            try {
                openPlayer(a);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });
        button.setOnKeyPressed((o) -> {
            if (o.getCode().equals(KeyCode.ENTER)) {
                String a = Search.getFilmAbsolutePath(target);

                target = Search.getEnglishName(target);
                Main.instances[0].images[Main.instances[0].totalTime % 12] = target + ".jpg";
                Main.instances[0].totalTime++;
                try {
                    openPlayer(a);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
        button2.setOnMouseClicked((o) -> {
            Main_Scene.Main.instances[0].getPrimaryStage().setScene(Main_Scene.Main.instances[0].getMainScene());
        });
        button2.setOnKeyPressed((o) -> {
            if (o.getCode().equals(KeyCode.ENTER)) {
                Main_Scene.Main.instances[0].getPrimaryStage().setScene(Main.instances[0].getMainScene());
            }
        });

        try {
            button.setGraphic(InformationGetter.svgGraphic(new File("Images/play.svg").getAbsolutePath(), 0.06));
            button2.setGraphic(InformationGetter.svgGraphic(new File("Images/home.svg").getAbsolutePath(), 0.06));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    /**
     * @param a movie location
     * @throws MalformedURLException if url convert false
     */
    private void openPlayer(String a) throws MalformedURLException {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../Player/Main.fxml"));
            MainController con = new MainController();
            con.setPath(a);
            loader.setController(con);
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        root.getStylesheets().add(new File("CSS\\player.css").toURI().toURL().toExternalForm());
        Scene scene = new Scene(root, 925, 600);
        Main.instances[0].getPrimaryStage().setScene(scene);
    }
}
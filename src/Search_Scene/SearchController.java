package Search_Scene;

import Main_Scene.Controller;
import Main_Scene.Main;
import Main_Scene.film;
import Scraping.InformationGetter;
import Show_Scene.ShowSceneController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    @FXML
    TextField in;
    @FXML
    TableView tbv;
    @FXML
    Button space;
    @FXML
    Button home;
    @FXML
    Button back;
    @FXML
    Button caplock;
    @FXML
    Button a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, d0, d1, d2, d3, d4, d5, d6, d7, d8, d9;
    @FXML
    TableColumn<film, String> name, country, Director, category, duration;
    @FXML
    private Text time;
    private ObservableList<film> sd;
    private boolean isCap = true;
    private film selected;
    private boolean hasSeleted = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        home.setOnMouseClicked((o) -> {
            Main.instances[0].getPrimaryStage().setScene(Main.instances[0].getMainScene());
        });
        home.setOnKeyPressed((o) -> {
            if (o.getCode().equals(KeyCode.ENTER)) {
                Main.instances[0].getPrimaryStage().setScene(Main.instances[0].getMainScene());
            }
        });
        space.setOnMouseClicked((o) -> {
            in.appendText(" ");
        });
        space.setOnKeyPressed((o) -> {
            if (o.getCode().equals(KeyCode.ENTER)) {
                in.appendText(" ");
            }
        });
        back.setOnMouseClicked((o) -> {
            if (in.getText().length() > 0)
                in.deleteText(in.getText().length() - 1, in.getText().length());

        });
        back.setOnKeyPressed((o) -> {
            if (o.getCode().equals(KeyCode.ENTER)) {
                if (in.getText().length() > 0)
                    in.deleteText(in.getText().length() - 1, in.getText().length());
            }
        });
        Button[] inputs = {a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, d0, d1, d2, d3, d4, d5, d6, d7, d8, d9};
        for (Button en : inputs
                ) {
            en.setOnMouseClicked((o) -> {
                in.appendText(en.getText());
            });
            en.setOnKeyPressed((o) -> {
                if (o.getCode().equals(KeyCode.ENTER)) {
                    in.appendText(en.getText());
                }
            });
        }
        time.textProperty().bind(new SimpleStringProperty(Main.getCon().getTime().getText()));
        a.requestFocus();
        film d = new film("s", "b", "is", "me", "sb");
        //set cell value factory
        name.setCellValueFactory(new PropertyValueFactory<>("filmName"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        Director.setCellValueFactory(new PropertyValueFactory<>("director"));
        country.setCellValueFactory(new PropertyValueFactory<>("country"));
        in.textProperty().addListener((a, b, c) -> {
            tbv.getItems().clear();
            ObservableList<film> results = FXCollections.observableArrayList(Search.Search(c, Main.getCon().getLanguage()));
            tbv.setItems(results);
        });
        TableColumn[] ts = {name, category, duration, Director, country};
        caplock.setOnMouseClicked((o) -> {
            if (isCap) {
                for (Button en : inputs
                        ) {
                    en.setText(en.getText().toLowerCase());
                }
                isCap = !isCap;
            } else {
                for (Button en : inputs
                        ) {
                    en.setText(en.getText().toUpperCase());
                }
                isCap = !isCap;
            }
        });
        tbv.getSelectionModel().selectedItemProperty().addListener((a, b, c) -> {
            selected = (film) c;
            hasSeleted = true;
        });
        tbv.setOnMouseClicked((o) -> {
            if (o.getClickCount() > 1) {
                String name = selected.getFilmName();
                Controller con = Main.getCon();
                boolean isNight = con.isNight();
                try {
                    openShowScene(isNight, con.getLanguage(), name);
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        tbv.setOnKeyPressed((o) -> {
            if (o.getCode().equals(KeyCode.ENTER)) {
                String name = selected.getFilmName();
                Controller con = Main.getCon();
                boolean isNight = con.isNight();
                try {
                    openShowScene(isNight, con.getLanguage(), name);
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        // images
        try {
            home.setGraphic(InformationGetter.svgGraphic(new File("Images/home.svg").getAbsolutePath(), 0.05));
            space.setGraphic(InformationGetter.svgGraphic(new File("Images/space.svg").getAbsolutePath(), 1.15));
            back.setGraphic(InformationGetter.svgGraphic(new File("Images/backspace.svg").getAbsolutePath(), 1.1));
            caplock.setGraphic(InformationGetter.svgGraphic(new File("Images/keyboard-capslock.svg").getAbsolutePath(), 1.2));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }


    }

    private void openShowScene(boolean isNight, String language, String target) throws MalformedURLException {
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

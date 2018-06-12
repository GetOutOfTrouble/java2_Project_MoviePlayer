package Player;

import Main_Scene.Main;
import Scraping.InformationGetter;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    Button fast;
    @FXML
    Button fast2;
    @FXML
    Button slow;
    @FXML
    Button slow2;
    @FXML
    Button reload;
    @FXML
    Button reload2;
    @FXML
    Button play;
    @FXML
    Button play2;
    @FXML
    Slider progressBar;
    @FXML
    HBox box;
    @FXML
    Slider volumeSlider;
    @FXML
    private Button home;
    @FXML
    private MediaView mv;
    private MediaPlayer mp;
    private Media me;
    private String path;


    /**
     * @param path slider progress
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return current time
     */
    public double getCurrentTime() {
        return mp.getCurrentTime().toMillis();
    }

    /**
     * @param time current time
     */
    public void setCurrentTime(double time) {
        Duration setTime = new Duration(time);
        mp.seek(setTime);
    }

    /**
     * @return total run time
     */
    public double getTotalRunTime() {
        return me.getDuration().toMillis();
    }

    /**
     * initialize button
     *
     * @param arg0 location
     * @param arg1 properties
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        home.mouseTransparentProperty().setValue(false);
        Group plays = null;
        Group plays2 = null;
        try {
            plays = InformationGetter.svgGraphic(new File("Images/play-.svg").getAbsolutePath(), 0.05);
            play.setGraphic(plays);
            slow.setGraphic(InformationGetter.svgGraphic(new File("Images/slow.svg").getAbsolutePath(), 0.05));
            fast.setGraphic(InformationGetter.svgGraphic(new File("Images/fast.svg").getAbsolutePath(), 0.05));
            reload.setGraphic(InformationGetter.svgGraphic(new File("Images/reload.svg").getAbsolutePath(), 0.05));
            plays2 = InformationGetter.svgGraphic(new File("Images/play-.svg").getAbsolutePath(), 0.07);
            play2.setGraphic(plays2);
            slow2.setGraphic(InformationGetter.svgGraphic(new File("Images/slow.svg").getAbsolutePath(), 0.07));
            fast2.setGraphic(InformationGetter.svgGraphic(new File("Images/fast.svg").getAbsolutePath(), 0.07));
            reload2.setGraphic(InformationGetter.svgGraphic(new File("Images/reload.svg").getAbsolutePath(), 0.07));
            home.setGraphic(InformationGetter.svgGraphic(new File("Images/home.svg").getAbsolutePath(), 0.03));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        me = new Media(new File(path).toURI().toString());
        mp = new MediaPlayer(me);
        mv.setMediaPlayer(mp);
        final DoubleProperty width = mv.fitWidthProperty();
        final DoubleProperty height = mv.fitHeightProperty();
        width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
        mv.setPreserveRatio(true);
        progressBar.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
                if (progressBar.isValueChanging()) {
                    if (play2.getText().equals(">")) {
                        mp.play();
                        //setCurrentTime(progressBar.getValue());
                        progressBar.setMax(getTotalRunTime());
                        progressBar.setMin(0);
                        setCurrentTime(progressBar.getValue());
                        mp.setRate(1);
                        mp.pause();
                    } else {
                        setCurrentTime(progressBar.getValue());
                        mp.play();
                    }
                }
            }
        });
        play2.setText(">");
        play.setText(">");
        Group finalPlays = plays;
        Group finalPlays1 = plays2;
        Group pauses = null;
        Group pauses2 = null;
        try {
            pauses2 = InformationGetter.svgGraphic(new File("Images/pause.svg").getAbsolutePath(), 0.07);
            pauses = InformationGetter.svgGraphic(new File("Images/pause.svg").getAbsolutePath(), 0.05);
        } catch (FileNotFoundException w) {
            w.printStackTrace();
        }
        Group finalPauses = pauses;
        Group finalPauses1 = pauses2;
        play2.setOnAction(e -> {
            if (play2.getText().equals(">")) {
                mp.play();
                progressBar.setMax(getTotalRunTime());
                progressBar.setMin(0);
                mp.setRate(1);
                play2.setText("||");
                play.setText("||");
                play.setGraphic(finalPauses);
                play2.setGraphic(finalPauses1);
            } else {
                mp.pause();
                play2.setText(">");
                play.setText(">");
                play.setGraphic(finalPlays);
                play2.setGraphic(finalPlays1);
            }
        });
        box.setVisible(false);

        slow2.setVisible(false);
        slow.setVisible(true);

        play2.setVisible(false);
        play.setVisible(true);

        fast2.setVisible(false);
        fast.setVisible(true);

        reload2.setVisible(false);
        reload.setVisible(true);

        mp.currentTimeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
                progressBar.setValue(mp.getCurrentTime().toMillis());

            }
        });
        volumeSlider.setValue(mp.getVolume() * 100);
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
                mp.setVolume(volumeSlider.getValue() / 100);
            }
        });
        home.setOnMouseClicked((o) -> {
            Main_Scene.Main.instances[0].getPrimaryStage().setScene(Main_Scene.Main.instances[0].getMainScene());
        });
        home.setOnKeyPressed((o) -> {
            if (o.getCode().equals(KeyCode.ENTER)) {
                Main_Scene.Main.instances[0].getPrimaryStage().setScene(Main.instances[0].getMainScene());
            }
        });
    }

    /**
     * set rate = 1.5
     * @param event when click fast
     */
    public void fast(ActionEvent event) {
        mp.setRate(1.50);

    }

    /**
     *  set rate =0.5
     * @param event when click slow
     */
    public void slow(ActionEvent event) {
        mp.setRate(.50);
    }

    /**
     *  reload the whole movie
     * @param event when click reload
     */
    public void re1oad(ActionEvent event) {
        mp.seek(mp.getStartTime());
        mp.play();
    }

    /**
     * dynamic effect
     * @param event when mouse move in
     */
    public void playEnter(MouseEvent event) {
        play.setVisible(false);
        play2.setVisible(true);
    }

    /**
     * dynamic effect
     * @param event when mouse move out
     */
    public void slowexit(MouseEvent event) {

        slow2.setVisible(false);

        slow.setVisible(true);

    }

    /**
     * dynamic effect
     * @param event when mouse move in
     */
    public void slowEnter(MouseEvent event) {
        slow.setVisible(false);
        slow2.setVisible(true);
    }

    /**
     * dynamic effect
     * @param event when mouse move out
     */
    public void playexit(MouseEvent event) {

        play2.setVisible(false);

        play.setVisible(true);

    }

    /**
     * dynamic effect
     * @param event when mouse move in
     */
    public void fastEnter(MouseEvent event) {
        fast.setVisible(false);
        fast2.setVisible(true);
    }

    /**
     * dynamic effect
     * @param event when mouse move out
     */
    public void fastexit(MouseEvent event) {

        fast2.setVisible(false);

        fast.setVisible(true);

    }

    /**
     * dynamic effect
     * @param event when mouse move in
     */
    public void reloadEnter(MouseEvent event) {
        reload.setVisible(false);
        reload2.setVisible(true);
    }

    /**
     * dynamic effect
     * @param event when mouse move out
     */
    public void reloadexit(MouseEvent event) {

        reload2.setVisible(false);

        reload.setVisible(true);

    }

    /**
     * dynamic effect
     * @param event when mouse move in
     */
    public void mouseEnter(MouseEvent event) {
        box.setVisible(true);
    }

    /**
     * dynamic effect
     * @param event when mouse move out
     */
    public void mouseExit(MouseEvent event) {
        box.setVisible(false);
    }



}
	
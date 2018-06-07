package Main_Scene;

import SwitchButton.SlidingSwitch;
import afester.javafx.svg.SvgLoader;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import static Scraping.InformationGetter.openImageStream;

public class Controller implements Initializable, Runnable {
    private File poster;
    private int count;
    @FXML
    private BorderPane bp;
    @FXML
    private HBox toggle;
    @FXML
    private StackPane stackPane;
    @FXML
    private Button search;
    @FXML
    private Button category;
    @FXML
    private Button history;
    @FXML
    private Button refresh;
    @FXML
    private Text time;
    @FXML
    private ImageView poster1;
    @FXML
    private ImageView poster2;
    @FXML
    private ImageView poster3;
    @FXML
    private ComboBox<String>languageSwitcher;
    private Lighting light;
    private Calendar time_system;
    private static Thread interna;
    private  String language ;
    private  static boolean stop;

    public Controller() {
        count = 4;
        language = "中文";
        poster = new File("Poster");
        stop = false;
        interna = new Thread( this);
        Light.Distant distant= new Light.Distant();
        distant.setAzimuth(45);
        distant.setElevation(45);
        distant.setColor(Color.rgb(255,255,255));
        light = new Lighting();
        light.setLight(distant);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //button
        try {
            String ImagePath = new File("").getAbsolutePath();
            Group image = svgGraphic(ImagePath +"/Images/search.svg");
            search.setGraphic(image);
            category.setGraphic(svgGraphic(ImagePath +"/Images/categorylist.svg"));
            history.setGraphic(svgGraphic(ImagePath +"/Images/clock.svg"));
            refresh.setGraphic(svgGraphic(ImagePath +"/Images/refresh.svg"));

        } catch (FileNotFoundException e) {
            System.err.println("Can not find Button image");
            e.printStackTrace();
        }
        //time
        time.setText( formatTextTime() );
        interna.start();

        //poster
        ImageView[] posters = new ImageView[3];
        //add focus listener for posters
        posters[0]=poster1;
        posters[1]=poster2;
        posters[2]=poster3;
        for (ImageView p:posters
             ) {
            p.focusedProperty().addListener((o)->{
                {if(p.isFocused())
                    p.setEffect(light);else p.setEffect(null);}
            });
        }
        for (ImageView p:posters
                ) {
            p.hoverProperty().addListener((o)->{
                {if(p.isHover())
                    p.setEffect(light);else p.setEffect(null);}
            });
        }
        //refresh
        refresh.setOnMouseClicked((o)->{changePoster();});
        refresh.setOnKeyPressed((o)->{if(o.getCode().equals(KeyCode.ENTER))changePoster();});
        //combo Box
        languageSwitcher.setOnKeyPressed((o)->{if(o.getCode().equals(KeyCode.ENTER))languageSwitcher.show();});
        languageSwitcher.getItems().addAll("中文", "English");
        languageSwitcher.valueProperty().addListener((o) ->{
            language=languageSwitcher.selectionModelProperty().get().getSelectedItem();
            try {
                Main cur = Main.instances[0];
                cur.changeSceneLanguage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        changePoster();

        search.requestFocus();
        search.requestLayout();

      //theme button
        SlidingSwitch slidingSwitch = new SlidingSwitch();
        slidingSwitch.setFocusTraversable(true);
        toggle.getChildren().add(slidingSwitch);
        slidingSwitch.focusedProperty().addListener(((o)->{
            if (slidingSwitch.isFocused())
                slidingSwitch.setEffect(light);else slidingSwitch.setEffect(null);
        }));

        slidingSwitch.setOnMouseClicked((o)->{
            try {
                changeCSS(slidingSwitch);
                slidingSwitch.toggleStatus();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });
        slidingSwitch.setOnKeyPressed((o)->{
            if(o.getCode().equals(KeyCode.ENTER)){
                try {
                    changeCSS(slidingSwitch);
                    slidingSwitch.toggleStatus();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void changeCSS(SlidingSwitch slidingSwitch) throws MalformedURLException {
        if (slidingSwitch.isOn()) {
            Main.instances[0].getMainScene().getStylesheets().remove(0);
            Main.instances[0].getMainScene().getStylesheets().add(new File("CSS").listFiles()[1].toURI().toURL().toExternalForm());

        } else {
            Main.instances[0].getMainScene().getStylesheets().remove(0);
            Main.instances[0].getMainScene().getStylesheets().add(new File("CSS").listFiles()[0].toURI().toURL().toExternalForm());

        }
    }

    private  static  Group svgGraphic(String SourcePath) throws FileNotFoundException {
        InputStream svgFile = new FileInputStream(SourcePath);

        SvgLoader loader = new SvgLoader();
        Group svgImage = loader.loadSvg(svgFile);
        // Scale the image and wrap it in a Group to make the button
        // properly scale to the size of the image
        svgImage.setScaleX(0.09);
        svgImage.setScaleY(0.09);
        Group image = new Group(svgImage);
        image.setAutoSizeChildren(true);
        return  image;
    }

    private String formatTextTime()
    {
        time_system = new GregorianCalendar();
        String da =  time_system.getTime().toString();
        String time = da.split(" ")[3];

        return time.substring(0,5);
    }


    @Override
    public void run() {
        while( Thread.currentThread() == interna && !stop )
        {
            try
            {
                Thread.sleep( 1000 );
                time.setText( formatTextTime() );
            }
            catch( InterruptedException e )
            {

            }
        }
    }

    public  static void stop() {
        stop= true;
        interna = null;
    }

    private void changePoster() {
       int length = poster.list().length;
        Image i1 = new Image (openImageStream(poster.listFiles()[count%length].getAbsolutePath()));
        Image i2 = new Image (openImageStream(poster.listFiles()[(count+1)%length].getAbsolutePath()));
        Image i3 = new Image(openImageStream(poster.listFiles()[(count+2)%length].getAbsolutePath()));
        poster1.setImage(i1);
        poster2.setImage(i2);
        poster3.setImage(i3);
        count+=3;
        count=count%length;
    }

    public  String getLanguage() {
        return language;
    }

}

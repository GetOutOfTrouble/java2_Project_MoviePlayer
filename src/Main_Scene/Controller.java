package Main_Scene;

import History.PosterCategory;
import Scraping.InformationGetter;
import Show_Scene.ShowSceneController;
import SwitchButton.SlidingSwitch;
import afester.javafx.svg.SvgLoader;
import category.CategoryController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable, Runnable {
    /**
     * thread of time refresh
     */
    private static Thread interna;
    /**
     * when stage closed, stop is true
     */
    private static boolean stop;
    /**
     * link the file of posters
     */
    private File poster;
    /**
     * the number of  total posters
     */
    private int count;
    /**
     * is night mode
     * determine css
     */
    private boolean isNight;
    /**
     * boarder pane
     */
    @FXML
    private BorderPane bp;
    /**
     * sliding switch
     */
    @FXML
    private HBox toggle;
    /**
     * search button
     */
    @FXML
    private Button search;
    /**
     * category button
     */
    @FXML
    private Button category;
    /**
     * history button
     */
    @FXML
    private Button history;
    /**
     * refresh button
     */
    @FXML
    private Button refresh;
    /**
     * current time
     */
    @FXML
    private Text time;
    /**
     * showing poster 1
     */
    @FXML
    private ImageView poster1;
    /**
     * showing poster 2
     */
    @FXML
    private ImageView poster2;
    /**
     * showing poster 3
     */
    @FXML
    private ImageView poster3;
    /**
     * combo box of language
     */
    @FXML
    private ComboBox<String>languageSwitcher;
    /**
     * effect of focused image
     */
    private Lighting light;
    /**
     * current time
     */
    private Calendar time_system;
    /**
     * current language
     */
    private  String language ;

    /**
     * constructor
     */
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

    /**
     * load svg to parent mode
     *
     * @param SourcePath svg path
     * @return group containing the svg
     * @throws FileNotFoundException if svg did not exist
     */
    private static Group svgGraphic(String SourcePath) throws FileNotFoundException {
        InputStream svgFile = new FileInputStream(SourcePath);

        SvgLoader loader = new SvgLoader();
        Group svgImage = loader.loadSvg(svgFile);
        // Scale the image and wrap it in a Group to make the button
        // properly scale to the size of the image
        svgImage.setScaleX(0.09);
        svgImage.setScaleY(0.09);
        Group image = new Group(svgImage);
        image.setAutoSizeChildren(true);
        return image;
    }

    /**
     * when stage closed stop
     */
    public static void stop() {
        stop = true;
        interna = null;
    }

    /**
     * initialize the scene
     *
     * @param location  FXML location
     * @param resources language resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        refresh.setOnMouseClicked((o) -> {
            try {
                changePoster();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });
        refresh.setOnKeyPressed((o) -> {
            if (o.getCode().equals(KeyCode.ENTER)) {
                try {
                    changePoster();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });


        // combo Box
        languageSwitcher.setOnKeyPressed((o) -> {
            if (o.getCode().equals(KeyCode.ENTER)) languageSwitcher.show();
            if (languageSwitcher.isShowing() && o.getCode().equals(KeyCode.SPACE)) {
                languageSwitcher.getSelectionModel().selectNext();
            }
            if (o.getCode().equals(KeyCode.UP)) {
                languageSwitcher.getSelectionModel().selectFirst();
            }

            if (o.getCode().equals(KeyCode.DOWN)) {
                languageSwitcher.getSelectionModel().selectLast();

            }
        });
        languageSwitcher.getItems().add("中文");
        languageSwitcher.getItems().add("English");
        languageSwitcher.getSelectionModel().selectedItemProperty().addListener((o, old, newv) -> {
            language = newv;
            try {
                Main cur = Main.instances[0];
                cur.changeSceneLanguage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        try {
            changePoster();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        //Switch button
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
        //button
        try {
            String ImagePath = new File("").getAbsolutePath();
            Group image = svgGraphic(ImagePath + "/Images/search.svg");
            search.setGraphic(image);
            category.setGraphic(svgGraphic(ImagePath + "/Images/categorylist.svg"));
            history.setGraphic(svgGraphic(ImagePath + "/Images/clock.svg"));
            refresh.setGraphic(svgGraphic(ImagePath + "/Images/refresh.svg"));

        } catch (FileNotFoundException e) {
            System.err.println("Can not find Button image");
            e.printStackTrace();
        }
        //search scene
        search.setOnKeyPressed((o) -> {
            if (o.getCode().equals(KeyCode.ENTER)) {
                try {
                    openSearchScene(isNight, language);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
        search.setOnMouseClicked((o) -> {
            try {
                openSearchScene(isNight, language);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });
        //category scene
        category.setOnKeyPressed((o) -> {
            if (o.getCode().equals(KeyCode.ENTER)) {
                try {
                    openCategory(isNight, language);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
        category.setOnMouseClicked((o) -> {
            try {
                openCategory(isNight, language);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });
        //history
        history.setOnKeyPressed((o) -> {
            if (o.getCode().equals(KeyCode.ENTER)) {
                try {
                    openHistory(isNight, language);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
        history.setOnMouseClicked((o) -> {
            try {
                openHistory(isNight, language);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });


        for (ImageView a : posters
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

    /**
     * open the show scene
     *
     * @param isNight  current scene is in night mode or not
     * @param language the showing language
     * @param target   the chosen movie
     * @throws MalformedURLException : to url false
     */
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
            if (root != null) {
                root.getStylesheets().add(new File("CSS\\showstage_dark.css").toURI().toURL().toExternalForm());
            } else {
                System.err.println("root is null");
            }
        } else {
            if (root != null) {
                root.getStylesheets().add(new File("CSS\\showstage.css").toURI().toURL().toExternalForm());
            } else {
                System.err.println("root is null");
            }

        }
        Scene scene = new Scene(root, 925, 600);
        Main.instances[0].getPrimaryStage().setScene(scene);
    }

    /**
     * open the search scene
     *
     * @param isNight  current scene is in night mode or not
     * @param language the showing language
     * @throws MalformedURLException : to url false
     */
    private void openSearchScene(boolean isNight, String language) throws MalformedURLException {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../Search_Scene/SearchScene.fxml"));
            loader.setResources(InformationGetter.propertyReader(Main.PropertyPATH, "search", language));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (isNight) {
            if (root != null) {
                root.getStylesheets().add(new File("CSS\\search_dark.css").toURI().toURL().toExternalForm());
            } else {
                System.err.println("root is null");
            }
        } else {
            if (root != null) {
                root.getStylesheets().add(new File("CSS\\search.css").toURI().toURL().toExternalForm());
            } else {
                System.err.println("root is null");
            }
        }
        Scene scene = new Scene(root, 925, 600);
        Main.instances[0].getPrimaryStage().setScene(scene);
    }

    /**
     * open the History scene
     *
     * @param isNight  current scene is in night mode or not
     * @param language the showing language
     * @throws MalformedURLException to url false
     */
    private void openHistory(boolean isNight, String language) throws MalformedURLException {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../History/posterCategory.fxml"));
            History.PosterCategory con = new PosterCategory();
            con.setLanguage(language);
            loader.setController(con);
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (isNight) {
            assert root != null;
            root.getStylesheets().add(new File("CSS\\postershow_dark.css").toURI().toURL().toExternalForm());
        } else {
            assert root != null;
            root.getStylesheets().add(new File("CSS\\postershow.css").toURI().toURL().toExternalForm());
        }
        Scene scene = new Scene(root, 925, 600);
        Main.instances[0].getPrimaryStage().setScene(scene);
    }

    /**
     * open the category scene
     *
     * @param isNight  current scene is in night mode or not
     * @param language the showing language
     * @throws MalformedURLException to url false
     */
    private void openCategory(boolean isNight, String language) throws MalformedURLException {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../category/Category.fxml"));
            CategoryController con = new CategoryController();
            con.setLanguage(language);
            loader.setController(con);
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (isNight) {
            assert root != null;
            root.getStylesheets().add(new File("CSS\\category_dark.css").toURI().toURL().toExternalForm());
        } else {
            assert root != null;
            root.getStylesheets().add(new File("CSS\\category.css").toURI().toURL().toExternalForm());
        }
        Scene scene = new Scene(root, 925, 600);
        Main.instances[0].getPrimaryStage().setScene(scene);
    }

    /**
     * css change : night mode
     *
     * @param slidingSwitch switch of css
     * @throws MalformedURLException to url false
     */
    private void changeCSS(SlidingSwitch slidingSwitch) throws MalformedURLException {
        if (slidingSwitch.isOn()) {
            Main.instances[0].getMainScene().getStylesheets().clear();
            Main.instances[0].getMainScene().getStylesheets().add(new File("CSS\\theme.css").toURI().toURL().toExternalForm());
            isNight = false;
        } else {
            Main.instances[0].getMainScene().getStylesheets().clear();
            Main.instances[0].getMainScene().getStylesheets().add(new File("CSS\\dark_theme.css").toURI().toURL().toExternalForm());
            isNight = true;
        }
    }

    /**
     * format time to the form of  xx:xx<br>
     * example: 15:30
     *
     * @return current time
     */
    private String formatTextTime() {
        time_system = new GregorianCalendar();
        String da =  time_system.getTime().toString();
        String time = da.split(" ")[3];

        return time.substring(0, 5);
    }

    /**
     * time changing
     */
    @Override
    public void run() {
        while( Thread.currentThread() == interna && !stop )
        {
            try
            {
                Thread.sleep( 1000 );
                time.setText(formatTextTime());
            } catch (InterruptedException e) {
                //do nothing
            }
        }
    }

    /**
     * change Poster
     *
     * @throws MalformedURLException if file dose not exist
     */
    private void changePoster() throws MalformedURLException {
        int length = Objects.requireNonNull(poster.list()).length;
        Image i1 = new Image(Objects.requireNonNull(poster.listFiles())[count % length].toURI().toURL().toExternalForm());
        Image i2 = new Image(Objects.requireNonNull(poster.listFiles())[(count + 1) % length].toURI().toURL().toExternalForm());
        Image i3 = new Image(Objects.requireNonNull(poster.listFiles())[(count + 2) % length].toURI().toURL().toExternalForm());
        poster1.setImage(i1);
        poster2.setImage(i2);
        poster3.setImage(i3);
        count += 3;
        count = count % length;
    }

    /**
     * get current language
     *
     * @return language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * set language
     *
     * @param language: current language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * get search button
     *
     * @return search button
     */
    public Button getSearch() {
        return search;
    }

    /**
     * get time
     *
     * @return format of 15:30
     */
    public Text getTime() {
        return time;
    }

    /**
     * get theme
     *
     * @return boolean of isNight mode
     */
    public boolean isNight() {
        return isNight;
    }
}

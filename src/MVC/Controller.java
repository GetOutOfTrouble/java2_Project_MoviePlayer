package MVC;

import afester.javafx.svg.SvgLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private StringProperty LogoMessage ;
    @FXML
    private Button[] standarButtons;
    private  static  Button initailButton(String SourcePath) throws FileNotFoundException {
        InputStream svgFile = new FileInputStream(SourcePath);

        SvgLoader loader = new SvgLoader();
        Group svgImage = loader.loadSvg(svgFile);
        // Scale the image and wrap it in a Group to make the button
        // properly scale to the size of the image
        svgImage.setScaleX(0.1);
        svgImage.setScaleY(0.1);
        Group graphic = new Group(svgImage);
        Button button = new Button();
        button.setGraphic(graphic);
        return  button;
    }
    public String getLogoMessage() {
        return LogoMessage.get();
    }

    public void setLogoMessage(String logoMessage) {
        this.LogoMessage.set(logoMessage);

    }

    public StringProperty logoMessageProperty() {
        return LogoMessage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

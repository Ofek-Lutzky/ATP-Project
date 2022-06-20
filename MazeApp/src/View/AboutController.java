package View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {

    @FXML
    private TextFlow aboutText = new TextFlow();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Text text  = new Text("About\n" +
                "and you need to get out\n" +
                "You must win the POKEMON COMPETITION and get to the Dragon Cup");

        text.setFill(Color.RED);

        text.setFont(Font.font("Lucida Sans Unicode", FontPosture.ITALIC, 20));

        aboutText.getChildren().add(text);
    }
}

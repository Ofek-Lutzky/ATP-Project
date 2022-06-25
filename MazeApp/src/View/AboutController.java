package View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

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

//        text.setFill((0,51,102));
//
//        text.setFont(Font.font("BN Matan", FontPosture.ITALIC, 20));


        Font font = Font.font("BN Matan", FontWeight.BOLD, FontPosture.REGULAR, 32);
        text.setFont(font);
        //Setting the color of the text
        text.setFill(Color.BROWN);
        //Setting the width
        text.setStrokeWidth(2);
        //Setting the stroke color
        text.setStroke(Color.BLUE);

        aboutText.getChildren().add(text);
    }
}

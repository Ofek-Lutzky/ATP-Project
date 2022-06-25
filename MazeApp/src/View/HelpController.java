package View;

import com.sun.scenario.effect.DropShadow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.scene.effect.Effect;

public class HelpController implements Initializable {
    @FXML
    private TextFlow helpText = new TextFlow();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            Text text  = new Text("You got stuck in the maze !\n" +
                    "Now! Your Mission is - to get out\n" +
                    "You must win the POKEMON COMPETITION and get\n"+ "to the Dragon Cup\n\n"+
                    "you can walk with arrows or with the num pad also in a Slants\n"+ "Good Luck!");

//        text.setFill(Color.RED);
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

        helpText.getChildren().add(text);

    }
}

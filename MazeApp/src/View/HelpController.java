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
//            DropShadow ds = new DropShadow();
//            ds.setOffsetY((int) 3.0f);
//            //ds.setColor(Color.color());

            Text text  = new Text("You got stuck in the maze!\n" +
                    "and you need to get out\n" +
                    "You must win the POKEMON COMPETITION and get to the Dragon Cup");

//        text.setY(100);
//        text.setX(20);
        text.setFill(Color.RED);

        text.setFont(Font.font("Lucida Sans Unicode", FontPosture.ITALIC, 20));

//            t.setEffect(ds);
//            t.setCache(true);
//            t.setX(10.0f);
//            t.setY(270.0f);
//            //t.setEffect();
//            t.setFill(Color.RED);
//            t.setFont(Font.font(null, FontWeight.BOLD, 32));

//            t.setFont(Font.loadFont("file:resources/fonts/isadoracyr.ttf", 120));
            helpText.getChildren().add(text);

    }
}

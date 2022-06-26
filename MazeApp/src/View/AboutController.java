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

        Text text  = new Text("About:\n" +
                "Programers: Ofek Lutzky And Noam Choen.\n" +
                "Algorithms: Binary Tree for the generate of the Maze.\n" +
                "Search Algo: BFS, BestFirstSearch, DFS.\n" +
                "Servers: One for generate and one for the solving each get\n               handle thread and there is threadpool that helping manage it all.                  \n" +
                "We Built the maze code nicely with Solid architecture\n and gave allot of thought in each code line.\n"+
                "Design pattern: Decorator design pattern, Strategy pattern\n, Object Adapter ,MVVM (with observer design). \n" +
                "Maybe in the next project Maze App in 3D!\n ");

//        text.setFill((0,51,102));
//
//        text.setFont(Font.font("BN Matan", FontPosture.ITALIC, 20));


        Font font = Font.font("BN Matan", FontWeight.BOLD, FontPosture.REGULAR, 32);
        text.setFont(font);
        //Setting the color of the text
        text.setFill(Color.TURQUOISE);
        //Setting the width
        text.setStrokeWidth(2);
        //Setting the stroke color
        text.setStroke(Color.BLUE);

        aboutText.getChildren().add(text);
    }
}

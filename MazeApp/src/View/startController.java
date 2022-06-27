package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class startController extends Canvas{
    public Scene scene;
    public javafx.scene.control.Button startBtnFirstScene;
    public Stage primaryStage;

    public MyViewController viewController;



    public void setScene(Scene scene) {
        this.scene = scene;
    }
    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void nextController(MyViewController viewController){
        this.viewController = viewController;
    }

    public void ToGame(ActionEvent event) {
        primaryStage.setScene(scene);
        //viewController.setResize(scene);
    }



}

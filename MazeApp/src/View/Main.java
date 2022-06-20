package View;

import Model.IModel;
import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import View.MyViewController;

import javax.swing.text.View;
import java.util.Observer;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/MyView.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Maze App");
        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.show();

        IModel model = new MyModel();
        MyViewModel myViewModel = new MyViewModel(model);
        MyViewController view = fxmlLoader.getController();
        view.setViewModel(myViewModel);
    }


    public static void main(String[] args) {
        launch(args);
    }
}

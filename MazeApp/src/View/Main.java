package View;

import Model.IModel;
import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.util.Optional;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/MyView.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Maze App");
        primaryStage.setScene(new Scene(root, 800, 800));
        MyViewController.setPrimaryStage(primaryStage);

        MyViewController viewController = fxmlLoader.getController();

        primaryStage.show();

        IModel model = new MyModel();
        MyViewModel myViewModel = new MyViewModel(model);
        MyViewController view = fxmlLoader.getController();
        view.setViewModel(myViewModel);

        closeGame(viewController,primaryStage);

    }



    private void closeGame(MyViewController viewController , Stage stage) {

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        public void handle(WindowEvent WE){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are You Sure?");
        ButtonType continueGame = new ButtonType("Continue playing");
        ButtonType closeGame = new ButtonType("Exit Game");
        alert.getButtonTypes().setAll(continueGame, closeGame);
        Optional<ButtonType> chosed = alert.showAndWait();

        if(chosed.get() == closeGame){
            viewController.stopServers();
            System.exit(0);
            alert.close();

        }
        else{
            WE.consume();
        }
    }});}


    public static void main(String[] args) {
        launch(args);
    }
}

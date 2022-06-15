package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameSceneController {

    // needed for the scene switiching
    private Stage stage;
    private Scene scene;
    private Parent root;


    /**
     *
     * @param event -ActionEvent
     * @throws IOException - exception
     * the function take the root of the FXML loader and change it to the scene specified inside the load function
     * this func is for the open scene (scene 1)
     */
    public void switchToOpenScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../View/MyView.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void rtnHome(ActionEvent event) throws IOException{
        this.switchToOpenScene(event);
    }
}

package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

//this will be the class controller of the main open scene
public class MyViewController implements IView, Initializable, Observer {

    // needed for the scene switiching
    public Stage stage;
    public Scene scene;
    public Parent root;

    public MyViewModel viewModel;

    //private Maze maze;

    @FXML
    public javafx.scene.control.TextField rowText;
    @FXML
    public javafx.scene.control.TextField colText;
    @FXML
    public MazeDisplayer mazeDisplayer;



    // the initialize can get to the fxml design in running while regular constructor not
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addObserver(this);

    }

    @Override
    public void mazeGenerate() { // todo input check
        try {
            int rows = Integer.parseInt(rowText.getText());
            int cols = Integer.parseInt(colText.getText());
//        if (rows <= 1 || cols <= 1 || rows > 1000 || cols > 1000) {
//            Alert alertGenerate = new Alert(Alert.AlertType.WARNING);
//            alertGenerate.setContentText("Invalid Input");
//            alertGenerate.show();
//        }
            viewModel.generateMaze(rows,cols);
        }
        catch (Exception e)
        {
//            Alert alertGenerate = new Alert(Alert.AlertType.WARNING);
//            alertGenerate.setContentText("Invalid Input");
//            alertGenerate.show();
        }

    }

    @Override
    public void mazeSolve() {
//        if(!showSolution)
//        {
//            try {
//                viewModel.solveMaze();
//                solutionButton.setText("Hide Solution");
//                showSolution = true;
//                viewModel.setShowSolution(true);
//
//            }
//            catch (Exception e)
//            {
//                Alert alertSolve = new Alert(Alert.AlertType.WARNING);
//                alertSolve.setContentText("You need to generate a maze first");
//                alertSolve.show();
//            }
//        }
//        else {
//            this.viewModel.removeSolution();
//            solutionButton.setText("Show Solution");
//            showSolution = false;
//            viewModel.setShowSolution(false);
//
//        }
    }

    public void mazeGenerated()
    {
        mazeDisplayer.drawMaze(viewModel.getMaze());
    }

    @Override
    public void mazeLoad() {
        mazeDisplayer.drawMaze(viewModel.getMaze());

    }

    @Override
    public void playerMoved() {

    }

    @Override
    public void update(Observable o, Object arg) {
        String action = (String) arg;
        String change = (String) arg;
        switch (change){
            case "mazeGenerated" -> mazeGenerated();
            //case "player moved" -> playerMoved();
            //case "mazeSolved" -> mazeSolved();
            //case "maze loaded" -> mazeLoaded();
            //case "hide solution" -> hideSolution();
            default -> System.out.println("Not implemented change: " + change);
        }
//        if (viewModel.gameOver()) {
//            try {
//                playWinningMusic();
//                openVideo();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

    }







    /**
     *
     * @param event -ActionEvent
     * @throws IOException - exception
     * the function take the root of the FXML loader and change it to the scene specified inside the load function
     * this func is for the game scene (scene 2)
     */
    public void switchToGameScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../View/GameScene.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    // the Btns functions of the start Scean
    public void startGame(ActionEvent actionEvent) throws IOException {
        this.switchToGameScene(actionEvent);
        this.mazeGenerate();
        mazeDisplayer.drawMaze(viewModel.getMaze());

    }

    public void showInstrucion(ActionEvent actionEvent) {
    }

    public void showRanking(ActionEvent actionEvent) {
    }
}

package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

//this will be the class controller of the main open scene
public class MyViewController implements IView, Observer {

    // needed for the scene switiching
//    public Stage stage;
//    public Scene scene;
//    public Parent root;

    public MyViewModel viewModel;
    boolean solutionShow = false;

    //private Maze maze;

    @FXML
    public javafx.scene.control.TextField rowText;
    @FXML
    public javafx.scene.control.TextField colText;

    @FXML
    public MazeDisplayer mazeDisplayer;
    @FXML
    public Button playBtn;

//    playBtn.setGraphic(new ImageView("playBtn.png"));


     //the initialize can get to the fxml design in running while regular constructor not
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//
//    }


    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addObserver(this);

    }

    @Override
    public void mazeGenerate() { // todo input check
        try {
            int rows = Integer.valueOf(rowText.getText());
            int cols = Integer.valueOf(colText.getText());

            if (rows <= 1 ||rows > 1000||  cols <= 1 || cols > 1000) {
                Alert alertGenerate = new Alert(Alert.AlertType.WARNING);
                alertGenerate.setContentText("Input Out Of Range!");
                alertGenerate.show();
            }
            else{
            viewModel.generateMaze(rows,cols);}
        }
        catch (Exception e)
        {
            Alert alertGenerate = new Alert(Alert.AlertType.WARNING);

            alertGenerate.setContentText("Invalid Input");

            alertGenerate.show();
        }

    }

    @Override
    public void mazeSolve() {
        if(!solutionShow)
        {
            try {
                viewModel.solveMaze();
//                solutionButton.setText("hideSolution");
//                showSolution = true;
                viewModel.setShowSolution(true);

            }
            catch (Exception e)
            {
                Alert alertSolve = new Alert(Alert.AlertType.WARNING);
                alertSolve.setContentText("You need to generate a maze first");
                alertSolve.show();
            }
        }
        else {
            this.viewModel.removeSolution();
//            solutionButton.setText("Show Solution");
//            showSolution = false;
            viewModel.setShowSolution(false);

        }
    }

    public void mazeGenerated()
    {
        mazeDisplayer.drawMaze(viewModel.getMaze());
    }

    public void mazeSolved()
    {
        mazeDisplayer.setSolution(viewModel.getSolution());
    }

    @Override
    public void mazeLoad() {
        mazeDisplayer.drawMaze(viewModel.getMaze());

    }


    @Override
    public void update(Observable o, Object arg) {
        String action = (String) arg;
        switch (action){
            case "mazeGenerated" -> mazeGenerated();
            case "playerMoved" -> playerMoved();
            case "mazeSolved" -> mazeSolved();
//            case "mazeLoaded" -> mazeLoaded();
//            case "hide solution" -> hideSolution();
            default -> System.out.println("Not implemented change: " + action);
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







//    /**
//     *
//     * @param event -ActionEvent
//     * @throws IOException - exception
//     * the function take the root of the FXML loader and change it to the scene specified inside the load function
//     * this func is for the game scene (scene 2)
//     * and pass data object view model, so, in the sc2 it can draw it
//     *
//     * 1.save the loader to the next scene
//     * 2.save his controller
//     * 3. call the method of the initData with the viewModel .so, it can draw the maze ot it side
//     */
//    public void switchToGameSceneAndPassData(ActionEvent event) throws IOException {
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("../View/GameScene.fxml"));
//        Parent viewParent = loader.load();
//
//        Scene viewScene = new Scene(viewParent);
//
//        //access controller and call method
//        GameSceneController controller = loader.getController();
//        controller.initData(viewModel);
//
//
//
//        //get stage info
//        //root = FXMLLoader.load(getClass().getResource("../View/GameScene.fxml"));
//        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
////        scene = new Scene(root);
//        window.setScene(viewScene);
//        window.show();
//    }


    // the Btns functions of the start Scean
    public void startGame(ActionEvent actionEvent) throws IOException {
        this.mazeGenerate();
        //this.switchToGameSceneAndPassData(actionEvent);
        mazeDisplayer.drawMaze(viewModel.getMaze());

    }

    public void showInstrucion(ActionEvent actionEvent) {
    }

    public void showRanking(ActionEvent actionEvent) {
    }



    @Override
    public void playerMoved() {
        System.out.println("ViewController playerMoved: " + viewModel.getCharacterRow() +" "+ viewModel.getCharacterCol());

        mazeDisplayer.setPlayerPosition(viewModel.getCharacterRow(), viewModel.getCharacterCol());
        mazeDisplayer.setSolution(viewModel.getSolution());


    }

    public void mouseClicked(MouseEvent mouseEvent)
    {
        mazeDisplayer.requestFocus();
    }

    public void keyPressed(javafx.scene.input.KeyEvent keyEvent) {
        System.out.println("Key Code: " + keyEvent.getCode().toString());
        viewModel.moveCharacter(keyEvent.getCode());
        keyEvent.consume();
    }
}

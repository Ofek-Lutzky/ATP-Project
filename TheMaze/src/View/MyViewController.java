package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MyViewController implements Initializable,Observer {
    private MyViewModel viewModel;
    @FXML
    public TextField textField_mazeRows;
    @FXML
    public TextField textField_mazeColumns;
    @FXML
    public MazeDisplayer mazeDisplayer;
    @FXML
    public Label lbl_player_row;
    @FXML
    public Label lbl_player_column;
    StringProperty update_player_position_row = new SimpleStringProperty();
    StringProperty update_player_position_col = new SimpleStringProperty();
    private Maze maze;


    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addObserver(this);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbl_player_row.textProperty().bind(update_player_position_row);
        lbl_player_column.textProperty().bind(update_player_position_col);
    }

    //public void setViewModel(MyViewModel viewModel) {
//        this.viewModel = viewModel;
//    }


    public String get_update_player_position_row() {
        return update_player_position_row.get();
    }

    public void set_update_player_position_row(String update_player_position_row) {
        this.update_player_position_row.set(update_player_position_row);
    }

    public String get_update_player_position_col() {
        return update_player_position_col.get();
    }

    public void set_update_player_position_col(String update_player_position_col) {
        this.update_player_position_col.set(update_player_position_col);
    }



    public void generateMaze()
    {
        try {
            int rows = Integer.valueOf(textField_mazeRows.getText());
            int cols = Integer.valueOf(textField_mazeColumns.getText());

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

    public void solveMaze()
    {
        viewModel.solveMaze();
        /////////////////////////////////////////////////
//        if(!solutionShow)
//        {
//            try {
//                viewModel.solveMaze();
////                solutionButton.setText("Hide Solution");
////                showSolution = true;
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
////            solutionButton.setText("Show Solution");
////            showSolution = false;
//            viewModel.setShowSolution(false);
//
//        }

    }


    public void showAlert(String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);;
        alert.show();
    }


    public void keyPressed(KeyEvent keyEvent) {

        viewModel.moveCharacter(keyEvent);
        keyEvent.consume();

    }

    public void mouseClicked(MouseEvent mouseEvent) {
        mazeDisplayer.requestFocus();
    }


    @Override
    public void update(Observable o, Object arg) {
//        if(o instanceof MyViewModel)
//        {
//            if(maze == null)//generateMaze
//            {
//                this.maze = viewModel.getMaze();
//                drawMaze();
//            }
//            else {
//                Maze maze = viewModel.getMaze();
//
//                if (maze == this.maze)//Not generateMaze
//                {
//                    int rowChar = mazeDisplayer.getRow_player();
//                    int colChar = mazeDisplayer.getCol_player();
//                    int rowFromViewModel = viewModel.getRowChar();
//                    int colFromViewModel = viewModel.getColChar();
//
//                    if(rowFromViewModel == rowChar && colFromViewModel == colChar)//Solve Maze
//                    {
//                        viewModel.getSolution();
//                        showAlert("Solving Maze ... ");
//                    }
//                    else//Update location
//                    {
//                        set_update_player_position_row(rowFromViewModel + "");
//                        set_update_player_position_col(colFromViewModel + "");
//                        this.mazeDisplayer.set_player_position(rowFromViewModel,colFromViewModel);
//                    }
//
//
//                }
//                else//GenerateMaze
//                {
//                    this.maze = maze;
//                    drawMaze();
//                }
//            }
//        }
        String action = (String) arg;
        switch (action){
            case "mazeGenerated" -> generateMaze();
//            case "playerMoved" -> playerMoved();
//            case "mazeSolved" -> mazeSolved();
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

    public void drawMaze()
    {
        mazeDisplayer.drawMaze(maze);
    }
}



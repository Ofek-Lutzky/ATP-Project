package View;

import ViewModel.MyViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

//this will be the class controller of the main open scene
public class MyViewController implements IView ,Observer,Initializable {

    // needed for the scene switiching
//    public Stage stage;
//    public Scene scene;
//    public Parent root;

    public MyViewModel viewModel;
    boolean solutionShow = false;
    boolean mouseDrag = false;

    //private Maze maze;

    @FXML
    public javafx.scene.control.TextField rowText;
    @FXML
    public javafx.scene.control.TextField colText;

    @FXML
    public MazeDisplayer mazeDisplayer;
    @FXML
    public Button playBtn;
    @FXML
    public Button solveMazeBtn;

    @FXML
    public javafx.scene.control.Label lbl_player_row;
    @FXML
    public javafx.scene.control.Label lbl_player_column;

    @FXML
    public AnchorPane boardPane;
    @FXML
    public ScrollPane scrollPane;

    @FXML
    public Button dragMouse;


    String gameMusic = new File("./src/resources/Music/GameMusic.mp3").toURI().toString();
    MediaPlayer gameMusicPlayer = new MediaPlayer(new Media(gameMusic));
    String winMusic = new File("./src/resources/Music/WinMusic.mp3").toURI().toString();
    MediaPlayer endMusicPlayer = new MediaPlayer(new Media(winMusic));

    StringProperty update_player_position_row = new SimpleStringProperty();
    StringProperty update_player_position_col = new SimpleStringProperty();

//

     //the initialize can get to the fxml design in running while regular constructor not
     @Override
     public void initialize(URL url, ResourceBundle resourceBundle) {
         lbl_player_row.textProperty().bind(update_player_position_row);
         lbl_player_column.textProperty().bind(update_player_position_col);
     }



    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addObserver(this);

    }

    public String get_update_player_position_row() {
        return update_player_position_row.get();
    }

    public void set_update_player_position_row(String update_player_position_row) {
        this.update_player_position_row.set(update_player_position_row + "");
    }

    public String get_update_player_position_col() {
        return update_player_position_col.get();
    }

    public void set_update_player_position_col(String update_player_position_col) {
        this.update_player_position_col.set(update_player_position_col + "");
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
                solutionShow = true;
                viewModel.setShowSolution(true);
                solveMazeBtn.setText("Hide Solution");

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
            solutionShow = false;
            viewModel.setShowSolution(false);
            solveMazeBtn.setText("Solve Maze");

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

    private void removeSolution()
    {
        viewModel.setShowSolution(false);
        mazeDisplayer.setShowSolution(false);
    }



    @Override
    public void update(Observable o, Object arg) {
        String action = (String) arg;

        switch (action){
            case "mazeGenerated" -> mazeGenerated();
            case "playerMoved" -> playerMoved();
            case "mazeSolved" -> mazeSolved();
            case "mazeLoaded" -> LoadedMaze();
            case "removeSolution" -> removeSolution();
            default -> System.out.println("Not implemented change: " + action);
        }
        if (viewModel.gameOver()) {
            try {
                winMusic();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            closeGame();
        }

    }

    private void LoadedMaze() {
         mazeDisplayer.drawMaze(viewModel.getMaze());
    }

    private void startMusic() {

        viewModel.setGameOver(false);
        endMusicPlayer.stop();
        gameMusicPlayer.play();
    }

    private void winMusic() throws InterruptedException {
        gameMusicPlayer.stop();
        endMusicPlayer.play();
        Thread.sleep(4000);
    }


    private void closeGame() {
        viewModel.setGameOver(true);
        stopServers();
        System.exit(0); // todo ask the client if he relly want to finish


    }


    public void stopServers() {
        System.out.println( "ViewConroller "+"stop Server");

        viewModel.stopServers();
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
         startMusic();
        this.mazeGenerate();
        //this.switchToGameSceneAndPassData(actionEvent);
        mazeDisplayer.drawMaze(viewModel.getMaze());

    }

    public void drawMaze(){
        mazeDisplayer.drawMaze(viewModel.getMaze());
    }


    public void showRanking(ActionEvent actionEvent) {
    }

//    public void newMaze(){
//          mazeGenerate();
//    }

    public void saveMaze(ActionEvent event){
         if (!viewModel.saveFile()){
             Alert alertGenerate = new Alert(Alert.AlertType.INFORMATION);
             alertGenerate.setContentText("The  save has Failed!");
             alertGenerate.show();
         }
         else{
             Alert alertGenerate = new Alert(Alert.AlertType.INFORMATION);
             alertGenerate.setContentText("The maze has Saved!");
             alertGenerate.show();
         }
    }

    public void loadMaze(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Filter the files that in the format i search for in the folder the mazes saved", "*.txt" ));
        File allMazesDirectory = new File("./AllMazes");
        fileChooser.setInitialDirectory(allMazesDirectory);

    //the if check if it loaded the file according to the absolute path.
    // if not we will inform our client with information alert
        if (!viewModel.loadFile(allMazesDirectory.getAbsolutePath() + '\\' +fileChooser.showOpenDialog(null).getName())){
            Alert alertGenerate = new Alert(Alert.AlertType.INFORMATION);
            alertGenerate.setContentText("The  load has Failed!");
            alertGenerate.show();
        }
        else{
            Alert alertGenerate = new Alert(Alert.AlertType.INFORMATION);
            alertGenerate.setContentText("The maze has Load!");
            alertGenerate.show();
        }
    }



    @Override
    public void playerMoved() {
        System.out.println("ViewController playerMoved: " + viewModel.getCharacterRow() +" "+ viewModel.getCharacterCol());

        mazeDisplayer.setPlayerPosition(viewModel.getCharacterRow(), viewModel.getCharacterCol());
        set_update_player_position_row(viewModel.getCharacterRow() + "");
        set_update_player_position_col(viewModel.getCharacterCol() + "");
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

    public void solveMazeFromPlayerPlace(ActionEvent event) {
    }

    public void showInstrucion(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Help.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Instruction");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public void showAbout(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("About.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("About");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }



    private double zoomFuctor = 1;

    public void zoomWithScroller(ScrollEvent scrollEvent) {
        if (scrollEvent.isControlDown()) {

            double deltaY = scrollEvent.getDeltaY();

            if (deltaY > 0)
                zoomFuctor += 0.1;
            else if (deltaY < 0)
                zoomFuctor -= 0.1;

            zoomFuctor = Math.max(zoomFuctor, 1);
            zoomFuctor = Math.min(zoomFuctor, 5);
            boardPane.setScaleX(zoomFuctor);
            boardPane.setScaleY(zoomFuctor);


            if (zoomFuctor == 1) {
                this.scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                this.scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            }
            else {
                this.scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                this.scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            }


            Group scrollZoomG = new Group();
            Group scrollContentG = new Group();

            scrollContentG.getChildren().add(scrollZoomG);
            scrollZoomG.getChildren().add(boardPane);

            scrollPane.setContent(scrollContentG);

            Scale scaleTransform = new Scale(zoomFuctor, zoomFuctor, 0, 0);
            scrollZoomG.getTransforms().add(scaleTransform);
        }
        scrollEvent.consume();
    }


    public void mouseDrag(ActionEvent event) {
        if(dragMouse.getText().equals("Move With Mouse"))
        {
            dragMouse.setText("Stop Mouse Drag");


        }
        else {
            dragMouse.setText("Move With Mouse");

        }
    }
}

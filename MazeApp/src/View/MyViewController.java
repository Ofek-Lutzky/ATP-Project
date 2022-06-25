package View;

import ViewModel.MyViewModel;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaView;
import javafx.scene.transform.Scale;
import javafx.stage.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

//this will be the class controller of the main open scene
public class MyViewController implements IView ,Observer,Initializable, Serializable {

    // needed for the scene switiching
//    public Stage stage;
//    public Scene scene;
//    public Parent root;

    public MyViewModel viewModel;
    boolean solutionShow = false;
    boolean mouseDrag = false;

    private boolean firstTimeBoot = true;
    public static Stage primaryStage;//todo maybe change to private and make an object in the main class


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
    File videoFileStart = new File("./src/resources/Videos/videoplayback.mp4");
    Media mediaStart;
    {
        try {
            mediaStart = new Media(videoFileStart.toURI().toURL().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    MediaPlayer videoPlayerStart = new MediaPlayer(mediaStart);
    MediaView videoViewStart = new MediaView(videoPlayerStart);

    File videoFileEnd = new File("./src/resources/Videos/rickRoll.mp4");
    Media mediaEnd;

    {
        try {
            mediaEnd = new Media(videoFileEnd.toURI().toURL().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    MediaPlayer videoPlayerEnd = new MediaPlayer(mediaEnd);
    MediaView videoViewEnd = new MediaView(videoPlayerEnd);



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
    public static void setPrimaryStage(Stage stage)
    {
        primaryStage = stage;
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
            case "mazeGenerated" ->
                    mazeGenerated();
            case "playerMoved" ->
                    playerMoved();
            case "mazeSolved" ->
                    mazeSolved();
            case "mazeLoaded" ->
                    LoadedMaze();
            case "removeSolution" ->
                    removeSolution();
            default ->
                    System.out.println("There is no such thing pls change action");
        }
        if (viewModel.gameOver()) {
            try {
                winMusic();
                openVideoBothStartAndEnd("End");
            } catch (Exception e) {
                e.printStackTrace();
                closeGame();

            }
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



    public void openVideoBothStartAndEnd(String chooseTheVideo) throws IOException, InterruptedException {

        if (chooseTheVideo.equals("Start")){
            videoViewStart.setMediaPlayer(videoPlayerStart);
            videoViewStart.setSmooth(true);
            videoPlayerStart.setAutoPlay(true);

            //Group root = new Group();
            AnchorPane root = new AnchorPane();
            root.getChildren().add(videoViewStart);

            Stage stage = new Stage();
            Scene scene = new Scene(root, 800, 800);

            videoViewStart.fitWidthProperty().bind(root.widthProperty());
            videoViewStart.fitHeightProperty().bind(root.heightProperty());


            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);

            Timeline tm = new Timeline(new KeyFrame(Duration.millis(4000),new KeyValue(videoViewStart.opacityProperty(),  0.0)));
            tm.setDelay(Duration.millis(4010));
            tm.play();

            stage.show();

            //stage.close();

            gameMusicPlayer.setMute(false);


        }
        else if(chooseTheVideo.equals("End")){

            // videoPlayer.setAutoPlay(true);
            videoPlayerEnd.play();
            gameMusicPlayer.setMute(true);
            videoPlayerEnd.setOnEndOfMedia(() -> videoPlayerEnd.seek(Duration.ZERO));

            StackPane root = new StackPane();
            root.getChildren().add(videoViewEnd);

            videoViewEnd.fitWidthProperty().bind(root.widthProperty());
            videoViewEnd.fitHeightProperty().bind(root.heightProperty());

            Scene scene = new Scene(root, 500, 500);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.setTitle("You Are the Winner");
            stage.show();
            primaryStage.hide();
            SetEndStageCloseEvent(stage);

        }
    }

    private void SetEndStageCloseEvent(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("You Won\n" + "what is your choice");

                ButtonType newGame = new ButtonType("Start New Game");
                ButtonType closeGame = new ButtonType("Exit Game");

                alert.getButtonTypes().setAll(newGame, closeGame);

                Optional<ButtonType> chose = alert.showAndWait();

                if (chose.get() == newGame)
                {
                    primaryStage.show();
                    videoPlayerEnd.pause();
                    viewModel.setGameOver(false);

                    try {
                        startGame(new ActionEvent());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    stage.close();

                }
                else if (chose.get() == closeGame) {
                    closeGame();
                }

            }
        });
    }

    private void closeGame() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are You Sure?");
        ButtonType continueGame = new ButtonType("Continue playing");
        ButtonType closeGame = new ButtonType("Exit Game");
        alert.getButtonTypes().setAll(continueGame, closeGame);
        Optional<ButtonType> chosed = alert.showAndWait();

        if(chosed.get() == closeGame){
            viewModel.setGameOver(true);
            stopServers();
            System.exit(0);
            alert.close();

        }
        else{
            alert.close();
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("You have made a good choice\n"+
                    "I Know were do you live!");
            alert.getButtonTypes().setAll(continueGame, closeGame);

            //alert2.close();
        }
    }


    public void stopServers() {
        System.out.println( "ViewConroller "+" stop Server");

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
    public void startGame(ActionEvent actionEvent) throws IOException, InterruptedException {
        if (firstTimeBoot) {
            openVideoBothStartAndEnd("Start");
            firstTimeBoot = false;
        }
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
            mouseDrag = true;
            dragMouse.setText("Stop Mouse Drag");


        }
        else {
            mouseDrag = false;
            dragMouse.setText("Move With Mouse");

        }
    }

    public void onMouseDrag(MouseEvent mouseEvent) {
        if(mouseDrag && !(viewModel.getMaze() == null)) {

            int maximumSize = Math.max(viewModel.getMaze().getMap()[0].length, viewModel.getMaze().getMap().length);

            double mouseX = helperMouseDragged(maximumSize,mazeDisplayer.getHeight(),
                    viewModel.getMaze().getMap().length ,mouseEvent.getX(),mazeDisplayer.getWidth() / maximumSize);

            double mouseY = helperMouseDragged(maximumSize,mazeDisplayer.getWidth(),
                    viewModel.getMaze().getMap()[0].length,mouseEvent.getY(),mazeDisplayer.getHeight() / maximumSize);

            if (mouseY < viewModel.getCharacterRow() &&  mouseX == viewModel.getCharacterCol() )
                viewModel.moveCharacter(KeyCode.UP);

            else if (mouseY == viewModel.getCharacterRow() && mouseX > viewModel.getCharacterCol() )
                viewModel.moveCharacter(KeyCode.RIGHT);

            else if ( mouseY == viewModel.getCharacterRow() && mouseX < viewModel.getCharacterCol() )
                viewModel.moveCharacter(KeyCode.LEFT);

            else if (mouseX == viewModel.getCharacterCol() && mouseY > viewModel.getCharacterRow()  )
                viewModel.moveCharacter(KeyCode.DOWN);

        }
    }

    private  double helperMouseDragged(int maxsize, double canvasSize, int mazeSize,double mouseEvent,double temp){
        double cellSize=canvasSize/maxsize;
        double start = (canvasSize / 2 - (cellSize * mazeSize / 2)) / cellSize;
        double mouse = (int) ((mouseEvent) / (temp) - start);
        return mouse;
    }

    public void newMaze(ActionEvent event) throws IOException, InterruptedException {
        if (firstTimeBoot) {
            openVideoBothStartAndEnd("Start");
            firstTimeBoot = false;
        }
        viewModel.generateMaze(20,20);
        mazeDisplayer.drawMaze(viewModel.getMaze());
    }
}

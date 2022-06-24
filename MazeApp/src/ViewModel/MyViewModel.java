package ViewModel;

import Model.IModel;
import Model.MovementDirection;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {
    private IModel model;
    public Stage primaryStage;

   // private Maze maze;
//    private int charRow;
//    private int charCol;

    public MyViewModel(IModel model) {
        this.model = model;
        //this.maze = null;
        this.model.assignObserver(this);//listen the Model for it's changes so we can update the view and notify it too.
    }

    public Maze getMaze() {
        return this.model.getMaze();
    }

    public int getCharacterRow() {
        System.out.println("MyviewModel  getCharacterRow: "+ model.getCharacterRow());
        return model.getCharacterRow();
    }

    public int getCharacterCol() {
        System.out.println("MyviewModel  getCharacterCol: "+ model.getCharacterCol());
        return model.getCharacterCol();
    }

    public Solution getSolution()
    {
        return model.getSolution();
    }

    //just update the view for the changes or that he got what it needed from the model
    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }

    public void generateMaze(int row,int col)
    {
        this.model.generateMaze(row,col);
    }

    /**
     *
     * @param keyCode - key board code
     * the function will convert the key clicked to the model cahnge movement of the player
     */
    public void moveCharacter(KeyCode keyCode)
    {
        MovementDirection direction;

        /////////////
        /*
            direction = 8 -> Up
            direction = 2 -> Down
            direction = 4 -> Left
            direction = 6 -> Right

            direction = 9 -> Up Right -> UR
            direction = 7 -> Up Left -> UL
            direction = 3 -> Down Right -> DR
            direction = 1 -> Down Left -> DL
         */

        switch(keyCode)
        {
//            case 8:
            case UP:
            case DIGIT8:
                direction = MovementDirection.UP;
                break;
//            case 2:
            case DOWN:
            case DIGIT2:
                direction = MovementDirection.DOWN;
                break;
//            case 6:
            case RIGHT:
            case DIGIT6:
                direction = MovementDirection.RIGHT;
                break;
//            case 4:
            case LEFT:
            case DIGIT4:
                direction = MovementDirection.LEFT;
                break;

            //Slants
//            case 9:
            case DIGIT9:
                direction = MovementDirection.UR;
                break;
//            case 7:
            case DIGIT7:
                direction = MovementDirection.UL;
                break;
//            case 3:
            case DIGIT3:
                direction = MovementDirection.DR;
                break;
            case DIGIT1:
           // case 1:
                direction = MovementDirection.DL;
                break;
            default:
                // no move
                return;

        }

        model.updateCharacterLocation(direction);
    }


    public void solveMaze()
    {
        model.solveMaze();
    }

    public boolean gameOver(){
        return model.gameOver();
    }
    public void setGameOver(boolean finishGame)
    {
        model.setGameOver(finishGame);
    }

    public void removeSolution() {
        model.solutionRestart();
    }

    public void setShowSolution(boolean showSol) {
        model.setShowSolution(showSol);
    }

    public void stopServers() {
        model.stopServers();
    }

    public boolean saveFile() {
        return model.saveFile();
    }

    public boolean loadFile(String name) {
        return model.loadFile(name);
    }

    public static void setPrimaryStage(Stage primaryStage) {

        primaryStage = primaryStage;
    }


}

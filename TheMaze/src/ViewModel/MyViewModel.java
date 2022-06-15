package ViewModel;

import Model.IModel;
import Model.MovementDirections;
import algorithms.mazeGenerators.Maze;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {

    private IModel model;
    private Maze maze;
    private int rowChar;
    private int colChar;


    public MyViewModel(IModel model) {
        this.model = model;
        this.model.assignObserver(this);
        //this.maze = null;
    }


    public Maze getMaze() {
        return maze;
    }


    public int getRowChar() {
        return rowChar;
    }

    public int getColChar() {
        return colChar;
    }

    @Override
    public void update(Observable o, Object arg) {
//        if(o instanceof IModel)
//        {
//            if(maze == null)//generateMaze
//            {
//                this.maze = model.getMaze();
//            }
//            else {
//                int[][] maze = model.getMaze();
//
//                if (maze == this.maze)//Not generateMaze
//                {
//                    int rowChar = model.getRowChar();
//                    int colChar = model.getColChar();
//                    if(this.colChar == colChar && this.rowChar == rowChar)//Solve Maze
//                    {
//                        model.getSolution();
//                    }
//                    else//Update location
//                    {
//                        this.rowChar = rowChar;
//                        this.colChar = colChar;
//                    }
//
//
//                }
//                else//GenerateMaze
//                {
//                    this.maze = maze;
//                }
//            }
//
//            setChanged();
//            notifyObservers();
//        }

        setChanged();
        notifyObservers(arg);
    }


    public void generateMaze(int row,int col)
    {
        this.model.generateMaze(row,col);
    }

    public void moveCharacter(KeyEvent keyEvent)
    {
        MovementDirections direction;

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

        switch(keyEvent.getCode())
        {
            case DIGIT8:
                direction = MovementDirections.UP;
                break;
            case DIGIT2:
                direction = MovementDirections.DOWN;
                break;
            case DIGIT6:
                direction = MovementDirections.RIGHT;
                break;
            case DIGIT4:
                direction = MovementDirections.LEFT;
                break;

            //Slants
            case DIGIT9:
                direction = MovementDirections.UR;
                break;
            case DIGIT7:
                direction = MovementDirections.UL;
                break;
            case DIGIT3:
                direction = MovementDirections.DR;
                break;
            case DIGIT1:
                direction = MovementDirections.DL;
                break;
            // no move
            default:
                return;

        }

        model.updateCharacterLocation(direction);
    }

    public void getSolution()
    {
        model.getSolution();
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

    public void stopServers() {
        model.stopServers();
    }

    public boolean saveFile() {
        return model.saveFile();
    }

    public boolean loadFile(String name) {
        return model.loadFile(name);
    }

    public void removeSolution() {
        model.solutionRestart();
    }

    public void setShowSolution(boolean showSol) {
        model.setShowSolution(showSol);
    }


}

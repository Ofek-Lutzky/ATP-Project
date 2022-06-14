package ViewModel;

import Model.IModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {
    private IModel model;

   // private Maze maze;

    private int charRow;
    private int charCol;

    public MyViewModel(IModel model) {
        this.model = model;
        //this.maze = null;
        this.model.assignObserver(this);//Observe the Model for it's changes
    }

    public Maze getMaze() {
        return this.model.getMaze();
    }

    public int getCharacterRow() {
        return model.getCharacterRow();
    }

    public int getCharacterCol() {
        return model.getCharacterCol();
    }

    public Solution getSolution()
    {
        return model.getSolution();
    }

    @Override
    public void update(Observable o, Object arg) {
        //todo getting the argument
        // in reference to the arg we will do the update
        setChanged();
        notifyObservers(arg);
    }

    public void generateMaze(int row,int col)
    {
        this.model.generateMaze(row,col);
    }


    public void moveCharacter(KeyCode keyCode)
    {
        int direction = -1;

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

//        switch(keyCode)
//        {
//            case UP: // todo maybe need מקרי קצה
//                direction = 8;
//                break;
//            case DOWN:
//                direction = 2;
//                break;
//            case LEFT:
//                direction = 4;
//                break;
//            case RIGHT:
//                direction = 6;
//                break;
//
//            //Slants
//            case UR:
//                direction = 9;
//                break;
//            case UL:
//                direction = 7;
//                break;
//            case DR:
//                direction = 3;
//                break;
//            case DL:
//                direction = 1;
//                break;
//
//        }
//
//        model.updateCharacterLocation(direction);
    }


    public void solveMaze()
    {
        //todo update the model that i will have the maze inside.
        //so when we will need to have the sol we will ask from the model
        model.solveMaze();
    }



}

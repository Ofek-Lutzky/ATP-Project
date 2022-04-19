package algorithms.mazeGenerators;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Maze {
    //todo add method as we like

    private Position startPosition;
    private Position goalPosition;
    private int[][] board;
//    0 = not visited
//    1 = Wall
//    2 = Visited
//    8 = Start node
//    9 = target node

    public Maze(Position startPosition, Position goalPosition, int[][] board) {
        this.startPosition = startPosition;
        this.goalPosition = goalPosition;
        this.board = board;
    }

    public Position getStartPosition(){
        return this.startPosition;
    }

    public Position getGoalPosition(){
        return this.goalPosition;
    }

    public void Print(){
        String[][] toPrint = new String[board.length][board[0].length];
        for (int i = 0; i < this.board.length; i++){
            for (int j = 0; j < this.board[0].length; j++){
                if (i == this.getStartPosition().getRowIndex() && j == this.getStartPosition().getColumnIndex())
                    toPrint[i][j] = "S";
                else if(i == this.getGoalPosition().getRowIndex() && j == this.getGoalPosition().getColumnIndex())
                    toPrint[i][j] = "E";
                else{
                    if (this.board[i][j] == 1){

                        toPrint[i][j] = " ";
                    }
                    else {
                        toPrint[i][j] = "▓";
                    }

//                    toPrint[i][j] = String.valueOf(this.board[i][j]);
                }

            }
        }

        for (int i = 0; i < this.board.length; i++){
            System.out.println(Arrays.toString(toPrint[i]));
        }

        //todo S as the start and E as the exit check about if the print of them is here or in position
    }
}

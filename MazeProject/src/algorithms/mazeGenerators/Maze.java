package algorithms.mazeGenerators;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Maze {
    //todo add method as we like

    private Position startPosition;
    private Position goalPosition;
    private int[][] board;

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
//        for (int i = 0; i < this.board.length; i++){
//            for (int j = 0; j < this.board[0].length; j++){
//                if (i == this.getStartPosition().getRowIndex() && j == this.getStartPosition().getColumnIndex())
//                    System.out.println("S");
//                else if(i == this.getGoalPosition().getRowIndex() && j == this.getGoalPosition().getColumnIndex())
//                    System.out.println("E");
//                else
//                    System.out.println(String.valueOf(this.board[i][j]));
//            }
//            System.out.println();
//        }


        String[][] toPrint = new String[board.length][board[0].length];
        for (int i = 0; i < this.board.length; i++){
            for (int j = 0; j < this.board[0].length; j++){
                if (i == this.getStartPosition().getRowIndex() && j == this.getStartPosition().getColumnIndex())
                    toPrint[i][j] = "S";
                else if(i == this.getGoalPosition().getRowIndex() && j == this.getGoalPosition().getColumnIndex())
                    toPrint[i][j] = "E";
                else
                    toPrint[i][j] = String.valueOf(this.board[i][j]);
            }
        }

        for (int i = 0; i < this.board.length; i++){
            System.out.println(Arrays.toString(toPrint[i]));
        }

        //todo S as the start and E as the exit check about if the print of them is here or in position
    }
}

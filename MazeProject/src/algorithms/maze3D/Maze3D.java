package algorithms.maze3D;

import algorithms.mazeGenerators.Position;

import java.util.Arrays;

public class Maze3D {

    private Position3D startPosition;
    private Position3D goalPosition;
    private int[][][] board;

    public Maze3D(Position3D startPosition, Position3D goalPosition, int[][][] board) {
        this.startPosition = startPosition;
        this.goalPosition = goalPosition;
        this.board = board;
    }

    public int[][][] getMap(){return this.board;}
    public Position3D getStartPosition(){return this.startPosition;}
    public Position3D getGoalPosition(){return this.goalPosition;}

    //todo for US
    public void print(){
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_RED = "\u001B[31m";

        String[][][] toPrint = new String[board[0][0].length][board.length][board[0].length];
        for (int d = 0; d < this.board.length; d++){
        for (int i = 0; i < this.board.length; i++){
            for (int j = 0; j < this.board[0].length; j++){
//                if (i == this.getStartPosition().getRowIndex() && j == this.getStartPosition().getColumnIndex())
//                    toPrint[d][i][j] = ANSI_GREEN +"S" + ANSI_RESET;
//                else if(i == this.getGoalPosition().getRowIndex() && j == this.getGoalPosition().getColumnIndex())
//                    toPrint[d][i][j] = ANSI_RED + "E" + ANSI_RESET;
//                else{
//                    if (this.board[d][i][j] == 1){
//
//                        toPrint[d][i][j] = "▓";
//                    }
//                    else {
//                        toPrint[d][i][j] = " ";
//                    }

                    toPrint[d][i][j] = String.valueOf(this.board[d][i][j]);
                }

            }}

//
//        for (int i = 0; i < this.board.length; i++){
//            System.out.println(Arrays.toString(toPrint[i][1]));
//        }
    }
}

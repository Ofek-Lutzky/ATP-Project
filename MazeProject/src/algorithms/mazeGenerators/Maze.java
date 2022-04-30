package algorithms.mazeGenerators;

import algorithms.search.AState;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Maze {
    //todo add method as we like
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";

    private Position startPosition;
    private Position goalPosition;
    private int[][] board;
//    0 = not visited
//    1 = Wall
//    S = start
//    E = End


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

    public void print(){

        //A nice presentation for us

//        String[][] toPrint = new String[board.length][board[0].length];
//        for (int i = 0; i < this.board.length; i++){
//            for (int j = 0; j < this.board[0].length; j++){
//                if (i == this.getStartPosition().getRowIndex() && j == this.getStartPosition().getColumnIndex())
//                    toPrint[i][j] = ANSI_GREEN +"S" + ANSI_RESET;
//                else if(i == this.getGoalPosition().getRowIndex() && j == this.getGoalPosition().getColumnIndex())
//                    toPrint[i][j] = ANSI_RED + "E" + ANSI_RESET;
//                else{
//                    if (this.board[i][j] == 1){
//
//                        toPrint[i][j] = "▓";
//                    }
//                    else {
//                        toPrint[i][j] = " ";
//                    }
//
////                    toPrint[i][j] = String.valueOf(this.board[i][j]);
//                }
//
//            }
//        }
////
//        for (int i = 0; i < this.board.length; i++){
//            System.out.println(Arrays.toString(toPrint[i]));
//        }

        //todo S as the start and E as the exit check about if the print of them is here or in position

        //todo

        System.out.print("{");
        for (int i = 0; i < this.board.length; i++){
            System.out.print("{");
            for (int j = 0; j < this.board[0].length; j++){
                if (j<this.board.length-1){
                    if (i == this.getStartPosition().getRowIndex() && j == this.getStartPosition().getColumnIndex())
                        System.out.print(ANSI_GREEN +"S" + ANSI_RESET + ",");
                    else if(i == this.getGoalPosition().getRowIndex() && j == this.getGoalPosition().getColumnIndex())
                        System.out.print(ANSI_RED + "E" + ANSI_RESET + ",");
                    else{
                        System.out.print(String.valueOf(this.board[i][j])+",");
                    }

                }
                else{
                    if (i == this.getStartPosition().getRowIndex() && j == this.getStartPosition().getColumnIndex())
                        System.out.print(ANSI_GREEN +"S" + ANSI_RESET );
                    else if(i == this.getGoalPosition().getRowIndex() && j == this.getGoalPosition().getColumnIndex())
                        System.out.print(ANSI_RED + "E" + ANSI_RESET );
                    else{
                        System.out.print(String.valueOf(this.board[i][j]));
                    }
                }



            }
            System.out.println("},");
        }
        System.out.print("};\n");
    }

    public int[][] getBoard() {
        return board;
    }


}

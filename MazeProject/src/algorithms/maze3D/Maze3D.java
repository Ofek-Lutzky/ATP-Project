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

        System.out.print("{\n");

        for(int d = 0; d<this.board.length;d++){
            System.out.println("{");
        for (int i = 0; i < this.board[d].length; i++){
            System.out.print("\t{");
            for (int j = 0; j < this.board[d][i].length; j++){

                if (j<this.board[d][i].length-1){
                    if (i == this.getStartPosition().getRowIndex() && j == this.getStartPosition().getColumnIndex() && d==this.getStartPosition().getDepthIndex())
                        System.out.print(ANSI_GREEN +"S" + ANSI_RESET + ",");
                    else if(i == this.getGoalPosition().getRowIndex() && j == this.getGoalPosition().getColumnIndex() && d==this.getGoalPosition().getDepthIndex())
                        System.out.print(ANSI_RED + "E" + ANSI_RESET + ",");
                    else{
                        System.out.print(String.valueOf(this.board[d][i][j])+",");
                    }

                }
                else{
                    if (i == this.getStartPosition().getRowIndex() && j == this.getStartPosition().getColumnIndex() && d==this.getStartPosition().getDepthIndex())
                        System.out.print(ANSI_GREEN +"S" + ANSI_RESET );
                    else if(i == this.getGoalPosition().getRowIndex() && j == this.getGoalPosition().getColumnIndex() &&  d==this.getGoalPosition().getDepthIndex())
                        System.out.print(ANSI_RED + "E" + ANSI_RESET );
                    else{
                        System.out.print(String.valueOf(this.board[d][i][j]));
                    }
                }

            }
            System.out.println("},");
        }
            System.out.println("},");
        }
        System.out.print("};\n");
    }
}

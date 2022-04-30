package algorithms.search;

import algorithms.mazeGenerators.Maze;

import java.util.ArrayList;
import java.util.Arrays;

public class Solution {
    private ArrayList<AState> solution;

    public Solution() {
        solution = new ArrayList<>();
    }

    public ArrayList<AState> getSolutionPath() {
        return this.solution;
    }

    public void addToSolution(AState state){
        this.solution.add(state);
    }

    //todo delete func and imports related before seding -- print solver
    public void print(Maze maze) {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_RED = "\u001B[31m";

        //A nice presentation for us

        String[][] toPrint = new String[maze.getBoard().length][maze.getBoard()[0].length];
        for (int i = 0; i < maze.getBoard().length; i++) {
            for (int j = 0; j < maze.getBoard()[0].length; j++) {
                if (i == maze.getStartPosition().getRowIndex() && j == maze.getStartPosition().getColumnIndex())
                    toPrint[i][j] = ANSI_GREEN + "S" + ANSI_RESET;
                else if (i == maze.getGoalPosition().getRowIndex() && j == maze.getGoalPosition().getColumnIndex())
                    toPrint[i][j] = ANSI_RED + "E" + ANSI_RESET;
                else {
                    if (maze.getBoard()[i][j] == 1) {

                        toPrint[i][j] = "▓";
                    } else {
                        toPrint[i][j] = " ";
                    }

//                    toPrint[i][j] = String.valueOf(this.board[i][j]);
                }

            }
        }

        for (int j = 0; j < solution.size(); j++) {
            ((MazeState)solution.get(j)).getRow();
            toPrint[((MazeState)solution.get(j)).getRow()][((MazeState)solution.get(j)).getColumn()] = ANSI_RED + "@" + ANSI_RESET;
        }

//
        for (int i = 0; i < maze.getBoard().length; i++) {
            System.out.println(Arrays.toString(toPrint[i]));
        }
    }

    }

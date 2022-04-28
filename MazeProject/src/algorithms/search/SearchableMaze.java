package algorithms.search;

import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.Maze;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable{

    Maze maze;

    public SearchableMaze(Maze maze) {
        this.maze = maze;
    }

    @Override
    public AState getStartState() {
        Position p = this.maze.getStartPosition();
        AState ms = new MazeState(p);
        return ms;
    }

    @Override
    public AState getGoalState() {
        Position p = this.maze.getGoalPosition();
        AState ms = new MazeState(p);
        return ms;
    }

    //the function a state that it already know it of mazeState kind
    // so we are doing casting and add the neighbors with zero what is mean that they are the possible state to move to
    // from out side the search algo woun't need to know how the function get the possible states
    //this is why the function adapt the maze that generated to be Ishearchable
    @Override
    public ArrayList<AState> getAllPossibleStates(AState state) {
        ArrayList<AState> nb = new ArrayList<>();

        int[][] board = this.maze.getBoard();
        int x = ((MazeState) state).getRow();
        int y = ((MazeState) state).getColumn();

        if (x > 0 && insideBoard(x - 1, y, board)) {
            nb.add(new MazeState(new Position(x - 1, y)));
        }
        if (x + 1 < board.length && insideBoard(x + 1, y, board)) {
            nb.add(new MazeState(new Position(x + 1, y)));
        }
        if (y > 0 && insideBoard(x, y - 1, board)) {
            nb.add(new MazeState(new Position(x, y - 1)));
        }
        if (y + 1 < board[x].length && insideBoard(x, y + 1, board)) {
            nb.add(new MazeState(new Position(x, y + 1)));

        }

        return nb;
    }


    private boolean insideBoard(int x, int y, int[][] grid) {
        if (y >= 0 && x >= 0 && x < grid.length && y < grid[x].length && grid[x][y] == 0) {
            return true;
        }
        return false;
    }
}

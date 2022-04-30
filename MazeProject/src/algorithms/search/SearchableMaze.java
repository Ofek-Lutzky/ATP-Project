package algorithms.search;

import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.Maze;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable{

    private Maze maze;
    private AState start;
    private AState end;

    public SearchableMaze(Maze maze) {
        this.maze = maze;
        Position p1 = this.maze.getStartPosition();
        this.start = new MazeState(p1);
        Position p2 = this.maze.getGoalPosition();
        this.end = new MazeState(p2);

    }

    @Override
    public AState getStartState() {
        return this.start;
    }

    @Override
    public AState getGoalState() {
        return this.end;
    }

    public Maze getMaze(){
        return this.maze;
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

        //the if's adding all the neighbors left right up down and slants in on steps if there is.
        //it also checks that there isn't any duplicate inside neighbors
        //the neighbors at most 9 so it not adding run time O(1)
        //adding as clock move up to right
        //up -> slant right up-> right -> slant right down -> down -> slant left down -> left -> slant left up

        //up -> slant right up
        if (x > 0 && insideBoard(x - 1, y, board)) {
            //up
            nb.add(new MazeState(new Position(x - 1, y)));
            //slant right up
            if (insideBoard(x - 1, y+1, board) && !neighborsContain(x - 1, y+1, nb)){
                nb.add(new MazeState(new Position(x - 1, y+1)));
            }}

        //slant right up-> right -> slant right down
        if (y + 1 < board[x].length && insideBoard(x, y + 1, board)) {

            //slant right up
            if (insideBoard(x-1, y + 1, board) && !neighborsContain(x-1, y + 1, nb)) {
                nb.add(new MazeState(new Position(x-1, y + 1)));
            }
            //right
            nb.add(new MazeState(new Position(x, y + 1)));
            //slant right down
            if (insideBoard(x+1, y + 1, board) && !neighborsContain(x+1, y + 1, nb)) {
                nb.add(new MazeState(new Position(x+1, y + 1)));
            }

        }

        //slant right down-> down -> slant left down
        if (x + 1 < board.length && insideBoard(x + 1, y, board)) {

            //slant right down
            if (insideBoard(x + 1, y+1, board) && !neighborsContain(x + 1, y+1, nb)){
                nb.add(new MazeState(new Position(x + 1, y+1)));
            }
            //down
            nb.add(new MazeState(new Position(x + 1, y)));
            //slant left down
            if (insideBoard(x + 1, y-1, board) && !neighborsContain(x + 1, y-1, nb)){
                nb.add(new MazeState(new Position(x + 1, y-1)));
            }

        }

        //slant left down -> left -> slant left up
        if (y > 0 && insideBoard(x, y - 1, board)) {
            //slant left down
            if (insideBoard(x+1, y - 1, board) && !neighborsContain(x+1, y - 1, nb)) {
                nb.add(new MazeState(new Position(x+1, y - 1)));
            }
            //left
            nb.add(new MazeState(new Position(x, y - 1)));
            //slant left up
            if (insideBoard(x-1, y - 1, board) && !neighborsContain(x - 1, y-1, nb)) {
                nb.add(new MazeState(new Position(x-1, y - 1)));
            }
        }

        //slant left up -> up (without the check for the up not need)
        if (x > 0 && insideBoard(x - 1, y, board)) {
            if (insideBoard(x - 1, y-1, board) && !neighborsContain(x - 1, y-1, nb)){
                nb.add(new MazeState(new Position(x - 1, y-1)));
            }
        }


        //////////////////////////////////////////////
//        if (x > 0 && insideBoard(x - 1, y, board)) {
//            nb.add(new MazeState(new Position(x - 1, y)));
//
//            if (insideBoard(x - 1, y-1, board) && !neighborsContain(x - 1, y-1, nb)){
//                nb.add(new MazeState(new Position(x - 1, y-1)));
//            }
//            if (insideBoard(x - 1, y+1, board) && !neighborsContain(x - 1, y+1, nb)){
//                nb.add(new MazeState(new Position(x - 1, y+1)));
//            }
//        }
//        if (x + 1 < board.length && insideBoard(x + 1, y, board)) {
//            nb.add(new MazeState(new Position(x + 1, y)));
//            if (insideBoard(x + 1, y-1, board) && !neighborsContain(x + 1, y-1, nb)){
//                nb.add(new MazeState(new Position(x + 1, y-1)));
//            }
//            if (insideBoard(x + 1, y+1, board) && !neighborsContain(x + 1, y+1, nb)){
//                nb.add(new MazeState(new Position(x + 1, y+1)));
//            }
//        }
//        if (y > 0 && insideBoard(x, y - 1, board)) {
//            nb.add(new MazeState(new Position(x, y - 1)));
//            if (insideBoard(x-1, y - 1, board) && !neighborsContain(x - 1, y-1, nb)) {
//                nb.add(new MazeState(new Position(x-1, y - 1)));
//            }
//            if (insideBoard(x+1, y - 1, board) && !neighborsContain(x+1, y - 1, nb)) {
//                nb.add(new MazeState(new Position(x+1, y - 1)));
//            }
//        }
//        if (y + 1 < board[x].length && insideBoard(x, y + 1, board)) {
//            nb.add(new MazeState(new Position(x, y + 1)));
//            if (insideBoard(x-1, y + 1, board) && !neighborsContain(x-1, y + 1, nb)) {
//                nb.add(new MazeState(new Position(x-1, y + 1)));
//            }
//            if (insideBoard(x+1, y + 1, board) && !neighborsContain(x+1, y + 1, nb)) {
//                nb.add(new MazeState(new Position(x+1, y + 1)));
//            }
//
//        }

        return nb;
    }


    private boolean insideBoard(int x, int y, int[][] grid) {
        if (y >= 0 && x >= 0 && x < grid.length && y < grid[x].length && grid[x][y] == 0) {
            return true;
        }
        return false;
    }

    //todo check why the contain method not working
    private boolean neighborsContain(int x, int y, ArrayList<AState> nb) {
        for (int i = 0; i < nb.size(); i++) {
            if (((MazeState)nb.get(i)).getRow() == x && ((MazeState)nb.get(i)).getColumn() == y) {
                return true;
            }
        }
        return false;
    }
}

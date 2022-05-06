package algorithms.maze3D;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.ISearchable;
import algorithms.search.MazeState;

import java.util.ArrayList;

public class SearchableMaze3D implements ISearchable {

    private Maze3D maze;
    private AState start;
    private AState end;

    public SearchableMaze3D(Maze3D maze) {
        this.maze = maze;
        Position3D p1 = this.maze.getStartPosition();
        this.start = new Maze3DState(p1);
        Position3D p2 = this.maze.getGoalPosition();
        this.end = new Maze3DState(p2);

    }

    @Override
    public AState getStartState() {
        return this.start;
    }

    @Override
    public AState getGoalState() {
        return this.end;
    }

    public Maze3D getMaze(){
        return this.maze;
    }

    //the function a state that it already know it of mazeState kind
    // so we are doing casting and add the neighbors with zero what is mean that they are the possible state to move to
    // from out side the search algo woun't need to know how the function get the possible states
    //this is why the function adapt the maze that generated to be Ishearchable
    @Override
    public ArrayList<AState> getAllPossibleStates(AState state) {
        ArrayList<AState> nb = new ArrayList<>();
        int d = ((Maze3DState) state).getDepth();
        int x = ((Maze3DState) state).getRow();
        int y = ((Maze3DState) state).getColumn();
        ArrayList<int[]> addToNb = neighbors3D(d,x,y,this.maze.getMap());

        for (int i = 0;i < addToNb.size();i++){
            if (!neighborsContain(addToNb.get(i)[0],addToNb.get(i)[1],addToNb.get(i)[2],nb)){
            nb.add(new Maze3DState(new Position3D(addToNb.get(i)[0],addToNb.get(i)[1],addToNb.get(i)[2])));}
        }

        return nb;

    }

    private ArrayList<int[]> neighbors3D(int d, int x, int y, int[][][] board) {

        ArrayList<int[]> nb = new ArrayList<>();

        if (d > 0 && board[d-1][x][y] == 0) {
            nb.add(new int[]{d-1, x, y});
        }
        if (d + 1 < board.length && board[d+1][x][y] == 0) {
            nb.add(new int[]{d+1, x, y});
        }

        if (x > 0 && board[d][x-1][y] == 0) {
            nb.add(new int[]{d, x - 1, y});
        }

        if (x + 1 < board[d].length && board[d][x+1][y] == 0) {
            nb.add(new int[]{d, x + 1, y});
        }

        if (y > 0 && board[d][x][y-1] == 0) {
            nb.add(new int[]{d, x, y - 1});
        }

        if (y + 1 < board[d][x].length && board[d][x][y+1] == 0) {
            nb.add(new int[]{d, x, y + 1});
        }

        return nb;
    }


    private boolean neighborsContain(int d, int x, int y, ArrayList<AState> nb) {
        for (int i = 0; i < nb.size(); i++) {
            if (((Maze3DState)nb.get(i)).getDepth() == d && ((Maze3DState)nb.get(i)).getRow() == x && ((Maze3DState)nb.get(i)).getColumn() == y) {
                return true;
            }
        }
        return false;
    }



}

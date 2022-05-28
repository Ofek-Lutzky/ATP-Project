package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {
    private BestFirstSearch bfs = new BestFirstSearch();

//    @Test
//    void init() {
//        assertFalse(null, bfs);
//    }

    @Test
    void addOneToVisited() {
        bfs.addOneToVisited();
        assertEquals(1, bfs.getNumberOfNodesEvaluated());
    }

    @Test
    void solve() {
        int[][] board = {{0,1,0},{0,1,0},{0,0,0}};
        Maze maze = new Maze(new Position(0,0), new Position(0,2), board);
        //maze.print();
        Solution sol = bfs.solve(new SearchableMaze(maze));
        //sol.print(maze);
        ArrayList<AState> checker = new ArrayList<>();
        checker.add(new MazeState(new Position(0,2)));
        checker.add(new MazeState(new Position(1,2)));
        checker.add(new MazeState(new Position(2,1)));
        checker.add(new MazeState(new Position(1,0)));

        for(int i=0; i<4; i++){
            assertEquals(checker.get(i),sol.getSolutionPath().get(i));
        }

        //check 2
        int[][] board2 = {{0,1},{0,1}};
        Maze maze2 = new Maze(new Position(0,0), new Position(1,0), board2);
        //maze.print();
        Solution sol2 = bfs.solve(new SearchableMaze(maze2));
        //sol.print(maze);
        ArrayList<AState> checker2 = new ArrayList<>();
        checker2.add(new MazeState(new Position(1,0)));

        assertEquals(checker2.get(0),sol2.getSolutionPath().get(0));


        //check 3
        Maze maze3 = (new MyMazeGenerator()).generate(0,0);
//        maze3.print();
        Solution sol3 = bfs.solve(new SearchableMaze(maze3));
        //sol.print(maze);
        ArrayList<AState> checker3 = new ArrayList<>();
        checker3.add(new MazeState(maze3.getGoalPosition()));

        assertEquals(checker3.get(0),sol3.getSolutionPath().get(0));

    }

    @Test
    void getName() {
        assertEquals("BestFirstSearch", bfs.getName());
    }

    @Test
    void setName() {
        assertEquals("BestFirstSearch",bfs.getName());
    }
}
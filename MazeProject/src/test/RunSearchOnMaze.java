package test;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.*;
import java.util.ArrayList;


public class RunSearchOnMaze {
    public static void main(String[] args) {
        IMazeGenerator mg = new MyMazeGenerator();
        Maze maze = mg.generate(30, 30);
//        maze.print();
        SearchableMaze searchableMaze = new SearchableMaze(maze);
//        solveProblem(searchableMaze, new BreadthFirstSearch(),maze);
        solveProblem(searchableMaze, new DepthFirstSearch(), maze);
        solveProblem(searchableMaze, new BestFirstSearch(),maze);
        solveProblem(searchableMaze, new BreadthFirstSearch(),maze);
    }
    private static void solveProblem(ISearchable domain, ISearchingAlgorithm searcher, Maze maze) {//todo don't forget to remove the maze from signeture
        //Solve a searching problem with a searcher
        Solution solution = searcher.solve(domain);
        System.out.println(String.format("'%s' algorithm - nodes evaluated: %s", searcher.getName(), searcher.getNumberOfNodesEvaluated()));
        //Printing Solution Path
        System.out.println("Solution path:");
        ArrayList<AState> solutionPath = solution.getSolutionPath();
        solution.print(maze);
        for (int i = 0; i < solutionPath.size(); i++) {
            System.out.println(String.format("%s. %s",i,solutionPath.get(i)));
        }
    }
}

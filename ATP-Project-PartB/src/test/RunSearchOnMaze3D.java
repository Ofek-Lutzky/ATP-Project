package test;

import algorithms.maze3D.IMaze3DGenerator;
import algorithms.maze3D.Maze3D;
import algorithms.maze3D.MyMaze3DGenerator;
import algorithms.maze3D.SearchableMaze3D;
import algorithms.search.*;


public class RunSearchOnMaze3D {
    public static void main(String[] args) {
        IMaze3DGenerator mg = new MyMaze3DGenerator();
        Maze3D maze = mg.generate(1, 4, 4);
        maze.print();
        SearchableMaze3D searchableMaze = new SearchableMaze3D(maze);
       // solveProblem(searchableMaze, new BreadthFirstSearch(),maze);
        solveProblem(searchableMaze, new DepthFirstSearch(), maze);
        solveProblem(searchableMaze, new BestFirstSearch(),maze);
        solveProblem(searchableMaze, new BreadthFirstSearch(),maze);
    }
    private static void solveProblem(ISearchable domain, ISearchingAlgorithm searcher, Maze3D maze) {
        //Solve a searching problem with a searcher
        Solution solution = searcher.solve(domain);
        System.out.println(String.format("'%s' algorithm - nodes evaluated: %s", searcher.getName(), searcher.getNumberOfNodesEvaluated()));
        //Printing Solution Path
//        System.out.println("Solution path:");
//        ArrayList<AState> solutionPath = solution.getSolutionPath();
    }

}

package test;
import algorithms.mazeGenerators.*;

public class RunMazeGenerator {
    public static void main(String[] args) {
//        testMazeGenerator(new EmptyMazeGenerator());
//        testMazeGenerator(new SimpleMazeGenerator());
//        testMazeGenerator(new MyMazeGenerator());

        // todo my tests:
//        //Position print test
//        Position p = new Position( 0,2);
//        System.out.println(p);

        //emptyMaze test
        EmptyMazeGenerator eMaze = new EmptyMazeGenerator();
        Maze maze = eMaze.generate(10,20);
        maze.Print();

    }

    public static void  testMazeGenerator(IMazeGenerator mazeGenerator){

        //todo test they gave to delete when send the task
//        // prints the time it takes the algorithm to run System.out.println(String.format("Maze generation
//        time(ms): %s", mazeGenerator.measureAlgorithmTimeMillis(100/*rows*/,100/*columns*/)));
//        // generate another maze
//        Maze maze = mazeGenerator.generate(100/*rows*/, 100/*columns*/);
//        // prints the maze
//        maze.print();
//        // get the maze entrance
//        Position startPosition = maze.getStartPosition();
//        // print the start position
//        System.out.println(String.format("Start Position: %s", startPosition)); // format "{row,column}"
//        // prints the maze exit position
//        System.out.println(String.format("Goal Position: %s", maze.getGoalPosition()));
    }
}

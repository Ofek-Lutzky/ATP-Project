package test;
import algorithms.maze3D.Maze3D;
import algorithms.maze3D.MyMaze3DGenerator;
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

        //test
//        EmptyMazeGenerator eMaze = new EmptyMazeGenerator();
////        float num = eMaze.measureAlgorithmTimeMillis(1000,1000);
////        System.out.println(num);
//        Maze maze = eMaze.generate(10,10);
//        maze.print();
//        System.out.println(maze.getStartPosition());
//        System.out.println(maze.getGoalPosition());
//
//        SimpleMazeGenerator sMaze = new SimpleMazeGenerator();
////        float num1 = sMaze.measureAlgorithmTimeMillis(1000,1000);
////        System.out.println(num1);
//        Maze maze1 = sMaze.generate(10,10);
//        maze1.print();
//        System.out.println(maze1.getStartPosition());
//        System.out.println(maze1.getGoalPosition());

//        MyMazeGenerator mMaze = new MyMazeGenerator();
////        float num2 = mMaze.measureAlgorithmTimeMillis(1000,1000);
////        System.out.println(num2);
//        Maze maze2 = mMaze.generate(10,10);
//        maze2.print();
//        System.out.println(maze2.getStartPosition());
//        System.out.println(maze2.getGoalPosition());

        MyMaze3DGenerator mMaze3D = new MyMaze3DGenerator();
//        float num2 = mMaze.measureAlgorithmTimeMillis(1000,1000);
//        System.out.println(num2);
        Maze3D maze3D = mMaze3D.generate(10,10,10);
        maze3D.print();
        System.out.println(maze3D.getStartPosition());
        System.out.println(maze3D.getGoalPosition());
    }

    public static void  testMazeGenerator(IMazeGenerator mazeGenerator){

//        //todo test they gave to delete when send the task
//        // prints the time it takes the algorithm to run
//        System.out.println(String.format("Maze generation time(ms): %s", mazeGenerator.measureAlgorithmTimeMillis(100/*rows*/,100/*columns*/)));
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

package test;

import algorithms.maze3D.Maze3D;
import algorithms.maze3D.MyMaze3DGenerator;

public class RunMaze3DGenerator {

    public static void main(String[] args) {
        MyMaze3DGenerator mMaze3D = new MyMaze3DGenerator();
        float num2 = mMaze3D.measureAlgorithmTimeMillis(500,500,500);
        System.out.println(num2);
        Maze3D maze3D = mMaze3D.generate(4,4,4);
        maze3D.print();
//        System.out.println(maze3D.getStartPosition());
//        System.out.println(maze3D.getGoalPosition());
    }
}

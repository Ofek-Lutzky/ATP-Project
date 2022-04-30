package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator{

    public EmptyMazeGenerator() {}

    @Override
    public Maze generate(int rows, int columns) {

        int[][] emptyBoard = new int[rows][columns];

        int[] startEndArray = this.startEndFunc(rows, columns);

        //return maze object
        return new Maze(new Position(startEndArray[0], startEndArray[1]), new Position(startEndArray[2], startEndArray[3]), emptyBoard);
    }
}

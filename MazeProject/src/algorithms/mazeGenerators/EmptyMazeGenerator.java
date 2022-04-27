package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator{

    public EmptyMazeGenerator() {}

    @Override
    public Maze generate(int rows, int columns) {
        //todo check where is need to be the start and the end in emptyMaze
        int[][] emptyBoard = new int[rows][columns];
//        for (int i = 0; i < emptyBoard.length; i++){
//            for (int j = 0; j < emptyBoard[0].length; j++){
//                emptyBoard[i][j] = 0;
//            }
//        }
        //todo check id you really need the loops becuase java gives 0 auto

        int[] startEndArray = this.startEndFunc(rows, columns);

        //return maze object
        return new Maze(new Position(startEndArray[0], startEndArray[1]), new Position(startEndArray[2], startEndArray[3]), emptyBoard);
    }
}

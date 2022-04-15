package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator{

    public EmptyMazeGenerator() {}

    @Override
    public Maze generate(int rows, int columns) {
        //todo check where is need to be the start and the end in emptyMaze
        int[][] emptyBoard = new int[rows][columns];
        for (int i = 0; i < emptyBoard.length; i++){
            for (int j = 0; j < emptyBoard[0].length; j++){
                emptyBoard[i][j] = 0;
            }
        }

        Maze rtnMaze = new Maze(new Position(0,0), new Position(0,0), emptyBoard);
        return rtnMaze;
    }
}

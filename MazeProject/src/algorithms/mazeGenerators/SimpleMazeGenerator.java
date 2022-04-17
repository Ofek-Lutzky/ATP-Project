package algorithms.mazeGenerators;
import java.util.concurrent.ThreadLocalRandom;

public class SimpleMazeGenerator extends AMazeGenerator{

    public SimpleMazeGenerator() {}

    @Override
    public Maze generate(int rows, int columns) {
        int[][] simpleBoard = new int[rows][columns];
        for (int i = 0; i < simpleBoard.length; i++){
            for (int j = 0; j < simpleBoard[0].length; j++){
                simpleBoard[i][j] = ThreadLocalRandom.current().nextInt(0, 1+1);
                //todo it can give me a maze without answer
            }
        }

        Maze rtnMaze = new Maze(new Position(0,0), new Position(rows-1,columns-1), simpleBoard);
        return rtnMaze;
    }
}

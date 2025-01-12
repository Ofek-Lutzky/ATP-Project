package algorithms.mazeGenerators;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SimpleMazeGenerator extends AMazeGenerator{

    public SimpleMazeGenerator() {}

    /**
     *
     * @param rows - the num of rows
     * @param columns the num of column
     * @return Maze object
     * the funtion is throwing random walls on the board.
     * after choose the start, end randomly on it frame
     * after braek the wall on one way to sure that will have pass
     */
    @Override
    public Maze generate(int rows, int columns) {
        // if the parametrs are not correct we will made difult maze size 10*10
        if (rows < 2 || columns < 2){
            rows = 10;
            columns = 10;
        }

        int[][] simpleBoard = new int[rows][columns];
        for (int i = 0; i < simpleBoard.length; i++){
            for (int j = 0; j < simpleBoard[0].length; j++){
                simpleBoard[i][j] = ThreadLocalRandom.current().nextInt(0, 1+1);
            }
        }


        int[] startEndArray = this.startEndFunc(rows, columns);
        simpleBoard = this.makeAPassToStartEnd(startEndArray, simpleBoard);


        //we will do a pass that certify that we have at least one solution for the maze.
        //break the walls on the way, by going right and down or up or left and down or up
        if (startEndArray[3] > startEndArray[1]){
            for (int i = startEndArray[1] ; i <= startEndArray[3]; i++){
                simpleBoard[startEndArray[0]][i] = 0;
            }
            if (startEndArray[2] > startEndArray[0]){
                for (int j = startEndArray[0] ; j <= startEndArray[2]; j++){
                    simpleBoard[j][startEndArray[3]] = 0;
                }
            }
            else{
                for (int j = startEndArray[0] ; j >= startEndArray[2]; j--){
                    simpleBoard[j][startEndArray[3]] = 0;
                }
            }
        }


        else{

            for (int i = startEndArray[1] ; i >= startEndArray[3]; i--){
                simpleBoard[startEndArray[0]][i] = 0;
            }
            if (startEndArray[2] > startEndArray[0]){
                for (int j = startEndArray[0] ; j <= startEndArray[2] ; j++){
                    simpleBoard[j][startEndArray[3]] = 0;
                }
            }
            else{
                for (int j = startEndArray[0] ; j >= startEndArray[2]; j--){
                    simpleBoard[j][startEndArray[3]] = 0;
                }
            }
        }

        //return maze object
        return new Maze(new Position(startEndArray[0], startEndArray[1]), new Position(startEndArray[2], startEndArray[3]), simpleBoard);
    }
}

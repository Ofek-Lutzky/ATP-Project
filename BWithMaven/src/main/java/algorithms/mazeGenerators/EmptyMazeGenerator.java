package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator{

    public EmptyMazeGenerator() {}

    /**
     *
     * @param rows - the num of rows
     * @param columns the num of column
     * @return new empty Maze with random start,end
     */
    @Override
    public Maze generate(int rows, int columns) {
        // if the parametrs are not correct we will made difult maze size 10*10
        if (rows < 2 || columns < 2){
            rows = 10;
            columns = 10;
        }

        int[][] emptyBoard = new int[rows][columns];

        int[] startEndArray = this.startEndFunc(rows, columns);

        //return maze object
        return new Maze(new Position(startEndArray[0], startEndArray[1]), new Position(startEndArray[2], startEndArray[3]), emptyBoard);
    }
}

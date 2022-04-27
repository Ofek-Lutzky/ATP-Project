package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Random;

public abstract class  AMazeGenerator implements IMazeGenerator{
    @Override
    public abstract Maze generate(int rows, int columns); //we will leave it abstract for the childrens to implement

    //todo check: in the pdf there is a,b,c is be needed?

    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns) {

        long timeBefore = System.currentTimeMillis();
        generate(rows,columns);
        long timeAfter = System.currentTimeMillis();

        return timeAfter-timeBefore;
    }

    //todo function that place the S, and the E in the maze board and doing all the validility checks


    //the function will return array of four integers that sighn the place of the start and the end
    //[sr,sc,er,ec] this whay
    //random just from the walls - Frame
    public int[] startEndFunc(int rows, int columns){

        int[] rand = new int[4];
        Random randomGenerator = new Random();

        int rowStart = randomGenerator.nextInt(rows - 1);
        int columnStart;
        // the column will pick according to the row.

        if (rowStart != 0 && rowStart != rows-1){
            int[] pickColumn = new int[]{0,columns-1};
            columnStart = pickColumn[randomGenerator.nextInt(2)];
        }
        else{
            columnStart = randomGenerator.nextInt(columns - 1);
        }

        int columnEnd = randomGenerator.nextInt(columns - 1);
        int rowEnd;
        // for the end we changed the range we picking the random so it probaly fall on other cell then the start in a better way

        if (columnEnd != 0 && columnEnd != columns-1){
            int[] pickRow = new int[]{0,rows-1};
            rowEnd = pickRow[randomGenerator.nextInt(2)];
        }
        else{
            rowEnd = randomGenerator.nextInt(rows - 1);
        }

        rand[0] = rowStart;
        rand[1] = columnStart;
        rand[2] = rowEnd;
        rand[3] = columnEnd;

        return rand;
    }

    //put the S and the E on the board and doing fix to check that there will be a pass
    //break one wall if there isn't
    public int[][] putStartEndOnBoard(int[] startEndArray, int[][] board){
        ArrayList<int[]> sNb = neighbors(startEndArray[0], startEndArray[1], board);
        ArrayList<int[]> eNb = neighbors(startEndArray[2], startEndArray[3], board);

        // to count if all the neighbors are one's if not doesnt need to do nothing
        int counter = 0;

        //for the start cell
        for (int[] ints : sNb) {
            if (board[ints[0]][ints[1]] == 1) {
                counter++;
            }
        }
        if (counter == sNb.size()){
            int rnd = new Random().nextInt(sNb.size());
            board[sNb.get(rnd)[0]][sNb.get(rnd)[1]] = 0;

        }
        counter = 0;
        // for the end cell
        for (int[] ints : eNb) {
            if (board[ints[0]][ints[1]] == 1) {
                counter++;
            }
        }
        if (counter == eNb.size()){
            int rnd = new Random().nextInt(eNb.size());
            board[eNb.get(rnd)[0]][eNb.get(rnd)[1]] = 0;
        }

        return board;
    }

    //add the neighbors
    private ArrayList<int[]> neighbors(int x, int y, int[][] board) {
        //todo check
        ArrayList<int[]> nb = new ArrayList<>();

        if (x > 0 && insideBoard(x - 1, y, board)) {
            nb.add(new int[]{x - 1, y});
        }
        if (x + 1 < board.length && insideBoard(x + 1, y, board)) {
            nb.add(new int[]{x + 1, y});
        }
        if (y > 0 && insideBoard(x, y - 1, board)) {
            nb.add(new int[]{x, y - 1});
        }
        if (y + 1 < board[x].length && insideBoard(x, y + 1, board)) {
            nb.add(new int[]{x, y + 1});
        }

        return nb;
    }

    //check validation
    private boolean insideBoard(int x, int y, int[][] grid) {
        if (y >= 0 && x >= 0 && x < grid.length && y < grid[x].length) {
            return true;
        }
        return false;
    }
}

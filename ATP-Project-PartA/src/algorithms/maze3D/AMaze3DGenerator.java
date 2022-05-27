package algorithms.maze3D;

import java.util.ArrayList;
import java.util.Random;

public abstract class AMaze3DGenerator implements IMaze3DGenerator {

    /**
     *
     * @param depth the index of depth
     * @param row the index of row
     * @param column the index of column
     * @return Maze object
     */
    @Override
    public abstract Maze3D generate(int depth, int row, int column);

    /**
     *
     * @param depth the index of depth
     * @param row the index of row
     * @param column the index of column
     * @return long timeAfter-timeBefore
     */
    @Override
    public long measureAlgorithmTimeMillis(int depth, int row, int column) {
        long timeBefore = System.currentTimeMillis();
        generate(depth, row, column);
        long timeAfter = System.currentTimeMillis();

        return timeAfter-timeBefore;
    }

    /**
     *
     * @param depth the nums of depth
     * @param rows the nums of rows
     * @param columns the nums of columns
     * @return int[] rand that contain the indexes of the Start,End
     * we did it in a smart way so there will be last change that the start and the end will fall near eachother
     */
    public int[] startEndFunc3D(int depth, int rows, int columns){

        int[] rand = new int[6];
        Random randomGenerator = new Random();

        int rowStart = randomGenerator.nextInt(rows - 1);
        int columnStart;
        int depthStart;

        // the column will pick according to the row.

        if (rowStart != 0 && rowStart != rows-1){
            int[] pickColumn = new int[]{0,columns-1};
            int[] pickDepth = new int[]{0,depth-1};
            columnStart = pickColumn[randomGenerator.nextInt(2)];
            depthStart = pickDepth[randomGenerator.nextInt(2)];
        }
        else{
            columnStart = randomGenerator.nextInt(columns - 1);
            depthStart = randomGenerator.nextInt(depth - 1);
        }


        int columnEnd;
        int rowEnd;
        int depthEnd = randomGenerator.nextInt(depth - 1);
        // for the end we changed the range we picking the random so it probaly fall on other cell then the start in a better way

        if (depthEnd != 0 && depthEnd != depth-1){
            int[] pickRow = new int[]{0,rows-1};
            int[] pickColumn = new int[]{0,columns-1};
            rowEnd = pickRow[randomGenerator.nextInt(2)];
            columnEnd = pickColumn[randomGenerator.nextInt(2)];
        }
        else{
            rowEnd = randomGenerator.nextInt(rows - 1);
            columnEnd = randomGenerator.nextInt(columns - 1);
        }

        while (depthEnd == depthStart && columnEnd == columnStart && rowStart == rowEnd){

            depthEnd = randomGenerator.nextInt(depth - 1);

            if (depthEnd != 0 && depthEnd != depth-1){
                int[] pickRow = new int[]{0,rows-1};
                int[] pickColumn = new int[]{0,columns-1};
                rowEnd = pickRow[randomGenerator.nextInt(2)];
                columnEnd = pickColumn[randomGenerator.nextInt(2)];
            }
            else{
                rowEnd = randomGenerator.nextInt(rows - 1);
                columnEnd = randomGenerator.nextInt(columns - 1);
            }
        }

        rand[0] = depthStart;
        rand[1] = rowStart;
        rand[2] = columnStart;
        rand[3] = depthEnd;
        rand[4] = rowEnd;
        rand[5] = columnEnd;

        return rand;
    }

    /**
     *
     * @param startEndArray an array of four integers that represent the indexes of the start and the end
     * @param board board
     * @return board just to be sure it is updated in the original
     * make sure that will have a pass
     * we did it in a smart way so there will be last change that the start and the end will fall near eachother
     * check S and the E are ok, doing fix to check that there will be a pass
     * break one wall if there isn't
     */

    public int[][][] makeAPassToStartEnd3D(int[] startEndArray, int[][][] board){
        for (int i = 0;i<board.length;i++){
            board[i][startEndArray[1]][startEndArray[2]] = 0;
            board[i][startEndArray[4]][startEndArray[5]] = 0;}


        //get the neighbors that not on the depths for choose random from them and break all the wall in the depth of it to sure pass
        ArrayList<int[]> sNb = neighborsForSure3DPass(startEndArray[0], startEndArray[1], startEndArray[2], board);
        ArrayList<int[]> eNb = neighborsForSure3DPass(startEndArray[3], startEndArray[4], startEndArray[5], board);

        int rnd1 = new Random().nextInt(sNb.size());
        while (sNb.get(rnd1)[1] == startEndArray[1] && sNb.get(rnd1)[2] == startEndArray[2] ){
            rnd1 = new Random().nextInt(sNb.size());
        }
        for (int i = 0;i<board.length;i++)
            board[i][sNb.get(rnd1)[1]][sNb.get(rnd1)[2]] = 0;


        int rnd2 = new Random().nextInt(eNb.size());
        while (eNb.get(rnd2)[1] == startEndArray[4] && eNb.get(rnd2)[2] == startEndArray[5]){
            rnd2 = new Random().nextInt(sNb.size());
        }
        for (int i = 0;i<board.length;i++)
            board[i][eNb.get(rnd2)[1]][eNb.get(rnd2)[2]] = 0;

        return board;
    }

    /**
     *
     * @param d the index of depth
     * @param x the index of row
     * @param y the index of column
     * @param board board
     * @return ArrayList<int[]> nb that present the neighbors
     */
    //add the neighbors
    private ArrayList<int[]> neighborsForSure3DPass(int d, int x, int y, int[][][] board) {

        ArrayList<int[]> nb = new ArrayList<>();

        if (x > 0) {
            nb.add(new int[]{d, x - 1, y});
        }
        if (x + 1 < board[d].length) {
            nb.add(new int[]{d, x + 1, y});
        }
        if (y > 0) {
            nb.add(new int[]{d, x, y - 1});
        }
        if (y + 1 < board[d][x].length) {
            nb.add(new int[]{d, x, y + 1});
        }
        return nb;
    }

}

package algorithms.maze3D;

import java.util.ArrayList;
import java.util.Random;

public abstract class AMaze3DGenerator implements IMazeGenerator3D{
    @Override
    public abstract Maze3D generate(int depth, int row, int column);

    @Override
    public long measureAlgorithmTimeMillis(int depth, int row, int column) {
        long timeBefore = System.currentTimeMillis();
        generate(depth, row, column);
        long timeAfter = System.currentTimeMillis();

        return timeAfter-timeBefore;
    }

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

    //check S and the E are ok, doing fix to check that there will be a pass
    //break one wall if there isn't
    public int[][][] makeAPassToStartEnd3D(int[] startEndArray, int[][][] board){
        for (int i = 0;i<board.length;i++){
            board[i][startEndArray[1]][startEndArray[2]] = 0;
            board[i][startEndArray[4]][startEndArray[5]] = 0;}


        //todo falling not giving nighbors
        ArrayList<int[]> sNb = neighbors3D(startEndArray[0], startEndArray[1], startEndArray[2], board);
        ArrayList<int[]> eNb = neighbors3D(startEndArray[3], startEndArray[4], startEndArray[5], board);

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

        // to count if all the neighbors are one's if not doesnt need to do nothing
//        int counter = 0;
//
//        //for the start cell
//        for (int[] ints : sNb) {
//            if (board[ints[0]][ints[1]][ints[2]] == 1) {
//                counter++;
//            }
//        }
//        if (counter == sNb.size()){
//            int rnd = new Random().nextInt(sNb.size());
//            for (int i = 0;i<board.length;i++)
//                board[i][sNb.get(rnd)[1]][sNb.get(rnd)[2]] = 0;
////                board[sNb.get(rnd)[0]][sNb.get(rnd)[1]][sNb.get(rnd)[2]] = 0;
//
//        }
//
//        counter = 0;
//        // for the end cell
//        for (int[] ints : eNb) {
//            if (board[ints[0]][ints[1]][ints[2]] == 1) {
//                counter++;
//            }
//        }
//        if (counter == eNb.size()){
//            int rnd = new Random().nextInt(eNb.size());
//            for (int i = 0;i<board.length;i++)
//                board[i][eNb.get(rnd)[1]][eNb.get(rnd)[2]] = 0;
////            board[eNb.get(rnd)[0]][eNb.get(rnd)[1]][eNb.get(rnd)[2]] = 0;
//
//        }



        return board;
    }

    //add the neighbors
    //add the neighbors
    private ArrayList<int[]> neighbors3D(int d, int x, int y, int[][][] board) {

        ArrayList<int[]> nb = new ArrayList<>();

        if (d > 0) {
            nb.add(new int[]{d-1, x, y});
        }
        if (d + 1 < board.length) {
            nb.add(new int[]{d+1, x, y});
        }

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

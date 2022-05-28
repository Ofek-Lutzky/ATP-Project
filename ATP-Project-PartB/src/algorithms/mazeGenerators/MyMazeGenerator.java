package algorithms.mazeGenerators;

import java.util.*;

public class MyMazeGenerator extends AMazeGenerator {

    /**
     * @param rows - the num of rows
     * @param columns the num of column
     * @return Maze object that his board done with Binary tree
     */
    @Override
    // the method generate the maze
    public Maze generate(int rows, int columns) {
        // if the parametrs are not correct we will made difult maze size 10*10
        if (rows < 2 || columns < 2){
            rows = 10;
            columns = 10;
        }

        // first making an empty board
        int[][] board = new int[rows][columns];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = 1;
            }
        }

        //mark the first cell for the algo
        board[0][0] = 0;
        BinaryTree(board);

        //randomize the start and the end places
        int[] startEndArray = this.startEndFunc(rows, columns);
        //insure solution by breck a wall
        board = this.makeAPassToStartEnd(startEndArray, board);

        //return maze object
        return new Maze(new Position(startEndArray[0], startEndArray[1]), new Position(startEndArray[2], startEndArray[3]), board);
    }

    /**
     *
     * @param x - the index of the row
     * @param y  - the index of the column
     * @param board - board
     * @return ArrayList<int[]> nb neighbors form up/left
     */

    //add the neighbors just from up and left as the binary tree algo need
    private ArrayList<int[]> neighbors(int x, int y, int[][] board) {

        ArrayList<int[]> nb = new ArrayList<>();

        if (x > 1 && board[x - 2][y] == 0) {
            nb.add(new int[]{x - 2, y});
        }
        if (y > 1 && board[x][y - 2] == 0) {
            nb.add(new int[]{x, y - 2});
        }

        return nb;
    }


    /**
     *
     * @param board - board
     * will update the board and break the walls in the way of Binary Tree
     */
    private void BinaryTree(int[][] board) {
        //simple as this
        //go threw all the cell and decide if connect them up or left randomly
        //if they have neighbors to connect to
        //finally we will have a pass
        Random random = new Random();
        for (int i = 0; i < board.length; i = i + 2) {
            for (int j = 0; j < board[0].length; j = j + 2) {
                ArrayList<int[]> nB = neighbors(i, j, board);
                if (nB.size() == 0) {
                    continue;
                }

                int[] n = nB.get(random.nextInt(nB.size()));

                int x = n[0];
                int y = n[1];

                //fill the chosen pass from the random
                if (j > y) {
                    board[i][j] = 0;
                    board[i][j - 1] = 0;
                    board[i][j - 2] = 0; //the same as board[x][ny] = 1
                } else if (i > x) {
                    board[i][j] = 0;
                    board[i - 1][j] = 0;
                    board[i - 2][j] = 0; //the same as board[nx][y] = 1
                }
            }
        }

    }


}

package algorithms.maze3D;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MyMaze3DGenerator extends AMaze3DGenerator{

    public MyMaze3DGenerator() {}
    @Override
    // the method generate the maze
    public Maze3D generate(int depth, int rows, int columns) {
        // first making an empty board
        int[][][] board = new int[depth][rows][columns];

        for (int d = 0;d< board.length;d++){
            for (int i = 0; i < board[d].length; i++) {
                Arrays.fill(board[d][i], 1);
            }
        }

        //mark the first cell for the algo
        board[0][0][0] = 0;
        BinaryTree(board);

        int[] startEndArray = this.startEndFunc3D(depth, rows, columns);
        //insure solution by breck a wall
        board = this.makeAPassToStartEnd3D(startEndArray, board);

        //return maze object
        return new Maze3D(new Position3D(startEndArray[0], startEndArray[1], startEndArray[2]), new Position3D(startEndArray[3], startEndArray[4], startEndArray[5]), board);
    }

    //add the neighbors just from up and left as the binary tree algo need
    private ArrayList<int[]> neighbors(int d,int x, int y, int[][][] board) {

        ArrayList<int[]> nb = new ArrayList<>();

        if (d > 1 && board[d-2][x][y] == 0) {
            nb.add(new int[]{d-2,x,y-2});
        }

        if (x > 1 && board[d][x-2][y] == 0) {
            nb.add(new int[]{d,x-2,y});
        }
        if (y > 1 && board[d][x][y-2] == 0) {
            nb.add(new int[]{d,x,y-2});
        }


        return nb;
    }


    /**
     *
     * @param d
     * @param x
     * @param y
     * @param nd
     * @param nx
     * @param ny
     * @param board
     *  the function fill the pass in the board. (braking the walls near that chosen)
     */
    //connect the boards new neighbor selected
    public void fillPath(int d,int x, int y,int nd, int nx, int ny, int[][][] board) {
        if (d > nd) {
            board[d][x][y] = 0;
            board[d-1][x][y] = 0;
            board[d-2][x][y] = 0; //the same as board[x][ny] = 1
        }
        else if (y > ny) {
            board[d][x][y] = 0;
            board[d][x][y - 1] = 0;
            board[d][x][y - 2] = 0; //the same as board[x][ny] = 1
        }
        else if (x > nx) {
            board[d][x][y] = 0;
            board[d][x - 1][y] = 0;
            board[d][x - 2][y] = 0; //the same as board[nx][y] = 1
        }

    }


    private void BinaryTree(int[][][] board){
        //simple as this
        //go threw all the cell and decide if connect them up or left randomly
        //if they have neighbors to connect to
        //finally we will have a pass
        Random random = new Random();
        for (int d = 0;d< board.length;d++){
        for (int i = 0; i < board[d].length; i = i+2) {
            for (int j = 0; j < board[d][i].length; j = j+2) {
                ArrayList<int[]> nB = neighbors(d, i, j, board);
                if (nB.size() == 0) {
                    continue;
                }

                int[] n = nB.get(random.nextInt(nB.size()));

                int z = n[0];
                int x = n[1];
                int y = n[2];

                fillPath(d,i,j,z,x,y,board);

            }
        }
        }

    }



}

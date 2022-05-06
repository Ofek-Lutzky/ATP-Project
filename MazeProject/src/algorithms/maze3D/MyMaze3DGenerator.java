package algorithms.maze3D;

import java.util.ArrayList;
import java.util.Random;

public class MyMaze3DGenerator extends AMaze3DGenerator{

    public MyMaze3DGenerator() {}

    @Override
    // the method generate the maze
    public Maze3D generate(int depth, int rows, int columns) {
        // first making an empty board
        int[][][] board = new int[depth][rows][columns];

        // the path will help us determine from what cells we will randomly pick in prim algo
        ArrayList<int[]> path = new ArrayList<>();

        //randomize cell to start the algorithm from
        Random randomGenerator = new Random();
        int depthFirst = randomGenerator.nextInt(depth - 1);
        int rowFirst = randomGenerator.nextInt(rows - 1);
        int columnFirst = randomGenerator.nextInt(columns - 1);
        this.addPath(depthFirst, rowFirst, columnFirst, board, path);
        board[depthFirst][rowFirst][columnFirst] = 1;

        this.Prim(board, path);

        //change the board that return from prim the 1's and 0's
        for (int d = 0; d < board.length; d++){
            for (int i = 0; i < board[d].length; i++) {
                for (int j = 0; j < board[d][i].length ; j++) {

                        if (board[d][i][j] == 0) {
                            board[d][i][j] = 1;
                        } else {
                            board[d][i][j] = 0;
                        }
                }
            }
        }

        int[] startEndArray = this.startEndFunc3D(depth, rows, columns);
        //todo i think i need to do a bigger pass maybe accordin to the depth
        board = this.makeAPassToStartEnd3D(startEndArray, board);

        //return maze object
        return new Maze3D(new Position3D(startEndArray[0], startEndArray[1], startEndArray[2]), new Position3D(startEndArray[3], startEndArray[4], startEndArray[5]), board);
    }

    //method that checking if we can add the cell to path array
    public void addPath(int d, int x, int y, int[][][] board, ArrayList<int[]> path) {
        //checks that the cell inside the board and there is no duplicate by path contains
        if (y >= 0 && x >= 0 && d >= 0 && d < board.length && x < board[d].length && y < board[d][x].length &&
                board[d][x][y] == 0 && !pathContain(d ,x, y, path)) {
            path.add(new int[]{d, x, y});
        }
    }

    //like an equal private method to help us check if the array already contain the cell.
    private boolean pathContain(int d, int x, int y, ArrayList<int[]> path) {
        for (int i = 0; i < path.size(); i++) {
            if (path.get(i)[0] == d && path.get(i)[1] == x && path.get(i)[2] == y) {
                return true;
            }
        }
        return false;
    }

    //for each cell we go threw in prim we will add the neighbors for the next iteration
    public void markAsVisited(int d, int x, int y, int[][][] board, ArrayList<int[]> path) {
        addPath(d,x - 2, y, board, path);
        addPath(d,x + 2, y, board, path);
        addPath(d, x, y - 2, board, path);
        addPath(d, x, y + 2, board, path);
        addPath(d-2, x, y, board, path);
        addPath(d+2, x, y , board, path);
    }


    //add the neighbors
    private ArrayList<int[]> neighbors(int d, int x, int y, int[][][] board) {

        ArrayList<int[]> nb = new ArrayList<>();

        if (d > 1 && board[d-2][x][y] == 1) {
            nb.add(new int[]{d-2, x, y});
        }
        if (d + 2 < board.length && board[d+2][x][y] == 1) {
            nb.add(new int[]{d+2, x, y});
        }

        if (x > 1 && board[d][x-2][y] == 1) {
            nb.add(new int[]{d, x - 2, y});
        }
        if (x + 2 < board[d].length && board[d][x+2][y] == 1) {
            nb.add(new int[]{d, x + 2, y});
        }
        if (y > 1 && board[d][x][y-2] == 1) {
            nb.add(new int[]{d, x, y - 2});
        }
        if (y + 2 < board[d][x].length && board[d][x][y+2] == 1) {
            nb.add(new int[]{d, x, y + 2});
        }

        return nb;
    }


    //connect the boards new neighbor selected
    private int[][][] direction(int d, int x, int y, int nd, int nx, int ny, int[][][] board) {

        if (d < nd) {
            board[d][x][y] = 1;
            board[d+1][x][y] = 1;
            board[d+2][x][y] = 1; //the same as board[nx][y] = 1
        } else if (d > nd) {
            board[d][x][y] = 1;
            board[d-1][x][y] = 1;
            board[d-2][x][y] = 1; //the same as board[nx][y] = 1
        }

         else if (x < nx) {
            board[d][x][y] = 1;
            board[d][x + 1][y] = 1;
            board[d][x + 2][y] = 1; //the same as board[nx][y] = 1
        } else if (x > nx) {
            board[d][x][y] = 1;
            board[d][x - 1][y] = 1;
            board[d][x - 2][y] = 1; //the same as board[nx][y] = 1
        }
        else if (y < ny) {
            board[d][x][y] = 1;
            board[d][x][y + 1] = 1;
            board[d][x][y + 2] = 1; //the same as board[x][ny] = 1
        } else if (y > ny) {
            board[d][x][y] = 1;
            board[d][x][y - 1] = 1;
            board[d][x][y - 2] = 1; //the same as board[x][ny] = 1
        }
        return board;

    }


    private boolean empty(ArrayList<int[]> path) {
        if (path.size() == 0) {
            return true;
        }
        return false;

    }

    //Prim algorithm
    private void Prim(int[][][] board, ArrayList<int[]> path) {
        Random randomGenerator = new Random();

        while (!this.empty(path)) {
            //so we taking random cell from the path array
            int[] toDelete = path.get(randomGenerator.nextInt(path.size()));
            path.remove(toDelete);
            int d = toDelete[0];
            int x = toDelete[1];
            int y = toDelete[2];
            //board[x][y] = 1; // marked as visited

            //we are finding his neighbors bottom, left, right, top and visited so i can connect to who ever i want
            ArrayList<int[]> n = neighbors(d, x, y, board);

            //if there is neighbors we want to pick one randomaly
            if (!this.empty(n)) {
                int[] toContinue = n.get(randomGenerator.nextInt(n.size()));
                int nd = toContinue[0];
                int nx = toContinue[1];
                int ny = toContinue[2];
                // now we want to know how we went so we will call direction func
                board = this.direction(d, x, y, nd, nx, ny, board);

            }

            this.markAsVisited(d, x, y, board, path);


//            for (int i = 0; i < board.length; i++){
//                System.out.println(Arrays.toString(board[i]));
//            }
//            System.out.println("\n");


        }
        //return board;
    }


}

package algorithms.mazeGenerators;

import java.util.*;

public class MyMazeGenerator extends AMazeGenerator {
    @Override
    // the method generate the maze
    public Maze generate(int rows, int columns) {
        // first making an empty board
        int[][] board = new int[rows][columns];

        // the path will help us determine from what cells we will randomly pick in prim algo
        ArrayList<int[]> path = new ArrayList<>();

        //randomize cell to start the algorithm from
        Random randomGenerator = new Random();
        int rowFirst = randomGenerator.nextInt(rows - 1);
        int columnFirst = randomGenerator.nextInt(columns - 1);
        this.addPath(rowFirst, columnFirst, board, path);
        board[rowFirst][columnFirst] = 1;

        this.Prim(board, path);

        //change the board that return from prim the 1's and 0's
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = 1;
                } else {
                    board[i][j] = 0;
                }
            }
        }

        int[] startEndArray = this.startEndFunc(rows, columns);
        board = this.makeAPassToStartEnd(startEndArray, board);

        //return maze object
        return new Maze(new Position(startEndArray[0], startEndArray[1]), new Position(startEndArray[2], startEndArray[3]), board);
    }

    //method that checking if we can add the cell to path array
    public void addPath(int x, int y, int[][] board, ArrayList<int[]> path) {
        //checks that the cell inside the board and there is no duplicate by path contains
        if (y >= 0 && x >= 0 && x < board.length && y < board[x].length && board[x][y] == 0 && !pathContain(x, y, path)) {
            path.add(new int[]{x, y});
        }
    }

    //like an equal private method to help us check if the array already contain the cell.
    private boolean pathContain(int x, int y, ArrayList<int[]> path) {
        for (int i = 0; i < path.size(); i++) {
            if (path.get(i)[0] == x && path.get(i)[1] == y) {
                return true;
            }
        }
        return false;
    }

    //for each cell we go threw in prim we will add the neighbors for the next iteration
    public void markAsVisited(int x, int y, int[][] board, ArrayList<int[]> path) {
//        board[x][y] = 1;
        addPath(x - 2, y, board, path);
        addPath(x + 2, y, board, path);
        addPath(x, y - 2, board, path);
        addPath(x, y + 2, board, path);
    }


    //add the neighbors
    private ArrayList<int[]> neighbors(int x, int y, int[][] board) {

        ArrayList<int[]> nb = new ArrayList<>();

        if (x > 1 && inside(x - 2, y, board)) {
            nb.add(new int[]{x - 2, y});
        }
        if (x + 2 < board.length && inside(x + 2, y, board)) {
            nb.add(new int[]{x + 2, y});
        }
        if (y > 1 && inside(x, y - 2, board)) {
            nb.add(new int[]{x, y - 2});
        }
        if (y + 2 < board[x].length && inside(x, y + 2, board)) {
            nb.add(new int[]{x, y + 2});
        }

        return nb;
    }

    //check validation
    private boolean inside(int x, int y, int[][] grid) {
        if (y >= 0 && x >= 0 && x < grid.length && y < grid[x].length && grid[x][y] == 1) {
            return true;
        }
        return false;
    }

    //connect the boards new neighbor selected
    public int[][] direction(int x, int y, int nx, int ny, int[][] board) {
        if (y < ny) {
            board[x][y] = 1;
            board[x][y + 1] = 1;
            board[x][y + 2] = 1; //the same as board[x][ny] = 1
        } else if (y > ny) {
            board[x][y] = 1;
            board[x][y - 1] = 1;
            board[x][y - 2] = 1; //the same as board[x][ny] = 1
        } else if (x < nx) {
            board[x][y] = 1;
            board[x + 1][y] = 1;
            board[x + 2][y] = 1; //the same as board[nx][y] = 1
        } else if (x > nx) {
            board[x][y] = 1;
            board[x - 1][y] = 1;
            board[x - 2][y] = 1; //the same as board[nx][y] = 1
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
    public void Prim(int[][] board, ArrayList<int[]> path) {
        Random randomGenerator = new Random();

        while (!this.empty(path)) {
            //so we taking random cell from the path array
            int[] toDelete = path.get(randomGenerator.nextInt(path.size()));
            path.remove(toDelete);
            int x = toDelete[0];
            int y = toDelete[1];
            //board[x][y] = 1; // marked as visited

            //we are finding his neighbors bottom, left, right, top and visited so i can connect to who ever i want
            ArrayList<int[]> n = neighbors(x, y, board);

            //if there is neighbors we want to pick one randomaly
            if (!this.empty(n)) {
                int[] toContinue = n.get(randomGenerator.nextInt(n.size()));
                int nx = toContinue[0];
                int ny = toContinue[1];
                // now we want to know how we went so we will call direction func
                board = this.direction(x, y, nx, ny, board);

            }

            this.markAsVisited(x, y, board, path);


//            for (int i = 0; i < board.length; i++){
//                System.out.println(Arrays.toString(board[i]));
//            }
//            System.out.println("\n");


        }
        //return board;
    }
}

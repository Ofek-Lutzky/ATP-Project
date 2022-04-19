package algorithms.mazeGenerators;
import java.util.*;

public class MyMazeGenerator extends AMazeGenerator{
    @Override
    public Maze generate(int rows, int columns) {
        int[][] board = new int[rows][columns];
        ArrayList<int[]> path = new ArrayList<int[]>();
        Random randomGenerator=new Random();
        int rowFirst = randomGenerator.nextInt(rows-1);
        int columnFirst = randomGenerator.nextInt(columns-1);
        this.addPath(rowFirst,columnFirst,board, path);
        board[rowFirst][columnFirst] = 1;
        this.Prim(rows,columns,board,path);

        for (int i = 0; i < board.length; i++){
            System.out.println(Arrays.toString(board[i]));
        }

        return null;
    }

    public void addPath(int x, int y, int[][] board, ArrayList<int[]> path){
        //todo check y < grid.length if its not x
        if (y>=0 && x>=0 && x < board.length && y < board[x].length && board[x][y] == 0 && !path.contains(new int[]{x, y})){
//            board[x][y] = 0;
            path.add(new int[]{x, y});//todo check
        }
    }



    public void markAsVisited(int x, int y, int[][] board, ArrayList<int[]> path){
//        board[x][y] = 1;
        addPath(x-2, y, board, path);
        addPath(x+2, y, board, path);
        addPath(x, y-2, board, path);
        addPath(x, y+2, board, path);
    }


    public ArrayList<int[]> neighbors(int x,int y,int[][] board){
        //todo check
        ArrayList<int[]> nb = new ArrayList<int[]>();

        if (x>1 && inside(x-2,y,board)){
            nb.add(new int[]{x-2, y});
        }
        if (x+2 < board.length && inside(x+2,y,board)){
            nb.add(new int[]{x+2, y});
        }
        if (y > 1 && inside(x,y-2,board)){
            nb.add(new int[]{x, y-2});
        }
        if (y+2 < board[x].length && inside(x,y+2,board)){
            nb.add(new int[]{x, y+2});
        }

        return nb;
    }

    private boolean inside(int x,int y,int[][] grid){
        if (y>=0 && x>=0 && x < grid.length && y < grid[x].length && grid[x][y] == 1 ){
            return true;
        }
        return false;
    }
//    private boolean inside(int x,int y,int[][] grid){
//        //todo check if i can remove the func and who that use it
//        if (y>=0 && x>=0 && x < grid.length && y < grid[x].length){
//            return true;
//        }
//        return false;
//    }


    //connect the boards new neighbor selected

    public int[][] direction(int x,int y,int nx,int ny, int[][] board){
        if (y<ny){
            board[x][y] = 1;
            board[x][y+1] = 1;
            board[x][y+2] = 1; //the same as board[x][ny] = 1
        }
        else if (y>ny){
            board[x][y] = 1;
            board[x][y-1] = 1;
            board[x][y-2] = 1; //the same as board[x][ny] = 1
        }
        else if (x<nx){
            board[x][y] = 1;
            board[x+1][y] = 1;
            board[x+2][y] = 1; //the same as board[nx][y] = 1
        }
        else if (x>nx){
            board[x][y] = 1;
            board[x-1][y] = 1;
            board[x-2][y] = 1; //the same as board[nx][y] = 1
        }
        return board;//todo

    }


//    public int[][] direction(int x,int y,int nx,int ny, int[][] board){
//
//    }



    private boolean empty(ArrayList<int[]> path){
        if (path.size() == 0){
            return true;
        }
        return false;

    }


    public int[][] Prim(int rows, int columns,int[][] board, ArrayList<int[]> path) {
        Random randomGenerator=new Random();
        //add the first random cell
        //i added it from the calling function
//        this.addPath(randomGenerator.nextInt(rows-1),randomGenerator.nextInt(columns-1),board, path);

        while (!this.empty(path)){

            //so we taking random cell from the path array
            int[] toDelete = path.get(randomGenerator.nextInt(path.size()));
            path.remove(toDelete);
            int x = toDelete[0];
            int y = toDelete[1];
            //board[x][y] = 1; // marked as visited
//
            //we are finding his neighbors bottom, left, right, top and visited so i can connect to who ever i want
            ArrayList<int[]> n = neighbors(x,y,board);

            //if there is neighbors we want to pick one randomaly
            if (!this.empty(n)){
                int[] toContinue = n.get(randomGenerator.nextInt(n.size()));
                int nx = toContinue[0];
                int ny = toContinue[1];
                // now we want to know how we went so we will call direction func
                board = this.direction(x,y,nx,ny,board);

                //this.markAsVisited(nx, ny, board, path);
            }

            this.markAsVisited(x, y, board, path);


            for (int i = 0; i < board.length; i++){
                System.out.println(Arrays.toString(board[i]));
            }
            System.out.println("\n");


        }
        return board;
    }
}

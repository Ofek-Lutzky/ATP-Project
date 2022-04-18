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
        this.Prim(rows,columns,board,path);

        for (int i = 0; i < board.length; i++){
            System.out.println(Arrays.toString(board[i]));
        }

        return null;
    }

    public void addPath(int x, int y, int[][] board, ArrayList<int[]> path){
        //todo check y < grid.length if its not x
        if (y>=0 && x>=0 && x < board.length && y < board[x].length && board[x][y] == 0){
//            board[x][y] = 0;
            path.add(new int[]{x, y});//todo check
        }
    }

    public ArrayList<int[]> neighbors(int x,int y,int[][] board){
        //todo check
        ArrayList<int[]> nb = new ArrayList<int[]>();

        if (x>0 && inside(x-1,y,board)){
            nb.add(new int[]{x-1, y});
        }
        if (x+1 < board.length && inside(x+1,y,board)){
            nb.add(new int[]{x+1, y});
        }
        if (y > 0 && inside(x,y-1,board)){
            nb.add(new int[]{x, y-1});
        }
        if (y+1 < board[x].length && inside(x,y+1,board)){
            nb.add(new int[]{x, y+1});
        }

        return nb;
    }

    public void markAsVisited(int x, int y, int[][] board, ArrayList<int[]> path){
        board[x][y] = 1;
        addPath(x-1, y, board, path);
        addPath(x+1, y, board, path);
        addPath(x, y-1, board, path);
        addPath(x, y+1, board, path);
    }



    private boolean inside(int x,int y,int[][] grid){
        if (y>=0 && x>=0 && x < grid.length && y < grid[x].length && grid[x][y] == 0){
            return true;
        }
        return false;
    }


    //right - 3 left - -3 up - 4 down - -4
//    public int direction(int fx,int fy,int tx,int ty){
//        if (fx<tx){
//            return 3;
//        }
//        else if (fx>tx){
//            return -3;
//        }
//        else if (fy<ty){
//            return -4;
//        }
//        else if (fy>ty){
//            return 4;
//        }
//        return 2;//todo

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

            //we are finding his neighbors
            ArrayList<int[]> n = neighbors(x,y,board);

            //if there is neighbors we want to pick one randomaly
            if (!this.empty(n)){
                int[] toContinue = n.get(randomGenerator.nextInt(n.size()));
                int nx = toContinue[0];
                int ny = toContinue[1];
                // now we want to know how we went so we will call direction func
//                int dir = this.direction(x,y,nx,ny);
//                board[x][y] = dir;
//                board[nx][ny] = -dir; // opposite direction
//                board[x][y] = 1;
                board[nx][ny] = 1;
            }

            this.markAsVisited(x, y, board, path);

//            for (int i = 0; i < board.length; i++){
//                System.out.println(Arrays.toString(board[i]));
//            }
//            System.out.println("\n");


        }
        return board;
    }
}

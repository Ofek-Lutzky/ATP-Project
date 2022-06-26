package Model;

import Client.*;
import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import Server.*;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class MyModel extends Observable implements IModel{
    private Maze maze;
    private Solution sol;
    private int characterRow;
    private int characterCol;
    private Server serverMazeGenerator;
    private Server serverSolveMaze;



    private boolean finishGame = false;
    private boolean solutionShow = false;


    public MyModel() {
       // maze = null;
        //rowChar =0;
        //colChar =0; todo think about it
        serverMazeGenerator = new Server(5400,1000, new ServerStrategyGenerateMaze());
        serverMazeGenerator.start();
        serverSolveMaze = new Server(5401,1000, new ServerStrategySolveSearchProblem());
        serverSolveMaze.start();
    }

    public void setFinishGame(boolean finishGame) {
        this.finishGame = finishGame;
    }

    @Override
    public void updateCharacterLocation(CharacterMovementDirection direction) {
        /*
            direction = 8 -> Up
            direction = 2 -> Down
            direction = 4 -> Left
            direction = 6 -> Right

            direction = 9 -> Up Right -> UR
            direction = 7 -> Up Left -> UL
            direction = 3 -> Down Right -> DR
            direction = 1 -> Down Left -> DL
         */

        System.out.println("MyModel updateCharacterLocation " + direction);

        switch(direction)
        {
            case UP: // todo maybe need מקרי קצה לא חושב שצריך את המינוס אחד במקרה הקיצוני של כולם
                if (characterRow > 0 && checkIfNotWall(characterRow - 1, characterCol))
                    moveCharacter(characterRow - 1, characterCol);
                break;
            case DOWN:
                if (characterRow < maze.getMap().length-1 && checkIfNotWall(characterRow + 1, characterCol))
                    moveCharacter(characterRow + 1, characterCol);
                break;
            case LEFT:
                if (characterCol > 0 && checkIfNotWall(characterRow , characterCol-1))
                    moveCharacter(characterRow, characterCol-1);
                break;
            case RIGHT:
                if (characterCol < maze.getMap()[0].length-1 && checkIfNotWall(characterRow , characterCol+1))
                    moveCharacter(characterRow, characterCol+1);
                break;

            //Slants need to also check that the move free in the two steps to the slant
            case UR:
                if (characterRow - 1 >= 0 && characterCol + 1 < maze.getMap()[0].length && maze.getMap()[characterRow - 1][characterCol + 1] == 0 && (maze.getMap()[characterRow - 1][characterCol] == 0 || maze.getMap()[characterRow][characterCol + 1] == 0)) {
                    moveCharacter(characterRow-1,characterCol+1);
                }
                break;
            case UL:
                if (characterRow - 1 >= 0 && characterCol - 1 >= 0 && maze.getMap()[characterRow - 1][characterCol - 1] == 0 && (maze.getMap()[characterRow - 1][characterCol] == 0 || maze.getMap()[characterRow][characterCol - 1] == 0)) {
                    moveCharacter(characterRow-1,characterCol-1);
                }
                break;
            case DR:
                if (characterRow + 1 < maze.getMap().length && characterCol + 1 < maze.getMap()[0].length && maze.getMap()[characterRow + 1][characterCol + 1] == 0 && (maze.getMap()[characterRow + 1][characterCol] == 0 || maze.getMap()[characterRow][characterCol + 1] == 0)) {
                    moveCharacter(characterRow+1,characterCol+1);
                }
                break;
            case DL:
                if (characterRow + 1 < maze.getMap().length && characterCol - 1 >= 0 && maze.getMap()[characterRow + 1][characterCol - 1] == 0 && (maze.getMap()[characterRow + 1][characterCol] == 0 || maze.getMap()[characterRow][characterCol - 1] == 0)) {
                    moveCharacter(characterRow+1,characterCol-1);
                }
                break;

        }


//        setChanged(); the move Character doing this
//        notifyObservers();

    }

    private boolean checkIfNotWall(int row,int col){
        if (maze.getMap()[row][col]!=1){
            return true;
        }
        return false;
    }


    @Override
    public void generateMaze(int rows, int cols) {
        solutionRestart();
        CommunicateWithServer_MazeGenerating(rows,cols);

        setChanged();
        notifyObservers("mazeGenerated");

        System.out.println("MyModel generateMaze" + maze.getStartPosition().getRowIndex() +" "+ maze.getStartPosition().getColumnIndex());
        moveCharacter(maze.getStartPosition().getRowIndex(), maze.getStartPosition().getColumnIndex());
    }

    private void moveCharacter(int rowIndex, int columnIndex) {

        System.out.println("MyModel moveCharacter" + rowIndex +" "+ columnIndex);

        if (rowIndex == maze.getGoalPosition().getRowIndex() && columnIndex == maze.getGoalPosition().getColumnIndex())
        {
            System.out.println("Mymodel" +"got to the end point");
            this.finishGame=true;
        }

        this.characterRow = rowIndex;
        this.characterCol = columnIndex;

        if(this.sol != null && this.solutionShow){
            solveCurrentStateMaze(); // call to it becuase not include the notify to observers
        }
        setChanged();
        notifyObservers("playerMoved");

    }

    //solve the maze from the current state
    public void solveCurrentStateMaze() {
        Position characterPos = new Position(characterRow,characterCol);
        maze.setStartPosition(characterPos);

        CommunicateWithServer_SolveSearchProblem(this.maze); }


    @Override
    public Maze getMaze() {
        return this.maze;
    }



    @Override
    public int getCharacterRow() {
        return this.characterRow;
    }

    @Override
    public int getCharacterCol() {
        return this.characterCol;
    }

    @Override
    public void assignObserver(Observer o) {
        this.addObserver(o);
    }

    @Override
    public void solveMaze() {
        Position characterPosition = new Position(characterRow,characterCol);
        maze.setStartPosition(characterPosition);
        CommunicateWithServer_SolveSearchProblem(maze);

        setChanged();
        notifyObservers("mazeSolved");
    }

    @Override
    public Solution getSolution() {
        return this.sol;
    }

    @Override
    public boolean gameOver() {
        return finishGame;
    }


    @Override
    public void setGameOver(boolean gameOver) {
        finishGame = gameOver;
    }

    @Override
    public void stopServers() {
        serverSolveMaze.stop();
        serverMazeGenerator.stop();
    }

    @Override
    public boolean saveFile() // todo change it to be like getting name dialog like the loading
    {
        if(maze != null) {

            Path path = Paths.get("AllMazes");

            if (!Files.exists(path)) {
                new File("AllMazes").mkdir();
            }

            String savedMazeName = new Date().getTime() + ".txt";

            String mazeFileName = "AllMazes" + '\\' + savedMazeName;

            try {
                OutputStream out = new MyCompressorOutputStream(new FileOutputStream(mazeFileName));
                out.write(maze.toByteArray());
                out.flush();
                out.close();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                //logger.error("couldn't save maze");
                return false;
            }
            //logger.info("maze saved");
            return true;
        }
        return false;
    }

    @Override
    public boolean loadFile(String name) {

        byte[] savedMazeBytes;

        try {
            InputStream in = new MyDecompressorInputStream(new FileInputStream(name));
            savedMazeBytes = new byte[100000];
            in.read(savedMazeBytes);
            in.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            //logger.error("couldn't load maze");
            return false;
        }

        maze = new Maze(savedMazeBytes);
        moveCharacter(maze.getStartPosition().getRowIndex(), maze.getStartPosition().getColumnIndex());

        setChanged();
        notifyObservers("mazeLoaded");
        //logger.info("maze loaded from file");
        return true;
    }


    @Override
    public void setShowSolution(boolean toShow) {
        solutionShow = toShow;

    }

    @Override
    public void solutionRestart() {
        this.sol = null;
        setChanged();
        notifyObservers("removeSolution");
    }




    private void CommunicateWithServer_MazeGenerating(int rows,int cols) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{rows, cols};
                        toServer.writeObject(mazeDimensions);
                        toServer.flush();
                        byte[] compressedMaze = (byte[])fromServer.readObject();
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[rows*cols];
                        is.read(decompressedMaze);
                        maze = new Maze(decompressedMaze);
//                        logger.info("generated maze in size " + maze.getMaze().length +"*" + maze.getMaze()[0].length);
                    }
                    catch (Exception var10)
                    {
//                        logger.fatal("failed to generate maze");
                        System.out.println(var10.getMessage());
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException var1)
        {
//            logger.fatal("failed to connect with server to generate maze");
        }
    }


    private void CommunicateWithServer_SolveSearchProblem(Maze maze) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        toServer.writeObject(maze);
                        toServer.flush();
                        sol = (Solution)fromServer.readObject();
//                        logger.info("maze solved with algorithem :" + Configurations.getConf().getSearchingAlgo());

                    } catch (Exception var10) {
//                        logger.error("failed to solve maze");
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException var1) {
//            logger.fatal("failed to connect server");
        }
    }



}

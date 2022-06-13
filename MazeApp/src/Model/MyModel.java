package Model;

import Client.*;
import IO.MyDecompressorInputStream;
import Server.*;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MyModel extends Observable implements IModel{
    private Maze maze;
    private Solution sol;
    private int playerRow;
    private int playerCol;
    private Server serverMazeGenerator;
    private Server serverSolveMaze;
    private boolean finishGame = false;
    private boolean solutionShow = false;


    public MyModel() {
        serverMazeGenerator = new Server(5400,1000, new ServerStrategyGenerateMaze());
        serverMazeGenerator.start();
        serverSolveMaze = new Server(5401,1000, new ServerStrategyGenerateMaze());
        serverSolveMaze.start();
    }

    @Override
    public void generateMaze(int rows, int cols) {
        solutionRestart();
        CommunicateWithServer_MazeGenerating(rows,cols);
    }

    @Override
    public Maze getMaze() {
        return this.maze;
    }

    @Override
    public void updatePlayerLocation(MovementDirection direction) {

    }

    @Override
    public int getPlayerRow() {
        return this.playerRow;
    }

    @Override
    public int getPlayerCol() {
        return this.playerCol;
    }

    @Override
    public void assignObserver(Observer o) {

    }

    @Override
    public void solveMaze() {
        Position playerPosition = new Position(playerRow,playerCol);
        maze.setStartPosition(playerPosition);
        CommunicateWithServer_SolveSearchProblem(maze);
    }

    @Override
    public Solution getSolution() {
        return this.sol;
    }

    @Override
    public boolean gameOver() {
        return false;
    }

    @Override
    public void setGameOver(boolean gameOver) {

    }

    @Override
    public void stopServers() {
        serverSolveMaze.stop();
        serverMazeGenerator.stop();
    }

    @Override
    public boolean saveFile() {
        return false;
    }

    @Override
    public boolean loadFile(String name) {
        return false;
    }

    @Override
    public void solutionRestart() {
        this.sol = null;

    }

    @Override
    public void setShowSolution(boolean b) {

    }
    /**
     * f- server communication
     * f- algo use (generate, solve)
     * f- save the maze the user is playing
     * f- save the current place of the character
     */


    private static void CommunicateWithServer_MazeGenerating(int rowsCount,int colsCount) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{rowsCount, colsCount};
                        toServer.writeObject(mazeDimensions); //send maze dimensions to server
                        toServer.flush();
                        byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[2500 /*CHANGE SIZE ACCORDING TO YOU MAZE SIZE*/]; //allocating byte[] for the decompressed maze -
                        is.read(decompressedMaze); //Fill decompressedMaze with bytes
                        Maze maze = new Maze(decompressedMaze); maze.print();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static void CommunicateWithServer_SolveSearchProblem(Maze maze) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new
                    IClientStrategy() {
                        @Override
                        public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                            try {
                                ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                                ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                                toServer.flush();
                                MyMazeGenerator mg = new MyMazeGenerator();
                                //Maze maze = mg.generate(50, 50);
                                maze.print();
                                toServer.writeObject(maze); //send maze to server
                                toServer.flush();
                                Solution mazeSolution = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                                //Print Maze Solution retrieved from the server
                                System.out.println(String.format("Solution steps: %s", mazeSolution));
                                ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();
                                for (int i = 0; i < mazeSolutionSteps.size(); i++) {
                                    System.out.println(String.format("%s. %s", i, mazeSolutionSteps.get(i).toString()));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}

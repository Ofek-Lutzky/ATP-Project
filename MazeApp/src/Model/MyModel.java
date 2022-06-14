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
        serverSolveMaze = new Server(5401,1000, new ServerStrategyGenerateMaze());
        serverSolveMaze.start();
    }


    @Override
    public void updateCharacterLocation(MovementDirection direction) {
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

        switch(direction)
        {
            case UP: // todo maybe need מקרי קצה
                characterRow--;
                break;
            case DOWN:
                characterRow++;
                break;
            case LEFT:
                characterCol--;
                break;
            case RIGHT:
                characterCol++;
                break;

            //Slants
            case UR:
                characterRow--;
                characterCol++;
                break;
            case UL:
                characterRow--;
                characterCol--;
                break;
            case DR:
                characterRow++;
                characterCol++;
                break;
            case DL:
                characterRow++;
                characterCol--;
                break;

        }
        setChanged();
        notifyObservers();
    }

    @Override
    public void generateMaze(int rows, int cols) {
        solutionRestart();
        CommunicateWithServer_MazeGenerating(rows,cols);

        setChanged();
        notifyObservers("mazeGenerated");
    }

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
        return this.characterRow;
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
        return false;
    }

    @Override
    public void setGameOver(boolean gameOver) {
        //todo check
        gameOver = true;
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
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException var1)
        {
//            logger.fatal("failed to connect with server to generate maze");
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

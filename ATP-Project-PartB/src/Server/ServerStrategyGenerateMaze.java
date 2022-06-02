package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;

import java.io.*;
import java.util.ArrayList;

public class ServerStrategyGenerateMaze implements IServerStrategy{

    /**
     *
     * @param inFromClient - the input stream that we get form the client  read the data
     * @param outToClient - the output stream that we get form the client return answer direct to the stream
     */
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);

            int[] sizeMazeRC = (int[])fromClient.readObject();

            int rows = sizeMazeRC[0];
            int cols = sizeMazeRC[1];

            IMazeGenerator generateMaze = null;

            String algorithmName = Configurations.getInstance().getGenerateAlgo();//the getConf is a singelton that giving us the object that hold the data

            if(algorithmName.equals("MyMazeGenerator"))
            {
                generateMaze = new MyMazeGenerator();
            }
            else if(algorithmName.equals("SimpleMazeGenerator"))
            {
                generateMaze = new SimpleMazeGenerator();
            }
            else if (algorithmName.equals("EmptyMazeGenerator"))
            {
                generateMaze = new EmptyMazeGenerator();
            }
            Maze maze = generateMaze.generate(rows,cols);
            byte[] mazeConvertToBytes = maze.toByteArray();
            ByteArrayOutputStream bufferByteArrayOut = new ByteArrayOutputStream();
            // the buffer will change it size occurding to the need it is dynamic allocate
            MyCompressorOutputStream out = new MyCompressorOutputStream(bufferByteArrayOut);
            //out to the buffer bufferByteArrayOut the maze by bytes
            out.write(mazeConvertToBytes);
            //empty the buffer that we wrote to
            out.flush();
            // out to the stream, what we wrote on the buffer
            toClient.writeObject(bufferByteArrayOut.toByteArray());

            //close the connection
            fromClient.close();
            toClient.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

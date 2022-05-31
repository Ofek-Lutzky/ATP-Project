package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;

import java.io.*;
import java.util.ArrayList;

public class ServerStrategyGenerateMaze implements IServerStrategy{


    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);

            int[] sizeMazeRC = (int[])fromClient.readObject();

            int rows = sizeMazeRC[0];
            int cols = sizeMazeRC[1];

            IMazeGenerator mazeGenerator;

            String algorithmName = Configurations.getConf().getGeneratingAlgo();//todo

            if(algorithmName.equals("MyMazeGenerator"))
            {
                mazeGenerator = new MyMazeGenerator();
            }
            else if(algorithmName.equals("SimpleMazeGenerator"))
            {
                mazeGenerator = new SimpleMazeGenerator();
            }
            else
            {
                mazeGenerator = new EmptyMazeGenerator();
            }
            Maze maze = mazeGenerator.generate(rows,cols);
            byte[] mazeConvertToBytes = maze.toByteArray();
            ByteArrayOutputStream bufferByteArrayOut = new ByteArrayOutputStream();
            //todo check how the MyCompressorOutputStream now what is the size of the buffer
            MyCompressorOutputStream out = new MyCompressorOutputStream(bufferByteArrayOut);
            //todo check why need to write to out and to object
            out.write(mazeConvertToBytes);
            out.flush();
            toClient.writeObject(bufferByteArrayOut.toByteArray());

            fromClient.close();
            toClient.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

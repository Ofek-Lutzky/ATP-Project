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

            int[] sizeRC = (int[])fromClient.readObject();

            int rows = sizeRC[0];
            int cols = sizeRC[1];
            IMazeGenerator mazeGenerator;

            String algorithmName = Configurations.getConf().getGeneratingAlgo();

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
            byte[] mazeConverToBytes = maze.toByteArray();
            ByteArrayOutputStream bufferByteArrayOut = new ByteArrayOutputStream();
            MyCompressorOutputStream out = new MyCompressorOutputStream(bufferByteArrayOut);
            out.write(mazeConverToBytes);
            out.flush();
            toClient.writeObject(bufferByteArrayOut.toByteArray());
            fromClient.close();
            toClient.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
